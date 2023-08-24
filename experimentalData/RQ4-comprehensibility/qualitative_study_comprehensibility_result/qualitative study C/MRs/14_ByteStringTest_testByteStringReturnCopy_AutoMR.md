NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
org.opcfoundation.ua.builtintypes.ByteStringTest_testByteStringReturnCopy_AutoMR
original_MTC_FQS:
org.opcfoundation.ua.builtintypes.ByteStringTest.testByteStringReturnCopy()
poj_dir:
OPCFoundation/UA-Java-Legacy/
original_MTC_class_path:
OPCFoundation/UA-Java-Legacy/src/test/java/org/opcfoundation/ua/builtintypes/ByteStringTest.java

# Codified MR:
```java
public void testByteStringReturnCopy_AutoMR(byte[] b) throws Exception {
    // tests that returned byte array is different java object
    ByteString bs = ByteString.valueOf(b);
    assertNotSame(bs.getValue(), b);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertNotSame(bs.getValue(), b), line: 54 
source_input: ['b'], first_invoked_method_name: valueOf, line: 53 
followUp_input: ['bs'], second_invoked_method_name: getValue, line: 54 


# Original MTC and related fields:
```java


@Test
public void testByteStringReturnCopy() throws Exception {
    // tests that returned byte array is different java object
    byte[] b = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    ByteString bs = ByteString.valueOf(b);
    assertNotSame(bs.getValue(), b);
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 2
 
## comments (if any): 
```txt
    cannot understand why such a relation hold

```
