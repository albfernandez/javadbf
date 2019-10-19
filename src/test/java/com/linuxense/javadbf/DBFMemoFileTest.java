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
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

public class DBFMemoFileTest {
	
	@Test
	public void testMemo83() throws Exception {
		testMemo83(true);
		testMemo83(false);
	}
	
	private void testMemo83(boolean inMemory) throws Exception {
		DBFMemoFile file = null;
		try {
			file = new DBFMemoFile(new File("src/test/resources/fixtures/dbase_83.dbt"), Charset.forName("windows-1252"), inMemory);

			String text="Our Original assortment...a little taste of heaven for everyone.  Let us\r\n" + 
					"select a special assortment of our chocolate and pastel favorites for you.\r\n" + 
					"Each petit four is its own special hand decorated creation. Multi-layers of\r\n" + 
					"moist cake with combinations of specialty fillings create memorable cake\r\n" + 
					"confections. Varietes include; Luscious Lemon, Strawberry Hearts, White\r\n" + 
					"Chocolate, Mocha Bean, Roasted Almond, Triple Chocolate, Chocolate Hazelnut,\r\n" + 
					"Grand Orange, Plum Squares, Milk chocolate squares, and Raspberry Blanc.";
	
			String content = file.readText(1);
			Assert.assertEquals(text, content);
		}
		finally {
			DBFUtils.close(file);
		}
	}
	
	
	@Test
	public void testMemo8b() throws Exception {
		testMemo8b(true);
		testMemo8b(false);
	}
	
	private void testMemo8b(boolean inMemory) throws Exception {
		DBFMemoFile file = null;
		try {
			file = new DBFMemoFile(new File("src/test/resources/fixtures/dbase_8b.dbt"), Charset.forName("windows-1252"), inMemory);
			
			Assert.assertEquals("First memo\r\n", file.readText(1));
			Assert.assertEquals("Second memo", file.readText(2));
			Assert.assertEquals("Thierd memo", file.readText(3));
			Assert.assertEquals("Fourth memo", file.readText(4));
			Assert.assertEquals("Fifth memo", file.readText(5));
			Assert.assertEquals("Sixth memo", file.readText(6));
			Assert.assertEquals("Seventh memo", file.readText(7));
			Assert.assertEquals("Eigth memo", file.readText(8));
			Assert.assertEquals("Nineth memo", file.readText(9));
		}
		finally {
			DBFUtils.close(file);
		}
	}
	
	
	@Test
	public void testMemoBinary() throws Exception {
		testMemoBinary(true);
		testMemoBinary(false);
	}
	
	private void testMemoBinary(boolean inMemory) throws Exception {
		DBFMemoFile file = null;
		try {
		// Inventory has extra 8 bytes before image...
			file = new DBFMemoFile(new File("src/test/resources/inventory.dbt"), Charset.forName("windows-1252"), inMemory);
			byte[] result = file.readBinary(1);
			Assert.assertEquals(14037, result.length);
		}
		finally {
			DBFUtils.close(file);
		}
	}
	
	@Test
	public void testMemoFPT () throws Exception {
		testMemoFPT(true);
		testMemoFPT(false);
	}
	
	private void testMemoFPT (boolean inMemory) throws Exception {
		DBFMemoFile file = null;
		try {
			file = new DBFMemoFile(new File("src/test/resources/fixtures/dbase_f5.fpt"), Charset.forName("windows-1252"), inMemory);
			
			Assert.assertEquals("torrossolla", file.readData(565, DBFDataType.MEMO));
				
			Assert.assertEquals("jos‚ vicente salvador\r\n" + 
					"capell…: salvador vidal\r\n" + 
					"en n‚ixer, les castellers li van fer un pilar i el van entregar al seu pare.", file.readData(52, DBFDataType.MEMO));
		}
		finally {
			DBFUtils.close(file);
		}
	}

}
