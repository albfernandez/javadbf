package com.linuxense.javadbf;

import org.junit.Assert;
import org.junit.Test;


public class DBFAligmentTest {
	
	@Test
	public void testValues() {
		Assert.assertEquals(2,DBFAlignment.values().length);
	}
	
	@Test
	public void testValueOf() {
		Assert.assertEquals(DBFAlignment.LEFT,DBFAlignment.valueOf("LEFT"));
	}

}
