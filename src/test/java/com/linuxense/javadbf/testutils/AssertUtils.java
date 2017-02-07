package com.linuxense.javadbf.testutils;



import org.junit.Assert;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;

public class AssertUtils {

	public static void assertColumnDefinition(DBFField field, String columnName, DBFDataType type, int length, int decimal) {
		Assert.assertEquals(columnName, field.getName());
		Assert.assertEquals(type, field.getType());
		Assert.assertEquals(length, field.getFieldLength());
		Assert.assertEquals(decimal, field.getDecimalCount());
	}

}
