package com.mr.extractor.MRdetector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.mr.extractor.MTIdentifier;
import com.mr.extractor.Parser;
import com.mr.extractor.config.Config;
import com.mr.extractor.util.SystemUtil;

public class MRInstancesDetector {

    public static void main(String[] args) throws ClassNotFoundException, IOException, CloneNotSupportedException {
        String PID = ""+SystemUtil.getProcessID();
        System.out.println( "PID_tool_main_start:" + PID + ":PID_end" );
        String methodToInvokeName=null; String POJ_DIR_PATH; String FILE_PATH; String MDName;
        Config config = new Config();
        if (args.length>0){
            methodToInvokeName = args[0];
            POJ_DIR_PATH = args[1];
            FILE_PATH = args[2];
            MDName = args[3];
        }
        else{
            methodToInvokeName = "detectWithTMD";
            FILE_PATH = config.DIR_DATA + "AutoMR/projects/gridgain__split__gridgain/modules/core/src/test/java/org/apache/ignite/internal/util/io/GridUnsafeDataInputOutputByteOrderSelfTest.java";
            POJ_DIR_PATH = config.DIR_DATA + "AutoMR/projects/gridgain__split__gridgain/";
            MDName = "testChar";
        }
        if(methodToInvokeName.equals("detectWithTMD"))
            detectWithTMD(POJ_DIR_PATH, FILE_PATH, MDName);
    }

    public static void detectWithFilePath() {
    }

    public static void detectWithTMD(String POJ_DIR_PATH, String FILE_PATH, String MDName) throws ClassNotFoundException, IOException , CloneNotSupportedException{
        // 在这里调用 MTidentifier? 找出所有MR instances?
        String Pattern = "M&A";
        MTIdentifier.MTIdentifierPattern_invoke( FILE_PATH,  POJ_DIR_PATH, Pattern, MDName);
    }
}
