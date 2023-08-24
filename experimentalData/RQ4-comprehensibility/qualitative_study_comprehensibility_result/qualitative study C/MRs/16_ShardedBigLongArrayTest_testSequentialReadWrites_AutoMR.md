NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.twitter.graphjet.hashing.ShardedBigLongArrayTest_testSequentialReadWrites_AutoMR
original_MTC_FQS:
com.twitter.graphjet.hashing.ShardedBigLongArrayTest.testSequentialReadWrites()
poj_dir:
twitter/GraphJet/
original_MTC_class_path:
twitter/GraphJet/graphjet-core/src/test/java/com/twitter/graphjet/hashing/ShardedBigLongArrayTest.java

# Codified MR:
```java
public void testSequentialReadWrites_AutoMR(int i, long entry) {
    int maxNumNodes = 1 << 16;
    int shardSize = 1 << 10;
    long nullEntry = -1L;
    BigLongArray shardedBigLongArray = new ShardedBigLongArray(maxNumNodes / 16, shardSize, nullEntry, new NullStatsReceiver());
    {
        assertEquals(nullEntry, shardedBigLongArray.getEntry(i));
        shardedBigLongArray.addEntry(entry, i);
        assertEquals(entry, shardedBigLongArray.getEntry(i));
    }
    {
        assertEquals(nullEntry, shardedBigLongArray.getEntry(maxNumNodes + i));
    }
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertEquals(entry, shardedBigLongArray.getEntry(i)), line: 27 
source_input: ['entry', 'i'], first_invoked_method_name: addEntry, line: 26 
followUp_input: ['i'], second_invoked_method_name: getEntry, line: 27 


# Original MTC and related fields:
```java


@Test
public void testSequentialReadWrites() {
    int maxNumNodes = 1 << 16;
    int shardSize = 1 << 10;
    long nullEntry = -1L;
    BigLongArray shardedBigLongArray = new ShardedBigLongArray(maxNumNodes / 16, shardSize, nullEntry, new NullStatsReceiver());
    for (int i = 0; i < maxNumNodes; i++) {
        long entry = i * 2;
        assertEquals(nullEntry, shardedBigLongArray.getEntry(i));
        shardedBigLongArray.addEntry(entry, i);
        assertEquals(entry, shardedBigLongArray.getEntry(i));
    }
    for (int i = 0; i < maxNumNodes; i++) {
        assertEquals(nullEntry, shardedBigLongArray.getEntry(maxNumNodes + i));
    }
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```
