NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
org.jsmpp.util.PDUByteBufferTest_initialLengthValidity_AutoMR
original_MTC_FQS:
org.jsmpp.util.PDUByteBufferTest.initialLengthValidity()
poj_dir:
opentelecoms-org/jsmpp/
original_MTC_class_path:
opentelecoms-org/jsmpp/jsmpp/src/test/java/org/jsmpp/util/PDUByteBufferTest.java

# Codified MR:
```java
public void initialLengthValidity_AutoMR(PDUByteBuffer byteBuffer) {
    byte[] pdu = byteBuffer.toBytes();
    assertEquals(pdu.length, byteBuffer.getCommandLengthValue());
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertEquals(pdu.length, byteBuffer.getCommandLengthValue()), line: 41 
source_input: ['byteBuffer'], first_invoked_method_name: toBytes, line: 39 
followUp_input: ['byteBuffer'], second_invoked_method_name: getCommandLengthValue, line: 41 


# Original MTC and related fields:
```java
private static final int INITIAL_LENGTH = INTEGER_LENGTH;
private PDUByteBuffer byteBuffer;
@BeforeMethod
public void setUp() {
    byteBuffer = new PDUByteBuffer(new SimpleCapacityPolicy());
}
@Test(groups = "checkintest")
public void initialLengthValidity() {
    byte[] pdu = byteBuffer.toBytes();
    assertEquals(pdu.length, INITIAL_LENGTH);
    assertEquals(pdu.length, byteBuffer.getCommandLengthValue());
    assertEquals(pdu.length, byteBuffer.getBytesLength());
}
@Test(groups = "checkintest")
public void initialLengthValidity() {
    byte[] pdu = byteBuffer.toBytes();
    assertEquals(pdu.length, INITIAL_LENGTH);
    assertEquals(pdu.length, byteBuffer.getCommandLengthValue());
    assertEquals(pdu.length, byteBuffer.getBytesLength());
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```