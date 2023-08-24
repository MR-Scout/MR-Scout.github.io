NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.moilioncircle.redis.replicator.util.ByteArrayTest_test_AutoMR
original_MTC_FQS:
com.moilioncircle.redis.replicator.util.ByteArrayTest.test()
poj_dir:
leonchen83/redis-replicator/
original_MTC_class_path:
leonchen83/redis-replicator/src/test/java/com/moilioncircle/redis/replicator/util/ByteArrayTest.java

# Codified MR:
```java
public void test_AutoMR(ByteArray bytes, int i, byte b) {
    String str = "sdajkl;jlqwjqejqweq89080c中jlxczksaouwq9823djadj";
    byte[] b1 = str.getBytes();
    {
        bytes.set(i, b);
        assertEquals(b, bytes.get(i));
        i++;
    }
    ByteArray bytes1 = new ByteArray(str.getBytes().length - 10, 10);
    ByteArray.arraycopy(bytes, 10, bytes1, 0, bytes.length - 10);
    str = "sdajk";
    ByteArray bytes2 = new ByteArray(str.getBytes().length, 10);
    b1 = str.getBytes();
    i = 0;
    {
        bytes2.set(i, b);
        assertEquals(b, bytes2.get(i));
        i++;
    }
    ByteArray bytes3 = new ByteArray(bytes2.length() - 1, 10);
    ByteArray.arraycopy(bytes2, 1, bytes3, 0, bytes2.length() - 1);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertEquals(b, bytes.get(i)), line: 40 
source_input: ['i', 'b', 'bytes'], first_invoked_method_name: set, line: 39 
followUp_input: ['i', 'bytes'], second_invoked_method_name: get, line: 40 


# Original MTC and related fields:
```java


@Test
public void test() {
    String str = "sdajkl;jlqwjqejqweq89080c中jlxczksaouwq9823djadj";
    ByteArray bytes = new ByteArray(str.getBytes().length, 10);
    byte[] b1 = str.getBytes();
    int i = 0;
    for (byte b : b1) {
        bytes.set(i, b);
        assertEquals(b, bytes.get(i));
        i++;
    }
    ByteArray bytes1 = new ByteArray(str.getBytes().length - 10, 10);
    ByteArray.arraycopy(bytes, 10, bytes1, 0, bytes.length - 10);
    assertEquals(str.substring(10), getString(bytes1));
    str = "sdajk";
    ByteArray bytes2 = new ByteArray(str.getBytes().length, 10);
    b1 = str.getBytes();
    i = 0;
    for (byte b : b1) {
        bytes2.set(i, b);
        assertEquals(b, bytes2.get(i));
        i++;
    }
    assertEquals(getString(bytes2), "sdajk");
    ByteArray bytes3 = new ByteArray(bytes2.length() - 1, 10);
    ByteArray.arraycopy(bytes2, 1, bytes3, 0, bytes2.length() - 1);
    assertEquals(str.substring(1), getString(bytes3));
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 
## comments (if any): 
```txt

```