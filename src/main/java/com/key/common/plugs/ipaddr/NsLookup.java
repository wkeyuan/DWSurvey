package com.key.common.plugs.ipaddr;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NsLookup {

	public static void main(String[] args) {
		String name="www.sun.com";
		try {
			InetAddress address = InetAddress.getByName(name);
			System.out.println(name + " : " + address.getHostAddress());
		}catch (UnknownHostException uhe) {
			System.err.println("Unable to find: " + name);
		}
	}
}
