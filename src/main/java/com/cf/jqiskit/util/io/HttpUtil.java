package com.cf.jqiskit.util.io;

public final class HttpUtil {
    public static boolean isSuccessful(int responseCode) {
        return responseCode < 400 || responseCode >= 600;
    }
}
