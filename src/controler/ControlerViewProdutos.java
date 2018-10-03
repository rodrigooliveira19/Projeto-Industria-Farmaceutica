
package controler;

import entidades.Estoque;
import entidades.Insumo;
import entidades.Produto;
import entidades.ProdutoManufaturado;
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
import view.Principal;
import view.Produtos;


public class ControlerViewProdutos {
    
    private Produtos VProdutos; 

    public ControlerViewProdutos() {
       this.VProdutos = new Produtos();
       VProdutos.setControlerViewProdutos(this);
       this.opProdutos("SALVAR");
       this.VProdutos.setVisible(true);
        
    }
    
    //Função para liberar as opções da view Produtos
    public void opProdutos(String op){
        
        switch(op){
            
            case ("SALVAR"):
                VProdutos.txt_produtoId.setEnabled(false);
                VProdutos.button_alterarTipo.setEnabled(false);
                VProdutos.txt_nomeFornecedor.setEditable(false);
                VProdutos.txt_fornecedor_id.setEditable(false);
                VProdutos.button_atualizar.setEnabled(false);
                VProdutos.button_excluir.setEnabled(false);
                VProdutos.button_verificarFornecedor.setEnabled(false);
                break; 
                
            case("ATUALIZAR"):
                VProdutos.button_alterarTipo.setEnabled(true);
                VProdutos.txt_nomeFornecedor.setEditable(true);
                VProdutos.txt_fornecedor_id.setEditable(true);
                VProdutos.button_atualizar.setEnabled(true);
                VProdutos.button_excluir.setEnabled(true);
                VProdutos.button_verificarFornecedor.setEnabled(true);
                VProdutos.button_salvar.setEnabled(false);
                break;
            case ("LIBERAR_TXT_FORNECEDOR"):
                VProdutos.txt_nomeFornecedor.setEditable(true);
                break; 
            case ("ALTERAR_TIPO"):
                VProdutos.radioButton_insumo.setEnabled(true);
                VProdutos.radioButton_PManufaturado.setEnabled(true); 
                break; 
            case ("TIPO_MANUFATURADO"):
                VProdutos.txt_nomeFornecedor.setText("");
                VProdutos.txt_nomeFornecedor.setEditable(false);
                break; 
            case ("OP_ESTOQUE_NAO"):
                VProdutos.txt_valorMinimo.setText("");
                VProdutos.txt_valorMaximo.setText("");
                VProdutos.txt_valorAtual.setText("");
                VProdutos.txt_valorMinimo.setEditable(false);
                VProdutos.txt_valorMaximo.setEditable(false);
                VProdutos.txt_valorAtual.setEditable(false);
                break;
            case ("OP_EStoque_SIM"):
                VProdutos.txt_valorMinimo.setEditable(true);
                VProdutos.txt_valorMaximo.setEditable(true);
                VProdutos.txt_valorAtual.setEditable(true);
                break; 
        }
    }
    
    //Funçao para apagar os dados que está no Jframe
    public void apagarDados(){
        VProdutos.txt_produtoId.setText("");
        VProdutos.txt_nomeProduto.setText("");
        VProdutos.txt_formulaQuimica.setText("");
        VProdutos.txt_grauToxico.setText("");
        VProdutos.buttonGroup1.clearSelection();
        VProdutos.txt_nomeFornecedor.setText("");
        VProdutos.txt_fornecedor_id.setText("");
        VProdutos.buttonGroup2.clearSelection();
        VProdutos.txt_valorMinimo.setText("");
        VProdutos.txt_valorMaximo.setText("");
        VProdutos.txt_valorAtual.setText("");   
    }
    
    // Função para setar o nome do fornecedor no campo txt_nomeFonecedor da view Produtos. 
    public void getNomeFornecedor(){
        try {
            String nomeFornecedor; 
            IFornecedorDao iFornecedorDao = new FornecedorDao();
            nomeFornecedor = iFornecedorDao.getNomeFornecedorID(Integer.parseInt(VProdutos.txt_fornecedor_id.getText()));
            VProdutos.txt_nomeFornecedor.setText(nomeFornecedor);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possivel acessar o produto");
        }
    }
    
    //Função para buscar um produto a partir de um id informado.
    public void buscarProdutoID(){
        this.apagarDados();
        
        try {
            Produto produto;
            Estoque estoque; 
            IProdutoDao IProdutodao = new ProdutoDao(); 
            //System.out.println("ID"+ Integer.parseInt(txt_produto_id_aux.getText()));
            String produtoId = VProdutos.txt_produto_id_aux.getText(); 
            produto = IProdutodao.getProdutoId(Integer.parseInt(VProdutos.txt_produto_id_aux.getText())); 
            VProdutos.txt_nomeProduto.setText(produto.getNome());
            VProdutos.txt_formulaQuimica.setText(produto.getFormulaQuimica());
            VProdutos.txt_grauToxico.setText(Integer.toString(produto.getGrauToxico()));
            String tipo =  produto.getTipo(); 
            estoque = produto.getEstoque(); 
            float min = estoque.getValorminimo() ; 
            float max = estoque.getValorMaximo(); 
            float atual = estoque.getValorAtual(); 
            VProdutos.txt_valorMinimo.setText(String.valueOf(min));
            VProdutos.txt_valorMaximo.setText(String.valueOf(max));
            VProdutos.txt_valorAtual.setText(String.valueOf(atual));
            VProdutos.txt_produtoId.setText(produtoId);
            VProdutos.txt_produtoId.setEditable(false);
            if(tipo.equals("PI")){
                VProdutos.radioButton_insumo.setSelected(true);
                VProdutos.radioButton_insumo.setEnabled(false);
                VProdutos.radioButton_PManufaturado.setEnabled(false);
            }
            else if(tipo.equals("PM")){
                VProdutos.radioButton_PManufaturado.setSelected(true);
                VProdutos.radioButton_PManufaturado.setEnabled(false);
                VProdutos.radioButton_insumo.setEnabled(false);   
            }
            
            this.opProdutos("ATUALIZAR");
                 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado.");
        }
    }
    
    // Função que a atualiza o produto que está em memoria no banco de dados.
    public void updateProduto(){
        // Pegar os dados 
        try {
            
            Produto produto =null; 
            int produtoId = Integer.parseInt(VProdutos.txt_produtoId.getText());
            String nomeProduto = VProdutos.txt_nomeProduto.getText();
            String formulaQuimica = VProdutos.txt_formulaQuimica.getText();
            int grauToxico = Integer.parseInt(VProdutos.txt_grauToxico.getText());
            float min = Float.parseFloat(VProdutos.txt_valorMinimo.getText()); 
            float max = Float.parseFloat(VProdutos.txt_valorMaximo.getText()); 
            float atual = Float.parseFloat(VProdutos.txt_valorAtual.getText());
            Estoque estoque = new Estoque(min, max, atual); 
            

            if (VProdutos.radioButton_insumo.isSelected()) {
                  produto = new Insumo("",nomeProduto, formulaQuimica, grauToxico); 
                  produto.setEstoque(estoque);
                  produto.setProdutoId(produtoId);
            }
            
            else if(VProdutos.radioButton_PManufaturado.isSelected()){
                produto = new ProdutoManufaturado(nomeProduto, formulaQuimica, grauToxico);
                produto.setEstoque(estoque);
                produto.setProdutoId(produtoId);
            }
            
            IProdutoDao iProdutoDao = new ProdutoDao(); 
            iProdutoDao.updateProduto(produto);
            
            //Se o campo fornecedor_id estiver com id de fornecedor atualiza-se a tabela fornecedor_produto no Banco de Dados
            if(!VProdutos.txt_fornecedor_id.getText().isEmpty() && !VProdutos.txt_produtoId.getText().isEmpty()){
                int fornecedorID = Integer.parseInt(VProdutos.txt_fornecedor_id.getText()); 
                iProdutoDao.salvarFonecedorProduto(fornecedorID, produtoId);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Verificar Dados");
        }
    }
    
    //Metodo que salva um produto no Banco de Dados
    public void salvarProduto(){
        Produto produto = null;
        //Se o tipo selecionado for Insumo
        if (VProdutos.radioButton_insumo.isSelected()) {
            try {
                produto = new Insumo(VProdutos.txt_nomeFornecedor.getText(), VProdutos.txt_nomeProduto.getText(),
                        VProdutos.txt_formulaQuimica.getText(), Integer.parseInt(VProdutos.txt_grauToxico.getText()));
                // produto = insumo; 
                //System.out.println(""+insumo.toString());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Dados Incorretos");
            }

        } else if (VProdutos.radioButton_PManufaturado.isSelected()) {
            try {
                produto = new ProdutoManufaturado(VProdutos.txt_nomeProduto.getText(),
                        VProdutos.txt_formulaQuimica.getText(), Integer.parseInt(VProdutos.txt_grauToxico.getText()));
                // produto = pManufaturado; 

                //  System.out.println(""+pManufaturado.toString());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Dados Incorretos");
            }
        }

        if (produto != null && VProdutos.radioButton_opEstoqueSim.isSelected()
                                             && VProdutos.txt_produtoId.getText().isEmpty()) {

            try {
                float min = Float.parseFloat(VProdutos.txt_valorMinimo.getText());
                float max = Float.parseFloat(VProdutos.txt_valorMaximo.getText());
                float atual = Float.parseFloat(VProdutos.txt_valorAtual.getText());
                Estoque estoque = new Estoque(min, max, atual);
                produto.setEstoque(estoque);

                //Salva-se no Banco de dados.
                IProdutoDao iProdutoDao = new ProdutoDao();
                iProdutoDao.salvarProduto(produto);
                
                if(produto instanceof Insumo)
                    JOptionPane.showMessageDialog(null, "Atenção. Cadastrar fornecedor para o Insumo");


            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Erro. Verificar estoque");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao Salvar o produto");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Erro. Não foi possível salvar o produto"); 
        }
    }
    
    //Metodo que pega o Id do produto e deleta o mensmo no Banco de Dados. 
    public void deleteProdutoId(){
        try {
            int produtoId = Integer.parseInt(VProdutos.txt_produtoId.getText());
            IProdutoDao iProdutoDao = new ProdutoDao();
            iProdutoDao.deleteProdutoId(produtoId);
            this.apagarDados();
        } catch (Exception ex) {
            //Logger.getLogger(Produtos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Metodo que busca todos os produtos no Banco de Dados e adiciona na Tabela da View Produtos
    public void getProdutos(){
        Produto auxProduto; 
        IProdutoDao iProdutoDao = new ProdutoDao(); 
        List<Produto> produtos = new ArrayList(); 
        DefaultTableModel auxTabela = (DefaultTableModel ) VProdutos.tabela.getModel(); 
        auxTabela.setNumRows(0);
        
        try { 
            produtos = iProdutoDao.getProdutos();
            for(int iCont=0; iCont < produtos.size(); iCont++){
               auxProduto = produtos.get(iCont); 
               Object [] dados ={auxProduto.getProdutoId(), auxProduto.getNome(), auxProduto.getFormulaQuimica(), 
                                        auxProduto.getTipo(),"",auxProduto.getEstoque().getValorminimo(), auxProduto.getEstoque().getValorMaximo(),
                                                                                                      auxProduto.getEstoque().getValorAtual()};
               auxTabela.addRow(dados);
            }
        } catch (Exception ex) {
            Logger.getLogger(Produtos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
