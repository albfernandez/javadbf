/*

(C) Copyright 2017 Alberto Fernández <infjaf@gmail.com>

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3.0 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library.  If not, see <http://www.gnu.org/licenses/>.

*/
package com.linuxense.javadbf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;



public class UtilsTest {

	private static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
	private static final Charset UTF8 = StandardCharsets.UTF_8;
	public UtilsTest () {
		super();
	}
	@Test
	public void testIsPureAscii() {
		assertTrue(DBFUtils.isPureAscii("abcd"));
		assertFalse(DBFUtils.isPureAscii("á"));
		assertFalse(DBFUtils.isPureAscii("ñ"));
		assertTrue(DBFUtils.isPureAscii(""));
		assertTrue(DBFUtils.isPureAscii((String) null));
	}
	@Test
	public void testToBoolean() {
		assertEquals(Boolean.TRUE, DBFUtils.toBoolean((byte) 't'));
		assertEquals(Boolean.TRUE, DBFUtils.toBoolean((byte) 'T'));
		assertEquals(Boolean.TRUE, DBFUtils.toBoolean((byte) 'y'));
		assertEquals(Boolean.TRUE, DBFUtils.toBoolean((byte) 'Y'));
		
		assertEquals(Boolean.FALSE, DBFUtils.toBoolean((byte) 'f'));
		assertEquals(Boolean.FALSE, DBFUtils.toBoolean((byte) 'F'));
		assertEquals(Boolean.FALSE, DBFUtils.toBoolean((byte) 'n'));
		assertEquals(Boolean.FALSE, DBFUtils.toBoolean((byte) 'N'));
		
		assertNull(DBFUtils.toBoolean((byte) '?'));
		
	}
	
	@Test 
	public void testContains () {
		assertTrue(DBFUtils.contains("test?test".getBytes(), (byte) '?'));
		assertFalse(DBFUtils.contains("testtest".getBytes(), (byte) '?'));
		assertFalse(DBFUtils.contains("".getBytes(), (byte) '?'));
		assertFalse(DBFUtils.contains(null, (byte)'?'));
	}
	@Test
	public void doubleFormating() {
		Assert.assertEquals(
			" 0.00", 
			new String(DBFUtils.doubleFormating(new Double(0.0), Charset.defaultCharset(), 5, 2))
		);
		
		Assert.assertEquals(
				"10.00", 
				new String(DBFUtils.doubleFormating(new Double(10.0), Charset.defaultCharset(), 5, 2))
			);
		Assert.assertEquals(
				" 5.05", 
				new String(DBFUtils.doubleFormating(new Double(5.05), Charset.defaultCharset(), 5, 2))
			);
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
	public void testTextPadding()  {
		assertEquals(
			"abc       ",
			new String(DBFUtils.textPadding("abc", ISO_8859_1, 10), ISO_8859_1)
		);
		
		assertEquals(
			"a",
			new String(DBFUtils.textPadding("abc", ISO_8859_1, 1), ISO_8859_1)
		);
		assertEquals(
			"001",
			new String(DBFUtils.textPadding("1", ISO_8859_1, 3, DBFAlignment.RIGHT, (byte) '0'), ISO_8859_1)
		);
		
		//TODO Test extreme cases (null, negative, etc)

	}
	
	@Test
	public void testTextPaddingUTF8() {
		assertEquals(
				"Simón    ",
				new String(DBFUtils.textPadding("Simón", UTF8, 10), UTF8)
		);		
	}

	public void testInvalidUT8Padding() {
		assertEquals(
				"est ",
				new String(DBFUtils.textPadding("está", UTF8, 4), UTF8));
	}
	@Test
	public void testRemoveSpaces() {
		assertEquals("123", new String(DBFUtils.removeSpaces("   123".getBytes())));
		assertEquals("123", new String(DBFUtils.removeSpaces("   123   ".getBytes())));
		assertEquals("123", new String(DBFUtils.removeSpaces("123   ".getBytes())));
		assertEquals("123", new String(DBFUtils.removeSpaces("123".getBytes())));
		assertEquals("", new String(DBFUtils.removeSpaces("".getBytes())));
	}

	@Test
	public void testRemoveNullBytes() {
		byte[] byteArray = {0x00, 0x31, 0x00, 0x32, 0x33, 0x00}; // will nulls
		assertEquals("123", new String(DBFUtils.removeNullBytes(byteArray)));
	}


	@Test
	public void testTrimRightSpaces() {
		assertEquals("123", new String(DBFUtils.trimRightSpaces("123".getBytes())));
		assertEquals("123", new String(DBFUtils.trimRightSpaces("123  ".getBytes())));
		assertEquals("  123", new String(DBFUtils.trimRightSpaces("  123  ".getBytes())));
		assertEquals("  123", new String(DBFUtils.trimRightSpaces("  123".getBytes())));
		assertEquals("", new String(DBFUtils.trimRightSpaces("    ".getBytes())));
		assertEquals("", new String(DBFUtils.trimRightSpaces(null)));
		
	}
	
	
	private class Pair {
		byte[] data;
		double expected;
		
		private Pair(byte[] data, double expected) {
			this.data = data;
			this.expected = expected;
		}
	}
	
	
	@Test
	public void testDoublesDouble() throws Exception {
		List<Pair> list = new ArrayList<Pair>();
		list.add(new Pair(new byte[] {-128,0,0,0,0,0,0,0}, 0.0));
		list.add(new Pair(new byte[] {0,0,0,0,0,0,0,0}, 0.0));
		list.add(new Pair(new byte[] {-65,-16,0,0,0,0,0,0}, 1.0));
		list.add(new Pair(new byte[] {-64,-73,122,80,-76,-95,104,-121}, 6010.315256202716));
		list.add(new Pair(new byte[] {63,-98,8,64,16,-128,33,-113}, -0.029328347212897617));
		list.add(new Pair(new byte[] {-65,15,-54,102,-98,53,-91,-17}, 6.063581147481764E-5));
		list.add(new Pair(new byte[] {-64,90,98,-125,120,90,126,-7}, 105.5392742999792));
		list.add(new Pair(new byte[] {-65,-59,-10,25,-76,122,28,-53}, 0.17157288849214178));
		list.add(new Pair(new byte[] {-64,89,0,0,0,0,0,0}, 100.0));
		list.add(new Pair(new byte[] {-63,7,-88,64,0,0,0,0}, 193800.0));
		list.add(new Pair(new byte[] {-64,117,-32,0,0,0,0,0}, 350.0));
		list.add(new Pair(new byte[] {63,5,6,118,-63,114,-45,-118}, -4.010248101719075E-5));
		list.add(new Pair(new byte[] {-66,-80,-109,-69,93,70,-61,1}, 9.880708279169888E-7));
		list.add(new Pair(new byte[] {-65,5,-121,107,-35,50,-104,55}, 4.1063288926765735E-5));
		list.add(new Pair(new byte[] {-64,-98,109,31,-5,-6,-36,115}, 1947.2812346646358));
		list.add(new Pair(new byte[] {-64,42,-90,-117,42,-118,-51,29}, 13.325280503695973));
		
		for (Pair p : list) {
			Assert.assertEquals(p.expected, DBFUtils.toDoubleBigEndian(p.data), 0.01);
		}
	}

}
