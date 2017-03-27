package seedu.task.model;

import java.util.Stack;

import seedu.task.model.task.Task;

public class UndoManager {

    private Stack<Task> undoTaskStack;
    private Stack<Task> redoTaskStack;
    private Stack<Task> editedTaskStack;
    private static CommandStack undoCommand;
    private static CommandStack redoCommand;

    public UndoManager() {
        undoTaskStack = new Stack<Task>();
        redoTaskStack = new Stack<Task>();
        editedTaskStack = new Stack<Task>();
        undoCommand = new CommandStack();
        redoCommand = new CommandStack();
    }

    public void pushUndoTask(Task task) {
        undoTaskStack.push(task);
    }

    public void pushRedoTask(Task task) {
        redoTaskStack.push(task);
    }

    public static void pushUndoCommand(String command) {
        undoCommand.pushCommand(command);
    }

    public void pushRedoCommand(String command) {
        redoCommand.pushCommand(command);
    }

    public void pushEditedTask(Task task) {
        editedTaskStack.push(task);
    }

    public Task popUndoTask () {
        Task task = undoTaskStack.pop();
        redoTaskStack.push(task);
        return task;
    }

    public Task popRedoTask() {
        Task task = redoTaskStack.pop();
        undoTaskStack.push(task);
        return task;
    }

    public String popUndoCommand() {
        String command = undoCommand.popCommand();
        redoCommand.pushCommand(command);
        return command;
    }

    public String popRedoCommand() {
        String command = redoCommand.popCommand();
        undoCommand.pushCommand(command);
        return command;
    }

    public Task popEditedTask() {
        return editedTaskStack.pop();
    }

    public boolean getUndoStackStatus() {
        return undoTaskStack.empty();
    }

    public boolean getRedoStackStatus() {
        return redoTaskStack.empty();
    }

    public boolean getCommandHistoryStatus() {
        return undoCommand.getCommandHistoryStatus();
    }

}
