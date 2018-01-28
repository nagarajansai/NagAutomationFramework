package com.automation.report;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigFileReadWrite {
	private static final Logger LOG = Logger.getLogger(ConfigFileReadWrite.class);

	public synchronized static String read(String fileName, String key) {
		String keyValue = null;

		BufferedReader bufferedReader = null;
		Properties prop = null;

		try {

			bufferedReader = new BufferedReader(new FileReader(fileName));
			prop = new Properties();
			prop.load(bufferedReader);
			keyValue = prop.getProperty(key);
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} catch (NullPointerException e) {

		} finally {
			LOG.debug("Inside finally");
			try {
				bufferedReader.close();
				LOG.debug("BufferedReader(bufferedReader) Instance Closed");
			} catch (IOException e) {

			} catch (NullPointerException e) {

			}
		}
		return keyValue;
	}

}
