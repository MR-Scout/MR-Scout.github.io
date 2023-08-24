NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
de.rub.nds.tlsattacker.core.state.StateTest_changingValidTlsContextInMultiContextStateSucceeds_AutoMR
original_MTC_FQS:
de.rub.nds.tlsattacker.core.state.StateTest.changingValidTlsContextInMultiContextStateSucceeds()
poj_dir:
tls-attacker/TLS-Attacker/
original_MTC_class_path:
tls-attacker/TLS-Attacker/TLS-Core/src/test/java/de/rub/nds/tlsattacker/core/state/StateTest.java

# Codified MR:
```java
public void changingValidTlsContextInMultiContextStateSucceeds_AutoMR(String conAlias1) {
    WorkflowTrace trace = new WorkflowTrace();
    String conAlias2 = "con2";
    trace.addConnection(new OutboundConnection(conAlias1));
    trace.addConnection(new InboundConnection(conAlias2));
    State state = new State(trace);
    TlsContext origCtx1 = state.getTlsContext(conAlias1);
    TlsContext newCtx = new TlsContext();
    newCtx.setConnection(origCtx1.getConnection());
    origCtx1.setSelectedCipherSuite(CipherSuite.TLS_FALLBACK_SCSV);
    newCtx.setSelectedCipherSuite(CipherSuite.TLS_AES_128_CCM_SHA256);
    state.replaceTlsContext(newCtx);
    assertNotSame(state.getTlsContext(conAlias1), origCtx1);
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertNotSame(state.getTlsContext(conAlias1), origCtx1), line: 162 
source_input: ['conAlias1'], first_invoked_method_name: getTlsContext, line: 154 
followUp_input: ['conAlias1'], second_invoked_method_name: getTlsContext, line: 162 


# Original MTC and related fields:
```java

@Test
public void initWithoutWorkflowTraceFailsProperly() {
    Config config = Config.createConfig();
    config.setWorkflowInput(null);
    config.setWorkflowTraceType(null);
    exception.expect(ConfigurationException.class);
    exception.expectMessage("Could not load workflow trace");
    State s = new State(config);
}
@Test
public void initFromGoodConfig() {
    String expected = "testInitFromConfig";
    Config config = Config.createConfig();
    config.setWorkflowTraceType(WorkflowTraceType.SHORT_HELLO);
    config.setDefaultApplicationMessageData(expected);
    State s = new State(config);
    assertNotNull(s.getConfig());
    assertEquals(s.getConfig(), config);
    assertNotNull(s.getWorkflowTrace());
    assertNotNull(s.getTlsContext());
    assertEquals(config.getDefaultApplicationMessageData(), expected);
    // TODO: assertThat(workflowTrace.getType(),
    // isEqual(WorkflowTraceType.SHORT_HELLO));
}
@Test
public void initFromConfigAndWorkflowTrace() {
    String expected = "testInitFromConfig";
    Config config = Config.createConfig();
    config.setDefaultApplicationMessageData(expected);
    WorkflowTrace trace = new WorkflowTrace();
    State s = new State(config, trace);
    assertNotNull(s.getConfig());
    assertEquals(s.getConfig(), config);
    assertEquals(config.getDefaultApplicationMessageData(), expected);
    assertNotNull(s.getWorkflowTrace());
    assertNotNull(s.getTlsContext());
    assertEquals(s.getTlsContext().getConnection(), trace.getConnections().get(0));
}
@Test
public void changingValidTlsContextInMultiContextStateSucceeds() {
    WorkflowTrace trace = new WorkflowTrace();
    String conAlias1 = "con1";
    String conAlias2 = "con2";
    trace.addConnection(new OutboundConnection(conAlias1));
    trace.addConnection(new InboundConnection(conAlias2));
    State state = new State(trace);
    TlsContext origCtx1 = state.getTlsContext(conAlias1);
    TlsContext newCtx = new TlsContext();
    newCtx.setConnection(origCtx1.getConnection());
    origCtx1.setSelectedCipherSuite(CipherSuite.TLS_FALLBACK_SCSV);
    newCtx.setSelectedCipherSuite(CipherSuite.TLS_AES_128_CCM_SHA256);
    assertThat(state.getTlsContext(conAlias1).getSelectedCipherSuite(), equalTo(CipherSuite.TLS_FALLBACK_SCSV));
    state.replaceTlsContext(newCtx);
    assertNotSame(state.getTlsContext(conAlias1), origCtx1);
    assertThat(state.getTlsContext(conAlias1).getSelectedCipherSuite(), equalTo(CipherSuite.TLS_AES_128_CCM_SHA256));
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 2
 
## comments (if any): 
```txt
cannnot understand the logic of the CUt.
```
