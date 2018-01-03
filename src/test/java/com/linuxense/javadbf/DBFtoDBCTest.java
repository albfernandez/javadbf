package com.linuxense.javadbf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ialek36 on 11/16/17.
 */
public class DBFtoDBCTest {
    @Test
    public void testFileConversion() throws IOException {
        //convert DBC to DBF
    	File originalDbf = new File("src/test/resources/dbc-files/sids.dbf");
        File inputFile = new File("src/test/resources/dbc-files/sids.dbc");
        File outputFile = File.createTempFile("sids-tests", ".dbf");
        DBFtoDBC.convert(inputFile, outputFile);
        //read DBF result
        byte[] converted = getAllBytesOrNull(outputFile);

        //load load expected DBF
        byte[] expected = getAllBytesOrNull(originalDbf);

        // skip first 4 bytes (it contains date and may fluctuate)
        converted = skipFirst(converted, 40);
        expected = skipFirst(expected, 40);

        Assert.assertArrayEquals(expected, converted);
    }

    private byte[] skipFirst(byte[] converted, int from) {
        return Arrays.copyOfRange(converted, from, converted.length);
    }

    private byte[] getAllBytesOrNull(File file) {
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
