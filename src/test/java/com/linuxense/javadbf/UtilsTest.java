package com.linuxense.javadbf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.Test;


public class UtilsTest {

	private static final Charset CHARSET_ISO = StandardCharsets.ISO_8859_1;
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
		assertEquals(Boolean.TRUE, Utils.toBoolean((byte) 't'));
		assertEquals(Boolean.TRUE, Utils.toBoolean((byte) 'T'));
		assertEquals(Boolean.TRUE, Utils.toBoolean((byte) 'y'));
		assertEquals(Boolean.TRUE, Utils.toBoolean((byte) 'Y'));
		
		assertEquals(Boolean.FALSE, Utils.toBoolean((byte) 'f'));
		assertEquals(Boolean.FALSE, Utils.toBoolean((byte) 'F'));
		assertEquals(Boolean.FALSE, Utils.toBoolean((byte) 'n'));
		assertEquals(Boolean.FALSE, Utils.toBoolean((byte) 'N'));
		
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
	public void testTextPadding() throws UnsupportedEncodingException {
		assertEquals(
			"abc       ",
			new String(Utils.textPadding("abc", CHARSET_ISO, 10))
		);
		
		assertEquals(
			"a",
			new String(Utils.textPadding("abc", CHARSET_ISO, 1))
		);
		assertEquals(
			"001",
			new String(Utils.textPadding("1", CHARSET_ISO, 3, DBFAlignment.RIGHT, (byte) '0'))
		);
		
		//TODO Test extreme cases (null, negative, etc)

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
