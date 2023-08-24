NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.salesforce.dva.argus.entity.NotificationTest_testSetSubject_AutoMR
original_MTC_FQS:
com.salesforce.dva.argus.entity.NotificationTest.testSetSubject()
poj_dir:
salesforce/Argus/
original_MTC_class_path:
salesforce/Argus/ArgusCore/src/test/java/com/salesforce/dva/argus/entity/NotificationTest.java

# Codified MR:
```java
public void testSetSubject_AutoMR(String subject, Notification testNotification) {
    testNotification.setEmailSubject(subject);
    assertEquals(subject, testNotification.getEmailSubject());
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertEquals(subject, testNotification.getEmailSubject()), line: 46 
source_input: ['subject', 'testNotification'], first_invoked_method_name: setEmailSubject, line: 45 
followUp_input: ['testNotification'], second_invoked_method_name: getEmailSubject, line: 46 


# Original MTC and related fields:
```java


@Test
public void testSetSubject() {
    String subject = "TEST_SUBJECT";
    Notification testNotification = new Notification();
    testNotification.setEmailSubject(subject);
    assertEquals(subject, testNotification.getEmailSubject());
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 0
 
## comments (if any): 
```txt

```