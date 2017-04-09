//@@author A0146789H

package seedu.task.commons.util;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class HttpUtilTest {

    @Test
    public void httputil_invalidUrl_failure() {
        boolean result = HttpUtil.uploadFile("http://completelynonexistenturl123.xyz", "storage",
                "./src/test/java/seedu/task/commons/util/HttpUtilTest.java");
        assertFalse(result);
    }
}
