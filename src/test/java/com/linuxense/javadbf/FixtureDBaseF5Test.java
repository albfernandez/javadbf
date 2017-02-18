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

public class FixtureDBaseF5Test {

	@Test
	public void test31() throws Exception {
		File file = new File("src/test/resources/fixtures/dbase_f5.dbf");
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(file));
			DBFReader reader = new DBFReader(inputStream);
			reader.setMemoFile(new File("src/test/resources/fixtures/dbase_f5.fpt"));
			DBFHeader header = reader.getHeader();
			Assert.assertNotNull(header);
			Assert.assertEquals(59, header.fieldArray.length);
			Assert.assertEquals(975, header.numberOfRecords);
			DBFField[] fieldArray = header.fieldArray;

			int i = 0;

			AssertUtils.assertColumnDefinition(fieldArray[i++], "NF", DBFDataType.fromCode((byte) 'N'), 5, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "SEXE", DBFDataType.fromCode((byte) 'C'), 1, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "NOM", DBFDataType.fromCode((byte) 'C'), 20, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "COG1", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "COG2", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TELEFON", DBFDataType.fromCode((byte) 'C'), 9, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "RENOM", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "NFP", DBFDataType.fromCode((byte) 'N'), 5, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "NFM", DBFDataType.fromCode((byte) 'N'), 5, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "ARXN", DBFDataType.fromCode((byte) 'C'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "DATN", DBFDataType.fromCode((byte) 'D'), 8, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "LLON", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "MUNN", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "COMN", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PROV", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PAIN", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OFIC", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "ARXB", DBFDataType.fromCode((byte) 'C'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "DATB", DBFDataType.fromCode((byte) 'D'), 8, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "LLOB", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "MUNB", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "COMB", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PAIB", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "DRIB", DBFDataType.fromCode((byte) 'C'), 30, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "INAB", DBFDataType.fromCode((byte) 'C'), 30, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OFTB", DBFDataType.fromCode((byte) 'C'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OFNB", DBFDataType.fromCode((byte) 'C'), 20, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "AXC1", DBFDataType.fromCode((byte) 'C'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "DTC1", DBFDataType.fromCode((byte) 'D'), 8, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "LLC1", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "NFC1", DBFDataType.fromCode((byte) 'N'), 5, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TCA1", DBFDataType.fromCode((byte) 'C'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OTC1", DBFDataType.fromCode((byte) 'C'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "ONC1", DBFDataType.fromCode((byte) 'C'), 20, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "AXC2", DBFDataType.fromCode((byte) 'C'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "DTC2", DBFDataType.fromCode((byte) 'D'), 8, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "LLC2", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "NFC2", DBFDataType.fromCode((byte) 'N'), 5, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TCA2", DBFDataType.fromCode((byte) 'C'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OTC2", DBFDataType.fromCode((byte) 'C'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "ONC2", DBFDataType.fromCode((byte) 'C'), 20, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "AXC3", DBFDataType.fromCode((byte) 'C'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "DTC3", DBFDataType.fromCode((byte) 'D'), 8, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "LLC3", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "NFC3", DBFDataType.fromCode((byte) 'N'), 5, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TCA3", DBFDataType.fromCode((byte) 'C'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OTC3", DBFDataType.fromCode((byte) 'C'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "ONC3", DBFDataType.fromCode((byte) 'C'), 20, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "ARXD", DBFDataType.fromCode((byte) 'C'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "DATD", DBFDataType.fromCode((byte) 'D'), 8, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "LLOD", DBFDataType.fromCode((byte) 'C'), 15, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OFTD", DBFDataType.fromCode((byte) 'C'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OFND", DBFDataType.fromCode((byte) 'C'), 20, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OBS1", DBFDataType.fromCode((byte) 'C'), 70, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OBS2", DBFDataType.fromCode((byte) 'C'), 70, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OBS3", DBFDataType.fromCode((byte) 'C'), 70, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OBS4", DBFDataType.fromCode((byte) 'C'), 70, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OBSE", DBFDataType.fromCode((byte) 'M'), 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "GHD", DBFDataType.fromCode((byte) 'C'), 15, 0);


			DbfToTxtTest.export(reader, File.createTempFile("javadbf-test", ".txt"));

		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

	}

}

/*



Database: dbase_f5.dbf
Type: (f5) FoxPro with memo file
Memo File: true
Records: 975

Fields:
Name             Type       Length     Decimal
------------------------------------------------------------------------------
NF               N          5          0         
SEXE             C          1          0         
NOM              C          20         0         
COG1             C          15         0         
COG2             C          15         0         
TELEFON          C          9          0         
RENOM            C          15         0         
NFP              N          5          0         
NFM              N          5          0         
ARXN             C          10         0         
DATN             D          8          0         
LLON             C          15         0         
MUNN             C          15         0         
COMN             C          15         0         
PROV             C          15         0         
PAIN             C          15         0         
OFIC             C          15         0         
ARXB             C          10         0         
DATB             D          8          0         
LLOB             C          15         0         
MUNB             C          15         0         
COMB             C          15         0         
PAIB             C          15         0         
DRIB             C          30         0         
INAB             C          30         0         
OFTB             C          10         0         
OFNB             C          20         0         
AXC1             C          10         0         
DTC1             D          8          0         
LLC1             C          15         0         
NFC1             N          5          0         
TCA1             C          10         0         
OTC1             C          10         0         
ONC1             C          20         0         
AXC2             C          10         0         
DTC2             D          8          0         
LLC2             C          15         0         
NFC2             N          5          0         
TCA2             C          10         0         
OTC2             C          10         0         
ONC2             C          20         0         
AXC3             C          10         0         
DTC3             D          8          0         
LLC3             C          15         0         
NFC3             N          5          0         
TCA3             C          10         0         
OTC3             C          10         0         
ONC3             C          20         0         
ARXD             C          10         0         
DATD             D          8          0         
LLOD             C          15         0         
OFTD             C          10         0         
OFND             C          20         0         
OBS1             C          70         0         
OBS2             C          70         0         
OBS3             C          70         0         
OBS4             C          70         0         
OBSE             M          10         0         
GHD              C          15         0         
*/
