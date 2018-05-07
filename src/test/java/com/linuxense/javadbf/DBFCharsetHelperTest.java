/*

(C) Copyright 2017 Alberto Fernández <infjaf@gmail.com>

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3.0 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library.  If not, see <http://www.gnu.org/licenses/>.

*/
package com.linuxense.javadbf;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.SortedMap;

import org.junit.Assert;
import org.junit.Ignore;
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
	@Ignore
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
	
	@Test
	public void testRussianAndNordicEncodingsBug35() {
		/* Russian IBM866 and Nordic ISO865 has changed values.
		 * 
		 * Correct values: https://msdn.microsoft.com/en-us/library/8t45x02s(v=vs.80).aspx
		 * 
		 */
		
		// Russian IBM866 0x65
		Charset russian = Charset.forName("IBM866");
		
		Assert.assertEquals(0x65, DBFCharsetHelper.getDBFCodeForCharset(russian));
		Assert.assertEquals(russian, DBFCharsetHelper.getCharsetByByte(0x65));		
		
		// Nordic IBM865 0x66
		
		Charset nordic = Charset.forName("IBM865");
		Assert.assertEquals(0x66, DBFCharsetHelper.getDBFCodeForCharset(nordic));
		Assert.assertEquals(nordic, DBFCharsetHelper.getCharsetByByte(0x66));		
	}
	
	
	@Test
	public void testEncodings() throws Exception {
		
		Assert.assertEquals(0x65, DBFCharsetHelper.getDBFCodeForCharset(Charset.forName("IBM866")));
		Assert.assertEquals(0x65, DBFCharsetHelper.getDBFCodeForCharset(Charset.forName("CP866")));
		
		Assert.assertEquals(0xc9, DBFCharsetHelper.getDBFCodeForCharset(Charset.forName("windows-1251")));
		Assert.assertEquals(0xc9, DBFCharsetHelper.getDBFCodeForCharset(Charset.forName("CP1251")));
		
		
	}


}
