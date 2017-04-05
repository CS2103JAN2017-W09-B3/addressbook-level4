package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.NattyDateUtil;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.UndoManager;

//@@author A0146789H
/**
 * Parses input arguments and creates a new AddCommand object
 */
/**
 * @author amon
 *
 */
public class AddCommandParser extends AbstractParser {

    private static final String PATTERN_MANDATORY_DESCRIPTION = "(?<description>.+?)";
    private static final String PATTERN_OPTIONAL_STARTDATE = "(?:\\s+(?:from|after|on)\\s+(?<startdate>.+?))?";
    private static final String PATTERN_OPTIONAL_ENDDATE = "(?:\\s+(?:to|by)\\s+(?<enddate>.+?))?";
    private static final String PATTERN_OPTIONAL_TAGS = "(?<tags>(?:\\s+#\\w+)+)?";
    private static final String ARGUMENTS_PATTERN = "^" + PATTERN_MANDATORY_DESCRIPTION + PATTERN_OPTIONAL_STARTDATE
            + PATTERN_OPTIONAL_ENDDATE + PATTERN_OPTIONAL_TAGS + "$";
    private static final String ARGUMENTS_PATTERN_QUOTED = "^" + PATTERN_MANDATORY_DESCRIPTION
            + PATTERN_OPTIONAL_STARTDATE + PATTERN_OPTIONAL_ENDDATE + PATTERN_OPTIONAL_TAGS + "$";
    private static final Pattern ARGUMENTS_FORMAT = Pattern.compile(ARGUMENTS_PATTERN, Pattern.CASE_INSENSITIVE);
    private static final Pattern ARGUMENTS_FORMAT_QUOTED = Pattern.compile(
            ARGUMENTS_PATTERN_QUOTED, Pattern.CASE_INSENSITIVE);

    private static final Logger logger = LogsCenter.getLogger(AddCommandParser.class);
    private static final String logPrefix = "[AddCommandParser]";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    @Override
    public Command parse(String args) {

        assert args != null;

        String taskName = null;
        String startDateString;
        String endDateString;
        String tagsString;
        Date startDate = null;
        Date endDate = null;

        Set<String> tagSet = new HashSet<String>();

        try {
            // Check for quoted task names
            if (args.length() > 0 && args.charAt(0) == '\'') {
                int nextIndex = args.indexOf('\'', 1);
                if (nextIndex > 0) {
                    taskName = args.substring(1, nextIndex);
                    args = args.substring(nextIndex);
                }
            }

            // Extract the tokens from the argument string using the appropriate pattern.
            Matcher matcher;
            if (taskName == null) {
                matcher = ARGUMENTS_FORMAT.matcher(args);
            } else {
                matcher = ARGUMENTS_FORMAT_QUOTED.matcher(args);
            }

            // Match on the processed arguments.
            if (!matcher.matches()) {
                throw new NoSuchElementException();
            }

            assert !args.isEmpty();

            // Extract the matcher groups
            if (taskName == null) {
                taskName = matcher.group("description");
            }

            startDateString = Optional.ofNullable(matcher.group("startdate")).orElse("");
            endDateString = Optional.ofNullable(matcher.group("enddate")).orElse("");
            tagsString = Optional.ofNullable(matcher.group("tags")).orElse("").trim();

            // Log tokens for debugging.
            logger.info(String.format("%s taskName: '%s', startDateString: '%s', endDateString: '%s', tags: '%s'",
                    logPrefix, taskName, startDateString, endDateString, tagsString));

            // Convert the String to Date objects
            startDate = NattyDateUtil.parseSingleDate(startDateString);
            endDate = NattyDateUtil.parseSingleDate(endDateString);

            // Remove the quotes if available
            taskName = taskName.charAt(0) == '\'' ? taskName.substring(1) : taskName;
            int lastIndex = taskName.length() - 1;
            taskName = taskName.charAt(lastIndex) == '\'' ? taskName.substring(0, lastIndex) : taskName;

            // Log the dates for debugging.
            if (startDate != null) {
                logger.info(String.format("%s startDate: %s", logPrefix, startDate.toString()));
            }
            if (endDate != null) {
                logger.info(String.format("%s endDate: %s", logPrefix, endDate.toString()));
            }

            // Add each tag to the tag set.
            tagSet = ParserUtil.parseTagStringToSet(tagsString);

            // Log the tags
            logger.info(String.format("%s tagSet: %s", logPrefix, tagSet.toString()));

            // Add the undo entry after successfully parsing an AddCommand
            UndoManager.pushUndoCommand(AddCommand.DEFACTO_COMMAND);

            return new AddCommand(
                    taskName,
                    startDate,
                    endDate,
                    false,
                    tagSet);
        } catch (NoSuchElementException nsee) {
            // TODO: This needs to be changed to the default case of search.
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see seedu.task.logic.parser.AbstractParser#isAcceptedCommand(java.lang.String)
     */
    @Override
    public boolean isAcceptedCommand(String command) {
        return AddCommand.isCommandWord(command);
    }
}

