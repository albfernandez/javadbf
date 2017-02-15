package com.linuxense.javadbf;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

public class DBFMemoFileTest {
	
	@Test
	public void testMemo83() throws Exception {
		DBFMemoFile file = new DBFMemoFile(new File("src/test/resources/fixtures/dbase_83.dbt"), Charset.forName("windows-1252"));

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
	
	
	@Test
	public void testMemo8b() throws Exception {
		DBFMemoFile file = new DBFMemoFile(new File("src/test/resources/fixtures/dbase_8b.dbt"), Charset.forName("windows-1252"));
		
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
	
	
	@Test
	public void testMemoBinary() throws Exception {
		// Inventory has extra 8 bytes before image...
		DBFMemoFile file = new DBFMemoFile(new File("src/test/resources/inventory.dbt"), Charset.forName("windows-1252"));
		byte[] result = file.readBinary(1);
		Assert.assertEquals(14037, result.length);		

	}
	

}
