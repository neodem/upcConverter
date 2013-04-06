
package com.neodem.upcConverter.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.upcConverter.bean.CD;
import com.neodem.upcConverter.converter.Converter;
import com.neodem.upcConverter.converter.ConverterException;
import com.neodem.upcConverter.converter.impl.DefaultConverter;

public class DefaultProcessor {

	protected static Log LOG = LogFactory.getLog(DefaultProcessor.class.getName());

	private Converter converter;

	public DefaultProcessor() {
		try {
			converter = new DefaultConverter();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * will read a csv file, process some of its fields and write out to another
	 * csv file
	 * 
	 * @param inputFile
	 * @param outputFile
	 */
	public void process(File inputFile, File outputFile) {
		BufferedReader in = null;
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(outputFile));
			in = new BufferedReader(new FileReader(inputFile));
			String line = null;
			while ((line = in.readLine()) != null) {
				CD cd = makeFromInputLine(line);
				String upc = cd.getUpcCode();
				String description = null;
				try {
					LOG.debug("converting " + upc);
					description = converter.getDescription(upc);
				} catch (ConverterException e) {
					LOG.error("skipped '" + upc + "' due to : " + e.getMessage());
					continue;
				}
				addDescription(description, cd);
				writeOutputLine(out, cd);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private CD makeFromInputLine(String line) {
		String[] tokens = StringUtils.split(line, ',');
		CD cd = new CD();
		cd.setUpcCode(tokens[0]);
		cd.setRating(tokens[1]);
		
		return cd;
	}

	private void writeOutputLine(BufferedWriter out, CD cd) throws IOException {
		out.write(cd.getUpcCode());
		out.write(',');
		out.write(cd.getArtist());
		out.write(',');
		out.write(cd.getAlbumTitle());
		out.write(',');
		out.write(cd.getRating());
		out.write('\n');
	}

	private void addDescription(String desc, CD cd) {
		String artist = getLeft(desc);
		String title = getRight(desc);
		cd.setAlbumTitle(title);
		cd.setArtist(artist);
	}

	private String getLeft(String description) {
		if (description == null) {
			return "";
		}
		if ("".equals(description)) {
			return "";
		}
		int index = description.indexOf('-');
		if (index == -1) {
			return description;
		}
		return description.substring(0, index - 1);
	}

	private String getRight(String description) {
		if (description == null) {
			return "";
		}
		if ("".equals(description)) {
			return "";
		}
		int index = description.indexOf('-');
		if (index == -1) {
			return description;
		}
		return description.substring(index + 2);
	}

}
