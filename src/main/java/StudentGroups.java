import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StudentGroups extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession sesion= (HttpSession)request.getSession();
        String taip = sesion.getAttribute("type").toString();
        String uzername = sesion.getAttribute("username").toString();
        
        if(taip.equals("Alumno")){
            ArrayList<Group> grupos = jsonToAlGroup();
            ArrayList<String> gruposDelAlumno = new ArrayList();
            ArrayList<Publish> pubTemp = jsonToAlPublish();
            int i = 0;
            for(i=0; i<grupos.size(); i++){
                for(String encontrar: grupos.get(i).getStudentUsername()){
                    if(encontrar.equals(uzername)){
                        gruposDelAlumno.add(grupos.get(i).getGroupName());
                    }
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
                out.println("<div class=\"row signup-box\">");
                out.println("<div class=\"col s12\">");



                for(String cad: gruposDelAlumno){
                    out.println("<div class=\"row\">");
                    out.println("<div class=\"col s10 push-s1 amber lighten-1\">");
                    out.println("<span>Grupo : "+cad+"</span>");
                    out.println("</div>");

                    out.println("<div class=\"row\">");
                    out.println("<div class=\"col s4 push-s1\"><span>Nombre de la publicación</span></div>");
                    out.println("<div class=\"col s3 push-s4\"><span>Acción</span></div>");
                    out.println("</div>");
                    int w = 0;
                    for(Publish pub: pubTemp){
                        if(pub.getGroupName().equals(cad)){
                            out.println("<div class=\"row\">");
                            out.println("<div class=\"col s4 push-s1\">");
                            out.println("<span>"+pub.getNombrePublicacion()+"</span>");
                            out.println("</div>");
                            out.println("<form action=\"StudentView\" method=\"get\" class=\"col s3 push-s4\">");
                            out.println("<input type=\"radio\" name=\"nombrePublicacion\" class=\"hide\" value=\""+pub.getNombrePublicacion()+"\" checked/>");
                            out.println("<input type=\"submit\" class=\"btn\" value=\"Ver gráfica\"/>");
                            out.println("</form>");
                            out.println("</div>");
                            w++;         
                        }
                    }
                    if(w==0){  out.println("<div class=\"row\"><div class=\"col s4 push-s1\"><span class=\"red accent-1\">Grupo sin publicaciones aun</span></div></div>");  }
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
                out.println("<li><a href=\"StudentGroups\">Ver Grupos</a></li>");
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

    private ArrayList<Publish> jsonToAlPublish() throws FileNotFoundException, IOException{
        String docBase = getServletConfig().getServletContext().getRealPath("/") + "publish.json";
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
    
}
