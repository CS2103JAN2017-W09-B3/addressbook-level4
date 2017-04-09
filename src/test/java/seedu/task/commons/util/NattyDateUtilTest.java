package seedu.task.commons.util;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

//@@author A0146789H
public class NattyDateUtilTest {

    /**
     * Checks if the date object passed in passes the expected inferred time object values.
     *
     * @param cal
     * @return true if the Calendar object matches the expected time for an inferred time object
     */
    public boolean nattyutil_inferredTimeTester(Date toTest) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(toTest);
        if (cal.get(Calendar.HOUR_OF_DAY) != 12) {
            return false;
        }
        if (cal.get(Calendar.MINUTE) != 0) {
            return false;
        }
        if (cal.get(Calendar.SECOND) != 0) {
            return false;
        }
        if (cal.get(Calendar.MILLISECOND) != 0) {
            return false;
        }
        return true;
    }

    public boolean nattyutil_relativeTimeTester(Date toTest, int daysOffset) {
        Calendar cal = Calendar.getInstance();
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, daysOffset);
        cal.setTime(toTest);
        if (!nattyutil_inferredTimeTester(toTest)) {
            return false;
        }
        // January is 0
        if (cal.get(Calendar.MONTH) != cal.get(Calendar.MONTH)) {
            return false;
        }
        if (cal.get(Calendar.DAY_OF_MONTH) != cal.get(Calendar.DAY_OF_MONTH)) {
            return false;
        }
        if (cal.get(Calendar.YEAR) != cal.get(Calendar.YEAR)) {
            return false;
        }
        return true;
    }

    /**
     *  Parses a simple date string in the Singaporean time format
     */
    @Test
    public void nattyutil_basicDate() {
        Date date = NattyDateUtil.parseSingleDate("10/03/17");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertTrue(cal.get(Calendar.MONTH) == 2); // January is 0
        assertTrue(cal.get(Calendar.DAY_OF_MONTH) == 10);
        assertTrue(cal.get(Calendar.YEAR) == 2017);
    }

    /**
     *  Parses a relative time (tomorrow)
     */
    @Test
    public void nattyutil_tomorrow() {
        Date date = NattyDateUtil.parseSingleDate("tomorrow");
        assertTrue(nattyutil_relativeTimeTester(date, 1));
    }

    /**
     *  Parses a relative time (tomorrow) in shorthand
     */
    @Test
    public void nattyutil_tomorrowShorthand() {
        Date date = NattyDateUtil.parseSingleDate("tmr");
        assertTrue(nattyutil_relativeTimeTester(date, 1));
    }

    /**
     *  Parses a relative time (tomorrow) in alternative forms
     */
    @Test
    public void nattyutil_tomorrowAlternative() {
        Date date = NattyDateUtil.parseSingleDate("the next day");
        assertTrue(nattyutil_relativeTimeTester(date, 1));
    }

    /**
     *  Parses a relative time (next week)
     */
    @Test
    public void nattyutil_nextWeek() {
        Date date = NattyDateUtil.parseSingleDate("next week");
        assertTrue(nattyutil_relativeTimeTester(date, 7));
    }

    /**
     *  Parses a relative time (next year)
     */
    @Test
    public void nattyutil_nextYear() {
        Date date = NattyDateUtil.parseSingleDate("next year");
        assertTrue(nattyutil_relativeTimeTester(date, 365));
    }
}
