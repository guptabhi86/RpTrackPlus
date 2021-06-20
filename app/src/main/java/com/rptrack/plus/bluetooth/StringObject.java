package com.rptrack.plus.bluetooth;

public class StringObject {
    String message=null;
    int messageType;

    public StringObject(String message, int messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public int getMessageType() {
        return messageType;
    }
}
