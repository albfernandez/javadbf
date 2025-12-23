package com.linuxense.javadbf.bug85float;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import com.linuxense.javadbf.testutils.DbfToTxtTest;

public class Bug85Test {

	public Bug85Test() {
		super();
	}
	
	@Test
	public void printFile() throws FileNotFoundException {
		File home = new File(System.getProperty("user.home"));
		File f = new File(home, "javadbf/PRODUTOS.DBF");
		Assumptions.assumeTrue(f.exists());
		DbfToTxtTest.parseFileCountRecords(f);
	}
}
