package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.Model;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0146789H
/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String[] COMMAND_WORDS = new String[] {"delete", "del", "remove", "-d", "rm"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    public static final String MESSAGE_USAGE = DEFACTO_COMMAND
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + DEFACTO_COMMAND + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "I have deleted the Task: %1$s";

    public int targetIndex;

    /**
     *  Creates an empty delete command with the relevant commands words.
     *
     */
    protected DeleteCommand() {
        super(COMMAND_WORDS);
    }

    public DeleteCommand(int targetIndex) {
        this();
        this.targetIndex = targetIndex;
    }

    //@@author
    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }
    //@@author A0138664W
    public CommandResult executeUndo(Task previousTask, Model model) throws CommandException {
        try {
            model.deleteTaskUndo(previousTask);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(UndoCommand.MESSAGE_UNDO_SUCCESS_DELETE, previousTask));
    }

    public CommandResult executeRedo(Task previousTask, Model model) throws CommandException {
        try {
            model.deleteTaskUndo(previousTask);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(RedoCommand.MESSAGE_REDO_SUCCESS_DELETE, previousTask));
    }

    //@@author A0146789H
    public static boolean isCommandWord(String command) {
        assert DeleteCommand.COMMAND_WORDS != null;

        return isCommandWord(DeleteCommand.COMMAND_WORDS, command);
    }
}
