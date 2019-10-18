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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

public class CreateEmptyFileFromTemplateTest {

	
	@Test	
	public void testCreate () throws IOException {
		File inputFile = new File("src/test/resources/countries.dbf");
		File outputFile = File.createTempFile("example", ".dbf");
		emptyFile(inputFile, outputFile);
	}

	private void emptyFile(File inputFile, File outputFile) throws IOException, DBFException {
		OutputStream os = null;
		DBFReader reader = null;
		DBFWriter writer = null;
		try {		
			os = new BufferedOutputStream(new FileOutputStream(outputFile));
			reader = new DBFReader(new FileInputStream(inputFile));
			
			DBFField[] fields = new DBFField[reader.getFieldCount()];
			for (int i = 0; i < reader.getFieldCount(); i++) {
				fields[i] = reader.getField(i);
			}
			
			writer = new DBFWriter(os, reader.getCharset());
			writer.setFields(fields);
			
			
		}
		finally {
			DBFUtils.close(reader);
			DBFUtils.close(writer);
			DBFUtils.close(os);
		}
	
	}
}
