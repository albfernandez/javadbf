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
	CURRENCY        ((byte)'Y', 8, 8, 8, false);
	
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
	 * Get the code as stored in the dbf file.
	 * @return the code
	 */	
	public byte getCode() {
		return this.code;
	}
	
	
	public int getMinSize() {
		return this.minSize;
	}
	public int getMaxSize() {
		return this.maxSize;
	}
	public int getDefaultSize() {
		return this.defaultSize;
	}
	
	public boolean isWriteSupported() {
		return this.writeSupported;
	}
	/**
	 * Gets the DBFDataType from the code used in the file
	 * @param cod 
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
