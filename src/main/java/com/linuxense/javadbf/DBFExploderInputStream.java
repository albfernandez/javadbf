package com.linuxense.javadbf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

class DBFExploderInputStream extends InputStream {

	private InputStream in;
	private Queue<Byte> queue =new LinkedList<Byte>();
	
	DBFExploderInputStream(InputStream in) {
		super();
		this.in = in;		
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
		
		byte[] decompressedData = new byte[compressedData.length * 4];
		int decompressed = DBFExploder.pkexplode(compressedData, decompressedData);
		
		for (int i = 0; i < decompressed; i++) {
			queue.add(decompressedData[i]);
		}
		
		return decompressed;
	}

	
}
