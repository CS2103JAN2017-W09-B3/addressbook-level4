package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.SaveToCommand;

//@@author A0139938L

public class SaveToCommandParser extends AbstractParser {
    /**
     * Parses the given {@code String} of arguments in the context of the SaveToCommand
     * and returns an SaveToCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        return new SaveToCommand(args);
    }

    //@@author A0146789H
    @Override
    public boolean isAcceptedCommand(String command) {
        return SaveToCommand.isCommandWord(command);
    }
}
//@@author
