//@@author A0138664W
package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.Task;

public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REDO_SUCCESS_EDIT =
            "Undo Command Successful.\nRestored previously edited task: %1$s";
    public static final String MESSAGE_REDO_SUCCESS_ADD =
            "Redo Command Successful.\nRestored previously added task: %1$s";
    public static final String MESSAGE_REDO_SUCCESS_DELETE = "Redo Command Successful.\nDeleted task: %1$s";
    public static final String MESSAGE_REDO_SUCCESS_CHECKED = "Redo Command Successful.\nChecked task: %1$s";
    public static final String MESSAGE_REDO_SUCCESS_UNCHEKED = "Redo Command Successful.\nUncheked task: %1$s";
    public static final String MESSAGE_REDO_SUCCESS = "Redo Command Successful.";
    public static final String NOTHING_TO_REDO = "Nothing To Redo";

    @Override
    public CommandResult execute() throws CommandException {

        if (model.getUndoManager().getRedoCommandHistoryStatus()) {
            return new CommandResult(NOTHING_TO_REDO);
        }

        String previousCommand = model.getUndoManager().popRedoCommand();

        if (model.getUndoManager().getRedoStackStatus()) {
            return new CommandResult(NOTHING_TO_REDO);
        }

        System.out.println(previousCommand);

        switch (previousCommand) {
        case DeleteCommand.COMMAND_WORD:
            Task previousTask = model.getUndoManager().popRedoTask();
            return new DeleteCommand().executeRedo(previousTask, model);
        case AddCommand.COMMAND_WORD:
            previousTask = model.getUndoManager().popRedoTask();
            return new AddCommand().executeRedo(previousTask, model);
        case EditCommand.COMMAND_WORD:
            previousTask = model.getUndoManager().popRedoEditedTask();
            Task editedTask = model.getUndoManager().popRedoTask();
            return new EditCommand().executeRedo(previousTask, editedTask, model);
        case CheckCommand.COMMAND_WORD:
            previousTask = model.getUndoManager().popRedoEditedTask();
            editedTask = model.getUndoManager().popRedoTask();
            return new CheckCommand().executeUndo(previousTask, editedTask, model);
        case UncheckCommand.COMMAND_WORD:
            previousTask = model.getUndoManager().popRedoEditedTask();
            editedTask = model.getUndoManager().popRedoTask();
            return new UncheckCommand().executeUndo(previousTask, editedTask, model);
        default:
            return new CommandResult(NOTHING_TO_REDO);
        }
    }
}
