package seedu.task.logic.commands;

import java.util.List;
import java.util.Set;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;
import seedu.task.model.task.CompletionStatus;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.StartTime;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addtag";
    public static final String ADD_TAG_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    private int filteredTaskListIndex;
    private Set<String> tags;
//    private Set<Tag> tagSet = new HashSet<>();

    public AddTagCommand(int filteredTaskListIndex, Set<String> tags)
    		throws IllegalValueException {
		assert filteredTaskListIndex > 0;
        assert tags != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.tags = tags;
//        for (String tagName : tags) {
//            tagSet.add(new Tag(tagName));
//        }
    }

	@Override
	public CommandResult execute() throws CommandException {
		List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

		if(filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

	    ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);

	    Task editedTask = null;

	    try {
			editedTask = createEditedTask(taskToEdit, tags);
		} catch (DuplicateTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			model.updateTask(filteredTaskListIndex, editedTask);
		} catch (UniqueTaskList.DuplicateTaskException dte) {
			throw new CommandException(MESSAGE_DUPLICATE_TASK);
		}
	    model.updateFilteredListToShowAll();
	    return new CommandResult(String.format(ADD_TAG_SUCCESS));
	}

	private static Task createEditedTask(ReadOnlyTask taskToEdit, Set<String> tags) throws DuplicateTagException, IllegalValueException {
		assert taskToEdit != null;

        Name name = taskToEdit.getName();
        StartTime startTime = taskToEdit.getStartTime();
        EndTime endTime = taskToEdit.getEndTime();
        CompletionStatus updatedCompletionStatus = taskToEdit.getCompletionStatus();

        UniqueTagList tagList = taskToEdit.getTags();

        for (String tagName: tags) {
        	tagList.add(new Tag(tagName));
        }


        return new Task(name, startTime, endTime, updatedCompletionStatus, tagList);
	}

}
