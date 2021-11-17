package com.linuxense.javadbf.bug99deleteresult;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;

public class Bug99WrongDeleteResultTest {

	public Bug99WrongDeleteResultTest() {
		super();
	}

	@Test
	public void testDelete1() throws Exception {
		File file = new File("src/test/resources/test_delete.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream(file), true);
			DBFRow row;
			while ((row = reader.nextRow()) != null) {
				assertEquals(row.getBoolean("deleted"), row.isDeleted());
			}
		} finally {
			DBFUtils.close(reader);
		}

	}

	@Test
	public void testDelete2() throws Exception {
		File file = new File("src/test/resources/test_delete.dbf");
		int total = 0;
		int deleted = 0;
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream(file), true);
			DBFRow row;

			while ((row = reader.nextRow()) != null) {
				total++;
				if (row.isDeleted()) {
					deleted++;
				}
			}
		} finally {
			DBFUtils.close(reader);
		}
		assertEquals(3, total);
		assertEquals(1, deleted);
	}
}
