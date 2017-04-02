package seedu.task.logic.commands;

//@@author A0139938L

public class LoadFromCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Load location has been changed.";
    private String filepath = "";
    //@@author A0146789H

    public static final String[] COMMAND_WORDS = new String[] {"loadfrom"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    public static final String MESSAGE_USAGE = DEFACTO_COMMAND + ": Defines a new load location for your tasks "
            + "Parameters: FILE_PATH"
            + "Example: " + DEFACTO_COMMAND
            + " ./taskmanager.xml";


    private LoadFromCommand() {
        super(COMMAND_WORDS);
    }

    public LoadFromCommand(String filepath){
        this();
        this.setFilepath(filepath);
    }

    //@@author A0139938L
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
