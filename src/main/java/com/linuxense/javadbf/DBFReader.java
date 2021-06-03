/*

(C) Copyright 2015-2017 Alberto Fernández <infjaf@gmail.com>
(C) Copyright 2004,2014 Jan Schlößin
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

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * DBFReader class can creates objects to represent DBF data.
 * <p>
 * This Class is used to read data from a DBF file. Meta data and records can be
 * queried against this document.
 * </p>
 * <p>
 * DBFReader cannot write to a DBF file. For creating DBF files use DBFWriter.
 * </p>
 * <p>
 * Fetching records is possible only in the forward direction and cannot be
 * re-wound. In such situations, a suggested approach is to reconstruct the
 * object.
 * </p>
 * <p>
 * The nextRecord() method returns an array of Objects and the types of these
 * Object are as follows:
 * </p>
 * <table>
 * <caption>Types mapping</caption>
 * <thead>
 * <tr>
 * <th>xBase Type</th>
 * <th>Java Type</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 * <td>C</td>
 * <td>String</td>
 * </tr>
 * <tr>
 * <td>N</td>
 * <td>java.math.BigDecimal</td>
 * </tr>
 * <tr>
 * <td>F</td>
 * <td>java.math.BigDecimal</td>
 * </tr>
 * <tr>
 * <td>L</td>
 * <td>Boolean</td>
 * </tr>
 * <tr>
 * <td>D</td>
 * <td>java.util.Date</td>
 * </tr>
 * <tr>
 * <td>Y</td>
 * <td>java.math.BigDecimal</td>
 * </tr>
 * <tr>
 * <td>I</td>
 * <td>Integer</td>
 * </tr>
 * <tr>
 * <td>T</td>
 * <td>java.util.Date</td>
 * </tr>
 * <tr>
 * <td>@</td>
 * <td>java.util.Date</td>
 * </tr>
 * <tr>
 * <td></td>
 * <td>Integer</td>
 * </tr>
 * <tr>
 * <td>V</td>
 * <td>String</td>
 * </tr>
 * <tr>
 * <td>O</td>
 * <td>Double</td>
 * </tr>
 * <tr>
 * <td>M</td>
 * <td>java.lang.String or byte[]</td>
 * </tr>
 * <tr>
 * <td>B</td>
 * <td>byte[] or java.lang.Double</td>
 * </tr>
 * <tr>
 * <td>G</td>
 * <td>byte[]</td>
 * </tr>
 * <tr>
 * <td>P</td>
 * <td>byte[]</td>
 * </tr>
 * <tr>
 * <td>Q</td>
 * <td>byte[]</td>
 * </tr>
 * </tbody>
 * </table>
 */
public class DBFReader extends DBFBase implements Closeable {

	private static final long MILLISECS_PER_DAY = 24*60*60*1000;
	private static final long TIME_MILLIS_1_1_4713_BC = -210866803200000L;

	protected InputStream inputStream;
	protected DataInputStream dataInputStream;
	private DBFHeader header;
	private boolean trimRightSpaces = true;
	private int internalRecord = 0;
	private int returnedRecords = 0;

	private DBFMemoFile memoFile = null;

	private boolean closed = false;

	private Map<String, Integer> mapFieldNames = new HashMap<String, Integer>();
	
	private boolean showDeletedRows = false;

	/**
	 * Intializes a DBFReader object.
	 *
	 * Tries to detect charset from file, if failed uses default charset ISO-8859-1
	 * When this constructor returns the object will have completed reading the
	 * header (meta date) and header information can be queried there on. And it
	 * will be ready to return the first row.
	 *
	 * @param in  the InputStream where the data is read from.
	 */
	public DBFReader(InputStream in) {
		this(in,null,false);
	}

	/**
	 * Intializes a DBFReader object.
	 *
	 * Tries to detect charset from file, if failed uses default charset ISO-8859-1
	 * When this constructor returns the object will have completed reading the
	 * header (meta date) and header information can be queried there on. And it
	 * will be ready to return the first row.
	 *
	 * @param in  the InputStream where the data is read from.
	 * @param showDeletedRows can be used to identify records that have been deleted.
	 */
	// TODO Change to boolean in 2.0
	public DBFReader(InputStream in, Boolean showDeletedRows) {
		this(in,null, showDeletedRows);
	}

	/**
	 * Initializes a DBFReader object.
	 *
	 * When this constructor returns the object will have completed reading the
	 * header (meta date) and header information can be queried there on. And it
	 * will be ready to return the first row.
	 *
	 * @param in the InputStream where the data is read from.
	 * @param charset charset used to decode field names and field contents. If null, then is autedetected from dbf file
	 */
	public DBFReader(InputStream in,Charset charset) {
		this(in, charset, false);
	}

	/**
	 * Initializes a DBFReader object.
	 *
	 * When this constructor returns the object will have completed reading the
	 * header (meta date) and header information can be queried there on. And it
	 * will be ready to return the first row.
	 *
	 * @param in the InputStream where the data is read from.
	 * @param charset charset used to decode field names and field contents. If null, then is autedetected from dbf file
	 * @param showDeletedRows can be used to identify records that have been deleted.
	 */
	public DBFReader(InputStream in, Charset charset, boolean showDeletedRows) {
		this(in, charset, showDeletedRows, true);
	}

	/**
	 * Initializes a DBFReader object.
	 *
	 * When this constructor returns the object will have completed reading the
	 * header (meta date) and header information can be queried there on. And it
	 * will be ready to return the first row.
	 *
	 * @param in the InputStream where the data is read from.
	 * @param charset charset used to decode field names and field contents. If null, then is autedetected from dbf file
	 * @param showDeletedRows can be used to identify records that have been deleted.
         * @param supportExtendedCharacterFields Defines whether 2-byte (extended) length character fields should be supported (see DBFField.adjustLengthForLongCharSupport(), default: true).
	 */
	public DBFReader(InputStream in, Charset charset, boolean showDeletedRows, boolean supportExtendedCharacterFields) {
		try {
			this.showDeletedRows = showDeletedRows;
			this.inputStream = in;
			this.dataInputStream = new DataInputStream(this.inputStream);
			this.header = new DBFHeader();
			this.header.setSupportExtendedCharacterFields(supportExtendedCharacterFields);
			this.header.read(this.dataInputStream, charset, showDeletedRows);
			setCharset(this.header.getUsedCharset());

			this.mapFieldNames = createMapFieldNames(this.header.userFieldArray);
		} catch (IOException e) {
			DBFUtils.close(dataInputStream);
			DBFUtils.close(in);
			throw new DBFException(e.getMessage(), e);
		}
	}


	private Map<String, Integer> createMapFieldNames(DBFField[] fieldArray) {
		Map<String, Integer> fieldNames = new HashMap<String, Integer>();
		for (int i = 0; i < fieldArray.length; i++) {
			String name = fieldArray[i].getName();
			fieldNames.put(name.toLowerCase(), i);
		}		
		return Collections.unmodifiableMap(fieldNames);
	}


	/**
		Returns the number of records in the DBF. This number includes deleted (hidden) records
		@return number of records in the DBF file.
	*/
	public int getRecordCount() {
		return this.header.numberOfRecords;
	}
	/**
	 * Returns the last time the file was modified
	 * @return the las time the file was modified
	 */
	public Date getLastModificationDate() {
		if (this.header != null) {
			return this.header.getLastModificationDate();
		}
		return null;
	}


	/**
	 * Returns the asked Field. In case of an invalid index, it returns a
	 * ArrayIndexOutofboundsException.
	 *
	 * @param index Index of the field. Index of the first field is zero.
	 * @return Field definition for selected field
	 */
	public DBFField getField(int index) {
		if (index < 0 || index >= this.header.userFieldArray.length) {
			throw new IllegalArgumentException("Invalid index field: (" + index+"). Valid range is 0 to " + (this.header.userFieldArray.length - 1));
		}
		return new DBFField(this.header.userFieldArray[index]);
	}

	/**
	 * Returns the number of field in the DBF.
	 * @return number of fields in the DBF file 
	 */
	public int getFieldCount() {
		return this.header.userFieldArray.length;
	}

	/**
	 * Reads the returns the next row in the DBF stream.
	 *
	 * @return The next row as an Object array. Types of the elements these
	 *          arrays follow the convention mentioned in the class description.
	 */
	public Object[] nextRecord() {
		if (this.closed) {
			throw new IllegalArgumentException("this DBFReader is closed");
		}
		List<Object> recordObjects = new ArrayList<Object>(this.getFieldCount());
		try {
			boolean isDeleted = false;

			do {
				try {
					if (isDeleted && !showDeletedRows) {
						skip(this.header.recordLength - 1);
						this.internalRecord++;
					}
					int t_byte = this.dataInputStream.readByte();
					if (t_byte == -1) {
						return null;
					}
					if (t_byte == END_OF_DATA && this.header.numberOfRecords == this.internalRecord) {
						return null;
					}
					isDeleted = t_byte == '*';
				}
				catch (EOFException e) {
					return null;
				}
			} while (isDeleted && !showDeletedRows);

			if(showDeletedRows) {
				recordObjects.add(isDeleted);
			}

			for (int i = 0; i < this.header.fieldArray.length; i++) {
				DBFField field = this.header.fieldArray[i];
				try {
					Object o = getFieldValue(field);
					if (field.isSystem() || field.getType() == DBFDataType.NULL_FLAGS) {
						if (field.getType() == DBFDataType.NULL_FLAGS && o instanceof BitSet) {
							BitSet nullFlags = (BitSet) o;
							int currentIndex = -1;
							for (int j = 0; j < this.header.fieldArray.length; j++) {
								DBFField field1 = this.header.fieldArray[j];
								if (field1.isNullable()) {
									currentIndex++;
									if (nullFlags.get(currentIndex)) {
										recordObjects.set(j, null);
									}
								}
								if (field1.getType() == DBFDataType.VARBINARY || field1.getType() == DBFDataType.VARCHAR){
									currentIndex++;
									if (recordObjects.get(j) instanceof byte[]) {
										byte[] data = (byte[]) recordObjects.get(j);
										int size = field1.getLength();
										if (!nullFlags.get(currentIndex)) {
											// Data is not full
											// length is stored in the last position
											size = data[data.length-1];
										}
										byte[] newData = new byte[size];
										System.arraycopy(data, 0, newData, 0, size);
										Object o1 = newData;
										if (field1.getType() == DBFDataType.VARCHAR) {
											o1 = new String(newData, getCharset());
										}
										recordObjects.set(j, o1);
									}
								}
							}
						}
					}
					else {
						recordObjects.add(o);
					}
					
				}
				catch (DBFException dbe) {
					throw new DBFException("Error reading field " + field.getName() + " of record " + this.internalRecord + " " + dbe.getMessage(), dbe);
				}
			}
		} catch (EOFException e) {
			throw new DBFException(e.getMessage(), e);
		} catch (IOException e) {
			throw new DBFException(e.getMessage(), e);
		}
		this.returnedRecords++;
		this.internalRecord++;
		return recordObjects.toArray();
	}
	/**
	 * Reads the returns the next row in the DBF stream.
	 *
	 * @return The next row as an DBFRow
	 */
	public DBFRow nextRow() {
		Object[] record = nextRecord();
		if (record == null) {
			return null;
		}
		return new DBFRow(record, mapFieldNames, this.header.fieldArray);
	}

	protected Object getFieldValue(DBFField field) throws IOException {
		int bytesReaded = 0;
		switch (field.getType()) {
		case CHARACTER:
			byte[] b_array = new byte[field.getLength()];
			bytesReaded = this.dataInputStream.read(b_array);
			if (bytesReaded < field.getLength()) {
				throw new EOFException("Unexpected end of file");
			}
			if (this.trimRightSpaces) {
				return new String(DBFUtils.trimRightSpaces(b_array), getCharset());
			}
			else {
				return new String(b_array, getCharset());
			}

		case VARCHAR:
		case VARBINARY:
			byte[] b_array_var = new byte[field.getLength()];
			bytesReaded = this.dataInputStream.read(b_array_var);
			if (bytesReaded < field.getLength()) {
				throw new EOFException("Unexpected end of file");
			}
			return b_array_var;
		case DATE:

			byte[] t_byte_year = new byte[4];
			bytesReaded = this.dataInputStream.read(t_byte_year);
			if (bytesReaded < 4) {
				throw new EOFException("Unexpected end of file");
			}

			byte[] t_byte_month = new byte[2];
			bytesReaded = this.dataInputStream.read(t_byte_month);
			if (bytesReaded < 2) {
				throw new EOFException("Unexpected end of file");
			}

			byte[] t_byte_day = new byte[2];
			bytesReaded = this.dataInputStream.read(t_byte_day);
			if (bytesReaded < 2) {
				throw new EOFException("Unexpected end of file");
			}

			try {
				GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(new String(t_byte_year, DBFStandardCharsets.US_ASCII)),
						Integer.parseInt(new String(t_byte_month, DBFStandardCharsets.US_ASCII)) - 1,
						Integer.parseInt(new String(t_byte_day, DBFStandardCharsets.US_ASCII)));
				return calendar.getTime();
			} catch (NumberFormatException e) {
				// this field may be empty or may have improper value set
				return null;
			}


		case FLOATING_POINT:
		case NUMERIC:
			return DBFUtils.readNumericStoredAsText(this.dataInputStream, field.getLength());

		case LOGICAL:
			byte t_logical = this.dataInputStream.readByte();
			return DBFUtils.toBoolean(t_logical);
		case LONG:
		case AUTOINCREMENT:
			int data = DBFUtils.readLittleEndianInt(this.dataInputStream);
			return data;
		case CURRENCY:
			int c_data = DBFUtils.readLittleEndianInt(this.dataInputStream);
			String s_data = String.format("%05d", c_data);
			String x1 = s_data.substring(0, s_data.length() - 4);
			String x2 = s_data.substring(s_data.length() - 4);

			skip(field.getLength() - 4);
			return new BigDecimal(x1 + "." + x2);
		case TIMESTAMP:
		case TIMESTAMP_DBASE7:
			int days = DBFUtils.readLittleEndianInt(this.dataInputStream);
			int time = DBFUtils.readLittleEndianInt(this.dataInputStream);

			if(days == 0 && time == 0) {
				return null;
			}
			else {
				Calendar calendar = new GregorianCalendar();
				calendar.setTimeInMillis(days * MILLISECS_PER_DAY + TIME_MILLIS_1_1_4713_BC + time);
				calendar.add(Calendar.MILLISECOND, -TimeZone.getDefault().getOffset(calendar.getTimeInMillis()));
				return calendar.getTime();
			}
		case MEMO:
		case GENERAL_OLE:
		case PICTURE:
		case BLOB:
			return readMemoField(field);
		case BINARY:
			if (field.getLength() == 8) {
				return  readDoubleField(field);
			}
			else {
				return readMemoField(field);
			}
		case DOUBLE:
			return readDoubleField(field);
		case NULL_FLAGS:
			byte [] data1 = new byte[field.getLength()];
			int readed = dataInputStream.read(data1);
			if (readed != field.getLength()){
				throw new EOFException("Unexpected end of file");
			}
			return DBFUtils.getBitSet(data1);
		default:
			skip(field.getLength());
			return null;
		}
	}

	private Object readDoubleField(DBFField field) throws IOException {
		byte[] data = new byte[field.getLength()];
		int bytesReaded = this.dataInputStream.read(data);
		if (bytesReaded < field.getLength()) {
			throw new EOFException("Unexpected end of file");
		}
		return ByteBuffer.wrap(
				new byte[]{
						data[7], data[6], data[5], data[4],
						data[3], data[2], data[1], data[0]
				}).getDouble();
	}

	private Object readMemoField(DBFField field) throws IOException {
		Number nBlock =  null;
		if (field.getLength() == 10) {
			nBlock = DBFUtils.readNumericStoredAsText(this.dataInputStream, field.getLength());
		}
		else {
			nBlock = DBFUtils.readLittleEndianInt(this.dataInputStream);
		}
		if (this.memoFile != null && nBlock != null) {
			return memoFile.readData(nBlock.intValue(), field.getType());
		}
		return null;
	}

	/**
	 * Safely skip bytesToSkip bytes (in some bufferd scenarios skip doesn't really skip all requested bytes)
	 * @param bytesToSkip number of bytes to skip
	 * @throws IOException if some IO error happens
	 */
	protected void skip(int bytesToSkip) throws IOException {
		DBFUtils.skip(this.dataInputStream, bytesToSkip);
	}
	/**
	 * Skip records from reading. Treat "deleted" records as normal records.
	 * @param recordsToSkip Number of records to skip.
	 * @throws IOException if some IO error happens
	 */
	public void skipRecords(int recordsToSkip) throws IOException {
		if (showDeletedRows) {
			skip(recordsToSkip * this.header.recordLength);
			this.internalRecord+=recordsToSkip;
			this.returnedRecords+=recordsToSkip;
		}
		else {
			for (int i = 0; i < recordsToSkip; i++) {
				nextRecord();
			}
		}
	}

	protected DBFHeader getHeader() {
		return this.header;
	}
	/**
	 * Determine if character fields should be right trimmed (default true)
	 * @return true if data is right trimmed
	 */
	public boolean isTrimRightSpaces() {
		return this.trimRightSpaces;
	}

	/**
	 * Determine if character fields should be right trimmed (default true)
	 * @param trimRightSpaces if reading fields should trim right spaces
	 */
	public void setTrimRightSpaces(boolean trimRightSpaces) {
		this.trimRightSpaces = trimRightSpaces;
	}

	

	/**
	 * Sets the memo file (DBT or FPT) where memo fields will be readed.
	 * If no file is provided, then this fields will be null.
	 * @param file the file containing the memo data
	 */
	public void setMemoFile(File file) {
		if (this.memoFile != null) {
			throw new IllegalStateException("Memo file is already setted");
		}
		setMemoFile(file, file.length() < (8*1024*1024));
	}
	
	/**
	 * Sets the memo file (DBT or FPT) where memo fields will be readed.
	 * If no file is provided, then this fields will be null.
	 * @param file the file containing the memo data
	 * @param inMemory if the memoFile shoud be loaded in memory (caution, it may hang your jvm if memo file is too big)
	 */
	public void setMemoFile(File file, boolean inMemory) {
		if (this.memoFile != null) {
			throw new IllegalStateException("Memo file is already setted");
		}
		if (!file.exists()){
			throw new DBFException("Memo file " + file.getName() + " not exists");
		}
		if (!file.canRead()) {
			throw new DBFException("Cannot read Memo file " + file.getName());
		}
		this.memoFile = new DBFMemoFile(file, this.getCharset());
	}

	@Override
	public void close() {
		this.closed = true;
		DBFUtils.close(this.dataInputStream);
		DBFUtils.close(this.memoFile);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(128);
		sb.append(this.header.getYear()).append("/");
		sb.append(this.header.getMonth()).append("/");
		sb.append(this.header.getDay()).append("\n");
		sb.append("Total records: ").append(this.header.numberOfRecords).append("\n");
		sb.append("Header length: ").append(this.header.headerLength).append("\n");
		sb.append("Columns:\n");
		for (DBFField field : this.header.fieldArray) {
			sb.append(field.getName());
			sb.append("\n");
		}
		return sb.toString();
	}


	protected int getEstimatedOutputSize() {
		return this.getHeader().numberOfRecords * this.getHeader().recordLength;
	}
}
