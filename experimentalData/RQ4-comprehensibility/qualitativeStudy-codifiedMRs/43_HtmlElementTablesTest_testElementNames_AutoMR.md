NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
org.owasp.html.HtmlElementTablesTest_testElementNames_AutoMR
original_MTC_FQS:
org.owasp.html.HtmlElementTablesTest.testElementNames()
poj_dir:
OWASP/java-html-sanitizer/
original_MTC_class_path:
OWASP/java-html-sanitizer/src/test/java/org/owasp/html/HtmlElementTablesTest.java

# Codified MR:
```java
public void testElementNames_AutoMR(int ei, HtmlElementTables t) {
    {
        String s = t.canonNameForIndex(ei);
        assertEquals(ei, t.indexForName(s));
        ++ei;
    }
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertEquals(ei, t.indexForName(s)), line: 34 
source_input: ['ei', 't'], first_invoked_method_name: canonNameForIndex, line: 33 
followUp_input: ['s', 't'], second_invoked_method_name: indexForName, line: 34 


# Original MTC and related fields:
```java
HtmlElementTables t;
@Before
@Override
public void setUp() throws Exception {
    super.setUp();
    t = HtmlElementTables.get();
}
@Test
public void testElementNames() {
    assertTrue(t.indexForName("a") != t.indexForName("b"));
    assertTrue(t.indexForName("p") != t.indexForName("q"));
    assertTrue(t.indexForName("customclass") >= 0);
    for (int ei = 0, nei = t.nElementTypes(); ei < nei; ++ei) {
        String s = t.canonNameForIndex(ei);
        assertEquals(ei, t.indexForName(s));
        ++ei;
    }
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 
## comments (if any): 
```txt

```