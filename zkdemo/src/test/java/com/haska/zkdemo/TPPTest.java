package com.haska.zkdemo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.haska.tpbuff.BinaryInputArchive;
import com.haska.tpbuff.BinaryOutputArchive;
import com.haska.tpbuff.ByteBufferInputStream;
import com.haska.tpbuff.generated.CloseConf;
import com.haska.tpbuff.generated.Conf;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TPPTest extends TestCase {
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TPPTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TPPTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	CloseConf cc = new CloseConf();
        Conf c = new Conf();
        
        c.setI8((byte)0x78);
        cc.setCc_ref(c);

        List<Conf> carray = new ArrayList<Conf>();
        Conf c1 = new Conf();
        c1.setI8((byte)0x79);
        
        carray.add(c1);
        Conf c2 = new Conf();
        c2.setI8((byte)0x7A);
        carray.add(c2);
        
        Conf c3 = new Conf();
        c3.setI8((byte)0x7B);
        carray.add(c3);
        
        Conf c4 = new Conf();
        c4.setI8((byte)0x7C);
        carray.add(c4);
        cc.setR_cc_ref(carray);

        cc.setB1((byte)0x1);
        
        byte[] barray = new byte[3];
        barray[0] = 0x11;
        barray[1] = 0x11;
        barray[2] = 0x12;
        cc.setRb(barray);

        
        cc.setI8((byte)0x2);
        byte[] barray2 = new byte[3];
        barray2[0] = 0x21;
        barray2[1] = 0x22;
        barray2[2] = 0x23;
        cc.setRi8(barray2);

        cc.setUi8((byte)0x3);
        byte[] barray3 = new byte[3];
        barray3[0] = 0x31;
        barray3[1] = 0x32;
        barray3[2] = 0x33;
        cc.setRui8(barray2);

        cc.setI16((short)0x4);
        short[] sarray = new short[3];
        sarray[0] = 0x41;
        sarray[1] = 0x42;
        sarray[2] = 0x43;
        cc.setRi16(sarray);

        cc.setUi16(0x5);
        int[] iarray = new int[3];
        iarray[0] = 0x51;
        iarray[1] = 0x52;
        iarray[2] = 0x53;
        cc.setRui16(iarray);

        int[] iarray2 = new int[3];
        cc.setI32(0x6);
        iarray2[0] = 0x61;
        iarray2[1] = 0x62;
        iarray2[2] = 0x63;
        cc.setRi32(iarray2);
        
        cc.setUi32(0x7);
        long[] larray = new long[3];
        larray[0] = 0x71;
        larray[1] = 0x72;
        larray[2] = 0x73;
        cc.setRui32(larray);

        cc.setI64(0x100);
        long[] larray2 = new long[4];
        larray2[0] = 0x1001;
        larray2[1] = 0x1002;
        larray2[2] = 0x1003;
        larray2[3] = 0x1004;
        cc.setRi64(larray2);

        cc.setUi64(0x200);
        long[] larray3 = new long[5];
        larray3[0] = 0x2001;
        larray3[1] = 0x2002;
        larray3[2] = 0x2003;
        larray3[3] = 0x2004;
        larray3[4] = 0x2005;
        cc.setRui64(larray3);

        cc.setD(7.0);
        double[] darray = new double[1];
        darray[0] = 8.0;
        cc.setRd(darray);

        cc.setF(10.0f);
        float[] farray = new float[1];
        farray[0] = 11.0f;
        cc.setRf(farray);
        
        cc.setL(0x400);
        long[] larray4 = new long[6];
        larray4[0] = 0x4001;
        larray4[1] = 0x4002;
        larray4[2] = 0x4003;
        larray4[3] = 0x4004;
        larray4[4] = 0x4005;
        larray4[5] = 0x4006;
        cc.setRl(larray4);
        
        cc.setI(0x500);
        cc.setRi(new int[]{0x5001});
        
        cc.setS((short)600);
        cc.setRs(new short[]{601,602});
        
        cc.setC('A');
        cc.setRc(new char[]{'a','b','c'});

        cc.setStr("Hello my tpp compiler!!!!");
        String[] strarray = new String[2];
        strarray[0] = new String("Tpp1 Hah");
        strarray[1] = new String("Tpp2 Hah");
        cc.setRstr(strarray);

        cc.dump();
        CloseConf de_cc = new CloseConf();
        try{
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			BinaryOutputArchive ba = BinaryOutputArchive.getArchive(out);
			cc.serialize(ba);

			
			BinaryInputArchive dbin = BinaryInputArchive
					.getArchive(new ByteBufferInputStream(ByteBuffer.wrap(out.toByteArray())));
			de_cc.deserialize(dbin);
			de_cc.dump();
		} catch (IOException e) {
            System.out.format("%s,%d", "hello", 20).println();;
		}
        
        byte b = 20;
        System.out.printf("%s,%d,%s,0x%x,%d,%s", "hello", 20, "end",b, 1000l,true);
    }
}
