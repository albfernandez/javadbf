package com.linuxense.javadbf;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by ialek36 on 11/16/17.
 */
public class DBFtoDBCTest {
    @Test
    public void testFileConversion() {
        //convert DBC to DBF
        String fileName = "src/test/resources/dbc-files/sids";
        DBFtoDBC.convert(fileName + ".dbc",
                    fileName +"-test.dbf");
        //read DBF result
        byte[] converted = getAllBytesOrNull(fileName + "-test.dbf");

        //load load expected DBF
        byte[] expected = getAllBytesOrNull(fileName +".dbf");

        // skip first 4 bytes (it contains date and may fluctuate)
        converted = skipFirst(converted, 40);
        expected = skipFirst(expected, 40);

        Assert.assertArrayEquals(expected, converted);
    }

    private byte[] skipFirst(byte[] converted, int from) {
        return Arrays.copyOfRange(converted, from, converted.length);
    }

    private byte[] getAllBytesOrNull(String fileName) {
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
