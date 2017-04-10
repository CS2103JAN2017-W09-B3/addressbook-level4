//@@author A0146789H

package seedu.task.commons.util;

import java.io.File;
import java.util.logging.Logger;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import seedu.task.commons.core.LogsCenter;

/**
 * Provides utility functions to send HTTP requests.
 *
 * @author amon
 *
 */
public class HttpUtil {

    // Declare the constants for the API
    public static final String API_HOSTNAME = "cs2107.spro.ink";
    public static final int API_PORT = 80;
    public static final String API_ENDPOINT = "/register/";

    private static final Logger logger = LogsCenter.getLogger(HttpUtil.class);
    private static final String logPrefix = "[HttpUtil]";

    /**
     * Uploads a file to the URL
     *
     * @param url
     * @param fileName
     * @param filePath
     * @return true if the file upload was successful.
     */
    public static boolean uploadFile(String url, String fileName, String filePath) {
        try {
            logger.info(String.format("%s: url: %s, fileName: %s, filePath: %s", logPrefix, url, fileName, filePath));
            HttpResponse<String> request = Unirest.post(url).field(fileName, new File(filePath)).asString();
            int status = request.getStatus();
            logger.info(String.format("%s: Return status code: %d", logPrefix, status));
            if (status != 200) {
                return false;
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Uploads the specified save file to the API endpoint.
     *
     * @param email
     * @param filePath
     * @return
     */
    public static boolean pushSaveFile(String email, int time, String filePath) {
        String url = String.format("http://%s:%d%s%s/%d/enable", API_HOSTNAME, API_PORT, API_ENDPOINT, email, time);
        return uploadFile(url, "storage", filePath);
    }

    public static boolean pushDisable(String email) {
        String url = String.format("http://%s:%d%s%s/0/disable", API_HOSTNAME, API_PORT, API_ENDPOINT, email);
        try {
            HttpResponse<String> request = Unirest.post(url).asString();
            int status = request.getStatus();
            if (status != 200) {
                return false;
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
