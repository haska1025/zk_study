/// Generated by tpp compiler. Don't edit!
package com.haska.tpbuff.generated;

import java.util.List;
import java.util.ArrayList;
import com.haska.tpbuff.*;
import java.io.IOException;
public class Conf extends CommandHeader implements Command {
  private byte i8;

  // getter/setter
  public byte getI8(){return i8;}
  public void setI8(byte param){i8=param;}


  // constructor
  public Conf(){
  }
  public void serialize(OutputArchive oa)throws IOException{
    oa.writeByte(i8);
  }
  public void deserialize(InputArchive ia)throws IOException{
    i8 = ia.readByte();
  }
  public void dump(){
    System.out.printf("i8 = %x", i8).println();
  }
}

