package seedu.task.logic.parser;

import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;

//@@author A0146789H
public class ClearCommandParser extends AbstractParser {

    /* (non-Javadoc)
     * @see seedu.task.logic.parser.AbstractParser#parse(java.lang.String)
     */
    @Override
    public Command parse(String args) {
        return new ClearCommand();
    }

    /* (non-Javadoc)
     * @see seedu.task.logic.parser.AbstractParser#isAcceptedCommand(java.lang.String)
     */
    @Override
    public boolean isAcceptedCommand(String command) {
        return ClearCommand.isCommandWord(command);
    }

}
