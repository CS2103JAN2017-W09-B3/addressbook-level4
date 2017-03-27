package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.Task;

public class RedoCommand extends Command {
	public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNDO_SUCCESS = "Redo Command Successful";
    public static final String NOTHING_TO_UNDO = "Nothing To Redo";


    @Override
    public CommandResult execute() throws CommandException {

//        if (model.getUndoManager().getCommandHistoryStatus()) {
//            return new CommandResult(NOTHING_TO_UNDO);
//        }

        String previousCommand = model.getUndoManager().popRedoCommand();

        if (model.getUndoManager().getRedoStackStatus()) {
            return new CommandResult(NOTHING_TO_UNDO);
        }

        System.out.println(previousCommand);

        switch (previousCommand) {
        case DeleteCommand.COMMAND_WORD:
            Task previousTask = model.getUndoManager().popRedoTask();
            new DeleteCommand().executeUndo(previousTask, model);
            break;
        case AddCommand.COMMAND_WORD:
            previousTask = model.getUndoManager().popRedoTask();
            new AddCommand().executeUndo(previousTask, model);
            break;
        case EditCommand.COMMAND_WORD:
            previousTask = model.getUndoManager().popRedoTask();
            Task editedTask = model.getUndoManager().popEditedTask();
            new EditCommand().executeUndo(previousTask, editedTask, model);
            break;
        default:
            return new CommandResult(NOTHING_TO_UNDO);
        }
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
}
