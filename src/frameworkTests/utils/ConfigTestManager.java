package frameworkTests.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigTestManager {
	/**
	 * 
	 * 	@since  2014/09/29
	 *  @author Daniel Barra
	 */

	private static String userConfig = System.getProperty("user.dir")+"\\config\\";
	private static String fileNameConfig = "config.properties";
	private static String mensagemConfig = "messages.properties";

	private static Properties properties;

	static {
		loadProperties();
	};

	//--

	/**
	 * Load archive of properties
	 *
	 */
	public static void loadProperties() {
		try {
			properties = new Properties();
			InputStream in = ConfigTestManager.class	.getResourceAsStream(fileNameConfig);
			if (in != null) {
				properties.load(in);
				in.close();
				System.out.println(fileNameConfig + " loaded!");
			} else {
				final File file = new File(userConfig + fileNameConfig);
				if (!file.exists()) {
					System.err.println("Can't to load properties file: "+ fileNameConfig);
				} else {
					in = new FileInputStream(file);
					properties.load(in);
					in.close();
				}
			}

		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	//XML
	
	private static final String directoryDefaultDataDriver = System.getProperty("user.dir")+"\\xml\\";
	private static final String extensionDefaultDateDriver = ".xml";

	public static String getDirectoryDefaultDataDriver() {
		return directoryDefaultDataDriver;
	}
	
	public static String getExtensionDefaultDateDriver() {
		return extensionDefaultDateDriver;
	}
	
	//Recuperando diretorios
	public static String getUserConfig() {
		return userConfig;
	}

	public static String getMensagemConfig() {
		return mensagemConfig;
	}

	//Recuperando dados Para BD via arquivo de configuracao;
	public static String getdbUsername() {
		return properties.getProperty("dbUsername", null);
	}
	public static String getdbPassword() {
		return properties.getProperty("dbPassword", null);
	}
	public static String getdbHostServ() {
		return properties.getProperty("dbHostserv", null);
	}
	public static String getdbSchema() {
		return properties.getProperty("dbSchema", null);
	}
	public static String getdbDrive() {
		return properties.getProperty("dbDrive", null);
	}
	public static String getdbConnecturl() {
		return properties.getProperty("getdbConecturl", null);
	}

	//Recuperando dados do modo de teste
	public static String getTypeBrowser() {
		return properties.getProperty("browser", null);
	}
	
	//Recuperando parametro especificado
	public static String getConfig(String config) {
		return properties.getProperty(config, null);
	}
}
