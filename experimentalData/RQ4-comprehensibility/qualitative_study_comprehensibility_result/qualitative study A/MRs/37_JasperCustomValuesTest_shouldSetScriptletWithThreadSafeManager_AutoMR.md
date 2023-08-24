NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
net.sf.dynamicreports.jasper.base.JasperCustomValuesTest_shouldSetScriptletWithThreadSafeManager_AutoMR
original_MTC_FQS:
net.sf.dynamicreports.jasper.base.JasperCustomValuesTest.shouldSetScriptletWithThreadSafeManager()
poj_dir:
dynamicreports/dynamicreports/
original_MTC_class_path:
dynamicreports/dynamicreports/dynamicreports-core/src/test/java/net/sf/dynamicreports/jasper/base/JasperCustomValuesTest.java

# Codified MR:
```java
public void shouldSetScriptletWithThreadSafeManager_AutoMR(JasperCustomValues cut, JasperScriptlet scriptlet) {
    cut.setJasperScriptlet(scriptlet);
    Assert.assertEquals(scriptlet, cut.getJasperScriptlet());
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: Assert.assertEquals(scriptlet, cut.getJasperScriptlet()), line: 40 
source_input: ['scriptlet', 'cut'], first_invoked_method_name: setJasperScriptlet, line: 39 
followUp_input: ['cut'], second_invoked_method_name: getJasperScriptlet, line: 40 


# Original MTC and related fields:
```java
private JasperScriptlet scriptlet = new JasperScriptlet();

@Test
public void shouldSetScriptletWithThreadSafeManager() {
    JasperCustomValues cut = createClassUnderTest(true);
    cut.setJasperScriptlet(scriptlet);
    Assert.assertEquals(scriptlet, cut.getJasperScriptlet());
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 4
 
## comments (if any): 
```txt

```