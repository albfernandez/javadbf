package com.linuxense.javadbf.bug102;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import com.linuxense.javadbf.testutils.DbfToTxtTest;

public class Bug102Test {

	public Bug102Test() {
		super();
	}

	@Test
	public void basicReadBug102() throws Exception {
		DBFReader reader = null;
		InputStream in = null;
		File input = new File("src/test/resources/bug-102/bug102.dbf");
		File memoFile = new File("src/test/resources/bug-102/bug102.dbt");
		try {
			in = new BufferedInputStream(new FileInputStream(input));
			reader = new DBFReader(in);
			reader.setMemoFile(memoFile);
			Object[] rowObjects = null;
			while ((rowObjects = reader.nextRecord()) != null) {
				for (int i = 0; i < rowObjects.length; i++) {
					Double dataNumber = (Double.parseDouble((String.valueOf(rowObjects[0]).trim())));
					System.out.println(new BigDecimal(dataNumber).toPlainString());
				}
			}

		} finally {
			DBFUtils.close(reader);
			DBFUtils.close(in);
		}
	}
	
	
	@Test
	public void print102toConsole() throws Exception {
		File input = new File("src/test/resources/bug-102/bug102.dbf");
		DbfToTxtTest.writeToConsole(input);
	}

	@Test
	public void basicReadBug102UsingNextRow() throws Exception {
		DBFReader dbfReader = null;
		InputStream in = null;
		File input = new File("src/test/resources/bug-102/bug102.dbf");
		File memoFile = new File("src/test/resources/bug-102/bug102.dbt");
		try {
			in = new BufferedInputStream(new FileInputStream(input));
			dbfReader = new DBFReader(in);
			dbfReader.setMemoFile(memoFile);
			DBFRow row = null;
			while ((row = dbfReader.nextRow()) != null) {
				System.out.println("row=" + row);
			}

		} finally {
			DBFUtils.close(dbfReader);
			DBFUtils.close(in);
		}
	}

}
