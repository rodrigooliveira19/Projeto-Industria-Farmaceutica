
package entidades;

import java.util.ArrayList;
import java.util.List;


public class ProdutoManufaturado extends Produto{
    
    private List<Componente> componentes; 

    public ProdutoManufaturado(String nome, String formulaQuimica, int grauToxico) {
        super(nome, formulaQuimica, grauToxico);
        this.componentes = new ArrayList(); 
        this.TIPO="PM";
    }
    
    public int getQtdComponentes(){
        return this.componentes.size(); 
    }
    
    public Componente getComponete(int posi){
        return this.componentes.get(posi); 
    }
    
    public void addComponente(Componente outro){
        this.componentes.add(outro); 
    }
    
    public void setComponentes(ArrayList<Componente> componentes){
        this.componentes = componentes; 
    }
    
    @Override
    public String toString() {
        Estoque estoque = this.getEstoque(); 
       // return "Produto Manufaturado{Nome: " + this.getNome()+" | "+ estoque.toString() +'}';
       return ""+ super.toString(); 
    }
    



    
    
    
    
    
}
