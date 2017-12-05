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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AdminModifyGroup")
public class AdminModifyGroup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Signup");
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
        
        HttpSession sesion= (HttpSession)request.getSession();
        String taip = sesion.getAttribute("type").toString();
        
        if(taip.equals("admin")){
            
            ObjectMapper mapper = new ObjectMapper();
            int q;
            ArrayList<User> usuarios = jsonToAl();
            ArrayList<User> profesores = new ArrayList();
            ArrayList<User> alumnos = new ArrayList();
            for(User temp: usuarios){
                if(temp.getType().equals("Alumno")){
                    alumnos.add(temp);
                } else {
                    profesores.add(temp);
                }
            }

            int numProfe = Integer.parseInt(request.getParameter("profe"));
            ArrayList<String> selectedStudent = new ArrayList();
            String temp, salida;
            for(q=0; q<alumnos.size(); q++){
                temp = request.getParameter("alumno" + q);
                if(temp != null){
                    selectedStudent.add(alumnos.get(Integer.parseInt(temp)).getUsername());
                }
            }

            Group grupo = new Group();
            grupo.setGroupName(request.getParameter("nomGrupo"));
            grupo.setProfessorUsername(profesores.get(numProfe).getUsername());
            grupo.setStudentUsername(selectedStudent);

            ArrayList<Group> todos = jsonToAlGroup();

            int r;
            for(r=0; r<todos.size(); r++){
                if(todos.get(r).getGroupName().equals(grupo.getGroupName())){
                    todos.remove(r);
                    todos.add(grupo);
                    break;
                }
            }


            String json = mapper.writeValueAsString(todos);
            json = json.substring(1, json.length() - 1);
            modifyArchive(json);
            response.sendRedirect("AdminDeleteGroup");
            
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
    
    private ArrayList<User> jsonToAl() throws FileNotFoundException, IOException{
        String docBase = getServletConfig().getServletContext().getRealPath("/") + "newjson.json";
        FileReader fr = new FileReader(docBase);
        BufferedReader br = new BufferedReader(fr);
        ObjectMapper mapper = new ObjectMapper();
        String json;
            if((json=br.readLine())!=null){
                json = "[" + json + "]"; 
            } else {
                json = "[]";
            }
        ArrayList<User> usuarios = mapper.readValue(json,new TypeReference<ArrayList<User>>(){} );
        try{
            if(null != fr)
                fr.close();
        }catch(Exception e2){
            e2.printStackTrace();
        }
        return usuarios;
    }
    
    private ArrayList<Group> jsonToAlGroup() throws FileNotFoundException, IOException{
        String docBase = getServletConfig().getServletContext().getRealPath("/") + "grupos.json";
        FileReader fr = new FileReader(docBase);
        BufferedReader br = new BufferedReader(fr);
        ObjectMapper mapper = new ObjectMapper();
        String json;
            if((json=br.readLine())!=null){
                json = "[" + json + "]"; 
            } else {
                json = "[]";
            }
        ArrayList<Group> grupo = mapper.readValue(json,new TypeReference<ArrayList<Group>>(){} );
        try{
            if(null != fr)
                fr.close();
        }catch(Exception e2){
            e2.printStackTrace();
        }
        return grupo;
    }
    
    private String alToJson(ArrayList<User> usuarios) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String temp = mapper.writeValueAsString(usuarios);
        String json = temp.substring(1, temp.length() - 1);
        return json;
    }
    
    private void modifyArchive(String json) throws FileNotFoundException{
        String docBase = getServletConfig().getServletContext().getRealPath("/") + "grupos.json";
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
