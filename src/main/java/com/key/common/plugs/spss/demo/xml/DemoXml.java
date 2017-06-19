package com.key.common.plugs.spss.demo.xml;

import java.io.*;

import com.key.common.plugs.spss.demo.csv.DemoCsv;
import com.pmstation.spss.*;

public class DemoXml {
  public static void main(String[] args){
    // Parse the command line
    if (args.length != 2) {
        printUsageAndExit();
    }

    // Create files from 2 command line parameters
    File fileXml = new File(args[0]);
    File fileSPSS = new File(args[1]);

    // Do convert from XML to SPSS format
    try {
      XmlToSPSS.convert(fileXml, fileSPSS, null);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  private static void printUsageAndExit() {
    System.err.println("Usage:");
    System.err.println("java " + DemoCsv.class.getName() +
        " <fileXML> <fileSPSS>");

    System.exit(1);
  }
}
