package guitests.guihandles;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import guitests.GuiRobot;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.task.TestApp;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the task list.
 */
public class TaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardAnchorPane";

    private static final String TASK_LIST_VIEW_ID = "#task-list-view";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyTask> getSelectedTasks() {
        ListView<ReadOnlyTask> taskList = getListView();
        return taskList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyTask> getListView() {
        return getNode(TASK_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param tasks A list of tasks in the correct order.
     */
    public boolean isListMatching(ReadOnlyTask... tasks) {
        return this.isListMatching(0, tasks);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param tasks A list of tasks in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyTask... tasks) throws IllegalArgumentException {
        if (tasks.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, tasks));
        for (int i = 0; i < tasks.length; i++) {
            final int scrollTo = i + startPosition;
            ListView<ReadOnlyTask> listview = getListView();
            guiRobot.interact(() -> listview.scrollTo(scrollTo));
            guiRobot.sleep(200);
            String result = getTask(startPosition + i).getName().fullName;
            String expected = tasks[i].getName().fullName;
            if (!TestUtil.compareCardAndTask(result,expected)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point = TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyTask... tasks) {
        List<ReadOnlyTask> tasksInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + tasks.length > tasksInList.size()) {
            return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < tasks.length; i++) {
            if (!tasksInList.get(startPosition + i).getName().fullName.equals(tasks[i].getName().fullName)) {
                return false;
            }
        }

        return true;
    }

    public TaskCardHandle navigateToTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> task = getListView().getItems().stream()
                .filter(t -> t.getName().fullName.equals(name))
                .findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToTask(task.get());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public TaskCardHandle navigateToTask(ReadOnlyTask task) {
        int index = getTaskIndex(task);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getTaskCardHandle(task);
    }


    /**
     * Returns the position of the task given, {@code NOT_FOUND} if not found in the list.
     */
    public int getTaskIndex(ReadOnlyTask targetTask) {
        List<ReadOnlyTask> tasksInList = getListView().getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if (tasksInList.get(i).getName().equals(targetTask.getName())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a task from the list by index
     */
    public ReadOnlyTask getTask(int index) {
        return getListView().getItems().get(index);
    }

    public TaskCardHandle getTaskCardHandle(int index) {
        return getTaskCardHandle(new Task(getListView().getItems().get(index)));
    }

    //@@author A0139938L
    public TaskCardHandle getTaskCardHandle(ReadOnlyTask task) {
        Set<Node> nodes = getAllCardNodes();
        Stream<Node> taskCardNodeStream = nodes.stream();
        taskCardNodeStream = taskCardNodeStream.filter(n ->
        new TaskCardHandle(guiRobot, primaryStage, n).isSameTask(task));
        Optional<Node> taskCardNode = taskCardNodeStream.findFirst();
        if (taskCardNode.isPresent()) {
            return new TaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }
    //@@author
    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTasks() {
        ListView<ReadOnlyTask> listview = getListView();
        ObservableList<ReadOnlyTask> oList = listview.getItems();
        int size = oList.size();
        return size;
    }
}
