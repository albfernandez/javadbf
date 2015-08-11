package com.linuxense.javadbf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
	public void testToBoolean() {
		assertTrue(Utils.toBoolean((byte) 't'));
		assertTrue(Utils.toBoolean((byte) 'T'));
		assertTrue(Utils.toBoolean((byte) 'y'));
		assertTrue(Utils.toBoolean((byte) 'Y'));
		
		assertFalse(Utils.toBoolean((byte) 'f'));
		assertFalse(Utils.toBoolean((byte) 'F'));
		assertFalse(Utils.toBoolean((byte) 'n'));
		assertFalse(Utils.toBoolean((byte) 'N'));
		
		assertNull(Utils.toBoolean((byte) '?'));
		
	}
	
	@Test 
	public void testContains () {
		assertTrue(Utils.contains("test?test".getBytes(), (byte) '?'));
		assertFalse(Utils.contains("testtest".getBytes(), (byte) '?'));
		assertFalse(Utils.contains("".getBytes(), (byte) '?'));
		assertFalse(Utils.contains(null, (byte)'?'));
	}
	@Test
	public void doubleFormating() {
		// TODO
	}
	@Test
	public void testLittleEndian() {
		// TODO
	}
	@Test
	public void testreadLittleEndianInt() {
		// TODO
	}
	@Test
	public void testReadLittleEndianShort() {
		// TODO
	}
	@Test
	public void testPadding() {
		// TODO
	}
	@Test
	public void testRemoveSpaces() {
		assertEquals("123", new String(Utils.removeSpaces("   123".getBytes())));
		assertEquals("123", new String(Utils.removeSpaces("   123   ".getBytes())));
		assertEquals("123", new String(Utils.removeSpaces("123   ".getBytes())));
		assertEquals("123", new String(Utils.removeSpaces("123".getBytes())));
		assertEquals("", new String(Utils.removeSpaces("".getBytes())));
	}

}
