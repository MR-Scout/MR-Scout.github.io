NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.itextpdf.layout.renderer.TextRendererTest_overflowWrapAnywhereItalicSimulationMaxWidth_AutoMR
original_MTC_FQS:
com.itextpdf.layout.renderer.TextRendererTest.overflowWrapAnywhereItalicSimulationMaxWidth()
poj_dir:
itext/itext7/
original_MTC_class_path:
itext/itext7/layout/src/test/java/com/itextpdf/layout/renderer/TextRendererTest.java

# Codified MR:
```java
public void overflowWrapAnywhereItalicSimulationMaxWidth_AutoMR(Text text, TextRenderer textRenderer) {
    text.setItalic();
    textRenderer.setParent(createDummyDocument().getRenderer());
    float maxWidthNoOverflowWrap = textRenderer.getMinMaxWidth().getMaxWidth();
    text.setProperty(Property.OVERFLOW_WRAP, OverflowWrapPropertyValue.ANYWHERE);
    float maxWidthAndOverflowWrap = textRenderer.getMinMaxWidth().getMaxWidth();
    Assert.assertEquals(maxWidthAndOverflowWrap, maxWidthNoOverflowWrap, 0.0001);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: Assert.assertEquals(maxWidthAndOverflowWrap, maxWidthNoOverflowWrap, 0.0001), line: 356 
source_input: ['textRenderer.getMinMaxWidth()'], first_invoked_method_name: getMaxWidth, line: 351 
followUp_input: ['textRenderer.getMinMaxWidth()'], second_invoked_method_name: getMaxWidth, line: 354 


# Original MTC and related fields:
```java


@Test
public void overflowWrapAnywhereItalicSimulationMaxWidth() {
    Text text = new Text("wow");
    text.setItalic();
    TextRenderer textRenderer = (TextRenderer) text.getRenderer();
    textRenderer.setParent(createDummyDocument().getRenderer());
    float maxWidthNoOverflowWrap = textRenderer.getMinMaxWidth().getMaxWidth();
    text.setProperty(Property.OVERFLOW_WRAP, OverflowWrapPropertyValue.ANYWHERE);
    float maxWidthAndOverflowWrap = textRenderer.getMinMaxWidth().getMaxWidth();
    Assert.assertEquals(maxWidthAndOverflowWrap, maxWidthNoOverflowWrap, 0.0001);
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 2
 
## comments (if any): 
```txt
Cannot follow the input relation if not checking the original testcaes
```