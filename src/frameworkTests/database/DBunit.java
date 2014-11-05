package frameworkTests.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;


/**
 * A classe permite trabalhar diretamente com o banco de dados,
 * permite manipular tabelas no banco de dados através de importação 
 * de arquivos XML
 * 
 * @author frederico.moreira
 *
 */

public class DBunit {
	private static String dbURL 	  		= null;
	private static String dbUsername 		= null;
	private static String dbPassword 		= null;
	private static String dbDriver 			= null;

	static Connection jdbcConnection		= null;
	private static IDatabaseConnection connection	= null;

	/**
	 * Este <b>método</b> monta o driver de acordo com o tipo de banco
	 * 
	 * @param	 driver Driver de acorodo com o tipo de banco
	 * @return	 jdbcDriver Retorna o driver no formato correto para ser usado na conexão
	 */
	private static String getJdbcDriver(String driver){		
		String jdbcDriver = null;
		if (driver.equals("oracle.jdbc.driver.OracleDriver"))
			jdbcDriver = "jdbc:oracle:thin:@";
		
		return jdbcDriver;
	}

	/**
	 * Este <b>Método</b> configura a conexão e conecta ao banco configurado.
	 * 
	 * @param driver			 &nbsp;Driver a ser usado na conexão
	 * @param serverName		 &nbsp;Nome do Servidor para conexão
	 * @param schema			 &nbsp;Schema do banco da conexão
	 * @param username			 &nbsp;Usuário para conexão com o banco
	 * @param password 		 	 &nbsp;Senha do usuário para conexão
	 * @return 
	 */
	public static void setJdbcConnection(String driver, String serverName, String schema, String username, String password){

		dbDriver = driver;
		String jdbcDriver = getJdbcDriver(dbDriver);
		dbURL = jdbcDriver + serverName + ":" + schema;
		dbUsername = username;
		dbPassword = password;

		try {
			Class.forName(driver);
			jdbcConnection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
			connection = new DatabaseConnection(jdbcConnection,dbUsername);
			DatabaseConfig config = connection.getConfig();
			config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Oracle10DataTypeFactory());
			config.setProperty("http://www.dbunit.org/features/caseSensitiveTableNames", true);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DatabaseUnitException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este método exporta para um arquivo XML somente os dados da(s) tabela(s) passada(s) como parâmetro  
	 * @param tablesNames
	 * @param filePath
	 */
	public static void exportTablesXMLDataSet(ArrayList<String> tablesNames, String filePath) {   	
		try {
			QueryDataSet partialDataSet = new QueryDataSet(connection);
			for (String tableName : tablesNames) {
				partialDataSet.addTable(tableName);        	
			}
			FlatXmlDataSet.write(partialDataSet, new FileOutputStream(filePath));        
		} catch (AmbiguousTableNameException e) {
			e.printStackTrace();
		} catch (DataSetException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}        	                  	
	}

	/**
	 * Este método executa uma operação de DELETE_ALL seguido da uma operação de INSERT,
	 * garantindo assim que a base de dados fique em um estado conhecido.
	 * 
	 * @param arqCaminho Caminho do arquivo XML que será carregado
	 */
	public static void executeCleanInsert(String arqCaminho){
		try {
			//IDataSet dataSet = new FlatXmlDataSet(new File(arqCaminho), true);
			IDataSet dataSet = new FlatXmlDataSetBuilder().build(
					new FileInputStream(arqCaminho));
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		} catch (DatabaseUnitException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este método executa uma operação de UPDATE,
	 * garantindo assim que a base de dados fique em um estado conhecido.
	 * 
	 * @param arqCaminho Caminho do arquivo XML que será carregado
	 */
	public static void executeUpdate(String arqCaminho){
		try {
			FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
			builder.setColumnSensing(true);
			IDataSet dataSet = builder.build(new File(arqCaminho));
			DatabaseOperation.UPDATE.execute(connection, dataSet);
		} catch (DatabaseUnitException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
