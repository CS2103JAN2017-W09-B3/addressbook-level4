package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.UndoManager;

//@@author A0146789H
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser extends AbstractParser {

    private static final String PATTERN_MANDATORY_INDEX = "(?<index>[1-9]\\d*)";
    private static final String PATTERN_OPTIONAL_DESCRIPTION = "(?:\\s+(?<description>[^#]+?))?";
    private static final String PATTERN_OPTIONAL_STARTDATE = "(?:\\s+(?:from|after|on)\\s+(?<startdate>.+?))?";
    private static final String PATTERN_OPTIONAL_ENDDATE = "(?:\\s+(?:to|by)\\s+(?<enddate>.+?))?";
    private static final String PATTERN_OPTIONAL_TAGS = "(?<tags>(?:\\s+#\\w+)+)?";
    private static final String ARGUMENTS_PATTERN = "^" + PATTERN_MANDATORY_INDEX + PATTERN_OPTIONAL_DESCRIPTION
            + PATTERN_OPTIONAL_STARTDATE + PATTERN_OPTIONAL_ENDDATE + PATTERN_OPTIONAL_TAGS + "$";
    private static final String ARGUMENTS_PATTERN_QUOTED = "^" + PATTERN_MANDATORY_INDEX
            + PATTERN_OPTIONAL_STARTDATE + PATTERN_OPTIONAL_ENDDATE + PATTERN_OPTIONAL_TAGS + "$";

    private static final Pattern ARGUMENTS_FORMAT = Pattern.compile(ARGUMENTS_PATTERN, Pattern.CASE_INSENSITIVE);
    private static final Pattern ARGUMENTS_FORMAT_QUOTED = Pattern.compile(
            ARGUMENTS_PATTERN_QUOTED, Pattern.CASE_INSENSITIVE);

    private static final Logger logger = LogsCenter.getLogger(AddCommandParser.class);
    private static final String logPrefix = "[EditCommandParser]";

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        assert args != null;

        int index;
        String taskName = null;
        String startDateString;
        String endDateString;
        String tagsString;
        String argsToCheck = args;

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();


        try {
            // Check for quoted task names
            if (args.matches(".*'.*'")) {
                int firstIndex = args.indexOf('\'');
                int nextIndex = args.lastIndexOf('\'');
                taskName = args.substring(firstIndex + 1, nextIndex);

                // Remove the task name from the command
                argsToCheck = args.replace("'" + taskName + "'", "").trim();
            }

            logger.info(String.format("%s argsToCheck: '%s'", logPrefix, argsToCheck));

            // Extract the tokens from the argument string.
            Matcher matcher;
            if (taskName == null) {
                matcher = ARGUMENTS_FORMAT.matcher(argsToCheck);
            } else {
                matcher = ARGUMENTS_FORMAT_QUOTED.matcher(argsToCheck);
            }
            if (!matcher.matches()) {
                throw new NoSuchElementException();
            }

            // Extract the matcher groups
            if (taskName == null) {
                taskName = Optional.ofNullable(matcher.group("description")).orElse("").trim();
            }
            index = ParserUtil.parseIndex(matcher.group("index")).get();
            startDateString = Optional.ofNullable(matcher.group("startdate")).orElse("");
            endDateString = Optional.ofNullable(matcher.group("enddate")).orElse("");
            tagsString = Optional.ofNullable(matcher.group("tags")).orElse("").trim();

            // Remove the quotes if available
            if (!taskName.isEmpty()) {
                taskName = taskName.charAt(0) == '\'' ? taskName.substring(1) : taskName;
                int lastIndex = taskName.length() - 1;
                taskName = taskName.charAt(lastIndex) == '\'' ? taskName.substring(0, lastIndex) : taskName;
            }

            // Log tokens for debugging.
            logger.info(String.format("%s taskName: '%s', startDateString: '%s', endDateString: '%s', tags: '%s'",
                    logPrefix, taskName, startDateString, endDateString, tagsString));

            // Set the EditTaskDescriptor change values.
            if (!taskName.isEmpty()) {
                editTaskDescriptor.setName(ParserUtil.parseName(taskName));
            }
            editTaskDescriptor.setStartTime(ParserUtil.parseStartTime(startDateString));
            editTaskDescriptor.setEndTime(ParserUtil.parseEndTime(endDateString));
            editTaskDescriptor.setTags(ParserUtil.parseTagsForEdit(ParserUtil.parseTagStringToSet(tagsString)));

        } catch (NoSuchElementException nsee) {
            // TODO: This needs to be changed to the default case of search.
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        // Add the undo entry after successfully parsing an EditCommand.
        UndoManager.pushUndoCommand(EditCommand.DEFACTO_COMMAND);

        return new EditCommand(index, editTaskDescriptor);
    }

    /* (non-Javadoc)
     * @see seedu.task.logic.parser.AbstractParser#isAcceptedCommand(java.lang.String)
     */
    @Override
    public boolean isAcceptedCommand(String command) {
        return EditCommand.isCommandWord(command);
    }

}
