NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.alibaba.csp.sentinel.slots.block.flow.param.ParameterMetricTest_testInitAndClearParameterMetric_AutoMR
original_MTC_FQS:
com.alibaba.csp.sentinel.slots.block.flow.param.ParameterMetricTest.testInitAndClearParameterMetric()
poj_dir:
ProgrammerAnthony/SentinelC/
original_MTC_class_path:
ProgrammerAnthony/SentinelC/sentinel-extension/sentinel-parameter-flow-control/src/test/java/com/alibaba/csp/sentinel/slots/block/flow/param/ParameterMetricTest.java

# Codified MR:
```java
public void testInitAndClearParameterMetric_AutoMR(ParameterMetric metric, ParamFlowRule rule, ParamFlowRule rule2) {
    metric.initialize(rule);
    CacheMap<Object, AtomicInteger> threadCountMap = metric.getThreadCountMap().get(rule.getParamIdx());
    CacheMap<Object, AtomicLong> timeRecordMap = metric.getRuleTimeCounter(rule);
    metric.initialize(rule);
    assertSame(timeRecordMap, metric.getRuleTimeCounter(rule));
    metric.initialize(rule2);
    CacheMap<Object, AtomicLong> timeRecordMap2 = metric.getRuleTimeCounter(rule2);
    rule2.setParamIdx(2);
    metric.initialize(rule2);
    assertNotSame(timeRecordMap2, metric.getRuleTimeCounter(rule2));
    ParamFlowRule rule3 = new ParamFlowRule("abc").setParamIdx(1).setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER);
    metric.initialize(rule3);
    metric.clear();
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertSame(timeRecordMap, metric.getRuleTimeCounter(rule)), line: 55 
source_input: ['rule', 'metric'], first_invoked_method_name: getRuleTimeCounter, line: 51 
followUp_input: ['rule', 'metric'], second_invoked_method_name: getRuleTimeCounter, line: 55 
## MR instance3
relation_assertion: assertNotSame(timeRecordMap2, metric.getRuleTimeCounter(rule2)), line: 65 
source_input: ['rule2', 'metric'], first_invoked_method_name: getRuleTimeCounter, line: 60 
followUp_input: ['rule2', 'metric'], second_invoked_method_name: getRuleTimeCounter, line: 65 


# Original MTC and related fields:
```java


@Test
public void testInitAndClearParameterMetric() {
    // Create a parameter metric for resource "abc".
    ParameterMetric metric = new ParameterMetric();
    ParamFlowRule rule = new ParamFlowRule("abc").setParamIdx(1);
    metric.initialize(rule);
    CacheMap<Object, AtomicInteger> threadCountMap = metric.getThreadCountMap().get(rule.getParamIdx());
    assertNotNull(threadCountMap);
    CacheMap<Object, AtomicLong> timeRecordMap = metric.getRuleTimeCounter(rule);
    assertNotNull(timeRecordMap);
    metric.initialize(rule);
    assertSame(threadCountMap, metric.getThreadCountMap().get(rule.getParamIdx()));
    assertSame(timeRecordMap, metric.getRuleTimeCounter(rule));
    ParamFlowRule rule2 = new ParamFlowRule("abc").setParamIdx(1);
    metric.initialize(rule2);
    CacheMap<Object, AtomicLong> timeRecordMap2 = metric.getRuleTimeCounter(rule2);
    assertSame(timeRecordMap, timeRecordMap2);
    rule2.setParamIdx(2);
    metric.initialize(rule2);
    assertNotSame(timeRecordMap2, metric.getRuleTimeCounter(rule2));
    ParamFlowRule rule3 = new ParamFlowRule("abc").setParamIdx(1).setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER);
    metric.initialize(rule3);
    assertNotSame(timeRecordMap, metric.getRuleTimeCounter(rule3));
    metric.clear();
    assertEquals(0, metric.getThreadCountMap().size());
    assertEquals(0, metric.getRuleTimeCounterMap().size());
    assertEquals(0, metric.getRuleTokenCounterMap().size());
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 
## comments (if any): 
```txt

```