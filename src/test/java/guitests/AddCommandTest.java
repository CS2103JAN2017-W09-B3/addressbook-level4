package guitests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.AddCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskBuilder;
import seedu.task.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() throws IllegalValueException {
        //@@author A0139410N
       //add one task
        TestTask[] currentList = null;
        try {
            currentList = td.getTypicalTasks();
        } catch (DataConversionException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        TestTask taskToAdd = new TestTaskBuilder(currentList[0])
                .withName("Another Task Name").withEndDate("today").build();
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = new TestTaskBuilder(currentList[1])
                .withName("Second Task").withEndDate("tomorrow").build();;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //@@author
        //add duplicate task
        commandBox.runCommand(currentList[2].getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(currentList[3]);

        //invalid command
        commandBox.runCommand("adds task99");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        String addedCardName = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertStringEquals(taskToAdd.getName().fullName, addedCardName);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
