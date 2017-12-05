
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;


public class ArrayMap {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Group> grupos = new ArrayList();
        Group grupo1 = new Group();
        grupo1.setGroupName("Grupillo Rivera :v");
        grupo1.setProfessorUsername("profesor");
        ArrayList<String> nom = new ArrayList();
        nom.add("nacho");
        nom.add("pancho");
        nom.add("chach");
        nom.add("zombye");
        grupo1.setStudentUsername(nom);

        Group grupo2 = new Group();
        grupo2.setGroupName("EspinozitaxD");
        grupo2.setProfessorUsername("nombre del profe");
        ArrayList<String> neim = new ArrayList();
        neim.add("uno");
        neim.add("dos");
        neim.add("tres");
        neim.add("cuatro");
        grupo2.setStudentUsername(neim);

        grupos.add(grupo1);
        grupos.add(grupo2);
        
        String json = mapper.writeValueAsString(grupos);
        System.out.println(json);
    }
    
    private void insertGroup(ArrayList<Group> grupos, Group grupo){
        
    }
    
}
