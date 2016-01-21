package database.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import com.mysql.jdbc.Driver;

import sun.reflect.Reflection;

public class ConnectionTest {

    public static void registerDriver() throws SQLException {
        DriverManager.registerDriver(new Driver());
    }

    public static Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/jiedianqian?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
        String userName = "root";
        String password = "mysql5.6";
        return DriverManager.getConnection(jdbcUrl, userName, password);
    }
    
    public static void main(String[] args) throws SQLException {
        String sql = "SHOW CHARACTER SET";
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("result :" + resultSet.getSQLXML(1));
            }    
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
        
    }

}
