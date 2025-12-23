package com.linuxense.javadbf.testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.linuxense.javadbf.utils.DBFUtils;

public final class FileUtils {
	
	private FileUtils() {
		throw new AssertionError("No instances allowed");
	}
	public static void copyFile(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	    	DBFUtils.close(is);
	        DBFUtils.close(os);
	    }
	}
}
