package seedu.task.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.Model;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.CompletionStatus;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartTime;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

//@@author A0146789H
/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String[] COMMAND_WORDS = new String[] {"add", "-a"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    public static final String MESSAGE_USAGE = DEFACTO_COMMAND + ": Adds a task to the task manager. "
            + "Parameters: DESCRIPTION from START_DATE to END_DATE #TAGS\n"
            + "Example: " + DEFACTO_COMMAND
            + " Do CS2103 tutorial from 03/06/17 to 03/08/17 #CS2103 #uni";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private Task toAdd;

    /**
     * Creates an empty command with the relevant command words.
     *
     */
    public AddCommand() {
        super(COMMAND_WORDS);
    }

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name)
            throws IllegalValueException {
        this();
        final Set<Tag> tagSet = new HashSet<>();
        this.toAdd = new Task(
                new Name(name),
                new StartTime(null),
                new EndTime(null),
                new CompletionStatus(false),
                new UniqueTagList(tagSet)
        );
    }

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, Date startDate, Date endDate, boolean completionStatus, Set<String> tags)
            throws IllegalValueException {
        this();
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new StartTime(startDate),
                new EndTime(endDate),
                new CompletionStatus(completionStatus),
                new UniqueTagList(tagSet)
                );
    }

    /**
     * @return the task to be added
     */
    public Task getToAdd() {
        return toAdd;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getTaskID(toAdd)));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }
    //@@author A0138664W
    public CommandResult executeUndo(Task previousTask, Model model) throws CommandException {
        try {
            model.addTaskUndo(previousTask);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getTaskID(previousTask)));
            return new CommandResult(String.format(UndoCommand.MESSAGE_UNDO_SUCCESS_ADD, previousTask));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

    public CommandResult executeRedo(Task previousTask, Model model) throws CommandException {
        try {
            model.addTaskUndo(previousTask);
            return new CommandResult(String.format(RedoCommand.MESSAGE_REDO_SUCCESS_ADD, previousTask));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

    //@@author A0146789H
    public static boolean isCommandWord(String command) {
        assert AddCommand.COMMAND_WORDS != null;

        return isCommandWord(AddCommand.COMMAND_WORDS, command);
    }
}
