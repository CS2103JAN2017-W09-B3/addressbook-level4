package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.IOException;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.logic.commands.EditCommand;
import seedu.task.model.task.Name;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskBuilder;

//@@author A0139938L

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    private TestTask[] expectedTasksList;

    // The list of tasks in the task list panel
    // is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    public EditCommandTest() {
        try {
            expectedTasksList = td.getTypicalTasks();
        } catch (DataConversionException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        commandBox.runCommand("unchecked 1");
        String detailsToEdit = "Bobby from 11/12/12 00:00 to 11/13/12 00:00 #husband";
        int taskManagerIndex = 1;

        TestTask editedTask = new TestTaskBuilder().withName("Bobby").withStartDate("11/12/12 00:00")
                .withEndDate("11/13/12 00:00").withCompletion(false).withTags("husband").build();

//        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "#bestie";
        int taskManagerIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TestTaskBuilder(taskToEdit).withTags("bestie").build();

//        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Presentation");

        String detailsToEdit = "CS2103 Presentation Preparation";
        int filteredTaskListIndex = 1;
        int taskManagerIndex = 1;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TestTaskBuilder(taskToEdit).withName("CS2103 Presentation Preparation").build();

//        assertEditSuccess(filteredTaskListIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        commandBox.runCommand("edit 100 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("edit 1 *&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        //        commandBox.runCommand("edit 1 from abcd");
        //        assertResultMessage(StartTime.MESSAGE_TIME_CONSTRAINTS);
        //
        //        commandBox.runCommand("edit 1 by yahoo!!!");
        //        assertResultMessage(EndTime.MESSAGE_TIME_CONSTRAINTS);
        //
        //        commandBox.runCommand("edit 1 #*&");
        //        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    //TODO: fix after v0.4
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit 3 Attend Sharons birthday from "
                + "1 Apr 6pm to 1 Apr 8pm");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param taskManagerIndex index of task to edit in the task manager.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */

    private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex,
            String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().fullName);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskManagerIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
//@@author

