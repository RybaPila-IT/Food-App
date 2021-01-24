import java.sql.*;

import oracle.jdbc.pool.OracleDataSource;


public class DatabaseConnector {

    private static final String PORT = "1521";
    private static final String HOST = "ora4.ii.pw.edu.pl";
    private static final String SERV = "pdb1.ii.pw.edu.pl";

    private Connection connection;

    public DatabaseConnector(String user, String password) throws SQLException {
        setConnection(user, password);
    }

    public void endConnection() throws SQLException {

        connection.close();
        //System.out.println("Polaczenia z baza danych zamkniete");

    }


    private void setConnection(String user, String password) throws SQLException {

        String connectionString = String.format(
                "jdbc:oracle:thin:%s/%s@//%s:%s/%s",
                user, password, HOST, PORT, SERV);

        //System.out.println(connectionString);

        OracleDataSource ods = new OracleDataSource();
        ods.setURL(connectionString);
        connection = ods.getConnection();

        DatabaseMetaData meta = connection.getMetaData();

        //System.out.println("Polaczenie do bazy danych nawiazane.");
        //System.out.println("Baza danych:" + " " + meta.getDatabaseProductVersion());

    }

}
