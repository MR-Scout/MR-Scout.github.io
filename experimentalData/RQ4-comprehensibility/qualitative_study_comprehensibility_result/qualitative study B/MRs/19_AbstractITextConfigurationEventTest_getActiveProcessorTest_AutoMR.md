NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.itextpdf.commons.actions.AbstractITextConfigurationEventTest_getActiveProcessorTest_AutoMR
original_MTC_FQS:
com.itextpdf.commons.actions.AbstractITextConfigurationEventTest.getActiveProcessorTest()
poj_dir:
itext/itext7/
original_MTC_class_path:
itext/itext7/commons/src/test/java/com/itextpdf/commons/actions/AbstractITextConfigurationEventTest.java

# Codified MR:
```java
public void getActiveProcessorTest_AutoMR(ITextProductEventProcessor processor) {
    AbstractITextConfigurationEvent event = new TestAbstractITextConfigurationEvent();
    event.addProcessor(processor);
    Assert.assertEquals(processor, event.getActiveProcessor(processor.getProductName()));
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: Assert.assertEquals(processor, event.getActiveProcessor(processor.getProductName())), line: 85 
source_input: ['processor'], first_invoked_method_name: addProcessor, line: 83 
followUp_input: ['processor.getProductName()'], second_invoked_method_name: getActiveProcessor, line: 85 


# Original MTC and related fields:
```java


@Test
public void getActiveProcessorTest() {
    AbstractITextConfigurationEvent event = new TestAbstractITextConfigurationEvent();
    ITextProductEventProcessor processor = new TestITextProductEventProcessor();
    event.addProcessor(processor);
    Assert.assertEquals(processor, event.getActiveProcessor(processor.getProductName()));
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 4
 
## comments (if any): 
```txt
Don't know how to construct a different input. Instantiate an anonymous class. Not sure it is correct or not.
```