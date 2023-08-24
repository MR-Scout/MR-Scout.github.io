NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
ru.yandex.clickhouse.jdbcbridge.core.ByteBufferTest_testWriteAndRead_AutoMR
original_MTC_FQS:
ru.yandex.clickhouse.jdbcbridge.core.ByteBufferTest.testWriteAndRead()
poj_dir:
ClickHouse/clickhouse-jdbc-bridge/
original_MTC_class_path:
ClickHouse/clickhouse-jdbc-bridge/src/test/java/ru/yandex/clickhouse/jdbcbridge/core/ByteBufferTest.java

# Codified MR:
```java
public void testWriteAndRead_AutoMR(ByteBuffer buffer, short s1, short s2, int i1, int i2, long l2) {
    byte b1 = Byte.MAX_VALUE;
    byte b2 = Byte.MIN_VALUE;
    byte[] bytes1 = new byte[] { b2, b1 };
    byte[] bytes2 = new byte[] { b2, b1 };
    long l1 = Long.MAX_VALUE;
    float f1 = Float.MAX_VALUE;
    float f2 = Float.MIN_VALUE;
    double d1 = Double.MAX_VALUE;
    double d2 = Double.MIN_VALUE;
    String str1 = "";
    String str2 = "1\r2\n3\ta,b.c;:$@^\\&*(!@#%`~-+=|[]{}'\"?/<>)";
    Date date1 = new Date(MILLIS_IN_DAY);
    Date date2 = new Date(DATETIME_MAX / MILLIS_IN_DAY * MILLIS_IN_DAY);
    java.sql.Timestamp dt1 = new java.sql.Timestamp(MILLIS_IN_DAY + 1000L);
    java.sql.Timestamp dt2 = new java.sql.Timestamp(DATETIME_MAX / MILLIS_IN_DAY * MILLIS_IN_DAY + 1000L);
    java.sql.Timestamp xdt1 = new java.sql.Timestamp(MILLIS_IN_DAY + 1L);
    java.sql.Timestamp xdt2 = new java.sql.Timestamp(DATETIME_MAX / MILLIS_IN_DAY * MILLIS_IN_DAY + 1L);
    buffer.writeNull();
    buffer.writeNonNull();
    buffer.writeByte(b1);
    buffer.writeByte(b2);
    // buffer.writeBytes(bytes1);
    // buffer.writeBytes(bytes2);
    buffer.writeInt8(b1);
    buffer.writeInt8(b2);
    buffer.writeUInt8(b1 + 1);
    buffer.writeUInt8(Math.abs(b2) - 1);
    buffer.writeInt16(s1);
    buffer.writeInt16(s2);
    buffer.writeUInt16(s1 + 1);
    buffer.writeUInt16(Math.abs(s2) - 1);
    buffer.writeInt32(i1);
    buffer.writeInt32(i2);
    buffer.writeUInt32(i1 + 1L);
    buffer.writeUInt32(i2 * -1L - 1L);
    buffer.writeInt64(l1);
    buffer.writeInt64(l2);
    buffer.writeUInt64(l1);
    buffer.writeUInt64(BigInteger.valueOf(l1).add(BigInteger.ONE));
    buffer.writeUInt64(BigInteger.valueOf(l2).multiply(BigInteger.valueOf(-1L)).subtract(BigInteger.ONE));
    buffer.writeFloat32(f1);
    buffer.writeFloat32(f2);
    buffer.writeFloat64(d1);
    buffer.writeFloat64(d2);
    buffer.writeDecimal(BigDecimal.valueOf(l1), 19, 4);
    buffer.writeString(str1);
    buffer.writeString(str2);
    buffer.writeDate(date1);
    buffer.writeDate(date2);
    buffer.writeDateTime(dt1);
    buffer.writeDateTime(dt2, TimeZone.getTimeZone("UTC"));
    buffer.writeDateTime64(xdt1, 3);
    buffer.writeDateTime64(xdt2, 3);
    // buffer.readBytes(bytes1);
    // assertEquals(bytes1, bytes2);
    // buffer.readBytes(bytes2, 0, bytes2.length);
    // assertEquals(bytes2, bytes1);
    assertEquals(buffer.readInt8(), b1);
    assertEquals(buffer.readInt16(), s1);
    assertEquals(buffer.readInt16(), s2);
    assertEquals(buffer.readInt32(), i1);
    assertEquals(buffer.readInt32(), i2);
    assertEquals(buffer.readInt64(), l2);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance7
relation_assertion: assertEquals(buffer.readInt16(), s1), line: 186 
source_input: ['s1', 'buffer'], first_invoked_method_name: writeInt16, line: 147 
followUp_input: ['buffer'], second_invoked_method_name: readInt16, line: 186 
## MR instance8
relation_assertion: assertEquals(buffer.readInt16(), s2), line: 187 
source_input: ['s2', 'buffer'], first_invoked_method_name: writeInt16, line: 148 
followUp_input: ['buffer'], second_invoked_method_name: readInt16, line: 187 
## MR instance11
relation_assertion: assertEquals(buffer.readInt32(), i1), line: 190 
source_input: ['i1', 'buffer'], first_invoked_method_name: writeInt32, line: 151 
followUp_input: ['buffer'], second_invoked_method_name: readInt32, line: 190 
## MR instance12
relation_assertion: assertEquals(buffer.readInt32(), i2), line: 191 
source_input: ['i2', 'buffer'], first_invoked_method_name: writeInt32, line: 152 
followUp_input: ['buffer'], second_invoked_method_name: readInt32, line: 191 
## MR instance16
relation_assertion: assertEquals(buffer.readInt64(), l2), line: 195 
source_input: ['l2'], first_invoked_method_name: writeInt64, line: 156 
followUp_input: ['buffer'], second_invoked_method_name: readInt64, line: 195 


# Original MTC and related fields:
```java


@Test(groups = { "unit" })
public void testWriteAndRead() {
    ByteBuffer buffer = ByteBuffer.newInstance(100);
    byte b1 = Byte.MAX_VALUE;
    byte b2 = Byte.MIN_VALUE;
    byte[] bytes1 = new byte[] { b2, b1 };
    byte[] bytes2 = new byte[] { b2, b1 };
    short s1 = Short.MAX_VALUE;
    short s2 = Short.MIN_VALUE;
    int i1 = Integer.MAX_VALUE;
    int i2 = Integer.MIN_VALUE;
    long l1 = Long.MAX_VALUE;
    long l2 = Long.MIN_VALUE;
    float f1 = Float.MAX_VALUE;
    float f2 = Float.MIN_VALUE;
    double d1 = Double.MAX_VALUE;
    double d2 = Double.MIN_VALUE;
    String str1 = "";
    String str2 = "1\r2\n3\ta,b.c;:$@^\\&*(!@#%`~-+=|[]{}'\"?/<>)";
    Date date1 = new Date(MILLIS_IN_DAY);
    Date date2 = new Date(DATETIME_MAX / MILLIS_IN_DAY * MILLIS_IN_DAY);
    java.sql.Timestamp dt1 = new java.sql.Timestamp(MILLIS_IN_DAY + 1000L);
    java.sql.Timestamp dt2 = new java.sql.Timestamp(DATETIME_MAX / MILLIS_IN_DAY * MILLIS_IN_DAY + 1000L);
    java.sql.Timestamp xdt1 = new java.sql.Timestamp(MILLIS_IN_DAY + 1L);
    java.sql.Timestamp xdt2 = new java.sql.Timestamp(DATETIME_MAX / MILLIS_IN_DAY * MILLIS_IN_DAY + 1L);
    buffer.writeNull();
    buffer.writeNonNull();
    buffer.writeByte(b1);
    buffer.writeByte(b2);
    // buffer.writeBytes(bytes1);
    // buffer.writeBytes(bytes2);
    buffer.writeInt8(b1);
    buffer.writeInt8(b2);
    buffer.writeUInt8(b1 + 1);
    buffer.writeUInt8(Math.abs(b2) - 1);
    buffer.writeInt16(s1);
    buffer.writeInt16(s2);
    buffer.writeUInt16(s1 + 1);
    buffer.writeUInt16(Math.abs(s2) - 1);
    buffer.writeInt32(i1);
    buffer.writeInt32(i2);
    buffer.writeUInt32(i1 + 1L);
    buffer.writeUInt32(i2 * -1L - 1L);
    buffer.writeInt64(l1);
    buffer.writeInt64(l2);
    buffer.writeUInt64(l1);
    buffer.writeUInt64(BigInteger.valueOf(l1).add(BigInteger.ONE));
    buffer.writeUInt64(BigInteger.valueOf(l2).multiply(BigInteger.valueOf(-1L)).subtract(BigInteger.ONE));
    buffer.writeFloat32(f1);
    buffer.writeFloat32(f2);
    buffer.writeFloat64(d1);
    buffer.writeFloat64(d2);
    buffer.writeDecimal(BigDecimal.valueOf(l1), 19, 4);
    buffer.writeString(str1);
    buffer.writeString(str2);
    buffer.writeDate(date1);
    buffer.writeDate(date2);
    buffer.writeDateTime(dt1);
    buffer.writeDateTime(dt2, TimeZone.getTimeZone("UTC"));
    buffer.writeDateTime64(xdt1, 3);
    buffer.writeDateTime64(xdt2, 3);
    assertEquals(buffer.readNull(), true);
    assertEquals(buffer.readNull(), false);
    assertEquals(buffer.readByte(), b1);
    assertEquals(buffer.readByte(), b2);
    // buffer.readBytes(bytes1);
    // assertEquals(bytes1, bytes2);
    // buffer.readBytes(bytes2, 0, bytes2.length);
    // assertEquals(bytes2, bytes1);
    assertEquals(buffer.readInt8(), b1);
    assertEquals(buffer.readInt8(), b2);
    assertEquals(buffer.readUInt8(), b1 + 1);
    assertEquals(buffer.readUInt8(), Math.abs(b2) - 1);
    assertEquals(buffer.readInt16(), s1);
    assertEquals(buffer.readInt16(), s2);
    assertEquals(buffer.readUInt16(), s1 + 1);
    assertEquals(buffer.readUInt16(), Math.abs(s2) - 1);
    assertEquals(buffer.readInt32(), i1);
    assertEquals(buffer.readInt32(), i2);
    assertEquals(buffer.readUInt32(), i1 + 1L);
    assertEquals(buffer.readUInt32(), i2 * -1L - 1L);
    assertEquals(buffer.readInt64(), l1);
    assertEquals(buffer.readInt64(), l2);
    assertEquals(buffer.readUInt64().longValue(), l1);
    assertEquals(buffer.readUInt64(), BigInteger.valueOf(l1).add(BigInteger.ONE));
    assertEquals(buffer.readUInt64(), BigInteger.valueOf(l2).multiply(BigInteger.valueOf(-1L)).subtract(BigInteger.ONE));
    assertEquals(buffer.readFloat32(), f1);
    assertEquals(buffer.readFloat32(), f2);
    assertEquals(buffer.readFloat64(), d1);
    assertEquals(buffer.readFloat64(), d2);
    assertEquals(buffer.readDecimal(19, 4), BigDecimal.valueOf(l1, 4).multiply(BigDecimal.valueOf(10000L)));
    assertEquals(buffer.readString(), str1);
    assertEquals(buffer.readString(), str2);
    assertEquals(buffer.readDate(), date1);
    assertEquals(buffer.readDate(), date2);
    assertEquals(buffer.readDateTime(), dt1);
    assertEquals(buffer.readDateTime(TimeZone.getTimeZone("UTC")), dt2);
    assertEquals(buffer.readDateTime64(), xdt1);
    assertEquals(buffer.readDateTime64(), xdt2);
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```