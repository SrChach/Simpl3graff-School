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

@WebServlet("/AdminDeleteGroup")
public class AdminDeleteGroup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession sesion= (HttpSession)request.getSession();
        String taip = sesion.getAttribute("type").toString();
        
        if(taip.equals("admin")){
            ArrayList<Group> grupo = jsonToAl();
        
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
                out.println("<div class=\"row abajo signup-box\">");
                out.println("<div class=\"col s12\">");


                int q;
                for(q=0; q<grupo.size(); q++){
                    out.println("<div class=\"row\">");
                    out.println("<div class=\"col s4\">");
                    out.println("<span>"+grupo.get(q).getGroupName()+"</span>");
                    out.println("</div>");
                    out.println("<form action=\"AdminDeleteGroup\" method=\"post\" class=\"col s4\">");
                    out.println("<input name=\"grupo\" type=\"radio\" class=\"hide\" value=\""+q+"\" checked/>");
                    out.println("<input name=\"tipo\" type=\"radio\" class=\"hide\" value=\"borrar\" checked/>");
                    out.println("<input class=\"btn btn-signup boton-type1\" type=\"submit\" value=\"Borrar grupo\"/>");
                    out.println("</form>");
                    out.println("<form action=\"AdminDeleteGroup\" method=\"post\" class=\"col s4\">");
                    out.println("<input name=\"grupo\" type=\"radio\" class=\"hide\" value=\""+q+"\" checked/>");
                    out.println("<input name=\"tipo\" type=\"radio\" class=\"hide\" value=\"modificar\" checked/>");
                    out.println("<input class=\"btn btn-signup boton-type1\" type=\"submit\" value=\"modificar grupo\"/>");
                    out.println("</form>");
                    out.println("");
                    out.println("</div>");
                }


                out.println("</div>");
                out.println("</div>");
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
        
        HttpSession sesion= (HttpSession)request.getSession();
        String taip = sesion.getAttribute("type").toString();
        
        if(taip.equals("admin")){
            int num;
            String tipo = request.getParameter("tipo");
            num = Integer.parseInt(request.getParameter("grupo"));

            if(tipo.equals("modificar")){
                option1(request, response, num);
            } else {
                option2(request, response, num);
            }
        } else {
            resp(response, "Algo ha ido mal", "Signin");
        }
        
        
        
    }
    
    protected void option1(HttpServletRequest request, HttpServletResponse response, int num)//Obtiene un grupo
	throws ServletException, IOException {
        ArrayList<Group> grupos = jsonToAl();
        Group temp = new Group();
        temp = grupos.get(num);
        String nombreDelGrupo = temp.getGroupName();
        String nombreDelProfesor = temp.getProfessorUsername();
        ArrayList<String> nombresDeAlumnos = new ArrayList();
        for(String i: temp.getStudentUsername()){
            nombresDeAlumnos.add(i);
        }
        
        ArrayList<User> usuarios = jsonToAlUsers();
        ArrayList<User> profesores = new ArrayList();
        ArrayList<User> alumnos = new ArrayList();
        for(User usuario: usuarios){
            if(usuario.getType().equals("Alumno")){
                alumnos.add(usuario);
            } else {
                profesores.add(usuario);
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
            out.println("<form action=\"AdminModifyGroup\" method=\"post\" class=\"row abajo signup-box\">");
            
            out.println("<div class=\"col s6\">");
            out.println("<div class=\"row\">");
            out.println("<div class=\"section\">");

            int q = 0;
            for(q=0; q<profesores.size(); q++){
                if(nombreDelProfesor.equals(profesores.get(q).getUsername())){
                    out.println("<input name=\"profe\" type=\"radio\" id=\"test"+q+"\" value=\""+q+"\" checked />");
                } else {
                    out.println("<input name=\"profe\" type=\"radio\" id=\"test"+q+"\" value=\""+q+"\" />");
                }
                out.println("<label for=\"test"+q+"\">añadir</label>");
                out.println("<span>nombre del profesor: "+profesores.get(q).getName());
                out.println(" Username: "+profesores.get(q).getUsername()+"</span><br/>");
            }
            out.println("<div class=\"container\">");
            out.println("<input name=\"nomGrupo\" type=\"text\" value=\"Nombre del Grupo: "+nombreDelGrupo+"\" class=\"validate\" required disabled/>");
            out.println("<input name=\"nomGrupo\" type=\"radio\" value=\""+nombreDelGrupo+"\" checked class=\"hide\"/>");
            out.println("</div>");
            
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            
            out.println("<div class=\"col s6\">");
            out.println("<div class=\"row\">");
            out.println("<div class=\"section\">");

            for(q=0; q<alumnos.size(); q++){
                int x = 0;
                for(String busca: nombresDeAlumnos){
                    if(alumnos.get(q).getUsername().equals(busca)){
                        out.println("<input name=\"alumno"+q+"\" type=\"checkbox\" id=\"prueba"+q+"\" value=\""+q+"\" checked/>");
                        x=1;
                        break;
                    }
                }
                if(x==0){
                    out.println("<input name=\"alumno"+q+"\" type=\"checkbox\" id=\"prueba"+q+"\" value=\""+q+"\" />");
                }
                
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
            out.println("<div class=\"col s5 center-align\">");
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
        
    }
    
    protected void option2(HttpServletRequest request, HttpServletResponse response, int num)//Obtiene un grupo
	throws ServletException, IOException {
        ArrayList<Group> grupos = jsonToAl();
        
        String nombre = grupos.get(num).getGroupName();
        ArrayList<Publish> publicacion = jsonToAlPublish();
        int n;
        for(n=0;n<publicacion.size(); n++){
            if(publicacion.get(n).getGroupName().equals(nombre)){
                publicacion.remove(n);
                if(n>0){    n--; }
            }
        }
        modifyArchivePublish(alToJsonPublish(publicacion));
        
        grupos.remove(num);
        String json = alToJson(grupos);
        modifyArchive(json);
        response.sendRedirect("AdminDeleteGroup");
    }
    
    private ArrayList<Publish> jsonToAlPublish() throws FileNotFoundException, IOException{
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

    private ArrayList<Group> jsonToAl() throws FileNotFoundException, IOException{
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
    
    private String alToJson(ArrayList<Group> grupo) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String temp = mapper.writeValueAsString(grupo);
        String json = temp.substring(1, temp.length() - 1);
        return json;
    }
    
    private String alToJsonPublish(ArrayList<Publish> publicacion) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String temp = mapper.writeValueAsString(publicacion);
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
    
    private ArrayList<User> jsonToAlUsers() throws FileNotFoundException, IOException{
        String docBase = getServletConfig().getServletContext().getRealPath("/") + "newjson.json";
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
        ArrayList<User> usuarios = mapper.readValue(json,new TypeReference<ArrayList<User>>(){} );
        try{
            if(null != fr)
                fr.close();
        }catch(Exception e2){
            e2.printStackTrace();
        }
        return usuarios;
    }
    
    private void modifyArchivePublish(String json) throws FileNotFoundException{
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
