package com.linuxense.javadbf;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

public class DBFCharsetHelperTest {

	public DBFCharsetHelperTest() {
		super();
	}

	@Test
	public void allCharsetsCanBeCreated() {
		for (int i = 0; i < 255; i++) {
			DBFCharsetHelper.getCharsetByByte(i);
		}
	}
	
	@Test
	public void t1 () {
		Assert.assertEquals(0x03, DBFCharsetHelper.getDBFCodeForCharset(StandardCharsets.ISO_8859_1));
		Assert.assertEquals(0x03, DBFCharsetHelper.getDBFCodeForCharset(Charset.forName("windows-1252")));
	}

}
