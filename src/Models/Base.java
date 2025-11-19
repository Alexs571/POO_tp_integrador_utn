
package Models;

/**
 *
 * @author Tobías
 */
public abstract class Base {
    
    private long id;
    private boolean eliminado;

    public Base(int id, boolean eliminado) {
        this.id = id;
        this.eliminado = eliminado;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Base(boolean eliminado) {
        this.eliminado = eliminado;
    }
    
    public Base(){
    };
    
    
}
