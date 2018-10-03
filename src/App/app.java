
package App;


import controler.ControlerViewProdutos;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import persistencia.IComponenteDao;
import persistencia.IFornecedorDao;
import persistencia.IProdutoDao;
import persistenciaSql.ComponenteDao;
import persistenciaSql.ConnectionDB;
import persistenciaSql.FornecedorDao;
import persistenciaSql.ProdutoDao;
import view.Componentes;
import view.Principal;
import view.Teste;

public class app {

    public static void main(String[] args) throws Exception{
/*
        // Criação dos estoques dos insumos 
        Estoque estoque1 = new Estoque(5, 100, 55);
        Estoque estoque2 = new Estoque(3, 100, 35);
        Estoque estoque3 = new Estoque(7, 100, 37);
        Estoque estoque4 = new Estoque(10, 100, 40);
        Estoque estoque5 = new Estoque(2, 100, 27);
        
        // Criação dos estoques dos produtos Manufaturados 
        Estoque estoqueM1 = new Estoque(10, 50, 25);
        Estoque estoqueM2 = new Estoque(9, 50, 30);
        Estoque estoqueM3 = new Estoque(7, 50, 36);
        Estoque estoqueM4 = new Estoque(10, 50, 50);
        Estoque estoqueM5 = new Estoque(5, 50, 32);

        // Crição dos insumos 
        Insumo insumo1 = new Insumo("Rodrigo Silva", "Agua", "H2O", 0);
        Insumo insumo2 = new Insumo("Rodrigo Silva", "Oleo", "oleo", 1);
        Insumo insumo3 = new Insumo("Rodrigo Silva", "Sal", "sal", 0);
        Insumo insumo4 = new Insumo("Gilvan", "Vinho", "vinho", 1);
        Insumo insumo5 = new Insumo("Gilvan", "Açucar", "açucar", 0);

        insumo1.setEstoque(estoque1);
        insumo2.setEstoque(estoque2);
        insumo3.setEstoque(estoque3);
        insumo4.setEstoque(estoque4);
        insumo5.setEstoque(estoque5);

        // Criação dos fornecedores 
        Fornecedor fornecedor1 = new Fornecedor("Rodrigo Silva", "190993", "rodrigo.oliveira");
        Fornecedor fornecedor2 = new Fornecedor("Gilvan Santos", "202020", "gilva.santos@terra.com.br");
        Fornecedor fornecedor3 = new Fornecedor("JANETE DE OLIVEIRA SILVA", "1000", "janete.oliveira@yahoo.com.br");

        fornecedor1.addInsumo(insumo1);
        fornecedor1.addInsumo(insumo2);
        fornecedor1.addInsumo(insumo3);

        fornecedor2.addInsumo(insumo4);
        fornecedor2.addInsumo(insumo5);

        //Criação dos Componenetes para os produtos manufaturados 
        Componente componente1 = new Componente("Agua", 5);
        Componente componente1_1 = new Componente("Oleo", 3);
        Componente componente2 = new Componente("Oleo", 2);
        Componente componente2_2 = new Componente("Sal", 5);
        Componente componente3 = new Componente("Sal", 2);
        Componente componente3_3 = new Componente("Vinho", 7);
        Componente componente4 = new Componente("Vinho", 5);
        Componente componente4_4 = new Componente("Açucar", 2);
        Componente componente5 = new Componente("Vinho", 10);
        Componente componente5_5 = new Componente("Agua", 2);

        // Criação dos produtos manufaturados
        ProdutoManufaturado produtoM1 = new ProdutoManufaturado("O Poder da Ação", "FACS", 200);
        ProdutoManufaturado produtoM2 = new ProdutoManufaturado("Oleo com Sal", "oleosal", 1);
        ProdutoManufaturado produtoM3 = new ProdutoManufaturado("Sal com Vinho", "salvinho", 1);

        ProdutoManufaturado produtoM4 = new ProdutoManufaturado("Vinho com Açucar", "vinhoaçucar", 1);
        ProdutoManufaturado produtoM5 = new ProdutoManufaturado("Vinho com Agua", "VINHOAGUA", 30);
        
        produtoM1.addComponente(componente1);
        produtoM1.addComponente(componente1_1);
        produtoM2.addComponente(componente2);
        produtoM2.addComponente(componente2_2);
        produtoM3.addComponente(componente3);
        produtoM3.addComponente(componente3_3);
        produtoM4.addComponente(componente4);
        produtoM4.addComponente(componente4_4);
        produtoM5.addComponente(componente5);
        produtoM5.addComponente(componente5_5);
        
        produtoM1.setEstoque(estoqueM1);
        produtoM2.setEstoque(estoqueM2);
        produtoM3.setEstoque(estoqueM3);
        produtoM4.setEstoque(estoqueM4);
        produtoM5.setEstoque(estoqueM5);
        
        //Criação da Classe Controlador de Produtos e adicionando todos os produtos
     
        ControladorProdutos controlador = new ControladorProdutos(); 
        controlador.addProduto(produtoM1);
        controlador.addProduto(produtoM2);
        controlador.addProduto(produtoM3);
        controlador.addProduto(produtoM4);
        controlador.addProduto(produtoM5);
        
        controlador.addProduto(insumo1);
        controlador.addProduto(insumo2);
        controlador.addProduto(insumo3);
        controlador.addProduto(insumo4);
        controlador.addProduto(insumo5);
      
        //System.out.println("Tenho "+controlador.getQtdProdutos()+ " produtos");
        System.out.println("Produção: ");
        controlador.registrarProducao("Agua com Oleo", 10);
        System.out.println("Estoque Agua com Oleo: "+controlador.getInformacaoProduto("Agua com Oleo"));
        System.out.println("Estoque dos insumos: "+controlador.getInformacaoProduto("Agua"));
        System.out.println("Estoque dos insumos: "+controlador.getInformacaoProduto("Oleo"));
        System.out.println("");
        controlador.registrarProducao("Oleo com Sal", 10);
        System.out.println("Estoque Oleo com Sal: "+controlador.getInformacaoProduto("Oleo com Sal"));
        System.out.println("Estoque dos insumos: "+controlador.getInformacaoProduto("Oleo"));
        System.out.println("Estoque dos insumos: "+controlador.getInformacaoProduto("Sal"));
        
        ConnectionDB con = new ConnectionDB(); 
        con.getConexao(); 
        
 
        Produto pr; 
        IProdutoDao iProduto = new ProdutoDao(); 
        IFornecedorDao fornecedor = new FornecedorDao();
        IComponenteDao iComponente = new ComponenteDao(); 
        //componente1.setProdutoId(7);
       // iComponente.deleteComponete(componente1);
     //  Componentes viewComponete =  new Componentes(); 
     //  viewComponete.setVisible(true);
         //fornecedor.getNomeFornecedorID(10);
       // fornecedor.updateFornecedor(fornecedor3);
        //fornecedor.salvarFornecedor(fornecedor2);
        //fornecedor.salvarFornecedor(fornecedor3);
       // ProdutoManufaturado M1 = (ProdutoManufaturado) produto.getProdutoId(5); 
        // produto.salvar(produtoM3);
        //System.out.println(""+produto.getProdutoId(6));
        // System.out.println(""+produto.getProdutoId(5));
        // produto.updateProduto(produtoM1);
       // produto.deleteProdutoId(8);
          Principal p =  new Principal(); 
          p.setVisible(true);
          
          //Teste t = new Teste(); 
          //t.setVisible(true);
        
       // String valor = ""; 
        //int valorInt = Integer.parseInt(valor); 
        //System.out.println("Valor Inteiro: "+valorInt);
        /*
        List<Produto> produtos = new ArrayList(); 
        produtos = iProduto.getProdutos(); 
        for(int iCont=0; iCont < produtos.size(); iCont++){
            pr = produtos.get(iCont); 
            System.out.println(""+ pr.toString());
            
        }
        */
        // System.out.println("Numero de produtos"+produtos.size());
        
        Principal p =  new Principal(); 
        p.setVisible(true);

       
        /*
        List<Fornecedor> fornecedores; 
        Fornecedor f; 
        IFornecedorDao iFornecedorDao = new FornecedorDao();
        fornecedores = iFornecedorDao.getFornecedores(20); 
        for(int i= 0; i< fornecedores.size();i++){
            f = fornecedores.get(i); 
            System.out.println(""+f.toString());
        }
        */ 
    }


}
