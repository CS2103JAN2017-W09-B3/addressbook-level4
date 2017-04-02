package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;

//@@author A0146789H
/**
 * Selects a task identified using it's last displayed index from the task manager.
 */
public class SelectCommand extends Command {

    private int targetIndex;

    public static final String[] COMMAND_WORDS = new String[] {"select"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    public static final String MESSAGE_USAGE = DEFACTO_COMMAND
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + DEFACTO_COMMAND + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    protected SelectCommand() {
        super(COMMAND_WORDS);
    }

    //@@author
    public SelectCommand(int targetIndex) {
        this();
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex));

    }

    //@@author A0146789H
    /**
     * @return the targetIndex
     */
    public int getTargetIndex() {
        return targetIndex;
    }

}
