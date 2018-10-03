
package entidades;

public class Componente {
    
    private String descricao; 
    private float qtd; 
    private int produtoId; 

    public Componente(String descricao, float qtd) {
        this.descricao = descricao;
        this.qtd = qtd;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String insumo) {
        this.descricao = insumo;
    }

    public float getQtd() {
        return this.qtd;
    }

    public void setQtd(float qtd) {
        this.qtd = qtd;
    }

    public int getProdutoId() {
        return this.produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }
    
}
