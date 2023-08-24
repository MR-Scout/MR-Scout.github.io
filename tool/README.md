# MR-Scout Prototype

**MR-Scout** is an automated approach to automatically discover and synthesize metamorphic relations from the test cases in open-source software (OSS) projects. 

MR-Scout works in three phases: MTC discovery, MR Synthesis, and MR Filtering.
Firstly, MR-Scout discovers MTCs based on the defined properties. 
Secondly, with discovered MTCs, MR-Scout deduces the constituents (e.g., source and follow-up inputs) of encoded MRs and then codifies these constituents into parameterized methods to facilitate automated test case generation. These parameterized methods are termed as "codified MR".
Finally, MR-Scout filters out codified MRs that demonstrate poor quality in applying to new test inputs.


### Environment Configuration 

* Java: 11.0.18
* Evosuite: 1.2.0


### Quick Start
Let us take the [dapr/java-sdk](https://github.com/dapr/java-sdk) project as an example to demonstrate the usage of MR-Scout.jar.
1. Download this project

   ~~~cmd
   $ git clone https://github.com/dapr/java-sdk.git dapr__split__java-sdk
   ~~~

2. Run MR-Scout: MTC Discovery
    
    Navigate to [dapr/java-sdk](https://github.com/dapr/java-sdk) directory and execute the MTC Identifier using the following commands:

    ```cmd
   $ cd $project_dir$; java -cp MR-Scout.jar com.mr.extractor.Main $project_name$ $project_dir$
   ```
    Example: 
   ```cmd
   $ cd dapr__split__java-sdk/;java -cp MR-Scout.jar com.mr.extractor.Main dapr__split__java-sdk ./
   ```

   Output:
   * identified MTC will be stored in JSON files under `$project_dir$AutoMR/MTidentifier/`.

   Example: 
   in the file `$project_dir$AutoMR/MTidentifier/io.dapr.utils.DurationUtilsTest.convertTimeBothWays__split__0.json`
   * fully-qulified signature of this MTC 
   `"FQS_testCaseUnderAnalysis":"io.dapr.utils.DurationUtilsTest.convertTimeBothWays()"`
   * the test file of this MTC
   `"Path_testClassUnderAnalysis":"dapr__split__java-sdk/sdk/src/test/java/io/dapr/utils/DurationUtilsTest.java"`
   * the code snippet of this MTC 
   ``` java
    @Test
    public void convertTimeBothWays() {
        String s = "4h15m50s60ms";
        Duration d1 = DurationUtils.convertDurationFromDaprFormat(s);
        String t = DurationUtils.convertDurationToDaprFormat(d1);
        Assert.assertEquals(s, t);
    }
   ```
3. Run MR-Scout: MR Synthesis

    Construct codified MRs using the following commands:

    ```cmd
   $ cd $project_dir$; java -cp MR-Scout.jar com.mr.extractor.MRgenerator.executableMRGenerator2 $MTC_file_path$ $project_dir$ $signature_of_MTC$
   ```
    Example: 
   ```cmd
   $ cd dapr__split__java-sdk/;java -cp MR-Scout.jar com.mr.extractor.MRgenerator.executableMRGenerator2 "sdk/src/test/java/io/dapr/utils/DurationUtilsTest.java" "./" "io.dapr.utils.DurationUtilsTest.convertTimeBothWays()"
   ```
   Output:
   * Codified MR will be stored in the file `sdk/src/test/java/io/dapr/utils/DurationUtilsTest_convertTimeBothWays_AutoMR.java`.
   * the fully-qulified name of generate codified MR class
    `io.dapr.utils.DurationUtilsTest_convertTimeBothWays_AutoMR`
   * the code snippet of this codified MR method
   ```java
    public void convertTimeBothWays_AutoMR(String s) {
        Duration d1 = DurationUtils.convertDurationFromDaprFormat(s);
        String t = DurationUtils.convertDurationToDaprFormat(d1);
        Assert.assertEquals(s, t);
    }
   ```
4. Run MR-Scout: MR Filtering
    * run Evosutie to generate new inputs for codified MRs. Please refer to the doc of [Evosuite](https://www.evosuite.org/documentation/tutorial-part-1/).

    * **Evosuite Configuration.** 
    We set the following flags while retaining the default values for other flags which can be found [here](https://github.com/MR-Scout/MR-Scout.github.io/blob/main/experimentalData/EvosuiteParameters.xlsx). 

    ``` java
    -seed hash(str(i_execution)) // i_execution: number of execution, i.e., 1,2,3 ....., 100 
    -Dnull_probability=0.1 
    -Dcatch_undeclared_exceptions=false
    -Dminimize=false 
    -Dcheck_contracts=true
    -Dcriterion=LINE:BRANCH
    -Dalgorithm=RANDOM_SEARCH 
    -Dassertions=false
    ```
 

### Update

If you have any questions or issues, please feel free to report an issue. We will continue to maintain this project. Thanks for your feedback😄. 
