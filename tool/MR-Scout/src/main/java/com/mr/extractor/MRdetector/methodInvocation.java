package com.mr.extractor.MRdetector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.mr.extractor.Parser;

import static java.util.stream.Collectors.toList;

import javassist.Loader.Simple;

public class methodInvocation implements java.io.Serializable{
    transient public MethodCallExpr mce = null;
    public String mceQualifiedSignature = null;
    public String invocationStatement = null;
    public HashMap<String, List<String>> Input_object_is_accessed_or_updated = new HashMap<String, List<String>>();
    public List<String> Input_object_is_accessed_or_updated_Analyzed = new ArrayList<>();


    // input
    public int lineNumberBegin = -1;
    public int lineNumberEnd = -1;
    public String objectReceiverName = null;
    transient Node objectReceiverNode = null;
    // String objectReceiverClassFQN = null;
    public List<String> inputArgumentsNames = new ArrayList<>();
    public List<String> inputNonPrimitiveArgumentsNames = new ArrayList<>();
    public List<String> inputArgumentInvolvedValuesOrVaribles = new ArrayList<>(); // Involved Values Or Varibles
    transient NodeList inputArguementsNodes = null;
    // HashMap<String, String> inputParameters_TypeMap = new HashMap<String, String>();
    public List<String> inputExpressions = new ArrayList<>();// 总
    public List<String> inputInvolvedValuesOrVaribles = new ArrayList<>();// 总 (input Involved Values Or Varibles)
    // HashMap<String, String> inputVariable_TypeMap = new HashMap<String, String>();
    // output
    public boolean invocationInAssertion;
    public String returnValueVariableName = null;
    public String returnValueVariableType = null;
    public List<String> outputExpressions = new ArrayList<>();// 总

    public methodInvocation(MethodCallExpr mce, String mceQualifiedSignature){
        // System.out.println( "methodInvocation: --------"+mce );
        // System.out.println( "methodInvocationSTMT: " + mce.getParentNode().get() );

        this.mce = mce;
        this.mceQualifiedSignature = mceQualifiedSignature;
        this.lineNumberBegin = mce.getRange().get().begin.line;
        this.lineNumberEnd = mce.getRange().get().end.line;
        this.Input_object_is_accessed_or_updated.put("u", new ArrayList<>());
        this.Input_object_is_accessed_or_updated.put("a", new ArrayList<>());
        this.getInputs(mce);
        this.getOutputs(mce);

        // System.out.println( "this.inputParametersNames: " + this.inputParametersNames );
        // System.out.println( "this.objectReceiverName: " + this.objectReceiverName );
        // System.out.println( "this.returnValueVariableName: " + this.returnValueVariableName );
        // System.out.println( "this.inputExpressions: " + this.inputExpressions );
        // System.out.println( "this.outputVariablesNames: " + this.outputExpressions );
    }

    /* TODO
     * get parameters: mce.getArguments()
     * get object receiver:  firstToken = methodInvocation.getChildNodes().get(0), firstToken != invoked class name
     *  - get all variable in this method
     *  - get varible in this statement which is not inputs and (not before  "=", followed by "." ).
     *  or
     *  - get the "variable" before "." (e.g., a.b().c().d() , a is the target)
    */
    void getInputs(MethodCallExpr mce){
        // System.out.println( "mce: " + mce  );
        // System.out.println( "mce.getClass(): " + mce.getClass()  );
        // System.out.println( "mce.getArguments(): " + mce.getArguments()  );
        // System.out.println( "mce.getDataKeys(): " + mce.getDataKeys()  );
        // System.out.println( "mce.getParentNode(): " + mce.getParentNode()  );
        // System.out.println( "mce.getParentNodeForChildren(): " + mce.getParentNodeForChildren()  );
        // System.out.println( "mce.getParentNodeForChildren().getChildNodes(): " + mce.getParentNodeForChildren().getChildNodes() );

        // get parameters: mce.getArguments()
        this.inputArguementsNodes = mce.getArguments();
        for (Object inputParametersNode : this.inputArguementsNodes) {
            // System.out.println(mce.findAll(NameExpr.class));
            // System.out.println( "mce: " + mce );
            // System.out.println( "inputParametersNode: " + inputParametersNode +" "+inputParametersNode.getClass());
            this.inputArgumentsNames.add(  inputParametersNode.toString() );

            // // get InvolvedValuesOrVaribles
            // // repository.findCustomNotes("Fix"): "Fix": class com.github.javaparser.ast.expr.StringLiteralExpr
            // if(inputParametersNode instanceof com.github.javaparser.ast.expr.StringLiteralExpr){
            //     this.inputArgumentInvolvedValuesOrVaribles.add(inputParametersNode.toString());
            // }
            // // _chartService.updateChart(chart);  chart: com.github.javaparser.ast.expr.NameExpr
            // if(inputParametersNode instanceof com.github.javaparser.ast.expr.NameExpr){
            //     this.inputArgumentInvolvedValuesOrVaribles.add(inputParametersNode.toString());
            // }

            // if(inputParametersNode instanceof com.github.javaparser.ast.expr.FieldAccessExpr){
            //     List<NameExpr> nameExprs = ((FieldAccessExpr)inputParametersNode).findAll(NameExpr.class);
            //     System.out.println("nameExprs: "+nameExprs);
            //     for (NameExpr nameExpr : nameExprs) {
            //         this.inputArgumentInvolvedValuesOrVaribles.add(nameExpr.toString());
            //     }
            // }
            // // _chartService.getChartByPrimaryKey(chart.getId());  chart.getId(): com.github.javaparser.ast.expr.MethodCallExpr
            // if(inputParametersNode instanceof com.github.javaparser.ast.expr.MethodCallExpr){
            //     List<NameExpr> nameExprs = ((MethodCallExpr)inputParametersNode).findAll(NameExpr.class);
            //     for (NameExpr nameExpr : nameExprs) {
            //         this.inputArgumentInvolvedValuesOrVaribles.add(nameExpr.toString());
            //     }
            // }
            // mce.findAll(NameExpr.class);
        }

        // PrimitiveType, Arguments
        MethodDeclaration declarationOfInovkedMce = Parser.getMethodDeclarationFromMethodCallExpr(mce);
        Parameter parameter=null;
        NodeList parametersOfInovkedMceDeclaration = declarationOfInovkedMce.getParameters();
        for (int index = 0; index < parametersOfInovkedMceDeclaration.size(); index++) {
            parameter = (Parameter) parametersOfInovkedMceDeclaration.get(index);
            if(!(parameter.getType() instanceof PrimitiveType) ) {
                String realArgumentName = this.inputArgumentsNames.get(index);
                this.inputNonPrimitiveArgumentsNames.add(realArgumentName);
            }
        }

        /* get object receiver: */
        Node methodInvocation = mce.getParentNodeForChildren(); // method invocation
        Node firstToken = methodInvocation.getChildNodes().get(0); // get the first token, which could be the object receiver. OctetUtil.bytesToShort(bytes): [OctetUtil, bytesToInt, bytes]
        // check the first token is variable or class name?
        if (firstToken.getClass().getSimpleName().equals("NameExpr")){ 
            // com.github.javaparser.ast.expr.NameExpr 表示类名或变量名, class com.github.javaparser.ast.expr.SimpleName 表示调用的方法名
            boolean firstTokenInMQS = this.mceQualifiedSignature.contains("." + firstToken + ".");
            if (!firstTokenInMQS) { // 说明不是invoked method's class name, consider it as variables
                this.objectReceiverName = firstToken.toString();
                this.objectReceiverNode = firstToken;
            }
        }
        else if(firstToken.getClass().getSimpleName().equals("FieldAccessExpr") || firstToken.getClass().getSimpleName().equals("MethodCallExpr")){
            // StatusCodes.Bad_SecurityChecksFailed.intValue(), StatusCodes.Bad_SecurityChecksFailed: FieldAccessExpr
            // code2.getValue().intValue(), code2.getValue(): MethodCallExpr
            this.objectReceiverName = firstToken.toString();
            this.objectReceiverNode = firstToken;
        }
        // limitation: aMap.map(Integers.negate).toString()中invoked method 是toString(), getChildNodes: [aMap.map(Integers.negate), toString]。结果： 无objectReceiverName。 // org.highj.data.MapTest.testMap()


        /* decide: which is the input
         * Input parameters are directly considered as inputs.
         * The object receiver is considered as one input, only if the object receiver’s fields are accessed in the method under analysis or in the transitive methods which are called by the method under analysis.
        */
        // Input parameters are directly considered as inputs. 
        if( this.inputArgumentsNames.size()>0 ){
            this.inputExpressions.addAll( this.inputArgumentsNames ); 
        }
        // The object receiver is considered as one input, only if the object receiver’s fields are accessed in the method under analysis or in the transitive methods which are called by the method under analysis.
        if( this.objectReceiverName != null ){
            if(!this.Input_object_is_accessed_or_updated_Analyzed.contains(objectReceiverName) ){
                // exe
                HashMap<String, Boolean> accessed_or_updated = input_object_is_accessed_or_updated_in_method( mce, objectReceiverName);

                // write result
                this.Input_object_is_accessed_or_updated_Analyzed.add(objectReceiverName); 
                if(accessed_or_updated.get("a"))
                    this.Input_object_is_accessed_or_updated.get("a").add(objectReceiverName);
                if(accessed_or_updated.get("u"))
                    this.Input_object_is_accessed_or_updated.get("u").add(objectReceiverName);
            }
            // judge
            if(this.Input_object_is_accessed_or_updated_Analyzed.contains(objectReceiverName) && this.Input_object_is_accessed_or_updated.get("a").contains(objectReceiverName)){
                this.inputExpressions.add( this.objectReceiverName );
                this.inputInvolvedValuesOrVaribles.add( this.objectReceiverName );
            }
                
        } 
        
    }

    /* 
     * get return value:
     *   - directly get the variable before "="
     * if in assertion, methodInvocation as output expression.
    */
    void getOutputs(MethodCallExpr mce){
        Node methodInvocation = mce.getParentNodeForChildren(); // method invocation
        Node methodInvocationSTMT = mce.getParentNode().get(); // statement? No, just parent node.
        
        // 说明是assign 语句
        if(methodInvocationSTMT.toString().contains("=") && !(methodInvocationSTMT.toString().contains("==")) ){ // assigment should existing with "="
            for (Node child : methodInvocationSTMT.getChildNodes()) {
                if( child.getClass().getSimpleName().equals("SimpleName") || child.getClass().getSimpleName().equals("NameExpr") ){// not xxxType, or MethodCallExpr 应该就可
                    if(methodInvocationSTMT.toString().split("=")[0].contains(child.toString()) ){ // 在 = 左边
                        this.returnValueVariableName = child.toString();
                        break;
                    }
                }
            }
            // SimpleName VS. NameExpr HostAddress address3 = HostAddress.fromParts("[1111:2222:3333:4444:5555:6666:7777:8888]", 1235)
            // SimpleName(): [HostAddress, address3, HostAddress, fromParts], NameExpr(): [HostAddress]
        }
        else{
            /* invocationInAssertion？ */
            // when methodInvocationSTMT or methodInvocationSTMT.parent do not have ";"。 一直递归向上找，直到methodInvocationSTMT.parent contains(";")停
            while(!methodInvocationSTMT.toString().contains(";") && !methodInvocationSTMT.getParentNode().get().toString().contains(";") )
                methodInvocationSTMT = methodInvocationSTMT.getParentNode().get(); // statement

            if(methodInvocationSTMT.toString().contains("assert")){ // assert中，直接把invocationInAssertion 作为returnValue expression
                this.invocationInAssertion = true;
                // this.returnValueVariableName = methodInvocation.toString();
            }
        }
        

        /* decide: which is the output 
         * invocationInAssertion, only consider the invocation expression as the output.
         * 
         * The return value is directly considered as an output.
         * The object receiver is considered as one output, only if the object receiver’s fields are updated in the method under analysis or in the transitive methods which are called by the method under analysis.
         * The input object reference is considered as outputs, only if the object’s fields are updated in the method under analysis or in the transitive methods which are called by the method under analysis.
        */
        
        // nvocationInAssertion, consider the invocation expression as the output.
        if( this.invocationInAssertion ){
            this.outputExpressions.add( methodInvocation.toString() );
        }
        else{
            // The return value is directly considered as an output.
            if( this.returnValueVariableName!= null ){
                this.outputExpressions.add( this.returnValueVariableName ); 
            }
            // The object receiver is considered as one output, only if the object receiver’s fields are updated in the method under analysis or in the transitive methods which are called by the method under analysis.
            if( this.objectReceiverName != null ){
                if(!this.Input_object_is_accessed_or_updated_Analyzed.contains(objectReceiverName) ){
                    // exe
                    HashMap<String, Boolean> accessed_or_updated = input_object_is_accessed_or_updated_in_method( mce, objectReceiverName);
    
                    // write result
                    this.Input_object_is_accessed_or_updated_Analyzed.add(objectReceiverName); 
                    if(accessed_or_updated.get("a"))
                        this.Input_object_is_accessed_or_updated.get("a").add(objectReceiverName);
                    if(accessed_or_updated.get("u"))
                        this.Input_object_is_accessed_or_updated.get("u").add(objectReceiverName);
                }
                // judge
                if(this.Input_object_is_accessed_or_updated_Analyzed.contains(objectReceiverName) && this.Input_object_is_accessed_or_updated.get("u").contains(objectReceiverName))
                    this.outputExpressions.add( this.objectReceiverName ); 
            }
            // The input object reference is considered as outputs, only if the object’s fields are updated in the method under analysis or in the transitive methods which are called by the method under analysis.
            if( this.inputArgumentsNames.size()>0 ){
                // 1 find object references (which are  not primitive type)
                // this.inputPrimitiveArgumentsNames;
                // 2. exe analyzer and judge the result.
                for (String inputNonPrimitiveArgument : this.inputNonPrimitiveArgumentsNames) {
                    if(!this.Input_object_is_accessed_or_updated_Analyzed.contains(inputNonPrimitiveArgument) ){
                        // exe
                        HashMap<String, Boolean> accessed_or_updated = input_object_is_accessed_or_updated_in_method( mce, inputNonPrimitiveArgument);
        
                        // write result
                        this.Input_object_is_accessed_or_updated_Analyzed.add(inputNonPrimitiveArgument); 
                        if(accessed_or_updated.get("a"))
                            this.Input_object_is_accessed_or_updated.get("a").add(inputNonPrimitiveArgument);
                        if(accessed_or_updated.get("u"))
                            this.Input_object_is_accessed_or_updated.get("u").add(inputNonPrimitiveArgument);
                    }
                    // judge
                    if(this.Input_object_is_accessed_or_updated_Analyzed.contains(inputNonPrimitiveArgument) && this.Input_object_is_accessed_or_updated.get("u").contains(inputNonPrimitiveArgument))
                        this.outputExpressions.add( inputNonPrimitiveArgument ); 
                }
            }
        }
        // methodInvocationSTMT: assertEquals(OctetUtil.bytesToShort(new byte[] { (byte) 0xff, (byte) 0xff }), -1)
        // this.returnValueVariableName: null
    }


    /* 
     * 1. get the body of the invoked method
     * 2. mapping input object name to the parameter name (called "OB") in this method
     * 3. accessed or updated in this method?
     * 4. with transitive method invocation?
     * 
    */
    static HashMap<String, Boolean> input_object_is_accessed_or_updated_in_method( MethodCallExpr mce, String input_object_name){
        // System.out.println( "mce + mce.getChildNodes(): " + mce + mce.getChildNodes() );
        // System.out.println("/* 1. get the body of the invoked method */");
        HashMap<String, Boolean> accessed_or_updated = new HashMap<String, Boolean>();
        accessed_or_updated.put("u",false); accessed_or_updated.put("a", false);

        /* 1. get the body of the invoked method */
        MethodDeclaration methodDeclarationOfInovkedMce = Parser.getMethodDeclarationFromMethodCallExpr(mce);
        if(methodDeclarationOfInovkedMce==null) return accessed_or_updated; // parser not succeed
        if(!methodDeclarationOfInovkedMce.getBody().isPresent()) return accessed_or_updated; // no body
        BlockStmt MDBody = methodDeclarationOfInovkedMce.getBody().get();
    
        // System.out.println("/* 2. mapping input object name to the parameter name (called 'OB') in this method */");
        /* 2. mapping input object name to the parameter name (called "OB") in this method */
        Parameter formalParameter; String formalParameterName="formalParameterName"; String formalParameterType="formalParameterType";
        Boolean inputObjectIsObjectReceiver = false;
        if(mce.toString().startsWith(input_object_name+".")){
            // input is "object receiver? "
            formalParameterName = "this"; inputObjectIsObjectReceiver = true;
        }
        else{
            int parameterIndex = -1;
            Expression expression;
            NodeList<Expression>  mceArguments = mce.getArguments();
            for (int i = 0; i < mceArguments.size(); i++) {
                expression = mceArguments.get(i);
                if(expression.toString().equals(input_object_name)){
                    parameterIndex = i;break;
                }
            }
            if(parameterIndex==-1)
                System.out.println("parameterIndex==-1: ");
            else{
                formalParameter = methodDeclarationOfInovkedMce.getParameter(parameterIndex);formalParameterName = formalParameter.getNameAsString();
            }
        }
        // System.out.println("formalParameterName: " + formalParameterName);

        // System.out.println("/* 3. accessed or updated in this method? */");
        /* 3. accessed or updated in this method? 
         * find all fieldAccessExprs which contains input object, check:
         *   fieldAccessExprSTMT中无"=", ->    "is accessed"
         *   fieldAccessExprSTMT中有"=", and input object before “=” ->    "is updated"
         *   fieldAccessExprSTMT中有"=", and input object after “=” ->    "is accessed"
        */
        List<FieldAccessExpr> fieldAccessExprs = MDBody.findAll(FieldAccessExpr.class);
        // List<AssignExpr> assignExprs =  MDBody.findAll(AssignExpr.class);// 这个不好用，直接就找不到。。。

        if( fieldAccessExprs.toString().contains(formalParameterName + ".") ){
            for (FieldAccessExpr fieldAccessExpr : fieldAccessExprs) {
                if(fieldAccessExpr.getChildNodes().get(0).toString().equals(formalParameterName) ){ // 该FieldAccessExpr，包含 input——object
                    Node fieldAccessExprSTMT = Parser.getTheSTMT(fieldAccessExpr);
                    if(!fieldAccessExprSTMT.toString().contains("=")){
                        accessed_or_updated.put("a", true);
                    }
                    else{
                        if(fieldAccessExprSTMT.toString().split("=")[0].contains(fieldAccessExpr.toString()) ){ // fieldAccessExprSTMT中有"=", 且，input object在“=”左边
                        // System.out.println("is updated");
                        accessed_or_updated.put("u", true);
                        }
                        if(fieldAccessExprSTMT.toString().split("=")[1].contains(fieldAccessExpr.toString()) ){ // fieldAccessExprSTMT中有"=", 且，input object在“=”右边
                            // System.out.println("is accessed");
                            accessed_or_updated.put("a", true);
                        }
                    }
                }
            }
            // fieldAccessExprs: [otherPoint.latitude, this.latitude, otherPoint.longitude, this.longitude, this.latitude, otherPoint.latitude]
            // fieldAccessExpr.getNameAsString():latitude
            // fieldAccessExpr.getChildNodes():[otherPoint, latitude]

        }
        if(accessed_or_updated.get("u") && accessed_or_updated.get("a"))  return accessed_or_updated;
        // System.out.println("111111 fieldAccessExprs: " + fieldAccessExprs);
        
        /* 4. with transitive method invocation?
         * find all mce, check: 
         *    the object receiver is formalParameterName?
         *    one of the real parameters is formalParameterName?
        */
        List<MethodCallExpr> mcesInMD = MDBody.findAll(MethodCallExpr.class);
        HashMap<String, Boolean> accessed_or_updated_sub = new HashMap<String, Boolean>();; 
        accessed_or_updated_sub.put("a", false);accessed_or_updated_sub.put("u", false);
        for (MethodCallExpr mceInMD : mcesInMD) {
            /*    the object receiver is formalParameterName? */
            if(mceInMD.getChildNodes().get(0).toString().equals(formalParameterName)){
                System.out.println("DIGUI: " + formalParameterName + ", " + mceInMD );
                accessed_or_updated_sub = input_object_is_accessed_or_updated_in_method( mceInMD, formalParameterName);
                accessed_or_updated.put("a",accessed_or_updated.get("a")| accessed_or_updated_sub.get("a"));
                accessed_or_updated.put("u",accessed_or_updated.get("u")| accessed_or_updated_sub.get("u"));
                if(accessed_or_updated.get("u") && accessed_or_updated.get("a"))  return accessed_or_updated;
            }
            /*    one of the real parameters is formalParameterName? */
            else{
                NodeList<Expression> mceInMD_Arguments =  mceInMD.getArguments();
                for (Expression argument : mceInMD_Arguments) {
                    if(argument.toString().equals(formalParameterName)) {
                        System.out.println("DIGUI: " + formalParameterName + ", " + mceInMD );
                        input_object_is_accessed_or_updated_in_method( mceInMD, formalParameterName);
                        accessed_or_updated.put("a",accessed_or_updated.get("a")| accessed_or_updated_sub.get("a"));
                        accessed_or_updated.put("u",accessed_or_updated.get("u")| accessed_or_updated_sub.get("u"));
                        if(accessed_or_updated.get("u") && accessed_or_updated.get("a"))  return accessed_or_updated;
                    }
                }
            }
            
        }

        /* one possible:  
         * 5. when just access fields but not update the fields, there will be no "this." expr which cannot be identified as fieldAccessExprs
         * In this case, there is only field accessing (no update), becase updating must have "xxx.field" which will be identified as fieldAccessExprs
         */
        if(accessed_or_updated.get("a"))  return accessed_or_updated;
        /* step 1 get the varibles declared as the fields of class */
        // 1-1 get the class of the invoked method
        ClassOrInterfaceDeclaration classDeclarationOfInovkedMce = Parser.getClassDeclarationFromMethodDeclaration(methodDeclarationOfInovkedMce);
        List<FieldDeclaration> fieldDeclarations= classDeclarationOfInovkedMce.getFields();
        List<SimpleName> declaredFieldsSimpleNames= new ArrayList<>();
        for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
            declaredFieldsSimpleNames.add(fieldDeclaration.getVariables().get(0).getName());
        }
        /* step 2 get the nameExpr in this method body  */
        List<NameExpr> NameExprsInMDBody = MDBody.findAll(NameExpr.class);
        /* step 3 if the intersection is not empty.  */
        List<String> NameExprsInMDBodyString = new ArrayList<>();
        for (NameExpr NameExprInMDBody : NameExprsInMDBody)
            NameExprsInMDBodyString.add(NameExprInMDBody.toString());
        List<String> declaredFieldsSimpleNamesString = new ArrayList<>();
        for (SimpleName declaredFieldsSimpleName : declaredFieldsSimpleNames)
            declaredFieldsSimpleNamesString.add(declaredFieldsSimpleName.toString());
        // 交集： https://juejin.cn/post/6844903833726894093
        List<String> intersection = NameExprsInMDBodyString.stream().filter(item -> declaredFieldsSimpleNamesString.contains(item)).collect(toList());
        if( intersection.size()>0 )
            accessed_or_updated.put("a",true);

        return accessed_or_updated;
    }

}
