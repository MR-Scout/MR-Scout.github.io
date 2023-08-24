NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
biweekly.io.TzUrlDotOrgGeneratorTest_cache_AutoMR
original_MTC_FQS:
biweekly.io.TzUrlDotOrgGeneratorTest.cache()
poj_dir:
mangstadt/biweekly/
original_MTC_class_path:
mangstadt/biweekly/src/test/java/biweekly/io/TzUrlDotOrgGeneratorTest.java

# Codified MR:
```java
public void cache_AutoMR(TzUrlDotOrgGenerator generator, TimeZone timezone) throws Exception {
    doReturn(ok()).when(generator).getInputStream(any(URI.class));
    VTimezone component1 = generator.generate(timezone);
    VTimezone component2 = generator.generate(timezone);
    VTimezone component3 = generator.generate(timezone);
    assertNotSame(component1, component2);
    assertNotSame(component2, component3);
    assertNotSame(component1, component3);
    verify(generator).getInputStream(any(URI.class));
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertNotSame(component1, component2), line: 143 
source_input: ['timezone', 'generator'], first_invoked_method_name: generate, line: 140 
followUp_input: ['timezone', 'generator'], second_invoked_method_name: generate, line: 141 
## MR instance2
relation_assertion: assertNotSame(component2, component3), line: 144 
source_input: ['timezone', 'generator'], first_invoked_method_name: generate, line: 141 
followUp_input: ['timezone', 'generator'], second_invoked_method_name: generate, line: 142 
## MR instance3
relation_assertion: assertNotSame(component1, component3), line: 145 
source_input: ['timezone', 'generator'], first_invoked_method_name: generate, line: 140 
followUp_input: ['timezone', 'generator'], second_invoked_method_name: generate, line: 142 


# Original MTC and related fields:
```java
private final TimeZone timezone = TimeZone.getTimeZone("America/New_York");
@Before
public void before() {
    TzUrlDotOrgGenerator.clearCache();
}
@Test
public void cache() throws Exception {
    TzUrlDotOrgGenerator generator = spy(new TzUrlDotOrgGenerator(true));
    doReturn(ok()).when(generator).getInputStream(any(URI.class));
    VTimezone component1 = generator.generate(timezone);
    VTimezone component2 = generator.generate(timezone);
    VTimezone component3 = generator.generate(timezone);
    assertNotSame(component1, component2);
    assertNotSame(component2, component3);
    assertNotSame(component1, component3);
    assertEquals("TEST", component1.getTimezoneId().getValue());
    assertEquals("TEST", component2.getTimezoneId().getValue());
    assertEquals("TEST", component3.getTimezoneId().getValue());
    verify(generator).getInputStream(any(URI.class));
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```