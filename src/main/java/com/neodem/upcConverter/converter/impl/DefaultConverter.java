package com.neodem.upcConverter.converter.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.neodem.upcConverter.converter.Converter;
import com.neodem.upcConverter.converter.ConverterException;
import com.neodem.upcConverter.converter.NotFoundException;
import com.neodem.upcConverter.converter.XMLException;


/**
 * uses an XML-RPC service to get the description
 * of the UPC
 * 
 * @author Vince
 *
 */
public class DefaultConverter implements Converter {

	private static final String URL = "http://www.upcdatabase.com/rpc";
	private static final String METHOD = "lookupEAN";
	
	private XmlRpcClient client;
	
	public DefaultConverter() throws MalformedURLException {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(URL));
		
		client = new XmlRpcClient();
		client.setConfig(config);
	}
	
	public String getDescription(String upc) throws ConverterException {
		if(StringUtils.isBlank(upc)) {
			throw new IllegalArgumentException("upc may not be blank");
		}
		if(!StringUtils.isNumeric(upc)) {
			throw new IllegalArgumentException("upc must be numeric");
		}
		if(upc.length() != 12) {
			throw new IllegalArgumentException("upc must be exactly 12 digits");
		}

		return process("0" + upc);
	}
	
	private String process(String upc) throws NotFoundException, XMLException {
		Object[] params = new Object[] { upc };
		Map<?, ?> result;
		try {
			result = (Map<?, ?>) client.execute(METHOD, params);
		} catch (XmlRpcException e) {
			throw new XMLException("XML issue :" + e.getMessage());
		}
		Map<String, Object> struct = convertMap(result);
		
		Boolean found = (Boolean) struct.get("found");
		if(found.booleanValue()) {
			return (String) struct.get("description");
		} else {
			throw new NotFoundException(upc);			
		}
	}
	
	private Map<String, Object> convertMap(Map<?, ?> result) {
		Map<String, Object> c = new HashMap<String, Object>();
		Set<?> keys = result.keySet();
		for (Object key : keys) {
			c.put((String)key, result.get(key));
		}
		
		return c;
	}
}
