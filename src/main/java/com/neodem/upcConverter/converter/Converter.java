package com.neodem.upcConverter.converter;



public interface Converter {
	public String getDescription(String upc) throws ConverterException;
}
