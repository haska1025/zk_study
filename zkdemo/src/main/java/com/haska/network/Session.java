package com.haska.network;

import java.io.IOException;

import com.haska.tpbuff.Command;

public interface Session {
    public void sendCommand(Command cmd) throws IOException;
    public boolean connnect(String hostname, int port) throws IOException;
    public void close();
}
