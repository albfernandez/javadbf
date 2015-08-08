/*

(C) Copyright 2015 Alberto Fernández <infjaf@gmail.com>
(C) Copyright 2014 Jan Schlößin
(C) Copyright 2003-2004 Anil Kumar K <anil@linuxense.com>

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

import java.io.DataInput;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
/*
	Utils
Class for contining utility functions.

This file is part of JavaDBF packege.

author: anil@linuxense.com
license: LGPL (http://www.gnu.org/copyleft/lesser.html)

$Id: Utils.java,v 1.8 2004-07-19 08:58:11 anil Exp $
*/
/**
	Miscelaneous functions required by the JavaDBF package.
*/
public final class Utils {

	public static final int ALIGN_LEFT = 10;
	public static final int ALIGN_RIGHT = 12;
	private static CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder(); 


	private Utils() {
		throw new AssertionError("No instances allowed");
	}

	public static int readLittleEndianInt(DataInput in) throws IOException {

		int bigEndian = 0;
		for (int shiftBy = 0; shiftBy < 32; shiftBy += 8) {
			bigEndian |= (in.readUnsignedByte() & 0xff) << shiftBy;
		}

		return bigEndian;
	}

	public static short readLittleEndianShort(DataInput in) throws IOException {
		int low = in.readUnsignedByte() & 0xff;
		int high = in.readUnsignedByte();
		return (short) (high << 8 | low);
	}

	public static byte[] trimLeftSpaces(byte[] arr) {
		StringBuilder t_sb = new StringBuilder(arr.length);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != ' ') {
				t_sb.append((char) arr[i]);
			}
		}
		return t_sb.toString().getBytes();
	}

	public static short littleEndian(short value) {

		short num1 = value;
		short mask = (short) 0xff;

		short num2 = (short) (num1 & mask);
		num2 <<= 8;
		mask <<= 8;

		num2 |= (num1 & mask) >> 8;

		return num2;
	}

	public static int littleEndian(int value) {

		int num1 = value;
		int mask = 0xff;
		int num2 = 0x00;

		num2 |= num1 & mask;

		for (int i = 1; i < 4; i++) {

			num2 <<= 8;
			mask <<= 8;
			num2 |= (num1 & mask) >> (8 * i);
		}

		return num2;
	}

	public static byte[] textPadding(String text, String characterSetName, int length)
			throws UnsupportedEncodingException {
		return textPadding(text, characterSetName, length, Utils.ALIGN_LEFT);
	}

	public static byte[] textPadding(String text, String characterSetName, int length, int alignment)
			throws UnsupportedEncodingException {

		return textPadding(text, characterSetName, length, alignment, (byte) ' ');
	}

	public static byte[] textPadding(String text, String characterSetName, int length, int alignment, byte paddingByte)
			throws java.io.UnsupportedEncodingException {

		if (text.length() >= length) {
			return text.substring(0, length).getBytes(characterSetName);
		}

		byte byte_array[] = new byte[length];
		Arrays.fill(byte_array, paddingByte);

		switch (alignment) {
		case ALIGN_RIGHT:
			int t_offset = length - text.length();
			System.arraycopy(text.getBytes(characterSetName), 0, byte_array, t_offset, text.length());
			break;
		case ALIGN_LEFT:
		default:
			System.arraycopy(text.getBytes(characterSetName), 0, byte_array, 0, text.length());
			break;
			
		}

		return byte_array;
	}

	public static byte[] doubleFormating(Double doubleNum, String characterSetName, int fieldLength, int sizeDecimalPart)
			throws java.io.UnsupportedEncodingException {
		int sizeWholePart = fieldLength - (sizeDecimalPart > 0 ? (sizeDecimalPart + 1) : 0);

		StringBuilder format = new StringBuilder(fieldLength);
		for (int i = 0; i < sizeWholePart; i++) {
			format.append("#");
		}
		if (sizeDecimalPart > 0) {
			format.append(".");
			for (int i = 0; i < sizeDecimalPart; i++) {
				format.append("0");
			}
		}

		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
		df.applyPattern(format.toString());
		return textPadding(df.format(doubleNum.doubleValue()).toString(), characterSetName, fieldLength, ALIGN_RIGHT);
	}

	public static boolean contains(byte[] arr, byte value) {
		for (byte data: arr) {
			if (data == value) {
				return true;
			}
		}
		return false;		
	}

	public static boolean isPureAscii(String v) {
		return asciiEncoder.canEncode(v);
	}
}
