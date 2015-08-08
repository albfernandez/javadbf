/*

(C) Copyright 2015 Alberto Fernández <infjaf@gmail.com>
(C) Copyright 2004 Anil Kumar K <anil@linuxense.com>

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

/*
Serves as the base class of DBFReader adn DBFWriter.

Support for choosing implemented character Sets as 
suggested by Nick Voznesensky <darkers@mail.ru>
*/
/**
Base class for DBFReader and DBFWriter.
*/
public abstract class DBFBase {

	protected String characterSetName = "8859_1";
	protected final int END_OF_DATA = 0x1A;

	/* 
	 If the library is used in a non-latin environment use this method to set 
	 corresponding character set. More information: 
	 http://www.iana.org/assignments/character-sets
	 Also see the documentation of the class java.nio.charset.Charset
	*/
	public String getCharactersetName() {

		return this.characterSetName;
	}

	public void setCharactersetName( String characterSetName) {

		this.characterSetName = characterSetName;
	}

}
