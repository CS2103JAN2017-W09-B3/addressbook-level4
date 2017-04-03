package seedu.task.model;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.transformation.FilteredList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.LoadFromRequestEvent;
import seedu.task.commons.events.model.SaveToRequestEvent;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.events.model.UpdateTasksEvent;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.chat.ChatList;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.CompletionStatus;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskType;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    //@@author A0139938L
    private final ChatList chatList;
    //@@author
    private final UndoManager undoManager = new UndoManager();

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        //@@author A0139938L
        chatList = this.taskManager.getChatList();
        //@@author
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    private void resetData(Optional<ReadOnlyTaskManager> taskManager) {
        ReadOnlyTaskManager readOnly = taskManager.get();
        this.resetData(readOnly);
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    protected void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }
//@@author A0138664W
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        Task deletedTask = new Task(target);
        undoManager.pushUndoTask(deletedTask);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        undoManager.pushUndoTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;
        Task taskBackup = new Task(filteredTasks.get(filteredTaskListIndex));
        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        undoManager.pushUndoTask(taskBackup);
        undoManager.pushEditedTask(new Task(editedTask));
        indicateTaskManagerChanged();
    }

    //@@author A0139410N
    //updates the incompleteType for all events every time there is new result update
    @Subscribe
    private void handleUpdateTasksEvent(UpdateTasksEvent event) {
        taskManager.UpdateTasksStatus();
    }
    //@@author

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    //@@author A0139410N
    @Override
    public void updateFilteredListToShowUnchecked() {
        filteredTasks.setPredicate(isUnchecked);
    }

    @Override
    public void updateFilteredListToShowChecked() {
        filteredTasks.setPredicate(isChecked);
    }

    @Override
    public void updateFilteredListToShowFloating() {
        filteredTasks.setPredicate(isFloating);
    }

    @Override
    public void updateFilteredListToShowDeadline() {
        filteredTasks.setPredicate(isDeadline);
    }

    @Override
    public void updateFilteredListToShowEvent() {
        filteredTasks.setPredicate(isEvent);
    }

    @Override
    public void updateFilteredListToShowUpcoming() {
        filteredTasks.setPredicate(isUpcoming);
    }

    @Override
    public void updateFilteredListToShowOverdue() {
        filteredTasks.setPredicate(isOverdue);
    }

    //@@author
    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    //@@author A0139410N
    @Override
    public void updateFilteredTaskList(Set<String> keywords, Set<Tag> tagKeywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)),
                new PredicateExpression(new TagQualifier(tagKeywords)));
    }

    //@@author
    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //@@author A0139410N
    private void updateFilteredTaskList (Expression nameExpression, Expression tagExpression) {
        filteredTasks.setPredicate(p -> (nameExpression.satisfies(p) || tagExpression.satisfies(p)));
    }

    //========== Inner classes/interfaces used for filtering =================================================

    //@@author A0139410N
    /** Predicate to check if completionStatus is false */
    Predicate<ReadOnlyTask> isUnchecked = new Predicate<ReadOnlyTask> () {
        @Override
        public boolean test(ReadOnlyTask t) {
            return t.getCompletionStatus().getCompletion() == false;
        }
    };

    /** Predicate to check if completionStatus is true */
    Predicate<ReadOnlyTask> isChecked = new Predicate<ReadOnlyTask> () {
        @Override
        public boolean test(ReadOnlyTask t) {
            return t.getCompletionStatus().getCompletion() == true;
        }
    };

    /** Predicate to check if TaskType is floating */
    Predicate<ReadOnlyTask> isFloating = new Predicate<ReadOnlyTask> () {
        @Override
        public boolean test(ReadOnlyTask t) {
            return t.getTaskType().equals(TaskType.SOMEDAY);
        }
    };

    /** Predicate to check if TaskType is deadline */
    Predicate<ReadOnlyTask> isDeadline = new Predicate<ReadOnlyTask> () {
        @Override
        public boolean test(ReadOnlyTask t) {
            return t.getTaskType().equals(TaskType.DEADLINE);
        }
    };

    /** Predicate to check if TaskType is event */
    Predicate<ReadOnlyTask> isEvent = new Predicate<ReadOnlyTask> () {
        @Override
        public boolean test(ReadOnlyTask t) {
            return t.getTaskType().equals(TaskType.EVENT);
        }
    };

    /** Predicate to check if TaskType is upcoming */
    Predicate<ReadOnlyTask> isUpcoming = new Predicate<ReadOnlyTask> () {
        @Override
        public boolean test(ReadOnlyTask t) {
            return t.getCompletionStatus().toString()
                    .equals(CompletionStatus.IncompleteType.UPCOMING.toString());
        }
    };

    /** Predicate to check if TaskType is overdue */
    Predicate<ReadOnlyTask> isOverdue = new Predicate<ReadOnlyTask> () {
        @Override
        public boolean test(ReadOnlyTask t) {
            return t.getCompletionStatus().toString()
                    .equals(CompletionStatus.IncompleteType.OVERDUE.toString());
        }
    };

    //@@author
    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        @Override
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        @Override
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    //@@author A0139410N
    private class TagQualifier implements Qualifier {
        private Set<Tag> tagKeyWords;

        TagQualifier(Set<Tag> tagKeyWords) {
            this.tagKeyWords = tagKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            UniqueTagList taskTags = task.getTags();
            return tagKeyWords.stream()
                    .filter(keyword -> taskTags.contains(keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "tags=" + String.join(", ", tagKeyWords.toString());
        }
    }

    //@@author A0139938L
    @Override
    public ChatList getChatList() {
        return chatList;
    }
//@@author A0138664W
    @Override
    public synchronized void deleteTaskUndo(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTaskUndo(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTaskUndo(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;
        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }

    @Override
    public UndoManager getUndoManager() {
        return undoManager;
    }

    @Override
    public int getTaskID(Task task) {
        return taskManager.getTaskID(task);
    }
//@@author

    //@@author A0139938L
    @Override
    public void changeSaveToLocation(ReadOnlyTaskManager taskManager, String filepath) {
        triggerSaveToRequest(taskManager, filepath);
    }
    /** Raises an event to request change in save to location */
    protected void triggerSaveToRequest(ReadOnlyTaskManager taskManager, String filepath) {
        raise(new SaveToRequestEvent(taskManager, filepath));
    }

    @Override
    public void changeLoadFromLocation(String filepath) {
        triggerLoadFromRequest(filepath);
    }

    private void triggerLoadFromRequest(String filepath) {
        LoadFromRequestEvent event = new LoadFromRequestEvent(filepath);
        raise(event);
        this.resetData(event.getTaskManager());
    }


    //@@author

}
