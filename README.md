# JavaDBF
JavaDBF is a Java library for reading and writing XBase files.
There are plenty of legacy applications around with .dbf as their primary storage format.
JavaDBF was initially written for data transfer with such applications.


#Introduction

Till late 90s dBase and its cousins were the most preferred database platform for small and even medium enterprise applications.
They required low hardware configurations and were cheaper to develop. 
Eventually more capable desktop databases like Microsoft Access came into picture,
DBF file format still remains one of the simplest way to store and transfer data.

DBF format has some advantages over CSV or XML: it can contain the structure definition including data type information. 
DBF is more like an open standard so it can be used as a data exchange format.
If you have a database application with an RDBMS at the back-end and 
you need to import a report to your spread sheet program, DBF format is the most elegant and sure-shot approach.

JavaDBF also comes handy when it is required to transfer data between applications which do not have a common data format.
Java developers often come across such situations when they are asked to share data with spreadsheet application. 

#Getting and Installing

Obtain the latest version of JavaDBF from release page at github.
Download the jar file and put it in your $CLASSPATH variable. You are ready to go. 

## Getting with Maven
If you are using Maven, you can add JavaDBF to your project using this dependency in your pom.xml

```
	<dependency>
		<groupId>com.github.albfernandez</groupId>
		<artifactId>javadbf</artifactId>
		<version>0.8.0</version>
	</dependency>
```


#Overview of the Library

JavaDBF has a simple API of its own and it does not implement the JDBC API. 
It is designed this way because JavaDBF is not indedned to support full-blown RDBMS-style database interaction.
And you are not supposed to use it like a back-end; it just doesn't work that way. 
Also, JavaDBF is not designed to be thread-safe; keep that in mind when you design threaded applications.

JavaDBF comes in the package com.linuxense.javadbf. 
Import that package in your Java code. Following examples will familiarise you with its APIs. 

#Data Type Mapping

JavaDBF supports almost all XBase data types except Memo field. 
While reading, those types are interpretted as appropriate Java types.
Following table shows the mapping scheme.


| XBase Type  | XBase Symbol | Java Type used in JavaDBF |
|----------   | ------------ | ---------------------------
|Character    | C            | java.lang.String          |
|Numeric      | N            | java.lang.Double          |
|Double       | F            | lava.lang.Double          |
|Logical      | L            | java.lang.Boolean         |
|Date         | D            | java.util.Date            |

FoxPro types (Read Only)

| FoxPro Type        | Symbol  | Java Type used in JavaDBF |
| -------------------| ------- | ------------------------- |  
| Currency           | Y       | java.math.BigDecimal      |
| Long               | I       | java.lang.Integer         |
| Date Type (FoxPro) | T       | java.util.Date            |

Unsupported types

| Type                  | Symbol |
| --------------------- | ------ |
| Memo                  | M      |
| Binary                | B      |
| General (OLE Objects) | G      |
| Picture (FoxPro)      | P      |
| AutoIncrement(dbase7) | +      | 
| Timestamp (dbase7)    | @      |
| Double (dbase7)       | O      |



#Reading a DBF File

To read a DBF file, JavaDBF provides a DBFReader class. 
Following is a ready-to-compile, self-explanatory program describing almost all feature of the DBFReader class. 
Copy/paste this listing and compile it. Keep a .dbf file handy to pass to this program as its argument.

```java
import java.io.*;
import com.linuxense.javadbf.*;

public class JavaDBFReaderTest {

  public static void main(String args[]) {

    try {

      // create a DBFReader object
      InputStream inputStream  = new FileInputStream(args[0]);
      DBFReader reader = new DBFReader(inputStream); 

      // get the field count if you want for some reasons like the following

      int numberOfFields = reader.getFieldCount();

      // use this count to fetch all field information
      // if required

      for(int i = 0; i < numberOfFields; i++) {

        DBFField field = reader.getField(i);

        // do something with it if you want
        // refer the JavaDoc API reference for more details
        //
        System.out.println(field.getName());
      }

      // Now, lets us start reading the rows

      Object[] rowObjects;

      while((rowObjects = reader.nextRecord()) != null) {

        for(int i = 0; i < rowObjects.length; i++) {
          System.out.println(rowObjects[i]);
        }
      }

      // By now, we have itereated through all of the rows
      
      inputStream.close();
    }
    catch(DBFException e) {
      e.printStrackTrace();
    }
    catch(IOException e) {
      e.printStrackTrace();
    }
  }  
}  
```

#Writing a DBF File

The class complementary to DBFReader is the DBFWriter.While creating a .dbf data file you will have to deal with two aspects: 

1. define the fields and 
2. populate data. 

As mentioned above a dbf field is represented by the class DBFField. First, let us familiarise this class.

##Defining Fields

Create an object of DBFField class:

```java
	DBFField field = new DBFField();
	field.setName("emp_name"); // give a name to the field
	field.setType(DBFDataType.CHARACTER); // and set its type
	field.setFieldLength(25); // and length of the field
```

This is, now, a complete DBFField Object ready to use. 
We have to create as many DBFField Objects as we want to be in the .dbf file. 
The DBFWriter class accept DBFField in an array. 
Now, let's move on to the next step of populating data.

##Preparing DBFWriter Object

A DBFWriter is used for creating a .dbf file. First lets create a DBFWriter object by calling its constructor 
and then set the fields created (as explained above) by calling the setFields method.

```java
	DBFWriter writer = new DBFWriter();
	// fields is a non-empty array of DBFField objects
	writer.setFields(fields); 
```
Now, the DBFWriter Object is ready to be populated. 
The method for adding data to the DBFWriter is addRecord and it takes an Object array as its argument. 
This Object array is supposed contain values for the fields added with one-to-one correspondence with the fields set. 

Following is a complete program explaining all the steps described above:

```java
import com.linuxense.javadbf.*;
import java.io.*;

public class DBFWriterTest {

  public static void main(String args[])
  throws DBFException, IOException {

    // let us create field definitions first
    // we will go for 3 fields

    DBFField[] fields = new DBFField[3];

    fields[0] = new DBFField();
    fields[0].setName("emp_code");
    fields[0].setType(DBFDataType.CHARACTER);
    fields[0].setFieldLength(10);

    fields[1] = new DBFField();
    fields[1].setName("emp_name");
    fields[1].setType(DBFDataType.CHARACTER);
    fields[1].setFieldLength(20);

    fields[2] = new DBFField();
    fields[2].setName("salary");
    fields[2].setType(DBFDataType.NUMERIC);
    fields[2].setFieldLength(12);
    fields[2].setDecimalCount(2);

    DBFWriter writer = new DBFWriter();
    writer.setFields(fields);

    // now populate DBFWriter

    Object rowData[] = new Object[3];
    rowData[0] = "1000";
    rowData[1] = "John";
    rowData[2] = new Double(5000.00);

    writer.addRecord(rowData);

    rowData = new Object[3];
    rowData[0] = "1001";
    rowData[1] = "Lalit";
    rowData[2] = new Double(3400.00);

    writer.addRecord(rowData);

    rowData = new Object[3];
    rowData[0] = "1002";
    rowData[1] = "Rohit";
    rowData[2] = new Double(7350.00);

    writer.addRecord(rowData);

	// write to file
    FileOutputStream fos = new FileOutputStream(args[0]);
    writer.write(fos);
    fos.close();
  }
}
```

Keep in mind that till the write method is called, all the added data will be kept in memory. 
So, if you are planning to write huge amount of data make sure that it will be safely held in memory 
till it is written to disk and the DBFWriter object is garbage-collected.
 Read the Sync Mode section to know how JavaDBF to use a special feature of JavaDBF to overcome this.

## Sync Mode: Writing Records to File as They are Added

This is useful when JavaDBF is used to create a DBF with very large number of records. 
In this mode, instead of keeping records in memory for writing them once for all,
records are written to file as addRecord() is called. Here is how to write in Sync Mode.

Create DBFWriter instance by passing a File object which represents a new/non-existent or empty file.
And you are done! But, as in the normal mode, remember to call write() when have added all the records.
This will help JavaDBF to write the meta data with correct values. Here is a sample code:

```java
import com.linuxense.javadbf.*;
import java.io.*;

public class DBFWriterTest {

  public static void main(String args[])
  throws DBFException, IOException {

    // ...

    DBFWriter writer = new DBFWriter(new File("/path/to/a/new/file")); 
    // this DBFWriter object is now in Syc Mode
    
    // set fields 
    writer.setFields(fields);
    
    // add the data
    writer.addRecord(rowData);
    
    // close
    writer.close();

    // ...
  }
}
```

#Appending Records

From version 0.4.0 onwards JavaDBF supports appending of records to an existing DBF file. 
Use the same constructor used in Sync Mode to achieve this. 
But here the File object passed to the construction should represent the DBF file to which records are to be appended. 

It is illegal to call setFields in DBFWriter object created for appending. 
Here also it is required to call the write() method after adding all the records.

```java
import com.linuxense.javadbf.*;
import java.io.*;

public class DBFWriterTest {

  public static void main(String args[])
  throws DBFException, IOException {

    // ...

    DBFWriter writer = new DBFWriter(new File("/path/to/an/existing/dbfile")); 
    // this DBFWriter object is now in Syc Mode   
    
    // add the data (no setFields, because it's an existing file)
    writer.addRecord(rowData);
    
    // close
    writer.close();

    // ...
  }
}
```

#Building from sources

Clone the repository or download de tar file from releases page on github, run the Maven command:

    git clone https://github.com/albfernandez/javadbf.git
    cd javadbf
    mvn clean package

The result file is ``target/javadbf-0.8.0.jar``



#Links

https://en.wikipedia.org/wiki/.dbf



