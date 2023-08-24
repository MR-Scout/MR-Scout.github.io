NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
org.apache.ignite.ml.math.primitives.matrix.LUDecompositionTest_getL_AutoMR
original_MTC_FQS:
org.apache.ignite.ml.math.primitives.matrix.LUDecompositionTest.getL()
poj_dir:
apache/ignite/
original_MTC_class_path:
apache/ignite/modules/ml/src/test/java/org/apache/ignite/ml/math/primitives/matrix/LUDecompositionTest.java

# Codified MR:
```java
/**
 */
public void getL_AutoMR(int i, int j) throws Exception {
    Matrix luDecompositionL = new LUDecomposition(testMatrix).getL();
    assertEquals("Unexpected value at (" + i + "," + j + ").", testL.getX(i, j), luDecompositionL.getX(i, j), 0.0000001d);
    luDecompositionL.destroy();
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance3
relation_assertion: assertEquals(testL.getX(i, j), luDecompositionL.getX(i, j), 0.0000001d), line: 92 
source_input: ['i', 'j'], first_invoked_method_name: getX, line: 93 
followUp_input: ['i', 'j'], second_invoked_method_name: getX, line: 93 


# Original MTC and related fields:
```java
/**
 */
private Matrix testL;
/**
 */
private Matrix testMatrix;
/**
 */
@Before
public void setUp() {
    double[][] rawMatrix = new double[][] { { 2.0d, 1.0d, 1.0d, 0.0d }, { 4.0d, 3.0d, 3.0d, 1.0d }, { 8.0d, 7.0d, 9.0d, 5.0d }, { 6.0d, 7.0d, 9.0d, 8.0d } };
    double[][] rawL = { { 1.0d, 0.0d, 0.0d, 0.0d }, { 3.0d / 4.0d, 1.0d, 0.0d, 0.0d }, { 1.0d / 2.0d, -2.0d / 7.0d, 1.0d, 0.0d }, { 1.0d / 4.0d, -3.0d / 7.0d, 1.0d / 3.0d, 1.0d } };
    double[][] rawU = { { 8.0d, 7.0d, 9.0d, 5.0d }, { 0.0d, 7.0d / 4.0d, 9.0d / 4.0d, 17.0d / 4.0d }, { 0.0d, 0.0d, -6.0d / 7.0d, -2.0d / 7.0d }, { 0.0d, 0.0d, 0.0d, 2.0d / 3.0d } };
    double[][] rawP = new double[][] { { 0, 0, 1.0d, 0 }, { 0, 0, 0, 1.0d }, { 0, 1.0d, 0, 0 }, { 1.0d, 0, 0, 0 } };
    rawPivot = new int[] { 3, 4, 2, 1 };
    testMatrix = new DenseMatrix(rawMatrix);
    testL = new DenseMatrix(rawL);
    testU = new DenseMatrix(rawU);
    testP = new DenseMatrix(rawP);
}
/**
 */
@Test
public void getL() throws Exception {
    Matrix luDecompositionL = new LUDecomposition(testMatrix).getL();
    assertEquals("Unexpected row size.", testL.rowSize(), luDecompositionL.rowSize());
    assertEquals("Unexpected column size.", testL.columnSize(), luDecompositionL.columnSize());
    for (int i = 0; i < testL.rowSize(); i++) for (int j = 0; j < testL.columnSize(); j++) assertEquals("Unexpected value at (" + i + "," + j + ").", testL.getX(i, j), luDecompositionL.getX(i, j), 0.0000001d);
    luDecompositionL.destroy();
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```