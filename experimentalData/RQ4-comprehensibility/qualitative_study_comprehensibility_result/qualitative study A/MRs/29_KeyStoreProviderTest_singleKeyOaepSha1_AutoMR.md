NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.amazonaws.encryptionsdk.jce.KeyStoreProviderTest_singleKeyOaepSha1_AutoMR
original_MTC_FQS:
com.amazonaws.encryptionsdk.jce.KeyStoreProviderTest.singleKeyOaepSha1()
poj_dir:
aws/aws-encryption-sdk-java/
original_MTC_class_path:
aws/aws-encryption-sdk-java/src/test/java/com/amazonaws/encryptionsdk/jce/KeyStoreProviderTest.java

# Codified MR:
```java
public void singleKeyOaepSha1_AutoMR(KeyStoreProvider mkp, AwsCrypto crypto, byte[] PLAINTEXT) throws Exception {
    addEntry("key1");
    final JceMasterKey mk1 = mkp.getMasterKey("key1");
    final CryptoResult<byte[], JceMasterKey> ct = crypto.encryptData(mkp, PLAINTEXT);
    final CryptoResult<byte[], JceMasterKey> result = crypto.decryptData(mkp, ct.getResult());
    assertArrayEquals(PLAINTEXT, result.getResult());
    // Only the first found key should be used
    assertEquals(1, result.getMasterKeys().size());
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertArrayEquals(PLAINTEXT, result.getResult()), line: 105 
source_input: ['mkp', 'PLAINTEXT', 'crypto'], first_invoked_method_name: encryptData, line: 102 
followUp_input: ['mkp', 'ct.getResult()', 'crypto'], second_invoked_method_name: decryptData, line: 104 


# Original MTC and related fields:
```java
private static final byte[] PLAINTEXT = generate(1024);
private static final KeyStore.PasswordProtection PP = new PasswordProtection(PASSWORD);
private KeyStore ks;
@Before
public void setup() throws Exception {
    ks = KeyStore.getInstance(KeyStore.getDefaultType());
    ks.load(null, PASSWORD);
}
@Test
public void singleKeyOaepSha1() throws Exception {
    addEntry("key1");
    final KeyStoreProvider mkp = new KeyStoreProvider(ks, PP, "KeyStore", "RSA/ECB/OAEPWithSHA-1AndMGF1Padding", "key1");
    final JceMasterKey mk1 = mkp.getMasterKey("key1");
    final AwsCrypto crypto = AwsCrypto.standard();
    final CryptoResult<byte[], JceMasterKey> ct = crypto.encryptData(mkp, PLAINTEXT);
    assertEquals(1, ct.getMasterKeyIds().size());
    final CryptoResult<byte[], JceMasterKey> result = crypto.decryptData(mkp, ct.getResult());
    assertArrayEquals(PLAINTEXT, result.getResult());
    // Only the first found key should be used
    assertEquals(1, result.getMasterKeys().size());
    assertEquals(mk1, result.getMasterKeys().get(0));
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```