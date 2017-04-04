/**
 *
 */
package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;

//@@author A0146789H
/**
 * @author amon
 *
 * Abstract class for all parsers to allow for easier registration of parsers.
 */
public abstract class AbstractParser {
    /** Parses the given string arguments into a command if valid.
     *
     * @param args
     * @return
     */
    public abstract Command parse(String args);

    /** Checks if the provided command word is a valid alias of the command.
     *
     * @param command
     * @return true if the word is a valid alias, false otherwise.
     */
    public abstract boolean isAcceptedCommand(String command);
}
