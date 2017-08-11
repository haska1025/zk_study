package com.haska.network;

public interface SessionCallback {
    public void onConnected();
    public void onRead(Command cmd);
    public void onClose();
}
