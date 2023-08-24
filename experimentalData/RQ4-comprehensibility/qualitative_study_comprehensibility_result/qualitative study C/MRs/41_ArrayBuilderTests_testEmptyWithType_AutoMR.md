NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.zavtech.morpheus.array.ArrayBuilderTests_testEmptyWithType_AutoMR
original_MTC_FQS:
com.zavtech.morpheus.array.ArrayBuilderTests.testEmptyWithType(java.lang.Class<?>)
poj_dir:
zavtech/morpheus-core/
original_MTC_class_path:
zavtech/morpheus-core/src/test/java/com/zavtech/morpheus/array/ArrayBuilderTests.java

# Codified MR:
```java
public void testEmptyWithType_AutoMR(Class<?> type, int input0) {
    final ArrayBuilder<?> builder = ArrayBuilder.of(input0, type);
    final Array<?> empty = builder.toArray();
    Assert.assertTrue(empty.typeCode() == ArrayType.of(type), "The array types match");
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: Assert.assertTrue(empty.typeCode() == ArrayType.of(type), "The array types match"), line: 87 
source_input: ['10', 'type'], first_invoked_method_name: of, line: 84 
followUp_input: ['builder'], second_invoked_method_name: toArray, line: 85 


# Original MTC and related fields:
```java


@Test(dataProvider = "types")
public void testEmptyWithType(Class<?> type) {
    final ArrayBuilder<?> builder = ArrayBuilder.of(10, type);
    final Array<?> empty = builder.toArray();
    Assert.assertTrue(empty.length() == 0, "The array is zero length");
    Assert.assertTrue(empty.typeCode() == ArrayType.of(type), "The array types match");
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 4
 
## comments (if any): 
```txt

```
