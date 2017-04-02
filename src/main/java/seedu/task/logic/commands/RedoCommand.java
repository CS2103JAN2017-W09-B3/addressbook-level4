//@@author A0138664W
package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.Task;

public class RedoCommand extends Command {
    //@@author A0146789H
    public static final String[] COMMAND_WORDS = new String[] {"redo"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    //@@author A0138664W
    public static final String MESSAGE_USAGE = DEFACTO_COMMAND
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + DEFACTO_COMMAND + " 1";

    public static final String MESSAGE_REDO_SUCCESS_EDIT =
            "Undo Command Successful.\nRestored previously edited task: %1$s";
    public static final String MESSAGE_REDO_SUCCESS_ADD =
            "Redo Command Successful.\nRestored previously added task: %1$s";
    public static final String MESSAGE_REDO_SUCCESS_DELETE = "Redo Command Successful.\nDeleted task: %1$s";
    public static final String MESSAGE_REDO_SUCCESS_CHECKED = "Redo Command Successful.\nChecked task: %1$s";
    public static final String MESSAGE_REDO_SUCCESS_UNCHEKED = "Redo Command Successful.\nUncheked task: %1$s";
    public static final String MESSAGE_REDO_SUCCESS = "Redo Command Successful.";
    public static final String NOTHING_TO_REDO = "Nothing To Redo";

    //@@author A0146789H
    public RedoCommand() {
        super(COMMAND_WORDS);
    }

    //@@author A0138664W
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

        // TODO: EDIT THIS
        switch (previousCommand) {
        case "delete":
            Task previousTask = model.getUndoManager().popRedoTask();
            return new DeleteCommand().executeRedo(previousTask, model);
        case "add":
            previousTask = model.getUndoManager().popRedoTask();
            return new AddCommand().executeRedo(previousTask, model);
        case "edit":
            previousTask = model.getUndoManager().popRedoEditedTask();
            Task editedTask = model.getUndoManager().popRedoTask();
            return new EditCommand().executeRedo(previousTask, editedTask, model);
        case "check":
            previousTask = model.getUndoManager().popRedoEditedTask();
            editedTask = model.getUndoManager().popRedoTask();
            return new CheckCommand().executeUndo(previousTask, editedTask, model);
        case "uncheck":
            previousTask = model.getUndoManager().popRedoEditedTask();
            editedTask = model.getUndoManager().popRedoTask();
            return new UncheckCommand().executeUndo(previousTask, editedTask, model);
        default:
            return new CommandResult(NOTHING_TO_REDO);
        }
    }

    //@@author A0146789H
    public static boolean isCommandWord(String command) {
        assert RedoCommand.COMMAND_WORDS != null;

        return isCommandWord(RedoCommand.COMMAND_WORDS, command);
    }
}
