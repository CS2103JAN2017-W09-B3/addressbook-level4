//@@author A0146789H

package seedu.task.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HttpUtilTest {

    // Invalid tests
    @Test
    public void httputil_invalidUrl_failure() {
        boolean result = HttpUtil.uploadFile("http://completelynonexistenturl123.xyz", "storage",
                "./src/test/java/seedu/task/commons/util/HttpUtilTest.java");
        assertFalse(result);
    }

    // Valid tests
    @Test
    public void httputil_validUrl_success() {
        boolean result = HttpUtil.uploadFile("http://cs2107.spro.ink/register/test@example.com/10/disable",
                "storage", "sample/taskmanager.xml");
        assertTrue(result);
    }

    @Test
    public void httputil_disableURL_success() {
        boolean result = HttpUtil.pushDisable("test@example.com");
        assertTrue(result);
    }

    @Test
    public void httputil_pushFile_success() {
        boolean result = HttpUtil.pushSaveFile("test@example.com", 15, "sample/taskmanager.xml");
        assertTrue(result);
    }
}
