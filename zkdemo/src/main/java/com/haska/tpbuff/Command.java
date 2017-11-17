package com.haska.tpbuff;

import java.io.IOException;

public interface Command {
    public void serialize(OutputArchive archive)
            throws IOException;
        public void deserialize(InputArchive archive)
            throws IOException; 
}
