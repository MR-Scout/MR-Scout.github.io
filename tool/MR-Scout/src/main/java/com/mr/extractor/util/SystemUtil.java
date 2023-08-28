package com.mr.extractor.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class SystemUtil {
    

    public static int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return Integer.valueOf(  runtimeMXBean.getName().split("@")[0] ).intValue() ;
    }
}
