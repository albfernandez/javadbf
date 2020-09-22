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
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.mocks.NullOutputStream;

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
			wr =  new DBFWriter(baos);
			wr.setCharset(StandardCharsets.UTF_8);
			DBFField fields[] = new DBFField[1];
	
			fields[0] = new DBFField();
			fields[0].setName("emp_name");
			fields[0].setType(DBFDataType.CHARACTER);
			fields[0].setLength(10);
			wr.setFields(fields);
			wr.addRecord(new Object[] { "Simón" });
			wr.addRecord(new Object[] { "Julián"});
			
			DBFUtils.close(wr);
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
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(new NullOutputStream());
			writer.setCharset(StandardCharsets.ISO_8859_1);
			Assert.assertEquals(StandardCharsets.ISO_8859_1, writer.getCharset());
			Assert.assertEquals(StandardCharsets.ISO_8859_1.displayName(), writer.getCharset().displayName());
			writer.setCharset(StandardCharsets.ISO_8859_1);
			Assert.assertEquals(StandardCharsets.ISO_8859_1.displayName(), writer.getCharset().displayName());
			Assert.assertEquals(StandardCharsets.ISO_8859_1, writer.getCharset());
		}
		finally {
			DBFUtils.close(writer);
		}
	}
	
	@Test
	public void testSetEncodingCP866() throws DBFException {
		DBFWriter writer = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			writer = new DBFWriter(baos, Charset.forName("CP866"));	
			DBFField fields[] = new DBFField[1];
			
			fields[0] = new DBFField();
			fields[0].setName("emp_name");
			fields[0].setType(DBFDataType.CHARACTER);
			fields[0].setLength(10);
			writer.setFields(fields);
			writer.addRecord(new Object[] { "Simon" });
			writer.addRecord(new Object[] { "Julian"});
		}
		finally {
			DBFUtils.close(writer);
		}
		byte[] data = baos.toByteArray();
		Assert.assertEquals(0x65, data[29]);
	}
	
	
	@Test
	public void testSetEncodingCP866StringConversion() throws DBFException {
		Charset charset = Charset.forName("CP866");
		String charsetString = charset.toString().toLowerCase();
		
		Assert.assertEquals("ibm866", charsetString);
	}
	
	@Test
	public void testUnicodeAsFieldName() throws IOException {
		NullOutputStream output = new NullOutputStream();
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(output, StandardCharsets.UTF_8);
			DBFField[] fields = new DBFField[1];

			fields[0] = new DBFField();
			fields[0].setName("Código890");
			fields[0].setType(DBFDataType.NUMERIC);
			fields[0].setLength(10);
			fields[0].setDecimalCount(0);

			writer.setFields(fields);
			writer.addRecord(new Object[] { 1 });
			writer.addRecord(new Object[] { 2 });
			writer.addRecord(new Object[] { 2 });
		}
		finally {
			DBFUtils.close(writer);
		}
		Assert.assertEquals(99L, output.getCount());
	}
	
	
	@Test	
	public void testUTF8 () throws IOException {
		File file = File.createTempFile("utf8", ".dbf");
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(file, StandardCharsets.UTF_8);
			DBFField[] fields = new DBFField[1];

			fields[0] = new DBFField();
			fields[0].setName("Código890");
			fields[0].setType(DBFDataType.NUMERIC);
			fields[0].setLength(10);
			fields[0].setDecimalCount(0);

			writer.setFields(fields);
			writer.addRecord(new Object[] { 1 });
			writer.addRecord(new Object[] { 2 });
			writer.addRecord(new Object[] { 2 });
		}
		finally {
			DBFUtils.close(writer);
		}
		Assert.assertEquals(99L, file.length());
		file.delete();
	}
	@Test	
	public void testUTF8_1 () throws IOException {
		File file = File.createTempFile("utf8", ".dbf");
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(file, Charset.forName("UTF-8"));
			DBFField[] fields = new DBFField[1];

			fields[0] = new DBFField();
			fields[0].setName("Código890");
			fields[0].setType(DBFDataType.NUMERIC);
			fields[0].setLength(10);
			fields[0].setDecimalCount(0);

			writer.setFields(fields);
			writer.addRecord(new Object[] { 1 });
			writer.addRecord(new Object[] { 2 });
			writer.addRecord(new Object[] { 2 });
		}
		finally {
			DBFUtils.close(writer);
		}
		Assert.assertEquals(99L, file.length());
		file.delete();
	}
	@Test	
	public void testUTF8_2 () throws IOException {
		File file = File.createTempFile("utf8", ".dbf");
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(file, Charset.forName("UTF8"));
			DBFField[] fields = new DBFField[1];

			fields[0] = new DBFField();
			fields[0].setName("Código890");
			fields[0].setType(DBFDataType.NUMERIC);
			fields[0].setLength(10);
			fields[0].setDecimalCount(0);

			writer.setFields(fields);
			writer.addRecord(new Object[] { 1 });
			writer.addRecord(new Object[] { 2 });
			writer.addRecord(new Object[] { 2 });
		}
		finally {
			DBFUtils.close(writer);
		}
		Assert.assertEquals(99L, file.length());
		file.delete();
	}
}
