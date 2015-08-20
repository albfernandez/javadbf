package com.linuxsense.javadbf.mocks;

import java.io.IOException;
import java.io.InputStream;

public class FailInputStream extends InputStream{

	@Override
	public int read() throws IOException {
		throw new IOException ("fail to read");
	}

}
