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

public class AdminDelete extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession sesion= (HttpSession)request.getSession();
        String taip = sesion.getAttribute("type").toString();
        
        if(taip.equals("admin")){
            int q = 0;
            ArrayList<User> usuarios = jsonToAl();
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
                out.println("<div class=\"row abajo\">");
                out.println("<div class=\"col s12\">");
                out.println("<div class=\"row\">");
                out.println("<div class=\"signup-box\">");


                out.println("<form action=\"AdminDelete\" method=\"post\">");
                out.println("<br/>");
                out.println("<div>");
                for(q=0; q<usuarios.size(); q++){

                    out.println("<div class=\"row\">");
                    out.println("<div class=\"col s2\">");
                    out.println("<input name=\"number\" type=\"radio\" id=\"test"+q+"\" value=\""+q+"\"/>");
                    out.println("<label for=\"test"+q+"\">Eliminar</label>");
                    out.println("</div>");
                    out.println("<div class=\"col s5\">");
                    out.println("<span>nombre de usuario: "+usuarios.get(q).getUsername()+"</span>");
                    out.println("</div>");
                    out.println("<div class=\"col s5\">");
                    out.println("nombre real: "+usuarios.get(q).getName()+"</span><br/>");
                    out.println("</div>");
                    out.println("</div>");

                }
                out.println("</div>");
                out.println("<br/>");
                out.println("<input class=\"btn btn-signup boton-type1\" type=\"submit\" value=\"Elimina al usuario\"/>");
                out.println("</form>");

                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</section>");
                out.println("<footer class=\"main-footer\">");
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
        
        }else {
            resp(response, "Algo ha ido mal", "Signin");
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
        int num;
        num = Integer.parseInt(request.getParameter("number"));
        ArrayList<User> usuarios = jsonToAl();
        usuarios.remove(num);
        String json = alToJson(usuarios);
        modifyArchive(json);
        response.sendRedirect("AdminDelete");
		
    }
    
    
    
    private void resp(HttpServletResponse response, String msg, String redirect)
			throws IOException {
	PrintWriter out = response.getWriter();
	out.println("<html>");
	out.println("<body>");
	out.println("<t1>" + msg + "</t1>");
        out.println("<a href=\""+redirect+"\">volver</a>");
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
    
    private String alToJson(ArrayList<User> usuarios) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String temp = mapper.writeValueAsString(usuarios);
        String json = temp.substring(1, temp.length() - 1);
        return json;
    }
    
    private void modifyArchive(String json){
        String docBase = getServletConfig().getServletContext().getRealPath("/") + "newjson.json";
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
