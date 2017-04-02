//@@author A0138664W
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.AddTagCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteTagCommand;
import seedu.task.logic.commands.IncorrectCommand;

public class DeleteTagsParser {
    private static final String PATTERN_MANDATORY_INDEX = "(?<index>[1-9]\\d*)";
    private static final String PATTERN_OPTIONAL_TAGS = "(?<tags>(?:\\s+#\\w+)+)?";
    private static final String ARGUMENTS_PATTERN = "^" + PATTERN_MANDATORY_INDEX + PATTERN_OPTIONAL_TAGS + "$";
    private static final Pattern ARGUMENTS_FORMAT = Pattern.compile(ARGUMENTS_PATTERN, Pattern.CASE_INSENSITIVE);

    public Command parse(String args) {

        assert args != null;

        int index;
        String tagsString;

        Set<String> tagSet = new HashSet<String>();

        try {
            // Extract the tokens from the argument string.
            final Matcher matcher = ARGUMENTS_FORMAT.matcher(args);
            if (!matcher.matches()) {
                throw new NoSuchElementException();
            }

            assert !args.isEmpty();
            index = ParserUtil.parseIndex(matcher.group("index")).get();
            tagsString = Optional.ofNullable(matcher.group("tags")).orElse("").trim();

            // Log tokens for debugging.
//            logger.info(String.format("%s taskName: '%s', startDateString: '%s', endDateString: '%s', tags: '%s'",
//                    logPrefix, taskName, startDateString, endDateString, tagsString));


            // Add each tag to the tag set.
            tagSet = ParserUtil.parseTagStringToSet(tagsString);

            // Log the tags
//            logger.info(String.format("%s tagSet: %s", logPrefix, tagSet.toString()));

            // Add the undo entry after successfully parsing an AddCommand
//            UndoManager.pushUndoCommand(AddCommand.COMMAND_WORD);

            return new DeleteTagCommand(index, tagSet);
        } catch (NoSuchElementException nsee) {
            // TODO: This needs to be changed to the default case of search.
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
