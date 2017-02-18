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

import java.io.File;
import java.io.FileInputStream;

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.testutils.AssertUtils;
import com.linuxense.javadbf.testutils.DbfToTxtTest;

public class FixtureDBase03Test {
	@Test
	public void test8b() throws Exception {
		File file = new File("src/test/resources/fixtures/dbase_03.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream(file));
			
			DBFHeader header = reader.getHeader();
			Assert.assertNotNull(header);
			Assert.assertEquals(31, header.fieldArray.length);
			Assert.assertEquals(14, header.numberOfRecords);
			DBFField []fieldArray = header.fieldArray;
			
			int i = 0;
			
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Point_ID"  , DBFDataType.fromCode((byte) 'C'), 12, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Type"      , DBFDataType.fromCode((byte) 'C'), 20, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Shape"     , DBFDataType.fromCode((byte) 'C'), 20, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Circular_D", DBFDataType.fromCode((byte) 'C'), 20, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Non_circul", DBFDataType.fromCode((byte) 'C'), 60, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Flow_prese", DBFDataType.fromCode((byte) 'C'), 20, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Condition" , DBFDataType.fromCode((byte) 'C'), 20, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Comments"  , DBFDataType.fromCode((byte) 'C'), 60, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Date_Visit", DBFDataType.fromCode((byte) 'D'), 8 , 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Time"      , DBFDataType.fromCode((byte) 'C'), 10, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Max_PDOP"  , DBFDataType.fromCode((byte) 'N'), 5 , 1);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Max_HDOP"  , DBFDataType.fromCode((byte) 'N'), 5 , 1);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Corr_Type" , DBFDataType.fromCode((byte) 'C'), 36, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Rcvr_Type" , DBFDataType.fromCode((byte) 'C'), 36, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "GPS_Date"  , DBFDataType.fromCode((byte) 'D'), 8 , 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "GPS_Time"  , DBFDataType.fromCode((byte) 'C'), 10, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Update_Sta", DBFDataType.fromCode((byte) 'C'), 36, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Feat_Name" , DBFDataType.fromCode((byte) 'C'), 20, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Datafile"  , DBFDataType.fromCode((byte) 'C'), 20, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Unfilt_Pos", DBFDataType.fromCode((byte) 'N'), 10, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Filt_Pos"  , DBFDataType.fromCode((byte) 'N'), 10, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Data_Dicti", DBFDataType.fromCode((byte) 'C'), 20, 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "GPS_Week"  , DBFDataType.fromCode((byte) 'N'), 6 , 0);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "GPS_Second", DBFDataType.fromCode((byte) 'N'), 12, 3);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "GPS_Height", DBFDataType.fromCode((byte) 'N'), 16, 3);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Vert_Prec" , DBFDataType.fromCode((byte) 'N'), 16, 1);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Horz_Prec" , DBFDataType.fromCode((byte) 'N'), 16, 1);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Std_Dev"   , DBFDataType.fromCode((byte) 'N'), 16, 6);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Northing"  , DBFDataType.fromCode((byte) 'N'), 16, 3);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Easting"   , DBFDataType.fromCode((byte) 'N'), 16, 3);      
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Point_ID"  , DBFDataType.fromCode((byte) 'N'), 9 , 0);      
			
			DbfToTxtTest.export(reader, File.createTempFile("javadbf-test", ".txt"));
			
		} finally {
			DBFUtils.close(reader);
		}

	}
}




/*

Database: dbase_03.dbf
Type: (03) dBase III without memo file
Memo File: false
Records: 14

Fields:
Name             Type       Length     Decimal
------------------------------------------------------------------------------
Point_ID         C          12         0         
Type             C          20         0         
Shape            C          20         0         
Circular_D       C          20         0         
Non_circul       C          60         0         
Flow_prese       C          20         0         
Condition        C          20         0         
Comments         C          60         0         
Date_Visit       D          8          0         
Time             C          10         0         
Max_PDOP         N          5          1         
Max_HDOP         N          5          1         
Corr_Type        C          36         0         
Rcvr_Type        C          36         0         
GPS_Date         D          8          0         
GPS_Time         C          10         0         
Update_Sta       C          36         0         
Feat_Name        C          20         0         
Datafile         C          20         0         
Unfilt_Pos       N          10         0         
Filt_Pos         N          10         0         
Data_Dicti       C          20         0         
GPS_Week         N          6          0         
GPS_Second       N          12         3         
GPS_Height       N          16         3         
Vert_Prec        N          16         1         
Horz_Prec        N          16         1         
Std_Dev          N          16         6         
Northing         N          16         3         
Easting          N          16         3         
Point_ID         N          9          0         

*/