package com.linuxense.javadbf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class EncodingTest {

	public EncodingTest() {
		super();
	}
	
	@Test
	public void testRead() throws IOException {
		String[] names = {
			"Álava",
			"Albacete",
			"Alicante",
			"Almería",
			"Ávila",
			"Badajoz",
			"Islas Baleares",
			"Barcelona",
			"Burgos",
			"Cáceres",
			"Cádiz",
			"Castellón",
			"Ciudad Real",
			"Córdoba",
			"La Coruña",
			"Cuenca",
			"Gerona",
			"Granada",
			"Guadalajara",
			"Guipúzcoa",
			"Huelva",
			"Huesca",
			"Jaén",
			"León",
			"Lleida",
			"La Rioja",
			"Lugo",
			"Madrid",
			"Málaga",
			"Murcia",
			"Navarra",
			"Orense",
			"Asturias",
			"Palencia",
			"Las Palmas",
			"Pontevedra",
			"Salamanca",
			"Santa Cruz de Tenerife",
			"Cantabria",
			"Segovia",
			"Sevilla",
			"Soria",
			"Tarragona",
			"Teruel",
			"Toledo",
			"Valencia",
			"Valladolid",
			"Vizcaya",
			"Zamora",
			"Zaragoza",
			"Ceuta",
			"Melilla"
		};
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream("src/test/resources/provincias_es.dbf"));

			int numberOfFields = reader.getFieldCount();
			Assert.assertEquals(5, numberOfFields);
			for (int i = 0; i < numberOfFields; i++) {
				DBFField field = reader.getField(i);
				Assert.assertNotNull(field.getName());
			}
			Object[] rowObject;
			int countedRows = 0;
			while ((rowObject = reader.nextRecord()) != null) {
				Assert.assertEquals(numberOfFields, rowObject.length);
				Assert.assertTrue(rowObject[1] instanceof String);
				Assert.assertEquals(names[countedRows], ((String)rowObject[1]).trim());
				countedRows++;
			}
			Assert.assertEquals(52, countedRows);
		}
		finally {
			DBFUtils.close(reader);
		}
	}
	
	@Test
	public void testUtf8 () throws DBFException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DBFReader reader = null;
		DBFWriter wr = null; 
		try  {
			wr =  new DBFWriter();
			wr.setCharset(StandardCharsets.UTF_8);
			DBFField fields[] = new DBFField[1];
	
			fields[0] = new DBFField();
			fields[0].setName("emp_name");
			fields[0].setType(DBFDataType.CHARACTER);
			fields[0].setLength(10);
			wr.setFields(fields);
			wr.addRecord(new Object[] { "Simón" });
			wr.addRecord(new Object[] { "Julián"});
			
			wr.write(baos);
			
			byte[] data = baos.toByteArray();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			
			List<String> names = new ArrayList<String>();
			reader = new DBFReader(bais);
			reader.setCharset(StandardCharsets.UTF_8);
			Object[] rowObject;
			while ((rowObject = reader.nextRecord()) != null) {
				names.add((String) rowObject[0]);
			}
			assertNotNull(names.get(0));
			assertEquals("Simón", names.get(0).trim());
			
			assertNotNull(names.get(1));
			assertEquals("Julián", names.get(1).trim());
		}
		finally {
			DBFUtils.close(reader);
			DBFUtils.close(wr);
		}
		
		
	}
	
	@Test
	public void testSetEncoding() throws DBFException {
		try (DBFWriter writer = new DBFWriter()) {
			writer.setCharset(StandardCharsets.ISO_8859_1);
			Assert.assertEquals(StandardCharsets.ISO_8859_1, writer.getCharset());
			Assert.assertEquals(StandardCharsets.ISO_8859_1.displayName(), writer.getCharset().displayName());
			writer.setCharset(StandardCharsets.ISO_8859_1);
			Assert.assertEquals(StandardCharsets.ISO_8859_1.displayName(), writer.getCharset().displayName());
			Assert.assertEquals(StandardCharsets.ISO_8859_1, writer.getCharset());
		}		
	}
}
