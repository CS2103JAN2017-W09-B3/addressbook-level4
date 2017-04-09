package seedu.task.model.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.task.Task;
import seedu.task.storage.XmlTaskManagerStorage;

//@@author A0139938L
public class SampleDataUtil {

    private static final String SAMPLE_DATA_FILE_PATH = "./sample/taskmanager.xml";

    public static Task[] getSampleTasks() throws DataConversionException, IOException {
        ReadOnlyTaskManager sampleTaskManager = getSampleTaskManager();
        Task[] tasks = sampleTaskManager.getTaskArray();
        List<Task> taskList = new ArrayList <Task> ();
        for (Task t : tasks) {
        	taskList.add(t);
        }
        taskList.sort(c);
        taskList.sort(checkUncheck);
        return taskList.toArray(new Task[taskList.size()]);
    }

    static Comparator<Task> c = new Comparator<Task>() {
        @Override
        public int compare(Task task1, Task task2) {
            Date task1Date = task1.getEndTime().getValue();
            Date task2Date = task2.getEndTime().getValue();

            if (!task1.hasEndTime()) {
                if (!task2.hasEndTime()) {
                    return 0;
                }
                return 1;
            } else if (task1.hasEndTime()) {
                if (!task2.hasEndTime()) {
                    return -1;
                }
            }

            if (task1Date.after(task2Date)) {
                return 1;
            } else if (task1Date.before(task2Date)) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    static Comparator<Task> checkUncheck = new Comparator<Task>() {

		@Override
		public int compare(Task o1, Task o2) {
			if(o1.getCompletionStatus().getCompletion()) {
				if(o2.getCompletionStatus().getCompletion()){
					return 0;
				}
				return 1;
			} else if(!o1.getCompletionStatus().getCompletion()){
				if(o2.getCompletionStatus().getCompletion()) {
					return -1;
				}
			}
			return 0;
		}

    };

    public static ReadOnlyTaskManager getSampleTaskManager() throws DataConversionException, IOException {
        // TODO: Make this read from resource
        XmlTaskManagerStorage sampleStorage = new XmlTaskManagerStorage(SAMPLE_DATA_FILE_PATH);
        ReadOnlyTaskManager taskManager = sampleStorage.getReadOnlyTaskManager(SAMPLE_DATA_FILE_PATH);

        return taskManager;
    }
}
//@@author

