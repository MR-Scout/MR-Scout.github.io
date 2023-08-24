NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:

codifiedMR_FQN:
org.eclipse.leshan.server.redis.serialization.SecurityInfoSerDesTest_security_info_rpk_ser_des_then_equal_AutoMR
original_MTC_FQS:
org.eclipse.leshan.server.redis.serialization.SecurityInfoSerDesTest.security_info_rpk_ser_des_then_equal()
poj_dir:
eclipse/leshan/
original_MTC_class_path:
eclipse/leshan/leshan-server-redis/src/test/java/org/eclipse/leshan/server/redis/serialization/SecurityInfoSerDesTest.java

# Codified MR:
```java
public void security_info_rpk_ser_des_then_equal_AutoMR(SecurityInfo si) throws Exception {
    byte[] publicX = Hex.decodeHex("89c048261979208666f2bfb188be1968fc9021c416ce12828c06f4e314c167b5".toCharArray());
    byte[] publicY = Hex.decodeHex("cbf1eb7587f08e01688d9ada4be859137ca49f79394bad9179326b3090967b68".toCharArray());
    // Get Elliptic Curve Parameter spec for secp256r1
    AlgorithmParameters algoParameters = AlgorithmParameters.getInstance("EC");
    algoParameters.init(new ECGenParameterSpec("secp256r1"));
    ECParameterSpec parameterSpec = algoParameters.getParameterSpec(ECParameterSpec.class);
    // Create key specs
    KeySpec publicKeySpec = new ECPublicKeySpec(new ECPoint(new BigInteger(publicX), new BigInteger(publicY)), parameterSpec);
    byte[] data = SecurityInfoSerDes.serialize(si);
    assertEquals(si, SecurityInfoSerDes.deserialize(data));
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertEquals(si, SecurityInfoSerDes.deserialize(data)), line: 69 
source_input: ['si'], first_invoked_method_name: serialize, line: 64 
followUp_input: ['data'], second_invoked_method_name: deserialize, line: 69 


# Original MTC and related fields:
```java


@Test
public void security_info_rpk_ser_des_then_equal() throws Exception {
    byte[] publicX = Hex.decodeHex("89c048261979208666f2bfb188be1968fc9021c416ce12828c06f4e314c167b5".toCharArray());
    byte[] publicY = Hex.decodeHex("cbf1eb7587f08e01688d9ada4be859137ca49f79394bad9179326b3090967b68".toCharArray());
    // Get Elliptic Curve Parameter spec for secp256r1
    AlgorithmParameters algoParameters = AlgorithmParameters.getInstance("EC");
    algoParameters.init(new ECGenParameterSpec("secp256r1"));
    ECParameterSpec parameterSpec = algoParameters.getParameterSpec(ECParameterSpec.class);
    // Create key specs
    KeySpec publicKeySpec = new ECPublicKeySpec(new ECPoint(new BigInteger(publicX), new BigInteger(publicY)), parameterSpec);
    SecurityInfo si = SecurityInfo.newRawPublicKeyInfo("myendpoint", KeyFactory.getInstance("EC").generatePublic(publicKeySpec));
    byte[] data = SecurityInfoSerDes.serialize(si);
    assertEquals("{\"ep\":\"myendpoint\",\"rpk\":{\"x\":\"89c048261979208666f2bfb188be1968fc9021c416ce12828c06f4e314c167b5\",\"y\":\"cbf1eb7587f08e01688d9ada4be859137ca49f79394bad9179326b3090967b68\",\"params\":\"secp256r1\"}}", new String(data));
    assertEquals(si, SecurityInfoSerDes.deserialize(data));
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 2


## comments (if any): 

```txt
I do not know the APIs for constructing new `si`. maybe it can be used for RSA.
```
