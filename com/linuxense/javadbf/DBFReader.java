/*
  DBFReader
  Class for reading metadata and records assuming that the given
	InputStream comtains a DBF stream.

  This file is part of JavaDBF packege.

  Author: anil@linuxense
  License: LGPL (http://www.gnu.org/copyleft/lesser.html)

  $Id: DBFReader.java,v 1.1 2003-06-04 09:32:33 anil Exp $
*/
package com.linuxense.javadbf;

import java.io.*;
import java.util.*;

public class DBFReader {

	DataInputStream dataInputStream;

	/* DBF structure start here */
	byte signature;       /* 0 */
	
	byte year;            /* 1 */
	byte month;           /* 2 */
	byte day;             /* 3 */
	int numberOfRecords;  /* 4-7 */
	short headerLength;   /* 8-9 */
	short recordLength;   /* 10-11 */
	short reserv1;        /* 12-13 */
	byte incompleteTransaction; /* 14 */
	byte encryptionFlag;  /* 15 */
	int freeRecordThread; /* 16-19 */
	int reserv2;          /* 20-23 */
	int reserv3;          /* 24-27 */
	byte mdxFlag;         /* 28 */
	byte languageDriver;  /* 29 */
	short reserv4;        /* 30-31 */
	DBFField []fieldArray; /* each 32 bytes */	
	byte terminator1;      /* n+1 */
	//byte[] databaseContainer; /* 263 bytes */
	/* DBF structure ends here */

	/* Class specific variables */
	boolean isClosed = true;

	public DBFReader( InputStream in) throws DBFException {

		try {

			this.dataInputStream = new DataInputStream( in);
			this.isClosed = false;
			readHeader();
		}
		catch( IOException e) {

			throw new DBFException( e.getMessage());	
		}
	}

	private void readHeader()
	throws IOException {

		signature = dataInputStream.readByte(); /* 0 */
		year = dataInputStream.readByte();      /* 1 */
		month = dataInputStream.readByte();     /* 2 */
		day = dataInputStream.readByte();       /* 3 */
		System.out.println( "date of change: " + (byte)year + "/" + (byte)month + "/" + (byte)day);
		numberOfRecords = Utils.readLittleEndianInt( dataInputStream); /* 4-7 */
		 System.out.println( "Number of records: " + numberOfRecords);

		headerLength = Utils.readLittleEndianShort( dataInputStream); /* 8-9 */
		recordLength = Utils.readLittleEndianShort( dataInputStream); /* 10-11 */

		reserv1 = Utils.readLittleEndianShort( dataInputStream);      /* 12-13 */
		incompleteTransaction = dataInputStream.readByte();           /* 14 */
		encryptionFlag = dataInputStream.readByte();                  /* 15 */
		freeRecordThread = Utils.readLittleEndianInt( dataInputStream); /* 16-19 */
		reserv2 = dataInputStream.readInt();                            /* 20-23 */
		reserv3 = dataInputStream.readInt();                            /* 24-27 */
		mdxFlag = dataInputStream.readByte();                           /* 28 */
		languageDriver = dataInputStream.readByte();                    /* 29 */
		reserv4 = Utils.readLittleEndianShort( dataInputStream);        /* 30-31 */

		Vector v_fields = new Vector();
		
		DBFField field = DBFField.createField( dataInputStream); /* 32 each */
		while( field != null) {

			v_fields.addElement( field);
			field = DBFField.createField( dataInputStream);
		}

		fieldArray = new DBFField[ v_fields.size()];
		
		for( int i=0; i<fieldArray.length; i++) {

			fieldArray[ i] = (DBFField)v_fields.elementAt( i);
		}	
		System.out.println( "Number of fields: " + fieldArray.length);

		/* it might be required to leap to the start of records at times */
		int t_dataStartIndex = this.headerLength - ( 32 + (32*fieldArray.length)) - 1;
		if( t_dataStartIndex > 0) {

			dataInputStream.skip( t_dataStartIndex);
		}
	}


	public String toString() {

		StringBuffer sb = new StringBuffer(  year + "/" + month + "/" + day + "\n"
		+ "Total records: " + numberOfRecords + 
		"\nHEader length: " + headerLength +
		"");

		for( int i=0; i<fieldArray.length; i++) {

			sb.append( fieldArray[i].getName());
			sb.append( "\n");
		}

		return sb.toString();
	}

	public DBFField getField( int index) 
	throws DBFException {

		if( isClosed) {

			throw new DBFException( "Source is not open");
		}

		return fieldArray[ index];
	}

	public int getFieldCount() 
	throws DBFException {

		if( isClosed) {

			throw new DBFException( "Source is not open");
		}

		if( fieldArray != null) {

			return fieldArray.length;
		}

		return -1;
	}

	public Object[] nextRecord() 
	throws DBFException {

		if( isClosed) {

			throw new DBFException( "Source is not open");
		}

		Object recordObjects[] = new Object[ fieldArray.length];

		try {

			boolean isDeleted = false;
			do {
				
				if( isDeleted) {
	
					dataInputStream.skip( this.recordLength-1);
				}
	
				int t_byte = dataInputStream.readByte();
				if( t_byte == 26) {

					return null;
				}

				isDeleted = (  t_byte == '*');
			} while( isDeleted);
	
			for( int i=0; i<fieldArray.length; i++) {
	
				switch( fieldArray[i].getDataType()) {
	
					case 'C':
	
						byte b_array[] = new byte[ fieldArray[i].getFieldLength()];
						dataInputStream.read( b_array);
						recordObjects[i] = new String( b_array);
						break;
	
					case 'D':
	
						byte t_byte_year[] = new byte[ 4];
						dataInputStream.read( t_byte_year);
	
						byte t_byte_month[] = new byte[ 2];
						dataInputStream.read( t_byte_month);
	
						byte t_byte_day[] = new byte[ 2];
						dataInputStream.read( t_byte_day);
	
						GregorianCalendar calendar = new GregorianCalendar( 
							Integer.parseInt( new String( t_byte_year)),
							Integer.parseInt( new String( t_byte_month)),
							Integer.parseInt( new String( t_byte_day))
						);
	
						recordObjects[i] = calendar.getTime();
						break;
	
					case 'F':
	
						try {

							byte t_float[] = new byte[ fieldArray[i].getFieldLength()];
							dataInputStream.read( t_float);
							t_float = Utils.trimLeftSpaces( t_float);
							if( t_float.length > 0 && !Utils.contains( t_float, (byte)'?')) {

								recordObjects[i] = new Float( new String( t_float));
							}
							else {

								recordObjects[i] = null;
							}
						}
						catch( NumberFormatException e) {

							throw new DBFException( "Failed to parse Float: " + e.getMessage());
						}

						break;
	
					case 'N':
	
						try {

							byte t_numeric[] = new byte[ fieldArray[i].getFieldLength()];
							dataInputStream.read( t_numeric);
							t_numeric = Utils.trimLeftSpaces( t_numeric);

							if( t_numeric.length > 0 && !Utils.contains( t_numeric, (byte)'?')) {

								recordObjects[i] = new Integer( new String( t_numeric));
							}
							else {

								recordObjects[i] = null;
							}
						}
						catch( NumberFormatException e) {

							throw new DBFException( "Failed to parse Number: " + e.getMessage());
						}

						break;
	
					case 'L':
	
						byte t_logical = dataInputStream.readByte();
						if( t_logical == 'Y' || t_logical == 't' || t_logical == 'T' || t_logical == 't') {
	
							recordObjects[i] = Boolean.TRUE;
						}
						else {
	
							recordObjects[i] = Boolean.FALSE;
						}
						break;
	
					case 'M':
						// TODO Later
						recordObjects[i] = new String( "null");
						break;

					default:
						recordObjects[i] = new String( "null");
				}
			}
		}
		catch( EOFException e) {

			return null;
		}
		catch( IOException e) {

			throw new DBFException( e.getMessage());
		}

		return recordObjects;
	}
}
