package com.neodem.upcConverter;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.upcConverter.processor.DefaultProcessor;

public class ConvertUPC {

	protected static Log LOG = LogFactory.getLog(ConvertUPC.class.getName());
	
	public ConvertUPC() {
		DefaultProcessor p = new DefaultProcessor();
		File dir = new File("/tmp");
		File in = new File(dir, "input.csv");
		File out = new File(dir, "output.csv");
		LOG.debug("starting");
		p.process(in, out);
		LOG.debug("complete");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ConvertUPC();
	}

}
