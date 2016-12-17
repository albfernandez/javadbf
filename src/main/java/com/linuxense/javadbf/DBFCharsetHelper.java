package com.linuxense.javadbf;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public final class DBFCharsetHelper {

	private DBFCharsetHelper() {
		throw new AssertionError("No instances allowed");
	}

	/**
	 * See http://support.microsoft.com/kb/129631/en-us See also:
	 * https://msdn.microsoft.com/en-us/library/aa975386(v=vs.71).aspx byte 29
	 * (0x1D) is code page mark See
	 * https://msdn.microsoft.com/en-us/library/aa975345(v=vs.71).aspx
	 * 
	 * @param b
	 * @return
	 */
	public static Charset getCharsetByByte(int b) {
		switch (b) {
		

		case 0x01:
			// U.S. MS-DOS
			return forName("IBM437"); 
		case 0x02:
			 // International MS-DOS
			return forName("IBM850");
		case 0x03:
			// Windows ANSI
			return forName("windows-1252"); 
		case 0x04:
			// Standard Macintosh
			return forName("MacRoman"); 			
		case 0x57:
			// ESRI shape files use code 0x57 to indicate that
			// data is written in ANSI (whatever that means).
			// http://www.esricanada.com/english/support/faqs/arcview/avfaq21.asp
			return forName("windows-1252");
		case 0x59:
			return forName("windows-1252");
		case 0x64:
			// Eastern European MS-DOS
			return forName("IBM852"); 
		case 0x65:
			 // Russian MS-DOS
			return forName("IBM865");
		case 0x66:
			// Nordic MS-DOS
			return forName("IBM866"); 
		case 0x67:
			// Icelandic MS-DOS
			return forName("IBM861"); 
			// case 0x68: return Charset.forName("895"); // Kamenicky (Czech)
			// MS-DOS
			// case 0x69: return Charset.forName("620"); // Mazovia (Polish)
			// MS-DOS
		case 0x6A:
			// Greek MS-DOS (437G)
			return forName("x-IBM737"); 
		case 0x6B:
			// Turkish MS-DOS
			return forName("IBM857"); 
		case 0x78:
			// Chinese (Hong Kong SAR, Taiwan) Windows
			return forName("windows-950"); 
		case 0x79:
			// Korean Windows
			return Charset.forName("windows-949"); 
		case 0x7A:
			// Chinese (PRC, Singapore) Windows
			return forName("GBK"); 
		case 0x7B:
			// Japanese Windows
			return forName("windows-932"); 
		case 0x7C:
			 // Thai Windows
			return forName("windows-874");
		case 0x7D:
			// Hebrew Windows
			return forName("windows-1255"); 
		case 0x7E:
			// Arabic Windows
			return forName("windows-1256"); 
		case 0x96:
			// Russian Macintosh
			return forName("x-MacCyrillic"); 
		case 0x97:
			 // Macintosh EE
			return forName("x-MacCentralEurope");
		case 0x98:
			// Greek Macintosh
			return forName("x-MacGreek"); 
		case 0xC8:
			// Eastern European Windows
			return forName("windows-1250"); 
		case 0xC9:
			// Russian Windows
			return forName("windows-1251"); 
		case 0xCA:
			// Turkish Windows
			return forName("windows-1254"); 
		case 0xCB:
			// Greek Windows
			return forName("windows-1253"); 
		}
		return null;
	}

	private static Charset forName(String name) {
		try {
			return Charset.forName(name);
		} catch (UnsupportedCharsetException e) {
			return null;
		}
	}
	
	public static int getDBFCodeForCharset(Charset charset) {
		if (charset == null) {
			return 0;
		}
		if ("windows-1252".equalsIgnoreCase(charset.toString())){
			return 0x03;
		}
		if ("iso-8859-1".equalsIgnoreCase(charset.toString())){
			return 0x03;
		}
		
		// Unsupported charsets returns 0
		return 0;
	}

}
