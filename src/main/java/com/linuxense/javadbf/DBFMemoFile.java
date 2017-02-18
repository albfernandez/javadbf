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
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Class for read memo files (DBT and FPT)
 */
public class DBFMemoFile {

	private File memoFile = null;
	private Charset charset = null;
	private int blockSize = 512;
	private boolean fpt = false;
	
	protected DBFMemoFile(File memoFile, Charset charset) {
		this.memoFile = memoFile;
		this.charset = charset;
		this.fpt = memoFile.getName().toLowerCase().endsWith(".fpt");
		readBlockSize();		
	}
	private void readBlockSize() {
		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile(memoFile, "r");			
					
			if (isFPT()) {
				file.seek(6);
				this.blockSize = file.readShort();				
			}
			else {
				file.seek(20);
				this.blockSize = DBFUtils.readLittleEndianShort(file);
			}
			if (this.blockSize == 0) {
				this.blockSize = 512;
			}
			
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		finally {
			DBFUtils.close(file);
		}
	}
	
	private boolean isFPT() {
		return this.fpt;
	}
	/**
	 * Only for testing purposes
	 * @param block position of first block of this field
	 * @return text contained in the block
	 */
	
	protected String readText(int block) {
		return (String) readData(block, DBFDataType.MEMO);
	}
	/**
	 * Only for testing purposes
	 * @param block postition of first block of this field 
	 * @return data in the block as byte
	 */
	protected byte[] readBinary(int block) {
		return (byte[]) readData(block, DBFDataType.BINARY);
	}
	
	protected Object readData(int block, DBFDataType type) {
		RandomAccessFile file = null;
		long blockStart = this.blockSize * (long) block;
		DBFDataType usedType = type;
		try {
			file = new RandomAccessFile(memoFile, "r");
			file.seek(blockStart);
			byte[] blockData = new byte[this.blockSize];
			ByteArrayOutputStream baos = new ByteArrayOutputStream(this.blockSize);
			boolean end = false;

			int itemSize = Integer.MAX_VALUE;
			boolean firstBlock = true;
			boolean checkForEndMark = true;
			while (!end) {
				int endIndex = file.read(blockData);
				if (endIndex <= 0) {
					break;
				}
				int initialIndex = 0;
				if (firstBlock && (isFPT() || isMagicDBase4(blockData))) {
					initialIndex = 8;
					checkForEndMark = false;
					if (isFPT()) {
						int intType = blockData[3];
						// 01 is text, other are binary						
						if (intType == 1) {
							usedType = DBFDataType.MEMO;
						}
						else if (intType == 2) {
							usedType = DBFDataType.BINARY;
						}
						else if (intType == 0) {
							usedType = DBFDataType.PICTURE;
						}
						
						itemSize = ByteBuffer.wrap(new byte[]{blockData[4], blockData[5], blockData[6], blockData[7]}).getInt();
					}
					else {
						itemSize = ByteBuffer.wrap(new byte[]{blockData[7], blockData[6], blockData[5], blockData[4]}).getInt() - 8;						
					}
					endIndex = Math.min(itemSize + 8, endIndex);
				}
				firstBlock = false;
				for (int i = initialIndex; i < endIndex && baos.size() < itemSize; i++) {
					baos.write(blockData[i]);
					if (checkForEndMark && i < endIndex -2 && blockData[i+1] == 0x1A && blockData[i+2] == 0x1A){
						end = true;
						break;
					}
					if (!checkForEndMark) {
						end = baos.size() >= itemSize;
					}
				}
			}
			byte[] data = baos.toByteArray();
			if (usedType != DBFDataType.MEMO) {
				return data;
			}
			return new String(data, charset);			
			
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		finally {
			DBFUtils.close(file);
		}
		
	}
	private boolean isMagicDBase4(byte[] blockData) {
		return blockData[0] == (byte) 0xFF && blockData[1] == (byte) 0xFF && blockData[2] == 0x08 && blockData[3] == 0x00;
	}
}
