//@@author A0138664W
package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.core.Messages;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.Model;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.CompletionStatus;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.StartTime;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

public class DeleteTagCommand extends Command {

    //@@author A0146789H
    public static final String[] COMMAND_WORDS = new String[] {"deltag"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    //@@author A0138664W
    public static final String MESSAGE_USAGE = DEFACTO_COMMAND + ": Delete existing tags in existing task.\n"
            + "Parameters: INDEX [#tag]\n"
            + "Example: " + DEFACTO_COMMAND
            + " 1 #CS2103 #uni";
    public static final String ADD_TAG_SUCCESS = "Deleted tags from task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_DUPLICATE_TAGS = "This tag already exists in the task.";
    public static final String MESSAGE_TASK_WITHOUT_TAGS = "This task has no tags.";


    private int filteredTaskListIndex;
    private Set<String> tags;

    //@@author A0146789H
    public DeleteTagCommand() {
        super(COMMAND_WORDS);
    }

    //@@author A0138664W
    public DeleteTagCommand(int filteredTaskListIndex, Set<String> tags) throws IllegalValueException {
        this();
        assert tags != null;
        assert filteredTaskListIndex > 0;
        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
        this.tags = tags;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task taskEdited = (Task)taskToEdit;
        Task editedTask = createTaskAfterDeletedTags(taskToEdit, tags);

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getTaskID(taskEdited)));
        return new CommandResult(String.format(ADD_TAG_SUCCESS, editedTask));
    }

    public CommandResult executeUndo(Task previousTask, Task editedTask, Model model) throws CommandException {
        int taskID = model.getTaskID(editedTask);
        try {
            model.updateTaskUndo(taskID, previousTask);
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(UndoCommand.MESSAGE_UNDO_SUCCESS_EDIT, previousTask));
    }

    public CommandResult executeRedo(Task previousTask, Task editedTask, Model model) throws CommandException {
        int taskID = model.getTaskID(editedTask);
        try {
            model.updateTaskUndo(taskID, previousTask);
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(RedoCommand.MESSAGE_REDO_SUCCESS_EDIT, previousTask));
    }

    private static Task createTaskAfterDeletedTags(ReadOnlyTask taskToEdit, Set<String> tags) throws CommandException {
        assert taskToEdit != null;

        //current won't enter cause getTags will not equal null
        if (taskToEdit.getTags() == null) {
            throw new CommandException(MESSAGE_TASK_WITHOUT_TAGS);
        }

        Name name = taskToEdit.getName();
        StartTime startTime = taskToEdit.getStartTime();
        EndTime endTime = taskToEdit.getEndTime();
        CompletionStatus updatedCompletionStatus = taskToEdit.getCompletionStatus();

        UniqueTagList tagListToCheck = taskToEdit.getTags();
        Set<Tag> tagSet = new HashSet<>();
        for (Tag t : tagListToCheck) {
            tagSet.add(t);
        }

        for (Tag t : tagListToCheck) {
            for (String s : tags) {
                if (s.equals(t.getTagName())) {
                    tagSet.remove(t);
                }
            }
        }

        UniqueTagList newTagList = new UniqueTagList(tagSet);

        return new Task(name, startTime, endTime, updatedCompletionStatus, newTagList);
    }

    //@@author A0146789H
    public static boolean isCommandWord(String command) {
        assert CheckCommand.COMMAND_WORDS != null;

        return isCommandWord(CheckCommand.COMMAND_WORDS, command);
    }
}
