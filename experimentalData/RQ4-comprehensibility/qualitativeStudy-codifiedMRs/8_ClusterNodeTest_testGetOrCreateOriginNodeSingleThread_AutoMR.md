NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.alibaba.csp.sentinel.node.ClusterNodeTest_testGetOrCreateOriginNodeSingleThread_AutoMR
original_MTC_FQS:
com.alibaba.csp.sentinel.node.ClusterNodeTest.testGetOrCreateOriginNodeSingleThread()
poj_dir:
ProgrammerAnthony/SentinelC/
original_MTC_class_path:
ProgrammerAnthony/SentinelC/sentinel-core/src/test/java/com/alibaba/csp/sentinel/node/ClusterNodeTest.java

# Codified MR:
```java
public void testGetOrCreateOriginNodeSingleThread_AutoMR(ClusterNode clusterNode, String origin1) {
    Node originNode1 = clusterNode.getOrCreateOriginNode(origin1);
    String origin2 = "origin2";
    Node originNode2 = clusterNode.getOrCreateOriginNode(origin2);
    // test same origin, no StatisticNode added into the originCountMap
    Node tmpOriginNode = clusterNode.getOrCreateOriginNode(origin1);
    assertSame(tmpOriginNode, originNode1);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance2
relation_assertion: assertSame(tmpOriginNode, originNode1), line: 58 
source_input: ['origin1', 'clusterNode'], first_invoked_method_name: getOrCreateOriginNode, line: 45 
followUp_input: ['origin1', 'clusterNode'], second_invoked_method_name: getOrCreateOriginNode, line: 56 


# Original MTC and related fields:
```java


@Test
public void testGetOrCreateOriginNodeSingleThread() {
    ClusterNode clusterNode = new ClusterNode("test");
    String origin1 = "origin1";
    Node originNode1 = clusterNode.getOrCreateOriginNode(origin1);
    assertNotNull(originNode1);
    assertEquals(1, clusterNode.getOriginCountMap().size());
    String origin2 = "origin2";
    Node originNode2 = clusterNode.getOrCreateOriginNode(origin2);
    assertNotNull(originNode2);
    assertEquals(2, clusterNode.getOriginCountMap().size());
    assertNotSame(originNode1, originNode2);
    // test same origin, no StatisticNode added into the originCountMap
    Node tmpOriginNode = clusterNode.getOrCreateOriginNode(origin1);
    assertEquals(2, clusterNode.getOriginCountMap().size());
    assertSame(tmpOriginNode, originNode1);
    assertTrue(clusterNode.getOriginCountMap().containsKey(origin1));
    assertTrue(clusterNode.getOriginCountMap().containsKey(origin2));
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 
## comments (if any): 
```txt

```