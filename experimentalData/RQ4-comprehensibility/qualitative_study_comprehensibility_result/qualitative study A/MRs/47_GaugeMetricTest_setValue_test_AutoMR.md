NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.salesforce.dva.argus.service.monitor.GaugeMetricTest_setValue_test_AutoMR
original_MTC_FQS:
com.salesforce.dva.argus.service.monitor.GaugeMetricTest.setValue_test()
poj_dir:
salesforce/Argus/
original_MTC_class_path:
salesforce/Argus/ArgusCore/src/test/java/com/salesforce/dva/argus/service/monitor/GaugeMetricTest.java

# Codified MR:
```java
public void setValue_test_AutoMR(double newValue, GaugeMetric gm) {
    final int iterations = 10;
    final double delta = 5.0;
    for (int i = 0; i < iterations; i++) {
        gm.addValue(delta);
    }
    final double expectedGaugeValue = delta * iterations;
    gm.setValue(newValue);
    assertEquals(newValue, gm.getCurrentGaugeAdderValue(), DOUBLE_COMPARISON_MAX_DELTA);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertEquals(newValue, gm.getCurrentGaugeAdderValue(), DOUBLE_COMPARISON_MAX_DELTA), line: 103 
source_input: ['newValue', 'gm'], first_invoked_method_name: setValue, line: 100 
followUp_input: ['gm'], second_invoked_method_name: getCurrentGaugeAdderValue, line: 103 


# Original MTC and related fields:
```java
private static final double DOUBLE_COMPARISON_MAX_DELTA = 0.001;
private GaugeMetric gm;
@Before
public void setUp() {
    final Metric m = new Metric(SCOPE, METRIC_NAME);
    m.setTags(TAGS);
    gm = new GaugeMetric(m);
}
@Test
public void setValue_test() {
    final int iterations = 10;
    final double delta = 5.0;
    for (int i = 0; i < iterations; i++) {
        gm.addValue(delta);
    }
    final double expectedGaugeValue = delta * iterations;
    assertEquals(0.0, gm.getValue(), DOUBLE_COMPARISON_MAX_DELTA);
    assertEquals(expectedGaugeValue, gm.getCurrentGaugeAdderValue(), DOUBLE_COMPARISON_MAX_DELTA);
    final double newValue = 78.6;
    gm.setValue(newValue);
    assertEquals(0.0, gm.getValue(), DOUBLE_COMPARISON_MAX_DELTA);
    assertEquals(newValue, gm.getCurrentGaugeAdderValue(), DOUBLE_COMPARISON_MAX_DELTA);
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```