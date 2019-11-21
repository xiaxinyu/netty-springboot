package com.netty.delimiter.frame;

public class Constant {
    public static final String DELIMITER = "$_";
    public static final byte[] DELIMITER_BYTE = DELIMITER.getBytes();
    public static final int MAX_FRAME_LENGTH = 2048;

    private final static String ECHO_REQ = "Hi, Summer. Welcome to Netty.";
    private final static String MODEL = "Welcome !!! ";

    public static String getShortText() {
        StringBuilder message = new StringBuilder(ECHO_REQ);
        for (int j = 0; j < 10; j++) {
            message.append(MODEL);
        }
        message.append(Constant.DELIMITER);
        return message.toString();
    }

    public static String getLongText() {
        StringBuilder message = new StringBuilder(ECHO_REQ);
        for (int j = 0; j < 100; j++) {
            message.append(MODEL);
        }
        message.append(Constant.DELIMITER);
        return message.toString();
    }
}
