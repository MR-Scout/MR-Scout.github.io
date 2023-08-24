TestFilePath: apache__split__freemarker/src/test/java/freemarker/template/utility/StringUtilTest.java
++++++ MethodDeclarationQualifiedSignature: freemarker.template.utility.StringUtilTest.testXHTMLEnc()
@Test
public void testXHTMLEnc() throws IOException {
    String s = "";
    assertSame(s, StringUtil.XHTMLEnc(s));
    s = "asd";
    assertSame(s, StringUtil.XHTMLEnc(s));
    testXHTMLEnc("a&amp;b&lt;c&gt;d&quot;e&#39;f", "a&b<c>d\"e'f");
    testXHTMLEnc("&lt;", "<");
    testXHTMLEnc("&lt;a", "<a");
    testXHTMLEnc("&lt;a&gt;", "<a>");
    testXHTMLEnc("a&gt;", "a>");
    testXHTMLEnc("&lt;&gt;", "<>");
    testXHTMLEnc("a&lt;&gt;b", "a<>b");
}


TestFilePath: FINRAOS__split__herd/herd-code/herd-service/src/test/java/org/finra/herd/service/helper/BusinessObjectDataHelperTest.java
++++++ MethodDeclarationQualifiedSignature: org.finra.herd.service.helper.BusinessObjectDataHelperTest.getStorageUnitByStorageName()
@Test
public void getStorageUnitByStorageName() {
    // Create business object data with several test storage units.
    BusinessObjectData businessObjectData = new BusinessObjectData();
    List<String> testStorageNames = Arrays.asList("Storage_1", "storage-2", "STORAGE3");
    List<StorageUnit> storageUnits = new ArrayList<>();
    businessObjectData.setStorageUnits(storageUnits);
    for (String testStorageName : testStorageNames) {
        StorageUnit storageUnit = new StorageUnit();
        storageUnits.add(storageUnit);
        Storage storage = new Storage();
        storageUnit.setStorage(storage);
        storage.setName(testStorageName);
    }
    // Validate that we can find all storage units regardless of the storage name case.
    for (String testStorageName : testStorageNames) {
        assertEquals(testStorageName, businessObjectDataHelper.getStorageUnitByStorageName(businessObjectData, testStorageName).getStorage().getName());
        assertEquals(testStorageName, businessObjectDataHelper.getStorageUnitByStorageName(businessObjectData, testStorageName.toUpperCase()).getStorage().getName());
        assertEquals(testStorageName, businessObjectDataHelper.getStorageUnitByStorageName(businessObjectData, testStorageName.toLowerCase()).getStorage().getName());
    }
}


TestFilePath: amzn__split__ion-java/test/com/amazon/ion/facet/FacetsTest.java
++++++ MethodDeclarationQualifiedSignature: com.amazon.ion.facet.FacetsTest.testAsFacetOfObject()
@Test
public void testAsFacetOfObject() {
    Object subject = null;
    assertSame(null, asFacet(Span.class, subject));
    subject = new Base();
    assertSame(null, asFacet(Unrelated.class, subject));
    // Cast
    assertSame(subject, asFacet(Object.class, subject));
    // Cast
    assertSame(subject, asFacet(Base.class, subject));
    subject = new Mid();
    assertSame(null, asFacet(Object.class, subject));
    assertSame(null, asFacet(Unrelated.class, subject));
    assertSame(null, asFacet(Faceted.class, subject));
    assertSame(null, asFacet(Base.class, subject));
    assertSame(null, asFacet(Span.class, subject));
    assertSame(i, asFacet(Number.class, subject));
    subject = new Bottom();
    assertSame(null, asFacet(Object.class, subject));
    assertSame(null, asFacet(Unrelated.class, subject));
    assertSame(null, asFacet(Faceted.class, subject));
    assertSame(null, asFacet(Base.class, subject));
    assertSame(null, asFacet(Span.class, subject));
    assertSame(i, asFacet(Number.class, subject));
}


TestFilePath: eclipse__split__eclipse-collections/unit-tests/src/test/java/org/eclipse/collections/impl/collection/mutable/primitive/AbstractBooleanIterableTestCase.java
++++++ MethodDeclarationQualifiedSignature: org.eclipse.collections.impl.collection.mutable.primitive.AbstractBooleanIterableTestCase.toArray()
@Test
public void toArray() {
    Assert.assertEquals(0L, this.newWith().toArray().length);
    Assert.assertTrue(Arrays.equals(new boolean[] { true }, this.newWith(true).toArray()));
    Assert.assertTrue(Arrays.equals(new boolean[] { false, true }, this.newWith(true, false).toArray()) || Arrays.equals(new boolean[] { true, false }, this.newWith(true, false).toArray()));
}