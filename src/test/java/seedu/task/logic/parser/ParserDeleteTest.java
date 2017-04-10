//@@author A0146789H
package seedu.task.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.UndoManager;

public class ParserDeleteTest {
    private Parser parser;
    @SuppressWarnings("unused")
    private UndoManager undomanager;

    // Set up the fixtures
    @Before
    public void setUp() {
        this.parser = new Parser();
        // Initialize the UndoManager as well to create a previous command stack
        this.undomanager = new UndoManager();
    }

    /* Invalid Tests
     *
     */
    @Test
    public void parser_deleteNoIndex_incorrectCommand() {
        String commandString = "delete";
        Command result = this.parser.parseCommand(commandString);

        // Check that the DeleteCommand is not able to be  parsed properly
        assertTrue(result instanceof IncorrectCommand);
    }

    /* Valid Tests
     *
     */

    // Add Command Tests

    @Test
    public void parser_deleteIndex_successful() {
        String commandString = "delete 1";
        Command result = this.parser.parseCommand(commandString);

        // Check that the DeleteCommand is parsed properly
        assertTrue(result instanceof DeleteCommand);

        DeleteCommand deleted = (DeleteCommand) result;

        // Check the description
        int index = deleted.targetIndex;
        assertTrue(index == 1);
    }

}
