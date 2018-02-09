package com.linuxense.javadbf.mocks;

import java.io.IOException;
import java.io.OutputStream;


public class NullOutputStream extends OutputStream {
    
	private long count = 0;
	

    /**
     * Constructor
     */
    public NullOutputStream() {
        super();
    }
    /**
     * Does nothing - output to <code>/dev/null</code>.
     * @param b The bytes to write
     * @param off The start offset
     * @param len The number of bytes to write
     */
    @Override
    public void write(byte[] b, int off, int len) {
        this.count+= len;
    }

    /**
     * Does nothing - output to <code>/dev/null</code>.
     * @param b The byte to write
     */
    @Override
    public void write(int b) {
        this.count++;
    }

    /**
     * Does nothing - output to <code>/dev/null</code>.
     * @param b The bytes to write
     * @throws IOException never
     */
    @Override
    public void write(byte[] b) throws IOException {
        if (b != null) {
        	this.count += b.length;
        }
    }
    
    public long getCount() {
    	return this.count;
    }

}