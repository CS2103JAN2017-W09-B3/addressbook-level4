//@@author A0146789H
package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ReminderCommand;

/**
 * @author amon
 *
 */
public class ReminderCommandParser extends AbstractParser {

    /* (non-Javadoc)
     * @see seedu.task.logic.parser.AbstractParser#parse(java.lang.String)
     */
    @Override
    public Command parse(String args) {
        String option = args.trim().toLowerCase();
        if (option.equals("enable") || option.equals("sync")) {
            return new ReminderCommand(true);
        } else if (args.equals("disable")) {
            return new ReminderCommand(false);
        }
        return new IncorrectCommand(ReminderCommand.MESSAGE_INCORRECT_ARGS);
    }

    /* (non-Javadoc)
     * @see seedu.task.logic.parser.AbstractParser#isAcceptedCommand(java.lang.String)
     */
    @Override
    public boolean isAcceptedCommand(String command) {
        return ReminderCommand.isCommandWord(command);
    }

}
