//@@author A0138664W
package seedu.task.model;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Stack;

public class CommandStack {
    private Stack<String> commandStack = new Stack<String>();
    private LinkedList<String> commandHistory = new LinkedList<String>();
    public ListIterator<String> commandHistoryIterator;

    public void pushCommand(String command) {
        commandStack.push(command);
    }

    public void addCommandHistory (String command) {
        commandHistory.addFirst(command);
        refreshIterator();
    }

    private void refreshIterator() {
        commandHistoryIterator = commandHistory.listIterator();
    }

    public String popCommand() {
        return commandStack.pop();
    }

    public boolean getCommandHistoryStatus() {
        return commandStack.empty();
    }
}
