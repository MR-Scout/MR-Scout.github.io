NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
dk.alexandra.fresco.suite.dummy.bool.DummyBooleanSBoolTest_testToString_AutoMR
original_MTC_FQS:
dk.alexandra.fresco.suite.dummy.bool.DummyBooleanSBoolTest.testToString()
poj_dir:
aicis/fresco/
original_MTC_class_path:
aicis/fresco/core/src/test/java/dk/alexandra/fresco/suite/dummy/bool/DummyBooleanSBoolTest.java

# Codified MR:
```java
public void testToString_AutoMR(DummyBooleanSBool value) throws Exception {
    String toString = value.toString();
    Assert.assertThat(value.toString(), Is.is(toString));
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: Assert.assertThat(value.toString(), Is.is(toString)), line: 19 
source_input: ['value'], first_invoked_method_name: toString, line: 17 
followUp_input: ['value'], second_invoked_method_name: toString, line: 19 


# Original MTC and related fields:
```java


@Test
public void testToString() throws Exception {
    DummyBooleanSBool value = new DummyBooleanSBool(true);
    String toString = value.toString();
    Assert.assertThat(toString, StringContains.containsString("true"));
    Assert.assertThat(value.toString(), Is.is(toString));
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 
## comments (if any): 
```txt

```