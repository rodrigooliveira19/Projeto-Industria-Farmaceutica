/*
 *Classe de conexão com o Banco de Dados My Sql
 */
package persistenciaSql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class ConnectionDB {
    
    private static final String URI = "jdbc:mysql://localhost:3306/industria_farmaceutica"; 
    private static final String USUARIO = "root"; 
    private static final String SENHA = "rodrigoso"; 
    private static Connection conn = null; 
    
    
    public Connection getConexao() {
       
        try {
            if(ConnectionDB.conn != null)
                return conn; 
            
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(URI, USUARIO, SENHA);
            System.out.println("Conectado com sucesso");
            return conn;
            
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de Conexão");
        }
 
        return null; 
    }
    
}
