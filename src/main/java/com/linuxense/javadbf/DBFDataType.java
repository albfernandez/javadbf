/*

(C) Copyright 2015-2016 Alberto Fernández <infjaf@gmail.com>

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

/**
 * The types supported by JavaDBF
 */
public enum DBFDataType {
	/**
	 * Default unknown type
	 */
	UNKNOWN         ((byte) 0),
	/**
	 * Character data, padded with whitespaces.
	 */
	CHARACTER       ((byte) 'C', 1, 254, 0, true), 
	/**
	 * Date
	 */
	DATE            ((byte) 'D', 8, 8, 8, true), 
	/**
	 * Numeric data
	 */
	FLOATING_POINT  ((byte) 'F', 1, 20, 0, true),
	/**
	 * To store boolean values.
	 */
	LOGICAL         ((byte) 'L', 1, 1, 1, true),
	/**
	 * Memo
	 */
	MEMO            ((byte) 'M'),
	/**
	 * Numeric data
	 */
	NUMERIC         ((byte) 'N', 1, 18, 0, true),
	/**
	 * Numeric long (FoxPro)
	 */
	LONG            ((byte) 'I', 4, 4, 4, false),
	/**
	 * Currency type (FoxPro)
	 */
	CURRENCY        ((byte)'Y', 8, 8, 8, false),
	/**
	 * Timestamp type (FoxPro)
	 */
	TIMESTAMP 		((byte)'T', 8, 8, 8, false);

	
	private byte code;
	private int minSize;
	private int maxSize;
	private int defaultSize;
	private boolean writeSupported = false;

	
	
	private DBFDataType(byte code) {
		this.code = code;
	}
	private DBFDataType(byte code, int minSize, int maxSize, int defaultSize, boolean writeSupported) {
		this.code = code;
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.defaultSize = defaultSize;
		this.writeSupported = writeSupported;
	}
	/**
	 * Gets the code as stored in the dbf file.
	 * @return the code for this type
	 */	
	public byte getCode() {
		return this.code;
	}
	
	/**
	 * Gets the minimum size for this type
	 * @return minimum size for this type
	 */
	public int getMinSize() {
		return this.minSize;
	}
	/**
	 * Gets the maximum size for this type
	 * @return Maximum size for this type
	 */
	public int getMaxSize() {
		return this.maxSize;
	}
	/**
	 * Gets the default size for this type
	 * @return default size for this type
	 */
	public int getDefaultSize() {
		return this.defaultSize;
	}
	/**
	 * Gets if JavaDBF can write this type
	 * @return true if JavaDBF can write this type
	 */
	public boolean isWriteSupported() {
		return this.writeSupported;
	}
	/**
	 * Gets the DBFDataType from the code used in the file
	 * @param cod the code used by dbase
	 * @return The DBFDataType from the code used in the file
	 */
	public static DBFDataType fromCode(byte cod) {
		for (DBFDataType type: values()) {
			if (cod == type.code){
				return type;
			}
		}
		throw new IllegalArgumentException("Unknown data type:" + cod);
	}
	

}
