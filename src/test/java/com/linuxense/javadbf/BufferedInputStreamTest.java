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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class BufferedInputStreamTest {

	public BufferedInputStreamTest() {
		super();
	}
	
	@Test
	public void testBuffered()  throws IOException {
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream("src/test/resources/books.dbf"));
			ReadDBFAssert.testReadDBFFile(is, 11, 10);
		}
		finally {
			if (is != null) {
				is.close();
			}
		}
	}

}
