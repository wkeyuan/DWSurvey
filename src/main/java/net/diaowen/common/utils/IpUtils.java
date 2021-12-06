package net.diaowen.common.utils;

public class IpUtils {

    public static String[] getIps(String ips){
        String[] ip2 = ips.split("/");
        String ip0 = ip2[0];
        String ip1 = ip0.substring(0,ip0.lastIndexOf(".")+1)+ip2[1];
        return new String[]{ip0,ip1};
    }

    public static long getIpNum(String ipAddress){
        String[] ip = ipAddress.split("\\.");
        long a = Integer.parseInt(ip[0]);
        long b = Integer.parseInt(ip[1]);
        long c = Integer.parseInt(ip[2]);
        long d = Integer.parseInt(ip[3]);

        return a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
    }


}
