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

public class StudentView extends HttpServlet {

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession sesion= (HttpSession)request.getSession();
        String taip = sesion.getAttribute("type").toString();
        int i;
        
        if(taip.equals("Alumno")){
        
            Publish actual = new Publish();
            String nombrePublicacion = request.getParameter("nombrePublicacion");
            ArrayList<Publish> pub = jsonToAlPublish();
            for(i=0; i<pub.size();i++){
                if(pub.get(i).getNombrePublicacion().equals(nombrePublicacion)){
                    actual = pub.get(i);
                    break;
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
                    out.println("<link href=\"build/nv.d3.css\" rel=\"stylesheet\" type=\"text/css\">");
                    out.println("<script src=\"build/d3.min.js\" charset=\"utf-8\"></script>");
                    out.println("<script src=\"build/nv.d3.js\"></script>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<section class=\"main-container\">");
                    out.println("<div class=\"container\">");
                    out.println("<div class=\"row signup-box\">");
                    out.println("<div class=\"col s12\">");


                    out.println("<div class=\"row\">");
                    out.println("<div class=\"col s6\">");
                    out.println("<h3>"+actual.getNombrePublicacion()+"</h3>");
                    out.println("</div>");
                    out.println("<div class=\"col s4 push-s2\" id=\"chartZoom\">");
                    out.println("<a href=\"#\" id=\"zoomIn\">Zoom In</a>");
                    out.println("<a href=\"#\" id=\"zoomOut\">Zoom Out</a>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("<div class=\"row\">");
                    out.println("<div class=\"col s10 push-s1 with-transitions\" id=\"chart1\">");
                    out.println("<svg></svg>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("<div class=\"row\">");
                    out.println("<div class=\"col s10 push-s1\">");
                    out.println("<h5 class=\"left-align\">"+actual.getDescripcion()+"</h5>");
                    out.println("<br/><span class=\"left-align\">Publicación asignada al grupo "+actual.getGroupName()+"</span>");
                    out.println("</div>");
                    out.println("<form action=\"StudentView\" method=\"post\" class=\"col s10 push-s1\">");
                    out.println("<div class=\"row\">");
                    out.println("<input class=\"col s8\" type=\"text\" name=\"comment\" placeholder=\"Escribe un comentario\" required/>");
                    out.println("<input class=\"hide\" type=\"radio\" name=\"nombrePublicacion\" value=\""+actual.getNombrePublicacion()+"\" checked/>");
                    out.println("<input type=\"submit\" class=\"btn btn-signup boton-type1 col s3 push-s1\" value=\"Enviar\">");
                    out.println("</div>");
                    out.println("</form>");                

                    for(Comment c: actual.getComentarios()){
                            out.println("<br/><div class=\"card-panel blue-grey lighten-3 col s9 push-s1\">");
                            out.println("<div class=\"row\">");
                            out.println("<div class=\"col s7 push-s1 left-align\">");
                            out.println("<i class=\"material-icons prefix\">account_circle</i>");
                            out.println("<span>"+c.getStudentUsername()+"</span>");
                            out.println("</div>");
                            out.println("<div class=\"col s4 left-align\">");
                            out.println("<i class=\"material-icons prefix\">assignment_turned_in</i>");
                            out.println("<span>calif: "+c.getCalificacion()+"</span>");
                            out.println("</div>");
                            out.println("<div class=\"col s10 left-align\">");

                            out.println("<p>"+c.getComment()+"</p>");

                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                    }
                    out.println("</div>");


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

                    out.println("<script>");
                    out.println("nv.addGraph(function() {");
                    out.println("var chart = nv.models.lineChart();");
                    out.println("var fitScreen = false;");
                    out.println("var width = 760;");
                    out.println("var height = 400;");
                    out.println("var zoom = 1;");
                    out.println("chart.useInteractiveGuideline(true);");
                    out.println("chart.xAxis");
                    out.println(".tickFormat(d3.format(',r'));");
                    out.println("chart.lines.dispatch.on(\"elementClick\", function(evt) {");
                    out.println("console.log(evt);");
                    out.println("});");
                    out.println("chart.yAxis");
                    out.println(".axisLabel('Eje Y')");
                    out.println(".tickFormat(d3.format(',.2f'));");
                    out.println("d3.select('#chart1 svg')");
                    out.println(".attr('perserveAspectRatio', 'xMinYMid')");
                    out.println(".attr('width', width)");
                    out.println(".attr('height', height)");
                    out.println(".datum(sinAndCos());");
                    out.println("setChartViewBox();");
                    out.println("resizeChart();");
                    out.println("nv.utils.windowResize(resizeChart);");
                    out.println("d3.select('#zoomIn').on('click', zoomIn);");
                    out.println("d3.select('#zoomOut').on('click', zoomOut);");
                    out.println("function setChartViewBox() {");
                    out.println("var w = width * zoom,");
                    out.println("h = height * zoom;");
                    out.println("chart");
                    out.println(".width(w)");
                    out.println(".height(h);");
                    out.println("d3.select('#chart1 svg')");
                    out.println(".attr('viewBox', '0 0 ' + w + ' ' + h)");
                    out.println(".transition().duration(500)");
                    out.println(".call(chart);");
                    out.println("}");
                    out.println("function zoomOut() {");
                    out.println("zoom += .25;");
                    out.println("setChartViewBox();");
                    out.println("}");
                    out.println("function zoomIn() {");
                    out.println("if (zoom <= .5) return;");
                    out.println("zoom -= .25;");
                    out.println("setChartViewBox();");
                    out.println("}");
                    out.println("function resizeChart() {");
                    out.println("var container = d3.select('#chart1');");
                    out.println("var svg = container.select('svg');");
                    out.println("if (fitScreen) {");
                    out.println("var windowSize = nv.utils.windowSize();");
                    out.println("svg.attr(\"width\", windowSize.width);");
                    out.println("svg.attr(\"height\", windowSize.height);");
                    out.println("} else {");
                    out.println("var aspect = chart.width() / chart.height();");
                    out.println("var targetWidth = parseInt(container.style('width'));");
                    out.println("svg.attr(\"width\", targetWidth);");
                    out.println("svg.attr(\"height\", Math.round(targetWidth / aspect));");
                    out.println("}");
                    out.println("}");
                    out.println("return chart;");
                    out.println("});");
                    out.println("function sinAndCos() {");
                    out.println("var f1 = [], f2 = [], f3 = [];");
                    out.println("var grafica = [\""+actual.getGrafica1()+"\", \""+actual.getGrafica2()+"\", \""+actual.getGrafica3()+"\"];");
                    out.println("var k = 1;");
                    out.println("for(k = 1; k <= 3; k++){");
                    out.println("var text = grafica[k-1];");
                    out.println("var i;");
                    out.println("if(text != \"\"){");
                    out.println("with (Math) f=eval(\"(function(x) {return \"+text+\";})\");");
                    out.println("for(i = 0; i< 100; i+=.125){");
                    out.println("if(k == 1){");
                    out.println("f1.push({ x:i, y: f(i) });");
                    out.println("} else if(k == 2){");
                    out.println("f2.push({ x:i, y: f(i) });");
                    out.println("} else if(k == 3){");
                    out.println("f3.push({ x:i, y: f(i) });");
                    out.println("}");
                    out.println("}");
                    out.println("}");
                    out.println("}");
                    out.println("return [");
                    out.println("{");
                    out.println("values: f1, key: \"funcion 1\", color: \"#ff7f0e\"");
                    out.println("},{");
                    out.println("values: f2, key: \"funcion 2\", color: \"#2ca02c\"");
                    out.println("},{");
                    out.println("values: f3, key: \"funcion 3\", color: \"#000000\"");
                    out.println("}");
                    out.println("];");
                    out.println("}");
                    out.println("</script>");

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
        String uzername = sesion.getAttribute("username").toString();
        
        if(taip.equals("Alumno")){
            int i;
            Comment comentario = new Comment();
            comentario.setComment(request.getParameter("comment"));
            comentario.setStudentUsername(uzername);
            
            String nombrePublicacion = request.getParameter("nombrePublicacion");
            ArrayList<Publish> pub = jsonToAlPublish();
            for(i=0; i<pub.size();i++){
                if(pub.get(i).getNombrePublicacion().equals(nombrePublicacion)){
                    pub.get(i).getComentarios().add(comentario);
                    break;
                }
            }
            
            modifyArchive(alToJson(pub));
            response.sendRedirect("StudentView?nombrePublicacion="+nombrePublicacion);
            
            
        }
        
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
    
    private String alToJson(ArrayList<Publish> publicacion) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String temp = mapper.writeValueAsString(publicacion);
        String json = temp.substring(1, temp.length() - 1);
        return json;
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
