
package entidades;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class SistemaDeControle {
    
    private List<Produto> produtos; 
    private List<SolicitacaoDeMaterial> solicitacoes; 

    public SistemaDeControle() {
        this.produtos = new ArrayList(); 
        this.solicitacoes = new ArrayList(); 
    }
    
    public ArrayList<Produto> registrarProducao(String nomeProduto, float quantidadeProduzida){ 
        Produto produto; 
        Estoque estoque;
        ArrayList<Produto> produtosModificados; 
        produtosModificados = new ArrayList(); 
        for(int iCont=0; iCont < produtos.size(); iCont++){ //Procurando o produto manufaturado e aumentando sua quantidade. 
            produto = produtos.get(iCont); 
            if(produto.getNome().equals(nomeProduto)){//Se for o produto manufaturado deve-se aumentar a quantidade.
                produtosModificados.add(produto); 
                estoque = produto.getEstoque(); 
                //if(estoque.getValorMaximo() >= estoque.getValorAtual() + quantidadeProduzida){
                    estoque.setValorAtual(estoque.getValorAtual()+ quantidadeProduzida); //Chama o metodo para reduzir o estoque dos componetes .
                    this.reduzirEstoqueInsumo(produto,quantidadeProduzida,produtosModificados); 
                    break; 
                //}
   
            }  
        }
        return produtosModificados;
    }
   
    private void reduzirEstoqueInsumo(Produto produto,float qtdProduzida,ArrayList<Produto> produtosModificados){ 
        Componente componente; 
        Produto insumo; 
        Estoque estoque; 
        float qtdTotal = 0;     
        if(produto instanceof ProdutoManufaturado){ //Percorrer a collection de componentes.
            for(int iCont=0; iCont < ((ProdutoManufaturado) produto).getQtdComponentes(); iCont++){
                componente =  ((ProdutoManufaturado) produto).getComponete(iCont);
                //Reduz o estoque de cada componente(insumo)
                for(int jCont=0; jCont < this.produtos.size(); jCont++){
                    insumo = produtos.get(jCont);
                    if(insumo instanceof Insumo){
                        if(insumo.getNome().equals(componente.getDescricao())){ //Se o insumo for o componente procurado
                            produtosModificados.add(insumo); 
                            estoque= insumo.getEstoque(); 
                            qtdTotal = qtdProduzida * componente.getQtd(); 
                            estoque.setValorAtual( estoque.getValorAtual() - qtdTotal);
                            JOptionPane.showMessageDialog(null, ""+estoque.getValorAtual());
                            qtdTotal =0; 
                            //Se o estoque do insumo atingir níveis críticas deve-se solicitar o material
                            if(estoque.getValorAtual() <= estoque.getValorminimo())
                                this.solitarMaterial(insumo);
                            break; 
                        }
                    }
                    
                }
            }
        }
        
    }
    
    private void solitarMaterial(Produto produto){
        if(produto instanceof Insumo){
            List<Fornecedor> fornecedor= ((Insumo)produto).getFornecedor(); 
            String nomeIsumo = produto.getNome(); 
            Estoque estoque=  produto.getEstoque(); 
            float qtd =  (estoque.getValorMaximo() - estoque.getValorminimo()); 
            SolicitacaoDeMaterial solicitacao = new SolicitacaoDeMaterial(null, nomeIsumo, qtd); 
            this.addSolicitacao(solicitacao);
        }
    }
    
    //metodo para adicionar os produtos 
    public void setProdutos(ArrayList<Produto> produtos){
        this.produtos =  produtos; 
    }
    
    //Metodo para retornar a qtd de produtos cadastrados 
    public int getQtdProdutos(){
        return this.produtos.size(); 
    }
    
    //Metodo para adicinar cada solicitação de isumos realizada
    private void addSolicitacao(SolicitacaoDeMaterial solicitacao){
        this.solicitacoes.add(solicitacao); 
        System.out.println(""+solicitacao.toString());
        JOptionPane.showMessageDialog(null, "Insumo com Estoque baixo,solicitação realizada");
    }
    
    //Retorna uma String com o  nome do produto e o Estoque associado
    public String getInformacaoProduto(String nomeProduto){
        
        for(Produto produto: this.produtos)
            if(produto.getNome().equals(nomeProduto)){
                return produto.toString(); 
            }
        return "Produto não encontrado"; 
        
    }
  
}
