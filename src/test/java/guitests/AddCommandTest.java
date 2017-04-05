package guitests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.AddCommand;
import seedu.task.model.task.Name;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() throws IllegalValueException {
        //add one task
        TestTask[] currentList = null;
        try {
            currentList = td.getTypicalTasks();
        } catch (DataConversionException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        TestTask taskToAdd = currentList[0];
        taskToAdd.setName(new Name("Another Task Name"));
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = currentList[1];
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

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
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
