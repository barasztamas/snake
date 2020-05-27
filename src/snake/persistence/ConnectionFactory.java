package snake.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class ConnectionFactory {
    private static MysqlConnectionPoolDataSource conn;

    private ConnectionFactory(){}

    public static Connection getConnection()
            throws ClassNotFoundException, SQLException {
        if (conn == null){
            Class.forName("com.mysql.jdbc.Driver"); // Driver betöltése
            conn = new MysqlConnectionPoolDataSource();
            conn.setServerName("localhost");
            conn.setPort(3306);
            conn.setDatabaseName("snake");
            conn.setUser("tanulo");
            conn.setPassword("asd123");
        }
        return conn.getPooledConnection().getConnection();
    }
}
