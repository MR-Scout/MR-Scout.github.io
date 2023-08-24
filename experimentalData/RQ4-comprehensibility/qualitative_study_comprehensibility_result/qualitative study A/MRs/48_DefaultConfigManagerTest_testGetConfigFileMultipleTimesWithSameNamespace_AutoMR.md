NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.ctrip.framework.apollo.internals.DefaultConfigManagerTest_testGetConfigFileMultipleTimesWithSameNamespace_AutoMR
original_MTC_FQS:
com.ctrip.framework.apollo.internals.DefaultConfigManagerTest.testGetConfigFileMultipleTimesWithSameNamespace()
poj_dir:
apolloconfig/apollo/
original_MTC_class_path:
apolloconfig/apollo/apollo-client/src/test/java/com/ctrip/framework/apollo/internals/DefaultConfigManagerTest.java

# Codified MR:
```java
public void testGetConfigFileMultipleTimesWithSameNamespace_AutoMR(String someNamespace, ConfigFileFormat someConfigFileFormat, DefaultConfigManager defaultConfigManager) throws Exception {
    ConfigFile someConfigFile = defaultConfigManager.getConfigFile(someNamespace, someConfigFileFormat);
    ConfigFile anotherConfigFile = defaultConfigManager.getConfigFile(someNamespace, someConfigFileFormat);
    assertThat("Get config file multiple times with the same namespace should return the same config file instance", someConfigFile, equalTo(anotherConfigFile));
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertThat("Get config file multiple times with the same namespace should return the same config file instance", someConfigFile, equalTo(anotherConfigFile)), line: 105 
source_input: ['someNamespace', 'someConfigFileFormat', 'defaultConfigManager'], first_invoked_method_name: getConfigFile, line: 101 
followUp_input: ['someNamespace', 'someConfigFileFormat', 'defaultConfigManager'], second_invoked_method_name: getConfigFile, line: 103 


# Original MTC and related fields:
```java
private DefaultConfigManager defaultConfigManager;
@Before
public void setUp() throws Exception {
    MockInjector.setInstance(ConfigFactoryManager.class, new MockConfigFactoryManager());
    MockInjector.setInstance(ConfigUtil.class, new ConfigUtil());
    defaultConfigManager = new DefaultConfigManager();
    someConfigContent = "someContent";
}
@Test
public void testGetConfigFileMultipleTimesWithSameNamespace() throws Exception {
    String someNamespace = "someName";
    ConfigFileFormat someConfigFileFormat = ConfigFileFormat.Properties;
    ConfigFile someConfigFile = defaultConfigManager.getConfigFile(someNamespace, someConfigFileFormat);
    ConfigFile anotherConfigFile = defaultConfigManager.getConfigFile(someNamespace, someConfigFileFormat);
    assertThat("Get config file multiple times with the same namespace should return the same config file instance", someConfigFile, equalTo(anotherConfigFile));
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```