NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.alipay.sofa.common.log.code.LogCode2DescriptionTest_alreadyInitialized_AutoMR
original_MTC_FQS:
com.alipay.sofa.common.log.code.LogCode2DescriptionTest.alreadyInitialized()
poj_dir:
sofastack/sofa-common-tools/
original_MTC_class_path:
sofastack/sofa-common-tools/src/test/java/com/alipay/sofa/common/log/code/LogCode2DescriptionTest.java

# Codified MR:
```java
public void alreadyInitialized_AutoMR(String COMPONENT_NAME) {
    LogCode2Description logCode2Description1 = LogCode2Description.create(COMPONENT_NAME);
    LogCode2Description logCode2Description2 = LogCode2Description.create(COMPONENT_NAME);
    Assert.assertEquals(logCode2Description1, logCode2Description2);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: Assert.assertEquals(logCode2Description1, logCode2Description2), line: 68 
source_input: ['COMPONENT_NAME'], first_invoked_method_name: create, line: 66 
followUp_input: ['COMPONENT_NAME'], second_invoked_method_name: create, line: 67 


# Original MTC and related fields:
```java
private static final String COMPONENT_NAME = "SOFA-TEST";

@Test
public void alreadyInitialized() {
    LogCode2Description logCode2Description1 = LogCode2Description.create(COMPONENT_NAME);
    LogCode2Description logCode2Description2 = LogCode2Description.create(COMPONENT_NAME);
    Assert.assertEquals(logCode2Description1, logCode2Description2);
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 2
 
## comments (if any): 
```txt
Cannot understand the semantics.
```
