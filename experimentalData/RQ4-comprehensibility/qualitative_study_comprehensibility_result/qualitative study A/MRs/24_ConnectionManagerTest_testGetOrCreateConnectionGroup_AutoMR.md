NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.alibaba.csp.sentinel.cluster.server.connection.ConnectionManagerTest_testGetOrCreateConnectionGroup_AutoMR
original_MTC_FQS:
com.alibaba.csp.sentinel.cluster.server.connection.ConnectionManagerTest.testGetOrCreateConnectionGroup()
poj_dir:
ProgrammerAnthony/SentinelC/
original_MTC_class_path:
ProgrammerAnthony/SentinelC/sentinel-cluster/sentinel-cluster-server-default/src/test/java/com/alibaba/csp/sentinel/cluster/server/connection/ConnectionManagerTest.java

# Codified MR:
```java
public void testGetOrCreateConnectionGroup_AutoMR(String namespace) {
    ConnectionGroup group1 = ConnectionManager.getOrCreateConnectionGroup(namespace);
    // Put one connection.
    ConnectionManager.addConnection(namespace, "12.23.34.45:1997");
    ConnectionGroup group2 = ConnectionManager.getOrCreateConnectionGroup(namespace);
    assertSame(group1, group2);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertSame(group1, group2), line: 100 
source_input: ['namespace'], first_invoked_method_name: getOrCreateConnectionGroup, line: 93 
followUp_input: ['namespace'], second_invoked_method_name: getOrCreateConnectionGroup, line: 98 


# Original MTC and related fields:
```java

@Before
public void setUp() {
    ConnectionManager.clear();
}
@Test
public void testGetOrCreateConnectionGroup() {
    String namespace = "test-namespace";
    assertNull(ConnectionManager.getConnectionGroup(namespace));
    ConnectionGroup group1 = ConnectionManager.getOrCreateConnectionGroup(namespace);
    assertNotNull(group1);
    // Put one connection.
    ConnectionManager.addConnection(namespace, "12.23.34.45:1997");
    ConnectionGroup group2 = ConnectionManager.getOrCreateConnectionGroup(namespace);
    assertSame(group1, group2);
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 2
 
## comments (if any): 
```txt

```