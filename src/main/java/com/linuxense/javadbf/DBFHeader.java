/*

(C) Copyright 2015-2017 Alberto Fernández <infjaf@gmail.com>
(C) Copyright 2014 Jan Schlößin
(C) Copyright 2004 Anil Kumar K <anil@linuxense.com>

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
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class for reading the metadata assuming that the given InputStream carries
 * DBF data.
 */
public class DBFHeader {

	public static final byte SIG_DBASE_III = (byte) 0x03;
	/* DBF structure start here */
	
	private byte signature;              /* 0 */
	private byte year;                   /* 1 */
	private byte month;                  /* 2 */
	private byte day;                    /* 3 */
	int numberOfRecords;         /* 4-7 */
	short headerLength;          /* 8-9 */
	short recordLength;          /* 10-11 */
	private short reserv1;               /* 12-13 */
	private byte incompleteTransaction;  /* 14 */
	private byte encryptionFlag;         /* 15 */
	private int freeRecordThread;        /* 16-19 */
	private int reserv2;                 /* 20-23 */
	private int reserv3;                 /* 24-27 */
	private byte mdxFlag;                /* 28 */
	private byte languageDriver;         /* 29 */
	private short reserv4;               /* 30-31 */
	DBFField []fieldArray;       /* each 32 bytes */	
	DBFField[] userFieldArray; 
	private byte terminator1;            /* n+1 */
	
	private Charset detectedCharset;
	private Charset usedCharset;
	


	private static final int DBASE_LEVEL_7 = 4;
	
	protected DBFHeader() {
		this.signature = SIG_DBASE_III;
		this.terminator1 = 0x0D;
	}

	void read( DataInput dataInput, Charset charset) throws IOException {

		this.signature = dataInput.readByte(); /* 0 */
		
				

		this.year = dataInput.readByte();      /* 1 */
		this.month = dataInput.readByte();     /* 2 */
		this.day = dataInput.readByte();       /* 3 */
		this.numberOfRecords = DBFUtils.readLittleEndianInt( dataInput); /* 4-7 */

		this.headerLength = DBFUtils.readLittleEndianShort( dataInput); /* 8-9 */
		this.recordLength = DBFUtils.readLittleEndianShort( dataInput); /* 10-11 */

		this.reserv1 = DBFUtils.readLittleEndianShort( dataInput);      /* 12-13 */
		this.incompleteTransaction = dataInput.readByte();           /* 14 */
		this.encryptionFlag = dataInput.readByte();                  /* 15 */
		this.freeRecordThread = DBFUtils.readLittleEndianInt( dataInput); /* 16-19 */
		this.reserv2 = dataInput.readInt();                            /* 20-23 */
		this.reserv3 = dataInput.readInt();                            /* 24-27 */
		this.mdxFlag = dataInput.readByte();                           /* 28 */
		this.languageDriver = dataInput.readByte();                    /* 29 */
		this.reserv4 = DBFUtils.readLittleEndianShort( dataInput);        /* 30-31 */
		
		
		this.detectedCharset = DBFCharsetHelper.getCharsetByByte(this.languageDriver);
		
		if (isDB7()) {
			byte[] languageName = new byte[32];
			dataInput.readFully(languageName);
//			this.languageDriverName =  new String (languageName, StandardCharsets.US_ASCII);
//			this.reserv5 =  dataInput.readInt(); 
			dataInput.readInt(); 
		}
		
		List<DBFField> v_fields = new ArrayList<>();
		
		this.usedCharset = this.detectedCharset;
		if (charset != null) {
			this.usedCharset = charset;
		}
		if (this.usedCharset == null) {
			this.usedCharset = StandardCharsets.ISO_8859_1;
		}
		
		DBFField field = null; 
		if (isDB7()) {
			/* 48 each */
			while ((field = DBFField.createFieldDB7(dataInput,this.usedCharset))!= null) {
				v_fields.add(field);
			}
		}
		else {
			/* 32 each */ 
			while ((field = DBFField.createField(dataInput,this.usedCharset))!= null) {
				v_fields.add(field);
			}	

		}
		this.fieldArray = v_fields.toArray(new DBFField[v_fields.size()]);
		List<DBFField> userFields = new ArrayList<>();
		for (DBFField field1: this.fieldArray) {
			if (!field1.isSystem()) {
				userFields.add(field1);
			}
		}
		this.userFieldArray = userFields.toArray(new DBFField[userFields.size()]);
		
	}
	int getTableHeaderSize() {
		if (isDB7()) {
			return 68;
		}
		return 32;
	}

	int getFieldDescriptorSize() {
		if (isDB7()) {
			return 48;
		}
		return 32;
	}
	private boolean isDB7() {
		return (this.signature & 0x7) == DBASE_LEVEL_7;
	}
	
	void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeByte(this.signature); /* 0 */

		Calendar calendar = Calendar.getInstance();
		this.year = (byte) (calendar.get(Calendar.YEAR) - 1900);
		this.month = (byte) (calendar.get(Calendar.MONTH) + 1);
		this.day = (byte) (calendar.get(Calendar.DAY_OF_MONTH));

		dataOutput.writeByte(this.year); /* 1 */
		dataOutput.writeByte(this.month); /* 2 */
		dataOutput.writeByte(this.day); /* 3 */

		this.numberOfRecords = DBFUtils.littleEndian(this.numberOfRecords);
		dataOutput.writeInt(this.numberOfRecords); /* 4-7 */

		this.headerLength = findHeaderLength();
		dataOutput.writeShort(DBFUtils.littleEndian(this.headerLength)); /* 8-9 */

		this.recordLength = sumUpLenghtOfFields();
		dataOutput.writeShort(DBFUtils.littleEndian(this.recordLength)); /* 10-11 */

		dataOutput.writeShort(DBFUtils.littleEndian(this.reserv1)); /* 12-13 */
		dataOutput.writeByte(this.incompleteTransaction); /* 14 */
		dataOutput.writeByte(this.encryptionFlag); /* 15 */
		dataOutput.writeInt(DBFUtils.littleEndian(this.freeRecordThread));/* 16-19 */
		dataOutput.writeInt(DBFUtils.littleEndian(this.reserv2)); /* 20-23 */
		dataOutput.writeInt(DBFUtils.littleEndian(this.reserv3)); /* 24-27 */

		dataOutput.writeByte(this.mdxFlag); /* 28 */
		if (this.languageDriver != 0) {
			dataOutput.writeByte(this.languageDriver); /* 29 */
		}
		else if (getUsedCharset() != null) {
			dataOutput.writeByte(DBFCharsetHelper.getDBFCodeForCharset(getUsedCharset()));
		}
		else {
			dataOutput.writeByte(0);
		}
		dataOutput.writeShort(DBFUtils.littleEndian(this.reserv4)); /* 30-31 */
		for (DBFField field : this.fieldArray) {
			field.write(dataOutput,getUsedCharset());
		}
		dataOutput.writeByte(this.terminator1); /* n+1 */
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
		(32*this.fieldArray.length)+
		1
		);
	}

	private short sumUpLenghtOfFields() {
		int sum = 0;
		for (DBFField field : this.fieldArray) {
			sum += field.getLength();
		}
		return (short) (sum + 1);
	}
	
	/**
	 * 
	 * @return The year the file was created
	 */
	public int getYear() {
		return 1900 + this.year;
	}
	/**
	 * 
	 * @return The month the file was created
	 */
	public int getMonth() {
		return this.month;
	}
	/**
	 * 
	 * @return The day of month the file was created
	 */
	public int getDay() {
		return this.day;
	}
	
	/**
	 * Gets the date the file was modified
	 * @return The date de file was created
	 */
	public Date getLastModificationDate() {
		if (this.year == 0 || this.month == 0 || this.day == 0){
			return null;
		}
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(this.year, this.month, this.day, 0, 0, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		}
		catch (Exception e) {
			return null;
		}
	}
	
	
	protected Charset getDetectedCharset() {
		return this.detectedCharset;
	}

	
	protected Charset getUsedCharset() {
		return this.usedCharset;
	}
	protected void setUsedCharset(Charset charset) {
		this.usedCharset = charset;
	}
	
}
