NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
org.yes.cart.utils.Base64UtilsTest_testEncodeDecode_AutoMR
original_MTC_FQS:
org.yes.cart.utils.Base64UtilsTest.testEncodeDecode()
poj_dir:
inspire-software/yes-cart/
original_MTC_class_path:
inspire-software/yes-cart/domain-api/src/test/java/org/yes/cart/utils/Base64UtilsTest.java

# Codified MR:
```java
public void testEncodeDecode_AutoMR(String original) throws Exception {
    final String base64 = "SmF2YSA4IG5vdyBzdXBwb3J0cyBCQVNFNjQgRW5jb2RpbmcgYW5kIERlY29kaW5nLiBXaHkgYXJlIHdlIGV2ZW4gd3JpdGluZyB0aGlzPyBBaCwgSmF2YSA3";
    final String encoded = Base64Utils.encode(original);
    final String decoded = Base64Utils.decode(encoded);
    assertEquals(decoded, original);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertEquals(decoded, original), line: 44 
source_input: ['original'], first_invoked_method_name: encode, line: 36 
followUp_input: ['encoded'], second_invoked_method_name: decode, line: 41 


# Original MTC and related fields:
```java


@Test
public void testEncodeDecode() throws Exception {
    final String original = "Java 8 now supports BASE64 Encoding and Decoding. Why are we even writing this? Ah, Java 7";
    final String base64 = "SmF2YSA4IG5vdyBzdXBwb3J0cyBCQVNFNjQgRW5jb2RpbmcgYW5kIERlY29kaW5nLiBXaHkgYXJlIHdlIGV2ZW4gd3JpdGluZyB0aGlzPyBBaCwgSmF2YSA3";
    final String encoded = Base64Utils.encode(original);
    assertEquals(base64, encoded);
    assertFalse(encoded.equals(original));
    final String decoded = Base64Utils.decode(encoded);
    assertNotNull(decoded);
    assertEquals(decoded, original);
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 4
 
## comments (if any): 
```txt

```
