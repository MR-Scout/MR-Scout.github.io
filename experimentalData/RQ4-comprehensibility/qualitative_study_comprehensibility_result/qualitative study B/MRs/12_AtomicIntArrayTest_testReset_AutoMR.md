NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
cn.myperf4j.base.util.concurrent.AtomicIntArrayTest_testReset_AutoMR
original_MTC_FQS:
cn.myperf4j.base.util.concurrent.AtomicIntArrayTest.testReset()
poj_dir:
LinShunKang/MyPerf4J/
original_MTC_class_path:
LinShunKang/MyPerf4J/MyPerf4J-Base/src/test/java/cn/myperf4j/base/util/concurrent/AtomicIntArrayTest.java

# Codified MR:
```java
public void testReset_AutoMR(int i, AtomicIntArray atomicIntArray) {
    final int length = atomicIntArray.length();
    {
        atomicIntArray.set(i, i);
    }
    {
        Assert.assertEquals(i, atomicIntArray.get(i));
    }
    atomicIntArray.reset();
    {
        Assert.assertEquals(0, atomicIntArray.get(i));
    }
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: Assert.assertEquals(i, atomicIntArray.get(i)), line: 27 
source_input: ['i', 'i', 'atomicIntArray'], first_invoked_method_name: set, line: 23 
followUp_input: ['i'], second_invoked_method_name: get, line: 27 


# Original MTC and related fields:
```java
private final AtomicIntArray atomicIntArray = new AtomicIntArray(1024);
@Before
public void clear() {
    atomicIntArray.reset();
}
@Test
public void testReset() {
    final int length = atomicIntArray.length();
    for (int i = 0; i < length; i++) {
        atomicIntArray.set(i, i);
    }
    for (int i = 0; i < length; i++) {
        Assert.assertEquals(i, atomicIntArray.get(i));
    }
    atomicIntArray.reset();
    for (int i = 0; i < length; i++) {
        Assert.assertEquals(0, atomicIntArray.get(i));
    }
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```