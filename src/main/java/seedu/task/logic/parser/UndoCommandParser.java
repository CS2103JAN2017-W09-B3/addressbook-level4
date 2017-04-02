//@@author A0146789H
package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.UndoCommand;

/**
 * @author amon
 *
 */
public class UndoCommandParser extends AbstractParser {

    /* (non-Javadoc)
     * @see seedu.task.logic.parser.AbstractParser#parse(java.lang.String)
     */
    @Override
    public Command parse(String args) {
        return new UndoCommand();
    }

    /* (non-Javadoc)
     * @see seedu.task.logic.parser.AbstractParser#isAcceptedCommand(java.lang.String)
     */
    @Override
    public boolean isAcceptedCommand(String command) {
        return UndoCommand.isCommandWord(command);
    }

}
