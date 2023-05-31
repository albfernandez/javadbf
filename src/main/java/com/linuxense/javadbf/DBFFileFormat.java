package com.linuxense.javadbf;

public enum DBFFileFormat {
	COMPATIBLE((byte) 0x3),
	ADVANCED((byte) 0x4);
	
	private byte signature;
	private DBFFileFormat(byte signature) {
		this.signature = signature;
	}
	public byte getSignature() {
		return this.signature;
	}
}
