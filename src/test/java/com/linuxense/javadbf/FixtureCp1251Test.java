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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.linuxense.javadbf.utils.DBFUtils;

public class FixtureCp1251Test {
	@Test
	public void test8b() throws Exception {
		File file = new File("src/test/resources/fixtures/cp1251.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream(file));
			
			DBFHeader header = reader.getHeader();
			
			Assertions.assertNotNull(header);
			Assertions.assertEquals(2, header.fieldArray.length);
			
			Assertions.assertEquals("RN", header.fieldArray[0].getName());
			Assertions.assertEquals(DBFDataType.NUMERIC, header.fieldArray[0].getType());
			Assertions.assertEquals(0, header.fieldArray[0].getDecimalCount());
			Assertions.assertEquals(4, header.fieldArray[0].getLength());		
			
			Assertions.assertEquals("NAME", header.fieldArray[1].getName());
			Assertions.assertEquals(DBFDataType.CHARACTER, header.fieldArray[1].getType());
			Assertions.assertEquals(0, header.fieldArray[1].getDecimalCount());
			Assertions.assertEquals(100, header.fieldArray[1].getLength());	
			
			
			
			Assertions.assertEquals(4, header.numberOfRecords);
			
			
			Object[] row = null;
			
			row = reader.nextRecord();			
			Assertions.assertEquals(1, ((Number) row[0]).intValue());
			Assertions.assertEquals("àìáóëàòîðíî-ïîëèêëèíè÷åñêîå", ((String)row[1]).trim());
			
			row = reader.nextRecord();			
			Assertions.assertEquals(2, ((Number) row[0]).intValue());
			Assertions.assertEquals("áîëüíè÷íîå", ((String)row[1]).trim());
			
			row = reader.nextRecord();			
			Assertions.assertEquals(3, ((Number) row[0]).intValue());
			Assertions.assertEquals("ÍÈÈ", ((String)row[1]).trim());
			
			row = reader.nextRecord();			
			Assertions.assertEquals(4, ((Number) row[0]).intValue());
			Assertions.assertEquals("îáðàçîâàòåëüíîå ìåäèöèíñêîå ó÷ðåæäåíèå", ((String)row[1]).trim());
			
			row = reader.nextRecord();		
			Assertions.assertNull(row);
			

			
		} finally {
			DBFUtils.close(reader);
		}

	}
}


/*

Database: cp1251.dbf
Type: (30) Visual FoxPro
Memo File: false
Records: 4

Fields:
Name             Type       Length     Decimal
------------------------------------------------------------------------------
RN               N          4          0         
NAME             C          100        0         

*/