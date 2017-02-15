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
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

public class DBFMemoFile {

	private File memoFile = null;
	private Charset charset = null;
	private int blockSize = 512;
	
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
		return new String(result, charset);
	}
	protected byte[] readBinary(int block) {
		RandomAccessFile file = null;
		int version = 3;
		try {			
			file = new RandomAccessFile(memoFile, "r");
			file.seek(this.blockSize * (long) block);
			byte[] blockData = new byte[this.blockSize];			
			ByteArrayOutputStream baos = new ByteArrayOutputStream(this.blockSize);
			boolean end = false;

			int itemSize = Integer.MAX_VALUE;
			while (!end) {
				int endIndex = file.read(blockData);
				if (endIndex <= 0) {
					break;
				}
				int initialIndex = 0;
				
				if (version == 3 && blockData[0] == (byte) 0xFF && blockData[1] == (byte) 0xFF && blockData[2] == 0x08 && blockData[3] == 0x00) {
					version = 4;
					initialIndex = 8;
					long oldpos = file.getFilePointer();
					file.seek(oldpos - blockSize + 4);	
					itemSize = DBFUtils.readLittleEndianInt(file) - 8;
					endIndex = Math.min(itemSize + 8, endIndex);
					file.seek(oldpos);
				}				
				
				for (int i = initialIndex; i < endIndex && baos.size() < itemSize; i++) {
					baos.write(blockData[i]);
					if (version != 4 && i < endIndex -2 && blockData[i+1] == 0x1A && blockData[i+2] == 0x1A){
						end = true;
						break;
					}
				}
				if (version == 4) {
					end = baos.size() >= itemSize;
				}				
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
