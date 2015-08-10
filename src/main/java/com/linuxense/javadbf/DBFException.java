/*

(C) Copyright 2015 Alberto Fernández <infjaf@gmail.com>
(C) Copyright 2003-2004 Anil Kumar K <anil@linuxense.com>

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

import java.io.IOException;

/**
 *	Represents exceptions happen in the JavaDBF classes.
 *
 */
public class DBFException extends IOException {

	private static final long serialVersionUID = 1906727217048909819L;

	public DBFException() {
		super();
	}

	public DBFException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBFException(Throwable cause) {
		super(cause);
	}

	public DBFException(String msg) {
		super(msg);
	}
}
