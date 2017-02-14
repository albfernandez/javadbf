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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

public class DBFMemoFile {

	private File memoFile = null;
	private Charset charset = null;
	private int blockSize = 512;
	private int version = 3;
	
	protected DBFMemoFile(File memoFile, Charset charset) {
		this.memoFile = memoFile;
		this.charset = charset;
		readBlockSize();
	}
	private void readBlockSize() {
		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile(memoFile, "r");
			file.seek(4);
			this.blockSize = DBFUtils.readLittleEndianInt(file);
			if (this.blockSize == 0) {
				this.blockSize = 512;
			}
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		finally {
			DBFUtils.close(file);
		}
	}
	
	protected String readText(int block) {
		byte[] result = readBinary(block);
		if (result == null) {
			return null;
		}
		return new String(result, charset);
	}
	protected byte[] readBinary(int block) {
		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile(memoFile, "r");
			file.seek(this.blockSize*block);
			byte[] blockData = new byte[this.blockSize];			
			ByteArrayOutputStream baos = new ByteArrayOutputStream(this.blockSize);
			boolean end = false;
			int count = 0;
			while (!end && count < 10) {
				int endIndex = file.read(blockData);
				int initialIndex = 0;
				
				if (blockData[0] == (byte) 0xFF && blockData[1] == (byte) 0xFF && blockData[2] == 0x08 && blockData[3] == 0x00) {
					version = 4;
					initialIndex = 8;
					long oldpos = file.getFilePointer();
					file.seek(oldpos - blockSize + 4);					
					endIndex = Math.min(DBFUtils.readLittleEndianInt(file), endIndex);
					file.seek(oldpos);
				}				
				
				for (int i = initialIndex; i < endIndex; i++) {
					baos.write(blockData[i]);
					if (i < endIndex -2 && blockData[i+1] == 0x1A && blockData[i+2] == 0x1A){
						end = true;
						break;
					}
				}
				if (version == 4) {
					end = true;
				}
				count++;
				
			}
			return baos.toByteArray();
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		finally {
			DBFUtils.close(file);
		}
	}
}
