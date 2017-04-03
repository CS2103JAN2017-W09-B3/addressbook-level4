//@@author A0146789H
package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.ListCheckedCommand;

/**
 * @author amon
 *
 */
public class ListCheckedCommandParser extends AbstractParser {

    /* (non-Javadoc)
     * @see seedu.task.logic.parser.AbstractParser#parse(java.lang.String)
     */
    @Override
    public Command parse(String args) {
        return new ListCheckedCommand();
    }

    /* (non-Javadoc)
     * @see seedu.task.logic.parser.AbstractParser#isAcceptedCommand(java.lang.String)
     */
    @Override
    public boolean isAcceptedCommand(String command) {
        return ListCheckedCommand.isCommandWord(command);
    }

}
