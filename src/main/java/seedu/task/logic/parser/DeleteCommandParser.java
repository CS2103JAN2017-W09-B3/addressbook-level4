package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.UndoManager;

//@@author A0146789H
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser extends AbstractParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    @Override
    public Command parse(String args) {

        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent() || index.get() <= 0) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        // Add the undo entry after the DeleteCommand is successfully parsed.
        UndoManager.pushUndoCommand(DeleteCommand.DEFACTO_COMMAND);

        return new DeleteCommand(index.get());
    }

    /* (non-Javadoc)
     * @see seedu.task.logic.parser.AbstractParser#isAcceptedCommand(java.lang.String)
     */
    @Override
    public boolean isAcceptedCommand(String command) {
        return DeleteCommand.isCommandWord(command);
    }
}
