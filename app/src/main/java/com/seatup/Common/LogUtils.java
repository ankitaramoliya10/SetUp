package com.seatup.Common;

import android.util.Log;

/**
 * Created by Android2 on 11/24/2017.
 */

public class LogUtils {

    private final String LOG_PREFIX = "";
    private final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private final int MAX_LOG_TAG_LENGTH = 23;
    public boolean LOGGING_ENABLED = true;
    private String TAG = "";

    public LogUtils(Class cls) {
        TAG = makeLogTag(cls);
    }

    public String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }
        return LOG_PREFIX + str;
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public void LOGD(String message) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, message);
            }
        }
    }

    public void LOGD(String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, message, cause);
            }
        }
    }

    public void LOGV(String message) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, message);
            }
        }
    }

    public void LOGV(String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, message, cause);
            }
        }
    }

    public void LOGI(String message) {
        if (LOGGING_ENABLED) {
            Log.i(TAG, message);
        }
    }

    public void LOGI(String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.i(TAG, message, cause);
        }
    }

    public void LOGW(String message) {
        if (LOGGING_ENABLED) {
            Log.w(TAG, message);
        }
    }

    public void LOGW(String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.w(TAG, message, cause);
        }
    }

    public void LOGE(String message) {
        if (LOGGING_ENABLED) {
            Log.e(TAG, message);
        }
    }

    public void LOGE(String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.e(TAG, message, cause);
        }
    }
}
