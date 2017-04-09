//@@author A0139410N
package seedu.task.logic.commands;


/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    //@@author A0146789H
    public static final String[] COMMAND_WORDS = new String[] {"list", "ls"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_USAGE = DEFACTO_COMMAND + ": Lists all tasks with index numbers, "
            + "use checked/unchecked/deadline/event/someday/upcoming/overdue as a keyword to only "
            + "show tasks of that type. Only one keyword is allowed. \n"
            + "Parameters: [" + ListUncheckedCommand.DEFACTO_COMMAND + "]["
            + ListCheckedCommand.DEFACTO_COMMAND + "][" + ListDeadlineCommand.DEFACTO_COMMAND
            + "][" + ListEventCommand.DEFACTO_COMMAND + "][" + ListFloatingCommand.DEFACTO_COMMAND
            + "][" + ListUpcomingCommand.DEFACTO_COMMAND + "][" + ListOverdueCommand.DEFACTO_COMMAND + "]\n"
            + "Example: " + DEFACTO_COMMAND + " " + ListUncheckedCommand.DEFACTO_COMMAND;

    public ListCommand() {
        super(COMMAND_WORDS);
    }

    //@@author A0139410N
    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    //@@author A0146789H
    public static boolean isCommandWord(String command) {
        assert ListCommand.COMMAND_WORDS != null;

        return isCommandWord(ListCommand.COMMAND_WORDS, command);
    }
}
