//@@author A0146789H
package seedu.task.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.task.logic.commands.AddTagCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.UndoManager;

public class ParserAddTagsTest {
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
    public void parser_addTags_noIndexTags() {
        Command result = this.parser.parseCommand("addtag");
        assertTrue(result instanceof IncorrectCommand);
    }

    @Test
    public void parser_addTags_noIndex() {
        Command result = this.parser.parseCommand("addtag #one");
        assertTrue(result instanceof IncorrectCommand);
    }

    /* Valid Tests
     *
     */

    @Test
    public void parser_addTags_oneTag() {
        String commandString = "addtag 5 #one";
        Command result = this.parser.parseCommand(commandString);

        assertTrue(result instanceof AddTagCommand);
    }

    @Test
    public void parser_addTags_noTags() {
        Command result = this.parser.parseCommand("addtag 1");
        assertTrue(result instanceof AddTagCommand);
    }
}
