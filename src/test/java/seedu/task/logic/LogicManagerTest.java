package seedu.task.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.commons.events.ui.ShowHelpRequestEvent;
import seedu.task.commons.util.NattyDateUtil;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.AddTagCommand;
import seedu.task.logic.commands.CheckCommand;
import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.DeleteTagCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.ExitCommand;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.logic.commands.ListCheckedCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.ListUncheckedCommand;
import seedu.task.logic.commands.RedoCommand;
import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.Model;
import seedu.task.model.ModelManager;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.TaskManager;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.CompletionStatus;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.StartTime;
import seedu.task.model.task.Task;
import seedu.task.storage.StorageManager;


public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskManager latestSavedAddressBook;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskManagerChangedEvent abce) {
        latestSavedAddressBook = new TaskManager(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Before
    public void setUp() {
        model = new ModelManager();
        String tempAddressBookFile = saveFolder.getRoot().getPath() + "TempAddressBook.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempAddressBookFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedAddressBook = new TaskManager(model.getTaskManager()); // last saved assumed to be up to date
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void tearDown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() {
        String invalidCommand = "       ";
        assertCommandFailure(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command, confirms that a CommandException is not thrown and that the result message is correct.
     * Also confirms that both the 'address book' and the 'last shown list' are as specified.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskManager, List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            ReadOnlyTaskManager expectedAddressBook,
            List<? extends ReadOnlyTask> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedAddressBook, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * Both the 'address book' and the 'last shown list' are verified to be unchanged.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskManager, List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        TaskManager expectedTaskManager = new TaskManager(model.getTaskManager());
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedTaskManager, expectedShownList);
    }

    /**
     * Executes the command, confirms that the result message is correct
     * and that a CommandException is thrown if expected
     * and also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
            ReadOnlyTaskManager expectedAddressBook,
            List<? extends ReadOnlyTask> expectedShownList) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertFalse("CommandException expected but was not thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException e) {
            assertTrue("CommandException not expected but was thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, e.getMessage());
        }

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedAddressBook, model.getTaskManager());
        assertEquals(expectedAddressBook, latestSavedAddressBook);
    }

    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = "uicfhmowqewca";
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new TaskManager(), Collections.emptyList());
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new TaskManager(), Collections.emptyList());
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();

        // setup starting state
        model.addTask(toBeAdded); // person already in internal address book

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded),  AddCommand.MESSAGE_DUPLICATE_TASK);

    }


    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedAB = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare task manager state
        helper.addToModel(model, 2);

        assertCommandSuccess("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }

    //@@author A0139410N
    @Test
    public void execute_listUnchecked_showsUnchecked() throws Exception {
        // prepare expectations, only supposed to show 2 unchecked task with 3 task in total
        TestDataHelper helper = new TestDataHelper();
        Task unchecked1 = helper.generateTaskWithName("not done");
        Task unchecked2 = helper.generateTaskWithName("Also not done");
        Task checked = helper.completedTask();

        List<Task> threeTasks = helper.generateTaskList(unchecked1, unchecked2, checked);
        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        List<Task> expectedList = helper.generateTaskList(unchecked1, unchecked2);

        // prepare address book state comprising of 2 unchecked task and 1 checked
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("list unchecked",
                ListUncheckedCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }

    //@@ author A0139410N
    @Test
    public void execute_listChecked_showsChecked() throws Exception {
     // prepare expectations, only supposed to show 1 checked task with 3 task in total
        TestDataHelper helper = new TestDataHelper();
        Task unchecked1 = helper.generateTaskWithName("not done");
        Task unchecked2 = helper.generateTaskWithName("Also not done");
        Task checked = helper.completedTask();

        List<Task> threeTasks = helper.generateTaskList(unchecked1, unchecked2, checked);
        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        List<Task> expectedList = helper.generateTaskList(checked);

        // prepare address book state comprising of 2 unchecked task and 1 checked
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("list checked",
                ListCheckedCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }

    //@@author
    //@@author A0138664W
    @Test
    public void execute_undo_noPreviousCommandInput() throws Exception {
        assertCommandSuccess("undo", UndoCommand.NOTHING_TO_UNDO, new TaskManager(), Collections.emptyList());
    }

    @Test
    public void execute_redo_noPreviousCommandInput() throws Exception {
        assertCommandSuccess("redo", RedoCommand.NOTHING_TO_REDO, new TaskManager(), Collections.emptyList());
    }

    @Test
    public void execcute_undoredo_add_delete() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

        expectedAB.removeTask(toBeAdded);
        assertCommandSuccess("delete 1",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());


        expectedAB.addTask(toBeAdded);
        assertCommandSuccess("undo",
                String.format(UndoCommand.MESSAGE_UNDO_SUCCESS_ADD, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

        expectedAB.removeTask(toBeAdded);
        assertCommandSuccess("undo",
                String.format(UndoCommand.MESSAGE_UNDO_SUCCESS_DELETE, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

        expectedAB.addTask(toBeAdded);
        assertCommandSuccess("redo",
                String.format(RedoCommand.MESSAGE_REDO_SUCCESS_ADD, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

        expectedAB.removeTask(toBeAdded);
        assertCommandSuccess("redo",
                String.format(RedoCommand.MESSAGE_REDO_SUCCESS_DELETE, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execcute_undoredo_edit() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.toEditTask();
        Task editedTask = helper.editedTask();
        Task toBeEdited = helper.toEditTask();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

        expectedAB.updateTask(0, editedTask);
        assertCommandSuccess("edit 1 This is edited Task #edit",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask),
                expectedAB,
                expectedAB.getTaskList());

        expectedAB.updateTask(0, toBeEdited);
        assertCommandSuccess("undo",
                String.format(UndoCommand.MESSAGE_UNDO_SUCCESS_EDIT, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

        expectedAB.updateTask(0, editedTask);
        assertCommandSuccess("redo",
                String.format(RedoCommand.MESSAGE_REDO_SUCCESS_EDIT, editedTask),
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_undoredo_addtag() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.editedTask();
        Task addedTag = helper.editedTagTask();
        Task undoTag = helper.editedTask();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

        expectedAB.updateTask(0, addedTag);
        assertCommandSuccess("addtag 1 #tagtest #tagtagtest",
                String.format(AddTagCommand.ADD_TAG_SUCCESS, addedTag), expectedAB, expectedAB.getTaskList());

        expectedAB.updateTask(0, undoTag);
        assertCommandSuccess("undo",
                String.format(UndoCommand.MESSAGE_UNDO_SUCCESS_EDIT, undoTag),
                expectedAB,
                expectedAB.getTaskList());

        expectedAB.updateTask(0, addedTag);
        assertCommandSuccess("redo",
                String.format(RedoCommand.MESSAGE_REDO_SUCCESS_EDIT, addedTag),
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_undoredo_deltag() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.editedTagTask();
        Task deletedTag = helper.editedTask();
        Task undoTag = helper.editedTagTask();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

        expectedAB.updateTask(0, deletedTag);
        assertCommandSuccess("deltag 1 #tagtest #tagtagtest",
                String.format(DeleteTagCommand.DEL_TAG_SUCCESS, deletedTag), expectedAB, expectedAB.getTaskList());

        expectedAB.updateTask(0, undoTag);
        assertCommandSuccess("undo",
                String.format(UndoCommand.MESSAGE_UNDO_SUCCESS_EDIT, undoTag),
                expectedAB,
                expectedAB.getTaskList());

        expectedAB.updateTask(0, deletedTag);
        assertCommandSuccess("redo",
                String.format(RedoCommand.MESSAGE_REDO_SUCCESS_EDIT, deletedTag),
                expectedAB,
                expectedAB.getTaskList());
    }
    //still buggy not used yet
    public void execcute_undoredo_checkUncheck() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeChecked = helper.adam();
        Task completed = helper.adamCompleted();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeChecked);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeChecked),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeChecked),
                expectedAB,
                expectedAB.getTaskList());

        expectedAB.updateTask(0, completed);
        assertCommandSuccess("checked 1",
                String.format(CheckCommand.MESSAGE_CHECK_SUCCESS, completed),
                expectedAB,
                expectedAB.getTaskList());
    }
    //@@author

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list
     *                    based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage)
            throws Exception {
        assertCommandFailure(commandWord , expectedMessage); //index missing
        assertCommandFailure(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandFailure(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandFailure(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandFailure(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list
     *                    based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 persons
        model.resetData(new TaskManager());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandFailure(commandWord + " 3", expectedMessage);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2)
                + String.format(SelectCommand.MESSAGE_SELECTED_TASK, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }


    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }


    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task adam() throws Exception {
            Name name = new Name("Adam Brown");
            StartTime startDate = new StartTime(NattyDateUtil.parseSingleDate("12/11/11 0909"));
            EndTime endDate = new EndTime(NattyDateUtil.parseSingleDate("12/11/11 0909"));
            CompletionStatus completion = new CompletionStatus(false);
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, startDate, endDate, completion, tags);
        }
        //@@author A0139410N
        Task completedTask() throws Exception {
            Name name = new Name("I am done");
            StartTime startDate = new StartTime(NattyDateUtil.parseSingleDate("12/11/11 0909"));
            EndTime endDate = new EndTime(NattyDateUtil.parseSingleDate("12/11/11 0909"));
            CompletionStatus completion = new CompletionStatus(true);
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, startDate, endDate, completion, tags);
        }

        //@@author A0138664W
        Task toEditTask() throws Exception {
            Name name = new Name("Before edit task");
            StartTime startDate = new StartTime(NattyDateUtil.parseSingleDate("12/11/11 0909"));
            EndTime endDate = new EndTime(NattyDateUtil.parseSingleDate("12/11/11 0909"));
            CompletionStatus completion = new CompletionStatus(false);
            Tag tag1 = new Tag("beforeEdit");
            UniqueTagList tags = new UniqueTagList(tag1);
            return new Task(name, startDate, endDate, completion, tags);
        }

        Task editedTask() throws Exception {
            Name name = new Name("This is edited Task");
            StartTime startDate = new StartTime(NattyDateUtil.parseSingleDate("12/11/11 0909"));
            EndTime endDate = new EndTime(NattyDateUtil.parseSingleDate("12/11/11 0909"));
            CompletionStatus completion = new CompletionStatus(false);
            Tag tag1 = new Tag("edit");
            UniqueTagList tags = new UniqueTagList(tag1);
            return new Task(name, startDate, endDate, completion, tags);
        }

        Task editedTagTask() throws Exception {
            Name name = new Name("This is edited Task");
            StartTime startDate = new StartTime(NattyDateUtil.parseSingleDate("12/11/11 0909"));
            EndTime endDate = new EndTime(NattyDateUtil.parseSingleDate("12/11/11 0909"));
            CompletionStatus completion = new CompletionStatus(false);
            Tag tag1 = new Tag("edit");
            Tag tag2 = new Tag("tagtest");
            Tag tag3 = new Tag("tagtagtest");
            UniqueTagList tags = new UniqueTagList(tag1, tag2, tag3);
            return new Task(name, startDate, endDate, completion, tags);
        }

        Task adamCompleted() throws Exception {
            Name name = new Name("Adam Brown");
            StartTime startDate = new StartTime(NattyDateUtil.parseSingleDate("12/11/11 0909"));
            EndTime endDate = new EndTime(NattyDateUtil.parseSingleDate("12/11/11 0909"));
            CompletionStatus completion = new CompletionStatus(true);
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, startDate, endDate, completion, tags);
        }

        //@@author A0146789H
        /**
         * Generates a valid person using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the person data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed),
                    new StartTime(NattyDateUtil.parseSingleDate("12/11/11 0909")),
                    new EndTime(NattyDateUtil.parseSingleDate("12/11/11 0909")),
                    new CompletionStatus(false),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
                    );
        }

        /** Generates the correct add command based on the person given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" from ").append(p.getEndTime());
            cmd.append(" to ").append(p.getStartTime());

            UniqueTagList tags = p.getTags();
            for (Tag t: tags) {
                cmd.append(" #").append(t.tagName);
            }

            return cmd.toString();
        }

        //@@author
        /**
         * Generates an TaskManager with auto-generated persons.
         */
        TaskManager generateTaskManager(int numGenerated) throws Exception {
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, numGenerated);
            return taskManager;
        }

        /**
         * Generates an TaskManager based on the list of Persons given.
         */
        TaskManager generateTaskManager(List<Task> tasks) throws Exception {
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, tasks);
            return taskManager;
        }

        /**
         * Adds auto-generated Task objects to the given TaskManager
         * @param taskManager The TaskManager to which the Persons will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception {
            addToTaskManager(taskManager, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given TaskManager
         */
        void addToTaskManager(TaskManager taskManager, List<Task> tasksToAdd) throws Exception {
            for (Task p: tasksToAdd) {
                taskManager.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Persons will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
            for (Task p: tasksToAdd) {
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Persons based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... persons) {
            return Arrays.asList(persons);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new StartTime(NattyDateUtil.parseSingleDate("11/12/12 0000")),
                    new EndTime(NattyDateUtil.parseSingleDate("11/12/12 0000")),
                    new CompletionStatus(false),
                    new UniqueTagList(new Tag("tag"))
                    );
        }
    }
}
