NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
dk.alexandra.fresco.lib.helper.SequentialProtocolProducerTest_testToString_AutoMR
original_MTC_FQS:
dk.alexandra.fresco.lib.helper.SequentialProtocolProducerTest.testToString()
poj_dir:
aicis/fresco/
original_MTC_class_path:
aicis/fresco/core/src/test/java/dk/alexandra/fresco/lib/helper/SequentialProtocolProducerTest.java

# Codified MR:
```java
public void testToString_AutoMR(SequentialProtocolProducer sequentialProtocolProducer) {
    String toString = sequentialProtocolProducer.toString();
    Assert.assertThat(sequentialProtocolProducer.toString(), Is.is(toString));
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: Assert.assertThat(sequentialProtocolProducer.toString(), Is.is(toString)), line: 22 
source_input: ['sequentialProtocolProducer'], first_invoked_method_name: toString, line: 18 
followUp_input: ['sequentialProtocolProducer'], second_invoked_method_name: toString, line: 22 


# Original MTC and related fields:
```java


@Test
public void testToString() {
    SequentialProtocolProducer sequentialProtocolProducer = new SequentialProtocolProducer(Collections.singletonList(new SingleProtocolProducer<>(new DummyBooleanNotProtocol(null))));
    String toString = sequentialProtocolProducer.toString();
    Assert.assertThat(toString, StringContains.containsString("SequentialProtocolProducer"));
    Assert.assertThat(toString, StringContains.containsString("SingleProtocolProducer"));
    Assert.assertThat(toString, StringContains.containsString("DummyBooleanNotProtocol"));
    Assert.assertThat(sequentialProtocolProducer.toString(), Is.is(toString));
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 2
 
## comments (if any): 
```txt
Trivial
我不理解这个 class 是做啥的
```
