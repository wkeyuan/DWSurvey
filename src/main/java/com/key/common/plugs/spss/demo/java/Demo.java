package com.key.common.plugs.spss.demo.java;

import java.io.*;
import com.pmstation.spss.*;

public class Demo {

  // Entrance point
  public static void main(String args[]) {
    try {
    	String fileName="F:\\source code\\spss\\spssw-1.66\\spss\\test.csv";
      // Parse the command line
//      if (args.length != 1) {
//          printUsageAndExit();
//      }

      // Open file output stream with filename args[0]
      OutputStream out = new FileOutputStream(fileName);

      // Assign SPSS output to the file
      SPSSWriter outSPSS = new SPSSWriter(out, "gbk");

      // Creating SPSS variable description table
      outSPSS.setCalculateNumberOfCases(false);
      outSPSS.addDictionarySection(-1);

      // Describing varible names and types
      outSPSS.addStringVar("cont", 32, "continents of the world");
      outSPSS.addNumericVar("size", 8, 2, "sq km");
      outSPSS.addNumericVar("pop", 8, 2, "population");

      // Create missing value 
      MissingValue mv = new MissingValue();
      mv.setOneDescreteMissingValue(1);
      outSPSS.addNumericVar("count", 8, 2, "number of countries", mv);

      // Create value labels
      ValueLabels valueLabels = new ValueLabels();
      valueLabels.putLabel(44, "Forty four");
      valueLabels.putLabel(23, "Twenty three");
      outSPSS.addValueLabels(4 , valueLabels);

      // Create SPSS varible value define table
      outSPSS.addDataSection();

      // Add values for all defined variables
      outSPSS.addData("Asia");
      outSPSS.addData(new Long(44579000L));
      outSPSS.addData(new Long(3674000000L));
      outSPSS.addData(new Long(44));

      outSPSS.addData("Africa");
      outSPSS.addData(new Long(30065000L));
      outSPSS.addData(new Long(778000000L));
      outSPSS.addData(new Long(53));

      outSPSS.addData("North America");
      outSPSS.addData(new Long(24256000L));
      outSPSS.addData(new Long(483000000L));
      outSPSS.addData(new Long(23));

      outSPSS.addData("South America");
      outSPSS.addData(new Long(17819000L));
      outSPSS.addData(new Long(342000000L));
      outSPSS.addData(new Long(12));

      outSPSS.addData("Antarctica");
      outSPSS.addData(new Long(13209000L));
      outSPSS.addData(new Long(0));
      outSPSS.addData(new Long(0));

      outSPSS.addData("Europe");
      outSPSS.addData(new Long(9938000L));
      outSPSS.addData(new Long(732000000L));
      outSPSS.addData(new Long(46));

      outSPSS.addData("Australia/Oceania");
      outSPSS.addData(new Long(7687000L));
      outSPSS.addData(new Long(31000000L));
      outSPSS.addData(new Long(14));

      outSPSS.addData("Transelvania");
      outSPSS.addData(new Long(7345560L));
      outSPSS.addData(new Long(34565456L));
      outSPSS.addData(new Long(44));

      // Create SPSS ending section
      outSPSS.addFinishSection();

      // Close output stream
      out.close();
    }
    catch (FileNotFoundException exOb) {
      System.out.println("FileNotFoundException (Demo.main): " +
          exOb.getMessage());
      exOb.printStackTrace(System.out);
      return;
    }
    catch (IOException exOb) {
      System.out.println("IOException (Demo.main): " + exOb.getMessage());
      exOb.printStackTrace(System.out);
      return;
    }
  }

  private static void printUsageAndExit() {
    System.err.println("Usage:");
    System.err.println("java " +
        Demo.class.getName() +
        " <fileSPSS>");
    System.exit(1);
  }
}
