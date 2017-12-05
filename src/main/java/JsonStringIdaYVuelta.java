
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;


public class JsonStringIdaYVuelta {
    public static void main(String[] args) throws JsonProcessingException, IOException {
        System.out.println("hola");
        ObjectMapper mapper = new ObjectMapper();
        
        User uno = new User();
        User dos = new User();
        User tres = new User();
        uno.setEmail("uno");
        uno.setName("uno");
        uno.setUsername("uno");
        uno.setPassword("uno");
        dos.setEmail("dos");
        dos.setName("dos");
        dos.setUsername("dos");
        dos.setPassword("dos");
        tres.setEmail("tres");
        tres.setName("tres");
        tres.setUsername("tres");
        tres.setPassword("tres");
        
        ArrayList<User> lista = new ArrayList<>();
        lista.add(uno);
        lista.add(dos);
        lista.add(tres);
        
        String json = mapper.writeValueAsString(lista);
        System.out.println(json);
        
        ArrayList<User> reconstructed = mapper.readValue(json,new TypeReference<ArrayList<User>>(){} );
        
        int a = 0;
        for(User i: reconstructed){
            a++;
            System.out.println("usuario "+a+":\n");
            System.out.println(i.getName() + " " + i.getEmail() + " " + i.getUsername() + " " + i.getPassword());
        }
        /*
        int i=0;
        for(i=0; i<reconstructed.size(); i++){
            System.out.println(reconstructed.get(i).getUsername()+"\n"+ reconstructed.get(i).getPassword());
        }
        */
    }
}
