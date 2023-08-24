NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.weibo.api.motan.registry.weibomesh.UtilTest_testUrlToJson_AutoMR
original_MTC_FQS:
com.weibo.api.motan.registry.weibomesh.UtilTest.testUrlToJson()
poj_dir:
weibocom/motan/
original_MTC_class_path:
weibocom/motan/motan-registry-weibomesh/src/test/java/com/weibo/api/motan/registry/weibomesh/UtilTest.java

# Codified MR:
```java
public void testUrlToJson_AutoMR(URL url) throws Exception {
    Map<String, String> params = new HashMap();
    params.put("testkey", "jksie;er,sjdf:");
    params.put("testkey2", "&$#://%");
    params.put("group", "testGroup");
    String urlString = Util.UrlToJson(url);
    URL ret = Util.JsonToUrl(urlString);
    Assert.assertEquals(url, ret);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: Assert.assertEquals(url, ret), line: 43 
source_input: ['url'], first_invoked_method_name: UrlToJson, line: 41 
followUp_input: ['urlString'], second_invoked_method_name: JsonToUrl, line: 42 


# Original MTC and related fields:
```java


@Test
public void testUrlToJson() throws Exception {
    Map<String, String> params = new HashMap();
    params.put("testkey", "jksie;er,sjdf:");
    params.put("testkey2", "&$#://%");
    params.put("group", "testGroup");
    URL url = new URL("motan2", "127.0.0.1", 8999, "testService", params);
    String urlString = Util.UrlToJson(url);
    URL ret = Util.JsonToUrl(urlString);
    Assert.assertEquals(url, ret);
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 4
 
## comments (if any): 
```txt

```
