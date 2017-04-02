//@@author A0139410N
package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;

//@@author A0146789H
/**
 * Finds and lists all tasks in task manager whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String[] COMMAND_WORDS = new String[] {"find", "search"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    public static final String MESSAGE_USAGE = DEFACTO_COMMAND + ": Finds all tasks whose names or tags contain "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + DEFACTO_COMMAND + " tutorial lab assignment";

    private Set<String> keywords;
    private final Set<Tag> tagKeywords = new HashSet<>();

    /**
     * Constructs a command with the relevant command words.
     */
    private FindCommand() {
        super(COMMAND_WORDS);
    }

    //@@author
    public FindCommand(Set<String> keywords) {
        this();
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

    //@@author A0146789H
    public static boolean isCommandWord(String command) {
        assert FindCommand.COMMAND_WORDS != null;

        return isCommandWord(FindCommand.COMMAND_WORDS, command);
    }
}
