import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Signin")
public class Signin extends HttpServlet {

        @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
            response.sendRedirect("index.html");
	}
    
        @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
		HttpSession sesion;
                if(username.equals("admin") && password.equals("zoiElAdminxd")){
                    sesion = request.getSession(true);
                    sesion.setAttribute("type", "admin");
                    sesion.setAttribute("username", "admin");
                    response.sendRedirect("Signup");
                } else {
                    int xst = exist(username, password);
                    /*  Si es profe retorna 2
                        Si es alumno retorna 1
                        Si no existe retorna 0
                    */
                    if(xst == 0){
                        resp(response, "Algo ha ido mal. Verifica tu nombre de usuario o contraseña");
                    }
                    if(xst == 1){
                        sesion = request.getSession(true);
                        sesion.setAttribute("type", "Alumno");
                        sesion.setAttribute("username", username.toString());
                        response.sendRedirect("StudentGroups");
                    }
                    if(xst == 2){
                        sesion = request.getSession(true);
                        sesion.setAttribute("type", "Profesor");
                        sesion.setAttribute("username", username.toString());
                        response.sendRedirect("ProfessorMain");
                    }
                }
                
		
	}

	private void resp(HttpServletResponse response, String msg)
			throws IOException {
                String docBase = getServletConfig().getServletContext().getRealPath("/");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<span1>" + msg + "</span>");
		out.println("</body>");
		out.println("</html>");
	}
        
        /*Si no existe: 0; si es Alumno: 1; Si es Profesor: 2*/
        private int exist(String username, String password)
			throws IOException {
            String docBase = getServletConfig().getServletContext().getRealPath("/") + "newjson.json";
            FileReader fr = new FileReader(docBase);
            BufferedReader br = new BufferedReader(fr);
            ObjectMapper mapper = new ObjectMapper();
            try{
                String json;
                if((json=br.readLine())!=null){
                    json = "[" + json + "]"; 
                } else {
                    json = "[]";
                }
                ArrayList<User> usuarios = mapper.readValue(json,new TypeReference<ArrayList<User>>(){} );
                for(User i: usuarios){
                    if(i.getUsername().equals(username) && i.getPassword().equals(password)){
                        if(i.getType().equals("Alumno")){
                            return 1;
                        } else if(i.getType().equals("Profesor")){
                            return 2;
                        }
                    }
                }

            }
            catch (Exception e){
                System.out.println("Error de lectura del fichero");
            }
            return 0;
	}
        
        
        
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
