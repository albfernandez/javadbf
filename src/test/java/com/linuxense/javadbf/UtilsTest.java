package com.linuxense.javadbf;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class UtilsTest {

	public UtilsTest () {
		super();
	}
	@Test
	public void testIsPureAscii() {
		assertTrue(Utils.isPureAscii("abcd"));
		assertFalse(Utils.isPureAscii("á"));
		assertFalse(Utils.isPureAscii("ñ"));
		assertTrue(Utils.isPureAscii(""));
		assertTrue(Utils.isPureAscii(null));
	}
	@Test 
	public void testContains () {
		
	}
	@Test
	public void doubleFormating() {
		
	}
	@Test
	public void testLittleEndian() {
		
	}
	@Test
	public void testreadLittleEndianInt() {
		
	}
	@Test
	public void testReadLittleEndianShort() {
		
	}
	@Test
	public void testPadding() {
		
	}
	@Test
	public void testTrimLeftSpaces() {
		
	}

}
