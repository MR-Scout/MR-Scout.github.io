NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.hellokaton.blade.kit.HashidsTest_test_large_number_AutoMR
original_MTC_FQS:
com.hellokaton.blade.kit.HashidsTest.test_large_number()
poj_dir:
lets-blade/blade/
original_MTC_class_path:
lets-blade/blade/blade-kit/src/test/java/com/hellokaton/blade/kit/HashidsTest.java

# Codified MR:
```java
public void test_large_number_AutoMR(long num_to_hash, Hashids a) {
    final String res = a.encode(num_to_hash);
    final long[] b = a.decode(res);
    Assert.assertEquals(num_to_hash, b[0]);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: Assert.assertEquals(num_to_hash, b[0]), line: 25 
source_input: ['num_to_hash', 'a'], first_invoked_method_name: encode, line: 23 
followUp_input: ['res', 'a'], second_invoked_method_name: decode, line: 24 


# Original MTC and related fields:
```java


@Test
public void test_large_number() {
    final long num_to_hash = 9007199254740992L;
    final Hashids a = new Hashids("this is my salt");
    final String res = a.encode(num_to_hash);
    final long[] b = a.decode(res);
    Assert.assertEquals(num_to_hash, b[0]);
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 4
 
## comments (if any): 
```txt

```