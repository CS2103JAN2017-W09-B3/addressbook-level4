//@@author A0146789H
package seedu.task.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ReminderTimeCommand;
import seedu.task.model.UndoManager;

public class ParserReminderTimeTest {
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
    public void parser_timeInvalid_failure() {
        String commandString = "remindme a";
        Command result = this.parser.parseCommand(commandString);
        assertTrue(result instanceof IncorrectCommand);
    }

    @Test
    public void parser_timeNegative_failure() {
        String commandString = "remindme -4";
        Command result = this.parser.parseCommand(commandString);
        assertTrue(result instanceof IncorrectCommand);
    }

    /* Valid Tests
     *
     */

    @Test
    public void parser_timeValid_successful() {
        String commandString = "remindme 10";
        Command result = this.parser.parseCommand(commandString);

        assertTrue(result instanceof ReminderTimeCommand);
    }

}
