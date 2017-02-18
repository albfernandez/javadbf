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
package com.linuxense.javadbf.testutils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFUtils;

public final class DbfToTxtTest {

	private DbfToTxtTest() {
		throw new AssertionError("no instances");
	}
	
	public static void export(DBFReader reader, File file) {
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file, "UTF-8");
			Object[] row = null;
	
			while ((row = reader.nextRecord()) != null) {
				for (Object o : row) {
					writer.print(o + ";");
				}
				writer.println("");
			}
		}
		catch (IOException e) {
			// nop
		}
		finally {
			DBFUtils.close(writer);
		}
	}
}
