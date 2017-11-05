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
