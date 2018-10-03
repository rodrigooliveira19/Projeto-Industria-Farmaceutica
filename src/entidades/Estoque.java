
package entidades;

public class Estoque {
    
    private float valorminimo; 
    private float valorMaximo; 
    private float valorAtual; 

    public Estoque(float valorminimo, float valorMaximo, float valorAtual) {
        this.valorminimo = valorminimo;
        this.valorMaximo = valorMaximo;
        this.valorAtual = valorAtual;
    }

    public Estoque() {
    }

    
    public float getValorminimo() {
        return valorminimo;
    }

    public void setValorminimo(float valorminimo) {
        this.valorminimo = valorminimo;
    }

    public float getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(float valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public float getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(float valoratual) {
        this.valorAtual = valoratual;
    }

    @Override
    public String toString() {
        return "Estoque{" + "valorminimo=" + this.valorminimo + ", valorMaximo=" + this.valorMaximo + ", valorAtual=" + this.valorAtual + '}';
    } 
    
}
