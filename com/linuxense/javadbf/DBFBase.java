/*
	$Id: DBFBase.java,v 1.1 2004-02-09 13:45:38 anil Exp $
	Serves as the base class of DBFReader adn DBFWriter.
	
	Support for choosing implemented character Sets as 
	suggested by Nick Voznesensky <darkers@mail.ru>
*/
package com.linuxense.javadbf;

public abstract class DBFBase {

	protected String characterSetName = "8859_1";

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
