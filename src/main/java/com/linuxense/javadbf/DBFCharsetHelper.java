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
			// case 0x68:
			// Kamenicky (Czech) MS-DOS
			// return Charset.forName("895");
			// case 0x69:
			// Mazovia (Polish) MS-DOS
			// return Charset.forName("620");

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
		if ("IBM852".equalsIgnoreCase(charsetName)) {
			return 0x64;
		}
		if ("IBM865".equalsIgnoreCase(charsetName)) {
			return 0x65;
		}
		if ("IBM866".equalsIgnoreCase(charsetName)) {
			return 0x66;
		}
		if ("IBM861".equalsIgnoreCase(charsetName)) {
			return 0x67;
		}
		// 0x68
		// 0x69
		if ("IBM737".equalsIgnoreCase(charsetName)) {
			return 0x6a;
		}
		if ("IBM857".equalsIgnoreCase(charsetName)) {
			return 0x6b;
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
		// Unsupported charsets returns 0
		return 0;

	}

}
