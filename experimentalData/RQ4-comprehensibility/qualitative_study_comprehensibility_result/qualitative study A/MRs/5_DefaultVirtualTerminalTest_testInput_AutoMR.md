NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
com.googlecode.lanterna.terminal.virtual.DefaultVirtualTerminalTest_testInput_AutoMR
original_MTC_FQS:
com.googlecode.lanterna.terminal.virtual.DefaultVirtualTerminalTest.testInput()
poj_dir:
mabe02/lanterna/
original_MTC_class_path:
mabe02/lanterna/src/test/java/com/googlecode/lanterna/terminal/virtual/DefaultVirtualTerminalTest.java

# Codified MR:
```java
public void testInput_AutoMR(KeyStroke keyStroke1, DefaultVirtualTerminal virtualTerminal, KeyStroke keyStroke2) {
    virtualTerminal.addInput(keyStroke1);
    virtualTerminal.addInput(keyStroke2);
    assertEquals(keyStroke1, virtualTerminal.pollInput());
    assertEquals(keyStroke2, virtualTerminal.readInput());
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertEquals(keyStroke1, virtualTerminal.pollInput()), line: 454 
source_input: ['keyStroke1', 'virtualTerminal'], first_invoked_method_name: addInput, line: 452 
followUp_input: ['virtualTerminal'], second_invoked_method_name: pollInput, line: 454 
## MR instance2
relation_assertion: assertEquals(keyStroke2, virtualTerminal.readInput()), line: 455 
source_input: ['keyStroke2', 'virtualTerminal'], first_invoked_method_name: addInput, line: 453 
followUp_input: ['virtualTerminal'], second_invoked_method_name: readInput, line: 455 


# Original MTC and related fields:
```java
private final DefaultVirtualTerminal virtualTerminal;
public DefaultVirtualTerminalTest() {
        this.virtualTerminal = new DefaultVirtualTerminal();
    }
@Test
public void initialTerminalStateIsAsExpected() {
    assertEquals(TerminalPosition.TOP_LEFT_CORNER, virtualTerminal.getCursorPosition());
    TerminalSize terminalSize = virtualTerminal.getTerminalSize();
    assertEquals(new TerminalSize(80, 24), terminalSize);
    for (int row = 0; row < terminalSize.getRows(); row++) {
        for (int column = 0; column < terminalSize.getColumns(); column++) {
            assertEquals(DEFAULT_CHARACTER, virtualTerminal.getCharacter(column, row));
        }
    }
}
@Test
public void testInput() {
    KeyStroke keyStroke1 = new KeyStroke('A', false, false);
    KeyStroke keyStroke2 = new KeyStroke('B', false, false);
    virtualTerminal.addInput(keyStroke1);
    virtualTerminal.addInput(keyStroke2);
    assertEquals(keyStroke1, virtualTerminal.pollInput());
    assertEquals(keyStroke2, virtualTerminal.readInput());
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 3
 
## comments (if any): 
```txt

```