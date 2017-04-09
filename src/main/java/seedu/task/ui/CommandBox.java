package seedu.task.ui;
//@@author A0139938L
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.model.UpdateTasksEvent;
import seedu.task.commons.events.ui.NewResultAvailableEvent;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.logic.Logic;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.CommandStack;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";
    private final Logic logic;
    private final CommandStack commandHistory;

    @FXML
    private TextField commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        this.commandHistory = new CommandStack();
        addToPlaceholder(commandBoxPlaceholder);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());
            commandHistory.addCommandHistory(commandTextField.getText());
            // process result of the command
            setStyleToIndicateCommandSuccess();
            commandTextField.setText("");
            if (commandResult != null) {
                logger.info("Result: " + commandResult.feedbackToUser);
                raise(new UpdateTasksEvent());
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            }
        } catch (CommandException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            commandTextField.setText("");
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    //@@author A0138664W
    @FXML
    private void keyListener(KeyEvent key) {
        KeyCode keycode = key.getCode();

        if (keycode == KeyCode.UP || keycode == KeyCode.KP_UP) {
            getPreviousCommand();
            key.consume();
        } else if (keycode == KeyCode.DOWN || keycode == KeyCode.KP_DOWN) {
            getNextCommand();
            key.consume();
        }

    }

    private void getNextCommand() {
        if (commandHistory.commandHistoryIterator.hasPrevious()) {
            String command = commandHistory.commandHistoryIterator.previous();
            commandTextField.replaceText(0, commandTextField.getLength(), command);
            commandTextField.end();
        }

    }

    private void getPreviousCommand() {
        if (commandHistory.commandHistoryIterator.hasNext()) {
            String command = commandHistory.commandHistoryIterator.next();
            commandTextField.replaceText(0, commandTextField.getLength(), command);
            commandTextField.end();
        }
    }
    //@@author

    /**
     * Sets the command box style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        commandTextField.getStyleClass().add(ERROR_STYLE_CLASS);
    }

}
//@@author

