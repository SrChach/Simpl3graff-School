import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfessorDeletePublish extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("ProfessorMain");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            HttpSession sesion= (HttpSession)request.getSession();
            String taip = sesion.getAttribute("type").toString();
        
            if(taip.equals("Profesor")){
                String nombrePublicacion = request.getParameter("nombrePublicacion");
                ArrayList<Publish> pub = jsonToAl();
                int i;
                for(i=0; i<pub.size();i++){
                    if(pub.get(i).getNombrePublicacion().equals(nombrePublicacion)){
                        pub.remove(i);
                    }
                }
                modifyArchive(alToJson(pub));
                response.sendRedirect("ProfessorMain");
            } else {
                resp(response, "Algo ha ido mal", "Signin");
            }
 
    }
    
    private void resp(HttpServletResponse response, String msg, String redirect)
			throws IOException {
	PrintWriter out = response.getWriter();
	out.println("<html>");
	out.println("<body>");
	out.println("<t1>" + msg + "</t1>");
        out.println("<br/><a href=\""+redirect+"\">Volver</a>");
        out.println("</body>");
	out.println("</html>");
    }

    private String alToJson(ArrayList<Publish> publicacion) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String temp = mapper.writeValueAsString(publicacion);
        String json = temp.substring(1, temp.length() - 1);
        return json;
    }
    
    private ArrayList<Publish> jsonToAl() throws FileNotFoundException, IOException{
        String docBase = getServletConfig().getServletContext().getRealPath("/") + "publish.json";
        System.out.println(docBase + "\n");
        FileReader fr = new FileReader(docBase);
        BufferedReader br = new BufferedReader(fr);
        ObjectMapper mapper = new ObjectMapper();
        String json;
            if((json=br.readLine())!=null){
                json = "[" + json + "]"; 
            } else {
                json = "[]";
            }
        ArrayList<Publish> publicacion = mapper.readValue(json,new TypeReference<ArrayList<Publish>>(){} );
        try{
            if(null != fr)
                fr.close();
        }catch(Exception e2){
            e2.printStackTrace();
        }
        return publicacion;
    }
    
    private void modifyArchive(String json) throws FileNotFoundException{
        String docBase = getServletConfig().getServletContext().getRealPath("/") + "publish.json";
        FileWriter fichero = null;
        PrintWriter pw = null;
        try{
            fichero = new FileWriter(docBase);
            pw = new PrintWriter(fichero);
            pw.print(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    

}
