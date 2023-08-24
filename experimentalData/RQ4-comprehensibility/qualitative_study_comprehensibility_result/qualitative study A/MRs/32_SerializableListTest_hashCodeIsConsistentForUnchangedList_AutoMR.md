NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
org.nem.core.serialization.SerializableListTest_hashCodeIsConsistentForUnchangedList_AutoMR
original_MTC_FQS:
org.nem.core.serialization.SerializableListTest.hashCodeIsConsistentForUnchangedList()
poj_dir:
NemProject/nem.core/
original_MTC_class_path:
NemProject/nem.core/src/test/java/org/nem/core/serialization/SerializableListTest.java

# Codified MR:
```java
// endregion
// region hashCode / equals
public void hashCodeIsConsistentForUnchangedList_AutoMR(SerializableList<MockSerializableEntity> list) {
    final int hashCode = list.hashCode();
    // Assert:
    MatcherAssert.assertThat(list.hashCode(), IsEqual.equalTo(hashCode));
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: MatcherAssert.assertThat(list.hashCode(), IsEqual.equalTo(hashCode)), line: 321 
source_input: ['list'], first_invoked_method_name: hashCode, line: 318 
followUp_input: ['list'], second_invoked_method_name: hashCode, line: 321 


# Original MTC and related fields:
```java


// endregion
// region hashCode / equals
@Test
public void hashCodeIsConsistentForUnchangedList() {
    // Arrange:
    final SerializableList<MockSerializableEntity> list = new SerializableList<>(10);
    final int hashCode = list.hashCode();
    // Assert:
    MatcherAssert.assertThat(list.hashCode(), IsEqual.equalTo(hashCode));
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 4
 
## comments (if any): 
```txt

```