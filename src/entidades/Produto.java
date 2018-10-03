/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Rodrigo
 */
public abstract class Produto  {
    
    protected String TIPO; 
    private int produtoId; 
    private String  nome; 
    private String formulaQuimica; 
    private int grauToxico; 
    private Estoque estoque; 

    public Produto(String nome, String formulaQuimica, int grauToxico) {
        this.nome = nome;
        this.formulaQuimica = formulaQuimica;
        this.grauToxico = grauToxico;
        this.estoque=null; 
    }
    
    

    public String getTipo() {
        return this.TIPO;
    }

    
    public int getProdutoId() {
        return this.produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFormulaQuimica() {
        return this.formulaQuimica;
    }

    public void setFormulaQuimica(String formulaQuimica) {
        this.formulaQuimica = formulaQuimica;
    }

    public int getGrauToxico() {
        return this.grauToxico;
    }

    public void setGrauToxico(int grauToxico) {
        this.grauToxico = grauToxico;
    }

    public Estoque getEstoque() {
        return this.estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return "Produto{" + "TIPO=" + TIPO + ", produtoId=" + produtoId + ", nome=" + nome + 
                ", formulaQuimica=" + formulaQuimica + ", grauToxico=" + grauToxico + ", estoque=" + estoque + '}';
    }
  
}
