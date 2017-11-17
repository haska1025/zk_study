/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haska.tpbuff;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public class BinaryInputArchive implements InputArchive {
    static public final String UNREASONBLE_LENGTH= "Unreasonable length = ";
    private DataInput in;
    
    static public BinaryInputArchive getArchive(InputStream strm) {
        return new BinaryInputArchive(new DataInputStream(strm));
    }
    
    /** Creates a new instance of BinaryInputArchive */
    public BinaryInputArchive(DataInput in) {
        this.in = in;
    }
    
    public byte readByte() throws IOException {
        return in.readByte();
    }
    
    public boolean readBool() throws IOException {
        return in.readBoolean();
    }
    public char readChar() throws IOException{
    	return in.readChar();
    }
    public short readShort() throws IOException{
    	return in.readShort();
    }
    public int readInt() throws IOException {
        return in.readInt();
    }
    
    public long readLong() throws IOException {
        return in.readLong();
    }
    
    public float readFloat() throws IOException {
        return in.readFloat();
    }
    
    public double readDouble() throws IOException {
        return in.readDouble();
    }
    
    public String readString() throws IOException {
    	int len = in.readInt();
    	if (len == -1) return null;
        checkLength(len);
    	byte b[] = new byte[len];
    	in.readFully(b);
    	return new String(b, "UTF8");
    }
    
    static public final int maxBuffer = Integer.getInteger("jute.maxbuffer", 0xfffff);

    public byte[] readBuffer() throws IOException {
        int len = readInt();
        if (len == -1) return null;
        checkLength(len);
        byte[] arr = new byte[len];
        in.readFully(arr);
        return arr;
    }    

    // Since this is a rough sanity check, add some padding to maxBuffer to
    // make up for extra fields, etc. (otherwise e.g. clients may be able to
    // write buffers larger than we can read from disk!)
    private void checkLength(int len) throws IOException {
        if (len < 0 || len > maxBuffer + 1024) {
            throw new IOException(UNREASONBLE_LENGTH + len);
        }
    }
}
