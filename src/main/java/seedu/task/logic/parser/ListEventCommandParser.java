//@@author A0146789H
package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.ListEventCommand;

public class ListEventCommandParser extends AbstractParser {

    /* (non-Javadoc)
     * @see seedu.task.logic.parser.AbstractParser#parse(java.lang.String)
     */
    @Override
    public Command parse(String args) {
        return new ListEventCommand();
    }

    /* (non-Javadoc)
     * @see seedu.task.logic.parser.AbstractParser#isAcceptedCommand(java.lang.String)
     */
    @Override
    public boolean isAcceptedCommand(String command) {
        return ListEventCommand.isCommandWord(command);
    }

}
