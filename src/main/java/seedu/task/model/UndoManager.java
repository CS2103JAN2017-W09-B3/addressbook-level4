//@@author A0138664W
package seedu.task.model;

import java.util.Stack;

import seedu.task.model.task.Task;

public class UndoManager {

    private Stack<Task> undoTaskStack;
    private Stack<Task> redoTaskStack;
    private Stack<Task> editedTaskStack;
    private Stack<Task> redoEditedTaskStack;
    private static CommandStack undoCommand;
    private static CommandStack redoCommand;

    public UndoManager() {
        undoTaskStack = new Stack<Task>();
        redoTaskStack = new Stack<Task>();
        editedTaskStack = new Stack<Task>();
        redoEditedTaskStack = new Stack<Task>();
        undoCommand = new CommandStack();
        redoCommand = new CommandStack();
    }

    public void pushUndoTask(Task task) {
        undoTaskStack.push(task);
    }

    public void pushRedoTask(Task task) {
        redoTaskStack.push(task);
    }

    public void pushEditedTask(Task task) {
        editedTaskStack.push(task);
    }

    public void pushRedoEditedTask(Task task) {
        redoEditedTaskStack.push(task);
    }

    public void pushRedoCommand(String command) {
        redoCommand.pushCommand(command);
    }

    public static void pushUndoCommand(String command) {
        undoCommand.pushCommand(command);
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
        Task task = editedTaskStack.pop();
        redoEditedTaskStack.push(task);
        return task;
    }

    public Task popRedoEditedTask() {
        Task task = redoEditedTaskStack.pop();
        editedTaskStack.push(task);
        return task;
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

    public boolean getRedoCommandHistoryStatus() {
        return redoCommand.getCommandHistoryStatus();
    }

}
