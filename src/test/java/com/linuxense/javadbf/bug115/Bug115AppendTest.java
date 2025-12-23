package com.linuxense.javadbf.bug115;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.linuxense.javadbf.DBFWriter;
import com.linuxense.javadbf.utils.DBFUtils;

public class Bug115AppendTest {
	
	public Bug115AppendTest() {
		super();
	}
	
	@Test
	public void testAppend() throws Exception {
		File orig = new File("src/test/resources/bug-115/bug115.dbf");
		
		File tmp = File.createTempFile("bug115", ".dbf");
		copyFile(orig, tmp);
		DBFWriter writer = null;
		
		try {
		
			writer = new DBFWriter(tmp);
			
			Object rowData[] = new Object[8];
			rowData[0] = "LOTE12345";
			rowData[1] = "PEDTEST";
			rowData[2] = "PEDTEST";
			rowData[3] = new Date();
			rowData[4] = "";
			rowData[5] = "";
			rowData[6] = new Integer(1);
			rowData[7] = "";
			
			writer.addRecord(rowData);
			
		}  finally {
			DBFUtils.close(writer);
			
		}
		
		tmp.delete();
	}

	private void copyFile(File orig, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new BufferedInputStream(new FileInputStream(orig));
			os = new BufferedOutputStream(new FileOutputStream(dest));
			byte[] data = new byte[16000];
			int read = 0;
			while((read = is.read(data)) > 0) {
				os.write(data, 0, read);;
			}
		}
		finally {
			DBFUtils.close(is);
			DBFUtils.close(os);
		}
	}
}
