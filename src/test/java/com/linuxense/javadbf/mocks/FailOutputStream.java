package com.linuxense.javadbf.mocks;

import java.io.IOException;
import java.io.OutputStream;

public class FailOutputStream extends OutputStream {

	@Override
	public void write(int b) throws IOException {
		throw new IOException("Fail writting");		
	}

}
