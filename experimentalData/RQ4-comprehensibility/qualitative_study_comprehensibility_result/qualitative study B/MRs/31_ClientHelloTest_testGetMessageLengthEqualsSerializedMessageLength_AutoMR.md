NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
org.eclipse.californium.scandium.dtls.ClientHelloTest_testGetMessageLengthEqualsSerializedMessageLength_AutoMR
original_MTC_FQS:
org.eclipse.californium.scandium.dtls.ClientHelloTest.testGetMessageLengthEqualsSerializedMessageLength()
poj_dir:
eclipse/californium/
original_MTC_class_path:
eclipse/californium/scandium-core/src/test/java/org/eclipse/californium/scandium/dtls/ClientHelloTest.java

# Codified MR:
```java
/**
 * Verifies that the calculated message length is the same as the length
 * of the serialized message.
 */
public void testGetMessageLengthEqualsSerializedMessageLength_AutoMR(ClientHello clientHello) {
    givenAClientHelloWithEmptyExtensions();
    assertThat("ClientHello's anticipated message length does not match its real length", clientHello.getMessageLength(), is(clientHello.fragmentToByteArray().length));
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertThat("ClientHello's anticipated message length does not match its real length", clientHello.getMessageLength(), is(clientHello.fragmentToByteArray().length)), line: 51 
source_input: ['clientHello'], first_invoked_method_name: getMessageLength, line: 52 
followUp_input: ['clientHello'], second_invoked_method_name: fragmentToByteArray, line: 52 


# Original MTC and related fields:
```java
ClientHello clientHello;

/**
 * Verifies that the calculated message length is the same as the length
 * of the serialized message.
 */
@Test
public void testGetMessageLengthEqualsSerializedMessageLength() {
    givenAClientHelloWithEmptyExtensions();
    assertThat("ClientHello's anticipated message length does not match its real length", clientHello.getMessageLength(), is(clientHello.fragmentToByteArray().length));
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt
Don't know how to construct.
```