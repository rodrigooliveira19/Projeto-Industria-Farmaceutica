
package entidades;

import java.util.List;

public class SolicitacaoDeMaterial {   
    private Fornecedor fornecedor; 
    private String insumo; 
    private float qtd; 

    //Construtor
    public SolicitacaoDeMaterial(Fornecedor fornecedor, String insumo, float qtd) {
        this.fornecedor = fornecedor;
        this.insumo = insumo;
        this.qtd = qtd;
    }
    
    //Getters e Setters
    public Fornecedor getFornecedor() {
        return this.fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getInsumo() {
        return this.insumo;
    }

    public void setInsumo(String insumo) {
        this.insumo = insumo;
    }

    public float getQtd() {
        return this.qtd;
    }

    public void setQtd(float qtd) {
        this.qtd = qtd;
    }
    
    //Metodos

    @Override
    public String toString() {
        return "SolicitacaoDeMaterial{" + "fornecedor=" + this.getFornecedor() + ", insumo=" + this.getInsumo() + 
                                                                                                ", qtd=" + this.getQtd() + '}';
    }
    
    
    
    
    
}
