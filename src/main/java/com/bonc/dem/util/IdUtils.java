package com.bonc.dem.util;

import java.util.UUID;

public class IdUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
