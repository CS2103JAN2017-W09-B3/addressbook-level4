package seedu.task.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.util.FileUtil;
import seedu.task.model.ReadOnlyTaskManager;

/**
 * A class to access TaskManager data stored as an xml file on the hard disk.
 */
public class XmlTaskManagerStorage implements TaskManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskManagerStorage.class);

    private String filePath;

    public XmlTaskManagerStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getTaskManagerFilePath() {
        return filePath;
    }

    //@@author A0139938L

    /**
     * @param filePath the filePath to set
     */
    private void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Changes the save location and performs a save operation
     * @throws IOException
     */
    @Override
    public void changeSaveToLocation(ReadOnlyTaskManager taskManager, String filepath) throws IOException{
        setFilePath(filepath);
        this.saveTaskManager(taskManager, filepath);
    }
    //@@author

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(filePath);
    }

    /**
     * Similar to {@link #readTaskManager()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        assert filePath != null;

        File taskManagerFile = new File(filePath);

        if (!taskManagerFile.exists()) {
            logger.info("TaskManager file "  + taskManagerFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskManager taskManagerOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(taskManagerOptional);
    }

    //@@author A0139938L
    /**
     * Gets the ReadOnlyTaskManager object from the target file
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public ReadOnlyTaskManager getReadOnlyTaskManager(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        assert filePath != null;

        File taskManagerFile = new File(filePath);

        if (!taskManagerFile.exists()) {
            logger.info("TaskManager file "  + taskManagerFile + " not found");
            return null;
        }

        ReadOnlyTaskManager taskManager = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return taskManager;
    }
    //@@author

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
        saveTaskManager(taskManager, filePath);
    }

    /**
     * Similar to {@link #saveTaskManager(ReadOnlyTaskManager)}
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
        assert taskManager != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskManager(taskManager));
    }


}
