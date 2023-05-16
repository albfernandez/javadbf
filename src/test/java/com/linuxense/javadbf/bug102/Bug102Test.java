package com.linuxense.javadbf.bug102;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import com.linuxense.javadbf.testutils.DbfToTxtTest;

public class Bug102Test {

	public Bug102Test() {
		super();
	}

	@Test
	public void basicReadBug102() throws Exception {
		DBFReader reader = null;
		InputStream in = null;
		File input = new File("src/test/resources/bug-102/bug102.dbf");
		File memoFile = new File("src/test/resources/bug-102/bug102.dbt");
		try {
			in = new BufferedInputStream(new FileInputStream(input));
			reader = new DBFReader(in);
			reader.setMemoFile(memoFile);
			Object[] rowObjects = null;
			while ((rowObjects = reader.nextRecord()) != null) {
				for (int i = 0; i < rowObjects.length; i++) {
					Double dataNumber = (Double.parseDouble((String.valueOf(rowObjects[0]).trim())));
					System.out.println(new BigDecimal(dataNumber).toPlainString());
				}
			}

		} finally {
			DBFUtils.close(reader);
			DBFUtils.close(in);
		}
	}
	
	
	@Test
	public void print102toConsole() throws Exception {
		File input = new File("src/test/resources/bug-102/bug102.dbf");
		DbfToTxtTest.writeToConsole(input);
	}

	@Test
	public void basicReadBug102UsingNextRow() throws Exception {
		DBFReader dbfReader = null;
		InputStream in = null;
		File input = new File("src/test/resources/bug-102/bug102.dbf");
		File memoFile = new File("src/test/resources/bug-102/bug102.dbt");
		try {
			in = new BufferedInputStream(new FileInputStream(input));
			dbfReader = new DBFReader(in);
			dbfReader.setMemoFile(memoFile);
			DBFRow row = null;
			String[] columns = new String[] {
					"DataNumber",
					"TerminalDi",
					"CurveType",
					"FilterCoun",
					"ErrorMessa",
					"MaxPeakNoUnit",
					"MinPeakNoUnit",
					"T1",
					"T2",
					"Tp",
					"Td",
					"Tc",
					"Tz",
					"BetaDash",
					"FreqOsc",
					"Urms",
					"FreqAC",
					"Udc",
					"UpValue",
					"SpareF1",
					"SpareF2",
					"SpareF3"
			};
			while ((row = dbfReader.nextRow()) != null) {
				System.out.println("row=" + row);
				
				for(String column: columns) {
					System.out.println(column + ":" + row.getDouble(column));
				}
			}

		} finally {
			DBFUtils.close(dbfReader);
			DBFUtils.close(in);
		}
	}
	
	@Test
	@Ignore
	public void other102Test() throws Exception {
		
		
//		DataNumber:193801.0
//		TerminalDi:0.1
//		CurveType:1.0
//		FilterCoun:0.0
//		ErrorMessa:-0.0
//		MaxPeakNoUnit:6010.315256202716
//		MinPeakNoUnit:-0.029328347212897617 // -143.74
		
		DBFReader dbfReader = null;
		InputStream in = null;
		File input = new File("src/test/resources/bug-102/bug102.dbf");
		File memoFile = new File("src/test/resources/bug-102/bug102.dbt");
		try {
			in = new BufferedInputStream(new FileInputStream(input));
			dbfReader = new DBFReader(in);
			dbfReader.setMemoFile(memoFile);
			DBFRow row = null;
			
			double maxpeak = 0.0;
			double minpeak = 0.0;
			while ((row = dbfReader.nextRow()) != null) {
				
				System.out.println("MinPeakNoUnit=" + row.getDouble("MinPeakNoUnit"));
				System.out.println("MaxPeakNoUnit=" + row.getDouble("MaxPeakNoUnit"));
				System.out.println("MinPeakN=" + row.getString("MinPeak"));
				System.out.println("MaxPeak=" + row.getString("MaxPeak"));
				double dn = row.getDouble("DataNumber");
				if (Math.abs(dn) == 193801.0) {
					System.out.println(dn);
					minpeak = row.getDouble("MinPeakNoUnit");
					maxpeak = row.getDouble("MaxPeakNoUnit");
				}
			}
			System.out.println(minpeak + "(-143.74)");
			System.out.println(maxpeak + "(6010.315)");
			
			Assert.assertEquals(6010.31, maxpeak, 0.1);
			Assert.assertEquals(-143.74, minpeak, 0.1);

		} finally {
			DBFUtils.close(dbfReader);
			DBFUtils.close(in);
		}
	}
	
	@Test
	public void testprint() {
		byte[] data = new byte[] {-63, 7, -89, -48, 0, 0, 0, 0};
		StringBuilder sb = new StringBuilder() ;
		for(byte b: data) {
			if (sb.length() == 0) {
				sb.append("{");
			}
			else {
				sb.append(",");
			}
			sb.append(b);
		}
		sb.append("}");
		System.out.println(sb);
			
		
	}
	
	

}
