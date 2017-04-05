//@@author A0146789H
package seedu.task.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.Command;
import seedu.task.model.UndoManager;
import seedu.task.model.task.Name;
import seedu.task.model.task.Task;

public class ParserAddTest {
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


    /* Valid Tests
     *
     */

    // Add Command Tests

    @Test
    public void parser_add_floatingTask() {
        String commandString = "add Test Task";
        Command result = this.parser.parseCommand(commandString);

        // Check that the AddCommand is parsed properly
        assertTrue(result instanceof AddCommand);

        AddCommand added = (AddCommand) result;
        Task toAdd = added.getToAdd();

        // Check the description
        Name name = toAdd.getName();
        assertTrue(name.fullName.equals("Test Task"));
    }
}
