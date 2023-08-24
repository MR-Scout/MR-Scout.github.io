NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
org.eclipse.leshan.server.redis.serialization.SecurityInfoSerDesTest_security_info_psk_ser_des_then_equal_AutoMR
original_MTC_FQS:
org.eclipse.leshan.server.redis.serialization.SecurityInfoSerDesTest.security_info_psk_ser_des_then_equal()
poj_dir:
eclipse/leshan/
original_MTC_class_path:
eclipse/leshan/leshan-server-redis/src/test/java/org/eclipse/leshan/server/redis/serialization/SecurityInfoSerDesTest.java

# Codified MR:
```java
public void security_info_psk_ser_des_then_equal_AutoMR(SecurityInfo si) {
    byte[] data = SecurityInfoSerDes.serialize(si);
    assertEquals(si, SecurityInfoSerDes.deserialize(data));
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertEquals(si, SecurityInfoSerDes.deserialize(data)), line: 43 
source_input: ['si'], first_invoked_method_name: serialize, line: 41 
followUp_input: ['data'], second_invoked_method_name: deserialize, line: 43 


# Original MTC and related fields:
```java


@Test
public void security_info_psk_ser_des_then_equal() {
    SecurityInfo si = SecurityInfo.newPreSharedKeyInfo("myendPoint", "pskIdentity", Hex.decodeHex("deadbeef".toCharArray()));
    byte[] data = SecurityInfoSerDes.serialize(si);
    assertEquals("{\"ep\":\"myendPoint\",\"id\":\"pskIdentity\",\"psk\":\"deadbeef\"}", new String(data));
    assertEquals(si, SecurityInfoSerDes.deserialize(data));
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 4
 
## comments (if any): 
```txt

```