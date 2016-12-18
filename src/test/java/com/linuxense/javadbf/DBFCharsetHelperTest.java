package com.linuxense.javadbf;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.SortedMap;

import org.junit.Assert;
import org.junit.Test;

public class DBFCharsetHelperTest {

	public DBFCharsetHelperTest() {
		super();
	}

	@Test
	public void allCharsetsCanBeCreated() {
		// All codes can be checked without throwing exception
		for (int i = 0; i < 255; i++) {
			DBFCharsetHelper.getCharsetByByte(i);
		}
	}
	@Test
	public void zeroCodeReturnsNull(){
		Assert.assertNull(null, DBFCharsetHelper.getCharsetByByte(0));
	}
	
	@Test
	public void allRegisteredCodesReturnsNotNull() {
		
		int[] validCodes = new int[]{
			0x01,
			0x02,
			0x03, 
			0x04,
			0x57,
			0x59, 
			0x64, 
			0x65,
			0x66,
			0x67,				
			//0x68,
			//0x69,
			0x6a,
			0x6b,
			0x78,
			0x79,
			0x7a,
			0x7b,
			0x7c,
			0x7d,
			0x7e,
			0x96,
			0x97,
			0x98,
			0xc8,
			0xc9,
			0xca,
			0xcb			
				
		};
		for (int validCode : validCodes) {
			Assert.assertNotNull(DBFCharsetHelper.getCharsetByByte(validCode));
		}		
		
	}


	
	@Test
	public void t1 () {
		Assert.assertEquals(0x03, DBFCharsetHelper.getDBFCodeForCharset(StandardCharsets.ISO_8859_1));
		Assert.assertEquals(0x03, DBFCharsetHelper.getDBFCodeForCharset(Charset.forName("windows-1252")));
	}
	
	
	@Test
	public void listEncodings() {
		SortedMap<String, Charset> charsets = Charset.availableCharsets();
		
		for (String name: charsets.keySet()) {
			 Charset charset = (Charset) charsets.get(name);
			 System.out.println(charset);
			 for (String alias: charset.aliases()){
				 System.out.println("    " + alias);
			 }			 			 
		}
	    
	}


}
