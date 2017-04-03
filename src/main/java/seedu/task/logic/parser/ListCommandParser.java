//@@author A0139410N
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListCommand;

/**
 * Parses input arguments and creates a new ListCommand/ListUncheckedCommand/ListCheckedCommand object
 */
public class ListCommandParser extends AbstractParser {

    // Parsers are registered here as an AbstractParser ArrayList.
    private static final ArrayList<AbstractParser> registeredParsers = new ArrayList<AbstractParser>();

    //@@author A0146789H
    public ListCommandParser() {
        registeredParsers.add(new ListUncheckedCommandParser());
        registeredParsers.add(new ListCheckedCommandParser());
        registeredParsers.add(new ListFloatingCommandParser());
        registeredParsers.add(new ListDeadlineCommandParser());
        registeredParsers.add(new ListEventCommandParser());
        registeredParsers.add(new ListUpcomingCommandParser());
        registeredParsers.add(new ListOverdueCommandParser());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an istCommand/ListUncheckedCommand/ListCheckedCommand object for execution.
     */
    @Override
    public Command parse(String args) {

        // keywords delimited by whitespace
        final String keywords = args.trim();

        // if list is used without any keywords, return entire task list
        if (keywords.isEmpty()) {
            return new ListCommand();
        }

        // Run through the registered parsers and return the command if valid
        for (AbstractParser parser : registeredParsers) {
            if (parser.isAcceptedCommand(keywords)) {
                return parser.parse(keywords);
            }
        }

        // Return the incorrect command message if none matches.
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
    }

    //@@author A0146789H
    @Override
    public boolean isAcceptedCommand(String command) {
        return ListCommand.isCommandWord(command);
    }

}
