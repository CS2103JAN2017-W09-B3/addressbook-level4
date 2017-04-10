package guitests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.logic.commands.ClearCommand;
import seedu.task.testutil.TestTask;
//@@author A0139938L
public class ClearCommandTest extends TaskManagerGuiTest {

    @Test
    public void clear() {
        //verify a non-empty list can be cleared
        TestTask[] currentList = null;
        try {
            currentList = td.getTypicalTasks();
        } catch (DataConversionException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertTrue(taskListPanel.isListMatching(currentList));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(currentList[0].getAddCommand());
        assertTrue(taskListPanel.isListMatching(currentList[0]));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_SUCCESS);
    }
}
//@@author
