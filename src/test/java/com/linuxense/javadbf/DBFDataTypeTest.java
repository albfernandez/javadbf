package com.linuxense.javadbf;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class DBFDataTypeTest {
	@Test
	public void testValues() {
		Assert.assertEquals(10,DBFDataType.values().length);
	}
	
	@Test
	public void testValueOf() {
		assertEquals(DBFDataType.CHARACTER,DBFDataType.valueOf("CHARACTER"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidCode() {
		assertNull(DBFDataType.fromCode((byte)1));
	}

}
