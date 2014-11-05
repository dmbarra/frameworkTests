package frameworkTests.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadMessage {
	private static Properties properties;


	/**
	 * return message of file
	 */
	public static String converterMensagem(String codeMessage){
		codeMessage = codeMessage.replaceAll(" ", "");
		codeMessage = codeMessage.replaceAll("m", "M");
		return properties.getProperty(codeMessage, null);
	}

	/**
	 * fixing message inserting correct values 
	 */
	public static String concatenarMensagem(String message, String value){
		return message.replaceAll(message.substring(message.indexOf("<"), message.indexOf(">")+1), value);
	}

	/**
	 * fixing message inserting correct values 
	 */
	public static String concatenarCodigoMensagem(String message, String code){
		StringBuilder stringBuilder = new StringBuilder(code);  
		stringBuilder.insert(code.length() - 1, '-');  
		stringBuilder.insert(code.length() - 5, '-'); 
		return message.replaceAll(message.substring(message.indexOf("<"), message.indexOf(">")+1), stringBuilder.toString());
	}

	static {
		loadMensagens();
	}

	/**
	 * Load archive of properties
	 */
	public static void loadMensagens() {
		try {
			properties = new Properties();
			InputStream in = LoadMessage.class
					.getResourceAsStream(ConfigTestManager.getMensagemConfig());
			if (in != null) {
				properties.load(in);
				in.close();
				System.out.println(ConfigTestManager.getMensagemConfig() + " loaded!");
			} else {
				final File file = new File(ConfigTestManager.getUserConfig() + ConfigTestManager.getMensagemConfig());
				if (!file.exists()) {
					System.err.println("Can't to load properties file: "+ ConfigTestManager.getMensagemConfig());

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
}
