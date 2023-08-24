NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.aliyun.oss.common.model.BucketRelatedTest_testReplicationRule_AutoMR
original_MTC_FQS:
com.aliyun.oss.common.model.BucketRelatedTest.testReplicationRule()
poj_dir:
aliyun/aliyun-oss-java-sdk/
original_MTC_class_path:
aliyun/aliyun-oss-java-sdk/src/test/java/com/aliyun/oss/common/model/BucketRelatedTest.java

# Codified MR:
```java
public void testReplicationRule_AutoMR(ReplicationRule replicationRule, List<String> objectPrefixList, List<AddBucketReplicationRequest.ReplicationAction> replicationActionList) {
    replicationRule.setReplicationStatus(ReplicationStatus.Doing);
    replicationRule.setObjectPrefixList(null);
    replicationRule.setObjectPrefixList(objectPrefixList);
    objectPrefixList.add("prefix");
    replicationRule.setObjectPrefixList(objectPrefixList);
    assertEquals(objectPrefixList, replicationRule.getObjectPrefixList());
    replicationRule.setReplicationActionList(null);
    replicationRule.setReplicationActionList(replicationActionList);
    replicationActionList.add(AddBucketReplicationRequest.ReplicationAction.ALL);
    replicationRule.setReplicationActionList(replicationActionList);
    assertEquals(replicationActionList, replicationRule.getReplicationActionList());
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertEquals(objectPrefixList, replicationRule.getObjectPrefixList()), line: 983 
source_input: ['objectPrefixList', 'replicationRule'], first_invoked_method_name: setObjectPrefixList, line: 978 
followUp_input: ['objectPrefixList', 'replicationRule'], second_invoked_method_name: setObjectPrefixList, line: 982 
## MR instance2
relation_assertion: assertEquals(replicationActionList, replicationRule.getReplicationActionList()), line: 994 
source_input: ['replicationActionList', 'replicationRule'], first_invoked_method_name: setReplicationActionList, line: 989 
followUp_input: ['replicationActionList', 'replicationRule'], second_invoked_method_name: setReplicationActionList, line: 993 


# Original MTC and related fields:
```java


@Test
public void testReplicationRule() {
    ReplicationRule replicationRule = new ReplicationRule();
    replicationRule.setReplicationStatus(ReplicationStatus.Doing);
    assertEquals(ReplicationStatus.Doing, replicationRule.getReplicationStatus());
    List<String> objectPrefixList = new ArrayList<String>();
    replicationRule.setObjectPrefixList(null);
    assertEquals(0, replicationRule.getObjectPrefixList().size());
    replicationRule.setObjectPrefixList(objectPrefixList);
    assertEquals(0, replicationRule.getObjectPrefixList().size());
    objectPrefixList.add("prefix");
    replicationRule.setObjectPrefixList(objectPrefixList);
    assertEquals(objectPrefixList, replicationRule.getObjectPrefixList());
    List<AddBucketReplicationRequest.ReplicationAction> replicationActionList = new ArrayList<AddBucketReplicationRequest.ReplicationAction>();
    replicationRule.setReplicationActionList(null);
    assertEquals(0, replicationRule.getReplicationActionList().size());
    replicationRule.setReplicationActionList(replicationActionList);
    assertEquals(0, replicationRule.getReplicationActionList().size());
    replicationActionList.add(AddBucketReplicationRequest.ReplicationAction.ALL);
    replicationRule.setReplicationActionList(replicationActionList);
    assertEquals(replicationActionList, replicationRule.getReplicationActionList());
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 1
 
## comments (if any): 
```txt
objectPrefixList.add("prefix"); is not easy to understand.
```