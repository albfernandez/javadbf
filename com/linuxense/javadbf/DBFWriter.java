/*
	DBFWriter
	Class for defining a DBF structure and addin data to that structure and 
	finally writing it to an OutputStream.

	This file is part of JavaDBF packege.

	author: anil@linuxense
	license: LGPL (http://www.gnu.org/copyleft/lesser.html)

	$Id: DBFWriter.java,v 1.4 2003-07-23 10:02:56 anil Exp $
*/
package com.linuxense.javadbf;
import java.io.*;
import java.util.*;

/**
	An object of this class can create a DBF file.

	Create an object, <br>
	then define fields by creating DBFField objects and<br>
	add them to the DBFWriter object<br>
	add records using the addRecord() method and then<br>
	call write() method.
*/
public class DBFWriter {

	
	byte signature = (byte)0x03;/* 0 */
	
	byte year;            /* 1 */
	byte month;           /* 2 */
	byte day;             /* 3 */
	int numberOfRecords;  /* 4-7 */
	short headerLength;   /* 8-9 */
	short recordLength;   /* 10-11 */
	short reserv1 = 0x00;        /* 12-13 */
	byte incompleteTransaction = 0x00; /* 14 */
	byte encryptionFlag = 0x00;  /* 15 */
	int freeRecordThread = 0x00; /* 16-19 */
	int reserv2 = 0x00;          /* 20-23 */
	int reserv3 = 0x00;          /* 24-27 */
	byte mdxFlag = 0x00;         /* 28 */
	byte languageDriver = 0x00;  /* 29 */
	short reserv4 = 0x00;        /* 30-31 */
	DBFField []fieldArray; /* each 32 bytes */	
	byte terminator1 = 0x0D;      /* n+1 */

	/* other class variables */
	Vector v_records = new Vector();

	/**
		Creates an empty Object.
	*/
	public DBFWriter() {
	}

	/**
		Sets a field.
	*/
	public void setFields( DBFField[] fields)
	throws DBFException {

		if( fields == null || fields.length == 0) {

			throw new DBFException( "Should have at least one field");
		}

		fieldArray = fields;
	}

	/**
		Add a record.
	*/
	public void addRecord( Object[] values)
	throws DBFException {

		if( fieldArray == null) {

			throw new DBFException( "Fields should be set before adding records");
		}

		if( values == null) {

			throw new DBFException( "Null cannot be added as row");
		}

		if( values.length != fieldArray.length) {

			throw new DBFException( "Invalid record. Invalid number of fields in row");
		}

		for( int i=0; i<fieldArray.length; i++) {

			switch( fieldArray[i].getDataType()) {

				case 'C':
					if( !(values[i] instanceof String)) {
						throw new DBFException( "Invalid value for field " + i);
					}
					break;

				case 'L':
					if( !( values[i] instanceof String)) {
					  throw new DBFException( "Invalid value for field " + i);
					}
					break;

				case 'N':
					if( !( values[i] instanceof Double)) {
						throw new DBFException( "Invalid value for field " + i);
					}
					break;

				case 'D':
					if( !( values[i] instanceof Date)) {
						throw new DBFException( "Invalid value for field " + i);
					}
					break;

				case 'F':
					if( !(values[i] instanceof Double)) {

						throw new DBFException( "Invalid value for field " + i);
					}
					break;
			}
		}

		v_records.addElement( values);
	}

	/**
		Writes the set data to the OutputStream.
	*/
	public void write( OutputStream out)
	throws DBFException {

		try {

			DataOutputStream outStream = new DataOutputStream( out);

			outStream.writeByte( signature);                       /* 0 */

			GregorianCalendar calendar = new GregorianCalendar();
			year = (byte)( calendar.get( Calendar.YEAR) - 1900);
			month = (byte)( calendar.get( Calendar.MONTH)+1);
			day = (byte)( calendar.get( Calendar.DAY_OF_MONTH));

			outStream.writeByte( year);  /* 1 */
			outStream.writeByte( month); /* 2 */
			outStream.writeByte( day);   /* 3 */
	
			numberOfRecords = v_records.size();
			//System.out.println( "Number of records in O/S: " + numberOfRecords);
			numberOfRecords = Utils.littleEndian( numberOfRecords);
			outStream.writeInt( numberOfRecords); /* 4-7 */

			headerLength = findHeaderLength();
			outStream.writeShort( Utils.littleEndian( headerLength)); /* 8-9 */

			recordLength = findRecordLength(); 
			outStream.writeShort( Utils.littleEndian( recordLength)); /* 10-11 */

			outStream.writeShort( Utils.littleEndian( reserv1)); /* 12-13 */
			outStream.writeByte( incompleteTransaction); /* 14 */
			outStream.writeByte( encryptionFlag); /* 15 */
			outStream.writeInt( Utils.littleEndian( freeRecordThread));/* 16-19 */
			outStream.writeInt( Utils.littleEndian( reserv2)); /* 20-23 */
			outStream.writeInt( Utils.littleEndian( reserv3)); /* 24-27 */

			outStream.writeByte( mdxFlag); /* 28 */
			outStream.writeByte( languageDriver); /* 29 */
			outStream.writeShort( Utils.littleEndian( reserv4)); /* 30-31 */

			for( int i=0; i<fieldArray.length; i++) {

				fieldArray[i].write( outStream);
			}

			outStream.writeByte( terminator1); /* n+1 */

			/* Now write all the records */
			int t_recCount = v_records.size();
			for( int i=0; i<t_recCount; i++) { // iterate through records

				Object[] t_values = (Object[])v_records.elementAt( i);
				outStream.write( (byte)' ');
				for( int j=0; j<fieldArray.length; j++) { // iterate throught fields

					switch( fieldArray[j].getDataType()) {

						case 'C':
							if( t_values[j] != null) {

								String str_value = t_values[j].toString();	
								outStream.write( Utils.textPadding( str_value, fieldArray[j].getFieldLength()));
							}
							else {

								outStream.write( Utils.textPadding( "", fieldArray[j].getFieldLength()));
							}

							break;

						case 'D':
							if( t_values[j] != null) {

								calendar = new GregorianCalendar();
								calendar.setTime( (Date)t_values[j]);
								StringBuffer t_sb = new StringBuffer();
								t_sb.append( calendar.get( Calendar.YEAR));
								t_sb.append( "/");
								t_sb.append( ( calendar.get( Calendar.MONTH)+1));
								t_sb.append( "/");
								t_sb.append( calendar.get( Calendar.DAY_OF_MONTH));
								outStream.write( t_sb.toString().getBytes());
							}
							else {

								outStream.write( "    /  /  ".getBytes());
							}

							break;

						case 'F':

							if( t_values[j] != null) {

								outStream.write( Utils.floatFormating( (Double)t_values[j], fieldArray[j].getFieldLength(), fieldArray[j].getDecimalCount()));
							}
							else {

								outStream.write( Utils.textPadding( "?", fieldArray[j].getFieldLength(), Utils.ALIGN_RIGHT));
							}

							break;

						case 'N':

							if( t_values[j] != null) {

								//System.out.println( t_values[j].toString());
								outStream.write(
									Utils.textPadding(
										((Double)t_values[j]).toString(),fieldArray[j].getFieldLength(), Utils.ALIGN_RIGHT));
							}
							else {

								outStream.write( 
									Utils.textPadding( "?", fieldArray[j].getFieldLength(), Utils.ALIGN_RIGHT));
							}

							break;
						case 'L':

							if( t_values[j] != null) {

								if( (Boolean)t_values[j] == Boolean.TRUE) {

									outStream.write( (byte)'T');
								}
								else {

									outStream.write((byte)'F');
								}
							}
							else {

								outStream.write( (byte)'?');
							}

							break;

						case 'M':

							break;

						default:	
							throw new DBFException( "Unknown field type " + fieldArray[j].getDataType());
					}
				}	// iterating through the fields
			} // iterating throgh records

			outStream.flush();
			//System.out.println( "Written " + v_records.size() + "(" + Utils.littleEndian(this.numberOfRecords) + ") records");
		}
		catch( IOException e) {

			throw new DBFException( e.getMessage());
		}
	}

	private short findRecordLength() {

		int recordLength = 0;
		for( int i=0; i<fieldArray.length; i++) {

			recordLength += fieldArray[i].getFieldLength();
		}

		//System.out.println( "Record length: " + (recordLength+1));
		return (short)(recordLength + 1);
	}

	private short findHeaderLength() {

		return (short)(
		1+
		3+
		4+
		2+
		2+
		2+
		1+
		1+
		4+
		4+
		4+
		1+
		1+
		2+
		(32*fieldArray.length)+
		1
		);
	}
}
