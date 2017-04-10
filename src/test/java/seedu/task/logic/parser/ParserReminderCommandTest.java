//@@author A0146789H
package seedu.task.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ReminderCommand;
import seedu.task.model.UndoManager;

public class ParserReminderCommandTest {
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
    public void parser_emptyArgs_failure() {
        String commandString = "reminders";
        Command result = this.parser.parseCommand(commandString);
        assertTrue(result instanceof IncorrectCommand);
    }


    /* Valid Tests
     *
     */

    @Test
    public void parser_enable_successful() {
        String commandString = "reminders enable";
        Command result = this.parser.parseCommand(commandString);

        assertTrue(result instanceof ReminderCommand);
    }

    @Test
    public void parser_disable_successful() {
        String commandString = "reminders disable";
        Command result = this.parser.parseCommand(commandString);

        assertTrue(result instanceof ReminderCommand);
    }
}
