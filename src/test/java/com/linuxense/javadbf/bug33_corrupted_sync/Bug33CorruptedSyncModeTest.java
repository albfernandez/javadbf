package com.linuxense.javadbf.bug33_corrupted_sync;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFWriter;
import com.linuxense.javadbf.testutils.DbfToTxtTest;
import com.linuxense.javadbf.testutils.FileUtils;

public class Bug33CorruptedSyncModeTest {

	public Bug33CorruptedSyncModeTest() {
		super();
	}
	
	@Test(expected=DBFException.class)
	public void testCorruptedFile() throws FileNotFoundException {
		String fileName = "src/test/resources/bug-33-corrupted-file-sync-mode/myfileafter-corrupted.dbf";
		DbfToTxtTest.writeToConsole(new File(fileName));
	}
	

	
	@Test
	public void testBug() throws IOException {
		File tmp = File.createTempFile("example", ".dbf");
		File orig = new File("src/test/resources/bug-33-corrupted-file-sync-mode/myfilebefore.dbf");
		
		FileUtils.copyFile(orig, tmp);
		
		DBFWriter writer = new DBFWriter(tmp); 

	    Object rowData[] = new Object[21];
			rowData[0] = 702;
			rowData[1] = "114";
			rowData[2] = "57272";
			rowData[3] = new Date(1493676000000L); // "Tue May 02 00:00:00 CEST 2017"
			rowData[4] = "11044";
			rowData[5] = "140-01";
			rowData[6] = "AD";
			rowData[7] = "";
			rowData[8] = "";
			rowData[9] = 80;
			rowData[10] = "09";
			rowData[11] = "LPG";
			rowData[12] = new Double(0);
			rowData[13] = new Double(0);
			rowData[14] = new Double(3);
			rowData[15] = new Double(0);
			rowData[16] = new Double(0);
			rowData[17] = new Double(0);
			rowData[18] = new Double(0);
			rowData[19] = new Double(0);
			rowData[20] = "";
			
			writer.addRecord(rowData);
			
			writer.close();
			
			DbfToTxtTest.writeToConsole(tmp);
	}
	

	
}
