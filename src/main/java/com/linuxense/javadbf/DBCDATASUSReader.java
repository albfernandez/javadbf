package com.linuxense.javadbf;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 *  Importing data from DBC (compressed DBF) files into data frames. Please note that this is the file format used by the Brazilian Ministry of Health (DATASUS), and it is not related to the Microsoft FoxPro or CANdb DBC file formats.
 *	DATASUS is the name of the Department of Informatics of Brazilian Health System. It is the agency resposible for publishing Brazilian public healthcare data. Besides DATASUS, the Brazilian National Agency for Supplementary Health (ANS) also uses this file format for its public data.
 */

public class DBCDATASUSReader extends DBFReader {

	public DBCDATASUSReader(InputStream in) {
		this(in, null, false);
	}

	public DBCDATASUSReader(InputStream in, boolean showDeletedRows) {
		this(in, null, showDeletedRows);
	}

	public DBCDATASUSReader(InputStream in, Charset charset) {
		this(in, charset, false);
	}

	public DBCDATASUSReader(InputStream in, Charset charset, boolean showDeletedRows) {
		super(in, charset, showDeletedRows);
		initDBC();
	}

	private void initDBC() {
		try {
			int uncompressedSize = this.getHeader().numberOfRecords * this.getHeader().recordLength; 
			// Skip CRC
			skip(4);
			this.dataInputStream = new DataInputStream(new DBFExploderInputStream(this.inputStream, uncompressedSize));
			
		} catch (IOException e) {
			throw new DBFException(e);
		}

	}


}
