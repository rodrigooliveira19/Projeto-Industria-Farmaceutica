
package persistenciaSql;

import entidades.Componente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.IComponenteDao;


public class ComponenteDao implements IComponenteDao {
    
    ConnectionDB conn = new ConnectionDB();
    private static final String SALVAR_COMPONENTE="insert into componente values (?,?,?)"; 
    private static final String DELETE_COMPONENTE= "delete from componente where produto_id = ? and descricao = ?";
    private static final String SELECT_COMPONENTES ="select *from componente where produto_id = ?"; 
    
    
    @Override
    public void salvarComponente(Componente componete) throws Exception{ 
        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(ComponenteDao.SALVAR_COMPONENTE);
            stmt.setInt(1, componete.getProdutoId());
            stmt.setString(2, componete.getDescricao());
            stmt.setFloat(3, componete.getQtd());
            stmt.executeUpdate();   
        } catch (SQLException e) {
        }  
    }
    
    @Override
    public void deleteComponete(Componente componenete) throws Exception{
        try {
            PreparedStatement stmt =  conn.getConexao().prepareStatement(ComponenteDao.DELETE_COMPONENTE); 
            stmt.setInt(1, componenete.getProdutoId());
            stmt.setString(2, componenete.getDescricao());
            stmt.executeUpdate(); 
        } catch (SQLException e) {
        }
    }
    
    @Override
    public ArrayList<Componente> getComponentes(int produto_id) throws Exception{
        List<Componente> componentes; 
        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(ComponenteDao.SELECT_COMPONENTES); 
            stmt.setInt(1, produto_id);
            ResultSet result =  stmt.executeQuery(); 
            componentes =  this.createComponentes(result); 
            return (ArrayList<Componente>) componentes; 
        } catch (SQLException e) {
        }
        return null;
    }
    
    private ArrayList<Componente> createComponentes(ResultSet result){
        ArrayList<Componente> componentes;
        componentes = new ArrayList();
        Componente componente; 
        try {
            while(result.next()){ 
                int produto_id = result.getInt("produto_id");
                String descricao = result.getString("descricao"); 
                Float qtd = result.getFloat("quantidade");
                componente =  new Componente(descricao, qtd); 
                componente.setProdutoId(produto_id);
                componentes.add(componente); 
            }
            return componentes; 
        } catch (SQLException ex) {
            Logger.getLogger(ComponenteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
