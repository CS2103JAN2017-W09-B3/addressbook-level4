//@@author A0146789H
package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.ListUncheckedCommand;

/**
 * @author amon
 *
 */
public class ListUncheckedCommandParser extends AbstractParser {

    @Override
    public Command parse(String args) {
        return new ListUncheckedCommand();
    }

    @Override
    public boolean isAcceptedCommand(String command) {
        return ListUncheckedCommand.isCommandWord(command);
    }

}
