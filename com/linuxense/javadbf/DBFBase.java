/*
	$Id: DBFBase.java,v 1.2 2004-03-31 10:39:32 anil Exp $
	Serves as the base class of DBFReader adn DBFWriter.
	
	@author: anil@linuxense.com

	Support for choosing implemented character Sets as 
	suggested by Nick Voznesensky <darkers@mail.ru>
*/
package com.linuxense.javadbf;

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
