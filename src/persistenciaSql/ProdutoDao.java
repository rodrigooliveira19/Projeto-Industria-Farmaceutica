
package persistenciaSql;

import entidades.Componente;
import entidades.Estoque;
import entidades.Fornecedor;
import entidades.Insumo;
import entidades.Produto;
import entidades.ProdutoManufaturado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import persistencia.IComponenteDao;
import persistencia.IFornecedorDao;
import persistencia.IProdutoDao;


public class ProdutoDao implements IProdutoDao {

    ConnectionDB conn = new ConnectionDB();
    private static final String INSET_PRODUTO = "insert into produto(tipo,nome,formula_quimica,grau_toxico,valor_minimo,valor_maximo,valor_atual)values (?,?,?,?,?,?,?)";
    private static final String SELECT_PRODUTOS = "select *from produto";
    private static final String SELECT_PRODUTOS_TIPO="select * from produto where tipo = ?"; 
    private static final String SELECT_PRODUTO_ID = "select * from produto where produto_id = ?";
    private static final String SELECT_PRODUTO_FORNECEDOR_ID ="select * from produto where  produto_id in (select produto_id from fornecedor_produto where fornecedor_id = ? )"; 
    private static final String UPDATE_PRODUTO = "update produto set tipo = ?, nome = ?, formula_quimica= ?, grau_toxico= ?, valor_minimo = ?, valor_maximo = ?, valor_atual = ? where produto_id = ?";
    private static final String DELETE_PRODUTO_ID = "delete from produto where produto_id = ?";
    private static final String DELETE_FORNECEDOR_PRODUTO = "delete from fornecedor_produto where produto_id = ?";
    private static final String UPDATE_ESTOQUE = "update produto set valor_minimo = ?, valor_maximo = ?, valor_atual = ? where produto_id = ?";
    private static final String INSERT_FORNECEDOR_ID_PRODUTO_ID = "insert into fornecedor_produto values (?,?)";

    //Salva os dados do produto no BD
    @Override
    public void salvarProduto(Produto produto) throws Exception {

        try {
            Estoque estoque = produto.getEstoque();
            PreparedStatement stmt = conn.getConexao().prepareStatement(INSET_PRODUTO);
            stmt.setString(1, produto.getTipo());
            stmt.setString(2, produto.getNome());
            stmt.setString(3, produto.getFormulaQuimica());
            stmt.setInt(4, produto.getGrauToxico());
            stmt.setFloat(5, estoque.getValorminimo());
            stmt.setFloat(6, estoque.getValorMaximo());
            stmt.setFloat(7, estoque.getValorAtual());
            int i = stmt.executeUpdate();
            if (i > 0) {
                JOptionPane.showMessageDialog(null, "Produto Cadastrado com Sucesso");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível cadastrar o produto");
        }

    }

    //Metodo para retornar todos os produtos do Banco de Dados
    @Override
    public ArrayList<Produto> getProdutos() throws Exception {
        List<Produto> produtos = new ArrayList();
        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(ProdutoDao.SELECT_PRODUTOS);
            ResultSet result = stmt.executeQuery();
            if (result != null) {
                produtos = this.createCollectionProduto(result);
            }

            return (ArrayList) produtos;

        } catch (SQLException e){
        }
        return null;
    }
    
    @Override
    public ArrayList<Produto> getProdutosTipo(String tipo) throws Exception{
        ArrayList<Produto> produtos = new ArrayList();
        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(ProdutoDao.SELECT_PRODUTOS_TIPO); 
            stmt.setString(1, tipo);
            ResultSet result =  stmt.executeQuery();
            produtos =  this.createCollectionProduto(result); 
            return produtos; 
        } catch (SQLException e) {
        }
        return null;
    }
    
    @Override
    public ArrayList<Produto> getProdutosFornecedorId(int fornecedorId) throws Exception{
        ArrayList<Produto> produtos = new ArrayList();
        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(ProdutoDao.SELECT_PRODUTO_FORNECEDOR_ID);
            stmt.setInt(1, fornecedorId);
            ResultSet result =  stmt.executeQuery(); 
            produtos = this.createCollectionProduto(result); 
            return produtos;      
        } catch (SQLException e) {
        }
        return null;
    }

    //Metodo para retornar a coleção de produtos
    private  ArrayList<Produto> createCollectionProduto(ResultSet result) throws Exception {
        Produto produto;
        Fornecedor fornecedor; 
        IFornecedorDao iFornecedorDao = new FornecedorDao(); 
        List<Produto> produtos = new ArrayList();
        List<Fornecedor> fornecedores = new ArrayList();
        while (result.next()) {
            int produto_id = result.getInt("produto_id");
            String tipo = result.getString("tipo");
            String nome = result.getString("nome");
            String formula_quimica = result.getString("formula_quimica");
            int grau_toxico = result.getInt("grau_toxico");
            float valor_minimo = result.getFloat("valor_minimo");
            float valor_maximo = result.getFloat("valor_maximo");
            float valor_atual = result.getFloat("valor_atual");
            Estoque estoque = new Estoque(valor_minimo, valor_maximo, valor_atual);
            if (tipo.equals("PI")) { //Se o produto for Insumo
                try {
                    produto = new Insumo("", nome, formula_quimica, grau_toxico);
                    produto.setProdutoId(produto_id);
                    produto.setEstoque(estoque);
                    fornecedores = iFornecedorDao.getFornecedores(produto_id);
                    ((Insumo) produto).setFornecedor(fornecedores);

                    produtos.add(produto); // adiciona na collection   

                } catch (SQLException e) {
                    System.out.println("Não acessou o nome do fornecedor");
                }
            } 
            else if (tipo.equals("PM")) { //Se o produto for do tipo Manufaturado
                IComponenteDao iComponenteDao =  new ComponenteDao(); // RESOLUÇÃO PARCIAL 
                ArrayList<Componente> componentes = null; 
                
                produto = new ProdutoManufaturado(nome, formula_quimica, grau_toxico);
                produto.setProdutoId(produto_id);
                produto.setEstoque(estoque);
                componentes = iComponenteDao.getComponentes(produto_id); 
                ((ProdutoManufaturado)produto).setComponentes(componentes);
                produtos.add(produto);
            }
        }
        return (ArrayList<Produto>) produtos;
    }

    //Retorna um produto do BD pelo Id informado.
    @Override
    public Produto getProdutoId(int produto_id) throws Exception {
       
        try {
            Produto produto  = null; 
            PreparedStatement stmt = conn.getConexao().prepareStatement(SELECT_PRODUTO_ID);
            stmt.setInt(1, produto_id);
            ResultSet result = stmt.executeQuery(); 
            produto =  this.createProduto(result); 
            return produto; 
        } catch (SQLException e) {
            System.out.println("Não acessou o produto");
        }
        return null;
    }

    //Metodo privado que recebe um result set e retorna o produto. 
    private Produto createProduto(ResultSet result) throws Exception {
        Produto produto;
        List<Fornecedor> fornecedores = null;
        IFornecedorDao iFornecedorDao = new FornecedorDao(); 
        
        while (result.next()) {
            int produto_id = result.getInt("produto_id");
            String tipo = result.getString("tipo");
            String nome = result.getString("nome");
            String formula_quimica = result.getString("formula_quimica");
            int grau_toxico = result.getInt("grau_toxico");
            float valor_minimo = result.getFloat("valor_minimo");
            float valor_maximo = result.getFloat("valor_maximo");
            float valor_atual = result.getFloat("valor_atual");
            Estoque estoque = new Estoque(valor_minimo, valor_maximo, valor_atual);
            
            if (tipo.equals("PI")) { //Se o produto for Insumo
                try {
                    produto = new Insumo("", nome, formula_quimica, grau_toxico);
                    produto.setProdutoId(produto_id);
                    produto.setEstoque(estoque);
                    fornecedores = iFornecedorDao.getFornecedores(produto_id); 
                    ((Insumo)produto).setFornecedor(fornecedores);
                    return produto;

                } catch (SQLException e) {
                    System.out.println("Não acessou o nome do fornecedor");
                }
            } //Se o produto for do tipo Manufaturado
            else if (tipo.equals("PM")) {
                produto = new ProdutoManufaturado(nome, formula_quimica, grau_toxico);
                produto.setProdutoId(produto_id);
                produto.setEstoque(estoque);
                return produto;            
            }
        }
        return null; 
       
    }

    //Metod que recebe um produto e atualiza seus campos no BD
    @Override
    public void updateProduto(Produto produto) throws Exception {

        try {
            Estoque estoque = produto.getEstoque();
            PreparedStatement stmt = conn.getConexao().prepareStatement(ProdutoDao.UPDATE_PRODUTO);
            stmt.setString(1, produto.getTipo());
            stmt.setString(2, produto.getNome());
            stmt.setString(3, produto.getFormulaQuimica());
            stmt.setInt(4, produto.getGrauToxico());
            stmt.setFloat(5, estoque.getValorminimo());
            stmt.setFloat(6, estoque.getValorMaximo());
            stmt.setFloat(7, estoque.getValorAtual());
            stmt.setInt(8, produto.getProdutoId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto Atualizado com Sucesso !!!");
        } catch (SQLException e) {
            System.out.println("Não foi possivel atualizar o produto " + produto.getNome());
        }
    }

    //Metodo que recebe um inteiro(Id do produto) e delata o mesmo do Banco de dados. 
    @Override
    public void deleteProdutoId(int produto_id) throws Exception {
        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(ProdutoDao.DELETE_FORNECEDOR_PRODUTO);
            stmt.setInt(1, produto_id);
            stmt.executeUpdate();
            stmt = conn.getConexao().prepareStatement(ProdutoDao.DELETE_PRODUTO_ID);
            stmt.setInt(1, produto_id);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Produto excluido com sucesso");
            } else {
                JOptionPane.showMessageDialog(null, "Produto Não Encontrado");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o produto");
        }

    }

    //Metodo para cadastrar na tabela do BD o fornecedor ao produto
    @Override
    public void salvarFonecedorProduto(int fornecedorId, int produtoId) throws Exception {

        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(ProdutoDao.INSERT_FORNECEDOR_ID_PRODUTO_ID);
            stmt.setInt(1, fornecedorId);
            stmt.setInt(2, produtoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro, ao cadastrar o fornecedor ao produto");
        }
    }
    
    @Override
    public void updateEstoqueProduto(ArrayList<Produto> produtosModificados) throws Exception{
        Produto produto; 
        Estoque estoque; 
        try {
            PreparedStatement stmt = conn.getConexao().prepareStatement(ProdutoDao.UPDATE_ESTOQUE);
            for(int iCont= 0; iCont < produtosModificados.size(); iCont++){
                produto = produtosModificados.get(iCont); 
                estoque = produto.getEstoque(); 
                stmt.setFloat(1, estoque.getValorminimo());
                stmt.setFloat(2, estoque.getValorMaximo());
                stmt.setFloat(3, estoque.getValorAtual());
                stmt.setInt(4, produto.getProdutoId());
                stmt.addBatch();
            }
            stmt.executeBatch(); 
            
        } catch (SQLException e) {
        }
    }

}
