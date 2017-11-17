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

import java.io.IOException;

/**
 * Interface that alll the serializers have to implement.
 *
 */
public interface OutputArchive {
    public void writeByte(byte b) throws IOException;
    public void writeBool(boolean b) throws IOException;
    public void writeChar(char c) throws IOException;
    public void writeShort(int s) throws IOException;
    public void writeInt(int i) throws IOException;
    public void writeUInt(long i) throws IOException;
    public void writeLong(long l) throws IOException;
    public void writeFloat(float f) throws IOException;
    public void writeDouble(double d) throws IOException;
    public void writeString(String s) throws IOException;
    public void writeBuffer(byte buf[]) throws IOException;
}
