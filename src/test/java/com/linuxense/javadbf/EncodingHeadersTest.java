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

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

public class EncodingHeadersTest {
	
	
	@Test
	public void testAutodetectCharset() throws IOException {
		test(null);
	}
	
	@Test
	public void testForceCharset() throws IOException {
		Charset GBK = Charset.forName("GBK");
		test(GBK);
	}
	
	
	public void test(Charset charset) throws IOException {
		
		String fieldNames[] = new String[] {"设计编号", "审核信息", "是否打印"};
		String firstColumn[] = new String[] {				
				"201500256-059K-001-001牡丹江林口朝阳         ",
				"201500256-059K-001-002牡丹江林口朝阳         ",
				"201500257-053K-001-001牡丹江林口朝阳         ",
				"201500258-023K-001-011牡丹江林口朝阳         ",
				"201500258-023K-001-012牡丹江林口朝阳         ",
				"201500259-018K-001-013牡丹江林口朝阳         ",
				"201500259-018K-001-014牡丹江林口朝阳         ",
				"201500259-018K-001-016牡丹江林口朝阳         ",
				"201500260-018K-001-015牡丹江林口朝阳         ",
				"201500260-018K-001-017牡丹江林口朝阳         ",
				"201500256-059K-001-001牡丹江林口朝阳         ",
				"201500256-059K-001-001牡丹江林口朝阳         ",
				"201500256-059K-001-002牡丹江林口朝阳         ",
				"201500256-059K-001-002牡丹江林口朝阳         ",
				"201500257-053K-001-001牡丹江林口朝阳         ",
				"201500258-023K-001-011牡丹江林口朝阳         ",
				"201500258-023K-001-011牡丹江林口朝阳         ",
				"201500258-023K-001-012牡丹江林口朝阳         ",
				"201500258-023K-001-012牡丹江林口朝阳         ",
				"201500259-018K-001-013牡丹江林口朝阳         ",
				"201500259-018K-001-013牡丹江林口朝阳         ",
				"201500259-018K-001-014牡丹江林口朝阳         ",
				"201500259-018K-001-016牡丹江林口朝阳         ",
				"201500260-018K-001-015牡丹江林口朝阳         ",
				"201500260-018K-001-015牡丹江林口朝阳         ",
				"201500260-018K-001-015牡丹江林口朝阳         ",
				"201500260-018K-001-015牡丹江林口朝阳         ",
				"201500260-018K-001-017牡丹江林口朝阳         "
				
		};
		String secondColumn[] = new String[] {
			">经营区：商品林；起源：天然林；采伐类型：抚育伐；采伐方式：透光伐；经营措施：上方透光；经理小班：1                      ",
			">经营区：商品林；起源：天然林；采伐类型：抚育伐；采伐方式：透光伐；经营措施：上方透光；经理小班：1                      ",
			">经营区：限伐区；起源：人工林；采伐类型：抚育伐；采伐方式：生长伐；经营措施：定株抚育；经理小班：12                     ",
			">经营区：商品林；起源：人工林；采伐类型：抚育伐；采伐方式：生长伐；经营措施：定株抚育；经理小班：2/7                    ",
			">经营区：商品林；起源：人工林；采伐类型：抚育伐；采伐方式：生长伐；经营措施：定株抚育；经理小班：3/7                    ",
			">经营区：限伐区；起源：人工林；采伐类型：抚育伐；采伐方式：生长伐；经营措施：定株抚育；经理小班：5/9                    ",
			">经营区：限伐区；起源：人工林；采伐类型：抚育伐；采伐方式：生长伐；经营措施：定株抚育；经理小班：5                      ",
			">经营区：限伐区；起源：人工林；采伐类型：抚育伐；采伐方式：生长伐；经营措施：定株抚育；经理小班：9                      ",
			">经营区：商品林；起源：人工林；采伐类型：抚育伐；采伐方式：生长伐；经营措施：定株抚育；经理小班：5/9                    ",
			">经营区：商品林；起源：人工林；采伐类型：抚育伐；采伐方式：生长伐；经营措施：定株抚育；经理小班：15                     ",
			"抚育伐、透光伐；林口059K1；     标准：限伐区、一般生态公益林、                                                          ",
			"***经营区填写不正确：林口059K1 调查：商品林      标准：限伐区                                                           ",
			"抚育伐、透光伐；林口059K1；     标准：限伐区、一般生态公益林、                                                          ",
			"***经营区填写不正确：林口059K1 调查：商品林      标准：限伐区                                                           ",
			"抚育伐、生长伐；林口053K12；     标准：限伐区、一般生态公益林、                                                         ",
			"抚育伐、生长伐；林口023K2；     标准：商品林、一般用材林、                                                              ",
			"抚育伐、生长伐；林口023K7；     标准：商品林、一般用材林、                                                              ",
			"抚育伐、生长伐；林口023K3；     标准：商品林、一般用材林、                                                              ",
			"抚育伐、生长伐；林口023K7；     标准：商品林、一般用材林、                                                              ",
			"抚育伐、生长伐；林口018K5；     标准：限伐区、一般生态公益林、                                                          ",
			"抚育伐、生长伐；林口018K9；     标准：限伐区、一般生态公益林、                                                          ",
			"抚育伐、生长伐；林口018K5；     标准：限伐区、一般生态公益林、                                                          ",
			"抚育伐、生长伐；林口018K9；     标准：限伐区、一般生态公益林、                                                          ",
			"抚育伐、生长伐；林口018K5；     标准：限伐区、一般生态公益林、                                                          ",
			"***经营区填写不正确：林口018K5 调查：商品林      标准：限伐区                                                           ",
			"抚育伐、生长伐；林口018K9；     标准：限伐区、一般生态公益林、                                                          ",
			"***经营区填写不正确：林口018K9 调查：商品林      标准：限伐区                                                           ",
			"抚育伐、生长伐；林口018K15；     标准：商品林、一般用材林、                                                             "
		};
		
		DBFReader reader= null;
		try {
			reader = new DBFReader(new FileInputStream("src/test/resources/gbk.dbf"), charset);
			reader.setTrimRightSpaces(false);
			int numberOfFields = reader.getFieldCount();
			Assert.assertEquals(3, numberOfFields);
			for (int i = 0; i < numberOfFields; i++) {
				DBFField field = reader.getField(i);
				Assert.assertNotNull(field.getName());
				Assert.assertEquals(fieldNames[i], field.getName());
			}
			Object[] rowObject;
			int countedRows = 0;
			while ((rowObject = reader.nextRecord()) != null) {
				Assert.assertEquals(numberOfFields, rowObject.length);
				Assert.assertTrue(rowObject[0] instanceof String);
				Assert.assertTrue(rowObject[1] instanceof String);

				Assert.assertEquals(firstColumn[countedRows], rowObject[0]);
				Assert.assertEquals(secondColumn[countedRows], rowObject[1]);
				
				countedRows++;
			}
			Assert.assertEquals(28, countedRows);
			
		} finally {
			DBFUtils.close(reader);
		}
	}
}
