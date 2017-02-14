/*

(C) Copyright 2015-2017 Alberto Fernández <infjaf@gmail.com>
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

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Base class for DBFReader and DBFWriter. Support for choosing implemented
 * character Sets as suggested by Nick Voznesensky darkers@mail.ru
 */
public abstract class DBFBase {

	protected static final int END_OF_DATA = 0x1A;
	protected static final Charset DEFAULT_CHARSET= StandardCharsets.ISO_8859_1;
	private Charset charset = DEFAULT_CHARSET;

	protected DBFBase() {
		super();
	}

	
	/**
	 * Gets the charset used to read and write files.
	 */
	public Charset getCharset() {
		return this.charset;
	}
	/**
	 * Sets the charset to use to read and write files.
	 * 
	 * If the library is used in a non-latin environment use this method to set
	 * corresponding character set. More information:
	 * http://www.iana.org/assignments/character-sets Also see the documentation
	 * of the class java.nio.charset.Charset
	 */
	public void setCharset(Charset charset) {
		this.charset = charset;
	}
	
	/**
	 * Gets the charset used to read and write files.
	 * 
	 * @return name of the charset
	 * @deprecated replaced by {@link DBFBase#getCharset()}
	 */
	@Deprecated
	public String getCharactersetName() {
		return this.charset.displayName();
	}

	/**
	 * Sets the charset to use to read and write files.
	 * 
	 * If the library is used in a non-latin environment use this method to set
	 * corresponding character set. More information:
	 * http://www.iana.org/assignments/character-sets Also see the documentation
	 * of the class java.nio.charset.Charset
	 * 
	 * @param characterSetName name of the charset
	 * @deprecated replaced by {@link DBFBase#setCharset(Charset)}
	 */
	@Deprecated
	public void setCharactersetName(String characterSetName) {
		this.charset = Charset.forName(characterSetName);
	}
	
}
