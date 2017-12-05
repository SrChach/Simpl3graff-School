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

@WebServlet("/AdminCreateGroup")
public class AdminCreateGroup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion= (HttpSession)request.getSession();
        String taip = sesion.getAttribute("type").toString();
        
        ArrayList<User> usuarios = jsonToAl();
        ArrayList<User> profesores = new ArrayList();
        ArrayList<User> alumnos = new ArrayList();
        
        if(taip.equals("admin")){
        
            for(User temp: usuarios){
                if(temp.getType().equals("Alumno")){
                    alumnos.add(temp);
                } else {
                    profesores.add(temp);
                }
            }

            try(PrintWriter out = response.getWriter()){

                response.setContentType("text/html;charset=UTF-8");
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<title>Simpl3graff</title>");
                out.println("<link href=\"https://fonts.googleapis.com/css?family=Advent+Pro|Courgette\" rel=\"stylesheet\">");
                out.println("<link rel=\"stylesheet\" href=\"css/materialize.min.css\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">");
                out.println("<link rel=\"stylesheet\" href=\"css/Simpl3graff.css\">");
                out.println("<link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">");
                out.println("</head>");
                out.println("<body>");

                out.println("<section class=\"main-container\">");
                out.println("<div class=\"container\">");
                out.println("<form class=\"row abajo signup-box\" action=\"AdminCreateGroup\" method=\"post\">");

                out.println("<div class=\"col s6\">");
                out.println("<div class=\"row\">");
                out.println("<div class=\"section\">");

                int q = 0;
                for(q=0; q<profesores.size(); q++){
                    out.println("<input name=\"profe\" type=\"radio\" id=\"test"+q+"\" value=\""+q+"\" />");
                    out.println("<label for=\"test"+q+"\">añadir</label>");
                    out.println("<span>nombre del profesor: "+profesores.get(q).getName());
                    out.println(" Username: "+profesores.get(q).getUsername()+"</span><br/>");
                }
                out.println("<div class=\"container\">");
                out.println("<input name=\"nomGrupo\" type=\"text\" placeholder=\"ponga el nombre del grupo\" required/>");
                out.println("</div>");

                out.println("</div>");
                out.println("</div>");
                out.println("</div>");

                out.println("<div class=\"col s6\">");
                out.println("<div class=\"row\">");
                out.println("<div class=\"section\">");

                for(q=0; q<alumnos.size(); q++){
                    out.println("<input name=\"alumno"+q+"\" type=\"checkbox\" id=\"prueba"+q+"\" value=\""+q+"\" />");
                    out.println("<label for=\"prueba"+q+"\">Añadir</label>");
                    out.println("<span>nombre del alumno: "+alumnos.get(q).getName());
                    out.println(" Username: "+alumnos.get(q).getUsername()+"</span><br/>");
                }

                out.println("</div>");
                out.println("</div>");
                out.println("</div>");



                out.println("<input class=\"btn btn-signup boton-type1\" type=\"submit\" value=\"Salvar grupo\"/>");
                out.println("</form>");
                out.println("</div>");
                out.println("<br/>");

                out.println("</section>");
                out.println("<footer class=\"main-footer page-footer\">");
                out.println("<div class=\"container\">");
                out.println("<div class=\"row abajo foot-pad\">");
                out.println("<div class=\"col s6 center-align\">");
                out.println("Proyecto de TPLWeb <br/>");
                out.println("Esta página usa <a href=\"http://materializecss.com/\">materializecss</a> y <a href=\"http://nvd3.org\">NDV3</a>");
                out.println("</div>");
                
                out.println("<div class=\"col s4 push-s2 center-align\">");
                out.println("<a class=\"dropdown-button btn btn-flat\" href=\"#\" data-activates=\"dropdown1\">Ir A</a>");
                out.println("<ul id=\"dropdown1\" class=\"dropdown-content\">");
                out.println("<li><a href=\"Signup\">Registrar Usuario</a></li>");
                out.println("<li><a href=\"AdminDelete\">Ver/Borrar Usuarios</a></li>");
                out.println("<li><a href=\"AdminDeleteGroup\">Ver Grupos</a></li>");
                out.println("<li><a href=\"AdminCreateGroup\">Crear Grupo</a></li>");
                out.println("<li><a href=\"Signin\">Salir</a></li>");
                out.println("</ul>");
                out.println("</div>");

                out.println("</div>");
                out.println("</div>");
                out.println("</footer>");
                out.println("<script src=\"build/jquery-3.2.1.min.js\"></script>");
                out.println("<script src=\"build/materialize.min.js\"></script>");
                out.println("</body>");
                out.println("</html>");

            }
            catch (Exception e){
                System.out.println("Error de lectura del fichero");
            }
            
        } else {
            resp(response, "Algo ha ido mal", "Signin");
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
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
        String nombrecito = request.getParameter("nomGrupo");
        ArrayList<Group> valida = jsonToAlGroup(); 
        int r = 0;
        for(Group f: valida){
            if(f.getGroupName().equals(nombrecito)){
                r++;
                break;
            }
        }
        
        if( r == 0){
            grupo.setGroupName(nombrecito);
            grupo.setProfessorUsername(profesores.get(numProfe).getUsername());
            grupo.setStudentUsername(selectedStudent);
            String json = mapper.writeValueAsString(grupo);
            modifyArchive(json);
            response.sendRedirect("AdminDeleteGroup");
        } else {
            resp(response, "El nombre de grupo ya está en uso", "AdminCreateGroup");
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
        System.out.println(docBase + "\n");
        FileReader fr = new FileReader(docBase);
        BufferedReader br = new BufferedReader(fr);
        ObjectMapper mapper = new ObjectMapper();
        String json;
            if((json=br.readLine())!=null){
                json = "[" + json + "]"; 
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
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fichero = new FileWriter(docBase, true);
            fr = new FileReader(docBase);
            br = new BufferedReader(fr);
            pw = new PrintWriter(fichero);
            int caract = fr.read();
            if(caract != -1){
                pw.print(",");
            }
            pw.print(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero)
                    fichero.close();
                if(null != fr)
                    fr.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    
    private ArrayList<Group> jsonToAlGroup() throws FileNotFoundException, IOException{
        String docBase = getServletConfig().getServletContext().getRealPath("/") + "grupos.json";
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
        ArrayList<Group> grupo = mapper.readValue(json,new TypeReference<ArrayList<Group>>(){} );
        try{
            if(null != fr)
                fr.close();
        }catch(Exception e2){
            e2.printStackTrace();
        }
        return grupo;
    }

}
