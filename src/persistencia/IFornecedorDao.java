
package persistencia;

import entidades.Fornecedor;
import java.util.ArrayList;
import java.util.List;

public interface IFornecedorDao {
    
    public void salvarFornecedor(Fornecedor fornecedor) throws  Exception;
    public void updateFornecedor(Fornecedor fornecedor) throws Exception;
    public String getNomeFornecedorID(int fornecedor_id) throws Exception; 
    public Fornecedor getFornecedorID(int fornecedor_id) throws Exception; 
    public int deleteFornecedor(int fornecedor_id) throws Exception; 
    public int deleteFornecedorProduto(int fornecedorId, int produtoId) throws Exception; 
    public List<String> getProdutoIdFornecedor(int fornecedor_id) throws Exception; 
    public ArrayList<Fornecedor> getFornecedores() throws Exception;
    public ArrayList<Fornecedor> getFornecedores(int produto_id) throws Exception; 
    
}
