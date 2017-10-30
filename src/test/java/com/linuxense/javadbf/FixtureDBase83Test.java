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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.testutils.AssertUtils;
import com.linuxense.javadbf.testutils.DbfToTxtTest;

public class FixtureDBase83Test {

	@Test
	public void test8b() throws Exception {
		File file = new File("src/test/resources/fixtures/dbase_83.dbf");
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(file));
			DBFReader reader = new DBFReader(inputStream);
			reader.setMemoFile(new File("src/test/resources/fixtures/dbase_83.dbt"));

			DBFHeader header = reader.getHeader();
			Assert.assertNotNull(header);
			Assert.assertEquals(15, header.fieldArray.length);
			Assert.assertEquals(67, header.numberOfRecords);

			DBFField[] fieldArray = header.fieldArray;

			int i = 0;

			AssertUtils.assertColumnDefinition(fieldArray[i++], "ID", DBFDataType.fromCode('N'), 19, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CATCOUNT", DBFDataType.fromCode('N'), 19, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "AGRPCOUNT", DBFDataType.fromCode('N'), 19, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PGRPCOUNT", DBFDataType.fromCode('N'), 19, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "ORDER", DBFDataType.fromCode('N'), 19, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CODE", DBFDataType.fromCode('C'), 50, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "NAME", DBFDataType.fromCode('C'), 100, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "THUMBNAIL", DBFDataType.fromCode('C'), 254, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "IMAGE", DBFDataType.fromCode('C'), 254, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PRICE", DBFDataType.fromCode('N'), 13, 2);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "COST", DBFDataType.fromCode('N'), 13, 2);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "DESC", DBFDataType.fromCode('M'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "WEIGHT", DBFDataType.fromCode('N'), 13, 2);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TAXABLE", DBFDataType.fromCode('L'), 1, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "ACTIVE", DBFDataType.fromCode('L'), 1, 0);

			DbfToTxtTest.export(reader, File.createTempFile("javadbf-test", ".txt"));

		} finally {
			DBFUtils.close(inputStream);
		}
	}
}

/*
Database: dbase_83.dbf
Type: (83) dBase III with memo file
Memo File: true
Records: 67

Fields:
Name             Type       Length     Decimal
------------------------------------------------------------------------------
ID               N          19         0         
CATCOUNT         N          19         0         
AGRPCOUNT        N          19         0         
PGRPCOUNT        N          19         0         
ORDER            N          19         0         
CODE             C          50         0         
NAME             C          100        0         
THUMBNAIL        C          254        0         
IMAGE            C          254        0         
PRICE            N          13         2         
COST             N          13         2         
DESC             M          10         0         
WEIGHT           N          13         2         
TAXABLE          L          1          0         
ACTIVE           L          1          0         

*/