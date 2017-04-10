//@@author A0146789H
package seedu.task.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteTagCommand;
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
    public void parser_delTagsNoIndexTags() {
        Command result = this.parser.parseCommand("deltag");
        assertTrue(result instanceof IncorrectCommand);
    }

    @Test
    public void parser_delTagsNoIndex_failure() {
        Command result = this.parser.parseCommand("deltag #one");
        assertTrue(result instanceof IncorrectCommand);
    }

    /* Valid Tests
     *
     */

    @Test
    public void parser_delTagsOneTag_successful() {
        String commandString = "deltag 5 #one";
        Command result = this.parser.parseCommand(commandString);
        assertTrue(result != null);
        assertTrue(result instanceof DeleteTagCommand);
    }

    @Test
    public void parser_addTagsNoTags_successful() {
        Command result = this.parser.parseCommand("deltag 1");
        assertTrue(result != null);
        assertTrue(result instanceof DeleteTagCommand);
    }
}
