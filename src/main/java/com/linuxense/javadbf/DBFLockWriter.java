package com.linuxense.javadbf;


import java.io.File;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;


/**
 * DBFWriter which obtain a lock on the file prior writting on it.
 *
 */
public class DBFLockWriter extends DBFWriter {


	public DBFLockWriter(File dbfFile) {
		super(dbfFile);
	}

	public DBFLockWriter(File dbfFile, Charset charset) {
		super(dbfFile, charset);
	}

	@Override
	public void addRecord(Object[] values) {
		if (this.isClosed()) {
			throw new IllegalStateException("You can add records a closed DBFWriter");
		}
		try {
			FileLock lock = this.getRamdonAccessFile().getChannel().lock();
			super.addRecord(values);
			if (lock.isValid()) {
				lock.release();
			}
		}
		catch (IOException ioe) {
			throw new DBFException(ioe.getMessage(), ioe);
		}
	}

	@Override
	public void close() {
		if (this.isClosed()) {
			return;
		}

		try {
			FileLock lock = this.getRamdonAccessFile().getChannel().lock();
			super.close();
			if (lock.isValid()) {
				lock.release();
			}
		}
		catch (IOException ioe) {
			throw new DBFException(ioe.getMessage(), ioe);
		}
	}

}
