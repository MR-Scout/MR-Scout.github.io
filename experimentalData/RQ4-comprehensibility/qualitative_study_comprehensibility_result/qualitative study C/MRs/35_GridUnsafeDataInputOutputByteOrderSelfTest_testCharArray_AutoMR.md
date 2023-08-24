NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
org.apache.ignite.internal.util.io.GridUnsafeDataInputOutputByteOrderSelfTest_testCharArray_AutoMR
original_MTC_FQS:
org.apache.ignite.internal.util.io.GridUnsafeDataInputOutputByteOrderSelfTest.testCharArray()
poj_dir:
apache/ignite/
original_MTC_class_path:
apache/ignite/modules/core/src/test/java/org/apache/ignite/internal/util/io/GridUnsafeDataInputOutputByteOrderSelfTest.java

# Codified MR:
```java
/**
 * @throws Exception If failed.
 */
public void testCharArray_AutoMR(char[] arr, GridUnsafeDataOutput out) throws Exception {
    for (int i = 0; i < ARR_LEN; i++) arr[i] = (char) RND.nextLong();
    out.writeCharArray(arr);
    byte[] outArr = out.internalArray();
    for (int i = 0; i < ARR_LEN; i++) assertEquals(arr[i], getCharByByteLE(outArr, i * 2 + LEN_BYTES));
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertEquals(arr[i], getCharByByteLE(outArr, i * 2 + LEN_BYTES)), line: 130 
source_input: ['arr', 'out'], first_invoked_method_name: writeCharArray, line: 125 
followUp_input: ['out'], second_invoked_method_name: internalArray, line: 127 


# Original MTC and related fields:
```java
/**
 * Array length.
 */
private static final int ARR_LEN = 16;
/**
 * Length bytes.
 */
private static final int LEN_BYTES = 4;
/**
 * Rnd.
 */
private static Random RND = new Random();
/**
 * Out.
 */
private GridUnsafeDataOutput out;
/**
 * In.
 */
private GridUnsafeDataInput in;
/**
 */
@Before
public void setUp() throws Exception {
    out = new GridUnsafeDataOutput(16 * 8 + LEN_BYTES);
    in = new GridUnsafeDataInput();
    in.inputStream(new ByteArrayInputStream(out.internalArray()));
}
/**
 * @throws Exception If failed.
 */
@Test
public void testCharArray() throws Exception {
    char[] arr = new char[ARR_LEN];
    for (int i = 0; i < ARR_LEN; i++) arr[i] = (char) RND.nextLong();
    out.writeCharArray(arr);
    byte[] outArr = out.internalArray();
    for (int i = 0; i < ARR_LEN; i++) assertEquals(arr[i], getCharByByteLE(outArr, i * 2 + LEN_BYTES));
    assertArrayEquals(arr, in.readCharArray());
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```
