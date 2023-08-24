NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.salesforce.dva.argus.entity.NotificationTest_testSetSubscriptionsForEmailValidationForValidMails_AutoMR
original_MTC_FQS:
com.salesforce.dva.argus.entity.NotificationTest.testSetSubscriptionsForEmailValidationForValidMails()
poj_dir:
salesforce/Argus/
original_MTC_class_path:
salesforce/Argus/ArgusCore/src/test/java/com/salesforce/dva/argus/entity/NotificationTest.java

# Codified MR:
```java
public void testSetSubscriptionsForEmailValidationForValidMails_AutoMR(Notification testNotification, List<String> validSubscriptions) {
    testNotification.setNotifierName(AlertService.SupportedNotifier.EMAIL.getName());
    validSubscriptions.add("foo@company.com");
    validSubscriptions.add("bar@com.pany.com");
    validSubscriptions.add("first.mid.last@company.com");
    validSubscriptions.add("first_mid.last@com.pany.co.in");
    testNotification.setSubscriptions(validSubscriptions);
    assertEquals(validSubscriptions, testNotification.getSubscriptions());
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertEquals(validSubscriptions, testNotification.getSubscriptions()), line: 27 
source_input: ['validSubscriptions', 'testNotification'], first_invoked_method_name: setSubscriptions, line: 26 
followUp_input: ['testNotification'], second_invoked_method_name: getSubscriptions, line: 27 


# Original MTC and related fields:
```java


@Test
public void testSetSubscriptionsForEmailValidationForValidMails() {
    Notification testNotification = new Notification();
    testNotification.setNotifierName(AlertService.SupportedNotifier.EMAIL.getName());
    List<String> validSubscriptions = new ArrayList<>();
    validSubscriptions.add("foo@company.com");
    validSubscriptions.add("bar@com.pany.com");
    validSubscriptions.add("first.mid.last@company.com");
    validSubscriptions.add("first_mid.last@com.pany.co.in");
    testNotification.setSubscriptions(validSubscriptions);
    assertEquals(validSubscriptions, testNotification.getSubscriptions());
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt
not a very good generalization: other relevant or non-relevant statements.
```