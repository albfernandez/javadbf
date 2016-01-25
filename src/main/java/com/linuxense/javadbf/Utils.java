/*

(C) Copyright 2015-2016 Alberto Fernández <infjaf@gmail.com>
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

/**
	Miscelaneous functions required by the JavaDBF package.
	@deprecated Use {@link DBFUtils}
*/
@Deprecated
public final class Utils {

	/**
	 * @deprecated Use {@link DBFAlignment#LEFT}
	 */
	@Deprecated
	public static final int ALIGN_LEFT = 10;
	/**
	 * @deprecated Use {@link DBFAlignment#RIGHT}
	 */	
	@Deprecated
	public static final int ALIGN_RIGHT = 12;
	

	private Utils() {
		throw new AssertionError("No instances of this class are allowed");
	}

	/**
	 * Read a littleEndian integer(32b its) from DataInput
	 * @param in DataInput to read from
	 * @return int value of next 32 bits as littleEndian
	 * @throws IOException
	 * @deprecated use {@link DBFUtils#readLittleEndianInt(DataInput)}
	 */
	@Deprecated
	public static int readLittleEndianInt(DataInput in) throws IOException {
		return DBFUtils.readLittleEndianInt(in);
	}

	/**
	 * Read a littleEndian short(16 bits) from DataInput
	 * @param in DataInput to read from
	 * @return short value of next 16 bits as littleEndian
	 * @throws IOException
	 * @deprecated use {@link DBFUtils#readLittleEndianShort(DataInput)}
	 */
	@Deprecated
	public static short readLittleEndianShort(DataInput in) throws IOException {
		return DBFUtils.readLittleEndianShort(in);
	}

	/**
	 * Remove all spaces (32) found in the data.
	 * @param array the data
	 * @return the data cleared of whitespaces
	 * @deprecated use {@link DBFUtils#removeSpaces(byte[])}
	 */
	@Deprecated
	public static byte[] removeSpaces(byte[] array) {
		return DBFUtils.removeSpaces(array);
	}
	
	

	/**
	 * @deprecated use {@link DBFUtils#littleEndian(short)}
	 */
	@Deprecated
	public static short littleEndian(short value) {
		return DBFUtils.littleEndian(value);
	}

	/**
	 * @deprecated use {@link DBFUtils#littleEndian(int)}
	 */
	@Deprecated
	public static int littleEndian(int value) {
		return DBFUtils.littleEndian(value);
	}

	/**
	 * @deprecated Use
	 *             {@link DBFUtils#textPadding(String, Charset, int, DBFAlignment, byte)}
	 */
	@Deprecated
	public static byte[] textPadding(String text, Charset charset, int length) {
		return DBFUtils.textPadding(text, charset, length, DBFAlignment.LEFT, (byte) ' ');
	}

	/**
	 * @deprecated Use
	 *             {@link DBFUtils#textPadding(String, Charset, int, DBFAlignment, byte)}
	 */
	@Deprecated
	public static byte[] textPadding(String text, Charset charset, int length, DBFAlignment alignment) {
		return DBFUtils.textPadding(text, charset, length, alignment, (byte) ' ');
	}



	/**
	 * @deprecated Use {@link DBFUtils#textPadding(String, Charset, int, DBFAlignment, byte)}
	 */
	@Deprecated
	public static byte[] textPadding(String text, Charset charset, int length, DBFAlignment alignment, byte paddingByte) {
		return DBFUtils.textPadding(text, charset, length, alignment, paddingByte);
	}
	
	/**
	 * @deprecated Use {@link DBFUtils#doubleFormating(Number, Charset, int, int)}
	 */
	@Deprecated
	public static byte[] doubleFormating(Number num, Charset charset, int fieldLength, int sizeDecimalPart) {
		return DBFUtils.doubleFormating(num.doubleValue(), charset, fieldLength, sizeDecimalPart);
	}

	/**
	 * @deprecated Use {@link DBFUtils#doubleFormating(Double, Charset, int, int)}
	 */
	@Deprecated
	public static byte[] doubleFormating(Double doubleNum, Charset charset, int fieldLength, int sizeDecimalPart) {
		return DBFUtils.doubleFormating(doubleNum, charset, fieldLength, sizeDecimalPart);
	}


	/**
	 * Checks that a byte array contains some specific byte
	 * @param array The array to search in
	 * @param value The byte to search for
	 * @return true if the array contains spcified value
	 * @deprecated Use {@link DBFUtils#contains(byte[], byte)}
	 */
	@Deprecated
	public static boolean contains(byte[] array, byte value) {
		return DBFUtils.contains(array, value);
	}

	/**
	 * Checks if a string is pure Ascii
	 * @param stringToCheck the string
	 * @return true if is ascci
	 * @deprecated Use {@link DBFUtils#isPureAscii(String)}
	 */
	@Deprecated
	public static boolean isPureAscii(String stringToCheck) {
		return DBFUtils.isPureAscii(stringToCheck);
	}

	/**
	 * Convert LOGICAL (L) byte to boolean value
	 * @param t_logical The byte value as stored in the file
	 * @return The boolean value
	 * @deprecated Use {@link DBFUtils#toBoolean(byte)}
	 */
	@Deprecated
	public static Object toBoolean(byte t_logical) {
		return DBFUtils.toBoolean(t_logical);
	}
	
	/**
	 * Trim spaces from both sides of the array.
	 * @param arr
	 * @return String trimmed both sides
	 * @deprecated this functions really trim all spaces, instead only left spaces, so for clarity is deprecated and 
	 * mantained for backwards compatibility, use {@link DBFUtils#removeSpaces(byte[])}
	 */
	@Deprecated
	public static byte[] trimLeftSpaces(byte[] arr) {
		return removeSpaces(arr);
	}
	
	/** 
	 * @deprecated Use {@link DBFUtils#textPadding(String, Charset, int, DBFAlignment, byte)}
	 */

	@Deprecated
	public static byte[] textPadding(String text, String characterSetName, int length, int alignment)
			throws UnsupportedEncodingException {

		return textPadding(text, characterSetName, length, alignment, (byte) ' ');
	}
	/** 
	 * @deprecated Use {@link DBFUtils#textPadding(String, Charset, int, DBFAlignment, byte)}
	 */
	@Deprecated
	public static byte[] textPadding(String text, String characterSetName, int length, int alignment, byte paddingByte)
			throws UnsupportedEncodingException {
		DBFAlignment align = DBFAlignment.RIGHT;
		if (alignment == ALIGN_LEFT) {
			align = DBFAlignment.LEFT;
		}
		return textPadding(text, characterSetName, length, align, paddingByte);

	}
	
	/** 
	 * @deprecated Use {@link DBFUtils#textPadding(String, Charset, int)}
	 */
	
	@Deprecated
	public static byte[] textPadding(String text, String characterSetName, int length)
			throws UnsupportedEncodingException {
		return textPadding(text, characterSetName, length, DBFAlignment.LEFT);
	}

	/** 
	 * @deprecated Use {@link DBFUtils#textPadding(String, Charset, int, DBFAlignment, byte)}
	 */

	@Deprecated
	public static byte[] textPadding(String text, String characterSetName, int length, DBFAlignment alignment)
			throws UnsupportedEncodingException {

		return textPadding(text, characterSetName, length, alignment, (byte) ' ');
	}

	/** 
	 * @deprecated Use {@link DBFUtils#textPadding(String, Charset, int, DBFAlignment, byte)}
	 */
	@Deprecated
	public static byte[] textPadding(String text, String characterSetName, int length, DBFAlignment alignment, byte paddingByte)
			throws UnsupportedEncodingException {
		return textPadding(text, Charset.forName(characterSetName), length, alignment, paddingByte);		
	}
	/**
	 * @deprecated Use {@link DBFUtils#doubleFormating(Number, Charset, int, int)}
	 */
	@Deprecated
	public static byte[] doubleFormating(Number num, String characterSetName, int fieldLength, int sizeDecimalPart)
			throws UnsupportedEncodingException {
		return doubleFormating(num.doubleValue(), characterSetName, fieldLength, sizeDecimalPart);
	}
	/**
	 * @deprecated Use {@link DBFUtils#doubleFormating(Double, Charset, int, int)}
	 */
	@Deprecated
	public static byte[] doubleFormating(Double doubleNum, String characterSetName, int fieldLength, int sizeDecimalPart)
			throws UnsupportedEncodingException {
		return doubleFormating(doubleNum, Charset.forName(characterSetName), fieldLength, sizeDecimalPart);
	}
	

}
