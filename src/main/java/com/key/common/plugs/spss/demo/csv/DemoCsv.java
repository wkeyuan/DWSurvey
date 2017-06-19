package com.key.common.plugs.spss.demo.csv;

import java.io.*;
import com.pmstation.spss.*;

public class DemoCsv {
  public static void main(String[] args){
    // Parse the command line
    if (args.length != 2) {
        printUsageAndExit();
    }

    // Create files from 2 command line parameters
    File fileCSV = new File(args[0]);
    File fileSPSS = new File(args[1]);

    // Do convert from CSV to SPSS format
    try {
      CsvToSPSS.convert(fileCSV, fileSPSS, null);
    } catch (Exception e){
      e.printStackTrace();
    }
  }
  
  private static void printUsageAndExit() {
    System.err.println("Usage:");
    System.err.println("java " +
        DemoCsv.class.getName() +
        " <fileCSV> <fileSPSS>");

    System.exit(1);
  }
}
