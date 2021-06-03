/*

(C) Copyright 2015-2017 Alberto Fernández <infjaf@gmail.com>
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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Locale;


/**
 * Miscelaneous functions required by the JavaDBF package.
 *
 * This class is for internal usage for JavaDBF and you should not use it.
 *
 */
public final class DBFUtils {

	private static final CharsetEncoder ASCII_ENCODER = Charset.forName("US-ASCII").newEncoder();

	private DBFUtils() {
		throw new AssertionError("No instances of this class are allowed");
	}

	/**
	 * Reads a number from a stream,
	 * @param dataInput the stream data
	 * @param length the legth of the number
	 * @return The number as a Number (BigDecimal)
	 * @throws IOException if an IO error happens
	 * @throws EOFException if reached end of file before length bytes
	 */
	public static Number readNumericStoredAsText(DataInputStream dataInput, int length) throws IOException {
		byte[] t_float = new byte[length];
		int readed = dataInput.read(t_float);
		if (readed != length) {
			throw new EOFException("failed to read:" + length + " bytes");
		}
		t_float = DBFUtils.removeSpaces(t_float);
		t_float = DBFUtils.removeNullBytes(t_float);
		if (t_float.length > 0 && DBFUtils.isPureAscii(t_float) && !DBFUtils.contains(t_float, (byte) '?') && !DBFUtils.contains(t_float, (byte) '*')) {
			String aux = new String(t_float, DBFStandardCharsets.US_ASCII).replace(',', '.');
			if (".".equals(aux)) {
				return BigDecimal.ZERO;
			}
			try {
				return new BigDecimal(aux);
			} catch (NumberFormatException e) {
				throw new DBFException("Failed to parse Float value [" + aux + "] : " + e.getMessage(), e);
			}
		} else {
			return null;
		}
		
	}

	/**
	 * Read a littleEndian integer(32b its) from DataInput
	 * @param in DataInput to read from
	 * @return int value of next 32 bits as littleEndian
	 * @throws IOException if an IO error happens
	 * @throws EOFException if reached end of file before 4 bytes are readed
	 */
	public static int readLittleEndianInt(DataInput in) throws IOException {
		int bigEndian = 0;
		for (int shiftBy = 0; shiftBy < 32; shiftBy += 8) {
			bigEndian |= (in.readUnsignedByte() & 0xff) << shiftBy;
		}
		return bigEndian;
	}

	/**
	 * Read a littleEndian short(16 bits) from DataInput
	 * @param in DataInput to read from
	 * @return short value of next 16 bits as littleEndian
	 * @throws IOException if an IO error happens
	 */
	public static short readLittleEndianShort(DataInput in) throws IOException {
		int low = in.readUnsignedByte() & 0xff;
		int high = in.readUnsignedByte();
		return (short) (high << 8 | low);
	}

	/**
	 * Remove all spaces (32) found in the data.
	 * @param array the data
	 * @return the data cleared of whitespaces
	 */
	public static byte[] removeSpaces(byte[] array) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(array.length);
		for (byte b : array) {
			if (b != ' ') {
				baos.write(b);
			}
		}
		return baos.toByteArray();
	}

	/**
	 * Remove all nulls (0) found in the data.
	 * @param array the data
	 * @return the data cleared of whitespaces
	 */
	public static byte[] removeNullBytes(byte[] array) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(array.length);
		for (byte b : array) {
			if (b != 0x00) {
				baos.write(b);
			}
		}
		return baos.toByteArray();
	}

	/**
	 * Convert a short value to littleEndian
	 * @param value value to be converted
	 * @return littleEndian short
	 */
	public static short littleEndian(short value) {

		short num1 = value;
		short mask = (short) 0xff;

		short num2 = (short) (num1 & mask);
		num2 <<= 8;
		mask <<= 8;

		num2 |= (num1 & mask) >> 8;

		return num2;
	}

	/**
	 * Convert an int value to littleEndian
	 * @param value value to be converted
	 * @return littleEndian int
	 */
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

	/**
	 * pad a string and convert it to byte[] to write to a dbf file (by default, add whitespaces to the end of the string)
	 * @param text The text to be padded
	 * @param charset Charset to use to encode the string
	 * @param length Size of the padded string
	 * @return bytes to write to the dbf file
	 */
	public static byte[] textPadding(String text, Charset charset, int length) {
		return textPadding(text, charset, length, DBFAlignment.LEFT, (byte) ' ');
	}

	/**
	 * pad a string and convert it to byte[] to write to a dbf file
	 * @param text The text to be padded
	 * @param charset Charset to use to encode the string
	 * @param length Size of the padded string
	 * @param alignment alignment to use to padd
	 * @param paddingByte the byte used to pad the string
	 * @return bytes to write to the dbf file
	 */
	public static byte[] textPadding(String text, Charset charset, int length, DBFAlignment alignment, byte paddingByte) {
		byte[] response = new byte[length];
		Arrays.fill(response, paddingByte);
		byte[] stringBytes = text.getBytes(charset);

		while (stringBytes.length > length) {
			text = text.substring(0, text.length() - 1);
			stringBytes = text.getBytes(charset);
		}

		int t_offset = 0;
		switch (alignment) {
		case RIGHT:
			t_offset = length - stringBytes.length;
			break;
		case LEFT:
		default:
			t_offset = 0;
			break;

		}
		System.arraycopy(stringBytes, 0, response, t_offset, stringBytes.length);

		return response;
	}

	/**
	 * Format a double number to write to a dbf file
	 * @param num number to format
	 * @param charset charset to use
	 * @param fieldLength field length
	 * @param sizeDecimalPart decimal part size
	 * @return bytes to write to the dbf file
	 */

	public static byte[] doubleFormating(Number num, Charset charset, int fieldLength, int sizeDecimalPart) {
		int sizeWholePart = fieldLength - (sizeDecimalPart > 0 ? sizeDecimalPart + 1 : 0);

		StringBuilder format = new StringBuilder(fieldLength);
		for (int i = 0; i < sizeWholePart-1; i++) {
			format.append("#");
		}
		if (format.length() < sizeWholePart) {
			format.append("0");
		}
		if (sizeDecimalPart > 0) {
			format.append(".");
			for (int i = 0; i < sizeDecimalPart; i++) {
				format.append("0");
			}
		}

		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
		df.applyPattern(format.toString());
		return textPadding(df.format(num), charset, fieldLength, DBFAlignment.RIGHT,
				(byte) ' ');
	}

	/**
	 * Checks that a byte array contains some specific byte
	 * @param array The array to search in
	 * @param value The byte to search for
	 * @return true if the array contains spcified value
	 */
	public static boolean contains(byte[] array, byte value) {
		if (array != null) {
			for (byte data : array) {
				if (data == value) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if a string is pure Ascii
	 * @param stringToCheck the string
	 * @return true if is ascci
	 */
	public static boolean isPureAscii(String stringToCheck) {
		if (stringToCheck == null || stringToCheck.length() == 0) {
			return true;
		}
		synchronized (ASCII_ENCODER) {
			return ASCII_ENCODER.canEncode(stringToCheck);
		}
	}

	/**
	 * Test if the data in the array is pure ASCII
	 * @param data data to check
	 * @return true if there are only ASCII characters
	 */
	public static boolean isPureAscii(byte[] data) {
		if (data == null) {
			return false;
		}
		for (byte b : data) {
			if (b < 0x20) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Convert LOGICAL (L) byte to boolean value
	 * @param t_logical The byte value as stored in the file
	 * @return The boolean value
	 */
	public static Object toBoolean(byte t_logical) {
		if (t_logical == 'Y' || t_logical == 'y' || t_logical == 'T' || t_logical == 't') {
			return Boolean.TRUE;
		} else if (t_logical == 'N' || t_logical == 'n' || t_logical == 'F' || t_logical == 'f') {
			return Boolean.FALSE;
		}
		return null;
	}

	/**
	 * Trims right spaces from string
	 * @param b_array the string
	 * @return the string without right spaces
	 */
	public static byte[] trimRightSpaces(byte[] b_array) {
		if (b_array == null || b_array.length == 0) {
			return new byte[0];
		}
		int pos = getRightPos(b_array);
		int length = pos + 1;
		byte[] newBytes = new byte[length];
		System.arraycopy(b_array, 0, newBytes, 0, length);
		return newBytes;
	}

	private static int getRightPos(byte[] b_array) {

		int pos = b_array.length - 1;
		while (pos >= 0 && b_array[pos] == (byte) ' ') {
			pos--;
		}
		return pos;
	}

	/**
	 * Closes silently a #{@link java.io.Closeable}.
	 * it can be null or throws an exception, will be ignored.
	 * @param closeable The item to close
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception ignore) {
				// nop
			}
		}
	}

	/**
	 * Safely skip bytesToSkip bytes (in some bufferd scenarios skip doesn't really skip all requested bytes)
	 * @param inputStream the Inputstream
	 * @param bytesToSkip number of bytes to skip
	 * @throws IOException if some IO error happens
	 */
	public static void skip(InputStream inputStream, long bytesToSkip) throws IOException {
		if (inputStream != null && bytesToSkip > 0) {
			long skipped = inputStream.skip(bytesToSkip);
			for (long i = skipped; i < bytesToSkip; i++) {
				inputStream.read();
			}
		}
	}

	/**
	 * Safely skip bytesToSkip
	 * @param dataInput the DataInput
	 * @param bytesToSkip number of bytes to skip
	 * @throws IOException if some IO error happens
	 */
	protected static void skipDataInput(DataInput dataInput, int bytesToSkip) throws IOException {
		if (dataInput != null && bytesToSkip > 0) {
			int skipped = dataInput.skipBytes(bytesToSkip);
			for (int i = skipped; i < bytesToSkip; i++) {
				dataInput.readByte();
			}
		}
	}

	protected static byte[] readAllBytes(File file) throws IOException {
		if (file == null) {
			throw new IllegalArgumentException("file must not be null");
		}
		byte[] data = new byte[(int) file.length()];
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			is.read(data);
		}
		finally {
			close(is);
		}
		return data;
	}

	protected static BitSet getBitSet(byte[] bytes) {
//		return BitSet.valueOf(bytes);
		BitSet bits = new BitSet();
	    for (int i = 0; i < bytes.length * 8; i++) {
	      if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0) {
	        bits.set(i);
	      }
	    }
	    return bits;
	}

}
