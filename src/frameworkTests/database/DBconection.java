package frameworkTests.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import frameworkTests.utils.ConfigTestManager;
import oracle.jdbc.OracleResultSet;



public class DBconection{
	static Connection conn = null;
	private static PreparedStatement ps;

	/**
	 * Connect BD oracle
	 */
	public static Connection getConnection() throws Exception {
		//Criação das variaveis de conexão
		try {
			Class.forName(ConfigTestManager.getdbDrive());
			conn = DriverManager.getConnection(ConfigTestManager.getdbConnecturl(), ConfigTestManager.getdbUsername(), ConfigTestManager.getdbPassword());
			conn.setAutoCommit(true);
		} catch (Exception e) {
			return null;
		}
		return conn;
	}

	/**
	 * Execute Query BD and return results
	 *	@return result
	 */
	//Metodo para executar uma query e retornar um resultset
	public static OracleResultSet consult(Connection conn, String query){
		OracleResultSet resultSet = null;
		try {
			// Create a PreparedStatement based on the query in query
			ps = conn.prepareStatement(query);
			// Execute the PreparedStatement
			resultSet = (OracleResultSet) ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * close connection BD
	 */
	//Metodo que fecha a conexão com o banco
	public static void closeConnection(Connection conn){
		try {
			if(!conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
