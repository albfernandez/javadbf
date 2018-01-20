/*

(C) Copyright 2017 Alberto Fernández <infjaf@gmail.com>

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

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * Data containdes in a row of the DBF file.
 * 
 * It has methods to ease obtain data.
 *
 */
public class DBFRow {

	private Object[] data;
	private Map<String, Integer> mapcolumnNames;
	private DBFField[] fields;

	protected DBFRow(Object[] data, Map<String, Integer> mapcolumnNames, DBFField[] fields) {
		super();
		this.data = data;
		this.mapcolumnNames = mapcolumnNames;
		this.fields = fields;
	}

	private int getColumnIndex(String columnName) {
		Objects.requireNonNull(columnName);
		String key = columnName.toLowerCase();
		Integer index = mapcolumnNames.get(key);
		if (index == null) {
			throw new DBFFieldNotFoundException("No field found for:" + columnName);
		}
		return index.intValue();
	}

	/**
	 * Check if the record is deleted. if you pass true on showDeletedRows to
	 * DBFReader constructor, deleted records are retrieved and this method allows
	 * you to check if the record is valid or deleted
	 * 
	 * @return true if the record is deleted
	 */
	public boolean isDeleted() {
		return "deleted".equals(this.fields[0].getName()) && getBoolean("deleted");
	}

	/**
	 * Read the column as original type
	 * 
	 * @param columnName
	 *            columnName
	 * @return the original value unconverted
	 */
	public Object getObject(String columnName) {
		return getObject(getColumnIndex(columnName));
	}

	/**
	 * Read the column as original type
	 * 
	 * @param columnIndex
	 *            columnIndex
	 * @return the original value unconverted
	 */
	public Object getObject(int columnIndex) {
		return data[columnIndex];
	}

	/**
	 * Reads the data as string
	 * 
	 * @param columnName
	 *            columnName
	 * @return the value converted to String
	 */
	public String getString(String columnName) {
		return getString(getColumnIndex(columnName));
	}

	/**
	 * Reads the data as string
	 * 
	 * @param columnIndex
	 *            columnIndex
	 * @return the value converted to String
	 */
	public String getString(int columnIndex) {
		if (columnIndex < 0 || columnIndex >= data.length) {
			throw new IllegalArgumentException("Invalid index field: (" + columnIndex+"). Valid range is 0 to " + (data.length - 1));			
		}
		Object fieldValue = data[columnIndex];
		if (fieldValue == null) {
			return null;
		}
		if (fieldValue instanceof String) {
			return (String) fieldValue;
		}
		return fieldValue.toString();
	}

	/**
	 * Reads the data as BigDecimal
	 * 
	 * @param columnName
	 *            columnName
	 * @return the data as BigDecimal
	 */
	public BigDecimal getBigDecimal(String columnName) {
		return getBigDecimal(getColumnIndex(columnName));
	}

	/**
	 * Reads the data as BigDecimal
	 * 
	 * @param columnIndex
	 *            columnIndex
	 * @return the data as BigDecimal
	 */
	public BigDecimal getBigDecimal(int columnIndex) {
		Object fieldValue = data[columnIndex];
		if (fieldValue == null) {
			return null;
		}
		if (fieldValue instanceof BigDecimal) {
			return (BigDecimal) fieldValue;
		}
		throw new DBFException("Unsupported type for BigDecimal at column:" + columnIndex + " "
				+ fieldValue.getClass().getCanonicalName());
	}

	/**
	 * Reads the data as Boolean
	 * 
	 * @param columnName
	 *            columnName
	 * @return the data as Boolean
	 */
	public boolean getBoolean(String columnName) {
		return getBoolean(getColumnIndex(columnName));
	}

	/**
	 * Reads the data as Boolean
	 * 
	 * @param columnIndex
	 *            columnIndex
	 * @return the data as Boolean
	 */
	public boolean getBoolean(int columnIndex) {
		Object fieldValue = data[columnIndex];
		if (fieldValue == null) {
			return Boolean.FALSE;
		}
		if (fieldValue instanceof Boolean) {
			return (Boolean) fieldValue;
		}
		throw new DBFException("Unsupported type for Boolean at column:" + columnIndex + " "
				+ fieldValue.getClass().getCanonicalName());
	}

	/**
	 * Reads the data as byte[]
	 * 
	 * @param columnName
	 *            columnName
	 * @return the data as byte[]
	 */

	public byte[] getBytes(String columnName) {
		return getBytes(getColumnIndex(columnName));
	}

	/**
	 * Reads the data as byte[]
	 * 
	 * @param columnIndex
	 *            columnIndex
	 * @return the data as Boolean
	 */
	public byte[] getBytes(int columnIndex) {
		Object fieldValue = data[columnIndex];
		if (fieldValue == null) {
			return null;
		}
		if (fieldValue instanceof byte[]) {
			return (byte[]) fieldValue;
		}
		throw new DBFException("Unsupported type for byte[] at column:" + columnIndex + " "
				+ fieldValue.getClass().getCanonicalName());
	}

	/**
	 * Reads the data as Date
	 * 
	 * @param columnName
	 *            columnName
	 * @return the data as Date
	 */

	public Date getDate(String columnName) {
		return getDate(getColumnIndex(columnName));
	}

	/**
	 * Reads the data as Date
	 * 
	 * @param columnIndex
	 *            columnIndex
	 * @return the data as Date
	 */
	public Date getDate(int columnIndex) {
		Object fieldValue = data[columnIndex];
		if (fieldValue == null) {
			return null;
		}
		if (fieldValue instanceof Date) {
			return (Date) fieldValue;
		}
		throw new DBFException(
				"Unsupported type for Date at column:" + columnIndex + " " + fieldValue.getClass().getCanonicalName());
	}

	/**
	 * Reads the data as Double
	 * 
	 * @param columnName
	 *            columnName
	 * @return the data as Double
	 */

	public double getDouble(String columnName) {
		return getDouble(getColumnIndex(columnName));
	}

	/**
	 * Reads the data as Double
	 * 
	 * @param columnIndex
	 *            columnIndex
	 * @return the data as Double
	 */
	public double getDouble(int columnIndex) {
		Object fieldValue = data[columnIndex];
		if (fieldValue == null) {
			return 0.0;
		}
		if (fieldValue instanceof Number) {
			return ((Number) fieldValue).doubleValue();
		}
		throw new DBFException("Unsupported type for Number at column:" + columnIndex + " "
				+ fieldValue.getClass().getCanonicalName());
	}

	/**
	 * Reads the data as Float
	 * 
	 * @param columnName
	 *            columnName
	 * @return the data as Float
	 */
	public float getFloat(String columnName) {
		return getFloat(getColumnIndex(columnName));
	}

	/**
	 * Reads the data as Float
	 * 
	 * @param columnIndex
	 *            columnIndex
	 * @return the data as Float
	 */
	public float getFloat(int columnIndex) {
		Object fieldValue = data[columnIndex];
		if (fieldValue == null) {
			return 0.0f;
		}
		if (fieldValue instanceof Number) {
			return ((Number) fieldValue).floatValue();
		}
		throw new DBFException("Unsupported type for Number at column:" + columnIndex + " "
				+ fieldValue.getClass().getCanonicalName());
	}

	/**
	 * Reads the data as int
	 * 
	 * @param columnName
	 *            columnName
	 * @return the data as int
	 */
	public int getInt(String columnName) {
		return getInt(getColumnIndex(columnName));
	}

	/**
	 * Reads the data as int
	 * 
	 * @param columnIndex
	 *            columnIndex
	 * @return the data as int
	 */
	public int getInt(int columnIndex) {
		Object fieldValue = data[columnIndex];
		if (fieldValue == null) {
			return 0;
		}
		if (fieldValue instanceof Number) {
			return ((Number) fieldValue).intValue();
		}
		throw new DBFException("Unsupported type for Number at column:" + columnIndex + " "
				+ fieldValue.getClass().getCanonicalName());
	}

	/**
	 * Reads the data as long
	 * 
	 * @param columnName
	 *            columnName
	 * @return the data as long
	 */
	public long getLong(String columnName) {
		return getLong(getColumnIndex(columnName));
	}

	/**
	 * Reads the data as long
	 * 
	 * @param columnIndex
	 *            columnIndex
	 * @return the data as long
	 */
	public long getLong(int columnIndex) {
		Object fieldValue = data[columnIndex];
		if (fieldValue == null) {
			return 0;
		}
		if (fieldValue instanceof Number) {
			return ((Number) fieldValue).longValue();
		}
		throw new DBFException("Unsupported type for Number at column:" + columnIndex + " "
				+ fieldValue.getClass().getCanonicalName());
	}

}
