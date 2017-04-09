//@@author A0146789H

package seedu.task.model;

import java.util.Objects;

import seedu.task.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    public GuiSettings guiSettings;
    public String reminderEmail;
    public int reminderTime;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
        this.reminderEmail = ""; // Empty email signifies no reminders
        this.reminderTime = 5; // Number of minutes before to remind
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public void setReminderEmail(String email) {
        this.reminderEmail = email;
    }

    public void setReminderTime(int time) {
        this.reminderTime = time;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        if (!reminderEmail.equals(o.reminderEmail)) {
            return false;
        }

        if (reminderTime != o.reminderTime) {
            return false;
        }

        return Objects.equals(guiSettings, o.guiSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings);
    }

    @Override
    public String toString() {
        String result = String.format("reminderEmail: %s\nreminderTime: %d\n%s", reminderEmail,
                reminderTime, guiSettings.toString());
        return result;
    }

}
