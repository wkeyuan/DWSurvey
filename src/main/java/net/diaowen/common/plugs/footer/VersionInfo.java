package net.diaowen.common.plugs.footer;

import net.diaowen.dwsurvey.config.DWSurveyConfig;

public class VersionInfo {

    public static String getVersionInfo() {
        return DWSurveyConfig.DWSURVEY_VERSION_INFO;
    }

    public static String getVersionBuilt() {
        return DWSurveyConfig.DWSURVEY_VERSION_BUILT;
    }

    public static String getVersionNumber() {
        return DWSurveyConfig.DWSURVEY_VERSION_NUMBER;
    }

    public static void main(String args[]) {
        System.out.println("Server version: " + getVersionInfo());
        System.out.println("Server built:   " + getVersionBuilt());
        System.out.println("Server number:  " + getVersionNumber());
        System.out.println("OS Name:        " +
                           System.getProperty("os.name"));
        System.out.println("OS Version:     " +
                           System.getProperty("os.version"));
        System.out.println("Architecture:   " +
                           System.getProperty("os.arch"));
        System.out.println("JVM Version:    " +
                           System.getProperty("java.runtime.version"));
        System.out.println("JVM Vendor:     " +
                           System.getProperty("java.vm.vendor"));
    }

}
