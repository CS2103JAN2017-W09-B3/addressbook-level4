//@@author A0138664W
package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.Task;

public class UndoCommand extends Command {
    //@@author A0146789H
    public static final String[] COMMAND_WORDS = new String[] {"undo"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    //@@author A0138664W
    public static final String MESSAGE_USAGE = DEFACTO_COMMAND
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + DEFACTO_COMMAND + " 1";
    public static final String MESSAGE_UNDO_SUCCESS_EDIT =
            "Undo Command Successful.\nRestored previously edited task: %1$s";
    public static final String MESSAGE_UNDO_SUCCESS_ADD =
            "Undo Command Successful.\nRestored previously added task: %1$s";
    public static final String MESSAGE_UNDO_SUCCESS_DELETE = "Undo Command Successful.\nDeleted task: %1$s";
    public static final String MESSAGE_UNDO_SUCCESS_CHECKED = "Undo Command Successful.\nChecked task: %1$s";
    public static final String MESSAGE_UNDO_SUCCESS_UNCHECKED = "Undo Command Successful.\nUnchecked task: %1$s";
    public static final String MESSAGE_UNDO_SUCCESS = "Undo Command Successful.";
    public static final String NOTHING_TO_UNDO = "Nothing To Undo";

    //@@author A0146789H
    public UndoCommand() {
        super(COMMAND_WORDS);
    }

    //@@author A0138664W
    @Override
    public CommandResult execute() throws CommandException {

        if (model.getUndoManager().getCommandHistoryStatus()) {
            return new CommandResult(NOTHING_TO_UNDO);
        }

        String previousCommand = model.getUndoManager().popUndoCommand();


        if (model.getUndoManager().getUndoStackStatus()) {
            return new CommandResult(NOTHING_TO_UNDO);
        }

        System.out.println(previousCommand);

        switch (previousCommand) {
        //TODO: FIX THIS
        case "add":
            Task previousTask = model.getUndoManager().popUndoTask();
            return new DeleteCommand().executeUndo(previousTask, model);
        case "delete":
            previousTask = model.getUndoManager().popUndoTask();
            return new AddCommand().executeUndo(previousTask, model);
        case "edit":
            previousTask = model.getUndoManager().popUndoTask();
            Task editedTask = model.getUndoManager().popEditedTask();
            return new EditCommand().executeUndo(previousTask, editedTask, model);
        case "uncheck":
            previousTask = model.getUndoManager().popUndoTask();
            editedTask = model.getUndoManager().popEditedTask();
            return new UncheckCommand().executeUndo(previousTask, editedTask, model);
        case "check":
            previousTask = model.getUndoManager().popUndoTask();
            editedTask = model.getUndoManager().popEditedTask();
            return new CheckCommand().executeUndo(previousTask, editedTask, model);
        default:
            return new CommandResult(NOTHING_TO_UNDO);
        }
    }

}
