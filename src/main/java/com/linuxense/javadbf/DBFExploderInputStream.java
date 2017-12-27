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
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

class DBFExploderInputStream extends InputStream {

	private InputStream in;
	private Queue<Byte> queue =new LinkedList<Byte>();
	private int estimatedUncompressedSize = 0;
	
	DBFExploderInputStream(InputStream in) {
		this(in, 0);		
	}
	DBFExploderInputStream(InputStream in, int uncompressedSize) {
		super();
		this.in = in;
		this.estimatedUncompressedSize = uncompressedSize;
	}

	@Override
	public int read() throws IOException {
		if (this.queue.isEmpty()) {
			int readed = fillQueue();
			if (readed < 0) {
				return -1;
			}
		}
		return queue.poll();
	}

	private int fillQueue() throws IOException {
		ByteArrayOutputStream baos = getCompressedByteStream();
		if (baos.size() <= 0) {
			return -1;
		}
		byte[] compressedData = baos.toByteArray();

		int outputBufferSize = getAdjustedOutputSize(compressedData);
		
		byte[] decompressedData = new byte[outputBufferSize];
		int decompressed = DBFExploder.pkexplode(compressedData, DBFExploder.createInMemoryStorage(decompressedData), outputBufferSize);
		
		for (int i = 0; i < decompressed; i++) {
			queue.add(decompressedData[i]);
		}
		
		return decompressed;
	}

	protected int getAdjustedOutputSize(byte[] compressedData) {
		int outputBufferSize = estimatedUncompressedSize;
		if (outputBufferSize < compressedData.length) {
			outputBufferSize = compressedData.length * 6;
		}
		return outputBufferSize;
	}

	protected ByteArrayOutputStream getCompressedByteStream() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		byte[] buffer = new byte[4096];
		int readed = 0;
		while ( ( readed = this.in.read(buffer)) > 0) {
			baos.write(buffer, 0, readed);
		}
		return baos;
	}


}
