/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import entidades.Produto;
import java.util.ArrayList;

/**
 *
 * @author Rodrigo
 */
public interface IProdutoDao {
    
    public void salvarProduto(Produto produto) throws Exception; 
    public Produto getProdutoId(int produto_id) throws Exception; 
    public ArrayList<Produto> getProdutos() throws Exception; 
    public ArrayList<Produto> getProdutosTipo(String tipo) throws Exception; 
    public ArrayList<Produto> getProdutosFornecedorId(int fornecedorId) throws Exception; 
    public void updateProduto(Produto produto) throws Exception;
    public void updateEstoqueProduto(ArrayList<Produto> produtosModificados) throws Exception;
    public void deleteProdutoId(int produto_id) throws Exception; 
    public void salvarFonecedorProduto(int fornecedorId, int produtoId) throws Exception; 
    
}
