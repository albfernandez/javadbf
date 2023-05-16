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

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;



// TODO Rename to DBFUtilsTest
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
	
	
	@Test
	@Ignore
	public void testConverterDoublePostive() throws Exception {
		double expected = 193786.00;
		byte[] data = new byte[] {-63, 7, -89, -48, 0, 0, 0, 0};
		
		System.out.println(toHexString(data));
		System.out.println(toBinaryString(data));
		
		double calculated = convertBigInteger(data);
		Assert.assertEquals(expected, calculated, 0.01);
	}
	
	@Test
	@Ignore
	public void testConverterDoubleNegative() throws Exception {
//		MaxPeakNoUnit {-64,-73,122,80,-76,-95,104,-121}; -6010.315256202716
//		MinPeakNoUnit {63,-98,8,64,16,-128,33,-113}; 0.029328347212897617
		
		double expected = -143.74;
		byte[] data = new byte[]  {63,-98,8,64,16,-128,33,-113};
		System.out.println(toHexString(data));
		System.out.println(toBinaryString(data));
		
		
		double calculated = convertBigInteger(data);
		Assert.assertEquals(expected, calculated, 0.01);
	}
	
	@Test
	@Ignore
	//Tz {-64,90,98,-125,120,90,126,-7}; -105.5392742999792
	public void testConverterDouble2() throws Exception {
		double expected = 105.539;
		byte[] data = new byte[]  {-64,90,98,-125,120,90,126,-7};
		System.out.println(toHexString(data));
		System.out.println(toBinaryString(data));
		
		//405A6283785A7EF9
		//c05a6283785a7ef9
		double calculated = convertBigInteger(data);
		Assert.assertEquals(expected, calculated, 0.01);
	}
	
	
	@Test
	public void testDoubles() throws Exception {
		
		List<Double> expectedList = new ArrayList<Double>(Arrays.asList(193786.00, 193786.00,  105.539, -143.74, -110488.58));
		List<byte[]> rawDataList = new ArrayList<byte[]>(Arrays.asList(
			new byte[] {-63, 7, -89, -48, 0, 0, 0, 0},
			new byte[] {65, 7, -89, -48, 0, 0, 0, 0},
			new byte[] {-64,90,98,-125,120,90,126,-7},
			new byte[] {63,-98,8,64,16,-128,33,-113},
			new byte[] {63,5,6,118,-63,114,-45,-118}			
		));

		for (int i = 0; i < expectedList.size(); i++) {
			double expected = expectedList.get(i);
			byte[] data = rawDataList.get(i);
			double bigInteger = convertBigInteger(data);
			double byteBufferBE = convertByteBufferBigEndian(data);
			double byteBufferLE = convertByteBufferLittleEndian(data);
			System.out.println("---");			
			System.out.println(toHexString(data));
			System.out.println(toBinaryString(data));
			System.out.println(expected);
			System.out.println("biginteger  =" + bigInteger);
			System.out.println("bytebufferBE=" + byteBufferBE);	
			System.out.println("bytebufferLE=" + byteBufferLE);			
			
		}
		for (byte[] data : rawDataList) {
			System.out.println("raw=" + toBinaryString(data));
		}
		
		long a = 0xFFFFFFFF;
		a = a & new BigInteger(new byte[] {63,-98,8,64,16,-128,33,-113}).longValue();
		a = a & new BigInteger(new byte[] {63,5,6,118,-63,114,-45,-118}).longValue();
		System.out.println("long=" + Long.toBinaryString(a));
		
		
	}
	
	/*
	 * MaxPeakNoUnit {-64,-73,122,80,-76,-95,104,-121}; 6010.315256202716
MinPeakNoUnit {63,-98,8,64,16,-128,33,-113}; -0.029328347212897617
T1 {-65,15,-54,102,-98,53,-91,-17}; 6.063581147481764E-5
T2 {-128,0,0,0,0,0,0,0}; -0.0
Tp {-128,0,0,0,0,0,0,0}; -0.0
Td {-128,0,0,0,0,0,0,0}; -0.0
Tc {-128,0,0,0,0,0,0,0}; -0.0
Tz {-64,90,98,-125,120,90,126,-7}; 105.5392742999792
BetaDash {-65,-59,-10,25,-76,122,28,-53}; 0.17157288849214178
FreqOsc {-128,0,0,0,0,0,0,0}; -0.0
Urms {-128,0,0,0,0,0,0,0}; -0.0
FreqAC {-128,0,0,0,0,0,0,0}; -0.0
Udc {-128,0,0,0,0,0,0,0}; -0.0
UpValue {-128,0,0,0,0,0,0,0}; -0.0
SpareF1 {-64,89,0,0,0,0,0,0}; 100.0
SpareF2 {0,0,0,0,0,0,0,0}; 0.0
SpareF3 {0,0,0,0,0,0,0,0}; 0.0
MinPeakNoUnit=0.029328347212897617
MaxPeakNoUnit=-6010.315256202716
MinPeakN=-143.74 A
MaxPeak=6.010 kA
	 */
	
	/*
	 * DataNumber {-63,7,-88,64,0,0,0,0}; 193800.0
ChannelNum {-65,-16,0,0,0,0,0,0}; 1.0
TerminalDi {-64,117,-32,0,0,0,0,0}; 350.0
CurveType {-65,-16,0,0,0,0,0,0}; 1.0
FilterCoun {-128,0,0,0,0,0,0,0}; -0.0
ErrorMessa {0,0,0,0,0,0,0,0}; 0.0
MaxPeakNoUnit {63,5,6,118,-63,114,-45,-118}; -4.010248101719075E-5
MinPeakNoUnit {-128,0,0,0,0,0,0,0}; -0.0
T1 {-66,-80,-109,-69,93,70,-61,1}; 9.880708279169888E-7
T2 {-65,5,-121,107,-35,50,-104,55}; 4.1063288926765735E-5
Tp {-128,0,0,0,0,0,0,0}; -0.0
Td {-128,0,0,0,0,0,0,0}; -0.0
Tc {-128,0,0,0,0,0,0,0}; -0.0
Tz {-64,-98,109,31,-5,-6,-36,115}; 1947.2812346646358
BetaDash {-64,42,-90,-117,42,-118,-51,29}; 13.325280503695973
FreqOsc {-128,0,0,0,0,0,0,0}; -0.0
Urms {-128,0,0,0,0,0,0,0}; -0.0
FreqAC {-128,0,0,0,0,0,0,0}; -0.0
Udc {-128,0,0,0,0,0,0,0}; -0.0
UpValue {-128,0,0,0,0,0,0,0}; -0.0
SpareF1 {-64,89,0,0,0,0,0,0}; 100.0
SpareF2 {0,0,0,0,0,0,0,0}; 0.0
SpareF3 {0,0,0,0,0,0,0,0}; 0.0
MinPeakNoUnit=-0.0
MaxPeakNoUnit=4.010248101719075E-5
MinPeakN=0.000 V
MaxPeak=-110.48 kV
	 */
	
	private double convertByteBufferLittleEndian(byte[] data) {
//		double d = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getDouble();
		ByteBuffer buffer = ByteBuffer.allocate(8);
//		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(data,0,data.length);
		buffer.order(ByteOrder.BIG_ENDIAN);
		double d = buffer.getDouble(0);
		return d;
	}
	private double convertByteBufferBigEndian(byte[] data) {
		double d = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).getDouble();
//		if (d!= 0.0) {
//			d = -d;
//		}
		return d;
	}
	
	private double convertBigInteger(byte[] data) {
		long longValue  = new BigInteger(data).longValue();
		double d =  Double.longBitsToDouble(longValue);
//		if (d != 0) {
//			return -d;
//		}
		return d;
//		return DBFUtils.toDouble(data);

	}
	//0011111100000101000001100111011011000001011100101101001110001010 <- memo
	//1100000011111010111110011000100101000111101011100001010001111011 <- calculado
	
	// 193786.00
	//1100000100000111101001111101000000000000000000000000000000000000 <- memo
	//0100000100000111101001111101000000000000000000000000000000000000 <-caculado
	
	
	// 105.539
	// 1100000001011010011000101000001101111000010110100111111011111001 <- memo
	// c05a6283785a7ef9
	// 0100000001011010011000101000001101111000010110100111111011111001 <- calculado
	// 405A6283785A7EF9
	
	//-143.74
	//3f9e08401080218f
	//C061F7AE147AE148
	/*
	1100000001011010011000101000001101111000010110100111111011111001
105.539
biginteger  =-105.5392742999792
bytebufferBE=-105.5392742999792
bytebufferLE=-105.5392742999792
	*/
	
	private String toBinaryString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
		for (int i = 0; i < Byte.SIZE * bytes.length; i++) {
			sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
		}
		return sb.toString();
	}
	private String toHexString(byte[] byteArray) {
	    StringBuffer hexStringBuffer = new StringBuffer();
	    for (int i = 0; i < byteArray.length; i++) {
	        hexStringBuffer.append(byteToHex(byteArray[i]));
	    }
	    return hexStringBuffer.toString();
	}
	private String byteToHex(byte num) {
	    char[] hexDigits = new char[2];
	    hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
	    hexDigits[1] = Character.forDigit((num & 0xF), 16);
	    return new String(hexDigits);
	}

}
