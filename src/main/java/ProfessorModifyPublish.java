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

public class ProfessorModifyPublish extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        HttpSession sesion= (HttpSession)request.getSession();
        String taip = sesion.getAttribute("type").toString();
        String uzername = sesion.getAttribute("username").toString();
        
        if(taip.equals("Profesor")){
            int i;
            String nombrePublicacion = request.getParameter("nombrePublicacion");
            ArrayList<Publish> pubTemp = jsonToAl();
            Publish pub = new Publish();
            for(i=0; i<pubTemp.size(); i++){
                if(pubTemp.get(i).getNombrePublicacion().equals(nombrePublicacion)){
                    pub = pubTemp.get(i);
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
                out.println("<link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">");
                out.println("<link href=\"build/nv.d3.css\" rel=\"stylesheet\" type=\"text/css\">");
                out.println("<script src=\"build/d3.min.js\" charset=\"utf-8\"></script>");
                out.println("<script src=\"build/nv.d3.js\"></script>");
                out.println("<link rel=\"stylesheet\" href=\"css/Simpl3graff.css\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<section class=\"main-container\">");
                out.println("<div class=\"container\">");
                out.println("<div class=\"row abajo signup-box\">");
                out.println("<div class=\"col s12\">");
                out.println("<div class=\"row\">");
                out.println("<div class=\"col s4 signp-form\">");
                out.println("<form id=\"frm1\">");
                out.println("Grafica1: <input type=\"text\" placeholder=\"escribe una funcion de x\" id=\"in1\" value=\""+pub.getGrafica1()+"\"/><br/>");
                out.println("</form>");
                out.println("<form id=\"frm2\">");
                out.println("Grafica2: <input type=\"text\" placeholder=\"escribe otra función de x\" id=\"in2\" value=\""+pub.getGrafica2()+"\"/><br/>");
                out.println("</form>");
                out.println("<form id=\"frm3\">");
                out.println("Grafica3: <input type=\"text\" placeholder=\"Ya la última :v\" id=\"in3\" value=\""+pub.getGrafica3()+"\"/><br/>");
                out.println("</form>");
                out.println("<button class=\"btn btn-signup boton-type1\" onclick=\"prueba(), cambioGraph()\">Graficalo!</button>");
                out.println("</div>");
                out.println("<div class=\"col s8\">");
                out.println("<div id=\"chart1\" class=\"with-transitions\">");
                out.println("<svg></svg>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("<div class=\"row\">");
                out.println("<h5 class=\"center-align\">Guarda tu publicacion</h5>");
                out.println("<form class=\"col s10 push-s1\" action=\"ProfessorModifyPublish\" method=\"post\">");
                out.println("<div class=\"input-field\">");
                out.println("<textarea name=\"descripcion\" id=\"textarea\" class=\"materialize-textarea\" placeholder=\"De qué trata tu gráfica?\">"+pub.getDescripcion()+"</textarea>");
                out.println("<label for=\"textarea\">Descripción</label>");
                out.println("</div>");
                out.println("<div class=\"row\">");
                out.println("<div class=\"col s6\">");
                out.println("<input id=\"nomGraf\" type=\"text\" value=\""+pub.getNombrePublicacion()+"\" disabled/>");
                out.println("<label for=\"nomGraf\">Nombre</label>");
                out.println("<input name=\"nombrePublicacion\" type=\"radio\" value=\""+pub.getNombrePublicacion()+"\" class=\"hide\" checked/>");
                out.println("<input name=\"groupName\" type=\"radio\" value=\""+pub.getGroupName()+"\" class=\"hide\" checked/>");
                out.println("</div>");
                out.println("<div class=\"col s6\">");
                out.println("<input type=\"radio\" name=\"grafica1\" class=\"hide\" id=\"grafa1\" checked/>");
                out.println("<input id=\"graf1\" type=\"text\" placeholder=\"Ecuaciones de la gráfica1\" disabled/>");
                out.println("<label for=\"graf1\">Ecuación 1</label>");
                out.println("</div>");
                out.println("</div>");
                out.println("<div class=\"row\">");
                out.println("<div class=\"col s6\">");
                out.println("<input type=\"radio\" name=\"grafica2\" class=\"hide\" id=\"grafa2\" checked/>");
                out.println("<input id=\"graf2\" type=\"text\" placeholder=\"Ecuaciones de la gráfica2\" disabled/>");
                out.println("<label for=\"graf2\">Ecuación 2</label>");
                out.println("</div>");
                out.println("<div class=\"col s6\">");
                out.println("<input type=\"radio\" name=\"grafica3\" class=\"hide\" id=\"grafa3\" checked/>");
                out.println("<input id=\"graf3\" type=\"text\" placeholder=\"Ecuaciones de la gráfica3\" disabled/>");
                out.println("<label for=\"graf3\">Ecuación3</label>");
                out.println("</div>");
                out.println("</div>");

                out.println("<div class=\"row\">");
                int x = 0;
                for(Comment c: pub.getComentarios()){
                            out.println("<br/><div class=\"card-panel blue-grey lighten-3 col s9 push-s1\">");
                            out.println("<div class=\"row\">");
                            out.println("<div class=\"col s4 push-s1 left-align\">");
                            out.println("<i class=\"material-icons prefix\">account_circle</i>");
                            out.println("<span>"+c.getStudentUsername()+"</span>");
                            out.println("</div>");
                            out.println("<div class=\"col s3 right-align\">");
                            out.println("<i class=\"material-icons prefix\">assignment_turned_in</i>");
                            out.println("<span>Calificar: </span>");
                            out.println("</div>");
                            
                            out.println("<div class=\"col s4 left-align\">");
                            out.println("<input type=\"text\" name=\"c"+x+"\" value=\"\"/>");
                            out.println("</div>");
                            
                            out.println("<div class=\"col s10 left-align\">");
                            out.println("<p>"+c.getComment()+"</p>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            x++;
                    }
                    out.println("</div>");
                


                out.println("<input type=\"submit\" class=\"btn btn-signup boton-type1\" value=\"Guardar\">");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("<br/>");
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
                out.println("<li><a href=\"ProfessorMain\">Ver Publicaciones</a></li>");
                out.println("<li><a href=\"ProfessorAddPublish\">Añadir Publicación</a></li>");
                out.println("<li><a href=\"Signin\">Salir</a></li>");
                out.println("</ul>");
                out.println("</div>");
                
                out.println("</div>");
                out.println("</div>");
                out.println("</footer>");
                out.println("<script src=\"build/jquery-3.2.1.min.js\"></script>");
                out.println("<script src=\"build/materialize.min.js\"></script>");
                out.println("<script>");
                out.println("function cambioGraph() {");
                out.println("var x = document.getElementById(\"in1\");");
                out.println("var w = document.getElementById(\"graf1\");");
                out.println("var y = document.getElementById(\"grafa1\");");
                out.println("w.value = x.value;");
                out.println("y.value = x.value;");
                out.println("var a = document.getElementById(\"in2\");");
                out.println("var b = document.getElementById(\"graf2\");");
                out.println("var c = document.getElementById(\"grafa2\");");
                out.println("b.value = a.value;");
                out.println("c.value = a.value;");
                out.println("var d = document.getElementById(\"in3\");");
                out.println("var e = document.getElementById(\"graf3\");");
                out.println("var f = document.getElementById(\"grafa3\");");
                out.println("e.value = d.value;");
                out.println("f.value = d.value;");
                out.println("}");
                out.println("function prueba(){");
                out.println("nv.addGraph(function() {");
                out.println("var chart = nv.models.lineChart();");
                out.println("var fitScreen = false;");
                out.println("var width = 600;");
                out.println("var height = 300;");
                out.println("var zoom = 1;");
                out.println("chart.useInteractiveGuideline(true);");
                out.println("chart.xAxis");
                out.println(".tickFormat(d3.format(',r'));");
                out.println("chart.lines.dispatch.on(\"elementClick\", function(evt)");
                out.println("{");
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
                out.println("})");
                out.println("};");
                out.println("function sinAndCos() {");
                out.println("var f1 = [],");
                out.println("f2 = [],");
                out.println("f3 = [];");
                out.println("var k = 1;");
                out.println("for(k = 1; k <= 3; k++){");
                out.println("var x = document.getElementById(\"frm\"+k);");
                out.println("var text = \"\";");
                out.println("var i;");
                out.println("for (i = 0; i < x.length ;i++) {");
                out.println("text += x.elements[i].value;");
                out.println("}");
                out.println("if(text != \"\"){");
                out.println("with (Math) f=eval(\"(function(x) {return \"+text+\";})\");");
                out.println("for(i = 0; i< 100; i++){");
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
                out.println("values: f1,");
                out.println("key: \"funcion 1\",");
                out.println("color: \"#ff7f0e\"");
                out.println("},{");
                out.println("values: f2,");
                out.println("key: \"funcion 2\",");
                out.println("color: \"#2ca02c\"");
                out.println("},{");
                out.println("values: f3,");
                out.println("key: \"funcion 3\",");
                out.println("color: \"#000000\"");
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
        
        if(taip.equals("Profesor")){
        
            String salida;
            int i; int j; int k;
            String nombrePublicacion = request.getParameter("nombrePublicacion");
            ArrayList<Publish> pubTemp = jsonToAl();
            for(i=0; i<pubTemp.size(); i++){
                if(pubTemp.get(i).getNombrePublicacion().equals(nombrePublicacion)){
                    
                    pubTemp.get(i).setDescripcion(request.getParameter("descripcion"));
                    pubTemp.get(i).setGrafica1(request.getParameter("grafica1"));
                    pubTemp.get(i).setGrafica2(request.getParameter("grafica2"));
                    pubTemp.get(i).setGrafica3(request.getParameter("grafica3"));
                    for(j=0;j<pubTemp.get(i).getComentarios().size();j++){
                        salida = request.getParameter("c" + j);
                        if(salida != null && salida != ""){
                            pubTemp.get(i).getComentarios().get(j).setCalificacion(Integer.parseInt(salida));
                        }
                    }
                    break;
                }
            }
            modifyArchive(alToJson(pubTemp));
            response.sendRedirect("ProfessorMain");
            
        }
        
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
    
    private String alToJson(ArrayList<Publish> publicacion) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String temp = mapper.writeValueAsString(publicacion);
        String json = temp.substring(1, temp.length() - 1);
        return json;
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
