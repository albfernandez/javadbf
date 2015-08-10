package com.linuxense.javadbf;


/**
 * The types supported by JavaDBF
 *
 */
public enum DBFDataType {
	UNKNOWN         ((byte) 0),
	/**
	 * Character data, padding with whitespaces.
	 */
	CHARACTER       ((byte) 'C'), 
	/**
	 * Date
	 */
	DATE            ((byte) 'D'), 
	/**
	 * Numeric data
	 */
	FLOATING_POINT  ((byte) 'F'),
	/**
	 * To store boolean values.
	 */
	LOGICAL         ((byte) 'L'),
	/**
	 * Memo
	 */
	MEMO            ((byte) 'M'),
	/**
	 * Numeric data
	 */
	NUMERIC         ((byte) 'N'),
	/**
	 * Numeric long
	 */
	LONG            ((byte) 'I');

	private byte code;

	private DBFDataType(byte code) {
		this.code = code;
	}
	/**
	 * Get the code as stored in the dbf file.
	 * @return the code
	 */	
	public byte getCode() {
		return this.code;
	}
	
	/**
	 * Gets the DBFDataType from the code used in the file
	 * @param cod 
	 * @return
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
