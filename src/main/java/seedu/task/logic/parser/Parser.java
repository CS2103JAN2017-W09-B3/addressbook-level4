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

       /*
        switch (commandWord) {
        // TODO: Fix this immediately
        case AddCommand.COMMAND_WORDS:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORDS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORDS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORDS:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORDS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORDS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORDS:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORDS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORDS:
            return new HelpCommand();

        case CheckCommand.COMMAND_WORDS:
            return new CheckedCommandParser().parse(arguments);

        case UncheckCommand.COMMAND_WORDS:
            return new UncheckedCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORDS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORDS:
            return new RedoCommand();

        case SaveToCommand.COMMAND_WORDS:
            return new SaveToCommandParser().parse(arguments);

        case LoadFromCommand.COMMAND_WORDS:
            return new LoadFromCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case "add":
            return new AddCommandParser().parse(arguments);

        case "edit":
            return new EditCommandParser().parse(arguments);

        case "select":
            return new SelectCommandParser().parse(arguments);

        case "delete":
            return new DeleteCommandParser().parse(arguments);

        case "clear":
            return new ClearCommand();

        case "find":
            return new FindCommandParser().parse(arguments);

        case "list":
            return new ListCommandParser().parse(arguments);

        case "exit":
            return new ExitCommand();

        case "help":
            return new HelpCommand();

        case "checked":
            return new CheckedCommandParser().parse(arguments);

        case "unchecked":
            return new UncheckedCommandParser().parse(arguments);

        case "undo":
            return new UndoCommand();

        case "redo":
            return new RedoCommand();

        case "saveto":
            return new SaveToCommandParser().parse(arguments);

        case "loadfrom":
            return new LoadFromCommandParser().parse(arguments);

        case "sort":
            return new SortCommandParser().parse(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
        */
    }
}
