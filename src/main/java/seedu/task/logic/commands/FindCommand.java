package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;



/**
 * Finds and lists all tasks in task manager whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names or tags contain "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " tutorial lab assignment";

    private final Set<String> keywords;
    private final Set<Tag> tagKeywords = new HashSet<>();

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
        for (String s: keywords) {
            try {
                tagKeywords.add(new Tag(s));
            } catch (IllegalValueException e) {
                //no need to do anything, simply ignore searching for the current term in taglist,
                //only search for the term in description
                ;
            }
        }
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords, tagKeywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
