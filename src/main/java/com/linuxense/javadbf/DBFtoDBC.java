package com.linuxense.javadbf;

import java.io.*;
import java.util.Calendar;

/**
 * Created by ialek36 on 11/8/17.
 */
public class DBFtoDBC {
    public static void main(String args[]) {

        System.out.println("Started...");
        System.out.println(Calendar.getInstance().getTime());

        convert(args[0], args[1]);

        System.out.println(Calendar.getInstance().getTime());
        System.out.println("finished...");
    }

    static void convert(String source, String target) {
        DBFReader reader = null;
        FileOutputStream out = null;
        try {

            // create a DBFReader object
            FileInputStream inStream = new FileInputStream(source);
            reader = new DBCDATASUSReader(inStream);

            // Create writer
            out = new FileOutputStream(target);

            DBFExploderInputStream myInputStream = new DBFExploderInputStream(inStream);

            byte[] compressedData = myInputStream.getCompressedByteStream().toByteArray();
            int outputBufferSize = myInputStream.getAdjustedOutputSize(compressedData);

            DataOutputStream outStream = new DataOutputStream(out);
            reader.getHeader().write(outStream);

            DBFExploder.pkexplode(compressedData, DBFExploder.createFileStorage(out), outputBufferSize);

//            outStream.write(DBFBase.END_OF_DATA);
            outStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            DBFUtils.close(reader);
            DBFUtils.close(out);
        }
    }
}