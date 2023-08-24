NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.aliyun.oss.common.model.ObjectRelatedTest_testGetObjectRequest_AutoMR
original_MTC_FQS:
com.aliyun.oss.common.model.ObjectRelatedTest.testGetObjectRequest()
poj_dir:
aliyun/aliyun-oss-java-sdk/
original_MTC_class_path:
aliyun/aliyun-oss-java-sdk/src/test/java/com/aliyun/oss/common/model/ObjectRelatedTest.java

# Codified MR:
```java
public void testGetObjectRequest_AutoMR(GetObjectRequest request, List<String> eTagList) {
    request.setUseUrlSignature(true);
    eTagList.add("tag1");
    request.setNonmatchingETagConstraints(eTagList);
    request.clearNonmatchingETagConstraints();
    eTagList = new ArrayList<String>();
    eTagList.add("tag2");
    request.setMatchingETagConstraints(eTagList);
    assertEquals(eTagList, request.getMatchingETagConstraints());
    request.clearMatchingETagConstraints();
    request = new GetObjectRequest((URL) null, null);
    Map<String, String> requestHeaders = new HashMap<String, String>();
    request = new GetObjectRequest((URL) null, requestHeaders);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertEquals(eTagList, request.getMatchingETagConstraints()), line: 209 
source_input: ['eTagList', 'request'], first_invoked_method_name: setNonmatchingETagConstraints, line: 201 
followUp_input: ['eTagList', 'request'], second_invoked_method_name: setMatchingETagConstraints, line: 208 


# Original MTC and related fields:
```java


@Test
public void testGetObjectRequest() {
    GetObjectRequest request = new GetObjectRequest("bucket", "key").withRange(10, 20);
    assertEquals(10, request.getRange()[0]);
    assertEquals(20, request.getRange()[1]);
    request.setUseUrlSignature(true);
    assertEquals(true, request.isUseUrlSignature());
    List<String> eTagList = new ArrayList<String>();
    eTagList.add("tag1");
    request.setNonmatchingETagConstraints(eTagList);
    assertEquals(eTagList, request.getNonmatchingETagConstraints());
    request.clearNonmatchingETagConstraints();
    assertEquals(0, request.getNonmatchingETagConstraints().size());
    eTagList = new ArrayList<String>();
    eTagList.add("tag2");
    request.setMatchingETagConstraints(eTagList);
    assertEquals(eTagList, request.getMatchingETagConstraints());
    request.clearMatchingETagConstraints();
    assertEquals(0, request.getMatchingETagConstraints().size());
    request = new GetObjectRequest((URL) null, null);
    assertEquals(null, request.getAbsoluteUri());
    Map<String, String> requestHeaders = new HashMap<String, String>();
    request = new GetObjectRequest((URL) null, requestHeaders);
    assertEquals(null, request.getAbsoluteUri());
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 1
 
## comments (if any): 
```txt
I think this is not a meaningful MR
```