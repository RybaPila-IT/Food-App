import java.sql.*;


import oracle.jdbc.pool.OracleDataSource; //sterownik bazy danych Oracle


public class DatabaseConnector {

    private static final String PORT = "1521";
    private static final String HOST = "ora4.ii.pw.edu.pl";
    private static final String SERV = "pdb1.ii.pw.edu.pl";

    private Connection connection;

    public DatabaseConnector(String user, String password) {



    }


    private void setConnection(String user, String password) throws SQLException {

        String connectionString = String.format(
                "jdbc:oracle:thin:%s/%s@//%s:%s/%s",
                user, password, HOST, PORT, SERV);





    }




}
