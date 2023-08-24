NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
org.eclipse.californium.scandium.ClusterMacTest_testSmallRecordWithOffsetClusterMac_AutoMR
original_MTC_FQS:
org.eclipse.californium.scandium.ClusterMacTest.testSmallRecordWithOffsetClusterMac()
poj_dir:
eclipse/californium/
original_MTC_class_path:
eclipse/californium/scandium-core/src/test/java/org/eclipse/californium/scandium/ClusterMacTest.java

# Codified MR:
```java
public void testSmallRecordWithOffsetClusterMac_AutoMR(Mac mac, DatagramPacket smallRecord) throws Exception {
    int offset = 6;
    mac.init(key);
    byte[] data = smallRecord.getData();
    data[offset + DtlsClusterConnector.CLUSTER_ADDRESS_LENGTH_OFFSET] = 4;
    smallRecord.setData(data, offset, data.length - offset);
    byte[] macBytes1 = DtlsManagedClusterConnector.calculateClusterMac(mac, smallRecord);
    DtlsManagedClusterConnector.setClusterMac(mac, smallRecord);
    byte[] macBytes2 = DtlsManagedClusterConnector.calculateClusterMac(mac, smallRecord);
    assertArrayEquals(macBytes1, macBytes2);
    data[offset + DtlsClusterConnector.CLUSTER_ADDRESS_OFFSET] += 1;
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertArrayEquals(macBytes1, macBytes2), line: 100 
source_input: ['mac', 'smallRecord'], first_invoked_method_name: calculateClusterMac, line: 92 
followUp_input: ['mac', 'smallRecord'], second_invoked_method_name: calculateClusterMac, line: 96 


# Original MTC and related fields:
```java
private SecretKey key;
private DatagramPacket smallRecord;
@Before
public void init() {
    Random random = RandomManager.currentRandom();
    byte[] init = new byte[16];
    random.nextBytes(init);
    key = SecretUtil.create(init, "Mac");
    byte[] small = new byte[30];
    random.nextBytes(small);
    small[DtlsClusterConnector.CLUSTER_ADDRESS_LENGTH_OFFSET] = 4;
    byte[] large = new byte[120];
    random.nextBytes(large);
    large[DtlsClusterConnector.CLUSTER_ADDRESS_LENGTH_OFFSET] = 4;
    smallRecord = new DatagramPacket(small, small.length);
    largeRecord = new DatagramPacket(large, large.length);
}
@Test
public void testSmallRecordWithOffsetClusterMac() throws Exception {
    int offset = 6;
    Mac mac = CipherSuite.TLS_PSK_WITH_AES_128_CCM_8.getThreadLocalPseudoRandomFunctionMac();
    mac.init(key);
    byte[] data = smallRecord.getData();
    data[offset + DtlsClusterConnector.CLUSTER_ADDRESS_LENGTH_OFFSET] = 4;
    smallRecord.setData(data, offset, data.length - offset);
    byte[] macBytes1 = DtlsManagedClusterConnector.calculateClusterMac(mac, smallRecord);
    DtlsManagedClusterConnector.setClusterMac(mac, smallRecord);
    byte[] macBytes2 = DtlsManagedClusterConnector.calculateClusterMac(mac, smallRecord);
    assertTrue(DtlsManagedClusterConnector.validateClusterMac(mac, smallRecord));
    assertArrayEquals(macBytes1, macBytes2);
    data[offset + DtlsClusterConnector.CLUSTER_ADDRESS_OFFSET] += 1;
    assertFalse(DtlsManagedClusterConnector.validateClusterMac(mac, smallRecord));
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 
## comments (if any): 
```txt

```