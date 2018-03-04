# JavaDBF Library Tutorial


(for JavaDBF ver. 0.4.0 and above)\
 Anil Kumar K.\
 anil at linuxense dot com\
 Linuxense Information Systems Pvt. Ltd.,
Trivandrum, India

## 1. Introduction


JavaDBF is a Java library for reading and writing XBase files. There are
plenty of legacy applications around with `.dbf` as their primary
storage format. JavaDBF was initially written for data transfer with
such applications.

Other than that, there are requirements to export data from a Java
application to a spreadshet program like GNumeric, Excel or Lotus 123. A
DBF file would be more appropriate in such situations rather than a CSV
or an HTML file because a DBF file can carry field type information.
More over, XBase format is like an Open-standard; it is understood by
almost all spreadsheet programms.

## 2. Getting and Installing

Obtain the latest version of JavaDBF from
http://sarovar.org/projects/javadbf/.
Create a folder in a convenient location and run:

```bash
   tar xvfz javadbf-x.x.x-tar.gz 
   cd javadbf-x.x.x
```

In this folder you will find `javadbf.jar` which contains the library.
Include this jar file in your `$CLASSPATH` variable. You are ready to
go.

# 3. Overview of the Library

JavaDBF has a simple API of its own and it does not implement the JDBC
API. It is designed this way because JavaDBF is not indedned to support
full-blown RDBMS-style database interaction. And you are not supposed to
use it like a back-end; it just doesn't work that way. Also, JavaDBF is
not designed to be thread-safe; keep that in mind when you design
threaded applications.

JavaDBF comes in the package `com.linuxense.javadbf`. Import that
package in your Java code. Following examples will familiarise you with
its APIs.

### 3.1. Data Type Mapping

In version 0.3.2, JavaDBF supports almost all XBase data types except
Memo field. While reading, those types are interpretted as appropriate
Java types. Following table shows the mapping scheme.

| XBase Type  | XBase Symbol | Java Type used in JavaDBF |
|----------   | ------------ | ---------------------------
|Character    | C            | java.lang.String          |
|Numeric      | N            | java.math.BigDecimal      |
|Double       | F            | java.math.BigDecimal      |
|Logical      | L            | java.lang.Boolean         |
|Date         | D            | java.util.Date            |

## 4. Reading a DBF File

To read a DBF file, JavaDBF provides a DBFReader class. Following is a
ready-to-compile, self-explanatory program describing almost all feature
of the `DBFReader` class. Copy/paste this listing and compile it. Keep a
`.dbf` file handy to pass to this program as its argument.

```java
    import java.io.*;
    import com.linuxense.javadbf.*;

    public class JavaDBFReaderTest {

      public static void main( String args[]) {

        try {

          // create a DBFReader object
          //
          InputStream inputStream  = new FileInputStream( args[ 0]); // take dbf file as program argument
          DBFReader reader = new DBFReader( inputStream); 

          // get the field count if you want for some reasons like the following
          //
          int numberOfFields = reader.getFieldCount();

          // use this count to fetch all field information
          // if required
          //
          for( int i=0; i<numberOfFields; i++) {

            DBFField field = reader.getField( i);

            // do something with it if you want
            // refer the JavaDoc API reference for more details
            //
            System.out.println( field.getName());
          }

          // Now, lets us start reading the rows
          //
          Object []rowObjects;

          while( (rowObjects = reader.nextRecord()) != null) {

            for( int i=0; i<rowObjects.length; i++) {

              System.out.println( rowObjects[i]);
            }
          }

          // By now, we have itereated through all of the rows
          
          inputStream.close();
        }
        catch( DBFException e) {

          System.out.println( e.getMessage());
        }
        catch( IOException e) {

          System.out.println( e.getMessage());
        }
      }  
    }  
```

## 5. Writing a DBF File


The class complementary to `DBFReader` is the `DBFWriter`.While creating
a `.dbf` data file you will have to deal with two aspects: 1. define the
fields and 2. populate data. As mentioned above a dbf field is
represented by the class `DBFField`. First, let us familiarise this
class.

### 5.1. Defining Fields

Create an object of `DBFField` class:

```java
      DBFField field = new DBFField();
      field.setField( "emp_name"); // give a name to the field
      field.setDataType( DBFField.FIELD_TYPE_C); // and set its type
      field.setFieldLength( 25); // and length of the field
```

This is, now, a complete `DBFField` Object ready to use. We have to
create as many `DBFField` Objects as we want to be in the `.dbf` file.
The `DBFWriter` class accept `DBFField` in an array. Now, let's move on
to the next step of populating data.

### 5.2. Preparing `DBFWriter` Object.

A `DBFWriter` is used for creating a `.dbf` file. First lets create a
`DBFWriter` object by calling its constructor and then set the fields
created (as explained above) by calling the `setFields` method.

```java
    DBFWriter writer = new DBFWriter();
    writer.setFields( fields); // fields is a non-empty array of DBFField objects
```

Now, the `DBFWriter` Object is ready to be populated. The method for
adding data to the `DBFWriter` is `addRecord` and it takes an Object
array as its argument. This Object array is supposed contain values for
the fields added with one-to-one correspondence with the fields set.

Following is a complete program explaining all the steps described
above:

```java
    import com.linuxense.javadbf.*;
    import java.io.*;

    public class DBFWriterTest {

      public static void main( String args[])
      throws DBFException, IOException {

        // let us create field definitions first
        // we will go for 3 fields
        //
        DBFField fields[] = new DBFField[ 3];

        fields[0] = new DBFField();
        fields[0].setName( "emp_code");
        fields[0].setDataType( DBFField.FIELD_TYPE_C);
        fields[0].setFieldLength( 10);

        fields[1] = new DBFField();
        fields[1].setField( "emp_name");
        fields[1].setDataType( DBFField.FIELD_TYPE_C);
        fields[1].setFieldLength( 20);

        fields[2] = new DBFField();
        fields[2].setField( "salary");
        fields[2].setDataType( DBFField.FIELD_TYPE_N);
        fields[2].setFieldLength( 12);
        fields[2].setDecimalCount( 2);

        DBFWriter writer = new DBFWriter();
        writer.setFields( fields);

        // now populate DBFWriter
        //

        Object rowData[] = new Object[3];
        rowData[0] = "1000";
        rowData[1] = "John";
        rowData[2] = new Double( 5000.00);

        writer.addRecord( rowData);

        rowData = new Object[3];
        rowData[0] = "1001";
        rowData[1] = "Lalit";
        rowData[2] = new Double( 3400.00);

        writer.addRecord( rowData);

        rowData = new Object[3];
        rowData[0] = "1002";
        rowData[1] = "Rohit";
        rowData[2] = new Double( 7350.00);

        writer.addRecord( rowData);

        FileOutputStream fos = new FileOutputStream( args[0]);
        writer.write( fos);
        fos.close();
      }
    }
```

Keep in mind that till the `write` method is called, all the added data
will be kept in memory. So, if you are planning to write huge amount of
data make sure that it will be safely held in memory till it is written
to disk and the `DBFWriter` object is garbage-collected. Read the
Sync Mode section to know how JavaDBF to use a
special feature of JavaDBF to overcome this.

### 5.3. Sync Mode --Writing Records to File as They are Added

This is useful when JavaDBF is used to create a DBF with very large
number of records. In this mode, instead of keeping records in memory
for writing them once for all, records are written to file as
`addRecord()` is called. Here is how to write in *Sync Mode*:

Create DBFWriter instance by passing a `File` object which represents a
new/non-existent or empty file. And you are done! But, as in the normal
mode, remember to call `write()` when have added all the records. This
will help JavaDBF to write the meta data with correct values. Here is a
sample code:
```java
    import com.linuxense.javadbf.*;
    import java.io.*;

    public class DBFWriterTest {

      public static void main( String args[])
      throws DBFException, IOException {

        // ...

        DBFWriter writer = new DBFWriter( new File( "/path/to/a/new/file")); /* this DBFWriter object is now in Syc Mode */
        // ...
      }
    }   
```

## 6. Appending Records


From version 0.4.0 onwards JavaDBF supports appending of records to an
existing DBF file. Use the same constructor used in Sync
Mode to achieve this. But here the `File` object passed to
the construction should represent the DBF file to which records are to
be appended.

It is illegal to call `setFields` in DBFWriter object created for
appending. Here also it is required to call the `write()` method after
adding all the records.

## 7. Planned Features


1.  Support for memo fields.

* * * * *

© 2003, 2004 Anil Kumar Krishnan Nair, Linuxense