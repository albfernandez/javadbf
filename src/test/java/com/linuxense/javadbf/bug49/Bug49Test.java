package com.linuxense.javadbf.bug49;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.utils.DBFUtils;

public class Bug49Test {

	public Bug49Test() {
		super();
	}

	@Test
	public void testBug49() throws Exception {
		DBFReader dbfReader = null;
		InputStream in = null;
		File input = new File ("src/test/resources/bug-49/student.dbf");
		try {
			in = new BufferedInputStream(new FileInputStream(input));
			dbfReader = new DBFReader(in);
			int fieldCount = dbfReader.getFieldCount();
			DBFRow row = null;
			int count=0;
			while ( ( row = dbfReader.nextRow() )  != null) {
				count++;
				for (int i = 0; i < fieldCount; i++) {
					row.getObject(i);
				}
			}
			Assertions.assertEquals(3, count);
		}
		finally {
			DBFUtils.close(dbfReader);
			DBFUtils.close(in);
		}
		
	}


}
