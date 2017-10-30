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
package com.linuxense.javadbf.docexamples;

import java.io.*;
import com.linuxense.javadbf.*;

public class JavaDBFReaderMemoTest {

	public static void main(String args[]) {
		DBFReader reader = null;
		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream(args[0]));

			reader.setMemoFile(new File("memo.dbt"));

			// do whatever you want with the data

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			DBFUtils.close(reader);
		}
	}
}
