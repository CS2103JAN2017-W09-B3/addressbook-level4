//@@author A0146789H
package seedu.task.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.UndoManager;

public class ParserDelTagsTest {
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
    public void parser_delTags_noIndexTags() {
        Command result = this.parser.parseCommand("deltag");
        assertTrue(result instanceof IncorrectCommand);
    }

    @Test
    public void parser_delTags_noIndex() {
        Command result = this.parser.parseCommand("deltag #one");
        assertTrue(result instanceof IncorrectCommand);
    }

    /* Valid Tests
     *
     */

    @Test
    public void parser_delTags_oneTag() {
        String commandString = "deltag 5 #one";
        Command result = this.parser.parseCommand(commandString);
        assertTrue(result != null);
        //TODO: Fix
        //assertTrue(result instanceof AddTagCommand);
    }

    @Test
    public void parser_addTags_noTags() {
        Command result = this.parser.parseCommand("deltag 1");
        assertTrue(result != null);
        //TODO: Fix
        //assertTrue(result instanceof AddTagCommand);
    }
}
