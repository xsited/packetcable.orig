package org.pcmm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PCMM Properties loader
 * 
 */
public class PCMMProperties {

	private static Properties properties;
	private static Logger logger = LoggerFactory
			.getLogger(PCMMProperties.class);

	static {
		// this requires JAVA 7 +
		try (InputStream in = new FileInputStream("pcmm.properties")) {
			properties = new Properties();
			properties.load(in);
		} catch (IOException ie) {
			logger.error(ie.getMessage());
		}
	}

	public static Properties getProperties() {
		return properties;
	}
	
	

}
