package com.haska.network;

import com.haska.tpbuff.Command;

public interface SessionCallback {
    public void onConnected();
    public void onRead(Command cmd);
    public void onClose();
}
