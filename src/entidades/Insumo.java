
package entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rodrigo
 */
public class Insumo extends Produto{
    
   // private String fornecedor; 
    private List<Fornecedor> fornecedor; 

    public Insumo(String fornecedor, String nome, String formulaQuimica, int grauToxico) {
        super(nome, formulaQuimica, grauToxico);
       // this.fornecedor = fornecedor;
        this.fornecedor = new ArrayList(); 
        this.TIPO="PI";
    }
/*
    public String getFornecedor() {
        return this.fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }
 */

    public List<Fornecedor> getFornecedor() {
        return this.fornecedor;
    }

    public void setFornecedor(List<Fornecedor> fornecedor) {
        this.fornecedor = fornecedor;
    }
    
    //Retorna o nome e concatena a String Estoque
    @Override
    public String toString() {
        Estoque estoque = this.getEstoque(); 
       // return "Insumo{Nome: " + this.getNome()+" | "+ estoque.toString()+'}';
       return "" + super.toString() + "|" /*+ "Fornecedor" + this.getFornecedor()*/; 
    }
 
}
