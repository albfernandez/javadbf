/*

(C) Copyright 2016-2017 Alberto Fernández <infjaf@gmail.com>

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
import java.nio.charset.UnsupportedCharsetException;

/**
 * Utility to map dbf charsets to java charsets.
 *
 * See http://support.microsoft.com/kb/129631/en-us
 *
 * See also:
 * https://msdn.microsoft.com/en-us/library/aa975386(v=vs.71).aspx byte 29
 *
 *(0x1D) is code page mark See https://msdn.microsoft.com/en-us/library/aa975345(v=vs.71).aspx
 */

public final class DBFCharsetHelper {

	private DBFCharsetHelper() {
		throw new AssertionError("No instances allowed");
	}

	/**
	 * Gets Java charset from DBF code
	 * @param b the code stored in DBF file
	 * @return Java charset, null if unknown.
	 */
	public static Charset getCharsetByByte(int b) {
		switch (b) {

		case 0x01: return forName("IBM437"); // U.S. MS-DOS
		case 0x02:return forName("IBM850"); // International MS-DOS
		case 0x03:return forName("windows-1252");// Windows ANSI
		case 0x04: return forName("MacRoman"); // Standard Macintosh
		case 0x08: return forName("IBM865");// # Danish OEM
		case 0x09: return forName("IBM437");   //   # Dutch OEM
		case 0x0A: return forName("IBM850");   //   # Dutch OEM*
		case 0x0B: return forName("IBM437");   //   # Finnish OEM
		case 0x0D: return forName("IBM437");   //   # French OEM
		case 0x0E: return forName("IBM850");   //   # French OEM*
		case 0x0F: return forName("IBM437");   //   # German OEM
		case 0x10: return forName("IBM850");   //   # German OEM*
		case 0x11: return forName("IBM437");   //   # Italian OEM
		case 0x12: return forName("IBM850");   //   # Italian OEM*
		case 0x13: return forName("IBM932");   //   # Japanese Shift-JIS
		case 0x14: return forName("IBM850");   //   # Spanish OEM*
		case 0x15: return forName("IBM437");   //   # Swedish OEM
		case 0x16: return forName("IBM850");   //   # Swedish OEM*
		case 0x17: return forName("IBM865");   //   # Norwegian OEM
		case 0x18: return forName("IBM437");   //   # Spanish OEM
		case 0x19: return forName("IBM437");   //   # English OEM (Britain)
		case 0x1A: return forName("IBM850");   //   # English OEM (Britain)*
		case 0x1B: return forName("IBM437");   //   # English OEM (U.S.)
		case 0x1C: return forName("IBM863");   //   # French OEM (Canada)
		case 0x1D: return forName("IBM850");   //   # French OEM*
		case 0x1F: return forName("IBM852");   //   # Czech OEM
		case 0x22: return forName("IBM852");   //   # Hungarian OEM
		case 0x23: return forName("IBM852");   //   # Polish OEM
		case 0x24: return forName("IBM860");   //   # Portuguese OEM
		case 0x25: return forName("IBM850");   //   # Portuguese OEM*
		case 0x26: return forName("IBM866");   //   # Russian OEM
		case 0x37: return forName("IBM850");   //   # English OEM (U.S.)*
		case 0x40: return forName("IBM852");   //   # Romanian OEM
		case 0x4D: return forName("IBM936");   //   # Chinese GBK (PRC)
		case 0x4E: return forName("IBM949");   //   # Korean (ANSI/OEM)
		case 0x4F: return forName("IBM950");   //   # Chinese Big5 (Taiwan)
		case 0x50: return forName("IBM874");   //   # Thai (ANSI/OEM)
		case 0x57:
			// ESRI shape files use code 0x57 to indicate that
			// data is written in ANSI (whatever that means).
			// http://www.esricanada.com/english/support/faqs/arcview/avfaq21.asp
			return forName("windows-1252");
		case 0x58: return forName("windows-1252");
		case 0x59: return forName("windows-1252");
		case 0x64: return forName("IBM852"); // Eastern European MS-DOS
		case 0x65: return forName("IBM866"); // Russian MS-DOS
		case 0x66: return forName("IBM865"); // Nordic MS-DOS
		case 0x67: return forName("IBM861"); // Icelandic MS-DOS
	    case 0x68: return forName("IBM895"); // Kamenicky (Czech) MS-DOS
	    case 0x69: return forName("IBM620"); // Mazovia (Polish) MS-DOS
		case 0x6A: return forName("x-IBM737"); // Greek MS-DOS (437G)
		case 0x6B: return forName("IBM857"); // Turkish MS-DOS
		case 0x6C: return forName("IBM863");   //   # French-Canadian MS-DOS
		case 0x78: return forName("windows-950"); // Chinese (Hong Kong SAR, Taiwan) Windows
		case 0x79: return Charset.forName("windows-949"); // Korean Windows
		case 0x7A: return forName("GBK"); // Chinese (PRC, Singapore) Windows
		case 0x7B: return forName("windows-932"); // Japanese Windows
		case 0x7C: return forName("windows-874"); // Thai Windows
		case 0x7D: return forName("windows-1255"); // Hebrew Windows
		case 0x7E: return forName("windows-1256"); // Arabic Windows
		case 0x86: return forName("IBM737");   //   # Greek OEM
		case 0x87: return forName("IBM852");   //   # Slovenian OEM
		case 0x88: return forName("IBM857");   //   # Turkish OEM
		case 0x96: return forName("x-MacCyrillic"); // Russian Macintosh
		case 0x97: return forName("x-MacCentralEurope"); // Macintosh EE
		case 0x98: return forName("x-MacGreek"); // Greek Macintosh
		case 0xC8: return forName("windows-1250"); // Eastern European Windows
		case 0xC9: return forName("windows-1251"); // Russian Windows
		case 0xCA: return forName("windows-1254"); // Turkish Windows
		case 0xCB: return forName("windows-1253"); // Greek Windows
		case 0xCC: return forName("IBM1257");  //   # Baltic Windows
		default: return null;
		}
	}
	
	private static Charset forName(String name) {
		try {
			return Charset.forName(name);
		} catch (UnsupportedCharsetException e) {
			return null;
		}
	}

	/**
	 * gets the DBF code for a given Java charset
	 * @param charset the Java charset
	 * @return the DBF code, 0 if unknown
	 */
	public static int getDBFCodeForCharset(Charset charset) {
		if (charset == null) {
			return 0;
		}
		String charsetName = charset.toString();
		

		if ("ibm437".equalsIgnoreCase(charsetName)) {
			return 0x01;
		}
		if ("ibm850".equalsIgnoreCase(charsetName)) {
			return 0x02;
		}
		if ("windows-1252".equalsIgnoreCase(charsetName)) {
			return 0x03;
		}
		if ("iso-8859-1".equalsIgnoreCase(charsetName)) {
			return 0x03;
		}
		if ("MacRoman".equalsIgnoreCase(charsetName)) {
			return 0x04;
		}
		if ("IBM437".equalsIgnoreCase(charsetName)) {
			return 0x09;
		}		
		if ("IBM932".equalsIgnoreCase(charsetName)) {
			return 0x13;
		}
		if ("IBM863".equalsIgnoreCase(charsetName)) {
			return 0x1c;
		}
		if ("IBM852".equalsIgnoreCase(charsetName)) {
			return 0x1f;
		}
		if ("IBM860".equalsIgnoreCase(charsetName)) {
			return 0x24;
		}
		if ("IBM936".equalsIgnoreCase(charsetName)) {
			return 0x4d;
		}
		if ("IBM949".equalsIgnoreCase(charsetName)) {
			return 0x4e;
		}
		if ("IBM950".equalsIgnoreCase(charsetName)) {
			return 0x4f;
		}
		if ("IBM874".equalsIgnoreCase(charsetName)) {
			return 0x50;
		}
		if ("IBM852".equalsIgnoreCase(charsetName)) {
			return 0x64;
		}
		if ("IBM866".equalsIgnoreCase(charsetName)) {
			return 0x65;
		}
		if ("IBM865".equalsIgnoreCase(charsetName)) {
			return 0x66;
		}
		if ("IBM861".equalsIgnoreCase(charsetName)) {
			return 0x67;
		}
		if ("IBM895".equalsIgnoreCase(charsetName)) {
			return 0x68;
		}
		if ("IBM620".equalsIgnoreCase(charsetName)) {
			return 0x69;
		}
		if ("IBM737".equalsIgnoreCase(charsetName)) {
			return 0x6a;
		}
		if ("IBM857".equalsIgnoreCase(charsetName)) {
			return 0x6b;
		}
		if ("IBM863".equalsIgnoreCase(charsetName)) {
			return 0x6c;
		}
		if ("IBM737".equalsIgnoreCase(charsetName)) {
			return 0x86;
		}
		if ("windows-950".equalsIgnoreCase(charsetName)) {
			return 0x78;
		}
		if ("windows-949".equalsIgnoreCase(charsetName)) {
			return 0x79;
		}
		if ("gbk".equalsIgnoreCase(charsetName)) {
			return 0x7a;
		}
		if ("windows-932".equalsIgnoreCase(charsetName)) {
			return 0x7b;
		}
		if ("windows-874".equalsIgnoreCase(charsetName)) {
			return 0x7c;
		}
		if ("windows-1255".equalsIgnoreCase(charsetName)) {
			return 0x7d;
		}
		if ("windows-1256".equalsIgnoreCase(charsetName)) {
			return 0x7e;
		}
		if ("IBM852".equalsIgnoreCase(charsetName)) {
			return 0x87;
		}
		if ("IBM857".equalsIgnoreCase(charsetName)) {
			return 0x88;
		}
		if ("x-MacCyrillic".equalsIgnoreCase(charsetName)) {
			return 0x96;
		}
		if ("x-MacCentralEurope".equalsIgnoreCase(charsetName)) {
			return 0x97;
		}
		if ("x-MacGreek".equalsIgnoreCase(charsetName)) {
			return 0x98;
		}

		if ("windows-1250".equalsIgnoreCase(charsetName)) {
			return 0xc8;
		}
		if ("windows-1251".equalsIgnoreCase(charsetName)) {
			return 0xc9;
		}
		if ("windows-1254".equalsIgnoreCase(charsetName)) {
			return 0xca;
		}
		if ("windows-1253".equalsIgnoreCase(charsetName)) {
			return 0xcb;
		}
		if ("IBM1257".equalsIgnoreCase(charsetName)) {
			return 0xcc;
		}
		// Unsupported charsets returns 0
		return 0;

	}

}
