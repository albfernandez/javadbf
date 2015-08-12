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
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/*
DBFWriter
Class for defining a DBF structure and addin data to that structure and 
finally writing it to an OutputStream.

*/
/**
	An object of this class can create a DBF file.

	Create an object, <br>
	then define fields by creating DBFField objects and<br>
	add them to the DBFWriter object<br>
	add records using the addRecord() method and then<br>
	call write() method.
*/
public class DBFWriter extends DBFBase {

	/* other class variables */
	DBFHeader header;
	List<Object[]> v_records = new ArrayList<>();
	int recordCount = 0;
	RandomAccessFile raf = null; /* Open and append records to an existing DBF */
	boolean appendMode = false;

	/**
		Creates an empty DBFWriter.
	*/
	public DBFWriter() {
		this.header = new DBFHeader();
	}

	/**
	 	Creates a DBFWriter which can append to records to an existing DBF file.
		@param dbfFile. The file passed in shouls be a valid DBF file.
		@exception DBFException if the passed in file does exist but not a valid DBF file, or if an IO error occurs.
	 */
	public DBFWriter(File dbfFile) throws DBFException {

		try {
			this.raf = new RandomAccessFile(dbfFile, "rw");
			this.header = new DBFHeader();

			/*
			 * before proceeding check whether the passed in File object is an
			 * empty/non-existent file or not.
			 */
			if (!dbfFile.exists() || dbfFile.length() == 0) {
				return;
			}

			this.header.read(this.raf);

			/* position file pointer at the end of the raf */
			this.raf.seek(this.raf.length() - 1); //to ignore the END_OF_DATA byte at EoF												 
		} catch (FileNotFoundException e) {
			throw new DBFException("Specified file is not found. " + e.getMessage(), e);
		} catch (IOException e) {
			throw new DBFException(e.getMessage() + " while reading header", e);
		}

		this.recordCount = this.header.numberOfRecords;
	}

	/**
		Sets fields.
	*/
	public void setFields(DBFField[] fields) throws DBFException {

		if (this.header.fieldArray != null) {
			throw new DBFException("Fields has already been set");
		}
		if (fields == null || fields.length == 0) {
			throw new DBFException("Should have at least one field");
		}
		for (int i = 0; i < fields.length; i++) {
			if (fields[i] == null) {
				throw new DBFException("Field " + i + " is null");
			}
		}
		this.header.fieldArray = fields;
		try {
			if (this.raf != null && this.raf.length() == 0) {
				// this is a new/non-existent file. So write header before proceeding
				this.header.write(this.raf);
			}
		} catch (IOException e) {
			throw new DBFException("Error accesing file:" + e.getMessage(), e);
		}
	}

	/**
		Add a record.
	*/
	public void addRecord( Object[] values)	throws DBFException {

		if( this.header.fieldArray == null) {
			throw new DBFException( "Fields should be set before adding records");
		}

		if( values == null) {
			throw new DBFException( "Null cannot be added as row");
		}

		if( values.length != this.header.fieldArray.length) {
			throw new DBFException( "Invalid record. Invalid number of fields in row");
		}

		for (int i = 0; i < this.header.fieldArray.length; i++) {
			Object value = values[i];
			if (values[i] == null) {
				continue;
			}

			switch (this.header.fieldArray[i].getType()) {

			case CHARACTER:
				if (!(value instanceof String)) {
					throw new DBFException("Invalid value for field " + i + ":" + value);
				}
				break;

			case LOGICAL:
				if (!(value instanceof Boolean)) {
					throw new DBFException("Invalid value for field " + i + ":" + value);
				}
				break;

			case DATE:
				if (!(value instanceof Date)) {
					throw new DBFException("Invalid value for field " + i + ":" + value);
				}
				break;
			case NUMERIC:
			case FLOATING_POINT:
				if (!(value instanceof Number)) {
					throw new DBFException("Invalid value for field " + i+ ":" + value);
				}
				break;
			default:
				throw new DBFException("Unsupported writting of field type " + i + " " + this.header.fieldArray[i].getType());
			}
			
		}

		if (this.raf == null) {
			this.v_records.add(values);
		} else {
			try {
				writeRecord(this.raf, values);
				this.recordCount++;
			} catch (IOException e) {
				throw new DBFException("Error occured while writing record. " + e.getMessage(), e);
			}
		}
	}

	/**
		Writes the set data to the OutputStream.
	*/
	public void write(OutputStream out) throws DBFException {
		try {
			if( this.raf == null) {
				DataOutputStream outStream = new DataOutputStream( out);
				this.header.numberOfRecords = this.v_records.size();
				this.header.write( outStream);

				/* Now write all the records */
				for (Object[] record: this.v_records) {
					writeRecord(outStream, record);
				}

				outStream.write( END_OF_DATA);
				outStream.flush();
			}
			else {
				/* everything is written already. just update the header for record count and the END_OF_DATA mark */
				this.header.numberOfRecords = this.recordCount;
				this.raf.seek( 0);
				this.header.write( this.raf);
				this.raf.seek( this.raf.length());
				this.raf.writeByte( END_OF_DATA);
				this.raf.close();
			}
		}
		catch( IOException e) {
			throw new DBFException( e.getMessage(), e);
		}
	}

	public void write() throws DBFException {
		this.write(null);
	}

	private void writeRecord(DataOutput dataOutput, Object[] objectArray) throws IOException {
		dataOutput.write((byte) ' ');
		for (int j = 0; j < this.header.fieldArray.length; j++) {
			/* iterate throught fields */
			switch (this.header.fieldArray[j].getType()) {

			case CHARACTER:
				if (objectArray[j] != null) {
					String str_value = objectArray[j].toString();
					dataOutput.write(Utils.textPadding(str_value, this.characterSetName,
							this.header.fieldArray[j].getFieldLength()));
				} else {
					dataOutput.write(Utils.textPadding("", this.characterSetName,
							this.header.fieldArray[j].getFieldLength()));
				}

				break;

			case DATE:
				if (objectArray[j] != null) {
					GregorianCalendar calendar = new GregorianCalendar();
					calendar.setTime( (Date)objectArray[j]);
					dataOutput.write( String.valueOf( calendar.get( Calendar.YEAR)).getBytes());
					dataOutput.write( Utils.textPadding( String.valueOf( calendar.get( Calendar.MONTH)+1), this.characterSetName, 2, DBFAlignment.RIGHT, (byte)'0'));
					dataOutput.write( Utils.textPadding( String.valueOf( calendar.get( Calendar.DAY_OF_MONTH)), this.characterSetName, 2, DBFAlignment.RIGHT, (byte)'0'));
				} else {
					dataOutput.write("        ".getBytes());
				}

				break;
			case NUMERIC:
			case FLOATING_POINT:

				if (objectArray[j] != null) {
					dataOutput.write(Utils.doubleFormating((Number) objectArray[j], this.characterSetName,
							this.header.fieldArray[j].getFieldLength(), this.header.fieldArray[j].getDecimalCount()));
				} else {
					dataOutput.write(Utils.textPadding(" ", this.characterSetName,
							this.header.fieldArray[j].getFieldLength(), DBFAlignment.RIGHT));
				}

				break;

			case LOGICAL:

				if (objectArray[j] != null) {
					if ((Boolean) objectArray[j] == Boolean.TRUE) {
						dataOutput.write((byte) 'T');
					} else {
						dataOutput.write((byte) 'F');
					}
				} else {
					dataOutput.write((byte) '?');
				}

				break;

			default:
				throw new DBFException("Unknown field type " + this.header.fieldArray[j].getType());
			}
		}
	}
}
