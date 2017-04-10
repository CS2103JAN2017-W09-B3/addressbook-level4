package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.core.LogsCenter;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.logic.commands.IncorrectCommand;

//@@author A0146789H
/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     * Allows for case insensitive matching.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)",
            Pattern.CASE_INSENSITIVE);

    private static final Logger logger = LogsCenter.getLogger(Parser.class);
    private static final String logPrefix = "[PARSER]";

    // Parsers are registered here as an AbstractParser ArrayList.
    private static final ArrayList<AbstractParser> registeredParsers = new ArrayList<AbstractParser>();

    public Parser() {
        /*
         *  Register the parsers individually on setup.
         *  The order of the parsers registered is important.
         */

        registeredParsers.add(new AddCommandParser());
        registeredParsers.add(new EditCommandParser());
        registeredParsers.add(new SelectCommandParser());
        registeredParsers.add(new DeleteCommandParser());
        registeredParsers.add(new ClearCommandParser());
        registeredParsers.add(new FindCommandParser());
        registeredParsers.add(new ListCommandParser());
        registeredParsers.add(new ExitCommandParser());
        registeredParsers.add(new HelpCommandParser());
        registeredParsers.add(new CheckCommandParser());
        registeredParsers.add(new UncheckCommandParser());
        registeredParsers.add(new UndoCommandParser());
        registeredParsers.add(new RedoCommandParser());
        registeredParsers.add(new SaveToCommandParser());
        registeredParsers.add(new LoadFromCommandParser());
        registeredParsers.add(new SortCommandParser());
        registeredParsers.add(new AddTagsParser());
        registeredParsers.add(new DeleteTagsParser());
        registeredParsers.add(new ReminderCommandParser());
        registeredParsers.add(new EmailCommandParser());
        registeredParsers.add(new ReminderTimeCommandParser());
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        logger.info(logPrefix + " Raw User Input: '" + userInput + "'");

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments").trim();

        logger.info(logPrefix + " Command Word: '" + commandWord + "'");
        logger.info(logPrefix + " Arguments: '" + arguments + "'");

        // Run through the registered parsers and return the command if valid
        for (AbstractParser parser : registeredParsers) {
            if (parser.isAcceptedCommand(commandWord)) {
                return parser.parse(arguments);
            }
        }

        // Handle the default case.
        return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
    }
}
