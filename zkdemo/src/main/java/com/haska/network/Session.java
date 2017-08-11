package com.haska.network;

import java.io.IOException;

public interface Session {
    public void sendCommand(Command cmd) throws IOException;
    public boolean connnect(String hostname, int port) throws IOException;
    public void close();
}
