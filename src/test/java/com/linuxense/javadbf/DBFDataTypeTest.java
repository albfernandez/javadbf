package com.linuxense.javadbf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class DBFDataTypeTest {

	
	@Test
	public void testValueOf() {
		assertEquals(DBFDataType.CHARACTER,DBFDataType.valueOf("CHARACTER"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidCode() {
		assertNull(DBFDataType.fromCode((byte)1));
	}

}
