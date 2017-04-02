package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.LoadFromCommand;

//@@author A0146789H

public class LoadFromCommandParser extends AbstractParser {
    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        return new LoadFromCommand(args);
    }

    @Override
    public boolean isAcceptedCommand(String command) {
        return LoadFromCommand.isCommandWord(command);
    }
}
