
package persistenciaSql;

import entidades.Fornecedor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import persistencia.IFornecedorDao;


public class FornecedorDao implements IFornecedorDao{
    
    ConnectionDB conn = new ConnectionDB();
    private static final String INSERT_FORNECEDOR = "insert into fornecedor(nome,cgc,webservice) values (?,?,?)";
    private static final String UPDATE_FORNECEDOR = "update fornecedor set nome = ?,cgc = ?,webservice = ? where fornecedor_id = ?"; 
    private static final String DELETE_FORNECEDOR_PRODUTO = "delete from fornecedor_produto where fornecedor_id = ? and produto_id = ?";
    private static final String DELETE_FORNECEDOR = "delete from fornecedor where fornecedor_id = ?";
    private static final String SELECT_FORNECEDORES = "select * from fornecedor where fornecedor_id  in (select fornecedor_id from fornecedor_produto f where f.produto_id = ?)";
    private static final String SELECT_PRODUTO_ID_FORNECEDOR = "select produto_id from fornecedor_produto where fornecedor_id= ?";
    private static final String SELECT_NOME_FORNECEDOR_ID = "select nome from fornecedor where fornecedor_id = ?"; 
    private static final String SELECT_FORNECEDOR_ID = "select *from fornecedor where fornecedor_id = ?"; 
    private static final String SELECT_FORNECEDOR = "select * from fornecedor"; 
    
    //Metodo que recebe uma classe fornecedor e salva os dados no Banco de Dados
    @Override
    public void salvarFornecedor(Fornecedor fornecedor) throws Exception{
        
        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(FornecedorDao.INSERT_FORNECEDOR); 
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getCgc());
            stmt.setString(3, fornecedor.getWebService());
            stmt.executeUpdate(); 
            JOptionPane.showMessageDialog(null, "Fornecedor Salvo com sucesso !!!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Não foi possivel salvar o Fornecedor.");
        }
    }
    
    //Metodo que recebe um fornecedor e atualiza no Banco
    @Override
    public void updateFornecedor(Fornecedor fornecedor) throws Exception{
        
        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(FornecedorDao.UPDATE_FORNECEDOR); 
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getCgc());
            stmt.setString(3, fornecedor.getWebService());
            stmt.setInt(4, fornecedor.getFornecedorId());
            int linhasAfetadas = stmt.executeUpdate(); 
            if(linhasAfetadas > 0)
                JOptionPane.showMessageDialog(null, "Fornecedor atualizado com Sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Não foi possivel atualizar o Fornecedor.");
        }
        
    }
    
    //Metodo que recebe um id de fornecedor e deleta o mesmo no Banco de Dados. 
    @Override
    public int deleteFornecedor(int fornecedor_id) throws Exception{   
        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(FornecedorDao.DELETE_FORNECEDOR); 
            stmt.setInt(1, fornecedor_id);
            return stmt.executeUpdate(); 
                      
        } catch (SQLException e) {          
        }
        return 0;
    }
    
    @Override
    public int deleteFornecedorProduto(int fornecedor_id, int produto_id) throws Exception{
        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(FornecedorDao.DELETE_FORNECEDOR_PRODUTO); 
            stmt.setInt(1, fornecedor_id);
            stmt.setInt(2, produto_id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
        }
        return 0;
    }
        

    //Metodo que recebe um id de fornecedor e retorna uma collection do tipo String com todos os id dos produtos que este fornece. 
    @Override
    public List<String> getProdutoIdFornecedor(int fornecedor_id) throws Exception {
        
        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(FornecedorDao.SELECT_PRODUTO_ID_FORNECEDOR);
            stmt.setInt(1, fornecedor_id);
            ResultSet result = stmt.executeQuery(); 
            List<String> idProdutos = new ArrayList(); 
            
            while(result.next()){
                String idProduto = Integer.toString( result.getInt("produto_id")); 
                idProdutos.add(idProduto); 
            }
            //System.out.println("Qtd: "+idProdutos.size());
            return idProdutos; 
            
        } catch (SQLException e) {
        }
        return null;
    }
    
    // Seleciona o nome do fornecedor pelo Id indicado. 
    @Override
    public String getNomeFornecedorID(int fornecedor_id) throws Exception{
        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(FornecedorDao.SELECT_NOME_FORNECEDOR_ID); 
            stmt.setInt(1, fornecedor_id);
            ResultSet result = stmt.executeQuery(); 
            
            if(result.next()){
                String nomeFornecedor = result.getString("nome");
                //System.out.println("Nome : "+nomeFornecedor);
                return nomeFornecedor; 
            }       
        } catch (SQLException e) {
        }
        
        return ""; 
    }
    
    //Metodo que recebe um id de fornecedor e retorna o mesmo do Banco de Dados. 
    @Override
    public Fornecedor getFornecedorID(int fornecedor_id) throws Exception{
        
        Fornecedor fornecedor; 
        try {
            PreparedStatement stmt =  conn.getConexao().prepareStatement(FornecedorDao.SELECT_FORNECEDOR_ID); 
            stmt.setInt(1, fornecedor_id);
            ResultSet result = stmt.executeQuery(); 
            
            if(result.next()){
                int id_fornecedor =  result.getInt("fornecedor_id"); 
                String nome = result.getString("nome"); 
                String cgc = result.getString("cgc"); 
                String webService = result.getString("webservice"); 
                fornecedor = new Fornecedor(nome, cgc, webService); 
                fornecedor.setFornecedorId(fornecedor_id);
                return fornecedor; 
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar o fornecedor.");
        }
        return null; 
    }
    
    @Override
    public ArrayList<Fornecedor> getFornecedores() throws Exception{
        List<Fornecedor> fornecedores = new ArrayList(); 
        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(FornecedorDao.SELECT_FORNECEDOR); 
            ResultSet result = stmt.executeQuery(); 
            fornecedores =  this.createFornecedores(result); 
            return (ArrayList<Fornecedor>) fornecedores; 
            
        } catch (SQLException e) {
        }
        return null;
    }
    
    @Override
    public ArrayList<Fornecedor> getFornecedores(int produto_id) throws Exception{
        ArrayList<Fornecedor> fornecedores; 
        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(FornecedorDao.SELECT_FORNECEDORES); 
            stmt.setInt(1, produto_id);
            ResultSet result = stmt.executeQuery(); 
            fornecedores = this.createFornecedores(result); 
            return fornecedores; 
        } catch (SQLException e) {
        }
        return null;
    }
    
    private ArrayList<Fornecedor> createFornecedores (ResultSet result){
        Fornecedor fornecedor; 
        List<Fornecedor> fornecedores = new ArrayList();
        
        try {
            while(result.next()){
                int fornecedorId = Integer.parseInt(result.getString("fornecedor_id")); 
                String nome = result.getString("nome");
                String cgc = result.getString("cgc");
                String webService = result.getString("webservice");
                fornecedor = new Fornecedor(nome, cgc, webService); 
                fornecedor.setFornecedorId(fornecedorId);
                fornecedores.add(fornecedor); 
            }
            return (ArrayList<Fornecedor>) fornecedores; 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao Buscar os Fornecedores");
        }
        return null;
    }
    
    
}
