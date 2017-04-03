//@@author A0139410N
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListCheckedCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.ListDeadlineCommand;
import seedu.task.logic.commands.ListEventCommand;
import seedu.task.logic.commands.ListFloatingCommand;
import seedu.task.logic.commands.ListOverdueCommand;
import seedu.task.logic.commands.ListUncheckedCommand;
import seedu.task.logic.commands.ListUpcomingCommand;

/**
 * Parses input arguments and creates a new ListCommand/ListUncheckedCommand/ListCheckedCommand object
 */
public class ListCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an istCommand/ListUncheckedCommand/ListCheckedCommand object for execution.
     */
    public Command parse(String args) {

        // keywords delimited by whitespace
        final String keywords = args.trim();

        // if list is used without any keywords, return entire task list
        if (keywords.isEmpty()) {
            return new ListCommand();
        // if list is used with unchecked, return unchecked task list
        } else if (keywords.equals(ListUncheckedCommand.LIST_COMMAND_WORD)) {
            return new ListUncheckedCommand();
        // if list is used with checked, return checked task list
        } else if (keywords.equals(ListCheckedCommand.LIST_COMMAND_WORD)) {
            return new ListCheckedCommand();
        // if list is used with floating, return floating task list
        } else if (keywords.equals(ListFloatingCommand.LIST_COMMAND_WORD)) {
            return new ListFloatingCommand();
        // if list is used with deadline, return deadline task list
        } else if (keywords.equals(ListDeadlineCommand.LIST_COMMAND_WORD)) {
            return new ListDeadlineCommand();
        // if list is used with event, return event task list
        } else if (keywords.equals(ListEventCommand.LIST_COMMAND_WORD)) {
            return new ListEventCommand();
        // if list is used with upcoming, return upcoming task list
        } else if (keywords.equals(ListUpcomingCommand.LIST_COMMAND_WORD)) {
            return new ListUpcomingCommand();
        // if list is used with overdue, return overdue task list
        } else if (keywords.equals(ListOverdueCommand.LIST_COMMAND_WORD)) {
            return new ListOverdueCommand();
        // if keywords does not match any of the above, return incorrect usage
        } else {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

}
