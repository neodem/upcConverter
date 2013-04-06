package com.neodem.upcConverter.util;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class GetHelp {

	/**
	 * @param args
	 * @throws MalformedURLException
	 * @throws XmlRpcException
	 */
	public static void main(String[] args) throws MalformedURLException, XmlRpcException {

		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("http://www.upcdatabase.com/rpc"));

		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		String result = (String) client.execute("help", new Object[] {});

		System.out.println(result);

	}

}
