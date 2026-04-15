package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:mysql://localhost:3306/Pratica04";
    private static final String root = "root";
    private static final String senha = "gatoraivoso248";

    // método GETTER para conexão via driver
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL, root, senha);
        } catch (SQLException e) {
            throw new RuntimeException("ERRO AO CONECTAR");
        }

    }
}