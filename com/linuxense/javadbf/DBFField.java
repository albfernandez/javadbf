/*
  DBFField
	Class represents a "field" (or column) definition of a DBF data structure.

  This file is part of JavaDBF packege.

  author: anil@linuxense
  license: LGPL (http://www.gnu.org/copyleft/lesser.html)

  $Id: DBFField.java,v 1.1 2003-06-04 09:32:33 anil Exp $
*/

package com.linuxense.javadbf;
import java.io.*;

public class DBFField {

	public static final byte FIELD_TYPE_C = (byte)'C';
	public static final byte FIELD_TYPE_L = (byte)'L';
	public static final byte FIELD_TYPE_N = (byte)'N';
	public static final byte FIELD_TYPE_F = (byte)'F';
	public static final byte FIELD_TYPE_D = (byte)'D';
	public static final byte FIELD_TYPE_M = (byte)'M';

	/* Field struct variables start here */
	byte[] fieldName = new byte[ 11]; /* 0-10*/
	byte dataType;                    /* 11 */
	int reserv1;                      /* 12-15 */
	byte fieldLength;                 /* 16 */
	byte decimalCount;                /* 17 */
	short reserv2;                    /* 18-19 */
	byte workAreaId;                  /* 20 */
	short reserv3;                    /* 21-22 */
	byte setFieldsFlag;               /* 23 */
	byte[] reserv4 = new byte[ 7];    /* 24-30 */
	byte indexFieldFlag;              /* 31 */
	/* Field struct variables end here */

	/* other class variables */
	int nameNullIndex = 0;

	public static DBFField createField( DataInputStream in) 
	throws IOException {

		DBFField field = new DBFField();

		byte t_byte = in.readByte(); /* 0 */
		if( t_byte == (byte)0x0d) {

			System.out.println( "End of header found");
			return null;
		}

		in.read( field.fieldName, 1, 10);	/* 1-10 */
		field.fieldName[0] = t_byte;

		for( int i=0; i<field.fieldName.length; i++) {

			if( field.fieldName[ i] == (byte)0) {

				field.nameNullIndex = i;
				break;
			}
		}

		field.dataType = in.readByte(); /* 11 */
		field.reserv1 = Utils.readLittleEndianInt( in); /* 12-15 */
		field.fieldLength = in.readByte();  /* 16 */
		field.decimalCount = in.readByte(); /* 17 */
		field.reserv2 = Utils.readLittleEndianShort( in); /* 18-19 */
		field.workAreaId = in.readByte(); /* 20 */
		field.reserv2 = Utils.readLittleEndianShort( in); /* 21-22 */
		field.setFieldsFlag = in.readByte(); /* 23 */
		in.read( field.reserv4); /* 24-30 */
		field.indexFieldFlag = in.readByte(); /* 31 */

		return field;
	}

	public void write( OutputStream os)
	throws IOException {

		DataOutputStream out = new DataOutputStream( os);

		// Field Name
		out.write( fieldName);        /* 0-10 */
		out.write( new byte[ 11 - fieldName.length]);

		/*
		// Then padd the rest of the bytes = terminator with null
		for( int i=11 - fieldName.length; i>0; i--) {

			out.write( (byte)0x00);
		}
		*/

		// data type
		out.writeByte( dataType); /* 11 */
		out.writeInt( 0x00);   /* 12-15 */
		out.writeByte( fieldLength); /* 16 */
		out.writeByte( decimalCount); /* 17 */
		out.writeShort( (short)0x00); /* 18-19 */
		out.writeByte( (byte)0x00); /* 20 */
		out.writeShort( (short)0x00); /* 21-22 */
		out.writeByte( (byte)0x00); /* 23 */
		out.write( new byte[7]); /* 24-30*/
		out.writeByte( (byte)0x00); /* 31 */
	}

	public String getName() {

		return new String( fieldName, 0, nameNullIndex);
	}

	public byte getDataType() {

		return dataType;
	}

	public int getFieldLength() {

		return fieldLength;
	}

	public int getDecimalCount() {

		return decimalCount;
	}

	// Setter methods

	// byte[] fieldName = new byte[ 11]; /* 0-10*/
  // byte dataType;                    /* 11 */
  // int reserv1;                      /* 12-15 */
  // byte fieldLength;                 /* 16 */
  // byte decimalCount;                /* 17 */
  // short reserv2;                    /* 18-19 */
  // byte workAreaId;                  /* 20 */
  // short reserv3;                    /* 21-22 */
  // byte setFieldsFlag;               /* 23 */
  // byte[] reserv4 = new byte[ 7];    /* 24-30 */
  // byte indexFieldFlag;              /* 31 */

	public void setFieldName( String value) {

		if( value == null) {

			throw new IllegalArgumentException( "Field name cannot be null");
		}

		if( value.length() == 0 || value.length() > 10) {

			throw new IllegalArgumentException( "Field name should be of length 0-10");
		}

		this.fieldName = value.getBytes();
	}

	public void setDataType( byte value) {

		switch( value) {

			case 'C':
			case 'L':
			case 'N':
			case 'F':
			case 'D':
			case 'M':

				this.dataType = value;
				break;

			default:
				throw new IllegalArgumentException( "Unknown data type");
		}
	}

	public void setFieldLength( int value) {

		if( value <= 0) {

			throw new IllegalArgumentException( "Field length should be a positive number");
		}

		fieldLength = (byte)value;
	}

	public void setDecimalCount( int value) {

		if( value < 0) {

			throw new IllegalArgumentException( "Decimal length should be a positive number");
		}

		decimalCount = (byte)value;
	}

}
