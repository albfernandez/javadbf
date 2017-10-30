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

import java.io.File;
import java.io.FileInputStream;

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.testutils.AssertUtils;
import com.linuxense.javadbf.testutils.DbfToTxtTest;

public class FixtureDBase30Test {

	@Test
	public void test30 () throws Exception {
		File file = new File("src/test/resources/fixtures/dbase_30.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream(file));
			
			DBFHeader header = reader.getHeader();
			Assert.assertNotNull(header);
			Assert.assertEquals(145, header.fieldArray.length);
			Assert.assertEquals(34, header.numberOfRecords);
			DBFField []fieldArray = header.fieldArray;
			
			int i = 0;
			
			
			AssertUtils.assertColumnDefinition(fieldArray[i++], "ACCESSNO", DBFDataType.fromCode((byte) 'C'),   15  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "ACQVALUE", DBFDataType.fromCode((byte) 'N'),   12  ,  2);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "APPNOTES", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "APPRAISOR", DBFDataType.fromCode((byte) 'C'),   75  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CABINET", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CAPTION", DBFDataType.fromCode((byte) 'C'),   30  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CAT", DBFDataType.fromCode((byte) 'C'),   1   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CATBY", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CATDATE", DBFDataType.fromCode((byte) 'D'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CATTYPE", DBFDataType.fromCode((byte) 'C'),   15  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CLASSES", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "COLLECTION", DBFDataType.fromCode((byte) 'C'),   75  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CONDDATE", DBFDataType.fromCode((byte) 'D'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CONDEXAM", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CONDITION", DBFDataType.fromCode((byte) 'C'),   35  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CONDNOTES", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CONTAINER", DBFDataType.fromCode((byte) 'C'),   40  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "COPYRIGHT", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CREATOR", DBFDataType.fromCode((byte) 'C'),   80  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CREDIT", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CURVALMAX", DBFDataType.fromCode((byte) 'N'),   12  ,  2);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CURVALUE", DBFDataType.fromCode((byte) 'N'),   12  ,  2);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "DATASET", DBFDataType.fromCode((byte) 'C'),   15  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "DATE", DBFDataType.fromCode((byte) 'C'),   50  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "DESCRIP", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "DIMNOTES", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "DISPVALUE", DBFDataType.fromCode((byte) 'C'),   10  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "DRAWER", DBFDataType.fromCode((byte) 'C'),   20  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "EARLYDATE", DBFDataType.fromCode((byte) 'N'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "EVENT", DBFDataType.fromCode((byte) 'C'),   80  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "EXHIBITID", DBFDataType.fromCode((byte) 'C'),   36  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "EXHIBITNO", DBFDataType.fromCode((byte) 'N'),   7   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "EXHLABEL1", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "EXHLABEL2", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "EXHLABEL3", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "EXHLABEL4", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "EXHSTART", DBFDataType.fromCode((byte) 'D'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "FILMSIZE", DBFDataType.fromCode((byte) 'C'),   35  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "FLAGDATE", DBFDataType.fromCode((byte) 'T'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "FLAGNOTES", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "FLAGREASON", DBFDataType.fromCode((byte) 'C'),   20  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "FRAME", DBFDataType.fromCode((byte) 'C'),   75  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "FRAMENO", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "GPARENT", DBFDataType.fromCode((byte) 'C'),   45  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "HOMELOC", DBFDataType.fromCode((byte) 'C'),   60  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "IMAGEFILE", DBFDataType.fromCode((byte) 'C'),   60  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "IMAGENO", DBFDataType.fromCode((byte) 'N'),   3   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "INSCOMP", DBFDataType.fromCode((byte) 'C'),   30  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "INSDATE", DBFDataType.fromCode((byte) 'D'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "INSPHONE", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "INSPREMIUM", DBFDataType.fromCode((byte) 'C'),   20  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "INSREP", DBFDataType.fromCode((byte) 'C'),   30  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "INSVALUE", DBFDataType.fromCode((byte) 'N'),   10  ,  2);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "INVNBY", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "INVNDATE", DBFDataType.fromCode((byte) 'D'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "LATEDATE", DBFDataType.fromCode((byte) 'N'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "LEGAL", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "LOANCOND", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "LOANDATE", DBFDataType.fromCode((byte) 'D'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "LOANDUE", DBFDataType.fromCode((byte) 'D'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "LOANID", DBFDataType.fromCode((byte) 'C'),   36  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "LOANINNO", DBFDataType.fromCode((byte) 'C'),   15  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "MAINTCYCLE", DBFDataType.fromCode((byte) 'C'),   10  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "MAINTDATE", DBFDataType.fromCode((byte) 'D'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "MAINTNOTE", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "MEDIUM", DBFDataType.fromCode((byte) 'C'),   75  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "NEGLOC", DBFDataType.fromCode((byte) 'C'),   60  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "NEGNO", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "NOTES", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OBJECTID", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OBJNAME", DBFDataType.fromCode((byte) 'C'),   40  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OLDNO", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "ORIGCOPY", DBFDataType.fromCode((byte) 'C'),   15  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OTHERNO", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "OUTDATE", DBFDataType.fromCode((byte) 'D'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PARENT", DBFDataType.fromCode((byte) 'C'),   40  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PEOPLE", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PLACE", DBFDataType.fromCode((byte) 'C'),   100 ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "POLICYNO", DBFDataType.fromCode((byte) 'C'),   20  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PRINTSIZE", DBFDataType.fromCode((byte) 'C'),   35  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PROCESS", DBFDataType.fromCode((byte) 'C'),   75  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PROVENANCE", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PUBNOTES", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "RECAS", DBFDataType.fromCode((byte) 'C'),   20  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "RECDATE", DBFDataType.fromCode((byte) 'C'),   10  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "RECFROM", DBFDataType.fromCode((byte) 'C'),   120 ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "RELATION", DBFDataType.fromCode((byte) 'C'),   36  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "RELNOTES", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "ROOM", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "SGFLAG", DBFDataType.fromCode((byte) 'C'),   1   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "SHELF", DBFDataType.fromCode((byte) 'C'),   20  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "SITE", DBFDataType.fromCode((byte) 'C'),   40  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "SITENO", DBFDataType.fromCode((byte) 'C'),   12  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "SLIDENO", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "STATUS", DBFDataType.fromCode((byte) 'C'),   20  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "STATUSBY", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "STATUSDATE", DBFDataType.fromCode((byte) 'D'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "STERMS", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "STUDIO", DBFDataType.fromCode((byte) 'C'),   60  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "SUBJECTS", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TCABINET", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TCONTAINER", DBFDataType.fromCode((byte) 'C'),   40  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TDRAWER", DBFDataType.fromCode((byte) 'C'),   20  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TEMPAUTHOR", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TEMPBY", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TEMPDATE", DBFDataType.fromCode((byte) 'D'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TEMPLOC", DBFDataType.fromCode((byte) 'C'),   60  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TEMPNOTES", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TEMPREASON", DBFDataType.fromCode((byte) 'C'),   50  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TEMPUNTIL", DBFDataType.fromCode((byte) 'C'),   10  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TITLE", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TITLESORT", DBFDataType.fromCode((byte) 'C'),   100 ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TROOM", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TSHELF", DBFDataType.fromCode((byte) 'C'),   20  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TWALL", DBFDataType.fromCode((byte) 'C'),   20  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF1", DBFDataType.fromCode((byte) 'C'),   75  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF10", DBFDataType.fromCode((byte) 'C'),   75  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF11", DBFDataType.fromCode((byte) 'C'),   20  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF12", DBFDataType.fromCode((byte) 'C'),   20  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF13", DBFDataType.fromCode((byte) 'N'),   12  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF14", DBFDataType.fromCode((byte) 'N'),   12  ,  2);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF15", DBFDataType.fromCode((byte) 'N'),   12  ,  2);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF16", DBFDataType.fromCode((byte) 'N'),   12  ,  3);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF17", DBFDataType.fromCode((byte) 'N'),   12  ,  3);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF18", DBFDataType.fromCode((byte) 'D'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF19", DBFDataType.fromCode((byte) 'D'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF20", DBFDataType.fromCode((byte) 'D'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF21", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF22", DBFDataType.fromCode((byte) 'M'),   4   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF2", DBFDataType.fromCode((byte) 'C'),   75  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF3", DBFDataType.fromCode((byte) 'C'),   75  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF4", DBFDataType.fromCode((byte) 'C'),   75  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF5", DBFDataType.fromCode((byte) 'C'),   75  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF6", DBFDataType.fromCode((byte) 'C'),   75  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF7", DBFDataType.fromCode((byte) 'C'),   75  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF8", DBFDataType.fromCode((byte) 'C'),   75  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UDF9", DBFDataType.fromCode((byte) 'C'),   75  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UPDATED", DBFDataType.fromCode((byte) 'T'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UPDATEDBY", DBFDataType.fromCode((byte) 'C'),   25  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "VALUEDATE", DBFDataType.fromCode((byte) 'D'),   8   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "WALL", DBFDataType.fromCode((byte) 'C'),   20  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "WEBINCLUDE", DBFDataType.fromCode((byte) 'L'),   1   ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "ZSORTER", DBFDataType.fromCode((byte) 'C'),   69  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "ZSORTERX", DBFDataType.fromCode((byte) 'C'),   44  ,  0);        
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PPID", DBFDataType.fromCode((byte) 'C'),   36  ,  0);        

			DbfToTxtTest.export(reader, File.createTempFile("javadbf-test", ".txt"));
			



			
		} finally {
			DBFUtils.close(reader);
		}

	}
	
}
