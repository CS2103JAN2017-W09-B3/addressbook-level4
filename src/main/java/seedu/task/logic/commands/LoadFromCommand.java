package seedu.task.logic.commands;

//@@author A0139938L

public class LoadFromCommand extends Command {

    public static final String COMMAND_WORD = "loadfrom";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Defines a new load location for your tasks "
            + "Parameters: FILE_PATH"
            + "Example: " + COMMAND_WORD
            + " ./taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "Load location has been changed.";

    private String filepath = "";

    public LoadFromCommand(String filepath) {
        this.setFilepath(filepath);
    }

    @Override
    public CommandResult execute() {
        model.changeLoadFromLocation(filepath);
        //TODO: Check if save worked before returning the message.
        return new CommandResult(MESSAGE_SUCCESS);
    }
    /**
     * @return the filepath
     */
    public String getFilepath() {
        return filepath;
    }
    /**
     * @param filepath the filepath to set
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

}
