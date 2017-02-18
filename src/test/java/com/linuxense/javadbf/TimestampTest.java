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

import org.junit.Test;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by mauri on 20/08/2016.
 */
public class TimestampTest {
    public TimestampTest() {
        super();
    }

    @Test
    public void testTimestampRead () throws IOException {
    	DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream("src/test/resources/bdays.dbf"));

            int numberOfFields = reader.getFieldCount();
            Assert.assertEquals(3, numberOfFields);

            for (int i = 0; i < numberOfFields; i++) {
                DBFField field = reader.getField(i);
                Assert.assertNotNull(field.getName());
            }

            Object[] rowObject;
            int countedRows = 0;
            while ((rowObject = reader.nextRecord()) != null) {
                Assert.assertEquals(numberOfFields, rowObject.length);
                Assert.assertTrue(rowObject[1] instanceof java.util.Date);

                Calendar calendar = new GregorianCalendar();
                calendar.set(1942,Calendar.NOVEMBER,27,10,15,0);
                calendar.clear(Calendar.MILLISECOND);

                Assert.assertTrue(rowObject[0].toString().trim().equals("jimi"));
                Assert.assertEquals(calendar.getTime(),(Date)rowObject[1]);
                Assert.assertTrue(rowObject[2].toString().trim().equals("hendrix"));

                countedRows++;
            }
            Assert.assertEquals(1, countedRows);
        }
        finally {
        	DBFUtils.close(reader);
        }
    }
}
