//@@author A0146789H
package seedu.task.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.NattyDateUtil;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.task.model.UndoManager;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.StartTime;

public class ParserEditTest {
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

    // Edit Command Tests

    @Test
    public void parser_edit_description() {
        String commandString = "edit 2 buy groceries";

        Command result = this.parser.parseCommand(commandString);

        // Check that the EditCommand is parsed properly
        assertTrue(result instanceof EditCommand);
        EditCommand toEdit = (EditCommand) result;
        EditTaskDescriptor taskDescriptor = toEdit.getEditTaskDescriptor();

        // Check that the proper fields are set
        assertTrue(taskDescriptor.getName().isPresent());
        assertTrue(taskDescriptor.getName().get().fullName.equals("buy groceries"));
        assertFalse(taskDescriptor.getStartTime().isPresent());
        assertFalse(taskDescriptor.getEndTime().isPresent());
        assertFalse(taskDescriptor.getCompletionStatus().isPresent());
        assertFalse(taskDescriptor.getTags().isPresent());
    }

    @Test
    public void parser_edit_descriptionDeadline() {
        String commandString = "edit 2 buy groceries by 05/13/17";
        EndTime compareEndTime = null;

        try {
            compareEndTime = new EndTime(NattyDateUtil.parseSingleDate("05/13/17"));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        Command result = this.parser.parseCommand(commandString);

        // Check that the EditCommand is parsed properly
        assertTrue(result instanceof EditCommand);
        EditCommand toEdit = (EditCommand) result;
        EditTaskDescriptor taskDescriptor = toEdit.getEditTaskDescriptor();

        // Check that the proper fields are set
        assertTrue(taskDescriptor.getName().isPresent());
        assertTrue(taskDescriptor.getName().get().fullName.equals("buy groceries"));
        assertFalse(taskDescriptor.getStartTime().isPresent());
        assertTrue(taskDescriptor.getEndTime().isPresent());
        assertTrue(taskDescriptor.getEndTime().get().equals(compareEndTime));
        assertFalse(taskDescriptor.getCompletionStatus().isPresent());
        assertFalse(taskDescriptor.getTags().isPresent());
    }

    @Test
    public void parser_edit_descriptionDeadlineWithQuotes() {
        String commandString = "edit 2 '' by 05/13/17";
        EndTime compareEndTime = null;

        try {
            compareEndTime = new EndTime(NattyDateUtil.parseSingleDate("05/13/17"));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        Command result = this.parser.parseCommand(commandString);

        // Check that the EditCommand is parsed properly
        assertTrue(result instanceof EditCommand);
        EditCommand toEdit = (EditCommand) result;
        EditTaskDescriptor taskDescriptor = toEdit.getEditTaskDescriptor();

        // Check that the proper fields are set
        assertFalse(taskDescriptor.getName().isPresent());
        assertFalse(taskDescriptor.getStartTime().isPresent());
        assertTrue(taskDescriptor.getEndTime().isPresent());
        assertTrue(taskDescriptor.getEndTime().get().equals(compareEndTime));
        assertFalse(taskDescriptor.getCompletionStatus().isPresent());
        assertFalse(taskDescriptor.getTags().isPresent());
    }
    @Test
    public void parser_edit_tags() {
        String commandString = "edit 2 #first #second";

        Command result = this.parser.parseCommand(commandString);

        // Check that the EditCommand is parsed properly
        assertTrue(result instanceof EditCommand);
        EditCommand toEdit = (EditCommand) result;
        EditTaskDescriptor taskDescriptor = toEdit.getEditTaskDescriptor();

        // Check that the proper fields are set
        assertFalse(taskDescriptor.getName().isPresent());
        assertFalse(taskDescriptor.getStartTime().isPresent());
        assertFalse(taskDescriptor.getEndTime().isPresent());
        assertFalse(taskDescriptor.getCompletionStatus().isPresent());
        assertTrue(taskDescriptor.getTags().isPresent());

        // Check tags
        UniqueTagList tags = taskDescriptor.getTags().get();
        Set<Tag> tagSet = tags.toSet();
        assertTrue(tagSet.size() == 2);
        try {
            assertTrue(tagSet.contains(new Tag("first")));
            assertTrue(tagSet.contains(new Tag("second")));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parser_edit_descriptionComplete() {
        String commandString = "edit 2 buy groceries from 05/13/17 to 05/14/17 #first #second";
        StartTime compareStartTime = null;
        EndTime compareEndTime = null;

        try {
            compareStartTime = new StartTime(NattyDateUtil.parseSingleDate("05/13/17"));
            compareEndTime = new EndTime(NattyDateUtil.parseSingleDate("05/14/17"));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        Command result = this.parser.parseCommand(commandString);

        // Check that the EditCommand is parsed properly
        assertTrue(result instanceof EditCommand);
        EditCommand toEdit = (EditCommand) result;
        EditTaskDescriptor taskDescriptor = toEdit.getEditTaskDescriptor();

        // Check that the proper fields are set
        assertTrue(taskDescriptor.getName().isPresent());
        assertTrue(taskDescriptor.getName().get().fullName.equals("buy groceries"));
        assertTrue(taskDescriptor.getStartTime().isPresent());
        assertTrue(taskDescriptor.getStartTime().get().equals(compareStartTime));
        assertTrue(taskDescriptor.getEndTime().isPresent());
        assertTrue(taskDescriptor.getEndTime().get().equals(compareEndTime));
        assertFalse(taskDescriptor.getCompletionStatus().isPresent());
        assertTrue(taskDescriptor.getTags().isPresent());

        // Check tags
        UniqueTagList tags = taskDescriptor.getTags().get();
        Set<Tag> tagSet = tags.toSet();
        assertTrue(tagSet.size() == 2);
        try {
            assertTrue(tagSet.contains(new Tag("first")));
            assertTrue(tagSet.contains(new Tag("second")));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }
}
