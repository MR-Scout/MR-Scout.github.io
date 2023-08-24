TestFilePath: Backblaze/JavaReedSolomon/src/test/java/com/backblaze/erasure/GaloisTest.java
++++++ MethodDeclarationQualifiedSignature: com.backblaze.erasure.GaloisTest.testCommutativity()
@Test
public void testCommutativity() {
    for (int i = -128; i < 128; i++) {
        for (int j = -128; j < 128; j++) {
            byte a = (byte) i;
            byte b = (byte) j;
            assertEquals(Galois.add(a, b), Galois.add(b, a));
            assertEquals(Galois.multiply(a, b), Galois.multiply(b, a));
        }
    }
}


TestFilePath: apache/metron/metron-platform/metron-common/src/test/java/org/apache/metron/common/utils/SerDeUtilsTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.metron.common.utils.SerDeUtilsTest.testArbitraryPojo()
@Test
public void testArbitraryPojo() {
    final ArbitraryPojo expected = new ArbitraryPojo();
    byte[] raw = SerDeUtils.toBytes(expected);
    Object actual = SerDeUtils.fromBytes(raw, Object.class);
    assertEquals(expected, actual);
}


TestFilePath: jfree/jfreechart/src/test/java/org/jfree/chart/title/DateTitleTest.java
++++++ MethodDeclarationQualifiedSignature: org.jfree.chart.title.DateTitleTest.testHashcode()
/**
 * Two objects that are equal are required to return the same hashCode.
 */
@Test
public void testHashcode() {
    DateTitle t1 = new DateTitle();
    DateTitle t2 = new DateTitle();
    assertEquals(t1, t2);
    int h1 = t1.hashCode();
    int h2 = t2.hashCode();
    assertEquals(h1, h2);
}


TestFilePath: ProgrammerAnthony/RocketMQC/common/src/test/java/org/apache/rocketmq/common/protocol/topic/OffsetMovedEventTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.rocketmq.common.protocol.topic.OffsetMovedEventTest.testFromBytes()
@Test
public void testFromBytes() throws Exception {
    OffsetMovedEvent event = mockOffsetMovedEvent();
    byte[] encodeData = event.encode();
    OffsetMovedEvent decodeData = RemotingSerializable.decode(encodeData, OffsetMovedEvent.class);
    assertEquals(event, decodeData);
}


TestFilePath: ihmcrobotics/ihmc-open-robotics-software/ihmc-robotics-toolkit/src/test/java/us/ihmc/robotics/occupancyGrid/OccupancyGridTest.java
++++++ MethodDeclarationQualifiedSignature: us.ihmc.robotics.occupancyGrid.OccupancyGridTest.testHashMapUniqueness()
@Test
public void testHashMapUniqueness() {
    int hashMap0 = OccupancyGridCell.computeHashCode(100, 100);
    int hashMap1 = OccupancyGridCell.computeHashCode(-100, 100);
    int hashMap2 = OccupancyGridCell.computeHashCode(-100, -100);
    int hashMap3 = OccupancyGridCell.computeHashCode(100, -100);
    assertFalse(hashMap0 == hashMap1);
    assertFalse(hashMap0 == hashMap2);
    assertFalse(hashMap0 == hashMap3);
    assertFalse(hashMap1 == hashMap2);
    assertFalse(hashMap1 == hashMap3);
    assertFalse(hashMap2 == hashMap3);
}


TestFilePath: wdullaer/MaterialDateTimePicker/library/src/test/java/com/wdullaer/materialdatetimepicker/time/TimepointTest.java
++++++ MethodDeclarationQualifiedSignature: com.wdullaer.materialdatetimepicker.time.TimepointTest.timepointsWithSameFieldsShouldHaveSameHashCode()
@Test
public void timepointsWithSameFieldsShouldHaveSameHashCode() {
    Timepoint first = new Timepoint(12, 0, 0);
    Timepoint second = new Timepoint(12, 0, 0);
    Assert.assertEquals(first.hashCode(), second.hashCode());
}


TestFilePath: rsksmart/rskj/rskj-core/src/test/java/org/ethereum/net/server/StatsTest.java
++++++ MethodDeclarationQualifiedSignature: org.ethereum.net.server.StatsTest.TestLowFrequency()
@Test
public void TestLowFrequency() {
    Stats stats = new Stats();
    stats.setAvg(500);
    ;
    double old = stats.score(MessageType.STATUS_MESSAGE);
    double updated = stats.update(1000, MessageType.STATUS_MESSAGE);
    Assert.assertTrue(updated > old);
}


TestFilePath: jaamsim/jaamsim/src/test/java/com/jaamsim/math/TestMat4d.java
++++++ MethodDeclarationQualifiedSignature: com.jaamsim.math.TestMat4d.testTranspose()
@Test
public void testTranspose() {
    Mat4d mat;
    mat = new Mat4d(seq);
    mat.transpose4();
    assertEqual(mat, seqTranspose);
    mat.transpose4();
    assertEqual(mat, seq);
    mat = new Mat4d();
    mat.transpose4(seq);
    assertEqual(mat, seqTranspose);
    mat.transpose4(seqTranspose);
    assertEqual(mat, seq);
}


TestFilePath: strimzi/strimzi-kafka-operator/operator-common/src/test/java/io/strimzi/operator/common/model/LabelsTest.java
++++++ MethodDeclarationQualifiedSignature: io.strimzi.operator.common.model.LabelsTest.testParseValidLabels()
@Test
public void testParseValidLabels() {
    String validLabels = "key1=value1,key2=value2";
    Map sourceMap = new HashMap<String, String>(2);
    sourceMap.put("key1", "value1");
    sourceMap.put("key2", "value2");
    Labels expected = Labels.fromMap(sourceMap);
    assertThat(Labels.fromString(validLabels), is(expected));
}


TestFilePath: vmware-archive/admiral/compute/src/test/java/com/vmware/admiral/compute/content/CompositeTemplateUtilTest.java
++++++ MethodDeclarationQualifiedSignature: com.vmware.admiral.compute.content.CompositeTemplateUtilTest.testDeserializeSerializeComplexDockerCompose()
@Test
public void testDeserializeSerializeComplexDockerCompose() throws IOException {
    String expectedContent = getContent("docker.complex.yaml");
    DockerCompose compose = deserializeDockerCompose(expectedContent);
    String content = serializeDockerCompose(compose);
    assertEqualsYamls(toUnixLineEnding(expectedContent), toUnixLineEnding(content));
    CompositeTemplate template = fromDockerComposeToCompositeTemplate(compose);
    assertNull(template.id);
    assertNull(template.status);
    assertComponentTypes(template.components);
    assertContainersComponents(ResourceType.CONTAINER_TYPE.getContentType(), 4, template.components);
    assertContainersComponents(ResourceType.NETWORK_TYPE.getContentType(), 0, template.components);
    assertContainersComponents(ResourceType.VOLUME_TYPE.getContentType(), 0, template.components);
    String contentTemplate = serializeCompositeTemplate(template);
    assertTrue((contentTemplate != null) && (!contentTemplate.isEmpty()));
}


TestFilePath: androidx/androidx/recyclerview/recyclerview/src/test/java/androidx/recyclerview/widget/SortedListTest.java
++++++ MethodDeclarationQualifiedSignature: androidx.recyclerview.widget.SortedListTest.testRandom()
@Test
public void testRandom() throws Throwable {
    Random random = new Random(System.nanoTime());
    List<Item> copy = new ArrayList<Item>();
    StringBuilder log = new StringBuilder();
    int id = 1;
    try {
        for (int i = 0; i < 10000; i++) {
            switch(random.nextInt(3)) {
                case // ADD
                0:
                    Item item = new Item(id++);
                    copy.add(item);
                    insert(item);
                    log.append("add ").append(item).append("\n");
                    break;
                case // REMOVE
                1:
                    if (copy.size() > 0) {
                        int index = random.nextInt(mList.size());
                        item = mList.get(index);
                        log.append("remove ").append(item).append("\n");
                        assertTrue(copy.remove(item));
                        assertTrue(mList.remove(item));
                    }
                    break;
                case // UPDATE
                2:
                    if (copy.size() > 0) {
                        int index = random.nextInt(mList.size());
                        item = mList.get(index);
                        // TODO this cannot work
                        Item newItem = new Item(item.id, item.cmpField, random.nextInt(1000));
                        while (newItem.data == item.data) {
                            newItem.data = random.nextInt(1000);
                        }
                        log.append("update ").append(item).append(" to ").append(newItem).append("\n");
                        int itemIndex = mList.add(newItem);
                        copy.remove(item);
                        copy.add(newItem);
                        assertSame(mList.get(itemIndex), newItem);
                        assertNotSame(mList.get(index), item);
                    }
                    break;
                case // UPDATE AT
                3:
                    if (copy.size() > 0) {
                        int index = random.nextInt(mList.size());
                        item = mList.get(index);
                        Item newItem = new Item(item.id, random.nextInt(), random.nextInt());
                        mList.updateItemAt(index, newItem);
                        copy.remove(item);
                        copy.add(newItem);
                        log.append("update at ").append(index).append(" ").append(item).append(" to ").append(newItem).append("\n");
                    }
            }
            int lastCmp = Integer.MIN_VALUE;
            for (int index = 0; index < copy.size(); index++) {
                assertFalse(mList.indexOf(copy.get(index)) == SortedList.INVALID_POSITION);
                assertTrue(mList.get(index).cmpField >= lastCmp);
                lastCmp = mList.get(index).cmpField;
                assertTrue(copy.contains(mList.get(index)));
            }
            for (int index = 0; index < mList.size(); index++) {
                assertNotNull(mList.mData[index]);
            }
            for (int index = mList.size(); index < mList.mData.length; index++) {
                assertNull(mList.mData[index]);
            }
        }
    } catch (Throwable t) {
        Collections.sort(copy, sItemComparator);
        log.append("Items:\n");
        for (Item item : copy) {
            log.append(item).append("\n");
        }
        log.append("SortedList:\n");
        for (int i = 0; i < mList.size(); i++) {
            log.append(mList.get(i)).append("\n");
        }
        throw new Throwable(" \nlog:\n" + log.toString(), t);
    }
}


TestFilePath: steveohara/j2mod/src/test/java/com/ghgande/j2mod/modbus/utils/BitVectorTest.java
++++++ MethodDeclarationQualifiedSignature: com.ghgande.j2mod.modbus.utils.BitVectorTest.testGetSetBytes()
@Test
public void testGetSetBytes() {
    byte[] testData = new byte[8];
    byte[] nullData = new byte[8];
    for (int i = 0; i < testData.length; i++) {
        testData[i] = (byte) i;
        nullData[i] = (byte) 0;
    }
    BitVector b1 = BitVector.createBitVector(nullData);
    b1.setBytes(testData);
    byte[] actualData = b1.getBytes();
    Assert.assertNotNull("Cannot retrieve bytes from bitvector", actualData);
    Assert.assertEquals("Returned data array does not have the same length as original", testData.length, actualData.length);
    for (int i = 0; i < testData.length; i++) {
        Assert.assertEquals("Byte " + i + " is not equal to testdata", testData[i], actualData[i]);
    }
}


TestFilePath: dhis2/dhis2-core/dhis-2/dhis-services/dhis-service-core/src/test/java/org/hisp/dhis/trackedentity/TrackedEntityInstanceServiceTest.java
++++++ MethodDeclarationQualifiedSignature: org.hisp.dhis.trackedentity.TrackedEntityInstanceServiceTest.testGetTrackedEntityInstanceByUid()
@Test
void testGetTrackedEntityInstanceByUid() {
    entityInstanceA1.setUid("A1");
    entityInstanceB1.setUid("B1");
    entityInstanceService.addTrackedEntityInstance(entityInstanceA1);
    entityInstanceService.addTrackedEntityInstance(entityInstanceB1);
    assertEquals(entityInstanceA1, entityInstanceService.getTrackedEntityInstance("A1"));
    assertEquals(entityInstanceB1, entityInstanceService.getTrackedEntityInstance("B1"));
}


TestFilePath: Cornutum/tcases/tcases-cli/src/test/java/org/cornutum/tcases/TestTcasesCommand.java
++++++ MethodDeclarationQualifiedSignature: org.cornutum.tcases.TestTcasesCommand.getTests_whenInputOnly()
@Test
public void getTests_whenInputOnly() throws Exception {
    // Given...
    InputStream inputDef = getClass().getResourceAsStream("tcases-Transform-Input.xml");
    // When...
    SystemTestDef testDef = TcasesIO.getTests(inputDef);
    // Then...
    SystemTestDef expectedTestDef = testResources_.read("tcases-Transform-Test.xml");
    assertThat("Test def generated", testDef, matches(new SystemTestDefMatcher(expectedTestDef)));
    // When...
    ByteArrayOutputStream testDefOut = new ByteArrayOutputStream();
    TcasesIO.writeTests(testDef, testDefOut);
    // Then...
    ByteArrayInputStream testDefString = new ByteArrayInputStream(testDefOut.toByteArray());
    assertThat("Test def written", testResources_.read(testDefString), matches(new SystemTestDefMatcher(expectedTestDef)));
}


TestFilePath: vsch/flexmark-java/flexmark-util/src/test/java/com/vladsch/flexmark/util/sequence/builder/PlainSegmentBuilderTest.java
++++++ MethodDeclarationQualifiedSignature: com.vladsch.flexmark.util.sequence.builder.PlainSegmentBuilderTest.test_optimizers3()
@Test
public void test_optimizers3() {
    String input = "012340123401234";
    BasedSequence sequence = BasedSequence.of(input);
    CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
    OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);
    segments.append(0, 5);
    segments.append("01234");
    assertEquals(escapeJavaString("OptimizedSegmentBuilder2{[0, 10), s=0:0, u=0:0, t=0:0, l=10, sz=1, na=1: [0, 10) }"), segments.toStringPrep());
    assertEquals(segments.toString(sequence).length(), segments.length());
}


TestFilePath: eseifert/gral/gral-core/src/test/java/de/erichseifert/gral/plots/PlotTest.java
++++++ MethodDeclarationQualifiedSignature: de.erichseifert.gral.plots.PlotTest.testDataAdd()
@Test
public void testDataAdd() {
    int sizeBefore, size;
    // Append
    DataSeries series3 = new DataSeries("series3", table, 0, 2);
    sizeBefore = plot.getData().size();
    plot.add(series3);
    size = plot.getData().size();
    assertEquals(sizeBefore + 1, size);
    assertEquals(series3, plot.get(size - 1));
    // Insert
    DataSeries series4 = new DataSeries("series4", table, 0);
    sizeBefore = plot.getData().size();
    plot.add(0, series4, false);
    size = plot.getData().size();
    assertEquals(sizeBefore + 1, size);
    assertEquals(series4, plot.get(0));
    assertFalse(plot.isVisible(series4));
}


TestFilePath: itext/itext7/commons/src/test/java/com/itextpdf/commons/actions/confirmations/ConfirmEventTest.java
++++++ MethodDeclarationQualifiedSignature: com.itextpdf.commons.actions.confirmations.ConfirmEventTest.constructorWithoutSequenceIdTest()
@Test
public void constructorWithoutSequenceIdTest() {
    ITextTestEvent iTextTestEvent = new ITextTestEvent(new SequenceId(), new TestMetaInfo(""), "eventType", "productName");
    ConfirmEvent confirmEvent = new ConfirmEvent(iTextTestEvent);
    Assert.assertSame(iTextTestEvent, confirmEvent.getConfirmedEvent());
    Assert.assertEquals("eventType", confirmEvent.getEventType());
    Assert.assertEquals("productName", confirmEvent.getProductName());
    Assert.assertSame(iTextTestEvent.getSequenceId(), confirmEvent.getSequenceId());
    Assert.assertEquals(EventConfirmationType.UNCONFIRMABLE, confirmEvent.getConfirmationType());
    Assert.assertNotNull(confirmEvent.getProductData());
    Assert.assertEquals(ITextTestEvent.class, confirmEvent.getClassFromContext());
}


TestFilePath: confluentinc/ksql/ksqldb-version-metrics-client/src/test/java/io/confluent/support/metrics/common/UuidTest.java
++++++ MethodDeclarationQualifiedSignature: io.confluent.support.metrics.common.UuidTest.stringRepresentationIsIdenticalToGeneratedUUID()
@Test
public void stringRepresentationIsIdenticalToGeneratedUUID() {
    // Given
    Uuid uuid = new Uuid();
    // When/Then
    assertEquals(uuid.getUuid(), uuid.toString());
}


TestFilePath: eclipse/eclipse-collections/unit-tests/src/test/java/org/eclipse/collections/impl/bimap/mutable/AbstractMutableBiMapTestCase.java
++++++ MethodDeclarationQualifiedSignature: org.eclipse.collections.impl.bimap.mutable.AbstractMutableBiMapTestCase.flipUniqueValues()
@Override
@Test
public void flipUniqueValues() {
    MutableBiMap<Integer, Character> map = this.classUnderTest();
    MutableBiMap<Character, Integer> result = map.flipUniqueValues();
    Assert.assertEquals(map.inverse(), result);
    Assert.assertNotSame(map.inverse(), result);
    result.put('d', 4);
    Assert.assertEquals(this.classUnderTest(), map);
}


TestFilePath: dhis2/dhis2-core/dhis-2/dhis-api/src/test/java/org/hisp/dhis/analytics/QueryKeyTest.java
++++++ MethodDeclarationQualifiedSignature: org.hisp.dhis.analytics.QueryKeyTest.testNoCollision()
@Test
void testNoCollision() {
    String keyA = new QueryKey().add("dimension", "dx").add("dimension", "aZASaK6ebLC").add("filter", "ou").add("aggregationType", AggregationType.SUM).asPlainKey();
    String keyB = new QueryKey().add("dimension", "dx").add("dimension", "aZASaK6ebLD").add("filter", "ou").add("aggregationType", AggregationType.SUM).asPlainKey();
    assertNotEquals(keyA, keyB);
}


TestFilePath: Vip-Augus/spring-analysis-note/spring-beans/src/test/java/org/springframework/beans/factory/support/QualifierAnnotationAutowireBeanFactoryTests.java
++++++ MethodDeclarationQualifiedSignature: org.springframework.beans.factory.support.QualifierAnnotationAutowireBeanFactoryTests.testAutowireCandidateWithFieldDescriptor()
@Ignore
@Test
public void testAutowireCandidateWithFieldDescriptor() throws Exception {
    DefaultListableBeanFactory lbf = new DefaultListableBeanFactory();
    ConstructorArgumentValues cavs1 = new ConstructorArgumentValues();
    cavs1.addGenericArgumentValue(JUERGEN);
    RootBeanDefinition person1 = new RootBeanDefinition(Person.class, cavs1, null);
    person1.addQualifier(new AutowireCandidateQualifier(TestQualifier.class));
    lbf.registerBeanDefinition(JUERGEN, person1); # JUERGEN: source input,
    ConstructorArgumentValues cavs2 = new ConstructorArgumentValues();
    cavs2.addGenericArgumentValue(MARK);
    RootBeanDefinition person2 = new RootBeanDefinition(Person.class, cavs2, null);
    lbf.registerBeanDefinition(MARK, person2); # lbf: output
    DependencyDescriptor qualifiedDescriptor = new DependencyDescriptor(QualifiedTestBean.class.getDeclaredField("qualified"), false);
    DependencyDescriptor nonqualifiedDescriptor = new DependencyDescriptor(QualifiedTestBean.class.getDeclaredField("nonqualified"), false);
    assertTrue(lbf.isAutowireCandidate(JUERGEN, null)); # <JUERGEN, lbf> related by isAutowireCandidate
    assertTrue(lbf.isAutowireCandidate(JUERGEN, nonqualifiedDescriptor));
    assertTrue(lbf.isAutowireCandidate(JUERGEN, qualifiedDescriptor));
    assertTrue(lbf.isAutowireCandidate(MARK, null));
    assertTrue(lbf.isAutowireCandidate(MARK, nonqualifiedDescriptor));
    assertFalse(lbf.isAutowireCandidate(MARK, qualifiedDescriptor));
}


TestFilePath: eclipse/eclipse-collections/unit-tests/src/test/java/org/eclipse/collections/impl/map/mutable/primitive/ObjectBooleanHashMapWithHashingStrategyTest.java
++++++ MethodDeclarationQualifiedSignature: org.eclipse.collections.impl.map.mutable.primitive.ObjectBooleanHashMapWithHashingStrategyTest.remove_with_hashing_strategy()
@Test
public void remove_with_hashing_strategy() {
    ObjectBooleanHashMapWithHashingStrategy<Person> map = ObjectBooleanHashMapWithHashingStrategy.newWithKeysValues(LAST_NAME_HASHING_STRATEGY, JOHNDOE, true, JANEDOE, false, JOHNSMITH, true, JANESMITH, false);
    map.remove(JANEDOE);
    Assert.assertEquals(ObjectBooleanHashMapWithHashingStrategy.newWithKeysValues(LAST_NAME_HASHING_STRATEGY, JOHNSMITH, false), map);
    map.remove(JOHNSMITH);
    Verify.assertEmpty(map);
    MutableList<String> collidingKeys = generateCollisions();
    ObjectBooleanHashMapWithHashingStrategy<String> map2 = ObjectBooleanHashMapWithHashingStrategy.newWithKeysValues(STRING_HASHING_STRATEGY, collidingKeys.get(0), true, collidingKeys.get(1), false, collidingKeys.get(2), true, collidingKeys.get(3), false);
    map2.remove(collidingKeys.get(3));
    Assert.assertEquals(ObjectBooleanHashMapWithHashingStrategy.newWithKeysValues(STRING_HASHING_STRATEGY, collidingKeys.get(0), true, collidingKeys.get(1), false, collidingKeys.get(2), true), map2);
    map2.remove(collidingKeys.get(0));
    Assert.assertEquals(ObjectBooleanHashMapWithHashingStrategy.newWithKeysValues(STRING_HASHING_STRATEGY, collidingKeys.get(1), false, collidingKeys.get(2), true), map2);
    Verify.assertSize(2, map2);
    ObjectBooleanHashMapWithHashingStrategy<Integer> map3 = ObjectBooleanHashMapWithHashingStrategy.newWithKeysValues(INTEGER_HASHING_STRATEGY, 1, true, null, false, 3, true);
    map3.remove(null);
    Assert.assertEquals(ObjectBooleanHashMapWithHashingStrategy.newWithKeysValues(INTEGER_HASHING_STRATEGY, 1, true, 3, true), map3);
}


TestFilePath: JMRI/JMRI/java/test/jmri/jmrit/display/layoutEditor/LayoutBlockTest.java
++++++ MethodDeclarationQualifiedSignature: jmri.jmrit.display.layoutEditor.LayoutBlockTest.testSetMemoryFromIdTagBlockValue()
@Test
public void testSetMemoryFromIdTagBlockValue() {
    // initialize the layout block and the related automatic block
    layoutBlock.initializeLayoutBlock();
    // get a memory and associate it with the layout block.
    Memory mem = jmri.InstanceManager.getDefault(jmri.MemoryManager.class).provideMemory("IM1");
    layoutBlock.setMemory(mem, "IM1");
    // verify the memory is associated
    Assert.assertEquals("memory saved", mem, layoutBlock.getMemory());
    // Get the referenced block
    Block block = jmri.InstanceManager.getDefault(jmri.BlockManager.class).getByUserName("Test Block");
    jmri.IdTag tag = new jmri.implementation.DefaultIdTag("1234");
    // change the value of the block.
    block.setValue(tag);
    // and verify the value is in the memory
    Assert.assertEquals("memory content same as block value", block.getValue(), mem.getValue());
}


TestFilePath: eclipse/eclipse-collections/unit-tests/src/test/java/org/eclipse/collections/impl/map/strategy/mutable/UnifiedMapWithHashingStrategyTest.java
++++++ MethodDeclarationQualifiedSignature: org.eclipse.collections.impl.map.strategy.mutable.UnifiedMapWithHashingStrategyTest.hashingStrategy()
@Test
public void hashingStrategy() {
    UnifiedMapWithHashingStrategy<Integer, Integer> map = UnifiedMapWithHashingStrategy.newWithKeysValues(INTEGER_HASHING_STRATEGY, 1, 1, 2, 2);
    Assert.assertSame(INTEGER_HASHING_STRATEGY, map.hashingStrategy());
}


TestFilePath: RWS/odata/odata_renderer/src/test/java/com/sdl/odata/renderer/json/writer/JsonErrorResponseWriterTest.java
++++++ MethodDeclarationQualifiedSignature: com.sdl.odata.renderer.json.writer.JsonErrorResponseWriterTest.testWriteError()
@Test
public void testWriteError() throws Exception {
    JsonErrorResponseWriter writer = new JsonErrorResponseWriter();
    String json = writer.getJsonError(new ODataServerException(EDM_ERROR, "EDM error"));
    assertEquals(prettyPrintJson(readContent(EXPECTED_ERROR_RESPONSE_PATH)), prettyPrintJson(json)); # two invocations & two outputs
}


TestFilePath: apache/openmeetings/openmeetings-web/src/test/java/org/apache/openmeetings/backup/TestImportUser.java
++++++ MethodDeclarationQualifiedSignature: org.apache.openmeetings.backup.TestImportUser.importNoLoginDeleted()
@Test
void importNoLoginDeleted() throws Exception {
    long userCount = userDao.count();
    File users = new File(getClass().getClassLoader().getResource(BACKUP_ROOT + "user/skip/users.xml").toURI());
    backupImport.importUsers(users.getParentFile());
    assertEquals(userCount, userDao.count(), "No records should be added"); # userDao.count() == userDao.count()
}


TestFilePath: gridgain/gridgain/modules/core/src/test/java/org/apache/ignite/client/FunctionalTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.ignite.client.FunctionalTest.testPutGet()
/**
 * Tested API:
 * <ul>
 * <li>{@link Ignition#startClient(ClientConfiguration)}</li>
 * <li>{@link IgniteClient#getOrCreateCache(String)}</li>
 * <li>{@link ClientCache#put(Object, Object)}</li>
 * <li>{@link ClientCache#get(Object)}</li>
 * <li>{@link ClientCache#containsKey(Object)}</li>
 * <li>{@link ClientCache#clear(Object)}</li>
 * </ul>
 */
@Test
public void testPutGet() throws Exception {
    // Existing cache, primitive key and object value
    try (Ignite ignored = Ignition.start(Config.getServerConfiguration());
        IgniteClient client = Ignition.startClient(getClientConfiguration())) {
        ClientCache<Integer, Person> cache = client.getOrCreateCache(Config.DEFAULT_CACHE_NAME);
        Integer key = 1;
        Person val = new Person(key, "Joe");
        cache.put(key, val);
        assertTrue(cache.containsKey(key));
        Person cachedVal = cache.get(key);
        assertEquals(val, cachedVal);
        cache.clear(key);
        assertFalse(cache.containsKey(key));
        assertNull(cache.get(key));
    }
    // Non-existing cache, object key and primitive value
    try (Ignite ignored = Ignition.start(Config.getServerConfiguration());
        IgniteClient client = Ignition.startClient(getClientConfiguration())) {
        ClientCache<Person, Integer> cache = client.getOrCreateCache("testPutGet");
        Integer val = 1;
        Person key = new Person(val, "Joe");
        cache.put(key, val);
        Integer cachedVal = cache.get(key);
        assertEquals(val, cachedVal);
        cache.clear(key);
        assertFalse(cache.containsKey(key));
        assertNull(cache.get(key));
    }
    // Object key and Object value
    try (Ignite ignored = Ignition.start(Config.getServerConfiguration());
        IgniteClient client = Ignition.startClient(getClientConfiguration())) {
        ClientCache<Person, Person> cache = client.getOrCreateCache("testPutGet");
        Person key = new Person(1, "Joe Key");
        Person val = new Person(1, "Joe Value");
        cache.put(key, val);
        Person cachedVal = cache.get(key);
        assertEquals(val, cachedVal);
        cache.clear(key);
        assertFalse(cache.containsKey(key));
        assertNull(cache.get(key));
    }
}


TestFilePath: openlookeng/hetu-core/presto-spi/src/test/java/io/prestosql/spi/TestHostAddress.java
++++++ MethodDeclarationQualifiedSignature: io.prestosql.spi.TestHostAddress.testRoundTrip()
@Test
public void testRoundTrip() {
    HostAddress address = HostAddress.fromParts("[1111:2222:3333:4444:5555:6666:7777:8888]", 1234);
    HostAddress fromParts = HostAddress.fromParts(address.getHostText(), address.getPort());
    assertEquals(address, fromParts);
    HostAddress fromString = HostAddress.fromString(address.toString());
    assertEquals(address, fromString);
    assertEquals(fromParts, fromString);
}


TestFilePath: grishka/Smithereen/src/test/java/smithereen/jsonld/URDNA2015Tests.java
++++++ MethodDeclarationQualifiedSignature: smithereen.jsonld.URDNA2015Tests.test004()
@Test
@DisplayName("bnode plus embed w/subject")
void test004() {
    List<String> input = readResourceAsLines("/urdna2015/test004-in.nq");
    List<RDFTriple> result = URDNA2015.normalize(parseRDF(input));
    List<String> expect = readResourceAsLines("/urdna2015/test004-urdna2015.nq");
    List<String> strResult = eachToString(result);
    Collections.sort(strResult);
    assertLinesMatch(expect, strResult); # two outputs 
}


TestFilePath: ihmcrobotics/ihmc-open-robotics-software/ihmc-robotics-toolkit/src/test/java/us/ihmc/robotics/stateMachine/old/conditionBasedStateMachine/StateMachineExampleTwoTest.java
++++++ MethodDeclarationQualifiedSignature: us.ihmc.robotics.stateMachine.old.conditionBasedStateMachine.StateMachineExampleTwoTest.testComplexStateMachineExample()
@Test
public void testComplexStateMachineExample() {
    YoRegistry registry = new YoRegistry("registry");
    final YoInteger ticksInStateOne = new YoInteger("ticksInStateOne", registry);
    final YoInteger ticksInStateTwo = new YoInteger("ticksInStateTwo", registry);
    final YoInteger ticksInStateThree = new YoInteger("ticksInStateThree", registry);
    final YoInteger ticksInStateFour = new YoInteger("ticksInStateFour", registry);
    final YoBoolean inStateOne = new YoBoolean("inStateOne", registry);
    final YoBoolean inStateTwo = new YoBoolean("inStateTwo", registry);
    final YoBoolean inStateThree = new YoBoolean("inStateThree", registry);
    final YoBoolean inStateFour = new YoBoolean("inStateFour", registry);
    final YoBoolean didTransitionIntoStateOne = new YoBoolean("didTransitionIntoStateOne", registry);
    final YoBoolean didTransitionIntoStateTwo = new YoBoolean("didTransitionIntoStateTwo", registry);
    final YoBoolean didTransitionIntoStateThree = new YoBoolean("didTransitionIntoStateThree", registry);
    final YoBoolean didTransitionIntoStateFour = new YoBoolean("didTransitionIntoStateFour", registry);
    final YoBoolean didTransitionOutOfStateOne = new YoBoolean("didTransitionOutOfStateOne", registry);
    final YoBoolean didTransitionOutOfStateTwo = new YoBoolean("didTransitionOutOfStateTwo", registry);
    final YoBoolean didTransitionOutOfStateThree = new YoBoolean("didTransitionOutOfStateThree", registry);
    final YoBoolean didTransitionOutOfStateFour = new YoBoolean("didTransitionOutOfStateFour", registry);
    final YoBoolean transitionFromOneToFour = new YoBoolean("transitionFromOneToFour", registry);
    final YoBoolean transitionFromThreeToFour = new YoBoolean("transitionFromThreeToFour", registry);
    final YoBoolean transitionFromThreeToOne = new YoBoolean("transitionFromThreeToOne", registry);
    final YoBoolean threeToOneTransitionAction = new YoBoolean("threeToOneTransitionAction", registry);
    YoDouble timeProvider = new YoDouble("time", registry);
    timeProvider.set(13.3);
    StateMachine<StateEnum> stateMachine = new StateMachine<StateEnum>("complexStateMachine", "switchTime", StateEnum.class, StateEnum.FOUR, timeProvider, registry);
    ExampleState stateOne = new ExampleState(StateEnum.ONE, inStateOne, didTransitionIntoStateOne, didTransitionOutOfStateOne, ticksInStateOne);
    ExampleState stateTwo = new ExampleState(StateEnum.TWO, inStateTwo, didTransitionIntoStateTwo, didTransitionOutOfStateTwo, ticksInStateTwo);
    ExampleState stateThree = new ExampleState(StateEnum.THREE, inStateThree, didTransitionIntoStateThree, didTransitionOutOfStateThree, ticksInStateThree);
    ExampleState stateFour = new ExampleState(StateEnum.FOUR, inStateFour, didTransitionIntoStateFour, didTransitionOutOfStateFour, ticksInStateFour);
    // Do this transition by calling stateOne.transitionToDefaultNextState();
    stateOne.setDefaultNextState(StateEnum.TWO);
    stateOne.addStateTransition(StateEnum.FOUR, new ExampleStateTransitionCondition(transitionFromOneToFour));
    stateTwo.addDelayBasedStateTransition(StateEnum.THREE, 1.0);
    StateTransitionCondition threeToFourCondition = new ExampleStateTransitionCondition(transitionFromThreeToFour);
    stateThree.addStateTransition(StateEnum.FOUR, threeToFourCondition);
    StateTransitionCondition stateTransitionCondition = new ExampleStateTransitionCondition(transitionFromThreeToOne);
    StateTransitionAction stateTransitionAction = new ExampleStateTransitionAction(threeToOneTransitionAction);
    StateTransition<StateEnum> stateTransition = new StateTransition<StateEnum>(StateEnum.ONE, stateTransitionCondition, stateTransitionAction);
    stateThree.addStateTransition(stateTransition);
    // Transition into itself (and call the transitionIntoAction() each time.
    stateThree.addImmediateStateTransition(StateEnum.THREE);
    ArrayList<StateTransition<StateEnum>> stateTransitions = stateThree.getStateTransitions();
    assertEquals(3, stateTransitions.size());
    assertEquals(StateEnum.FOUR, stateTransitions.get(0).getNextStateEnum());
    assertEquals(StateEnum.ONE, stateTransitions.get(1).getNextStateEnum());
    assertEquals(StateEnum.THREE, stateTransitions.get(2).getNextStateEnum());
    stateFour.addImmediateStateTransition(StateEnum.THREE);
    stateMachine.addState(stateOne);
    stateMachine.addState(stateTwo);
    stateMachine.addState(stateThree);
    stateMachine.addState(stateFour);
    assertEquals(stateFour, stateMachine.getCurrentState());
    assertEquals(StateEnum.FOUR, stateMachine.getCurrentStateEnum());
    assertEquals(null, stateMachine.getPreviousStateEnum());
    assertEquals(null, stateMachine.getPreviousState());
    assertNull(stateOne.getPreviousState());
    assertFalse(inStateOne.getBooleanValue());
    assertFalse(didTransitionIntoStateOne.getBooleanValue());
    assertFalse(didTransitionOutOfStateOne.getBooleanValue());
    assertEquals(0, ticksInStateOne.getIntegerValue());
    assertNull(stateFour.getPreviousState());
    assertFalse(inStateFour.getBooleanValue());
    assertFalse(didTransitionIntoStateFour.getBooleanValue());
    assertFalse(didTransitionOutOfStateFour.getBooleanValue());
    assertEquals(0, ticksInStateFour.getIntegerValue());
    stateMachine.setCurrentState(StateEnum.ONE);
    assertTrue(inStateOne.getBooleanValue());
    assertEquals(stateOne, stateMachine.getCurrentState());
    assertEquals(StateEnum.ONE, stateMachine.getCurrentStateEnum());
    assertTrue(didTransitionIntoStateOne.getBooleanValue());
    assertFalse(didTransitionOutOfStateOne.getBooleanValue());
    // setCurrentState should do the transitionInto, but not the doAction().
    assertEquals(0, ticksInStateOne.getIntegerValue());
    stateMachine.doAction();
    stateMachine.checkTransitionConditions();
    assertTrue(inStateOne.getBooleanValue());
    assertTrue(didTransitionIntoStateOne.getBooleanValue());
    assertFalse(didTransitionOutOfStateOne.getBooleanValue());
    assertEquals(1, ticksInStateOne.getIntegerValue());
    stateOne.transitionToDefaultNextState();
    stateMachine.doAction();
    stateMachine.checkTransitionConditions();
    assertFalse(inStateOne.getBooleanValue());
    assertTrue(didTransitionIntoStateOne.getBooleanValue());
    assertTrue(didTransitionOutOfStateOne.getBooleanValue());
    assertEquals(2, ticksInStateOne.getIntegerValue());
    assertEquals(StateEnum.ONE, stateMachine.getPreviousStateEnum());
    assertEquals(stateOne, stateMachine.getPreviousState());
    assertEquals(stateOne, stateTwo.getPreviousState());
    assertTrue(inStateTwo.getBooleanValue());
    assertTrue(didTransitionIntoStateTwo.getBooleanValue());
    assertFalse(didTransitionOutOfStateTwo.getBooleanValue());
    assertEquals(0, ticksInStateTwo.getIntegerValue());
    stateMachine.doAction();
    stateMachine.checkTransitionConditions();
    assertTrue(inStateTwo.getBooleanValue());
    assertTrue(didTransitionIntoStateTwo.getBooleanValue());
    assertFalse(didTransitionOutOfStateTwo.getBooleanValue());
    assertEquals(1, ticksInStateTwo.getIntegerValue());
    assertFalse(didTransitionIntoStateThree.getBooleanValue());
    timeProvider.add(1.01);
    stateMachine.doAction();
    stateMachine.checkTransitionConditions();
    assertFalse(inStateTwo.getBooleanValue());
    assertTrue(didTransitionIntoStateTwo.getBooleanValue());
    assertTrue(didTransitionOutOfStateTwo.getBooleanValue());
    assertEquals(2, ticksInStateTwo.getIntegerValue());
    assertEquals(StateEnum.TWO, stateMachine.getPreviousStateEnum());
    assertEquals(stateTwo, stateMachine.getPreviousState());
    assertEquals(stateTwo, stateThree.getPreviousState());
    assertTrue(inStateThree.getBooleanValue());
    assertTrue(didTransitionIntoStateThree.getBooleanValue());
    assertFalse(didTransitionOutOfStateThree.getBooleanValue());
    assertEquals(0, ticksInStateThree.getIntegerValue());
    didTransitionIntoStateThree.set(false);
    stateMachine.doAction();
    stateMachine.checkTransitionConditions();
    assertTrue(inStateThree.getBooleanValue());
    assertTrue(didTransitionIntoStateThree.getBooleanValue());
    assertTrue(didTransitionOutOfStateThree.getBooleanValue());
    assertEquals(1, ticksInStateThree.getIntegerValue());
    didTransitionOutOfStateThree.set(false);
    didTransitionIntoStateThree.set(false);
    stateMachine.doAction();
    stateMachine.checkTransitionConditions();
    assertTrue(inStateThree.getBooleanValue());
    assertTrue(didTransitionIntoStateThree.getBooleanValue());
    assertTrue(didTransitionOutOfStateThree.getBooleanValue());
    assertEquals(2, ticksInStateThree.getIntegerValue());
    transitionFromThreeToFour.set(true);
    didTransitionIntoStateThree.set(false);
    didTransitionOutOfStateThree.set(false);
    stateMachine.doAction();
    stateMachine.checkTransitionConditions();
    assertFalse(inStateThree.getBooleanValue());
    assertFalse(didTransitionIntoStateThree.getBooleanValue());
    assertTrue(didTransitionOutOfStateThree.getBooleanValue());
    assertEquals(3, ticksInStateThree.getIntegerValue());
    assertEquals(StateEnum.THREE, stateMachine.getPreviousStateEnum());
    assertEquals(stateThree, stateMachine.getPreviousState());
    assertEquals(stateThree, stateFour.getPreviousState());
    assertTrue(inStateFour.getBooleanValue());
    assertTrue(didTransitionIntoStateFour.getBooleanValue());
    assertFalse(didTransitionOutOfStateFour.getBooleanValue());
    assertEquals(0, ticksInStateFour.getIntegerValue());
    didTransitionIntoStateThree.set(false);
    didTransitionOutOfStateThree.set(false);
    stateMachine.doAction();
    stateMachine.checkTransitionConditions();
    assertFalse(inStateFour.getBooleanValue());
    assertTrue(didTransitionIntoStateFour.getBooleanValue());
    assertTrue(didTransitionOutOfStateFour.getBooleanValue());
    assertEquals(1, ticksInStateFour.getIntegerValue());
    assertEquals(StateEnum.FOUR, stateMachine.getPreviousStateEnum());
    assertEquals(stateFour, stateMachine.getPreviousState());
    assertEquals(stateFour, stateThree.getPreviousState());
    assertTrue(inStateThree.getBooleanValue());
    assertTrue(didTransitionIntoStateThree.getBooleanValue());
    assertFalse(didTransitionOutOfStateThree.getBooleanValue());
    assertEquals(3, ticksInStateThree.getIntegerValue());
    transitionFromThreeToOne.set(true);
    transitionFromThreeToFour.set(false);
    didTransitionIntoStateOne.set(false);
    didTransitionOutOfStateOne.set(false);
    assertFalse(threeToOneTransitionAction.getBooleanValue());
    stateMachine.doAction();
    stateMachine.checkTransitionConditions();
    assertTrue(threeToOneTransitionAction.getBooleanValue());
    assertTrue(inStateOne.getBooleanValue());
    assertTrue(didTransitionIntoStateOne.getBooleanValue());
    assertFalse(didTransitionOutOfStateOne.getBooleanValue());
    assertEquals(2, ticksInStateOne.getIntegerValue());
    stateMachine.doAction();
    stateMachine.checkTransitionConditions();
    assertEquals(StateEnum.THREE, stateMachine.getPreviousStateEnum());
    assertEquals(stateThree, stateMachine.getPreviousState());
    assertEquals(stateThree, stateOne.getPreviousState());
    assertTrue(inStateOne.getBooleanValue());
    assertTrue(didTransitionIntoStateOne.getBooleanValue());
    assertFalse(didTransitionOutOfStateOne.getBooleanValue());
    assertEquals(3, ticksInStateOne.getIntegerValue());
    transitionFromOneToFour.set(true);
    didTransitionIntoStateFour.set(false);
    didTransitionOutOfStateFour.set(false);
    stateMachine.doAction();
    stateMachine.checkTransitionConditions();
    assertFalse(inStateOne.getBooleanValue());
    assertTrue(didTransitionIntoStateOne.getBooleanValue());
    assertTrue(didTransitionOutOfStateOne.getBooleanValue());
    assertEquals(4, ticksInStateOne.getIntegerValue());
    assertEquals(stateOne, stateFour.getPreviousState());
    assertEquals(stateFour, stateMachine.getCurrentState());
    assertTrue(inStateFour.getBooleanValue());
    assertTrue(didTransitionIntoStateFour.getBooleanValue());
    assertFalse(didTransitionOutOfStateFour.getBooleanValue());
    assertEquals(1, ticksInStateFour.getIntegerValue());
    assertEquals("ONE: (FOUR)", stateOne.toString());
    assertEquals("TWO: (THREE)", stateTwo.toString());
    assertEquals("THREE: (FOUR, ONE, THREE)", stateThree.toString());
    assertEquals("FOUR: (THREE)", stateFour.toString());
    assertEquals("State Machine:\nONE: (FOUR)\nTWO: (THREE)\nTHREE: (FOUR, ONE, THREE)\nFOUR: (THREE)\n", stateMachine.toString());
}


TestFilePath: spring-attic/reactive-streams-commons/src/test/java/rsc/util/SpscLinkedArrayQueueTest.java
++++++ MethodDeclarationQualifiedSignature: rsc.util.SpscLinkedArrayQueueTest.grow()
@Test
public void grow() {
    for (int i = 0; i < 32; i++) {
        Assert.assertTrue(queue.offer(i));
        Assert.assertFalse(queue.isEmpty());
        Assert.assertEquals(1 + i, queue.size());
    }
    for (int i = 0; i < 32; i++) {
        Assert.assertEquals((Integer) i, queue.peek());
        Assert.assertEquals((Integer) i, queue.poll());
    }
    Assert.assertTrue(queue.isEmpty());
    Assert.assertEquals(0, queue.size());
}


TestFilePath: Vip-Augus/spring-analysis-note/spring-core/src/test/java/org/springframework/core/MethodParameterTests.java
++++++ MethodDeclarationQualifiedSignature: org.springframework.core.MethodParameterTests.testHashCode()
@Test
public void testHashCode() throws NoSuchMethodException {
    assertEquals(stringParameter.hashCode(), stringParameter.hashCode());
    assertEquals(longParameter.hashCode(), longParameter.hashCode());
    assertEquals(intReturnType.hashCode(), intReturnType.hashCode());
    Method method = getClass().getMethod("method", String.class, Long.TYPE);
    MethodParameter methodParameter = new MethodParameter(method, 0);
    assertEquals(stringParameter.hashCode(), methodParameter.hashCode());
    assertNotEquals(longParameter.hashCode(), methodParameter.hashCode());
}


TestFilePath: gridgain/gridgain/modules/core/src/test/java/org/apache/ignite/internal/processors/cache/distributed/dht/GridCacheDhtEntrySelfTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.ignite.internal.processors.cache.distributed.dht.GridCacheDhtEntrySelfTest.testClearWithReaders()
/**
 * @throws Exception If failed.
 */
@Test
public void testClearWithReaders() throws Exception {
    Integer key = 1;
    IgniteBiTuple<ClusterNode, ClusterNode> t = getNodes(key);
    ClusterNode primary = t.get1();
    ClusterNode other = t.get2();
    IgniteCache<Integer, String> near0 = near(grid(primary.id()));
    IgniteCache<Integer, String> near1 = near(grid(other.id()));
    assert near0 != near1;
    GridDhtCacheAdapter<Integer, String> dht0 = dht(grid(primary.id()));
    GridDhtCacheAdapter<Integer, String> dht1 = dht(grid(other.id()));
    // Put on primary node.
    String val = "v1";
    near0.put(key, val); # 
    GridDhtCacheEntry e0 = (GridDhtCacheEntry) dht0.peekEx(key);
    GridDhtCacheEntry e1 = (GridDhtCacheEntry) dht1.peekEx(key);
    assert e0 == null || e0.readers().isEmpty();
    assert e1 == null || e1.readers().isEmpty();
    // Get value on other node.
    assertEquals(val, near1.get(key)); # val: source input, near1.get(key)# follow-up output 
    e0 = (GridDhtCacheEntry) dht0.peekEx(key);
    e1 = (GridDhtCacheEntry) dht1.peekEx(key);
    assert e0 != null;
    assert e0.readers().contains(other.id());
    assert e1 == null || e1.readers().isEmpty();
    assert !internalCache(near0).clearLocally(key);
    assertEquals(1, near0.localSize(CachePeekMode.ALL));
    assertEquals(1, dht0.size());
    assertEquals(1, near1.localSize(CachePeekMode.ALL));
    assertEquals(0, dht1.size());
}


TestFilePath: OpenOLAT/OpenOLAT/src/test/java/org/olat/resource/OLATResourceManagerTest.java
++++++ MethodDeclarationQualifiedSignature: org.olat.resource.OLATResourceManagerTest.findResourceable()
@Test
public void findResourceable() {
    String resName = UUID.randomUUID().toString();
    TestResourceable resource = new TestResourceable(8213655l, resName);
    OLATResource ores = rm.findOrPersistResourceable(resource);
    dbInstance.commitAndCloseSession();
    Assert.assertNotNull(ores);
    Assert.assertNotNull(ores.getKey());
    // find by id
    OLATResource reloadedOres = rm.findResourceable(8213655l, resName);
    Assert.assertNotNull(reloadedOres);
    Assert.assertEquals(ores, reloadedOres);
}


TestFilePath: swimos/swim/swim-java/swim-runtime/swim-core/swim.spatial/src/test/java/swim/spatial/QTreePageSpec.java
++++++ MethodDeclarationQualifiedSignature: swim.spatial.QTreePageSpec.splitLeafWithLiftedTiles()
@Test
public void splitLeafWithLiftedTiles() {
    final QTree<String, Z2Shape, String> tree = QTree.empty(Z2Shape.shapeForm());
    QTreePage<String, Z2Shape, String> page = tree.root;
    page = page.updated("k0", null, 6, 0x40L, 2, 0x5cL, "v0", tree);
    page = page.updated("k1", null, 2, 0x5cL, 6, 0x40L, "v1", tree);
    page = page.updated("k2", null, 0, 0x5cL, 0, 0x7cL, "v2", tree);
    page = page.updated("k3", null, 0, 0x7cL, 0, 0x5cL, "v3", tree);
    page = page.split(tree);
    assertEquals(page.arity(), 3);
    assertEquals(page.x(), 0xfc00000000000001L);
    assertEquals(page.y(), 0xfc00000000000001L);
    assertEquals(page.xRank(), 6);
    assertEquals(page.yRank(), 6);
    assertEquals(page.xBase(), 0x40L);
    assertEquals(page.yBase(), 0x40L);
    assertEquals(page.span(), 4);
    assertTrue(page.containsKey("k0", 6, 0x40L, 2, 0x5cL, tree));
    assertTrue(page.containsKey("k1", 2, 0x5cL, 6, 0x40L, tree));
    assertTrue(page.containsKey("k2", 0, 0x5cL, 0, 0x7cL, tree));
    assertTrue(page.containsKey("k3", 0, 0x7cL, 0, 0x5cL, tree));
    assertEquals(page.get("k0", 6, 0x40L, 2, 0x5cL, tree), "v0");
    assertEquals(page.get("k1", 2, 0x5cL, 6, 0x40L, tree), "v1");
    assertEquals(page.get("k2", 0, 0x5cL, 0, 0x7cL, tree), "v2");
    assertEquals(page.get("k3", 0, 0x7cL, 0, 0x5cL, tree), "v3");
    final QTreePage<String, Z2Shape, String> page0 = page.getPage(0);
    assertEquals(page0.x(), 0xc000000000000017L);
    assertEquals(page0.y(), 0xfc00000000000001L);
    assertEquals(page0.xRank(), 2);
    assertEquals(page0.yRank(), 6);
    assertEquals(page0.xBase(), 0x5cL);
    assertEquals(page0.yBase(), 0x40L);
    assertEquals(page0.span(), 2);
    assertTrue(page0.containsKey("k1", 2, 0x5cL, 6, 0x40L, tree));
    assertTrue(page0.containsKey("k2", 0, 0x5cL, 0, 0x7cL, tree));
    assertEquals(page0.get("k1", 2, 0x5cL, 6, 0x40L, tree), "v1");
    assertEquals(page0.get("k2", 0, 0x5cL, 0, 0x7cL, tree), "v2");
    final QTreePage<String, Z2Shape, String> page1 = page.getPage(1);
    assertEquals(page1.x(), 0x000000000000007cL);
    assertEquals(page1.y(), 0x000000000000005cL);
    assertEquals(page1.xRank(), 0);
    assertEquals(page1.yRank(), 0);
    assertEquals(page1.xBase(), 0x7cL);
    assertEquals(page1.yBase(), 0x5cL);
    assertEquals(page1.span(), 1);
    assertTrue(page1.containsKey("k3", 0, 0x7cL, 0, 0x5cL, tree));
    assertEquals(page1.get("k3", 0, 0x7cL, 0, 0x5cL, tree), "v3");
}


TestFilePath: apache/activemq-artemis/tests/integration-tests/src/test/java/org/apache/activemq/artemis/tests/integration/journal/NIOJournalCompactTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.activemq.artemis.tests.integration.journal.NIOJournalCompactTest.testLiveSize()
@Test
public void testLiveSize() throws Exception {
    setup(2, 60 * 1024, true);
    createJournal();
    startJournal();
    loadAndCheck();
    ArrayList<Long> listToDelete = new ArrayList<>();
    ArrayList<Integer> expectedSizes = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
        long id = idGenerator.generateID();
        listToDelete.add(id);
        expectedSizes.add(recordLength + JournalImpl.SIZE_ADD_RECORD + 1);
        add(id);
        journal.forceMoveNextFile();
        update(id);
        expectedSizes.add(recordLength + JournalImpl.SIZE_ADD_RECORD + 1);
        journal.forceMoveNextFile();
    }
    JournalFile[] files = journal.getDataFiles();
    stopJournal();
    createJournal();
    startJournal();
    loadAndCheck();
    journal.forceMoveNextFile();
    JournalFile[] files2 = journal.getDataFiles();
    Assert.assertEquals(files.length, files2.length);
    for (int i = 0; i < files.length; i++) {
        Assert.assertEquals(expectedSizes.get(i).intValue(), files[i].getLiveSize());
        Assert.assertEquals(expectedSizes.get(i).intValue(), files2[i].getLiveSize());
    }
    for (long id : listToDelete) {
        delete(id);
    }
    journal.forceMoveNextFile();
    JournalFile[] files3 = journal.getDataFiles();
    for (JournalFile file : files3) {
        Assert.assertEquals(0, file.getLiveSize());
    }
    stopJournal();
    createJournal();
    startJournal();
    loadAndCheck();
    files3 = journal.getDataFiles();
    for (JournalFile file : files3) {
        Assert.assertEquals(0, file.getLiveSize());
    }
}


TestFilePath: trinodb/trino/plugin/trino-iceberg/src/test/java/io/trino/plugin/iceberg/TestIcebergTableWithCustomLocation.java
++++++ MethodDeclarationQualifiedSignature: io.trino.plugin.iceberg.TestIcebergTableWithCustomLocation.testCreateRenameDrop()
@Test
public void testCreateRenameDrop() {
    String tableName = "test_create_rename_drop";
    String renamedName = "test_create_rename_drop_renamed";
    assertQuerySucceeds(format("CREATE TABLE %s as select 1 as val", tableName));
    Optional<Table> table = metastore.getTable("tpch", tableName);
    assertThat(table).as("Table should exist").isPresent();
    String tableInitialLocation = table.get().getStorage().getLocation();
    assertQuerySucceeds(format("ALTER TABLE %s RENAME TO %s", tableName, renamedName));
    Optional<Table> renamedTable = metastore.getTable("tpch", renamedName);
    assertThat(renamedTable).as("Table should exist").isPresent();
    String renamedTableLocation = renamedTable.get().getStorage().getLocation();
    assertEquals(renamedTableLocation, tableInitialLocation, "Location should not be changed");
    assertQuerySucceeds(format("DROP TABLE %s", renamedName));
    assertThat(metastore.getTable("tpch", tableName)).as("Initial table should not exist").isEmpty();
    assertThat(metastore.getTable("tpch", renamedName)).as("Renamed table should be dropped").isEmpty();
}


TestFilePath: baremaps/baremaps/baremaps-core/src/test/java/com/baremaps/core/database/PostgresRelationRepositoryTest.java
++++++ MethodDeclarationQualifiedSignature: com.baremaps.core.database.PostgresRelationRepositoryTest.insert()
@Test
@Tag("integration")
void insert() throws RepositoryException {
    relationRepository.put(RELATION_2);
    assertEquals(RELATION_2, relationRepository.get(RELATION_2.getId()));
}


TestFilePath: Vip-Augus/spring-analysis-note/spring-core/src/test/java/org/springframework/util/StopWatchTests.java
++++++ MethodDeclarationQualifiedSignature: org.springframework.util.StopWatchTests.validUsage()
@Test
public void validUsage() throws Exception {
    String id = "myId";
    StopWatch sw = new StopWatch(id);
    long int1 = 166L;
    long int2 = 45L;
    String name1 = "Task 1";
    String name2 = "Task 2";
    assertFalse(sw.isRunning());
    sw.start(name1);
    Thread.sleep(int1);
    assertTrue(sw.isRunning());
    assertEquals(name1, sw.currentTaskName());
    sw.stop();
    // TODO are timings off in JUnit? Why do these assertions sometimes fail
    // under both Ant and Eclipse?
    // long fudgeFactor = 5L;
    // assertTrue("Unexpected timing " + sw.getTotalTime(), sw.getTotalTime() >=
    // int1);
    // assertTrue("Unexpected timing " + sw.getTotalTime(), sw.getTotalTime() <= int1
    // + fudgeFactor);
    sw.start(name2);
    Thread.sleep(int2);
    sw.stop();
    // assertTrue("Unexpected timing " + sw.getTotalTime(), sw.getTotalTime() >= int1
    // + int2);
    // assertTrue("Unexpected timing " + sw.getTotalTime(), sw.getTotalTime() <= int1
    // + int2 + fudgeFactor);
    assertTrue(sw.getTaskCount() == 2);
    String pp = sw.prettyPrint();
    assertTrue(pp.contains(name1));
    assertTrue(pp.contains(name2));
    StopWatch.TaskInfo[] tasks = sw.getTaskInfo();
    assertTrue(tasks.length == 2);
    assertTrue(tasks[0].getTaskName().equals(name1));
    assertTrue(tasks[1].getTaskName().equals(name2));
    String toString = sw.toString();
    assertTrue(toString.contains(id));
    assertTrue(toString.contains(name1));
    assertTrue(toString.contains(name2));
    assertEquals(id, sw.getId());
}


TestFilePath: deptofdefense/AndroidTacticalAssaultKit-CIV/atak/ATAK/app/src/androidTest/java/com/atakmap/net/AbstractAtakAuthenticationDatabaseTest.java
++++++ MethodDeclarationQualifiedSignature: com.atakmap.net.AbstractAtakAuthenticationDatabaseTest.insertTypeAndServerRoundtrip_with_no_expiration()
@Test
public void insertTypeAndServerRoundtrip_with_no_expiration() throws IOException {
    try (FileUtils.AutoDeleteFile f = FileUtils.AutoDeleteFile.createTempFile()) {
        final String type = "test_type";
        final String site = "the.test.server";
        final String username = "abcdef";
        final String password = "123456";
        final boolean expires = false;
        AtakAuthenticationDatabaseIFace authdb = newInstance(f.file);
        authdb.saveCredentialsForType(type, site, username, password, expires);
        AtakAuthenticationCredentials creds = authdb.getCredentialsForType(type, site);
        assertNotNull(creds);
        assertEquals(type, creds.type);
        assertEquals(site, creds.site);
        assertEquals(username, creds.username);
        assertEquals(password, creds.password);
    }
}


TestFilePath: xiancloud/xian/xian-zookeeper/xian-curator/xian-curator-x-discovery/src/test/java/org/apache/curator/x/discovery/TestJsonInstanceSerializer.java
++++++ MethodDeclarationQualifiedSignature: org.apache.curator.x.discovery.TestJsonInstanceSerializer.testBasic()
@Test
public void testBasic() throws Exception {
    JsonInstanceSerializer<String> serializer = new JsonInstanceSerializer<String>(String.class);
    ServiceInstance<String> instance = new ServiceInstance<String>("name", "id", "address", 10, 20, "payload", 0, ServiceType.DYNAMIC, new UriSpec("{a}/b/{c}"), true);
    byte[] bytes = serializer.serialize(instance);
    ServiceInstance<String> rhs = serializer.deserialize(bytes);
    Assert.assertEquals(instance, rhs);
    Assert.assertEquals(instance.getId(), rhs.getId());
    Assert.assertEquals(instance.getName(), rhs.getName());
    Assert.assertEquals(instance.getPayload(), rhs.getPayload());
    Assert.assertEquals(instance.getAddress(), rhs.getAddress());
    Assert.assertEquals(instance.getPort(), rhs.getPort());
    Assert.assertEquals(instance.getSslPort(), rhs.getSslPort());
    Assert.assertEquals(instance.getUriSpec(), rhs.getUriSpec());
    Assert.assertEquals(instance.isEnabled(), rhs.isEnabled());
}


TestFilePath: ChargeTimeEU/Java-OCA-OCPP/ocpp-v1_6/src/test/java/eu/chargetime/ocpp/model/test/SampledValueTest.java
++++++ MethodDeclarationQualifiedSignature: eu.chargetime.ocpp.model.test.SampledValueTest.setMeasurand_energyActiveImportInterval_measurandIsSet()
@Test
public void setMeasurand_energyActiveImportInterval_measurandIsSet() {
    // Given
    String measurand = "Energy.Active.Import.Interval";
    // When
    sampledValue.setMeasurand(measurand);
    // Then
    assertThat(sampledValue.getMeasurand(), equalTo(measurand));
}


TestFilePath: google/binnavi/src/test/java/com/google/security/zynamics/binnavi/API/disassembly/ViewTest.java
++++++ MethodDeclarationQualifiedSignature: com.google.security.zynamics.binnavi.API.disassembly.ViewTest.testCreateCodeNode()
@Test
public void testCreateCodeNode() throws PartialLoadException, com.google.security.zynamics.binnavi.API.disassembly.CouldntLoadDataException {
    final MockViewListener listener = new MockViewListener();
    m_view.addListener(listener);
    final CModule internalModule = new CModule(1, "", "", new Date(), new Date(), "00000000000000000000000000000000", "0000000000000000000000000000000000000000", 0, 0, new CAddress(0), new CAddress(0), null, null, Integer.MAX_VALUE, false, m_provider);
    final CFunction parentFunction = new CFunction(internalModule, new MockView(), new CAddress(0x123), "Mock Function", "Mock Function", "Mock Description", 0, 0, 0, 0, FunctionType.NORMAL, "", 0, null, null, null, m_provider);
    final Function function = new Function(module, parentFunction);
    final List<Instruction> instructions = Lists.newArrayList(new Instruction(new MockInstruction()));
    m_view.load();
    final CodeNode node = m_view.createCodeNode(function, instructions);
    assertEquals(node, m_view.getGraph().getNodes().get(0));
    assertEquals(listener.events, "addedNode;changedGraphType;");
    assertEquals(com.google.security.zynamics.binnavi.API.disassembly.GraphType.Flowgraph, m_view.getGraphType());
    m_view.removeListener(listener);
}


TestFilePath: kiegroup/appformer/uberfire-extensions/uberfire-wires/uberfire-wires-core/uberfire-wires-core-grids/src/test/java/org/uberfire/ext/wires/core/grids/client/model/impl/GridColumnIndexingTest.java
++++++ MethodDeclarationQualifiedSignature: org.uberfire.ext.wires.core.grids.client.model.impl.GridColumnIndexingTest.testMoveColumnToLeft()
@Test
public void testMoveColumnToLeft() {
    final GridData grid = new BaseGridData();
    final GridColumn<String> gc1 = new MockMergableGridColumn<String>("col1", 100);
    final GridColumn<String> gc2 = new MockMergableGridColumn<String>("col2", 100);
    final GridColumn<String> gc3 = new MockMergableGridColumn<String>("col3", 100);
    final GridColumn<String> gc4 = new MockMergableGridColumn<String>("col4", 100);
    grid.appendColumn(gc1); # input: gc1,
    grid.appendColumn(gc2);
    grid.appendColumn(gc3);
    grid.appendColumn(gc4);
    grid.moveColumnTo(1, gc4);
    final List<GridColumn<?>> columns = grid.getColumns(); # output: columns
    assertEquals(4, columns.size());
    assertEquals(0, gc1.getIndex());
    assertEquals(1, gc2.getIndex());
    assertEquals(2, gc3.getIndex());
    assertEquals(3, gc4.getIndex());
    assertEquals(columns.get(0), gc1); # intput and output
    assertEquals(columns.get(1), gc4);
    assertEquals(columns.get(2), gc2);
    assertEquals(columns.get(3), gc3);
}


TestFilePath: apache/rya/extras/indexing/src/test/java/org/apache/rya/indexing/accumulo/temporal/TemporalIntervalTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.rya.indexing.accumulo.temporal.TemporalIntervalTest.hashTest()
@Test
public void hashTest() throws Exception {
    // Use MAX to see how it handles overflowing values.  Should silently go negative.
    int hashcode01Same = (new TemporalInterval(new TemporalInstantRfc3339(new DateTime(Integer.MAX_VALUE / 2)), new TemporalInstantRfc3339(new DateTime(Integer.MAX_VALUE)))).hashCode(); # hashcode -> 01
    int hashcode02Same = (new TemporalInterval(new TemporalInstantRfc3339(new DateTime(Integer.MAX_VALUE / 2)), new TemporalInstantRfc3339(new DateTime(Integer.MAX_VALUE)))).hashCode(); # hashcode -> 02
    int hashcode03Diff = (new TemporalInterval(new TemporalInstantRfc3339(new DateTime(Integer.MAX_VALUE / 2)), new TemporalInstantRfc3339(new DateTime(Integer.MAX_VALUE)))).hashCode();
    int hashcode04Diff = (new TemporalInterval(new TemporalInstantRfc3339(new DateTime(Integer.MIN_VALUE)), new TemporalInstantRfc3339(new DateTime(Integer.MIN_VALUE)))).hashCode();
    int hashcode05Diff = (new TemporalInterval(new TemporalInstantRfc3339(new DateTime(Integer.MAX_VALUE)), new TemporalInstantRfc3339(new DateTime(Integer.MAX_VALUE)))).hashCode();
    int hashcode06Diff = (new TemporalInterval(new TemporalInstantRfc3339(new DateTime(0)), new TemporalInstantRfc3339(new DateTime(0)))).hashCode(); 
    int hashcode07Diff = (new TemporalInterval(new TemporalInstantRfc3339(new DateTime(1000)), new TemporalInstantRfc3339(new DateTime(1000)))).hashCode(); 
    int hashcode08Diff = (new TemporalInterval(new TemporalInstantRfc3339(new DateTime(0)), new TemporalInstantRfc3339(new DateTime(1000)))).hashCode();
    int hashcode09Diff = (new TemporalInterval((TemporalInstantRfc3339.getMinimumInstance()), (TemporalInstantRfc3339.getMaximumInstance()))).hashCode();
    int hashcode10Diff = (new TemporalInterval(new TemporalInstantRfc3339(new DateTime(0)), (TemporalInstantRfc3339.getMaximumInstance()))).hashCode();
    Assert.assertEquals("Same input should produce same hashcode. (always!)", hashcode01Same, hashcode02Same); # outputs 
    Assert.assertTrue("Different small input should produce different hashcode. (usually!) hashcodes:" + hashcode03Diff + " " + hashcode04Diff + " " + hashcode03Diff + " " + hashcode05Diff, hashcode03Diff != hashcode04Diff && hashcode03Diff != hashcode05Diff);
    Assert.assertTrue("Different large input should produce different hashcode. (usually!) hashcodes:" + hashcode06Diff + " " + hashcode07Diff + " " + hashcode06Diff + " " + hashcode08Diff + " key for date 0= " + (new TemporalInstantRfc3339(new DateTime(0))).getAsKeyString() + " key for date 1000= " + (new TemporalInstantRfc3339(new DateTime(1000))).getAsKeyString(), hashcode06Diff != hashcode07Diff && hashcode06Diff != hashcode08Diff); # 
    Assert.assertTrue("Different max and min input should produce different hashcode. (usually!) hashcodes:" + hashcode09Diff + " != " + hashcode10Diff + "fyi: key for date max= " + (TemporalInstantRfc3339.getMaximumInstance()).getAsKeyString() + " key for date min= " + (TemporalInstantRfc3339.getMinimumInstance()).getAsKeyString(), hashcode09Diff != hashcode10Diff);
}


TestFilePath: gridgain/gridgain/modules/indexing/src/test/java/org/apache/ignite/internal/processors/cache/persistence/db/wal/IgniteWalRecoveryTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.ignite.internal.processors.cache.persistence.db.wal.IgniteWalRecoveryTest.testWalRenameDirSimple()
/**
 * @throws Exception If fail.
 */
@Test
public void testWalRenameDirSimple() throws Exception {
    IgniteEx ignite = startGrid(1);
    ignite.cluster().active(true);
    IgniteCache<Object, Object> cache = ignite.cache(CACHE_NAME);
    for (int i = 0; i < 100; i++) cache.put(i, new IndexedObject(i));
    final Object consistentId = ignite.cluster().localNode().consistentId();
    stopGrid(1);
    final File cacheDir = cacheDir(CACHE_NAME, consistentId.toString());
    renamed = cacheDir.renameTo(new File(cacheDir.getParent(), "cache-" + RENAMED_CACHE_NAME));
    assert renamed;
    ignite = startGrid(1);
    ignite.cluster().active(true);
    cache = ignite.cache(RENAMED_CACHE_NAME);
    for (int i = 0; i < 100; i++) assertEquals(new IndexedObject(i), cache.get(i));
}


TestFilePath: ChiselsAndBits/Chisels-and-Bits/common/src/test/java/mod/chiselsandbits/utils/BitWidthMaskTest.java
++++++ MethodDeclarationQualifiedSignature: mod.chiselsandbits.utils.BitWidthMaskTest.getMaskedBitWidth()
@Test
public void getMaskedBitWidth() {
    final int mask = BitUtils.getBitMask(testWidth);
    final int resultingWidth = BitUtils.getMaskWidth(mask);
    Assert.assertEquals("The masked bit width should be the same as the bit width", testWidth, resultingWidth);
}


TestFilePath: ApsaraDB/galaxysql/polardbx-calcite/src/test/java/org/apache/calcite/util/PermutationTestCase.java
++++++ MethodDeclarationQualifiedSignature: org.apache.calcite.util.PermutationTestCase.testEmpty()
@Test
public void testEmpty() {
    final Permutation perm = new Permutation(0);
    assertTrue(perm.isIdentity());
    assertEquals("[]", perm.toString());
    assertTrue(perm.equals(perm));
    assertTrue(perm.equals(perm.inverse()));
    try {
        perm.set(1, 0);
        fail("expected exception");
    } catch (ArrayIndexOutOfBoundsException e) {
        // success
    }
    try {
        perm.set(-1, 2);
        fail("expected exception");
    } catch (ArrayIndexOutOfBoundsException e) {
        // success
    }
}


TestFilePath: briar/briar/bramble-core/src/test/java/org/briarproject/bramble/crypto/KeyAgreementTest.java
++++++ MethodDeclarationQualifiedSignature: org.briarproject.bramble.crypto.KeyAgreementTest.testDerivesStaticEphemeralSharedSecret()
@Test
public void testDerivesStaticEphemeralSharedSecret() throws Exception {
    String label = getRandomString(123);
    KeyPair aStatic = crypto.generateAgreementKeyPair();
    KeyPair aEphemeral = crypto.generateAgreementKeyPair();
    KeyPair bStatic = crypto.generateAgreementKeyPair();
    KeyPair bEphemeral = crypto.generateAgreementKeyPair();
    SecretKey aShared = crypto.deriveSharedSecret(label, bStatic.getPublic(), bEphemeral.getPublic(), aStatic, aEphemeral, true, inputs); # output1
    SecretKey bShared = crypto.deriveSharedSecret(label, aStatic.getPublic(), aEphemeral.getPublic(), bStatic, bEphemeral, false, inputs); # output2
    assertArrayEquals(aShared.getBytes(), bShared.getBytes());
}


TestFilePath: swimos/swim/swim-java/swim-runtime/swim-core/swim.structure/src/test/java/swim/structure/RecordMapViewMutableSpec.java
++++++ MethodDeclarationQualifiedSignature: swim.structure.RecordMapViewMutableSpec.testAliasedViewPut()
@Test
public void testAliasedViewPut() {
    final Record xs = Record.of().attr("k", "v").slot("a", "b").slot("c", "d");
    final Record ys = xs.branch();
    final Record yss = ys.subList(1, 3);
    yss.put("a", "B");
    assertTrue(xs.isAliased());
    assertFalse(ys.isAliased());
    assertEquals(xs.size(), 3);
    assertEquals(ys.size(), 3);
    assertEquals(yss.size(), 2);
    assertEquals(xs, Record.of().attr("k", "v").slot("a", "b").slot("c", "d"));
    assertEquals(ys, Record.of().attr("k", "v").slot("a", "B").slot("c", "d"));
    assertEquals(yss, Record.of().slot("a", "B").slot("c", "d"));
}


TestFilePath: zavtech/morpheus-core/src/test/java/com/zavtech/morpheus/range/RangeFilterTests.java
++++++ MethodDeclarationQualifiedSignature: com.zavtech.morpheus.range.RangeFilterTests.testRangeOfZonedDateTimes(java.time.ZonedDateTime, java.time.ZonedDateTime, java.time.Duration, boolean)
@Test(dataProvider = "ZonedDateTimeRanges")
public void testRangeOfZonedDateTimes(ZonedDateTime start, ZonedDateTime end, Duration step, boolean parallel) {
    final boolean ascend = start.isBefore(end);
    final Range<ZonedDateTime> range = Range.of(start, end, step, v -> v.getHour() == 6);
    final Array<ZonedDateTime> array = range.toArray(parallel);
    final ZonedDateTime first = array.first(v -> true).map(ArrayValue::getValue).get();
    final ZonedDateTime last = array.last(v -> true).map(ArrayValue::getValue).get();
    Assert.assertEquals(array.typeCode(), ArrayType.ZONED_DATETIME);
    Assert.assertTrue(!array.style().isSparse());
    Assert.assertEquals(range.start(), start, "The range start");
    Assert.assertEquals(range.end(), end, "The range end");
    int index = 0;
    ZonedDateTime value = first;
    while (ascend ? value.isBefore(last) : value.isAfter(last)) {
        final ZonedDateTime actual = array.getValue(index);
        Assert.assertEquals(actual, value, "Value matches at " + index);
        Assert.assertTrue(ascend ? actual.compareTo(start) >= 0 && actual.isBefore(end) : actual.compareTo(start) <= 0 && actual.isAfter(end), "Value in bounds at " + index);
        value = ascend ? value.plus(step) : value.minus(step);
        while (value.getHour() == 6) value = ascend ? value.plus(step) : value.minus(step);
        index++;
    }
}


TestFilePath: umple/umple/cruise.umple/test/cruise/umple/implementation/OneToManyUnidirectionalTest.java
++++++ MethodDeclarationQualifiedSignature: cruise.umple.implementation.OneToManyUnidirectionalTest.addToNewMentor()
@Test
public void addToNewMentor() {
    Mentor m = new Mentor();
    Mentor m2 = new Mentor();
    Student s = m.addStudent("123");
    m2.addStudent(s);
    Assert.assertEquals(0, m.numberOfStudents());
    Assert.assertEquals(1, m2.numberOfStudents());
    Assert.assertEquals(s, m2.getStudent(0));
}


TestFilePath: eclipse/eclipse-collections/unit-tests/src/test/java/org/eclipse/collections/impl/factory/ListsTest.java
++++++ MethodDeclarationQualifiedSignature: org.eclipse.collections.impl.factory.ListsTest.withAllSortedImmutableWithComparator()
@Test
public void withAllSortedImmutableWithComparator() {
    Assert.assertEquals(Lists.immutable.of(100, 50, 5, 1), Lists.immutable.withAllSorted(Comparators.reverseNaturalOrder(), Lists.mutable.of(50, 5, 100, 1)));
}


TestFilePath: apache/datasketches-java/src/test/java/org/apache/datasketches/kll/KllDirectFloatsSketchTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.datasketches.kll.KllDirectFloatsSketchTest.serializeDeserializeFullViaUpdatableWritableWrap()
@Test
public void serializeDeserializeFullViaUpdatableWritableWrap() {
    final KllFloatsSketch sketch1 = getDFSketch(200, 0);
    final int n = 1000;
    for (int i = 0; i < n; i++) {
        sketch1.update(i);
    }
    final byte[] bytes = KllHelper.toUpdatableByteArrayImpl(sketch1);
    final KllFloatsSketch sketch2 = KllFloatsSketch.writableWrap(WritableMemory.writableWrap(bytes), memReqSvr);
    assertEquals(bytes.length, sketch1.getCurrentUpdatableSerializedSizeBytes());
    assertFalse(sketch2.isEmpty());
    assertEquals(sketch2.getNumRetained(), sketch1.getNumRetained());
    assertEquals(sketch2.getN(), sketch1.getN());
    assertEquals(sketch2.getNormalizedRankError(false), sketch1.getNormalizedRankError(false));
    assertEquals(sketch2.getMinValue(), sketch1.getMinValue());
    assertEquals(sketch2.getMaxValue(), sketch1.getMaxValue());
    assertEquals(sketch2.getCurrentCompactSerializedSizeBytes(), sketch1.getCurrentCompactSerializedSizeBytes());
}


TestFilePath: eclipse/eclipse-collections/unit-tests/src/test/java/org/eclipse/collections/impl/bag/sorted/immutable/ImmutableEmptySortedBagTest.java
++++++ MethodDeclarationQualifiedSignature: org.eclipse.collections.impl.bag.sorted.immutable.ImmutableEmptySortedBagTest.reject()
@Override
@Test
public void reject() {
    Assert.assertEquals(this.classUnderTest(), this.classUnderTest().reject(each -> each % 2 == 0));
    Assert.assertEquals(this.classUnderTest(Comparators.reverseNaturalOrder()), this.classUnderTest(Comparators.reverseNaturalOrder()).reject(each -> each % 2 == 0)); 
}


TestFilePath: synthetichealth/synthea/src/test/java/org/mitre/synthea/helpers/PhysiologyValueGeneratorTest.java
++++++ MethodDeclarationQualifiedSignature: org.mitre.synthea.helpers.PhysiologyValueGeneratorTest.testValueGenerator()
@Test
public void testValueGenerator() {
    Person person = new Person(0);
    // Person born on July 25, 1988
    person.attributes.put(Person.BIRTHDATE, dateToSimTime("1988-07-25"));
    // Set their BMI to a normal 22 kg/m^2
    person.setVitalSign(VitalSign.BMI, 22.0);
    PhysiologyGeneratorConfig config = PhysiologyValueGenerator.getConfig("circulation_hemodynamics.yml");
    // Don't use pre generators so the model will run initially
    config.setUsePreGenerators(false);
    // Set the input variance threshold for R_sys to 0.1 so a small change will not
    // cause a re-run of the simulation
    for (IoMapper mapper : config.getInputs()) {
        if (mapper.getTo() == "R_sys") {
            mapper.setVariance(0.1);
        }
    }
    // Set our current simulation time
    long simTime = dateToSimTime("2019-09-19");
    SimRunner runner = new SimRunner(config, person);
    // Get the generator for systolic BP
    PhysiologyValueGenerator generator = new PhysiologyValueGenerator(config, runner, VitalSign.SYSTOLIC_BLOOD_PRESSURE, person, 0.0);
    double sys1 = generator.getValue(simTime);
    // Make sure it's a reasonable value
    assertTrue("sys1 is a reasonable systolic bp value", (100 < sys1) && (sys1 < 160));
    // Change the time by just a few seconds. The simulation should not run again,
    // which means the output value will be exactly the same.
    simTime += 30000;
    double sys2 = generator.getValue(simTime);
    assertEquals(sys1, sys2, 0);
    // Change the time by several years. Now the simulation should definitely run
    // again, which will change the output value.
    simTime = dateToSimTime("2070-09-19");
    double sys3 = generator.getValue(simTime);
    // Make sure it's a reasonable value
    assertTrue("sys3 is a reasonable systolic bp value", (100 < sys1) && (sys1 < 160));
    assertNotEquals(sys1, sys3);
    // Change the output variance to a small amount and verify that the result is no longer
    // exactly the same but within the variance amount.
    generator.setOutputVariance(1.0);
    double sys4 = generator.getValue(simTime);
    assertEquals(sys3, sys4, 1.0);
    assertNotEquals(sys3, sys4);
}


TestFilePath: pkilller/super-jadx/jadx-gui/src/test/java/jadx/gui/utils/JumpManagerTest.java
++++++ MethodDeclarationQualifiedSignature: jadx.gui.utils.JumpManagerTest.testNavigation2()
@Test
public void testNavigation2() {
    JumpPosition pos1 = makeJumpPos();
    jm.addPosition(pos1);
    // 1@
    JumpPosition pos2 = makeJumpPos();
    jm.addPosition(pos2);
    // 1 - 2@
    JumpPosition pos3 = makeJumpPos();
    jm.addPosition(pos3);
    // 1 - 2 - 3@
    JumpPosition pos4 = makeJumpPos();
    jm.addPosition(pos4);
    // 1 - 2 - 3 - 4@
    assertThat(jm.getPrev(), sameInstance(pos3));
    // 1 - 2 - 3@ - 4
    assertThat(jm.getPrev(), sameInstance(pos2));
    // 1 - 2@ - 3 - 4
    JumpPosition pos5 = makeJumpPos();
    jm.addPosition(pos5);
    // 1 - 2 - 5@
    assertThat(jm.getNext(), nullValue());
    assertThat(jm.getNext(), nullValue());
    assertThat(jm.getPrev(), sameInstance(pos2));
    // 1 - 2@ - 5
    assertThat(jm.getPrev(), sameInstance(pos1));
    // 1@ - 2 - 5
    assertThat(jm.getPrev(), nullValue());
    assertThat(jm.getNext(), sameInstance(pos2));
    // 1 - 2@ - 5
    assertThat(jm.getNext(), sameInstance(pos5));
    // 1 - 2 - 5@
    assertThat(jm.getNext(), nullValue());
}


TestFilePath: apache/datasketches-java/src/test/java/org/apache/datasketches/tuple/adouble/AdoubleTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.datasketches.tuple.adouble.AdoubleTest.estimationModeWithSamplingNoResizing()
@Test
public void estimationModeWithSamplingNoResizing() {
    final UpdatableSketch<Double, DoubleSummary> sketch = new UpdatableSketchBuilder<>(new DoubleSummaryFactory(mode)).setSamplingProbability(0.5f).setResizeFactor(ResizeFactor.X1).build();
    for (int i = 0; i < 16384; i++) {
        sketch.update(i, 1.0);
    }
    Assert.assertTrue(sketch.isEstimationMode());
    Assert.assertEquals(sketch.getEstimate(), 16384, 16384 * 0.01);
    Assert.assertTrue(sketch.getEstimate() >= sketch.getLowerBound(1));
    Assert.assertTrue(sketch.getEstimate() < sketch.getUpperBound(1));
}


TestFilePath: apache/ignite/modules/core/src/test/java/org/apache/ignite/marshaller/GridMarshallerMappingConsistencyTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.ignite.marshaller.GridMarshallerMappingConsistencyTest.testMappingsPersistedOnJoin()
/**
 * Make a value be rebalanced to a node after the mapping was accepted.
 * Check, that the mapping is available after restart.
 *
 * @throws Exception If failed.
 */
@Test
public void testMappingsPersistedOnJoin() throws Exception {
    Ignite g1 = startGrid(1);
    Ignite g2 = startGrid(2);
    g1.cluster().active(true);
    CacheConfiguration<Integer, DummyObject> cacheCfg = new CacheConfiguration<>(CACHE_NAME);
    cacheCfg.setBackups(1);
    IgniteCache<Integer, DummyObject> c1 = g1.getOrCreateCache(cacheCfg);
    IgniteCache<Integer, DummyObject> c2 = g2.getOrCreateCache(cacheCfg);
    int k = primaryKey(c2);
    stopGrid(2);
    c1.put(k, new DummyObject(k));
    startGrid(2);
    awaitPartitionMapExchange();
    stopAllGrids();
    g2 = startGrid(2);
    g2.cluster().active(true);
    c2 = g2.cache(CACHE_NAME);
    assertEquals(k, c2.get(k).val);
}


TestFilePath: opencast/opencast/modules/smil-impl/src/test/java/org/opencastproject/smil/impl/SmilServiceImplTest.java
++++++ MethodDeclarationQualifiedSignature: org.opencastproject.smil.impl.SmilServiceImplTest.testAddParallel()
/**
 * Test of addParallel methods, of class SmilServiceImpl.
 */
@Test
public void testAddParallel() throws Exception {
    SmilResponse smilResponse = smilService.createNewSmil();
    smilResponse = smilService.addParallel(smilResponse.getSmil());
    assertNotNull(smilResponse.getSmil().getBody().getMediaElements().get(0));
    assertEquals(smilResponse.getSmil().getBody().getMediaElements().get(0), smilResponse.getEntity());
    assertTrue(smilResponse.getSmil().getBody().getMediaElements().get(0) instanceof SmilMediaParallelImpl);
    SmilMediaContainer par = (SmilMediaContainer) smilResponse.getEntity();
    assertTrue(par.isContainer());
    assertSame(SmilMediaContainer.ContainerType.PAR, par.getContainerType());
    smilResponse = smilService.addParallel(smilResponse.getSmil(), smilResponse.getEntity().getId());
    assertNotNull(smilResponse.getSmil().getBody().getMediaElements().get(0));
    assertTrue(smilResponse.getSmil().getBody().getMediaElements().get(0) instanceof SmilMediaContainer);
    SmilMediaContainer parent = (SmilMediaContainer) smilResponse.getSmil().getBody().getMediaElements().get(0);
    assertNotNull(parent.getElements().get(0));
    assertTrue(parent.getElements().get(0) instanceof SmilMediaParallelImpl);
    assertEquals(parent.getElements().get(0).getId(), smilResponse.getEntity().getId());
}


TestFilePath: apolloconfig/apollo/apollo-portal/src/test/java/com/ctrip/framework/apollo/portal/service/AppNamespaceServiceTest.java
++++++ MethodDeclarationQualifiedSignature: com.ctrip.framework.apollo.portal.service.AppNamespaceServiceTest.testCreatePrivateAppNamespaceExistedInAnotherAppId()
@Test
@Sql(scripts = "/sql/appnamespaceservice/init-appnamespace.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public void testCreatePrivateAppNamespaceExistedInAnotherAppId() {
    AppNamespace appNamespace = assembleBaseAppNamespace();
    appNamespace.setPublic(false);
    appNamespace.setName("datasource");
    appNamespace.setAppId("song0711-01");
    appNamespaceService.createAppNamespaceInLocal(appNamespace);
    AppNamespace createdAppNamespace = appNamespaceService.findByAppIdAndName(appNamespace.getAppId(), appNamespace.getName());
    Assert.assertNotNull(createdAppNamespace);
    Assert.assertEquals(appNamespace.getName(), createdAppNamespace.getName());
}


TestFilePath: apache/rya/extras/rya.pcj.fluo/pcj.fluo.app/src/test/java/org/apache/rya/indexing/pcj/fluo/app/ConstructGraphTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.rya.indexing.pcj.fluo.app.ConstructGraphTest.testConstructGraphSerializerBlankNode()
@Test
public void testConstructGraphSerializerBlankNode() throws MalformedQueryException {
    String query = "select ?x where { _:b <uri:talksTo> ?x. _:b <uri:worksAt> ?y }";
    SPARQLParser parser = new SPARQLParser();
    ParsedQuery pq = parser.parseQuery(query, null);
    List<StatementPattern> patterns = StatementPatternCollector.process(pq.getTupleExpr());
    ConstructGraph graph = new ConstructGraph(patterns);
    String constructString = ConstructGraphSerializer.toConstructString(graph);
    ConstructGraph deserialized = ConstructGraphSerializer.toConstructGraph(constructString);
    assertEquals(graph, deserialized);
}


TestFilePath: eclipse/eclipse-collections/unit-tests/src/test/java/org/eclipse/collections/impl/utility/ListIterateTest.java
++++++ MethodDeclarationQualifiedSignature: org.eclipse.collections.impl.utility.ListIterateTest.removeIfWith()
@Test
public void removeIfWith() {
    MutableList<Integer> objects = FastList.newListWith(1, 2, 3, 4);
    ListIterate.removeIfWith(objects, Predicates2.lessThan(), 3);
    Assert.assertEquals(FastList.newListWith(3, 4), objects);
}


TestFilePath: vsch/flexmark-java/flexmark-util/src/test/java/com/vladsch/flexmark/util/collection/OrderedMultiMapTest.java
++++++ MethodDeclarationQualifiedSignature: com.vladsch.flexmark.util.collection.OrderedMultiMapTest.testRemoveReversedReversedIteration()
@Test
public void testRemoveReversedReversedIteration() throws Exception {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
    for (int i = 0; i < 10; i++) {
        Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
        Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
    }
    orderedMap.putAll(orderedMap);
    Assert.assertEquals(true, orderedMap.equals(orderedMap));
    Iterator<Map.Entry<String, Integer>> iterator = orderedMap.entrySet().reversedIterable().reversedIterator();
    int i = 0;
    while (iterator.hasNext()) {
        Map.Entry<String, Integer> it = iterator.next();
        Assert.assertEquals(String.valueOf(i), it.getKey());
        Assert.assertEquals(i, (int) it.getValue());
        i++;
    }
    iterator = orderedMap.entrySet().reversedIterable().reversedIterator();
    int j = 0;
    while (iterator.hasNext()) {
        Map.Entry<String, Integer> item = iterator.next();
        iterator.remove();
        Assert.assertEquals(j == 9 ? 0 : 10, orderedMap.keySet().getValueList().size());
        int lastJ = j + 1;
        for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
            Assert.assertEquals(String.valueOf(lastJ), it.getKey());
            Assert.assertEquals(lastJ, (int) it.getValue());
            lastJ++;
        }
        j++;
    }
}


TestFilePath: synthetichealth/synthea/src/test/java/org/mitre/synthea/engine/FixedRecordTest.java
++++++ MethodDeclarationQualifiedSignature: org.mitre.synthea.engine.FixedRecordTest.fixedDemographicsImportTest()
@Test
public void fixedDemographicsImportTest() {
    // List of raw RecordGroups imported directly from the input file for later comparison.
    List<FixedRecordGroup> rawRecordGroups = generator.importFixedPatientDemographicsFile();
    // Generate each patient from the fixed record input file.
    for (int i = 0; i < generator.options.population; i++) {
        generator.generatePerson(i);
    }
    // Make sure that the correct number of people were imported from the fixed records.
    assertEquals(4, generator.internalStore.size());
    assertEquals(generator.internalStore.size(), rawRecordGroups.size());
    // Check that each person has HealthRecords that match their fixed demographic records.
    for (int p = 0; p < generator.internalStore.size(); p++) {
        // Get the current person and pull their list of records.
        Person currentPerson = generator.internalStore.get(p);
        FixedRecordGroup recordGroup = (FixedRecordGroup) currentPerson.attributes.get(Person.RECORD_GROUP);
        // Make sure the person has the correct number of records.
        assertTrue(currentPerson.records.size() >= 3);
        assertTrue(recordGroup.records.size() == 3);
        // Track the number of fixed records that match the person's attributes exactly.
        int fixedRecordMatches = 0;
        // Cycle the person's FixedRecords to compare them to the raw imported FixedRecords.
        for (int r = 0; r < currentPerson.records.size(); r++) {
            int recordToPull = Math.min(r, recordGroup.count - 1);
            FixedRecord personFixedRecord = recordGroup.records.get(recordToPull);
            recordToPull = Math.min(r, rawRecordGroups.get(p).records.size() - 1);
            FixedRecord rawFixedRecord = rawRecordGroups.get(p).records.get(recordToPull);
            // Compare the person's current FixedRecord with the raw imported FixedRecords.
            assertEquals(personFixedRecord.firstName, rawFixedRecord.firstName);
            assertEquals(personFixedRecord.lastName, rawFixedRecord.lastName);
            assertEquals(personFixedRecord.getBirthDate(), rawFixedRecord.getBirthDate());
            assertEquals(personFixedRecord.gender, rawFixedRecord.gender);
            assertEquals(personFixedRecord.phoneAreaCode, rawFixedRecord.phoneAreaCode);
            assertEquals(personFixedRecord.phoneNumber, rawFixedRecord.phoneNumber);
            assertEquals(personFixedRecord.addressLineOne, rawFixedRecord.addressLineOne);
            assertEquals(personFixedRecord.addressLineTwo, rawFixedRecord.addressLineTwo);
            assertEquals(personFixedRecord.city, rawFixedRecord.city);
            assertEquals(personFixedRecord.zipcode, rawFixedRecord.zipcode);
            assertEquals(personFixedRecord.parentFirstName, rawFixedRecord.parentFirstName);
            assertEquals(personFixedRecord.parentLastName, rawFixedRecord.parentLastName);
            assertEquals(personFixedRecord.parentEmail, rawFixedRecord.parentEmail);
            // One FixedRecord should match the person's attributes exactly as the "gold standard".
            if ((currentPerson.attributes.get(Person.FIRST_NAME).equals(rawFixedRecord.firstName)) && (currentPerson.attributes.get(Person.LAST_NAME).equals(rawFixedRecord.lastName)) && (currentPerson.attributes.get(Person.ADDRESS).equals(rawFixedRecord.addressLineOne)) && (currentPerson.attributes.get(Person.BIRTHDATE).equals(rawFixedRecord.getBirthDate())) && (currentPerson.attributes.get(Person.GENDER).equals(rawFixedRecord.gender)) && (currentPerson.attributes.get(Person.TELECOM).equals(rawFixedRecord.getTelecom())) && (currentPerson.attributes.get(Person.STATE).equals(rawFixedRecord.state)) && (currentPerson.attributes.get(Person.CITY).equals(rawFixedRecord.city)) && (currentPerson.attributes.get(Person.ZIP).equals(rawFixedRecord.zipcode)) && (currentPerson.attributes.get(Person.IDENTIFIER_RECORD_ID).equals(rawFixedRecord.recordId)) && (currentPerson.attributes.get(Person.IDENTIFIER_SITE).equals(rawFixedRecord.site))) {
                fixedRecordMatches++;
            }
        }
        // One FixedRecord should match the person's attributes exactly as a "gold standard" record.
        assertTrue(fixedRecordMatches >= 1);
    }
}


TestFilePath: eclipse/californium/scandium-core/src/test/java/org/eclipse/californium/scandium/dtls/InMemoryConnectionStoreTest.java
++++++ MethodDeclarationQualifiedSignature: org.eclipse.californium.scandium.dtls.InMemoryConnectionStoreTest.testGetAddressRetrievesLocalConnection()
@Test
public void testGetAddressRetrievesLocalConnection() {
    // given a connection store containing a connection with a peer
    store.put(con);
    // when retrieving the connection for the given peer
    Connection connectionWithPeer = store.get(con.getPeerAddress());
    assertThat(connectionWithPeer, is(con));
}


TestFilePath: mapbox/mapbox-gl-native-android/MapboxGLAndroidSDKTestApp/src/androidTest/java/com/mapbox/mapboxsdk/testapp/style/HeatmapLayerTest.java
++++++ MethodDeclarationQualifiedSignature: com.mapbox.mapboxsdk.testapp.style.HeatmapLayerTest.testFilter()
@Test
@UiThreadTest
public void testFilter() {
    Timber.i("Filter");
    assertNotNull(layer);
    // Get initial
    assertEquals(layer.getFilter(), null);
    // Set
    Expression filter = eq(get("undefined"), literal(1.0));
    layer.setFilter(filter);
    assertEquals(layer.getFilter().toString(), filter.toString());
    // Set constant
    filter = literal(true);
    layer.setFilter(filter);
    assertEquals(layer.getFilter().toString(), filter.toString());
}


TestFilePath: apache/kylin/core-common/src/test/java/org/apache/kylin/common/persistence/ResourceToolTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.kylin.common.persistence.ResourceToolTest.testCopy()
@Test
public void testCopy() throws IOException {
    KylinConfig dstConfig = KylinConfig.createInstanceFromUri(dstPath);
    ResourceStore srcStore = ResourceStore.getStore(KylinConfig.getInstanceFromEnv());
    ResourceStore dstStore = ResourceStore.getStore(dstConfig);
    // metadata under source path and destination path are not equal before copy
    Assert.assertNotEquals(srcStore.listResources("/"), dstStore.listResources("/"));
    new ResourceTool().copy(KylinConfig.getInstanceFromEnv(), dstConfig, "/");
    // After copy, two paths have same metadata
    NavigableSet<String> dstFiles = dstStore.listResourcesRecursively("/"); # invocation1
    NavigableSet<String> srcFiles = srcStore.listResourcesRecursively("/"); # invocation2
    Assert.assertTrue(srcFiles.containsAll(EXEC_FILES));
    Assert.assertFalse(dstFiles.containsAll(EXEC_FILES));
    srcFiles.removeAll(EXEC_FILES);
    Assert.assertEquals(srcFiles, dstFiles); # two outputs
}


TestFilePath: eclipse/vorto/repository/repository-core/src/test/java/org/eclipse/vorto/repository/core/search/SearchUnitTest.java
++++++ MethodDeclarationQualifiedSignature: org.eclipse.vorto.repository.core.search.SearchUnitTest.testEmptyValueForTagDoesNotPreventParsingFollowingTags()
@Test
public void testEmptyValueForTagDoesNotPreventParsingFollowingTags() {
    String query = "name: author:mena";
    SearchParameters expected = new SearchParameters().withUntaggedName("name:*").withAuthor("mena");
    assertEquals(expected, SearchParameters.build(query));
}


TestFilePath: ProgrammerAnthony/ZuulC/zuul-core/src/test/java/com/netflix/zuul/message/http/HttpQueryParamsTest.java
++++++ MethodDeclarationQualifiedSignature: com.netflix.zuul.message.http.HttpQueryParamsTest.parseKeyWithoutValue()
@Test
public void parseKeyWithoutValue() {
    HttpQueryParams expected = new HttpQueryParams();
    expected.add("k1", "");
    HttpQueryParams actual = HttpQueryParams.parse("k1");
    assertEquals(expected, actual);
    assertEquals("k1", actual.toEncodedString());
}


TestFilePath: eclipse/eclipse-collections/unit-tests/src/test/java/org/eclipse/collections/impl/set/mutable/primitive/AbstractImmutableByteHashSetTestCase.java
++++++ MethodDeclarationQualifiedSignature: org.eclipse.collections.impl.set.mutable.primitive.AbstractImmutableByteHashSetTestCase.testEquals()
@Override
@Test
public void testEquals() {
    super.testEquals();
    ImmutableByteSet set1 = this.newWith((byte) 1, (byte) 31, (byte) 32);
    ImmutableByteSet set2 = this.newWith((byte) 32, (byte) 31, (byte) 1);
    ImmutableByteSet set3 = this.newWith((byte) 32, (byte) 32, (byte) 31, (byte) 1);
    ImmutableByteSet set4 = this.newWith((byte) 32, (byte) 32, (byte) 31, (byte) 1, (byte) 1);
    Verify.assertEqualsAndHashCode(set1, set2);
    Verify.assertEqualsAndHashCode(set1, set3);
    Verify.assertEqualsAndHashCode(set1, set4);
    Verify.assertEqualsAndHashCode(set2, set3);
    Verify.assertEqualsAndHashCode(set2, set4);
}


TestFilePath: ibinti/bugvm/Core/binder/src/test/java/com/bugvm/javacpp/PointerTest.java
++++++ MethodDeclarationQualifiedSignature: com.bugvm.javacpp.PointerTest.testIntPointer()
@Test
public void testIntPointer() {
    System.out.println("IntPointer");
    int intSize = Integer.SIZE / 8;
    assertEquals(intSize, Loader.sizeof(IntPointer.class));
    int[] array = new int[8192];
    IntPointer pointer = new IntPointer(array);
    assertEquals(array.length, pointer.limit());
    assertEquals(array.length, pointer.capacity());
    for (int i = 0; i < array.length; i++) {
        array[i] = i;
        pointer.put(i, i);
        assertEquals(array[i], pointer.get(i));
    }
    for (int i = 0; i < array.length; i++) {
        pointer.position(i).put(array[i]);
        assertEquals(array[i], pointer.position(i).get());
    }
    int[] array2 = new int[array.length];
    pointer.position(0).get(array2);
    assertArrayEquals(array, array2);
    IntBuffer buffer = pointer.asBuffer();
    assertTrue(buffer.compareTo(IntBuffer.wrap(array)) == 0);
    assertEquals(pointer.address(), new IntPointer(buffer).address());
    int offset = 42;
    pointer.put(array, offset, array.length - offset);
    pointer.get(array2, offset, array.length - offset);
    assertArrayEquals(array, array2);
    IntPointer pointer2 = new IntPointer(array.length).zero();
    pointer2.position(10).limit(30).fill(0xFF);
    pointer2.position(20).put(pointer.position(20).limit(30));
    pointer.position(0);
    pointer2.position(0);
    for (int i = 0; i < array.length; i++) {
        if (i < 10) {
            assertEquals(0, pointer2.get(i));
        } else if (i < 20) {
            assertEquals(0xFFFFFFFF, pointer2.get(i));
        } else if (i < 30) {
            assertEquals(pointer.get(i), pointer2.get(i));
        } else {
            assertEquals(0, pointer2.get(i));
        }
    }
    assertEquals(maxBytes, Pointer.maxBytes);
    int chunks = 10;
    IntPointer[] pointers = new IntPointer[chunks];
    long chunkSize = Pointer.maxBytes / intSize / chunks;
    for (int j = 0; j < chunks - 1; j++) {
        pointers[j] = new IntPointer(chunkSize);
    }
    assertTrue(Pointer.DeallocatorReference.totalBytes >= (chunks - 1) * chunkSize * intSize);
    try {
        fieldReference = pointers;
        new IntPointer(chunkSize);
        fail("OutOfMemoryError should have been thrown.");
    } catch (OutOfMemoryError e) {
        System.out.println(e);
        System.out.println(e.getCause());
    }
    for (int j = 0; j < chunks; j++) {
        pointers[j] = null;
    }
    // make sure garbage collection runs
    fieldReference = null;
    pointers[0] = new IntPointer(chunkSize);
    assertTrue(Pointer.DeallocatorReference.totalBytes < (chunks - 1) * chunkSize * intSize);
    assertTrue(Pointer.DeallocatorReference.totalBytes >= chunkSize * intSize);
    System.out.println(Pointer.DeallocatorReference.totalBytes + " " + chunkSize * intSize);
}


TestFilePath: hortonworks/streamline/streams/layout/src/test/java/com/hortonworks/streamline/streams/layout/component/TopologyDagTest.java
++++++ MethodDeclarationQualifiedSignature: com.hortonworks.streamline.streams.layout.component.TopologyDagTest.testAddEdge()
@Test
public void testAddEdge() throws Exception {
    Source src = new StreamlineSource(Collections.singleton(new Stream("f1", "f2")));
    Sink sink = new StreamlineSink();
    Processor processor = new StreamlineProcessor(Collections.singleton(new Stream("r1")));
    topology.add(src).add(processor).add(sink);
    topology.addEdge(src, processor);
    topology.addEdge(processor, sink);
    assertEquals(1, topology.getEdgesFrom(src).size());
    assertEquals(src, topology.getEdgesFrom(src).get(0).getFrom());
    assertEquals(processor, topology.getEdgesFrom(src).get(0).getTo());
    assertEquals(1, topology.getEdgesFrom(processor).size());
    assertEquals(processor, topology.getEdgesFrom(processor).get(0).getFrom());
    assertEquals(sink, topology.getEdgesFrom(processor).get(0).getTo());
    assertEquals(2, topology.getEdges(processor).size());
    assertEquals(0, topology.getEdgesTo(src).size());
    assertEquals(0, topology.getEdgesFrom(sink).size());
}


TestFilePath: deptofdefense/AndroidTacticalAssaultKit-CIV/atak/ATAK/app/src/androidTest/java/com/atakmap/map/layer/feature/FeatureDataSourceContentFactoryTests.java
++++++ MethodDeclarationQualifiedSignature: com.atakmap.map.layer.feature.FeatureDataSourceContentFactoryTests.FeatureDataSourceContentFactory_parse_supported_multiple_providers()
@Test
public void FeatureDataSourceContentFactory_parse_supported_multiple_providers() {
    final String prefix = UUID.randomUUID().toString();
    FeatureDataSource[] unsupported = new FeatureDataSource[5];
    for (int i = 0; i < unsupported.length; i++) {
        unsupported[i] = MockFeatureDataSource.createNullContent(prefix + "." + i, 1);
        FeatureDataSourceContentFactory.register(unsupported[i]);
    }
    final FeatureDataSource supported = MockFeatureDataSource.createEmptyContent(prefix + "." + unsupported.length, 1);
    FeatureDataSourceContentFactory.register(supported); # supported: ->  invocation1
    try {
        FeatureDataSource.Content content = null;
        try {
            final File file = new File("/dev/null");
            content = FeatureDataSourceContentFactory.parse(file, null); # invocation2 -> content
            Assert.assertNotNull(content);
            Assert.assertEquals(content.getType(), supported.getName()); # assertion
            Assert.assertEquals(content.getProvider(), supported.getName());
        } finally {
            if (content != null)
                content.close();
        }
    } finally {
        FeatureDataSourceContentFactory.unregister(supported);
        for (int i = 0; i < unsupported.length; i++) FeatureDataSourceContentFactory.unregister(unsupported[i]);
    }
}


TestFilePath: openlookeng/hetu-core/presto-spi/src/test/java/io/prestosql/spi/predicate/TestEquatableValueSet.java
++++++ MethodDeclarationQualifiedSignature: io.prestosql.spi.predicate.TestEquatableValueSet.testEntireSet()
@Test
public void testEntireSet() {
    EquatableValueSet equatables = EquatableValueSet.all(ID);
    assertEquals(equatables.getType(), ID);
    assertFalse(equatables.isNone());
    assertTrue(equatables.isAll());
    assertFalse(equatables.isSingleValue());
    assertFalse(equatables.isWhiteList());
    assertEquals(equatables.getValues().size(), 0);
    assertEquals(equatables.complement(), EquatableValueSet.none(ID));
    assertTrue(equatables.containsValue(0L));
    assertTrue(equatables.containsValue(1L));
}


TestFilePath: swimos/swim/swim-java/swim-runtime/swim-core/swim.math/src/test/java/swim/math/TensorArraySpec.java
++++++ MethodDeclarationQualifiedSignature: swim.math.TensorArraySpec.testAdd()
@Test
public void testAdd() {
    assertEquals(R2x2.add(R2x2.of(new R2Vector(2.0, 0.5), new R2Vector(4.0, -1.0)), R2x2.of(new R2Vector(4.0, 1.0), new R2Vector(8.0, -2.0))), R2x2.of(new R2Vector(6.0, 1.5), new R2Vector(12.0, -3.0)));
}


TestFilePath: mollyim/mollyim-android/app/src/androidTest/java/org/thoughtcrime/securesms/lock/PinHashing_hashPin_Test.java
++++++ MethodDeclarationQualifiedSignature: org.thoughtcrime.securesms.lock.PinHashing_hashPin_Test.argon2_hashed_pin_password_with_spaces_diacritics_and_non_arabic_numerals()
@Test
public void argon2_hashed_pin_password_with_spaces_diacritics_and_non_arabic_numerals() throws IOException {
    String pin = " Pass६örd ";
    byte[] backupId = Hex.fromStringCondensed("cba811749042b303a6a7efa5ccd160aea5e3ea243c8d2692bd13d515732f51a8");
    MasterKey masterKey = new MasterKey(Hex.fromStringCondensed("9571f3fde1e58588ba49bcf82be1b301ca3859a6f59076f79a8f47181ef952bf"));
    HashedPin hashedPin = PinHashing.hashPin(pin, () -> backupId);
    KbsData kbsData = hashedPin.createNewKbsData(masterKey);
    assertArrayEquals(hashedPin.getKbsAccessKey(), kbsData.getKbsAccessKey());
    assertArrayEquals(Hex.fromStringCondensed("ab645acdccc1652a48a34b2ac6926340ff35c03034013f68760f20013f028dd8"), kbsData.getKbsAccessKey());
    assertArrayEquals(Hex.fromStringCondensed("11c0ba1834db15e47c172f6c987c64bd4cfc69c6047dd67a022afeec0165a10943f204d5b8f37b3cb7bab21c6dfc39c8"), kbsData.getCipherText());
    assertEquals(masterKey, kbsData.getMasterKey());
    assertEquals("577939bccb2b6638c39222d5a97998a867c5e154e30b82cc120f2dd07a3de987", kbsData.getMasterKey().deriveRegistrationLock());
    String localPinHash = PinHashing.localPinHash(pin);
    assertTrue(PinHashing.verifyLocalPinHash(localPinHash, pin));
}


TestFilePath: zalando/nakadi/core-common/src/test/java/org/zalando/nakadi/repository/kafka/PartitionsCalculatorTest.java
++++++ MethodDeclarationQualifiedSignature: org.zalando.nakadi.repository.kafka.PartitionsCalculatorTest.ensureCorrectValuesReturnedForCentralCase()
@Test
public void ensureCorrectValuesReturnedForCentralCase() throws IOException {
    final PartitionsCalculator calculator = buildTest();
    final int testCaseCount = 100;
    for (int i = 0; i < testCaseCount; ++i) {
        final float mbsPerSecond = (100.f * i) / testCaseCount;
        final int countLower = calculator.getBestPartitionsCount(100, mbsPerSecond);
        final int countUpper = calculator.getBestPartitionsCount(1000, mbsPerSecond);
        final int countBetween = calculator.getBestPartitionsCount(550, mbsPerSecond);
        if (countLower > countUpper) {
            assertThat(countBetween, lessThanOrEqualTo(countLower));
            assertThat(countBetween, greaterThanOrEqualTo(countUpper));
        } else {
            assertThat(countBetween, lessThanOrEqualTo(countUpper));
            assertThat(countBetween, greaterThanOrEqualTo(countLower));
        }
    }
}


TestFilePath: openlookeng/hetu-core/presto-hive/src/test/java/io/prestosql/plugin/hive/TestFileSystemCache.java
++++++ MethodDeclarationQualifiedSignature: io.prestosql.plugin.hive.TestFileSystemCache.testFileSystemCache()
@Test
public void testFileSystemCache() throws IOException {
    ImpersonatingHdfsAuthentication auth = new ImpersonatingHdfsAuthentication(new SimpleHadoopAuthentication());
    HdfsEnvironment environment = new HdfsEnvironment(new HiveHdfsConfiguration(new HdfsConfigurationInitializer(new HiveConfig()), ImmutableSet.of()), new HiveConfig(), auth);
    FileSystem fs1 = getFileSystem(environment, "user");
    FileSystem fs2 = getFileSystem(environment, "user");
    assertSame(fs1, fs2);
    FileSystem fs3 = getFileSystem(environment, "other_user");
    assertNotSame(fs1, fs3);
    FileSystem fs4 = getFileSystem(environment, "other_user");
    assertSame(fs3, fs4);
}


TestFilePath: rsksmart/rskj/rskj-core/src/test/java/co/rsk/net/messages/MessageTest.java
++++++ MethodDeclarationQualifiedSignature: co.rsk.net.messages.MessageTest.encodeDecodeBlockHeadersResponseMessage()
@Test
public void encodeDecodeBlockHeadersResponseMessage() {
    List<BlockHeader> headers = new ArrayList<>();
    for (int k = 1; k <= 4; k++) headers.add(blockGenerator.getBlock(k).getHeader());
    BlockHeadersResponseMessage message = new BlockHeadersResponseMessage(100, headers);
    byte[] encoded = message.getEncoded();
    Assert.assertNotNull(encoded);
    Message result = Message.create(blockFactory, encoded);
    Assert.assertNotNull(result);
    Assert.assertArrayEquals(encoded, result.getEncoded());
    Assert.assertEquals(MessageType.BLOCK_HEADERS_RESPONSE_MESSAGE, result.getMessageType());
    BlockHeadersResponseMessage newmessage = (BlockHeadersResponseMessage) result;
    Assert.assertEquals(100, newmessage.getId());
    Assert.assertEquals(headers.size(), newmessage.getBlockHeaders().size());
    for (int k = 0; k < headers.size(); k++) {
        Assert.assertEquals(headers.get(k).getNumber(), newmessage.getBlockHeaders().get(k).getNumber());
        Assert.assertEquals(headers.get(k).getHash(), newmessage.getBlockHeaders().get(k).getHash());
        Assert.assertArrayEquals(headers.get(k).getFullEncoded(), newmessage.getBlockHeaders().get(k).getFullEncoded());
    }
}


TestFilePath: dhis2/dhis2-core/dhis-2/dhis-services/dhis-service-core/src/test/java/org/hisp/dhis/sms/config/GatewayAdministrationServiceTest.java
++++++ MethodDeclarationQualifiedSignature: org.hisp.dhis.sms.config.GatewayAdministrationServiceTest.testRemoveDefaultGateway()
@Test
void testRemoveDefaultGateway() {
    subject.addGateway(bulkConfig);
    subject.addGateway(clickatellConfig);
    String bulkId = getConfigByClassName(BULKSMS).getUid();
    String clickatelId = getConfigByClassName(CLICKATELL).getUid();
    assertNotNull(bulkId);
    assertNotNull(clickatelId);
    assertEquals(bulkConfig, subject.getDefaultGateway());
    assertTrue(subject.removeGatewayByUid(bulkId));
    assertGateways(1);
    assertGateway(BULKSMS, Assertions::assertNull);
    assertEquals(clickatellConfig, subject.getDefaultGateway());
}


TestFilePath: gridgain/gridgain/modules/core/src/test/java/org/apache/ignite/internal/processors/cache/GridCacheAbstractMetricsSelfTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.ignite.internal.processors.cache.GridCacheAbstractMetricsSelfTest.testPutsReads()
/**
 * @throws Exception If failed.
 */
@Test
public void testPutsReads() throws Exception {
    IgniteCache<Integer, Integer> cache0 = grid(0).cache(DEFAULT_CACHE_NAME);
    int keyCnt = keyCount();
    int expReads = 0;
    int expMisses = 0;
    // Put and get a few keys.
    for (int i = 0; i < keyCnt; i++) {
        // +1 put
        cache0.getAndPut(i, i);
        boolean isPrimary = affinity(cache0).isPrimary(grid(0).localNode(), i);
        expReads += expectedReadsPerPut(isPrimary);
        expMisses += expectedMissesPerPut(isPrimary);
        info("Puts: " + cache0.localMetrics().getCachePuts());
        for (int j = 0; j < gridCount(); j++) {
            IgniteCache<Integer, Integer> cache = grid(j).cache(DEFAULT_CACHE_NAME);
            int cacheWrites = (int) cache.localMetrics().getCachePuts();
            assertEquals("Wrong cache metrics [i=" + i + ", grid=" + j + ']', i + 1, cacheWrites);
        }
        // +1 read
        assertEquals("Wrong value for key: " + i, Integer.valueOf(i), cache0.get(i));
        expReads++;
    }
    // Check metrics for the whole cache.
    int puts = 0;
    int reads = 0;
    int hits = 0;
    int misses = 0;
    for (int i = 0; i < gridCount(); i++) {
        CacheMetrics m = grid(i).cache(DEFAULT_CACHE_NAME).localMetrics();
        puts += m.getCachePuts();
        reads += m.getCacheGets();
        hits += m.getCacheHits();
        misses += m.getCacheMisses();
    }
    info("Stats [reads=" + reads + ", hits=" + hits + ", misses=" + misses + ']');
    assertEquals(keyCnt * gridCount(), puts);
    assertEquals(expReads, reads);
    assertEquals(keyCnt, hits);
    assertEquals(expMisses, misses);
}


TestFilePath: deptofdefense/AndroidTacticalAssaultKit-CIV/atak/ATAK/app/src/androidTest/java/com/atakmap/map/layer/feature/AttributeSetTests.java
++++++ MethodDeclarationQualifiedSignature: com.atakmap.map.layer.feature.AttributeSetTests.AttributeSet_long_roundtrip()
@Test
public void AttributeSet_long_roundtrip() {
    final long i = new java.util.Random().nextLong();
    final String key = UUID.randomUUID().toString();
    AttributeSet attrs = new AttributeSet();
    attrs.setAttribute(key, i);
    Assert.assertEquals(attrs.getLongAttribute(key), i);
}


TestFilePath: rsksmart/rskj/rskj-core/src/test/java/co/rsk/trie/delete/SecureTrieKeyValueTest.java
++++++ MethodDeclarationQualifiedSignature: co.rsk.trie.delete.SecureTrieKeyValueTest.testRecursivelyDelete()
@Test
public void testRecursivelyDelete() {
    byte[] key0 = "0".getBytes();
    byte[] key1 = "1".getBytes();
    byte[] key2 = "112999".getBytes();
    byte[] key3 = "11200".getBytes();
    byte[] key4 = "1145".getBytes();
    byte[] msg0 = ByteUtil.toHexString(key0).getBytes();
    byte[] msg1 = ByteUtil.toHexString(key1).getBytes();
    byte[] msg2 = ByteUtil.toHexString(key2).getBytes();
    byte[] msg3 = ByteUtil.toHexString(key3).getBytes();
    byte[] msg4 = ByteUtil.toHexString(key4).getBytes();
    List<byte[]> keys = Arrays.asList(key0, key1, key2, key3, key4);
    List<byte[]> values = Arrays.asList(msg0, msg1, msg2, msg3, msg4);
    Trie trie = new Trie();
    trie = trie.put(keys.get(0), values.get(0));
    int trieSize = trie.trieSize();
    for (int i = 1; i < keys.size(); i++) {
        trie = trie.put(keys.get(i), values.get(i));
    }
    // Now check that all values are there
    for (int i = 0; i < keys.size(); i++) {
        Assert.assertArrayEquals(trie.get(keys.get(i)), values.get(i));
    }
    trie = trie.deleteRecursive(key1);
    // Now only key0 must remain
    for (int i = 1; i < keys.size(); i++) {
        Assert.assertNull(trie.get(keys.get(i)));
    }
    // Now check the tree size and make sure it's the original
    Assert.assertEquals(trieSize, trie.trieSize());
}


TestFilePath: amzn/ion-java/test/com/amazon/ion/impl/SymbolTableTest.java
++++++ MethodDeclarationQualifiedSignature: com.amazon.ion.impl.SymbolTableTest.testLocalSymbolTableAppendImportBoundary()
@Test
public void testLocalSymbolTableAppendImportBoundary() throws IOException {
    String text = LocalSymbolTablePrefix + "{" + "  symbols:[ \"s11\"]" + "}\n" + "1\n" + LocalSymbolTablePrefix + "{" + "  imports:" + ION_SYMBOL_TABLE + "," + "  symbols:[ \"s21\"]" + "}\n" + "null";
    IonDatagram datagram = loader().load(text);
    SymbolTable symbolTable = datagram.get(0).getSymbolTable();
    assertSame(symbolTable, datagram.get(1).getSymbolTable());
    symbolTable.intern("o1");
    symbolTable.intern("a1");
    // new symbols don't influence SIDs for existing symbols; they are appended
    checkSymbol("s11", systemMaxId() + 1, symbolTable);
    checkSymbol("s21", systemMaxId() + 2, symbolTable);
    checkSymbol("o1", systemMaxId() + 3, symbolTable);
    checkSymbol("a1", systemMaxId() + 4, symbolTable);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    IonWriter writer = IonBinaryWriterBuilder.standard().build(out);
    datagram.writeTo(writer);
    writer.close();
    IonDatagram roundtripped = loader().load(out.toByteArray());
    assertTrue(Equivalence.ionEquals(datagram, roundtripped));
}


TestFilePath: jfree/jfreechart/src/test/java/org/jfree/chart/renderer/xy/XYBoxAndWhiskerRendererTest.java
++++++ MethodDeclarationQualifiedSignature: org.jfree.chart.renderer.xy.XYBoxAndWhiskerRendererTest.testHashcode()
/**
 * Two objects that are equal are required to return the same hashCode.
 */
@Test
public void testHashcode() {
    XYBoxAndWhiskerRenderer r1 = new XYBoxAndWhiskerRenderer();
    XYBoxAndWhiskerRenderer r2 = new XYBoxAndWhiskerRenderer();
    assertEquals(r1, r2);
    int h1 = r1.hashCode();
    int h2 = r2.hashCode();
    assertEquals(h1, h2);
}


TestFilePath: jrfeng/snow/player/src/androidTest/java/snow/player/playlist/PlaylistTest.java
++++++ MethodDeclarationQualifiedSignature: snow.player.playlist.PlaylistTest.getAllMusicItem()
@Test
public void getAllMusicItem() {
    List<MusicItem> items = mPlaylist.getAllMusicItem();
    assertEquals(mPlaylist.size(), items.size());
    items.remove(0);
    assertEquals(mSize, mPlaylist.size());
    items.add(generateMusicItem(mSize));
    assertEquals(mSize, mPlaylist.size());
}


TestFilePath: terl/lazysodium-java/src/test/java/com/goterl/lazysodium/Ristretto255Test.java
++++++ MethodDeclarationQualifiedSignature: com.goterl.lazysodium.Ristretto255Test.scalarMultBase()
@Test
public void scalarMultBase() throws Exception {
    // Test vectors from https://ristretto.group/test_vectors/ristretto255.html
    String[] expected = new String[] { // This is the basepoint
    "e2f2ae0a6abc4e71a884a961c500515f58e30b6aa582dd8db6a65945e08d2d76", // These are small multiples of the basepoint
    "6a493210f7499cd17fecb510ae0cea23a110e8d5b901f8acadd3095c73a3b919", "94741f5d5d52755ece4f23f044ee27d5d1ea1e2bd196b462166b16152a9d0259", "da80862773358b466ffadfe0b3293ab3d9fd53c5ea6c955358f568322daf6a57", "e882b131016b52c1d3337080187cf768423efccbb517bb495ab812c4160ff44e", "f64746d3c92b13050ed8d80236a7f0007c3b3f962f5ba793d19a601ebb1df403", "44f53520926ec81fbd5a387845beb7df85a96a24ece18738bdcfa6a7822a176d", "903293d8f2287ebe10e2374dc1a53e0bc887e592699f02d077d5263cdd55601c", "02622ace8f7303a31cafc63f8fc48fdc16e1c8c8d234b2f0d6685282a9076031", "20706fd788b2720a1ed2a5dad4952b01f413bcf0e7564de8cdc816689e2db95f", "bce83f8ba5dd2fa572864c24ba1810f9522bc6004afe95877ac73241cafdab42", "e4549ee16b9aa03099ca208c67adafcafa4c3f3e4e5303de6026e3ca8ff84460", "aa52e000df2e16f55fb1032fc33bc42742dad6bd5a8fc0be0167436c5948501f", "46376b80f409b29dc2b5f6f0c52591990896e5716f41477cd30085ab7f10301e", "e0c418f7c8d9c4cdd7395b93ea124f3ad99021bb681dfc3302a9d99a2e53e64e" };
    RistrettoPoint base = RistrettoPoint.base(lazySodium);
    assertEquals(RistrettoPoint.fromHex(lazySodium, expected[0]), base);
    for (byte i = 1; i <= 15; ++i) {
        BigInteger n = BigInteger.valueOf(i);
        // scalar encoding is little-endian
        String nHex = String.format("%02x", i) + IntStream.range(0, 31).mapToObj(j -> "00").collect(Collectors.joining());
        byte[] nBytes = Ristretto255.scalarBuffer();
        nBytes[0] = i;
        RistrettoPoint res1 = lazySodium.cryptoScalarmultRistretto255Base(n);
        RistrettoPoint res2 = lazySodium.cryptoScalarmultRistretto255Base(nHex);
        RistrettoPoint res3 = lazySodium.cryptoScalarmultRistretto255Base(nBytes);
        RistrettoPoint res4 = base.times(n);
        RistrettoPoint expectedPoint = RistrettoPoint.fromHex(lazySodium, expected[i - 1]);
        assertEquals(expectedPoint, res1);
        assertEquals(res1, res2);
        assertEquals(res2, res3);
        assertEquals(res3, res4);
    }
}


TestFilePath: apache/ignite/modules/core/src/test/java/org/apache/ignite/internal/binary/BinaryMarshallerSelfTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.ignite.internal.binary.BinaryMarshallerSelfTest.testDuplicateFields()
/**
 * Test duplicate fields.
 *
 * @throws Exception If failed.
 */
@Test
public void testDuplicateFields() throws Exception {
    BinaryMarshaller marsh = binaryMarshaller();
    DuplicateFieldsB obj = new DuplicateFieldsB(1, 2);
    BinaryObjectImpl objBin = marshal(obj, marsh);
    String fieldName = "x";
    String fieldNameA = DuplicateFieldsA.class.getName() + "." + fieldName;
    String fieldNameB = DuplicateFieldsB.class.getName() + "." + fieldName;
    // Check "hasField".
    assert !objBin.hasField(fieldName);
    assert objBin.hasField(fieldNameA);
    assert objBin.hasField(fieldNameB);
    // Check direct field access.
    assertNull(objBin.field(fieldName));
    assertEquals(Integer.valueOf(1), objBin.field(fieldNameA));
    assertEquals(Integer.valueOf(2), objBin.field(fieldNameB));
    // Check metadata.
    BinaryType type = objBin.type();
    Collection<String> fieldNames = type.fieldNames();
    assertEquals(2, fieldNames.size());
    assert !fieldNames.contains(fieldName);
    assert fieldNames.contains(fieldNameA);
    assert fieldNames.contains(fieldNameB);
    // Check field access through type.
    BinaryField field = type.field(fieldName);
    BinaryField fieldA = type.field(fieldNameA);
    BinaryField fieldB = type.field(fieldNameB);
    assert !field.exists(objBin);
    assert fieldA.exists(objBin);
    assert fieldB.exists(objBin);
    assertNull(field.value(objBin));
    assertEquals(Integer.valueOf(1), fieldA.value(objBin));
    assertEquals(Integer.valueOf(2), fieldB.value(objBin));
    // Check object deserialization.
    DuplicateFieldsB deserialized = objBin.deserialize();
    assertEquals(obj.xA(), deserialized.xA());
    assertEquals(obj.xB(), deserialized.xB());
}


TestFilePath: apache/datasketches-java/src/test/java/org/apache/datasketches/tuple/adouble/FilterTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.datasketches.tuple.adouble.FilterTest.filteringInEstimationMode()
@Test
public void filteringInEstimationMode() {
    final UpdatableSketch<Double, DoubleSummary> sketch = new UpdatableSketchBuilder<>(new DoubleSummaryFactory(mode)).build();
    final int n = 10000;
    fillSketch(sketch, n, 0.0);
    fillSketch(sketch, 2 * n, 1.0);
    final Filter<DoubleSummary> filter = new Filter<>(o -> o.getValue() < 0.5);
    final Sketch<DoubleSummary> filteredSketch = filter.filter(sketch);
    Assert.assertEquals(filteredSketch.getEstimate(), n, n * 0.05);
    Assert.assertEquals(filteredSketch.getThetaLong(), sketch.getThetaLong());
    Assert.assertFalse(filteredSketch.isEmpty());
    Assert.assertTrue(filteredSketch.getLowerBound(1) <= filteredSketch.getEstimate());
    Assert.assertTrue(filteredSketch.getUpperBound(1) >= filteredSketch.getEstimate());
}

TestFilePath: espertechinc/esper/common/src/test/java/com/espertech/esper/common/client/json/minimaljson/JsonObject_Test.java
++++++ MethodDeclarationQualifiedSignature: com.espertech.esper.common.client.json.minimaljson.JsonObject_Test.hashCode_differsForDifferentObjects()
@Test
public void hashCode_differsForDifferentObjects() {
    assertFalse(object().hashCode() == object("a", "1").hashCode());
    assertFalse(object("a", "1").hashCode() == object("a", "2").hashCode());
    assertFalse(object("a", "1").hashCode() == object("b", "1").hashCode());
}


TestFilePath: mollyim/mollyim-android/libsignal/service/src/test/java/org/whispersystems/signalservice/api/payments/MoneyTest_MobileCoin_add.java
++++++ MethodDeclarationQualifiedSignature: org.whispersystems.signalservice.api.payments.MoneyTest_MobileCoin_add.add_1_lhs()
@Test
public void add_1_lhs() {
    Money mobileCoin1 = Money.mobileCoin(BigDecimal.ONE);
    Money mobileCoin2 = Money.mobileCoin(BigDecimal.ZERO);
    Money sum = mobileCoin1.add(mobileCoin2);
    assertEquals(Money.mobileCoin(BigDecimal.ONE), sum);
}


TestFilePath: gazbert/bxbot/bxbot-core/src/test/java/com/gazbert/bxbot/core/util/TestExchangeAdapterCreation.java
++++++ MethodDeclarationQualifiedSignature: com.gazbert.bxbot.core.util.TestExchangeAdapterCreation.testCreatingValidExchangeAdapter()
@Test
void testCreatingValidExchangeAdapter() {
    final TradingApi tradingApi = ConfigurableComponentFactory.createComponent(VALID_EXCHANGE_ADAPTER_IMPL);
    final ExchangeAdapter exchangeAdapter = ConfigurableComponentFactory.createComponent(VALID_EXCHANGE_ADAPTER_IMPL);
    assertNotNull(tradingApi);
    assertNotNull(exchangeAdapter);
    assertEquals(VALID_EXCHANGE_ADAPTER_IMPL, exchangeAdapter.getClass().getCanonicalName());
}


TestFilePath: jfree/jfreechart/src/test/java/org/jfree/chart/renderer/xy/XYLineAndShapeRendererTest.java
++++++ MethodDeclarationQualifiedSignature: org.jfree.chart.renderer.xy.XYLineAndShapeRendererTest.testHashcode()
/**
 * Two objects that are equal are required to return the same hashCode.
 */
@Test
public void testHashcode() {
    XYLineAndShapeRenderer r1 = new XYLineAndShapeRenderer();
    XYLineAndShapeRenderer r2 = new XYLineAndShapeRenderer();
    assertEquals(r1, r2);
    int h1 = r1.hashCode();
    int h2 = r2.hashCode();
    assertEquals(h1, h2);
}


TestFilePath: amzn/ion-java/test/com/amazon/ion/DatagramTest.java
++++++ MethodDeclarationQualifiedSignature: com.amazon.ion.DatagramTest.testRemoveAfterClone()
@Test
public void testRemoveAfterClone() throws IOException {
    IonDatagram copy = getClone();
    int beforeSize = copy.size();
    IonStruct struct = (IonStruct) copy.get(0);
    copy.remove(struct);
    assertEquals(beforeSize - 1, copy.size());
}


TestFilePath: trinodb/trino/core/trino-spi/src/test/java/io/trino/spi/TestHostAddress.java
++++++ MethodDeclarationQualifiedSignature: io.trino.spi.TestHostAddress.testEquality()
@Test
public void testEquality() {
    HostAddress address1 = HostAddress.fromParts("[1111:2222:3333:4444:5555:6666:7777:8888]", 1234);
    HostAddress address1NoBrackets = HostAddress.fromParts("1111:2222:3333:4444:5555:6666:7777:8888", 1234);
    assertEquals(address1, address1NoBrackets);
    HostAddress address1FromString = HostAddress.fromString("[1111:2222:3333:4444:5555:6666:7777:8888]:1234");
    assertEquals(address1, address1FromString);
    HostAddress address2 = HostAddress.fromParts("[1111:2222:3333:4444:5555:6666:7777:9999]", 1234);
    assertNotEquals(address1, address2);
    HostAddress address3 = HostAddress.fromParts("[1111:2222:3333:4444:5555:6666:7777:8888]", 1235);
    assertNotEquals(address1, address3);
}


TestFilePath: pinterest/terrapin/server/src/test/java/com/pinterest/terrapin/server/ResourcePartitionMapTest.java
++++++ MethodDeclarationQualifiedSignature: com.pinterest.terrapin.server.ResourcePartitionMapTest.removeReader()
@Test
public void removeReader() throws Exception {
    String resource = "resource1";
    TestReader reader1 = new TestReader();
    resourcePartitionMap.addReader(resource, "part1", reader1);
    TestReader reader2 = new TestReader();
    resourcePartitionMap.addReader(resource, "part2", reader2);
    // Remove reader1.
    assertEquals(reader1, resourcePartitionMap.removeReader(resource, "part1"));
    // Make sure we throw the correct exception if we try to access resource1, part1.
    testGetExceptionErrorCode(resource, "part1", TerrapinGetErrorCode.NOT_SERVING_PARTITION);
    // Remove a partition for which none of the resources exist. Check that we throw
    // UnsupportedOperationException.
    boolean gotUnsupportedOpException = false;
    try {
        resourcePartitionMap.removeReader(resource, "part3");
    } catch (UnsupportedOperationException e) {
        gotUnsupportedOpException = true;
    }
    assertTrue(gotUnsupportedOpException);
    // We should still have access to reader2.
    assertEquals(reader2, resourcePartitionMap.getReader(resource, "part2"));
    // Remove reader2.
    assertEquals(reader2, resourcePartitionMap.removeReader(resource, "part2"));
    // Make sure we throw the correct exception if we try to access resource1, part2.
    // We throw NOT_SERVING_RESOURCE since all partitions for the resource have been removed.
    testGetExceptionErrorCode(resource, "part2", TerrapinGetErrorCode.NOT_SERVING_RESOURCE);
}


TestFilePath: mwilliamson/java-mammoth/src/test/java/org/zwobble/mammoth/tests/html/HtmlCollapseTests.java
++++++ MethodDeclarationQualifiedSignature: org.zwobble.mammoth.tests.html.HtmlCollapseTests.elementsWithDifferentTagNamesAreNotCollapsed()
@Test
public void elementsWithDifferentTagNamesAreNotCollapsed() {
    assertThat(Html.collapse(list(Html.collapsibleElement("p", list(Html.text("One"))), Html.collapsibleElement("div", list(Html.text("Two"))))), deepEquals(list(Html.collapsibleElement("p", list(Html.text("One"))), Html.collapsibleElement("div", list(Html.text("Two"))))));
}


TestFilePath: yahoo/elide/elide-datastore/elide-datastore-aggregation/src/test/java/com/yahoo/elide/datastores/aggregation/timegrains/serde/TimeSerdeTest.java
++++++ MethodDeclarationQualifiedSignature: com.yahoo.elide.datastores.aggregation.timegrains.serde.TimeSerdeTest.testTimeDeserializeMonth()
@Test
public void testTimeDeserializeMonth() {
    LocalDateTime localDate = LocalDateTime.of(2020, java.time.Month.of(01), 01, 00, 00, 00);
    Time expectedDate = new Time(localDate, true, true, false, false, false, false, (unused) -> "");
    Serde serde = new Time.TimeSerde();
    Object actualDate = serde.deserialize(MONTH);
    assertEquals(expectedDate, actualDate);
    assertEquals(MONTH, serde.serialize(actualDate));
}


TestFilePath: dyn4j/dyn4j/src/test/java/org/dyn4j/world/AbstractPhysicsWorldTest.java
++++++ MethodDeclarationQualifiedSignature: org.dyn4j.world.AbstractPhysicsWorldTest.getJoinedBodies()
/**
 * Tests the getJoinedBodies method.
 */
@Test
public void getJoinedBodies() {
    TestWorld w = new TestWorld();
    Body b1 = new Body();
    Body b2 = new Body();
    w.addBody(b1);
    w.addBody(b2);
    List<Body> bodies = w.getJoinedBodies(b1);
    TestCase.assertNotNull(bodies);
    TestCase.assertTrue(bodies.isEmpty());
    Joint<Body> j = new DistanceJoint<Body>(b1, b2, new Vector2(), new Vector2());
    w.addJoint(j);
    bodies = w.getJoinedBodies(b1);
    TestCase.assertNotNull(bodies);
    TestCase.assertFalse(bodies.isEmpty());
    TestCase.assertSame(b2, bodies.get(0));
}


TestFilePath: ApsaraDB/galaxysql/polardbx-common/src/test/java/com/alibaba/polardbx/common/utils/time/TimeStorageTest.java
++++++ MethodDeclarationQualifiedSignature: com.alibaba.polardbx.common.utils.time.TimeStorageTest.testTime()
@Test
public void testTime() {
    IntStream.range(0, 1 << 20).mapToObj(i -> RandomTimeGenerator.generateDatetimeString(1)).map(l -> l.get(0)).forEach(s -> {
        MysqlDateTime t = StringTimeParser.parseTime(((String) s).getBytes());
        if (t == null) {
            return;
        }
        t.setMonth(0);
        t.setDay(0);
        long l = TimeStorage.writeTime(t);
        MysqlDateTime t1 = TimeStorage.readTime(l);
        Assert.assertEquals(t + " hour", t.getHour(), t1.getHour());
        Assert.assertEquals(t + " minute", t.getMinute(), t1.getMinute());
        Assert.assertEquals(t + " second", t.getSecond(), t1.getSecond());
        Assert.assertEquals(t + " nano", t.getSecondPart(), t1.getSecondPart());
    });
}


TestFilePath: nats-io/nats.java/src/test/java/io/nats/client/NKeyTests.java
++++++ MethodDeclarationQualifiedSignature: io.nats.client.NKeyTests.testPublicOnly()
@Test
public void testPublicOnly() throws Exception {
    NKey theKey = NKey.createUser(null);
    assertNotNull(theKey);
    char[] publicKey = theKey.getPublicKey();
    assertEquals(NKey.fromPublicKey(publicKey), NKey.fromPublicKey(publicKey));
    assertEquals(NKey.fromPublicKey(publicKey).hashCode(), NKey.fromPublicKey(publicKey).hashCode());
    NKey pubOnly = NKey.fromPublicKey(publicKey);
    // for coverage
    assertEquals(pubOnly, pubOnly);
    byte[] data = "Public and Private".getBytes(StandardCharsets.UTF_8);
    byte[] sig = theKey.sign(data);
    assertTrue(pubOnly.verify(data, sig));
    NKey otherKey = NKey.createServer(null);
    assertFalse(otherKey.verify(data, sig));
    assertNotEquals(otherKey, theKey);
    assertNotEquals(otherKey, pubOnly);
    assertNotEquals(pubOnly.getPublicKey()[0], '\0');
    pubOnly.clear();
    assertEquals(pubOnly.getPublicKey()[0], '\0');
}


TestFilePath: dyn4j/dyn4j/src/test/java/org/dyn4j/collision/broadphase/CollisionItemBroadphasePairTest.java
++++++ MethodDeclarationQualifiedSignature: org.dyn4j.collision.broadphase.CollisionItemBroadphasePairTest.copy()
/**
 * Tests the copy method.
 */
@Test
public void copy() {
    TestCollisionBody body1 = new TestCollisionBody();
    Fixture fixture1 = new Fixture(Geometry.createCircle(0.5));
    TestCollisionBody body2 = new TestCollisionBody();
    Fixture fixture2 = new Fixture(Geometry.createCircle(0.5));
    BroadphasePair<CollisionItem<TestCollisionBody, Fixture>> pair = new BroadphasePair<CollisionItem<TestCollisionBody, Fixture>>(new BroadphaseItem<TestCollisionBody, Fixture>(body1, fixture1), new BroadphaseItem<TestCollisionBody, Fixture>(body2, fixture2));
    BroadphasePair<CollisionItem<TestCollisionBody, Fixture>> copy = pair.copy();
    TestCase.assertEquals(body1, pair.getFirst().getBody());
    TestCase.assertEquals(fixture1, pair.getFirst().getFixture());
    TestCase.assertEquals(body2, pair.getSecond().getBody());
    TestCase.assertEquals(fixture2, pair.getSecond().getFixture());
    TestCase.assertEquals(pair.getFirst().getBody(), copy.getFirst().getBody());
    TestCase.assertEquals(pair.getFirst().getFixture(), copy.getFirst().getFixture());
    TestCase.assertEquals(pair.getSecond().getBody(), copy.getSecond().getBody());
    TestCase.assertEquals(pair.getSecond().getFixture(), copy.getSecond().getFixture());
    TestCase.assertEquals(pair.hashCode(), copy.hashCode());
    TestCase.assertFalse(pair == copy);
    TestCase.assertTrue(pair.equals(copy));
}


TestFilePath: signaflo/java-timeseries/math/src/test/java/com/github/signaflo/math/RealSpec.java
++++++ MethodDeclarationQualifiedSignature: com.github.signaflo.math.RealSpec.whenRealNumberSquaredResultCorrect()
@Test
public void whenRealNumberSquaredResultCorrect() {
    assertThat(a.squared(), is(Real.from(9.0)));
}


TestFilePath: openlookeng/hetu-core/hetu-heuristic-index/src/test/java/io/hetu/core/heuristicindex/TestHindexBitmapIndex.java
++++++ MethodDeclarationQualifiedSignature: io.hetu.core.heuristicindex.TestHindexBitmapIndex.testBitmapOperatorInputRows(java.lang.String)
@Test(dataProvider = "bitmapOperatorInputRowsTest")
public void testBitmapOperatorInputRows(String predicateQuery) throws Exception {
    System.out.println("Running testBitmapOperatorInputRows[predicateQuery: " + predicateQuery + "]");
    String tableName = getNewTableName();
    createTableBitmapSupportedDataTypes(tableName);
    String indexName = getNewIndexName();
    String tmpPredicateQuery = "SELECT * FROM " + tableName + " WHERE " + predicateQuery;
    assertQuerySucceeds("CREATE INDEX " + indexName + " USING bitmap ON " + tableName + " (p1)");
    MaterializedResult predicateQueryResultLoadingIndex = computeActual(tmpPredicateQuery);
    long predicateQueryInputRowsLoadingIndex = getInputRowsOfLastQueryExecution(tmpPredicateQuery);
    // Wait before continuing
    Thread.sleep(1000);
    MaterializedResult predicateQueryResultIndexLoaded = computeActual(tmpPredicateQuery);
    long predicateQueryInputRowsIndexLoaded = getInputRowsOfLastQueryExecution(tmpPredicateQuery);
    assertTrue(verifyEqualResults(predicateQueryResultLoadingIndex, predicateQueryResultIndexLoaded), "The results should be equal.");
    assertTrue(predicateQueryInputRowsLoadingIndex > predicateQueryInputRowsIndexLoaded || predicateQueryInputRowsIndexLoaded == 0, "Predicate query with index loaded should have the least input rows. " + "predicateQueryInputRowsLoadingIndex: " + predicateQueryInputRowsLoadingIndex + " predicateQueryInputRowsIndexLoaded: " + predicateQueryInputRowsIndexLoaded);
}

TestFilePath: dhis2/dhis2-core/dhis-2/dhis-api/src/test/java/org/hisp/dhis/common/QueryItemTest.java
++++++ MethodDeclarationQualifiedSignature: org.hisp.dhis.common.QueryItemTest.testGet()
@Test
void testGet() {
    QueryItem qiA = new QueryItem(deB, lsA, ValueType.TEXT, AggregationType.SUM, null);
    qiA.addFilter(new QueryFilter(QueryOperator.IN, "UIDA;UIDB"));
    qiA.setRepeatableStageParams(rspA);
    List<String> expected = Lists.newArrayList("UIDA", "UIDB");
    assertEquals(expected, qiA.getLegendSetFilterItemsOrAll());
    QueryItem qiB = new QueryItem(deB, lsA, ValueType.TEXT, AggregationType.SUM, null);
    expected = Lists.newArrayList("UIDA", "UIDB", "UIDC");
    assertEquals(expected, qiB.getLegendSetFilterItemsOrAll());
    assertEquals(rspA, qiA.getRepeatableStageParams());
    assertEquals(rspA.toString(), qiA.getRepeatableStageParams().toString());
}


TestFilePath: eclipse/eclipse-collections/unit-tests/src/test/java/org/eclipse/collections/impl/list/fixed/ArrayAdapterTest.java
++++++ MethodDeclarationQualifiedSignature: org.eclipse.collections.impl.list.fixed.ArrayAdapterTest.appendString()
@Override
@Test
public void appendString() {
    // ArrayAdapter doesn't support add and cannot contain itself
    StringBuilder stringBuilder = new StringBuilder();
    this.newWith(1, 2, 3, 4).appendString(stringBuilder);
    Assert.assertEquals(FastList.newList(this.newWith(1, 2, 3, 4)).makeString(), stringBuilder.toString());
}


TestFilePath: sofastack/sofa-rpc/core/api/src/test/java/com/alipay/sofa/rpc/context/RpcInternalContextTest.java
++++++ MethodDeclarationQualifiedSignature: com.alipay.sofa.rpc.context.RpcInternalContextTest.testPop()
@Test
public void testPop() {
    RpcInternalContext.pushContext();
    RpcInternalContext.popContext();
    RpcInternalContext current = RpcInternalContext.peekContext();
    Assert.assertNull(current);
    // 生成一个
    RpcInternalContext parentCtx = RpcInternalContext.getContext();
    Assert.assertNotNull(parentCtx);
    parentCtx.setRemoteAddress("127.0.0.1", 12200);
    Assert.assertEquals(RpcInternalContext.getContext(), parentCtx);
    Assert.assertEquals(RpcInternalContext.getContext().getRemoteAddress().toString(), "127.0.0.1:12200");
    // push进去后，当前为空
    RpcInternalContext.pushContext();
    current = RpcInternalContext.peekContext();
    Assert.assertNull(current);
    Assert.assertFalse(parentCtx.equals(RpcInternalContext.getContext()));
    Assert.assertNull(RpcInternalContext.getContext().getRemoteAddress());
    RpcInternalContext.removeContext();
    // 删掉后，当前为空
    current = RpcInternalContext.peekContext();
    Assert.assertNull(current);
    // pop一个出来
    RpcInternalContext.popContext();
    current = RpcInternalContext.getContext();
    Assert.assertNotNull(current);
    Assert.assertEquals(RpcInternalContext.getContext(), parentCtx);
    Assert.assertEquals(RpcInternalContext.getContext().getRemoteAddress().toString(), "127.0.0.1:12200");
}


TestFilePath: oracle/coherence/prj/test/unit/coherence-tests/src/test/java/com/tangosol/coherence/config/CacheMappingRegistryTest.java
++++++ MethodDeclarationQualifiedSignature: com.tangosol.coherence.config.CacheMappingRegistryTest.shouldAllowSchemaMappingOrCacheMappingRegistration()
@Test
public void shouldAllowSchemaMappingOrCacheMappingRegistration() throws Exception {
    SchemeMappingRegistry resourceMappingRegistry = new SchemeMappingRegistry();
    CacheMappingRegistry cacheMappingRegistry = new CacheMappingRegistry(resourceMappingRegistry);
    CacheMapping mapping1 = new CacheMapping("foo", "bar");
    TopicMapping mapping2 = new TopicMapping("foo", "DistributedScheme", PagedTopicScheme.class);
    TopicMapping mapping3 = new TopicMapping("bar", "DistributedScheme", PagedTopicScheme.class);
    CacheMapping mapping4 = new CacheMapping("bar", "barbar");
    CacheMapping mapping5 = new CacheMapping("foobar", "barbar");
    assertThat(resourceMappingRegistry.findMapping("foo", CacheMapping.class), nullValue());
    resourceMappingRegistry.register(mapping1);
    assertThat(resourceMappingRegistry.findMapping("foo", CacheMapping.class), is(mapping1));
    assertThat(resourceMappingRegistry.findMapping("foo", TopicMapping.class), nullValue());
    assertThat(cacheMappingRegistry.findCacheMapping("foo"), is(mapping1));
    resourceMappingRegistry.register(mapping2);
    assertThat(resourceMappingRegistry.findMapping("foo", CacheMapping.class), is(mapping1));
    assertThat(resourceMappingRegistry.findMapping("foo", TopicMapping.class), is(mapping2));
    assertThat(resourceMappingRegistry.findMapping("foo", ResourceMapping.class), is(mapping1));
    resourceMappingRegistry.register(mapping4);
    cacheMappingRegistry.register(mapping5);
    resourceMappingRegistry.register(mapping3);
    assertThat(resourceMappingRegistry.size(), is(5));
    Iterator<ResourceMapping> iter = resourceMappingRegistry.iterator();
    int cTopicMapping = 0;
    int cCacheMapping = 0;
    while (iter.hasNext()) {
        ResourceMapping resource = iter.next();
        if (resource instanceof TopicMapping) {
            cTopicMapping++;
        } else if (resource instanceof CacheMapping) {
            cCacheMapping++;
        }
    }
    assertThat(cTopicMapping, is(2));
    assertThat(cCacheMapping, is(3));
    assertThat(cacheMappingRegistry.findCacheMapping("foo"), is(mapping1));
    assertThat(cacheMappingRegistry.findCacheMapping("foobar"), is(mapping5));
    assertThat(cacheMappingRegistry.findCacheMapping("bar"), is(mapping4));
    assertThat(cacheMappingRegistry.size(), is(3));
}


TestFilePath: demilich1/metastone/game/src/test/java/net/demilich/metastone/tests/BlackrockMountainTests.java
++++++ MethodDeclarationQualifiedSignature: net.demilich.metastone.tests.BlackrockMountainTests.testBlackwingTechnician()
@Test
public void testBlackwingTechnician() {
    GameContext context = createContext(HeroClass.DRUID, HeroClass.HUNTER);
    Player player = context.getPlayer1();
    context.getLogic().removeAllCards(player.getId());
    Minion blackwingTechnician = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_blackwing_technician"));
    Assert.assertEquals(blackwingTechnician.getHp(), blackwingTechnician.getBaseHp());
    Assert.assertEquals(blackwingTechnician.getAttack(), blackwingTechnician.getBaseAttack());
    context.getLogic().receiveCard(player.getId(), CardCatalogue.getCardById("minion_azure_drake"));
    blackwingTechnician = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_blackwing_technician"));
    Assert.assertEquals(blackwingTechnician.getHp(), blackwingTechnician.getBaseHp() + 1);
    Assert.assertEquals(blackwingTechnician.getAttack(), blackwingTechnician.getBaseAttack() + 1);
}


TestFilePath: flink-extended/dl-on-flink/dl-on-flink-tensorflow-2.x/src/test/java/org/flinkextended/flink/ml/tensorflow/coding/ExampleCodingTest.java
++++++ MethodDeclarationQualifiedSignature: org.flinkextended.flink.ml.tensorflow.coding.ExampleCodingTest.table()
@Test
public void table() {
    TFConfig config = new TFConfig(1, 0, null, new String[] {}, null, null);
    TableSchema inputSchema = new TableSchema(new String[] { "fieldName" }, new TypeInformation[] { BasicTypeInfo.STRING_TYPE_INFO });
    TableSchema outputSchema = new TableSchema(new String[] { "fieldName" }, new TypeInformation[] { BasicTypeInfo.STRING_TYPE_INFO });
    ExampleCodingConfigUtil.configureExampleCoding(config, inputSchema, outputSchema, ExampleCodingConfig.ObjectType.ROW, Row.class);
    Assert.assertEquals(config.getProperty(TFConstants.INPUT_TF_EXAMPLE_CONFIG), config.getProperty(TFConstants.OUTPUT_TF_EXAMPLE_CONFIG));
}


TestFilePath: matsim-org/matsim-libs/contribs/socnetsim/src/test/java/org/matsim/contrib/socnetsim/usage/replanning/JoinableActivitiesPlanLinkIdentifierTest.java
++++++ MethodDeclarationQualifiedSignature: org.matsim.contrib.socnetsim.usage.replanning.JoinableActivitiesPlanLinkIdentifierTest.testSingleTourPlansZeroDurationAct()
@Test
public void testSingleTourPlansZeroDurationAct() {
    // Logger.getLogger( JoinableActivitiesPlanLinkIdentifier.class ).setLevel( Level.TRACE );
    final String type = "type";
    final Id<ActivityFacility> facility = Id.create("fac", ActivityFacility.class);
    final Plan plan1 = createSingleTripPlan(Id.create(1, Person.class), type, facility, 30, 30);
    final Plan plan2 = createSingleTripPlan(Id.create(2, Person.class), type, facility, 22, 40);
    final PlanLinkIdentifier testee = new JoinableActivitiesPlanLinkIdentifier(type);
    Assert.assertEquals("inconsistency!", testee.areLinked(plan1, plan2), testee.areLinked(plan2, plan1));
    Assert.assertTrue("plans with zero-length overlaping activities should be joint", testee.areLinked(plan1, plan2));
}


TestFilePath: f4b6a3/uuid-creator/src/test/java/com/github/f4b6a3/uuid/codec/UriCodecTest.java
++++++ MethodDeclarationQualifiedSignature: com.github.f4b6a3.uuid.codec.UriCodecTest.testEncodeAndDecode()
@Test
public void testEncodeAndDecode() {
    UuidCodec<URI> codec = new UriCodec();
    for (int i = 0; i < DEFAULT_LOOP_LIMIT; i++) {
        UUID uuid = UUID.randomUUID();
        // encode
        URI uri = codec.encode(uuid);
        // decode back
        assertEquals(uuid, codec.decode(uri));
    }
}


TestFilePath: gravitee-io/gravitee-api-management/gravitee-apim-repository/gravitee-apim-repository-test/src/test/java/io/gravitee/repository/ApiKeyRepositoryTest.java
++++++ MethodDeclarationQualifiedSignature: io.gravitee.repository.ApiKeyRepositoryTest.create_should_create_apiKey_with_right_data()
@Test
public void create_should_create_apiKey_with_right_data() throws Exception {
    String id = "id-of-new-apikey";
    ApiKey apiKey = new ApiKey();
    apiKey.setId(id);
    apiKey.setKey("apiKey");
    apiKey.setCreatedAt(new Date());
    apiKey.setRevoked(true);
    apiKey.setPaused(true);
    apiKey.setExpireAt(parse("11/02/2016"));
    apiKey.setDaysToExpirationOnLastNotification(30);
    apiKeyRepository.create(apiKey);
    Optional<ApiKey> optional = apiKeyRepository.findById(id);
    assertTrue("ApiKey not found", optional.isPresent());
    ApiKey keyFound = optional.get();
    assertNotNull("ApiKey not found", keyFound);
    assertEquals("Key value saved doesn't match", apiKey.getKey(), keyFound.getKey());
    assertTrue("Key expiration doesn't match", compareDate(apiKey.getExpireAt(), keyFound.getExpireAt()));
    assertEquals("Key paused status doesn't match", apiKey.isPaused(), keyFound.isPaused());
    assertEquals("Key revoked status doesn't match", apiKey.isRevoked(), keyFound.isRevoked());
    assertEquals("Days to expiration on last notification don't match", apiKey.getDaysToExpirationOnLastNotification(), keyFound.getDaysToExpirationOnLastNotification());
}


TestFilePath: Cloudslab/cloudsim/modules/cloudsim/src/test/java/org/cloudbus/cloudsim/UtilizationModelStochasticTest.java
++++++ MethodDeclarationQualifiedSignature: org.cloudbus.cloudsim.UtilizationModelStochasticTest.testGetUtilization()
/**
 * Test method for {@link cloudsim.UtilizationModelStochastic#getUtilization(double)}.
 */
@Test
public void testGetUtilization() {
    double utilization0 = utilizationModel.getUtilization(0);
    double utilization1 = utilizationModel.getUtilization(1);
    assertNotNull(utilization0);
    assertNotNull(utilization1);
    assertNotSame(utilization0, utilization1);
    assertEquals(utilization0, utilizationModel.getUtilization(0), 0);
    assertEquals(utilization1, utilizationModel.getUtilization(1), 0);
}


TestFilePath: jfree/jfreechart/src/test/java/org/jfree/chart/annotations/XYTitleAnnotationTest.java
++++++ MethodDeclarationQualifiedSignature: org.jfree.chart.annotations.XYTitleAnnotationTest.testHashCode()
/**
 * Two objects that are equal are required to return the same hashCode.
 */
@Test
public void testHashCode() {
    TextTitle t = new TextTitle("Title");
    XYTitleAnnotation a1 = new XYTitleAnnotation(1.0, 2.0, t);
    XYTitleAnnotation a2 = new XYTitleAnnotation(1.0, 2.0, t);
    assertEquals(a1, a2);
    int h1 = a1.hashCode();
    int h2 = a2.hashCode();
    assertEquals(h1, h2);
}


TestFilePath: JMRI/JMRI/java/test/jmri/NmraPacketTest.java
++++++ MethodDeclarationQualifiedSignature: jmri.NmraPacketTest.testGetAccSignalDecoderPktAddr2044()
@Test
public void testGetAccSignalDecoderPktAddr2044() {
    // max valid value
    int addr = 2044;
    byte[] ba = NmraPacket.accSignalDecoderPkt(addr, 12);
    Assert.assertEquals(addr, NmraPacket.getAccSignalDecoderPktAddress(ba));
}


TestFilePath: swimos/swim/swim-java/swim-runtime/swim-core/swim.structure/src/test/java/swim/structure/RecordMapMutableSpec.java
++++++ MethodDeclarationQualifiedSignature: swim.structure.RecordMapMutableSpec.testMutablePut()
@Test
public void testMutablePut() {
    final Record xs = Record.of().attr("k", "v").slot("a", "b");
    xs.put("k", "V");
    assertEquals(xs.size(), 2);
    assertEquals(xs.fieldCount(), 2);
    assertEquals(xs.get("k"), Text.from("V"));
    assertEquals(xs.get("a"), Text.from("b"));
    assertEquals(xs, Record.of().attr("k", "V").slot("a", "b"));
    xs.put("a", "B");
    assertEquals(xs.size(), 2);
    assertEquals(xs.fieldCount(), 2);
    assertEquals(xs.get("k"), Text.from("V"));
    assertEquals(xs.get("a"), Text.from("B"));
    assertEquals(xs, Record.of().attr("k", "V").slot("a", "B"));
    xs.put(Num.from(4), 5);
    assertEquals(xs.size(), 3);
    assertEquals(xs.fieldCount(), 3);
    assertEquals(xs.get("k"), Text.from("V"));
    assertEquals(xs.get("a"), Text.from("B"));
    assertEquals(xs.get(Num.from(4)), Num.from(5));
    assertEquals(xs, Record.of().attr("k", "V").slot("a", "B").slot(Num.from(4), 5));
    xs.put(Num.from(4), "FOUR");
    assertEquals(xs.size(), 3);
    assertEquals(xs.fieldCount(), 3);
    assertEquals(xs.get("k"), Text.from("V"));
    assertEquals(xs.get("a"), Text.from("B"));
    assertEquals(xs.get(Num.from(4)), Text.from("FOUR"));
    assertEquals(xs, Record.of().attr("k", "V").slot("a", "B").slot(Num.from(4), "FOUR"));
}


TestFilePath: amaembo/streamex/src/test/java/one/util/streamex/api/IntStreamExTest.java
++++++ MethodDeclarationQualifiedSignature: one.util.streamex.api.IntStreamExTest.testPairMap()
@Test
public void testPairMap() {
    assertEquals(0, IntStreamEx.range(0).pairMap(Integer::sum).count());
    assertEquals(0, IntStreamEx.range(1).pairMap(Integer::sum).count());
    assertEquals(Collections.singletonMap(1, 9999L), IntStreamEx.range(10000).pairMap((a, b) -> b - a).boxed().groupingBy(Function.identity(), Collectors.counting()));
    assertEquals(Collections.singletonMap(1, 9999L), IntStreamEx.range(10000).parallel().pairMap((a, b) -> b - a).boxed().groupingBy(Function.identity(), Collectors.counting()));
    assertEquals("Test Capitalization Stream", IntStreamEx.ofChars("test caPiTaliZation streaM").parallel().prepend(0).pairMap((c1, c2) -> !Character.isLetter(c1) && Character.isLetter(c2) ? Character.toTitleCase(c2) : Character.toLowerCase(c2)).charsToString());
    assertArrayEquals(IntStreamEx.range(9999).toArray(), dropLast(IntStreamEx.range(10000)).toArray());
    withRandom(r -> {
        int[] data = r.ints(1000, 1, 1000).toArray();
        int[] expected = new int[data.length - 1];
        int lastSquare = data[0] * data[0];
        for (int i = 0; i < expected.length; i++) {
            int newSquare = data[i + 1] * data[i + 1];
            expected[i] = newSquare - lastSquare;
            lastSquare = newSquare;
        }
        int[] result = IntStreamEx.of(data).map(x -> x * x).pairMap((a, b) -> b - a).toArray();
        assertArrayEquals(expected, result);
    });
    assertEquals(1, IntStreamEx.range(1000).map(x -> x * x).pairMap((a, b) -> b - a).pairMap((a, b) -> b - a).distinct().count());
    assertArrayEquals(IntStreamEx.constant(1, 100).toArray(), IntStreamEx.iterate(0, i -> i + 1).parallel().pairMap((a, b) -> b - a).limit(100).toArray());
    assertFalse(IntStreamEx.range(1000).greater(2000).parallel().pairMap((a, b) -> a).findFirst().isPresent());
}


TestFilePath: openlookeng/hetu-core/presto-hive/src/test/java/io/prestosql/plugin/hive/TestHiveRoles.java
++++++ MethodDeclarationQualifiedSignature: io.prestosql.plugin.hive.TestHiveRoles.testRevokeRoleMultipleTimes()
@Test
public void testRevokeRoleMultipleTimes() {
    executeFromAdmin("CREATE ROLE role1");
    executeFromAdmin("CREATE ROLE role2");
    executeFromAdmin("GRANT role1 TO USER user WITH ADMIN OPTION");
    executeFromAdmin("GRANT role2 TO ROLE role1 WITH ADMIN OPTION");
    assertContains(listApplicableRoles("user"), applicableRoles("user", "USER", "role1", "YES", "role1", "ROLE", "role2", "YES"));
    executeFromAdmin("REVOKE ADMIN OPTION FOR role1 FROM USER user");
    executeFromAdmin("REVOKE ADMIN OPTION FOR role1 FROM USER user");
    executeFromAdmin("REVOKE ADMIN OPTION FOR role2 FROM ROLE role1");
    executeFromAdmin("REVOKE ADMIN OPTION FOR role2 FROM ROLE role1");
    assertContains(listApplicableRoles("user"), applicableRoles("user", "USER", "role1", "NO", "role1", "ROLE", "role2", "NO"));
    executeFromAdmin("REVOKE role1 FROM USER user");
    executeFromAdmin("REVOKE role1 FROM USER user");
    executeFromAdmin("REVOKE role2 FROM ROLE role1");
    executeFromAdmin("REVOKE role2 FROM ROLE role1");
    assertEqualsIgnoreOrder(listApplicableRoles("user"), applicableRoles("user", "USER", "public", "NO"));
}


TestFilePath: itext/itext7/kernel/src/test/java/com/itextpdf/kernel/utils/objectpathitems/IndirectPathItemTest.java
++++++ MethodDeclarationQualifiedSignature: com.itextpdf.kernel.utils.objectpathitems.IndirectPathItemTest.equalsAndHashCodeTest()
@Test
public void equalsAndHashCodeTest() {
    PdfIndirectReference cmpIndirect = testCmp.getFirstPage().getPdfObject().getIndirectReference();
    PdfIndirectReference outIndirect = testOut.getFirstPage().getPdfObject().getIndirectReference();
    IndirectPathItem indirectPathItem1 = new IndirectPathItem(cmpIndirect, outIndirect);
    IndirectPathItem indirectPathItem2 = new IndirectPathItem(cmpIndirect, outIndirect);
    boolean result = indirectPathItem1.equals(indirectPathItem2);
    Assert.assertTrue(result);
    Assert.assertEquals(indirectPathItem1.hashCode(), indirectPathItem2.hashCode());
}


TestFilePath: apache/dolphinscheduler/dolphinscheduler-dao/src/test/java/org/apache/dolphinscheduler/dao/mapper/EnvironmentMapperTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.dolphinscheduler.dao.mapper.EnvironmentMapperTest.testQueryAllEnvironmentList()
/**
 * test query all environments
 */
@Test
public void testQueryAllEnvironmentList() {
    Environment entity = insertOne();
    List<Environment> environments = environmentMapper.queryAllEnvironmentList();
    Assert.assertEquals(environments.size(), 1);
    Assert.assertEquals(entity.toString(), environments.get(0).toString());
}


TestFilePath: authorwlh/wlhbdp/bdp-devops/dolphinscheduler/dolphinscheduler-dao/src/test/java/org/apache/dolphinscheduler/dao/mapper/EnvironmentMapperTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.dolphinscheduler.dao.mapper.EnvironmentMapperTest.testQueryAllEnvironmentList()
/**
 * test query all environments
 */
@Test
public void testQueryAllEnvironmentList() {
    Environment entity = insertOne();
    List<Environment> environments = environmentMapper.queryAllEnvironmentList();
    Assert.assertEquals(environments.size(), 1);
    Assert.assertEquals(entity.toString(), environments.get(0).toString());
}


TestFilePath: cantaloupe-project/cantaloupe/src/test/java/edu/illinois/library/cantaloupe/processor/codec/AbstractImageWriterTest.java
++++++ MethodDeclarationQualifiedSignature: edu.illinois.library.cantaloupe.processor.codec.AbstractImageWriterTest.testGetPreferredIIOImplementationsWithNoUserPreference()
@Test
void testGetPreferredIIOImplementationsWithNoUserPreference() {
    String[] impls = ((AbstractIIOImageWriter) instance).getPreferredIIOImplementations();
    assertArrayEquals(((AbstractIIOImageWriter) instance).getApplicationPreferredIIOImplementations(), impls);
}


TestFilePath: eclipse/eclipse-collections/unit-tests/src/test/java/org/eclipse/collections/impl/AbstractRichIterableTestCase.java
++++++ MethodDeclarationQualifiedSignature: org.eclipse.collections.impl.AbstractRichIterableTestCase.collectChar()
@Test
public void collectChar() {
    CharIterable result = this.newWith(1, 2, 3, 4).collectChar(PrimitiveFunctions.unboxIntegerToChar());
    Assert.assertEquals(CharBags.mutable.of((char) 1, (char) 2, (char) 3, (char) 4), result.toBag());
    Assert.assertEquals(CharBags.mutable.of((char) 1, (char) 2, (char) 3, (char) 4), CharBags.mutable.ofAll(result));
}


TestFilePath: jfree/jfreechart/src/test/java/org/jfree/chart/labels/BubbleXYItemLabelGeneratorTest.java
++++++ MethodDeclarationQualifiedSignature: org.jfree.chart.labels.BubbleXYItemLabelGeneratorTest.testHashCode()
/**
 * Simple check that hashCode is implemented.
 */
@Test
public void testHashCode() {
    BubbleXYItemLabelGenerator g1 = new BubbleXYItemLabelGenerator();
    BubbleXYItemLabelGenerator g2 = new BubbleXYItemLabelGenerator();
    assertEquals(g1, g2);
    assertEquals(g1.hashCode(), g2.hashCode());
}


TestFilePath: thomasmueller/minperf/src/test/java/org/minperf/select/SelectTest.java
++++++ MethodDeclarationQualifiedSignature: org.minperf.select.SelectTest.testBitInInt()
@Test
public void testBitInInt() {
    Random r = new Random(1);
    for (int n = 0; n < 64; n++) {
        for (int i = 0; i < 100; i++) {
            if (Long.bitCount(i) < n + 1) {
                continue;
            }
            int a = VerySimpleSelect.selectBitSlow(i, n);
            int b = VerySimpleSelect.selectBitLong(i, n);
            int c = VerySimpleSelect.selectBit(i, n);
            int d = VerySimpleSelect.selectBitReverse(Integer.reverse(i), n);
            assertEquals(a, b);
            assertEquals(a, c);
            assertEquals(a, d);
        }
        for (int i = 0; i < 100; i++) {
            int x = r.nextInt();
            if (Integer.bitCount(i) < n + 1) {
                continue;
            }
            int a = VerySimpleSelect.selectBitSlow(x, n);
            int b = VerySimpleSelect.selectBitLong(x & 0xffffffffL, n);
            int c = VerySimpleSelect.selectBitLongReverse(Long.reverse(x & 0xffffffffL), n);
            int d = VerySimpleSelect.selectBit(x, n);
            int e = VerySimpleSelect.selectBitReverse(Integer.reverse(x), n);
            assertEquals(a, b);
            assertEquals(a, c);
            assertEquals(a, d);
            assertEquals(a, e);
        }
        for (int i = 0; i < 100; i++) {
            long x = r.nextLong();
            if (Long.bitCount(x) < n + 1) {
                continue;
            }
            int a = VerySimpleSelect.selectBitSlow(x, n);
            int b = VerySimpleSelect.selectBitLong(x, n);
            assertEquals(a, b);
        }
    }
}


TestFilePath: jfree/jfreechart/src/test/java/org/jfree/data/time/MinuteTest.java
++++++ MethodDeclarationQualifiedSignature: org.jfree.data.time.MinuteTest.testHashcode()
/**
 * Two objects that are equal are required to return the same hashCode.
 */
@Test
public void testHashcode() {
    Minute m1 = new Minute(45, 5, 1, 2, 2003);
    Minute m2 = new Minute(45, 5, 1, 2, 2003);
    assertEquals(m1, m2);
    int h1 = m1.hashCode();
    int h2 = m2.hashCode();
    assertEquals(h1, h2);
}


TestFilePath: apache/ignite/modules/core/src/test/java/org/apache/ignite/internal/processors/cache/CacheStopAndDestroySelfTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.ignite.internal.processors.cache.CacheStopAndDestroySelfTest.testDhtClose()
/**
 * Test Dht close.
 *
 * @throws Exception If failed.
 */
@Test
public void testDhtClose() throws Exception {
    startGridsMultiThreaded(gridCount());
    IgniteCache<Integer, Integer> dhtCache0 = grid(0).getOrCreateCache(getDhtConfig());
    final Integer key = primaryKey(dhtCache0);
    assertNull(dhtCache0.get(key));
    dhtCache0.put(key, key);
    assertEquals(key, dhtCache0.get(key));
    // DHT Close. No-op.
    IgniteCache<Integer, Integer> dhtCache1 = grid(1).cache(CACHE_NAME_DHT);
    IgniteCache<Integer, Integer> dhtCache2 = grid(2).cache(CACHE_NAME_DHT);
    dhtCache0.close();
    try {
        // Not affected, but can not be taken.
        dhtCache0.get(key);
        fail();
    } catch (IllegalStateException ignored) {
        // No-op
    }
    // Not affected.
    assertEquals(key, dhtCache1.get(key));
    // Not affected.
    assertEquals(key, dhtCache2.get(key));
    // DHT Creation after closed.
    IgniteCache<Integer, Integer> dhtCache0New = grid(0).cache(CACHE_NAME_DHT);
    assertNotSame(dhtCache0, dhtCache0New);
    // Not affected, can be taken since cache reopened.
    assertEquals(key, dhtCache0New.get(key));
    dhtCache2.put(key, key + 1);
    assertEquals((Object) (key + 1), dhtCache0New.get(key));
    // Check close at last node.
    stopAllGrids(true);
    startGrid(0);
    dhtCache0 = grid(0).getOrCreateCache(getDhtConfig());
    assertNull(dhtCache0.get(key));
    dhtCache0.put(key, key);
    assertEquals(key, dhtCache0.get(key));
    // Closing last node.
    dhtCache0.close();
    try {
        // Can not be taken.
        dhtCache0.get(key);
        fail();
    } catch (IllegalStateException ignored) {
        // No-op
    }
    // Reopening cache.
    dhtCache0 = grid(0).cache(CACHE_NAME_DHT);
    // Entry not loosed.
    assertEquals(key, dhtCache0.get(key));
}


TestFilePath: gridgain/gridgain/modules/core/src/test/java/org/apache/ignite/internal/processors/cache/CacheStopAndDestroySelfTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.ignite.internal.processors.cache.CacheStopAndDestroySelfTest.testDhtClose()
/**
 * Test Dht close.
 *
 * @throws Exception If failed.
 */
@Test
public void testDhtClose() throws Exception {
    startGridsMultiThreaded(gridCount());
    IgniteCache<Integer, Integer> dhtCache0 = grid(0).getOrCreateCache(getDhtConfig());
    final Integer key = primaryKey(dhtCache0);
    assertNull(dhtCache0.get(key));
    dhtCache0.put(key, key);
    assertEquals(key, dhtCache0.get(key));
    // DHT Close. No-op.
    IgniteCache<Integer, Integer> dhtCache1 = grid(1).cache(CACHE_NAME_DHT);
    IgniteCache<Integer, Integer> dhtCache2 = grid(2).cache(CACHE_NAME_DHT);
    dhtCache0.close();
    try {
        // Not affected, but can not be taken.
        dhtCache0.get(key);
        fail();
    } catch (IllegalStateException ignored) {
        // No-op
    }
    // Not affected.
    assertEquals(key, dhtCache1.get(key));
    // Not affected.
    assertEquals(key, dhtCache2.get(key));
    // DHT Creation after closed.
    IgniteCache<Integer, Integer> dhtCache0New = grid(0).cache(CACHE_NAME_DHT);
    assertNotSame(dhtCache0, dhtCache0New);
    // Not affected, can be taken since cache reopened.
    assertEquals(key, dhtCache0New.get(key));
    dhtCache2.put(key, key + 1);
    assertEquals((Object) (key + 1), dhtCache0New.get(key));
    // Check close at last node.
    stopAllGrids(true);
    startGrid(0);
    dhtCache0 = grid(0).getOrCreateCache(getDhtConfig());
    assertNull(dhtCache0.get(key));
    dhtCache0.put(key, key);
    assertEquals(key, dhtCache0.get(key));
    // Closing last node.
    dhtCache0.close();
    try {
        // Can not be taken.
        dhtCache0.get(key);
        fail();
    } catch (IllegalStateException ignored) {
        // No-op
    }
    // Reopening cache.
    dhtCache0 = grid(0).cache(CACHE_NAME_DHT);
    // Entry not loosed.
    assertEquals(key, dhtCache0.get(key));
}


TestFilePath: didi/LogiKM/kafka-manager-extends/kafka-manager-account/src/test/java/com/xiaojukeji/kafka/manager/account/AccountServiceTest.java
++++++ MethodDeclarationQualifiedSignature: com.xiaojukeji.kafka.manager.account.AccountServiceTest.getAccountFromCache2AutoHandleTest()
@Test(description = "自动审批测试")
public void getAccountFromCache2AutoHandleTest() {
    Account account = getAccount();
    account.setUsername(Constant.AUTO_HANDLE_USER_NAME);
    account.setChineseName(Constant.AUTO_HANDLE_CHINESE_NAME);
    account.setAccountRoleEnum(AccountRoleEnum.OP);
    Assert.assertEquals(accountService.getAccountFromCache(Constant.AUTO_HANDLE_USER_NAME).toString(), account.toString());
}


TestFilePath: pulsarIO/jetstream/jetstreamcore/src/test/java/com/ebay/jetstream/util/offheap/QueueDirectMemoryManagerTest.java
++++++ MethodDeclarationQualifiedSignature: com.ebay.jetstream.util.offheap.QueueDirectMemoryManagerTest.test()
@Test
public void test() {
    int pageNum = 3000;
    int pageSize = 8192;
    QueueDirectMemoryManager m = new QueueDirectMemoryManager(pageSize, pageNum);
    for (int i = 1; i < pageNum + 5; i++) {
        if (m.getOOMErrorCount() > 100) {
            break;
        }
        int first = m.malllocFirstPage();
        Assert.assertEquals(m.getUsedMemory(), pageSize);
        if (i < pageNum) {
            int address = m.malllocPages(i, first);
            if (address == QueueDirectMemoryManager.NULL_PAGE) {
                Assert.assertEquals(m.getUsedMemory(), pageSize);
            } else {
                Assert.assertEquals(address, first);
                Assert.assertEquals(m.getUsedMemory(), pageSize * (i + 1));
            }
        } else {
            Assert.assertEquals(m.malllocPages(i, first), QueueDirectMemoryManager.NULL_PAGE);
        }
        int curPage = first;
        int nextPage;
        while (curPage != QueueDirectMemoryManager.NULL_PAGE) {
            nextPage = m.getNextPage(curPage);
            m.free(curPage);
            curPage = nextPage;
        }
        Assert.assertEquals(m.getUsedMemory(), 0);
    }
}


TestFilePath: esastack/esa-restlight/restlight-server-adapter/src/test/java/esa/httpserver/impl/AsyncRequestImplTest.java
++++++ MethodDeclarationQualifiedSignature: esa.httpserver.impl.AsyncRequestImplTest.testHeaderAndTrailerConvert()
@Test
void testHeaderAndTrailerConvert() throws IOException {
    final Request mock = mock(Request.class);
    when(mock.method()).thenReturn(HttpMethod.POST);
    when(mock.rawMethod()).thenReturn(HttpMethod.POST.name());
    when(mock.headers()).thenReturn(new Http1HeadersImpl());
    final Aggregation aggregation = mock(Aggregation.class);
    final ByteBuf body = Unpooled.copiedBuffer("abc".getBytes(StandardCharsets.UTF_8));
    when(aggregation.body()).thenReturn(body);
    when(aggregation.trailers()).thenReturn(new Http1HeadersImpl());
    when(mock.aggregated()).thenReturn(aggregation);
    final AsyncRequestImpl req = new AsyncRequestImpl(mock);
    when(mock.version()).thenReturn(HttpVersion.HTTP_1_1);
    assertEquals(io.netty.handler.codec.http.HttpVersion.HTTP_1_1, req.httpVersion());
    assertEquals(io.netty.handler.codec.http.HttpVersion.HTTP_1_1.text(), req.protocol());
    when(mock.scheme()).thenReturn("http");
    assertEquals("http", req.scheme());
    when(mock.uri()).thenReturn("/foo?a=1");
    assertEquals("/foo?a=1", req.uri());
    when(mock.path()).thenReturn("/foo");
    assertEquals("/foo", req.path());
    when(mock.query()).thenReturn("a=1");
    assertEquals("a=1", req.query());
    when(mock.version()).thenReturn(HttpVersion.HTTP_1_1);
    assertEquals("HTTP/1.1", req.getProtocol());
    assertFalse(req.hasAttribute("name0"));
    req.setAttribute("name0", "value1");
    assertTrue(req.hasAttribute("name0"));
    when(mock.scheme()).thenReturn("https");
    assertEquals("https", req.scheme());
    when(mock.path()).thenReturn("/abc");
    assertEquals("/abc", req.getRequestURI());
    when(mock.uri()).thenReturn("/def");
    assertEquals("/def", req.getURIAndQueryString());
    assertEquals("POST", req.getMethod());
    assertEquals(io.netty.handler.codec.http.HttpMethod.POST, req.method());
    assertEquals("POST", req.rawMethod());
    assertSame(body, req.byteBufBody());
    assertSame(body, req.getBodyByteBuf());
    assertEquals("abc", new String(req.body(), StandardCharsets.UTF_8));
    assertEquals("abc", new String(req.getBody(), StandardCharsets.UTF_8));
    assertEquals(3, req.contentLength());
    assertEquals(3, req.getContentLength());
    final HttpInputStream in = req.inputStream();
    assertSame(in, req.getInputStream());
    assertSame(in, req.inputStream());
    assertTrue(in instanceof ByteBufHttpInputStream);
    assertEquals("abc", IOUtils.toString(in, StandardCharsets.UTF_8));
    when(mock.remoteAddress()).thenReturn(new InetSocketAddress("127.0.0.1", 8080));
    assertEquals("127.0.0.1", req.remoteAddr());
    assertEquals("127.0.0.1", req.getRemoteAddr());
    assertEquals(8080, req.remotePort());
    when(mock.tcpSourceAddress()).thenReturn(new InetSocketAddress("127.0.0.2", 8080));
    assertEquals("127.0.0.2", req.tcpSourceAddr());
    assertEquals("127.0.0.2", req.getTcpSourceAddr());
    when(mock.localAddress()).thenReturn(new InetSocketAddress("127.0.0.1", 1234));
    assertEquals("127.0.0.1", req.localAddr());
    assertEquals("127.0.0.1", req.getLocalAddr());
    assertEquals(1234, req.localPort());
    assertEquals(1234, req.getLocalPort());
    final LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("a", "1");
    params.add("b", "2");
    when(mock.paramMap()).thenReturn(params);
    assertSame(params, req.parameterMap());
    assertEquals("1", req.getParameter("a"));
    assertEquals("2", req.getParameter("b"));
    assertEquals(1, req.getParameters("a").size());
    assertEquals(2, req.parameterMap().size());
    assertEquals(2, req.getParameterMap().size());
    when(mock.toString()).thenReturn("foo");
    assertEquals("foo", req.toString());
}


TestFilePath: nikfoundas/etcd-viewer/src/test/java/org/github/etcd/rest/TestEtcdProxy.java
++++++ MethodDeclarationQualifiedSignature: org.github.etcd.rest.TestEtcdProxy.testDeleteValue()
@Test
public void testDeleteValue() {
    String key = createTestKey();
    etcdProxy.saveNode(new EtcdNode(key, "foobar"));
    EtcdNode retrieved = etcdProxy.getNode(key);
    assertNotNull(retrieved);
    assertFalse(retrieved.isDir());
    assertEquals(key, retrieved.getKey());
    EtcdNode deleted = etcdProxy.deleteNode(retrieved);
    assertNotNull(deleted);
    assertEquals(key, deleted.getKey());
    try {
        retrieved = etcdProxy.getNode(key);
        fail("Node: " + key + " should be deleted");
    } catch (Exception e) {
    }
}


TestFilePath: dhis2/dhis2-core/dhis-2/dhis-services/dhis-service-core/src/test/java/org/hisp/dhis/option/OptionServiceTest.java
++++++ MethodDeclarationQualifiedSignature: org.hisp.dhis.option.OptionServiceTest.testSaveGet()
@Test
void testSaveGet() {
    long idA = optionService.saveOptionSet(optionSetA);
    long idB = optionService.saveOptionSet(optionSetB);
    long idC = optionService.saveOptionSet(optionSetC);
    OptionSet actualA = optionService.getOptionSet(idA);
    OptionSet actualB = optionService.getOptionSet(idB);
    OptionSet actualC = optionService.getOptionSet(idC);
    assertEquals(optionSetA, actualA);
    assertEquals(optionSetB, actualB);
    assertEquals(optionSetC, actualC);
    assertEquals(4, optionSetA.getOptions().size());
    assertEquals(4, optionSetB.getOptions().size());
    assertEquals(0, optionSetC.getOptions().size());
    assertTrue(optionSetA.getOptions().contains(option1));
    assertTrue(optionSetA.getOptions().contains(option2));
    assertTrue(optionSetA.getOptions().contains(option3));
    assertTrue(optionSetA.getOptions().contains(option4));
}


TestFilePath: Lihuanghe/SMSGate/src/test/java/com/zx/sms/codec/cmpp/TestMsgCmppSubmit7FDecoder.java
++++++ MethodDeclarationQualifiedSignature: com.zx.sms.codec.cmpp.TestMsgCmppSubmit7FDecoder.testCodec()
@Test
public void testCodec() {
    CmppSubmitRequestMessage msg = new CmppSubmitRequestMessage();
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("a", 1);
    map.put("b", "adf");
    msg.setAttachment((Serializable) map);
    msg.setMsgContent("12341");
    ByteBuf buf = encode(msg);
    ByteBuf copybuf = buf.copy();
    // packageLength
    buf.readInt();
    Assert.assertEquals(msg.getPacketType().getCommandId(), buf.readInt());
    Assert.assertEquals(msg.getHeader().getSequenceId(), buf.readInt());
    CmppSubmitRequestMessage result = decode(copybuf);
    Assert.assertEquals(msg.getAttachment(), result.getAttachment());
    Assert.assertEquals(msg.getMsgContent(), result.getMsgContent());
}


TestFilePath: itext/itext7/kernel/src/test/java/com/itextpdf/kernel/utils/objectpathitems/OffsetPathItemTest.java
++++++ MethodDeclarationQualifiedSignature: com.itextpdf.kernel.utils.objectpathitems.OffsetPathItemTest.equalsAndHashCodeTest()
@Test
public void equalsAndHashCodeTest() {
    int offset = 1;
    OffsetPathItem offsetPathItem1 = new OffsetPathItem(offset);
    OffsetPathItem offsetPathItem2 = new OffsetPathItem(offset);
    boolean result = offsetPathItem1.equals(offsetPathItem2);
    Assert.assertTrue(result);
    Assert.assertEquals(offsetPathItem1.hashCode(), offsetPathItem2.hashCode());
}


TestFilePath: jfree/jfreechart/src/test/java/org/jfree/chart/renderer/xy/DeviationRendererTest.java
++++++ MethodDeclarationQualifiedSignature: org.jfree.chart.renderer.xy.DeviationRendererTest.testEquals()
/**
 * Test that the equals() method distinguishes all fields.
 */
@Test
public void testEquals() {
    // default instances
    DeviationRenderer r1 = new DeviationRenderer();
    DeviationRenderer r2 = new DeviationRenderer();
    assertEquals(r1, r2);
    assertEquals(r2, r1);
    r1.setAlpha(0.1f);
    assertNotEquals(r1, r2);
    r2.setAlpha(0.1f);
    assertEquals(r1, r2);
}


TestFilePath: itext/itext7/sign/src/test/java/com/itextpdf/signatures/sign/PdfSignatureTest.java
++++++ MethodDeclarationQualifiedSignature: com.itextpdf.signatures.sign.PdfSignatureTest.setAndGetNameTest()
@Test
public void setAndGetNameTest() throws IOException {
    PdfSignature signature = getTestSignature(sourceFolder + "simpleSignature.pdf");
    Assert.assertNull(signature.getName());
    String name = "iText person";
    signature.setName(name);
    Assert.assertEquals(name, signature.getName());
}


TestFilePath: BNYMellon/spring-kata/spring-boot/todo/src/test/java/bnymellon/training/spring/boot/todo/model/response/StatusTest.java
++++++ MethodDeclarationQualifiedSignature: bnymellon.training.spring.boot.todo.model.response.StatusTest.testHashCode()
@Test
public void testHashCode() throws Exception {
    assertEquals(status1.hashCode(), status2.hashCode(), "Status1 and Status2 should have the same hashCode");
    assertNotEquals(status1.hashCode(), status3.hashCode(), "Status1 and Status3 should not have the same hashCode");
}


TestFilePath: eclipse/eclipse-collections/unit-tests/src/test/java/org/eclipse/collections/impl/map/immutable/ImmutableMapIterableTestCase.java
++++++ MethodDeclarationQualifiedSignature: org.eclipse.collections.impl.map.immutable.ImmutableMapIterableTestCase.getIfAbsent()
@Test
public void getIfAbsent() {
    Integer absentKey = this.size() + 1;
    String absentValue = String.valueOf(absentKey);
    // Absent key behavior
    ImmutableMapIterable<Integer, String> classUnderTest = this.classUnderTest();
    Assert.assertEquals(absentValue, classUnderTest.getIfAbsentValue(absentKey, absentValue));
    // Present key behavior
    Assert.assertEquals("1", classUnderTest.getIfAbsentValue(1, absentValue));
    // Still unchanged
    Assert.assertEquals(this.equalUnifiedMap(), classUnderTest);
}


TestFilePath: mwilliamson/java-mammoth/src/test/java/org/zwobble/mammoth/tests/html/HtmlCollapseTests.java
++++++ MethodDeclarationQualifiedSignature: org.zwobble.mammoth.tests.html.HtmlCollapseTests.consecutiveCollapsibleElementsAreCollapsedIfTheyHaveTheSameTagAndAttributes()
@Test
public void consecutiveCollapsibleElementsAreCollapsedIfTheyHaveTheSameTagAndAttributes() {
    assertThat(Html.collapse(list(Html.collapsibleElement("p", list(Html.text("One"))), Html.collapsibleElement("p", list(Html.text("Two"))))), deepEquals(list(Html.collapsibleElement("p", list(Html.text("One"), Html.text("Two"))))));
}


TestFilePath: apache/solr/solr/solrj/src/test/org/apache/solr/client/solrj/request/TestCoreAdmin.java
++++++ MethodDeclarationQualifiedSignature: org.apache.solr.client.solrj.request.TestCoreAdmin.testValidCoreRename()
@Test
public void testValidCoreRename() throws Exception {
    Collection<String> names = cores.getAllCoreNames();
    assertFalse(names.toString(), names.contains("coreRenamed"));
    assertTrue(names.toString(), names.contains("core1"));
    CoreAdminRequest.renameCore("core1", "coreRenamed", getSolrAdmin());
    names = cores.getAllCoreNames();
    assertTrue(names.toString(), names.contains("coreRenamed"));
    assertFalse(names.toString(), names.contains("core1"));
    // rename it back
    CoreAdminRequest.renameCore("coreRenamed", "core1", getSolrAdmin());
    names = cores.getAllCoreNames();
    assertFalse(names.toString(), names.contains("coreRenamed"));
    assertTrue(names.toString(), names.contains("core1"));
    assertEquals(names.size(), cores.getNumAllCores());
}


TestFilePath: openjdk/jmc/core/tests/org.openjdk.jmc.common.test/src/main/java/org/openjdk/jmc/common/test/unit/KindOfQuantityTest.java
++++++ MethodDeclarationQualifiedSignature: org.openjdk.jmc.common.test.unit.KindOfQuantityTest.ScalarUnitTest.testCustomCommonFormats()
@Test
public void testCustomCommonFormats() {
    Assume.assumeTrue("Custom units currently only supported for LinearUnit:s", unit instanceof LinearUnit);
    LinearUnit unit = (LinearUnit) this.unit;
    // Test a large number of quantities, but keep test deterministic.
    Random rnd = new Random(17);
    for (int i = 0; i < 1000; i++) {
        IQuantity quantity = randomCustomQuantity(unit, rnd);
        String auto = quantity.displayUsing(IDisplayable.AUTO);
        String exact = quantity.displayUsing(IDisplayable.EXACT);
        String verbose = quantity.displayUsing(IDisplayable.VERBOSE);
        assertAllSpacesAreNonBreaking(auto);
        assertAllSpacesAreNonBreaking(exact);
        if (exact.length() == verbose.length()) {
            // This does not hold for custom units.
            assertAllSpacesAreNonBreaking(verbose);
        }
        // These should of course not be strict, but since it holds now, it is nice
        // to be informed of when it no longer holds.
        assertTrue("auto <= exact", auto.length() <= exact.length());
        assertTrue("exact <= verbose", exact.length() <= verbose.length());
    }
}


TestFilePath: NationalSecurityAgency/emissary/src/test/java/emissary/kff/KffChainTest.java
++++++ MethodDeclarationQualifiedSignature: emissary.kff.KffChainTest.testComputationsOnEmptyFilterChain()
@Test
public void testComputationsOnEmptyFilterChain() {
    KffChain chain = new KffChain();
    List<String> myAlgs = new ArrayList<String>();
    myAlgs.add("MD5");
    myAlgs.add("SHA-1");
    myAlgs.add("SHA-256");
    chain.setAlgorithms(myAlgs);
    assertEquals(3, chain.getAlgorithms().size(), "Algorithms stored in chain");
    assertEquals(0, chain.size(), "Size of chain is zero");
    try {
        KffResult kr = chain.check("TEST ITEM", DATA);
        Set<String> algs = kr.getResultNames();
        assertNotNull(algs, "Algorithm set returned");
        assertEquals(3, algs.size(), "All results present");
        Iterator<String> i = algs.iterator();
        assertEquals("MD5", i.next(), "MD5 alg present");
        assertEquals("SHA-1", i.next(), "SHA-1 alg present");
        assertEquals("SHA-256", i.next(), "SHA-256 alg present");
        assertEquals("TEST ITEM", kr.getItemName(), "Item name copied");
        // Test values on convenience methods match
        assertEquals(kr.getResultString("SHA-1"), kr.getShaString(), "SHA-1 convenience method");
        byte[] md5c = kr.getMd5();
        byte[] md5r = kr.getResult("MD5");
        assertEquals(md5c.length, md5r.length, "MD5 Results match");
        for (int j = 0; j < md5c.length; j++) {
            assertEquals(md5r[j], md5c[j], "MD5 results match at pos " + j);
        }
        assertFalse(kr.isHit(), "Cannot have a hit on zero length chain");
    } catch (Exception ex) {
        throw new AssertionError("Could not compute results: " + ex.getMessage());
    }
}


TestFilePath: box/mojito/webapp/src/test/java/com/box/l10n/mojito/service/pollableTask/PollableTaskServiceTest.java
++++++ MethodDeclarationQualifiedSignature: com.box.l10n.mojito.service.pollableTask.PollableTaskServiceTest.testGetPollableTask()
@Test
public void testGetPollableTask() {
    PollableTask createPollableTask = pollableTaskService.createPollableTask(null, testIdWatcher.getEntityName("testGetPollableTask"), "a message", 0);
    PollableTask pollableTask = pollableTaskService.getPollableTask(createPollableTask.getId());
    assertEquals(pollableTask.getId(), createPollableTask.getId());
    assertEquals(pollableTask.getName(), createPollableTask.getName());
    assertEquals("a message", createPollableTask.getMessage());
    assertEquals("\"a message\"", createPollableTask.getMessageAsJson());
    assertEquals(0, createPollableTask.getExpectedSubTaskNumber());
    assertNull(createPollableTask.getFinishedDate());
    assertFalse(createPollableTask.isAllFinished());
}


TestFilePath: rsksmart/rskj/rskj-core/src/test/java/org/ethereum/rpc/Web3ImplLogsTest.java
++++++ MethodDeclarationQualifiedSignature: org.ethereum.rpc.Web3ImplLogsTest.getLogsTwiceFromBlockchainWithEventInContractCreation()
@Test
public void getLogsTwiceFromBlockchainWithEventInContractCreation() throws Exception {
    addEventInContractCreation();
    FilterRequest fr = new FilterRequest();
    fr.setFromBlock("earliest");
    web3.eth_getLogs(fr);
    Object[] logs = web3.eth_getLogs(fr);
    assertNotNull(logs);
    assertEquals(1, logs.length);
    String txhash = ((LogFilterElement) logs[0]).transactionHash;
    TransactionReceiptDTO txdto = web3.eth_getTransactionReceipt(txhash);
    assertEquals(txdto.getContractAddress(), ((LogFilterElement) logs[0]).address);
}


TestFilePath: rsksmart/rskj/rskj-core/src/test/java/co/rsk/trie/TrieHashTest.java
++++++ MethodDeclarationQualifiedSignature: co.rsk.trie.TrieHashTest.emptyTriesHasTheSameHash()
@Test
public void emptyTriesHasTheSameHash() {
    Trie trie1 = new Trie();
    Trie trie2 = new Trie();
    Trie trie3 = new Trie();
    Assert.assertEquals(trie1.getHash(), trie1.getHash());
    Assert.assertEquals(trie1.getHash(), trie2.getHash());
    Assert.assertEquals(trie3.getHash(), trie2.getHash());
}


TestFilePath: salesforce/Argus/ArgusCore/src/test/java/com/salesforce/dva/argus/service/ChartServiceTest.java
++++++ MethodDeclarationQualifiedSignature: com.salesforce.dva.argus.service.ChartServiceTest.testCreateChart()
@Test
public void testCreateChart() {
    List<ChartQuery> queries = Arrays.asList(new ChartQuery(ChartQueryType.METRIC, "-1h:argus.jvm:mem.heap.used:avg"));
    Chart chart = new Chart(_adminUser, _adminUser, ChartType.LINE, queries);
    chart.setTitle("This is an example chart");
    chart = _chartService.updateChart(chart);
    assertNotNull(chart.getId());
    Chart retrievedChart = _chartService.getChartByPrimaryKey(chart.getId());
    assertEquals(chart.getId(), retrievedChart.getId());
}


TestFilePath: HTTP-RPC/HTTP-RPC/httprpc-client/src/test/java/org/httprpc/beans/BeanAdapterTest.java
++++++ MethodDeclarationQualifiedSignature: org.httprpc.beans.BeanAdapterTest.testObjectMethodDelegation()
@Test
public void testObjectMethodDelegation() {
    Map<String, Object> map1 = new HashMap<String, Object>() {

        @Override
        public String toString() {
            return "abc";
        }
    };
    map1.put("flag", true);
    Map<String, Object> map2 = mapOf(entry("flag", true));
    TestInterface.NestedInterface nestedBean1 = BeanAdapter.coerce(map1, TestInterface.NestedInterface.class);
    TestInterface.NestedInterface nestedBean2 = BeanAdapter.coerce(map2, TestInterface.NestedInterface.class);
    assertEquals(nestedBean1, nestedBean2);
    assertEquals(map1.hashCode(), nestedBean1.hashCode());
    assertEquals(map1.toString(), nestedBean1.toString());
}


TestFilePath: newrelic/newrelic-java-agent/newrelic-agent/src/test/java/com/newrelic/agent/TransactionAppNamingTest.java
++++++ MethodDeclarationQualifiedSignature: com.newrelic.agent.TransactionAppNamingTest.testSetAndThenGet()
@Test
public void testSetAndThenGet() {
    Transaction tx = Transaction.getTransaction();
    tx.setApplicationName(ApplicationNamePriority.SERVLET_INIT_PARAM, "ServletName");
    Assert.assertEquals(ApplicationNamePriority.SERVLET_INIT_PARAM, tx.getPriorityApplicationName().getPriority());
    Assert.assertEquals("ServletName", tx.getPriorityApplicationName().getName());
    Assert.assertEquals(1, tx.getPriorityApplicationName().getNames().size());
    Assert.assertEquals(tx.getPriorityApplicationName().getNames().get(0), tx.getPriorityApplicationName().getName());
    Assert.assertEquals(tx.getPriorityApplicationName().getName(), tx.getApplicationName());
}


TestFilePath: dyn4j/dyn4j/src/test/java/org/dyn4j/world/result/DetectResultTest.java
++++++ MethodDeclarationQualifiedSignature: org.dyn4j.world.result.DetectResultTest.copy()
/**
 * Tests the copy methods.
 */
@Test
public void copy() {
    Body body = new Body();
    BodyFixture bf = new BodyFixture(Geometry.createCircle(0.5));
    DetectResult<Body, BodyFixture> dr = new DetectResult<Body, BodyFixture>();
    ConvexCastResult<Body, BodyFixture> ccr = new ConvexCastResult<Body, BodyFixture>();
    ConvexDetectResult<Body, BodyFixture> cdr = new ConvexDetectResult<Body, BodyFixture>();
    RaycastResult<Body, BodyFixture> rr = new RaycastResult<Body, BodyFixture>();
    // NOTE: the goal of this test is not to test the xxx.copy(xxx) methods of
    // the constituent objects, so we're just setting a value to test that
    // the result.setXXX method worked
    TimeOfImpact toi = new TimeOfImpact();
    toi.setTime(0.5);
    Penetration pen = new Penetration();
    pen.setDepth(5.0);
    Raycast ray = new Raycast();
    ray.setDistance(4.0);
    dr.setBody(body);
    dr.setFixture(bf);
    ccr.setBody(body);
    ccr.setFixture(bf);
    ccr.setTimeOfImpact(toi);
    cdr.setBody(body);
    cdr.setFixture(bf);
    cdr.setPenetration(pen);
    rr.setBody(body);
    rr.setFixture(bf);
    rr.setRaycast(ray);
    // test standard copy
    DetectResult<Body, BodyFixture> dr2 = dr.copy();
    ConvexCastResult<Body, BodyFixture> ccr2 = ccr.copy();
    ConvexDetectResult<Body, BodyFixture> cdr2 = cdr.copy();
    RaycastResult<Body, BodyFixture> rr2 = rr.copy();
    TestCase.assertEquals(dr2.getBody(), body);
    TestCase.assertEquals(dr2.getFixture(), bf);
    TestCase.assertEquals(ccr2.getBody(), body);
    TestCase.assertEquals(ccr2.getFixture(), bf);
    TestCase.assertEquals(ccr2.getTimeOfImpact().getTime(), toi.getTime());
    TestCase.assertTrue(ccr2.getTimeOfImpact() != toi);
    TestCase.assertEquals(cdr2.getBody(), body);
    TestCase.assertEquals(cdr2.getFixture(), bf);
    TestCase.assertEquals(cdr2.getPenetration().getDepth(), pen.getDepth());
    TestCase.assertTrue(cdr2.getPenetration() != pen);
    TestCase.assertEquals(rr2.getBody(), body);
    TestCase.assertEquals(rr2.getFixture(), bf);
    TestCase.assertEquals(rr2.getRaycast().getDistance(), ray.getDistance());
    TestCase.assertTrue(rr2.getRaycast() != ray);
    // test updates to the copy don't update the original
    ccr.getTimeOfImpact().setTime(0.7);
    cdr.getPenetration().setDepth(1.0);
    rr.getRaycast().setDistance(2.0);
    TestCase.assertEquals(ccr2.getTimeOfImpact().getTime(), toi.getTime());
    TestCase.assertEquals(cdr2.getPenetration().getDepth(), pen.getDepth());
    TestCase.assertEquals(rr2.getRaycast().getDistance(), ray.getDistance());
    // test copy(other)
    dr2 = new DetectResult<Body, BodyFixture>();
    ccr2 = new ConvexCastResult<Body, BodyFixture>();
    cdr2 = new ConvexDetectResult<Body, BodyFixture>();
    rr2 = new RaycastResult<Body, BodyFixture>();
    dr2.copy(dr);
    ccr2.copy(ccr);
    cdr2.copy(cdr);
    rr2.copy(rr);
    TestCase.assertEquals(dr2.getBody(), body);
    TestCase.assertEquals(dr2.getFixture(), bf);
    TestCase.assertEquals(ccr2.getBody(), body);
    TestCase.assertEquals(ccr2.getFixture(), bf);
    TestCase.assertEquals(ccr2.getTimeOfImpact().getTime(), ccr.getTimeOfImpact().getTime());
    TestCase.assertTrue(ccr2.getTimeOfImpact() != ccr.getTimeOfImpact());
    TestCase.assertEquals(cdr2.getBody(), body);
    TestCase.assertEquals(cdr2.getFixture(), bf);
    TestCase.assertEquals(cdr2.getPenetration().getDepth(), cdr.getPenetration().getDepth());
    TestCase.assertTrue(cdr2.getPenetration() != cdr.getPenetration());
    TestCase.assertEquals(rr2.getBody(), body);
    TestCase.assertEquals(rr2.getFixture(), bf);
    TestCase.assertEquals(rr2.getRaycast().getDistance(), rr.getRaycast().getDistance());
    TestCase.assertTrue(rr2.getRaycast() != rr.getRaycast());
}


TestFilePath: linkedin/PalDB/paldb/src/test/java/com/linkedin/paldb/impl/TestStorageSerialization.java
++++++ MethodDeclarationQualifiedSignature: com.linkedin.paldb.impl.TestStorageSerialization.testLong()
@Test
public void testLong() throws IOException, ClassNotFoundException {
    long[] vals = { Long.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE - 1, Integer.MIN_VALUE + 1, -Short.MIN_VALUE * 2, -Short.MIN_VALUE + 1, -Short.MIN_VALUE, -10, -9, -8, -7, -6, -5, -4, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 127, 254, 255, 256, Short.MAX_VALUE, Short.MAX_VALUE + 1, Short.MAX_VALUE * 2, Integer.MAX_VALUE, Integer.MAX_VALUE + 1, Long.MAX_VALUE };
    for (long i : vals) {
        byte[] buf = serialization.serialize(i);
        Object l2 = serialization.deserialize(buf);
        Assert.assertTrue(l2.getClass() == Long.class);
        Assert.assertEquals(l2, i);
    }
}


TestFilePath: terl/lazysodium-java/src/test/java/com/goterl/lazysodium/Ristretto255Test.java
++++++ MethodDeclarationQualifiedSignature: com.goterl.lazysodium.Ristretto255Test.randomScalar()
@Test
public void randomScalar() {
    BigInteger s1 = lazySodium.cryptoCoreRistretto255ScalarRandom();
    BigInteger s2 = lazySodium.cryptoCoreRistretto255ScalarRandom();
    BigInteger s3 = lazySodium.cryptoCoreRistretto255ScalarRandom();
    // all three scalars should be positive and non-equal
    assertFalse(s1.compareTo(BigInteger.ZERO) < 0);
    assertFalse(s2.compareTo(BigInteger.ZERO) < 0);
    assertFalse(s3.compareTo(BigInteger.ZERO) < 0);
    assertNotEquals(s1, s2);
    assertNotEquals(s1, s3);
    assertNotEquals(s2, s3);
}


TestFilePath: trinodb/trino/core/trino-main/src/test/java/io/trino/cost/TestStatisticRange.java
++++++ MethodDeclarationQualifiedSignature: io.trino.cost.TestStatisticRange.testAddAndCollapseDistinctValues()
@Test
public void testAddAndCollapseDistinctValues() {
    assertEquals(unboundedRange(NaN).addAndCollapseDistinctValues(unboundedRange(NaN)), unboundedRange(NaN));
    assertEquals(unboundedRange(NaN).addAndCollapseDistinctValues(unboundedRange(1)), unboundedRange(NaN));
    assertEquals(unboundedRange(1).addAndCollapseDistinctValues(unboundedRange(NaN)), unboundedRange(NaN));
    assertEquals(unboundedRange(1).addAndCollapseDistinctValues(unboundedRange(2)), unboundedRange(2));
    assertEquals(StatisticRange.empty().addAndCollapseDistinctValues(StatisticRange.empty()), StatisticRange.empty());
    assertEquals(range(0, 1, 1).addAndCollapseDistinctValues(StatisticRange.empty()), range(0, 1, 1));
    assertEquals(range(0, 1, 1).addAndCollapseDistinctValues(range(1, 2, 1)), range(0, 2, 1));
    assertEquals(range(0, 3, 3).addAndCollapseDistinctValues(range(2, 6, 4)), range(0, 6, 6));
}


TestFilePath: authorwlh/wlhbdp/bdp-olap/trino/core/trino-main/src/test/java/io/trino/cost/TestStatisticRange.java
++++++ MethodDeclarationQualifiedSignature: io.trino.cost.TestStatisticRange.testAddAndCollapseDistinctValues()
@Test
public void testAddAndCollapseDistinctValues() {
    assertEquals(unboundedRange(NaN).addAndCollapseDistinctValues(unboundedRange(NaN)), unboundedRange(NaN));
    assertEquals(unboundedRange(NaN).addAndCollapseDistinctValues(unboundedRange(1)), unboundedRange(NaN));
    assertEquals(unboundedRange(1).addAndCollapseDistinctValues(unboundedRange(NaN)), unboundedRange(NaN));
    assertEquals(unboundedRange(1).addAndCollapseDistinctValues(unboundedRange(2)), unboundedRange(2));
    assertEquals(StatisticRange.empty().addAndCollapseDistinctValues(StatisticRange.empty()), StatisticRange.empty());
    assertEquals(range(0, 1, 1).addAndCollapseDistinctValues(StatisticRange.empty()), range(0, 1, 1));
    assertEquals(range(0, 1, 1).addAndCollapseDistinctValues(range(1, 2, 1)), range(0, 2, 1));
    assertEquals(range(0, 3, 3).addAndCollapseDistinctValues(range(2, 6, 4)), range(0, 6, 6));
}


TestFilePath: JetBrains/Arend/src/test/java/org/arend/typechecking/constructions/DefCall.java
++++++ MethodDeclarationQualifiedSignature: org.arend.typechecking.constructions.DefCall.conStatic()
@Test
public void conStatic() {
    typeCheckModule("\\data D | c\n" + "\\func test => c");
    test(ConCall((Constructor) getDefinition("c"), Levels.EMPTY, Collections.emptyList()));
    assertEquals(getDefinition("c"), getDefinition("D.c"));
}


TestFilePath: authorwlh/wlhbdp/bdp-olap/trino/plugin/trino-hive/src/test/java/io/trino/plugin/hive/s3select/TestS3SelectRecordCursor.java
++++++ MethodDeclarationQualifiedSignature: io.trino.plugin.hive.s3select.TestS3SelectRecordCursor.shouldReturnOnlyQuantityColumnInTheDDl()
@Test
public void shouldReturnOnlyQuantityColumnInTheDDl() {
    String ddlSerializationValue = "struct article { varchar address varchar company date date_pub int quantity}";
    String expectedDDLSerialization = "struct article { int quantity}";
    assertEquals(buildSplitSchema(ddlSerializationValue, ARTICLE_COLUMN, QUANTITY_COLUMN), buildExpectedProperties(expectedDDLSerialization, ARTICLE_COLUMN, QUANTITY_COLUMN));
}


TestFilePath: gridgain/gridgain/modules/core/src/test/java/org/apache/ignite/internal/util/io/GridUnsafeDataInputOutputByteOrderSelfTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.ignite.internal.util.io.GridUnsafeDataInputOutputByteOrderSelfTest.testChar()
/**
 * @throws Exception If failed.
 */
@Test
public void testChar() throws Exception {
    char val = (char) RND.nextLong();
    out.writeChar(val);
    assertEquals(val, getCharByByteLE(out.internalArray()));
    assertEquals(val, in.readChar());
}


TestFilePath: apache/ignite/modules/core/src/test/java/org/apache/ignite/internal/util/io/GridUnsafeDataInputOutputByteOrderSelfTest.java
++++++ MethodDeclarationQualifiedSignature: org.apache.ignite.internal.util.io.GridUnsafeDataInputOutputByteOrderSelfTest.testChar()
/**
 * @throws Exception If failed.
 */
@Test
public void testChar() throws Exception {
    char val = (char) RND.nextLong();
    out.writeChar(val);
    assertEquals(val, getCharByByteLE(out.internalArray()));
    assertEquals(val, in.readChar());
}