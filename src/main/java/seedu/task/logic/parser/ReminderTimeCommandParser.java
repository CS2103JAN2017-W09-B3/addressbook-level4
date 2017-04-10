//@@author A0146789H
package seedu.task.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ReminderTimeCommand;

/**
 * @author amon
 *
 */
public class ReminderTimeCommandParser extends AbstractParser {
    public static final String PATTERN_POSITIVE_INT = "(^[1-9]\\d*$)";
    public static final Pattern ARGUMENTS_POSINT = java.util.regex.Pattern.compile(PATTERN_POSITIVE_INT);

    /* (non-Javadoc)
     * @see seedu.task.logic.parser.AbstractParser#parse(java.lang.String)
     */
    @Override
    public Command parse(String args) {
        String toMatch = args.trim();
        Matcher matcher = ARGUMENTS_POSINT.matcher(toMatch);
        if (matcher.matches()) {
            return new ReminderTimeCommand(Integer.parseInt(matcher.group(1)));
        }
        return new IncorrectCommand(ReminderTimeCommand.MESSAGE_INCORRECT);
    }

    /* (non-Javadoc)
     * @see seedu.task.logic.parser.AbstractParser#isAcceptedCommand(java.lang.String)
     */
    @Override
    public boolean isAcceptedCommand(String command) {
        return ReminderTimeCommand.isCommandWord(command);
    }
}
