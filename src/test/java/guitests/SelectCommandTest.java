package guitests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.task.ReadOnlyTask;

public class SelectCommandTest extends TaskManagerGuiTest {


    @Test
    public void selectTask_nonEmptyList() {
        assertSelectionInvalid(100); // invalid index
        assertNoTaskSelected();

        assertSelectionSuccess(1); // first Task in the list
        int taskCount = 0;
        try {
            taskCount = td.getTypicalTasks().length;
        } catch (DataConversionException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertSelectionSuccess(taskCount); // last Task in the list
        int middleIndex = taskCount / 2;
        assertSelectionSuccess(middleIndex); // a Task in the middle of the list
        assertSelectionInvalid(taskCount + 1); // invalid index
        assertTaskSelected(middleIndex); // assert previous selection remains
    }

    @Test
    public void selectTask_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        ReadOnlyTask selectedTask = taskListPanel.getTask(index - 1);
        assertResultMessage("Selected Task: " + index + "\nTask Name: " +  selectedTask.getAsText());
        assertTaskSelected(index);
    }

    private void assertTaskSelected(int index) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = taskListPanel.getSelectedTasks().get(0);
        assertEquals(taskListPanel.getTask(index - 1), selectedTask);
    }

    private void assertNoTaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }

}
