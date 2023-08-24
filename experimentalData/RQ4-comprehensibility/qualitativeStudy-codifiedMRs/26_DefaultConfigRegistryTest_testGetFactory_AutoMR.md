NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.ctrip.framework.apollo.spi.DefaultConfigRegistryTest_testGetFactory_AutoMR
original_MTC_FQS:
com.ctrip.framework.apollo.spi.DefaultConfigRegistryTest.testGetFactory()
poj_dir:
apolloconfig/apollo/
original_MTC_class_path:
apolloconfig/apollo/apollo-client/src/test/java/com/ctrip/framework/apollo/spi/DefaultConfigRegistryTest.java

# Codified MR:
```java
public void testGetFactory_AutoMR(String someNamespace, ConfigFactory someConfigFactory, DefaultConfigRegistry defaultConfigRegistry) throws Exception {
    defaultConfigRegistry.register(someNamespace, someConfigFactory);
    assertThat("Should return the registered config factory", defaultConfigRegistry.getFactory(someNamespace), equalTo(someConfigFactory));
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertThat("Should return the registered config factory", defaultConfigRegistry.getFactory(someNamespace), equalTo(someConfigFactory)), line: 48 
source_input: ['someNamespace', 'someConfigFactory', 'defaultConfigRegistry'], first_invoked_method_name: register, line: 46 
followUp_input: ['someNamespace', 'defaultConfigRegistry'], second_invoked_method_name: getFactory, line: 49 


# Original MTC and related fields:
```java
private DefaultConfigRegistry defaultConfigRegistry;
@Before
public void setUp() throws Exception {
    defaultConfigRegistry = new DefaultConfigRegistry();
}
@Test
public void testGetFactory() throws Exception {
    String someNamespace = "someName";
    ConfigFactory someConfigFactory = new MockConfigFactory();
    defaultConfigRegistry.register(someNamespace, someConfigFactory);
    assertThat("Should return the registered config factory", defaultConfigRegistry.getFactory(someNamespace), equalTo(someConfigFactory));
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 
## comments (if any): 
```txt

```