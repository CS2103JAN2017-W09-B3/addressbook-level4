package seedu.task.storage;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.eventbus.Subscribe;

import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.model.LoadFromRequestEvent;
import seedu.task.commons.events.model.SaveToRequestEvent;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.exceptions.DataLoadingExceptionEvent;
import seedu.task.commons.exceptions.DataSavingExceptionEvent;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.UserPrefs;

/**
 * Manages storage of TaskManager data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskManagerStorage taskManagerStorage;
    private UserPrefsStorage userPrefsStorage;
    private static final String VALID_FILE_REGEX = "(.+)?[\\w,\\s,\\d]+\\.xml$";
    private static final Pattern ARGUMENTS_FORMAT = Pattern.compile(VALID_FILE_REGEX);
    private static final String MESSAGE_INVALID_FILE = "Please specify a file name ending in .xml";


    public StorageManager(TaskManagerStorage taskManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taskManagerStorage = taskManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        this(new XmlTaskManagerStorage(addressBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ TaskManager methods ==============================

    @Override
    public String getTaskManagerFilePath() {
        return taskManagerStorage.getTaskManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(taskManagerStorage.getTaskManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskManagerStorage.readTaskManager(filePath);
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
        saveTaskManager(taskManager, taskManagerStorage.getTaskManagerFilePath());
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskManagerStorage.saveTaskManager(taskManager, filePath);
    }


    @Override
    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskManager(event.data);
        } catch (IOException e) {
            // TODO Auto-generated catch block

        }
    }

    //@@author A0139938L

    @Override
    @Subscribe
    public void handleSaveToRequestEvent(SaveToRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Changing save to location to " + event.filepath));
        changeSaveToLocation(event.taskManager, event.filepath);
    }

    @Override
    public void changeSaveToLocation(ReadOnlyTaskManager taskManager, String filepath) {
        try {
            if (this.isFileValidXmlFile(filepath)) {
                taskManagerStorage.changeSaveToLocation(taskManager, filepath);
                raise(new DataSavingExceptionEvent(""));
            } else {
                raise(new DataSavingExceptionEvent(this.MESSAGE_INVALID_FILE));
            }
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleLoadFromRequestEvent(LoadFromRequestEvent event) throws IOException, DataConversionException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Changing load from location to " + event.filepath));
        changeLoadFromLocation(event);
    }

    private void changeLoadFromLocation(LoadFromRequestEvent event) {
        try {
            if (this.isFileValidXmlFile(event.filepath)) {
                event.taskManager = readTaskManager(event.filepath);
                if (!event.taskManager.isPresent()) {
                    raise(new DataLoadingExceptionEvent("No such file exists."));
                } else {
                    raise(new DataLoadingExceptionEvent(""));
                }
            } else {
                raise(new DataLoadingExceptionEvent(this.MESSAGE_INVALID_FILE));
            }
        } catch (DataConversionException | IOException | NoSuchElementException e) {
            raise(new DataLoadingExceptionEvent(e));
        }
    }

    /**
     * Checks if file path is a valid xml file.
     * @param filepath
     * @return
     */
    private boolean isFileValidXmlFile(String filepath) {
        Matcher matcher = ARGUMENTS_FORMAT.matcher(filepath);
        return matcher.matches();
    }
    //@@author

}
