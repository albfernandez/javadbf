/*
 	Utils
  Class for contining utility functions.

  This file is part of JavaDBF packege.

  author: anil@linuxense
  license: LGPL (http://www.gnu.org/copyleft/lesser.html)

  $Id: Utils.java,v 1.4 2004-01-08 17:55:02 anil Exp $
*/
package com.linuxense.javadbf;

import java.io.*;
import java.util.*;
import java.text.*;

/**
	Miscelaneous functions required by the JavaDBF package.
*/
public class Utils {

	public static final int ALIGN_LEFT = 10;
	public static final int ALIGN_RIGHT = 12;

	public static int readLittleEndianInt( DataInputStream in)
	throws IOException {

		int bigEndian = 0;
		for( int shiftBy=0; shiftBy<32; shiftBy+=8) {

			bigEndian |= (in.readByte()&0xff) << shiftBy;
		}

		return bigEndian;
	}

	public static short readLittleEndianShort( DataInputStream in)
	throws IOException {

		int low = in.readByte() & 0xff;
		int high = in.readByte();

		return (short )(high << 8 | low); 
	}

	public static byte[] trimLeftSpaces( byte [] arr) {

		StringBuffer t_sb = new StringBuffer( arr.length);

		for( int i=0; i<arr.length; i++) {

			if( arr[i] != ' ') {

				t_sb.append( (char)arr[ i]);
			}
		}

		return t_sb.toString().getBytes();
	}

	public static short littleEndian( short value) {

		short num1 = value;
		short mask = (short)0xff;

		short num2 = (short)(num1&mask);
		num2<<=8;
		mask<<=8;

		num2 |= (num1&mask)>>8;

		return num2;
	}

	public static int littleEndian(int value) {

		int num1 = value;
		int mask = 0xff;
		int num2 = 0x00;

		num2 |= num1 & mask;

		for( int i=1; i<4; i++) {

			num2<<=8;
			mask <<= 8;
			num2 |= (num1 & mask)>>(8*i);
		}

		return num2;
	}
	
	public static byte[] textPadding( String text, int length) {

		return textPadding( text, length, Utils.ALIGN_LEFT);
	}

	public static byte[] textPadding( String text, int length, int alignment) {

		return textPadding( text, length, alignment, (byte)' ');
	}

	public static byte[] textPadding( String text, int length, int alignment,
	byte paddingByte) {

		if( text.length() >= length) {

			return text.substring( 0, length).getBytes();
		}

		byte byte_array[] = new byte[ length];
		Arrays.fill( byte_array, paddingByte);

		switch( alignment) {

			case ALIGN_LEFT:
				System.arraycopy( text.getBytes(), 0, byte_array, 0, text.length());
				break;

			case ALIGN_RIGHT:
				int t_offset = length - text.length();
				System.arraycopy( text.getBytes(), 0, byte_array, t_offset, text.length());
				break;
			}	

		return byte_array;
	}

	public static byte[] floatFormating( Double doubleNum, int fieldLength, int sizeDecimalPart) {

		int sizeWholePart = fieldLength - ( sizeDecimalPart + 1);
		/*
		long t_long = doubleNum.longValue();
		while( t_long > 0) {

			sizeWholePart++;
			t_long/= 10;
		}
		*/

		//int sizeDecimalPart = fieldLength - sizeWholePart - 1/* for the . */;
		StringBuffer format = new StringBuffer( fieldLength);

		for( int i=0; i<sizeWholePart; i++) {

			format.append( "#");
		}

		format.append( ".");

		for( int i=0; i<sizeDecimalPart; i++) {

			format.append( "0");
		}

		//System.out.println( "Pattern: " + format.toString());
		DecimalFormat df = new DecimalFormat( format.toString());
		return textPadding( df.format( doubleNum.doubleValue()).toString(), fieldLength, ALIGN_RIGHT);
	}

	public static boolean contains( byte[] arr, byte value) {

		boolean found = false;
		for( int i=0; i<arr.length; i++) {

			if( arr[i] == value) {

				found = true;
				break;
			}
		}

		return found;
	}
}
