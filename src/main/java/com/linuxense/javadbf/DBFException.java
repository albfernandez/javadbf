/*

(C) Copyright 2015-2017 Alberto Fernández <infjaf@gmail.com>
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


/**
 *	Represents exceptions happen in the JavaDBF classes.
 *
 */
public class DBFException extends RuntimeException {

	private static final long serialVersionUID = 1906727217048909819L;


	/**
	 * Constructs an DBFException with the specified detail message.
	 * @param message The detail message (which is saved for later retrieval by the Throwable.getMessage() method)
	 */
	public DBFException(String message) {
		super(message);
	}
	/**
	 * Constructs an DBFException with the specified detail message and cause.
	 * @param message The detail message (which is saved for later retrieval by the Throwable.getMessage() method)
	 * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method).
	 */
	public DBFException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DBFException(Throwable cause) {
		super(cause);
	}



}
