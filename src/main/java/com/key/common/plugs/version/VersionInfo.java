package com.key.common.plugs.version;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class VersionInfo {


    private static final String versionInfo;

    private static final String versionBuilt;

    private static final String versionNumber;

    static {

        String info = null;
        String built = null;
        String number = null;

        Properties props = new Properties();

        try {
            InputStream is = VersionInfo.class.getResourceAsStream("/com/key/common/plugs/version/VersionInfo.properties");

            if(is!=null){
                props.load(is);
                info = props.getProperty("version.info");
                built = props.getProperty("version.built");
                number = props.getProperty("version.number");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (info == null || info.equals("DWSurvey OSS @VERSION@"))
            info = "DWSurvey OSS V3.1.0-dev";
        if (built == null || built.equals("@VERSION_BUILT@"))
            built = "unknown";
        if (number == null || number.equals("@VERSION_NUMBER@"))
            number = "3.1.x";

        versionInfo = info;
        versionBuilt = built;
        versionNumber = number;
    }

    public static String getVersionInfo() {
        return versionInfo;
    }

    public static String getVersionBuilt() {
        return versionBuilt;
    }

    public static String getVersionNumber() {
        return versionNumber;
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
