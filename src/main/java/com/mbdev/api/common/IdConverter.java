package com.mbdev.api.common;

import org.apache.commons.lang3.RandomStringUtils;
import java.security.SecureRandom;

public class IdConverter {
    public static String generateKey(int keyLength) {
        return RandomStringUtils.random(keyLength, 0, 0, true, true, null, new SecureRandom());
    }

}
