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
		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		byte[] buffer = new byte[4096];
		int readed = 0;
		while ( ( readed = this.in.read(buffer)) > 0) {
			baos.write(buffer, 0, readed);
		}
		if (baos.size() <= 0) {
			return -1;
		}
		byte[] compressedData = baos.toByteArray();
		
		int outputBufferSize = estimatedUncompressedSize;
		if (outputBufferSize < compressedData.length) {
			outputBufferSize = compressedData.length * 6;
		}
			
		
		byte[] decompressedData = new byte[outputBufferSize];
		int decompressed = DBFExploder.pkexplode(compressedData, decompressedData);
		
		for (int i = 0; i < decompressed; i++) {
			queue.add(decompressedData[i]);
		}
		
		return decompressed;
	}

	
}
