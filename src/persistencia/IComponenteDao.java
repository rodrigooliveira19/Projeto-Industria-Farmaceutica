
package persistencia;

import entidades.Componente;
import java.util.ArrayList;

public interface IComponenteDao {
    public void salvarComponente(Componente componenete) throws Exception; 
    public void deleteComponete(Componente componenete) throws Exception; 
    public ArrayList<Componente> getComponentes(int produto_id) throws Exception; 
    
}
