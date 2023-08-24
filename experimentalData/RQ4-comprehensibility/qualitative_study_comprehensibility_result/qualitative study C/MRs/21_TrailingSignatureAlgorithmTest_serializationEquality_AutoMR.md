NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.amazonaws.encryptionsdk.internal.TrailingSignatureAlgorithmTest_serializationEquality_AutoMR
original_MTC_FQS:
com.amazonaws.encryptionsdk.internal.TrailingSignatureAlgorithmTest.serializationEquality()
poj_dir:
aws/aws-encryption-sdk-java/
original_MTC_class_path:
aws/aws-encryption-sdk-java/src/test/java/com/amazonaws/encryptionsdk/internal/TrailingSignatureAlgorithmTest.java

# Codified MR:
```java
public void serializationEquality_AutoMR(PublicKey publicKey) throws Exception {
    CryptoAlgorithm algorithm = CryptoAlgorithm.ALG_AES_128_GCM_IV12_TAG16_HKDF_SHA256_ECDSA_P256;
    String serializedPublicKey = TrailingSignatureAlgorithm.forCryptoAlgorithm(algorithm).serializePublicKey(publicKey);
    PublicKey deserializedPublicKey = TrailingSignatureAlgorithm.forCryptoAlgorithm(algorithm).deserializePublicKey(serializedPublicKey);
    assertEquals(publicKey, deserializedPublicKey);
    algorithm = CryptoAlgorithm.ALG_AES_256_GCM_IV12_TAG16_HKDF_SHA384_ECDSA_P384;
    publicKey = TrailingSignatureAlgorithm.forCryptoAlgorithm(algorithm).generateKey().getPublic();
    serializedPublicKey = TrailingSignatureAlgorithm.forCryptoAlgorithm(algorithm).serializePublicKey(publicKey);
    deserializedPublicKey = TrailingSignatureAlgorithm.forCryptoAlgorithm(algorithm).deserializePublicKey(serializedPublicKey);
    assertEquals(publicKey, deserializedPublicKey);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertEquals(publicKey, deserializedPublicKey), line: 90 
source_input: ['publicKey'], first_invoked_method_name: serializePublicKey, line: 85 
followUp_input: ['serializedPublicKey'], second_invoked_method_name: deserializePublicKey, line: 87 


# Original MTC and related fields:
```java


@Test
public void serializationEquality() throws Exception {
    CryptoAlgorithm algorithm = CryptoAlgorithm.ALG_AES_128_GCM_IV12_TAG16_HKDF_SHA256_ECDSA_P256;
    PublicKey publicKey = TrailingSignatureAlgorithm.forCryptoAlgorithm(algorithm).generateKey().getPublic();
    String serializedPublicKey = TrailingSignatureAlgorithm.forCryptoAlgorithm(algorithm).serializePublicKey(publicKey);
    PublicKey deserializedPublicKey = TrailingSignatureAlgorithm.forCryptoAlgorithm(algorithm).deserializePublicKey(serializedPublicKey);
    assertEquals(publicKey, deserializedPublicKey);
    algorithm = CryptoAlgorithm.ALG_AES_256_GCM_IV12_TAG16_HKDF_SHA384_ECDSA_P384;
    publicKey = TrailingSignatureAlgorithm.forCryptoAlgorithm(algorithm).generateKey().getPublic();
    serializedPublicKey = TrailingSignatureAlgorithm.forCryptoAlgorithm(algorithm).serializePublicKey(publicKey);
    deserializedPublicKey = TrailingSignatureAlgorithm.forCryptoAlgorithm(algorithm).deserializePublicKey(serializedPublicKey);
    assertEquals(publicKey, deserializedPublicKey);
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```
