package seedu.task.testutil;

import java.io.IOException;

import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.util.SampleDataUtil;

//@@author A0139938L
/**
 * Creates sample test tasks.
 */
public class TypicalTestTasks {

//    public TestTask task1, task2, task3, task4, task5, task6, task7, task8, task9;

    public TypicalTestTasks() {
//        try {
//            task1 = new TestTaskBuilder().withName("Task 1")
//                    .withCompletion(true).withEndDate("03/06/17 00:00")
//                    .withStartDate("03/06/17 00:00")
//                    .withTags("friends").build();
//            task2 = new TestTaskBuilder().withName("Task 2").withCompletion(false)
//                    .withEndDate("03/06/17 00:00").withStartDate("03/06/17 00:00")
//                    .withTags("owesMoney", "friends").build();
//            task3 = new TestTaskBuilder().withName("Task 3").withStartDate("03/06/17 00:00")
//                    .withEndDate("03/06/17 00:00").withCompletion(false).withTags("test").build();
//            task4 = new TestTaskBuilder().withName("Task 4").withStartDate("03/06/17 00:000")
//                    .withEndDate("03/06/17 00:000").withCompletion(false).withTags("test").build();
//            task5 = new TestTaskBuilder().withName("Task 5").withStartDate("03/06/17 00:000")
//                    .withEndDate("03/06/17 00:000").withCompletion(false).withTags("test").build();
//            task6 = new TestTaskBuilder().withName("Task 6").withStartDate("03/06/17 00:000")
//                    .withEndDate("03/06/17 00:000").withCompletion(true).withTags("test").build();
//            task7 = new TestTaskBuilder().withName("Task 7").withStartDate("03/06/17 00:000")
//                    .withEndDate("03/06/17 00:000").withCompletion(true).withTags("test").build();
//
//            // Manually added
//            task8 = new TestTaskBuilder().withName("Task 8").withStartDate("03/06/17 00:000")
//                    .withEndDate("03/06/17 00:000").withCompletion(false).withTags("test").build();
//            task9 = new TestTaskBuilder().withName("Task 9").withStartDate("03/06/17 00:000")
//                    .withEndDate("03/06/17 00:000").withCompletion(false).withTags("test").build();
//        } catch (IllegalValueException e) {
//            e.printStackTrace();
//            assert false : "not possible";
//        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) throws DataConversionException, IOException {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() throws DataConversionException, IOException {
        Task[] tasks = SampleDataUtil.getSampleTasks();
        TestTask[] testTasks = new TestTask[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            Task task = tasks[i];
            TestTask testTask = new TestTask();

            testTask.setName(task.getName());
            testTask.setStartTime(task.getStartTime());
            testTask.setEndTime(task.getEndTime());
            testTask.setCompletionStatus(task.getCompletionStatus());
            testTask.setTags(task.getTags());

            testTasks[i] = testTask;
        }
        return testTasks;
    }

    public TaskManager getTypicalTaskManager() throws DataConversionException, IOException {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
//@@author
