NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.weibo.api.motan.rpc.DefaultResponseFutureTest_testNormal_AutoMR
original_MTC_FQS:
com.weibo.api.motan.rpc.DefaultResponseFutureTest.testNormal()
poj_dir:
weibocom/motan/
original_MTC_class_path:
weibocom/motan/motan-core/src/test/java/com/weibo/api/motan/rpc/DefaultResponseFutureTest.java

# Codified MR:
```java
public void testNormal_AutoMR(DefaultResponse defaultResponse, DefaultResponseFuture response) {
    DefaultRequest request = new DefaultRequest();
    defaultResponse.setValue("success");
    response.onSuccess(defaultResponse);
    Object result = response.getValue();
    Assert.assertEquals(result, defaultResponse.getValue());
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: Assert.assertEquals(result, defaultResponse.getValue()), line: 43 
source_input: ['defaultResponse', 'response'], first_invoked_method_name: onSuccess, line: 40 
followUp_input: ['response'], second_invoked_method_name: getValue, line: 42 


# Original MTC and related fields:
```java
static URL url = new URL("motan", "localhost", 18080, "testurl");

@Test
public void testNormal() {
    DefaultRequest request = new DefaultRequest();
    DefaultResponse defaultResponse = new DefaultResponse();
    defaultResponse.setValue("success");
    DefaultResponseFuture response = new DefaultResponseFuture(request, 100, url);
    response.onSuccess(defaultResponse);
    Object result = response.getValue();
    Assert.assertEquals(result, defaultResponse.getValue());
    Assert.assertTrue(response.isDone());
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```