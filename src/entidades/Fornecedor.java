
package entidades;


public class Fornecedor {
    
    private int fornecedorId; 
    private String nome; 
    private String cgc; 
    private String webService; 
    private Insumo[] insumos; 

    public Fornecedor(String nome, String cgc, String webService) {
        this.nome = nome;
        this.cgc = cgc;
        this.webService = webService;
        this.insumos =  new Insumo[0]; 
    }

    public int getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(int fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCgc() {
        return this.cgc;
    }

    public void setCgc(String cgc) {
        this.cgc = cgc;
    }

    public String getWebService() {
        return this.webService;
    }

    public void setWebService(String webService) {
        this.webService = webService;
    }
    
    public void addInsumo(Insumo outro){
        
        Insumo[] novoInsumos = new Insumo[this.insumos.length + 1]; 
        
        for(int iCont=0; iCont <this.insumos.length; iCont++ )
            novoInsumos[iCont] = insumos[iCont];
        
        novoInsumos[this.insumos.length]= outro; 
        insumos= novoInsumos; 
    }

    @Override
    public String toString() {
        return "Fornecedor{" + "fornecedorId=" + fornecedorId + ", nome=" + nome + ", cgc=" + cgc + ", webService=" + webService + '}';
    }   
    
}
