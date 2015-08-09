package com.linuxense.javadbf;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("src/test/resources/provincias_es.dbf");
			DBFReader reader = new DBFReader(inputStream);

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
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}
