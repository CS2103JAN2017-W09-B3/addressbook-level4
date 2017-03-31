package guitests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.task.ReadOnlyTask;

public class SelectCommandTest extends TaskManagerGuiTest {


    @Test
    public void selectPerson_nonEmptyList() {
        assertSelectionInvalid(10); // invalid index
        assertNoPersonSelected();

        assertSelectionSuccess(1); // first person in the list
        int personCount = 0;
        try {
            personCount = td.getTypicalTasks().length;
        } catch (DataConversionException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertSelectionSuccess(personCount); // last person in the list
        int middleIndex = personCount / 2;
        assertSelectionSuccess(middleIndex); // a person in the middle of the list

        assertSelectionInvalid(personCount + 1); // invalid index
        assertPersonSelected(middleIndex); // assert previous selection remains
    }

    @Test
    public void selectPerson_emptyList() {
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
        assertResultMessage("Selected Task: " + index);
        assertPersonSelected(index);
    }

    private void assertPersonSelected(int index) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedPerson = taskListPanel.getSelectedTasks().get(0);
        assertEquals(taskListPanel.getTask(index - 1), selectedPerson);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoPersonSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }

}
