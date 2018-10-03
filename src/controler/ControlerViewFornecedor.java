package controler;

import entidades.Fornecedor;
import entidades.Produto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import persistencia.IFornecedorDao;
import persistencia.IProdutoDao;
import persistenciaSql.FornecedorDao;
import persistenciaSql.ProdutoDao;
import view.Fornecedores;


public class ControlerViewFornecedor {
    private Fornecedores VFornecedor; 

    public ControlerViewFornecedor() {
        VFornecedor = new Fornecedores(); 
        VFornecedor.setControlador(this);
        this.inicializarCadastro(); // 
        VFornecedor.setVisible(true);
    }
    
    public void inicializarCadastro() {
        VFornecedor.button_atualizar.setEnabled(false);
        VFornecedor.button_excluir.setEnabled(false);
        VFornecedor.txt_idFornecedor.setEnabled(false);
        VFornecedor.txt_buscarFornecedorId.setText(""); // Adicionado para limpar o campo de buscaId após uma exclusão.
        VFornecedor.button_salvar.setEnabled(true);
    }
    
    public void liberarCadastro() {
        VFornecedor.button_atualizar.setEnabled(true);
        VFornecedor.button_excluir.setEnabled(true);
        VFornecedor.button_salvar.setEnabled(false);
    }
    
    public void limparDadosFornecedor() {
        VFornecedor.txt_nomeFornecedor.setText("");
        VFornecedor.txt_cgc.setText("");
        VFornecedor.txt_webService.setText("");
        VFornecedor.txt_idFornecedor.setText("");
        VFornecedor.txt_buscarFornecedorId.setText("");
    }
    
    //Metodo que salva o fornecedor no Banco de Dados. 
    public void cadastroSalvarFornecedor(){
        Fornecedor fornecedor = new Fornecedor(VFornecedor.txt_nomeFornecedor.getText(), VFornecedor.txt_cgc.getText(),VFornecedor.txt_webService.getText()); 
        IFornecedorDao iFornecedorDao = new FornecedorDao(); 
        try {
            iFornecedorDao.salvarFornecedor(fornecedor);
            this.limparDadosFornecedor();
        } catch (Exception ex) {
            Logger.getLogger(Fornecedores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cadastroBuscarFonecedorId(){
        Fornecedor fornecedor;
        IFornecedorDao iFornecedorDao = new FornecedorDao();
        if(!VFornecedor.txt_buscarFornecedorId.getText().isEmpty()){
            int fornecedor_id = Integer.parseInt(VFornecedor.txt_buscarFornecedorId.getText());
            try {
                fornecedor = iFornecedorDao.getFornecedorID(fornecedor_id);
                if (fornecedor != null) {
                    VFornecedor.txt_idFornecedor.setText(VFornecedor.txt_buscarFornecedorId.getText());
                    VFornecedor.txt_nomeFornecedor.setText(fornecedor.getNome());
                    VFornecedor.txt_cgc.setText(fornecedor.getCgc());
                    VFornecedor.txt_webService.setText(fornecedor.getWebService());
                    this.liberarCadastro();
                } else {
                    JOptionPane.showMessageDialog(null, "Fornecedor não encontrado");
                }

            } catch (Exception ex) {

            }
        }
    }
    
    //Metodo que atualiza os dados do fornecedor no Banco de Dados. 
    public void cadastroAtualizar(){
        int fornecedorId =  Integer.parseInt(VFornecedor.txt_idFornecedor.getText()); 
        Fornecedor fornecedor = new Fornecedor(VFornecedor.txt_nomeFornecedor.getText(), VFornecedor.txt_cgc.getText(),VFornecedor.txt_webService.getText()); 
        fornecedor.setFornecedorId(fornecedorId);
        IFornecedorDao iFornecedorDao = new FornecedorDao(); 
        try {
            iFornecedorDao.updateFornecedor(fornecedor);
        } catch (Exception e) {
            
        }
        
    }
    //Metodo que esxluir o fornecedor do Banco de Dados. 
    public void cadastroExcluir(){
                int fornecedorId =  Integer.parseInt(VFornecedor.txt_idFornecedor.getText()); 
        IFornecedorDao iFornecedorDao = new FornecedorDao();
        try {
             int linhaAfetada = iFornecedorDao.deleteFornecedor(fornecedorId); 
             if (linhaAfetada > 0) {
                JOptionPane.showMessageDialog(null, "Fornecedor excluido com Sucesso");
                this.limparDadosFornecedor();
            } else {
                JOptionPane.showMessageDialog(null, "Erro.Fornecedor pode estar associado a um produto");
            }
            
        } catch (Exception e) {
        }
    }
    
    //Metodo que busca todos os fornecedores no Banco de Dados e adiciona na Tabela para visualização. 
    public void cadastroBuscarTodos(){
       List<Fornecedor> fornecedores =  new ArrayList(); 
       Fornecedor fornecedor; 
       IFornecedorDao iFornecedor = new FornecedorDao(); 
       DefaultTableModel auxTabela = (DefaultTableModel) VFornecedor.tabela_fornecedor.getModel(); 
       auxTabela.setNumRows(0);
       
        try {
            fornecedores = iFornecedor.getFornecedores(); 
            for(int iCont = 0; iCont < fornecedores.size(); iCont++){
                fornecedor = fornecedores.get(iCont); 
                Object[] dados = {fornecedor.getFornecedorId(), fornecedor.getNome(), fornecedor.getCgc(), fornecedor.getWebService()}; 
                auxTabela.addRow(dados);
            }
            
            this.inicializarCadastro();
            
        }  catch (Exception ex) {
            Logger.getLogger(Fornecedores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Metodo que fecha a view Fornecedores
    public void cadastroSair(){
        VFornecedor.dispose();
    }
    //Metodo que seta o nome do fornecedor e busca todos os produtos que o mesmo fornece. 
    public void listagemBuscarProdutosFornecedor(){
             IFornecedorDao iFornecedorDao = new FornecedorDao(); 
        IProdutoDao iProdutoDao = new ProdutoDao(); 
        DefaultTableModel auxTabela = (DefaultTableModel) VFornecedor.table_listagemFornecedorProduto.getModel(); 
        auxTabela.setNumRows(0);
        String nomeFornecedor; 
        List<Produto> produtos; 
        Produto p; 
        if(!VFornecedor.txt_fornecedorIdListagem.getText().isEmpty()){
            try {
                int fornecedorId = Integer.parseInt(VFornecedor.txt_fornecedorIdListagem.getText()); 
                nomeFornecedor = iFornecedorDao.getNomeFornecedorID(fornecedorId); 
                VFornecedor.txt_nomeFornecedorListagem.setText(nomeFornecedor);
                produtos = iProdutoDao.getProdutosFornecedorId(fornecedorId); 
                for(int iCont=0; iCont < produtos.size(); iCont++){
                    p =  produtos.get(iCont); 
                    Object[] dados = {p.getProdutoId(), p.getNome(), p.getFormulaQuimica(), p.getGrauToxico()};
                    auxTabela.addRow(dados); 
                }
                this.liberarCadastro();
            } catch (Exception e) {
            }
        }
    }
    
    //Metodo que exclui um produto do fornecedor. 
    public void listagemExcluirProduto(){
                if(!VFornecedor.txt_listagemProdutoId.getText().isEmpty()){
            int produtoId = Integer.parseInt(VFornecedor.txt_listagemProdutoId.getText()); 
            int fornecedorId = Integer.parseInt(VFornecedor.txt_fornecedorIdListagem.getText());
            IFornecedorDao iFornecedorDao = new FornecedorDao(); 
            try {
                int linhaAfetada = iFornecedorDao.deleteFornecedorProduto(fornecedorId, produtoId);
                if(linhaAfetada > 0){
                    VFornecedor.txt_listagemProdutoId.setText("");
                    JOptionPane.showMessageDialog(null, "Exclusão realizada com Sucesso");
                }
                    
            } catch (Exception e) {
            }
        }
    }
    
    
    
    
}
