/*
  DBFException
	Represents exceptions happen in the JAvaDBF classes.

  This file is part of JavaDBF packege.

  author: anil@linuxense
  license: LGPL (http://www.gnu.org/copyleft/lesser.html)

  $Id: DBFException.java,v 1.1 2003-06-04 09:32:33 anil Exp $
*/
package com.linuxense.javadbf;

import java.io.IOException;

public class DBFException extends IOException {

	public DBFException() {

		super();
	}

	public DBFException( String msg) {

		super( msg);
	}
}
