package com.haska.tpbuff;

import java.io.IOException;

public interface Command {
    public void serialize(OutputArchive archive, String tag)
            throws IOException;
        public void deserialize(InputArchive archive, String tag)
            throws IOException; 
}
