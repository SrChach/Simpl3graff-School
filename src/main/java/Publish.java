
import java.util.ArrayList;

public class Publish {
    private String nombrePublicacion;
    private String groupName;
    private String descripcion;
    private String grafica1;
    private String grafica2;
    private String grafica3;
    private ArrayList<Comment> comentarios;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    
    public String getNombrePublicacion() {
        return nombrePublicacion;
    }

    public void setNombrePublicacion(String nombrePublicacion) {
        this.nombrePublicacion = nombrePublicacion;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGrafica1() {
        return grafica1;
    }

    public void setGrafica1(String grafica1) {
        this.grafica1 = grafica1;
    }

    public String getGrafica2() {
        return grafica2;
    }

    public void setGrafica2(String grafica2) {
        this.grafica2 = grafica2;
    }

    public String getGrafica3() {
        return grafica3;
    }

    public void setGrafica3(String grafica3) {
        this.grafica3 = grafica3;
    }

    public ArrayList<Comment> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comment> comentarios) {
        this.comentarios = comentarios;
    }
    
    
}
