NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
nl.jqno.equalsverifier.internal.prefabvalues.PrefabValuesTest_giveRedCopyFromFallbackFactory_AutoMR
original_MTC_FQS:
nl.jqno.equalsverifier.internal.prefabvalues.PrefabValuesTest.giveRedCopyFromFallbackFactory()
poj_dir:
jqno/equalsverifier/
original_MTC_class_path:
jqno/equalsverifier/equalsverifier-test-core/src/test/java/nl/jqno/equalsverifier/internal/prefabvalues/PrefabValuesTest.java

# Codified MR:
```java
public void giveRedCopyFromFallbackFactory_AutoMR(TypeTag POINT_TAG) {
    Point actual = pv.giveRedCopy(POINT_TAG);
    assertNotSame(pv.giveRed(POINT_TAG), actual);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertNotSame(pv.giveRed(POINT_TAG), actual), line: 94 
source_input: ['POINT_TAG'], first_invoked_method_name: giveRedCopy, line: 92 
followUp_input: ['POINT_TAG'], second_invoked_method_name: giveRed, line: 94 


# Original MTC and related fields:
```java
private static final TypeTag POINT_TAG = new TypeTag(Point.class);
private static final TypeTag INT_TAG = new TypeTag(int.class);
private PrefabValues pv;
private final int i;
private final int i;
@BeforeEach
public void setUp() {
    factoryCache.put(String.class, new AppendingStringTestFactory());
    factoryCache.put(int.class, values(42, 1337, 42));
    pv = new PrefabValues(factoryCache);
}
@Test
public void giveRedCopyFromFallbackFactory() {
    Point actual = pv.giveRedCopy(POINT_TAG);
    assertEquals(new Point(42, 42), actual);
    assertNotSame(pv.giveRed(POINT_TAG), actual);
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```