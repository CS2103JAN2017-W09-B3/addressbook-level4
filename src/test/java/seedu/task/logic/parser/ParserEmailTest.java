//@@author A0146789H
package seedu.task.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.EmailCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.UndoManager;

public class ParserEmailTest {
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
    public void parser_emailInvalid_failure() {
        String commandString = "email notanemail";
        Command result = this.parser.parseCommand(commandString);
        assertTrue(result instanceof IncorrectCommand);
    }


    /* Valid Tests
     *
     */

    @Test
    public void parser_emailValid_successful() {
        String commandString = "email e0011018@u.nus.edu";
        Command result = this.parser.parseCommand(commandString);

        assertTrue(result instanceof EmailCommand);
    }

}
