package objs;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class Database{
    private String url;
    private String username;
    private String password;

    public Database(String driver, String url, String username, String password) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver class is missing in classpath", e);
        }
        this.url = url;
        this.username = username;
        this.password = password;
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}