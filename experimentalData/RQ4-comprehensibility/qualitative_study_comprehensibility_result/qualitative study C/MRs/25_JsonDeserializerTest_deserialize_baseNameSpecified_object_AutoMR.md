NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
org.eclipse.leshan.core.json.JsonDeserializerTest_deserialize_baseNameSpecified_object_AutoMR
original_MTC_FQS:
org.eclipse.leshan.core.json.JsonDeserializerTest.deserialize_baseNameSpecified_object()
poj_dir:
eclipse/leshan/
original_MTC_class_path:
eclipse/leshan/leshan-core/src/test/java/org/eclipse/leshan/core/json/JsonDeserializerTest.java

# Codified MR:
```java
public void deserialize_baseNameSpecified_object_AutoMR(String dataString, LwM2mJsonJacksonEncoderDecoder LwM2mJson) throws LwM2mJsonException {
    // Not sure yet how Leshan will handle the object lin case
    // As it said in the Specs sec. 6.3.4 JSON
    // Table 20 Value as a JSON string if the Resource data type is Objlnk
    // Format according to Appendix C (e.g “10:03”)
    StringBuilder b = new StringBuilder();
    b.append("{\"bn\":\"/\",");
    b.append("\"e\":[");
    b.append("{\"n\":\"A/0/0/0\",\"ov\":\"B:0\"},");
    b.append("{\"n\":\"A/0/0/1\",\"ov\":\"B:1\"},");
    b.append("{\"n\":\"A/0/1\",\"sv\":\"8613800755500\"},");
    b.append("{\"n\":\"B/0/0\",\"sv\":\"myService1\"},");
    b.append("{\"n\":\"B/0/1\",\"sv\":\"Internet.15.234\"},");
    b.append("{\"n\":\"B/0/2\",\"ov\":\"C:0\"},");
    b.append("{\"n\":\"B/1/0\",\"sv\":\"myService2\"},");
    b.append("{\"n\":\"B/1/1\",\"sv\":\"Internet.15.235\"},");
    b.append("{\"n\":\"B/1/2\",\"ov\":\"FFFF:FFFF\"},");
    b.append("{\"n\":\"C/0/0\",\"sv\":\"85.76.76.84\"},");
    b.append("{\"n\":\"C/0/1\",\"sv\":\"85.76.255.255\"}]}");
    log.debug(dataString.trim());
    JsonRootObject element = LwM2mJson.fromJsonLwM2m(dataString);
    log.debug(element.toString());
    String outString = LwM2mJson.toJsonLwM2m(element);
    Assert.assertEquals(dataString.trim(), outString);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: Assert.assertEquals(dataString.trim(), outString), line: 108 
source_input: ['dataString', 'LwM2mJson'], first_invoked_method_name: fromJsonLwM2m, line: 105 
followUp_input: ['element', 'LwM2mJson'], second_invoked_method_name: toJsonLwM2m, line: 107 


# Original MTC and related fields:
```java
private static final Logger log = LoggerFactory.getLogger(JsonDeserializerTest.class);
private static final LwM2mJsonJacksonEncoderDecoder LwM2mJson = new LwM2mJsonJacksonEncoderDecoder();

@Test
public void deserialize_baseNameSpecified_object() throws LwM2mJsonException {
    // Not sure yet how Leshan will handle the object lin case
    // As it said in the Specs sec. 6.3.4 JSON
    // Table 20 Value as a JSON string if the Resource data type is Objlnk
    // Format according to Appendix C (e.g “10:03”)
    StringBuilder b = new StringBuilder();
    b.append("{\"bn\":\"/\",");
    b.append("\"e\":[");
    b.append("{\"n\":\"A/0/0/0\",\"ov\":\"B:0\"},");
    b.append("{\"n\":\"A/0/0/1\",\"ov\":\"B:1\"},");
    b.append("{\"n\":\"A/0/1\",\"sv\":\"8613800755500\"},");
    b.append("{\"n\":\"B/0/0\",\"sv\":\"myService1\"},");
    b.append("{\"n\":\"B/0/1\",\"sv\":\"Internet.15.234\"},");
    b.append("{\"n\":\"B/0/2\",\"ov\":\"C:0\"},");
    b.append("{\"n\":\"B/1/0\",\"sv\":\"myService2\"},");
    b.append("{\"n\":\"B/1/1\",\"sv\":\"Internet.15.235\"},");
    b.append("{\"n\":\"B/1/2\",\"ov\":\"FFFF:FFFF\"},");
    b.append("{\"n\":\"C/0/0\",\"sv\":\"85.76.76.84\"},");
    b.append("{\"n\":\"C/0/1\",\"sv\":\"85.76.255.255\"}]}");
    String dataString = b.toString();
    log.debug(dataString.trim());
    JsonRootObject element = LwM2mJson.fromJsonLwM2m(dataString);
    log.debug(element.toString());
    String outString = LwM2mJson.toJsonLwM2m(element);
    Assert.assertEquals(dataString.trim(), outString);
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```
