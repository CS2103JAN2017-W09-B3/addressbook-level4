//@@author A0138664W
package seedu.task.logic.commands;

import java.util.List;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.core.Messages;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.Model;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

public class CheckCommand extends TaskCompleted {

    //@@author A0146789H
    public static final String[] COMMAND_WORDS = new String[] {"check", "checked", "complete"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    public static final String MESSAGE_USAGE = DEFACTO_COMMAND + ": Mark task completion status to check/completed.\n"
            + "Example: " + DEFACTO_COMMAND + " 1\n"
            + "Parameters: INDEX (must be a positive integer)";

    //@@author A0138664W
    public static final String MESSAGE_CHECK_SUCCESS = "Task %1$s checked/completed!";
    public static final String MESSAGE_TASK_ALREADY_CHECKED = "Task %1$s is already checked.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private int filteredTaskListIndex;

    //@@author A0146789H
    protected CheckCommand () {
        super(COMMAND_WORDS);
    }

    //@@author A0138664W
    public CheckCommand (int filteredTaskListIndex) {
        this();
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMarkComplete = lastShownList.get(filteredTaskListIndex);

        if (taskToMarkComplete.getCompletionStatus().getCompletion() == true) {
            throw new CommandException(String.format(MESSAGE_TASK_ALREADY_CHECKED, taskToMarkComplete.getName()));
        }

        Task completedTask = changeTaskCompletion(taskToMarkComplete);

        try {
            model.updateTask(filteredTaskListIndex, completedTask);
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getTaskID(completedTask)));
        return new CommandResult(String.format(MESSAGE_CHECK_SUCCESS, taskToMarkComplete.getName()));
    }

    //@@author A0138664W
    public CommandResult executeUndo(Task previousTask, Task editedTask, Model model) throws CommandException {
        int taskID = model.getTaskID(editedTask);
        previousTask = changeTaskCompletion(previousTask);
        try {
            model.updateTaskUndo(taskID, previousTask);
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(UndoCommand.MESSAGE_UNDO_SUCCESS_CHECKED, previousTask));
    }

    public CommandResult executeRedo(Task previousTask, Task editedTask, Model model) throws CommandException {
        int taskID = model.getTaskID(editedTask);
        previousTask = changeTaskCompletion(previousTask);
        try {
            model.updateTaskUndo(taskID, previousTask);
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(RedoCommand.MESSAGE_REDO_SUCCESS_CHECKED, previousTask));
    }

    //@@author A0146789H
    public static boolean isCommandWord(String command) {
        assert CheckCommand.COMMAND_WORDS != null;

        return isCommandWord(CheckCommand.COMMAND_WORDS, command);
    }
}
