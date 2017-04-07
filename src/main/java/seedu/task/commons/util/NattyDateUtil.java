package seedu.task.commons.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.task.commons.core.LogsCenter;
import seedu.task.logic.parser.AddCommandParser;

//@@author A0146789H
/**
 * @author amon
 *
 * This class provides utility functions to flexibly parse Dates from a string.
 */
/**
 * @author amon
 *
 */
/**
 * @author amon
 *
 */
/**
 * @author amon
 *
 */
public class NattyDateUtil {

    private static final Logger logger = LogsCenter.getLogger(AddCommandParser.class);
    private static final String logPrefix = "[AddCommandParser]";

    /**
     * Parse a string into a single date object using Natty.
     *
     * @param dataString
     * @return a Date object representing the input date
     */
    public static Date parseSingleDate(String dataString) {
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(dataString);

        logger.info(String.format("%s parsing date string '%s'", logPrefix, dataString));

        // Check if there are any results. If not, return null.
        if (groups.isEmpty() || groups.get(0).getDates().isEmpty()) {
            logger.info(String.format("%s failed to parse date string", logPrefix));
            return null;
        }

        DateGroup dg = groups.get(0);
        Date result = dg.getDates().get(0);

        // Log information about the date parsed
        logger.info(String.format("%s date: %s, isDateInferred(): %s, isTimeInferred(): %s",
                    logPrefix, result.toString(), dg.isDateInferred(), dg.isTimeInferred()));

        // If the time was inferred, set the time explicitly to (high) noon.
        Date finalDate = setExplicitTime(result, 12, 0, 0, 0);
        logger.info(String.format("%s newResult: %s", logPrefix, finalDate.toString()));

        return finalDate;
    }

    /**
     * Set the time fields explicitly for a Date object
     *
     * @param original date to modify
     * @param hour to set
     * @param minute to set
     * @param second to set
     * @param millisecond to set
     * @return a new Date object with the supplied values
     */
    public static Date setExplicitTime(Date original, int hour, int minute, int second, int millisecond) {
        // We have to use the Calendar object as a wrapper to avoid deprecated methods
        Calendar modified = Calendar.getInstance();
        modified.setTime(original);
        modified.set(Calendar.HOUR, hour);
        modified.set(Calendar.MINUTE, minute);
        modified.set(Calendar.SECOND, second);
        modified.set(Calendar.MILLISECOND, millisecond);
        Date newResult = modified.getTime();
        return newResult;
    }

}
