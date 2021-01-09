package com.linuxense.javadbf.bug77;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.linuxense.javadbf.ReadDBFAssert;

public class Bug77NoHeaderRecordTerminatorTest {

	public Bug77NoHeaderRecordTerminatorTest() {
		super();
	}
	
	@Test
	public void testReadCountriesNoTerminator() throws  IOException {
		// This file is original continents.dbf tunned by hand to reproduce the bug
		File f = new File("src/test/resources/bug-77-no-header-record-terminator/continents_no_header_record_terminator.dbf");
		ReadDBFAssert.testReadDBFFile(f, 1, 7, false);
	}
	
	@Test
	public void testReadCountriesNullTerminator() throws  IOException {
		// This file is original continents.dbf tunned by hand to remove header record terminator.
		File f = new File("src/test/resources/bug-77-no-header-record-terminator/continents-null-header-record-terminator.dbf");
		ReadDBFAssert.testReadDBFFile(f, 1, 7, false);
	}
}
