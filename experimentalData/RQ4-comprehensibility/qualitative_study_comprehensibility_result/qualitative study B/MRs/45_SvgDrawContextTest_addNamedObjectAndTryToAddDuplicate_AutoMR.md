NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.itextpdf.svg.renderers.SvgDrawContextTest_addNamedObjectAndTryToAddDuplicate_AutoMR
original_MTC_FQS:
com.itextpdf.svg.renderers.SvgDrawContextTest.addNamedObjectAndTryToAddDuplicate()
poj_dir:
itext/itext7/
original_MTC_class_path:
itext/itext7/svg/src/test/java/com/itextpdf/svg/renderers/SvgDrawContextTest.java

# Codified MR:
```java
public void addNamedObjectAndTryToAddDuplicate_AutoMR(ISvgNodeRenderer expectedOne, String dummyName, SvgDrawContext context) {
    ISvgNodeRenderer expectedTwo = new DummySvgNodeRenderer();
    context.addNamedObject(dummyName, expectedOne);
    context.addNamedObject(dummyName, expectedTwo);
    Object actual = context.getNamedObject(dummyName);
    Assert.assertEquals(expectedOne, actual);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: Assert.assertEquals(expectedOne, actual), line: 230 
source_input: ['dummyName', 'expectedOne', 'context'], first_invoked_method_name: addNamedObject, line: 227 
followUp_input: ['dummyName', 'context'], second_invoked_method_name: getNamedObject, line: 229 


# Original MTC and related fields:
```java
private SvgDrawContext context;
@Before
public void setUp() {
    tokenDoc = new PdfDocument(new PdfWriter(new ByteArrayOutputStream()));
    page1 = new PdfCanvas(tokenDoc.addNewPage());
    page2 = new PdfCanvas(tokenDoc.addNewPage());
    context = new SvgDrawContext(null, null);
}
@Test
public void addNamedObjectAndTryToAddDuplicate() {
    ISvgNodeRenderer expectedOne = new DummySvgNodeRenderer();
    ISvgNodeRenderer expectedTwo = new DummySvgNodeRenderer();
    String dummyName = "Ed";
    context.addNamedObject(dummyName, expectedOne);
    context.addNamedObject(dummyName, expectedTwo);
    Object actual = context.getNamedObject(dummyName);
    Assert.assertEquals(expectedOne, actual);
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```