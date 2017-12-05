import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
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

@WebServlet("/Signup")
public class Signup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        
        HttpSession sesion= (HttpSession)request.getSession();
        String taip = sesion.getAttribute("type").toString();
        
        if(taip.equals("admin")){
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
                out.println("<div class=\"col s10\">");
                out.println("<div class=\"row\">");
                out.println("<div class=\"col s12 m7\">");
                out.println("<div class=\"signup-box\">");
                out.println("<div class=\"row\">");
                out.println("<h1>Simpl3graff</h1>");
                out.println("<div class=\"signup-form\">");
                out.println("<h2>Registro de nuevo usuario</h2><i class=\"small material-icons\">account_circle</i>");
                out.println("<div class=\"section\">");
                out.println("<div>");
                out.println("<form action=\"Signup\" method=\"post\">");
                out.println("<input type=\"email\" name=\"email\" placeholder=\"Correo electrónico\"/>");
                out.println("<input type=\"text\" name=\"name\" placeholder=\"Nombre completo\"/>");
                out.println("<input type=\"text\" name=\"username\" placeholder=\"Nombre de usuario\"/>");
                out.println("<input type=\"password\" name=\"password\" placeholder=\"Contraseña\"/>");
                out.println("<br/>");
                out.println("<div>");
                out.println("<input name=\"type\" type=\"radio\" id=\"test1\" value=\"Alumno\" checked/>");
                out.println("<label for=\"test1\">Alumno</label>");
                out.println("<input name=\"type\" type=\"radio\" id=\"test2\" value=\"Profesor\" />");
                out.println("<label for=\"test2\">Profesor</label>");
                out.println("</div>");
                out.println("<br/>");
                out.println("<input class=\"btn btn-signup boton-type1\" type=\"submit\" value=\"Registrate\"/>");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("<div class=\"col m5 hide-on-small-only\">");
                out.println("<img class=\"graph\" src=\"public/graph.jpg\"/>");
                out.println("<a class=\"btn btn-fb github\" href=\"AdminDelete\">Borrar Usuarios</a>");
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
                out.println("Proyecto de TPLWeb <br/>\n Esta página usa <a href=\"http://materializecss.com/\">materializecss</a> y <a href=\"http://nvd3.org\">NDV3</a>");
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
        /*Escribe usuarios en el JSON*/
        User usuario = new User();
        
        ArrayList<User> temp = jsonToAl();
        
        usuario.setEmail(request.getParameter("email"));
        usuario.setName(request.getParameter("name"));
        usuario.setUsername(request.getParameter("username"));
        usuario.setPassword(request.getParameter("password"));
        usuario.setType(request.getParameter("type"));

        int y=0;
        
        for(User t: temp){
            if(t.getUsername().equals(usuario.getUsername())){
                resp(response, "nombre de usuario en uso, elija otro por favor", "Signup");
                y=1;
                break;
            }
        }
        
        if(y == 0){
            FileWriter fichero = null;
            FileReader fr = null;
            PrintWriter pw = null;

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(usuario);

            try{
                /* Linea shida aqui */

                String docBase = getServletConfig().getServletContext().getRealPath("/") + "newjson.json";
                System.out.println(docBase);
                fichero = new FileWriter(docBase, true);
                fr = new FileReader(docBase);
                /* aqui termina */
                pw = new PrintWriter(fichero);
                int caract = fr.read();
                if(caract != -1){
                    pw.print(",");
                }
                pw.print(jsonString);
            }catch(Exception e){
                e.printStackTrace();
            } finally {
                try{
                    if(null != fichero)
                        fichero.close();
                    if(null != fr)
                        fr.close();
                }catch(Exception e2){
                    e2.printStackTrace();
                }
            }

            resp(response, "Usuario correctamente registrado", "Signup");
            System.out.println(jsonString);
        
        }
        
    }

    private void resp(HttpServletResponse response, String msg, String redirect)
                    throws IOException {
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<body>");
            out.println("<t1>" + msg + "</t1>");
            out.println("<a href=\""+redirect+"\">Volver</a>");
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

}
