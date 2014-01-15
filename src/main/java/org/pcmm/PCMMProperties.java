package org.pcmm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads the PCMM Properties file.
 * 
 */
public class PCMMProperties {

	private static Properties properties;
	private static Logger logger = LoggerFactory
			.getLogger(PCMMProperties.class);

	static {
		try {
			InputStream in = PCMMProperties.class.getClassLoader()
					.getResourceAsStream("pcmm.properties");
			properties = new Properties();
			properties.load(in);
			in.close();
		} catch (IOException ie) {
			logger.error(ie.getMessage());
		}
	}

	public static String get(String key) {
		return properties.getProperty(key);
	}
	
}
