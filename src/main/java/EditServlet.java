import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String userId = request.getParameter("id");
        try (PrintWriter out = response.getWriter()) {
            String result = "";
            Connection connection;
            PreparedStatement ps;
            ResultSet rs;
            
            String method = request.getMethod();
            if ("POST".equalsIgnoreCase(method)) {
                String name = request.getParameter("inputName");
                String password = request.getParameter("inputPass");
                String email = request.getParameter("inputEmail");
                String country = request.getParameter("inputCountry");
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    String connectionURL = "jdbc:sqlserver://localhost:1433;databaseName=demodb";
                    connection = DriverManager.getConnection(connectionURL, "sa", "sa");
                    
                    String updateQuery = "update users set name = ?, password = ?, email = ?, country = ? WHERE id = ?";
                    ps = connection.prepareStatement(updateQuery);
                    ps.setString(1, name);
                    ps.setString(2, password);
                    ps.setString(3, email);
                    ps.setString(4, country);
                    ps.setInt(5, Integer.parseInt(userId));
                    int kq = ps.executeUpdate();
                    if(kq > 0){
                        out.println("Record updated successfully!");
                        request.getRequestDispatcher("ViewServlet").include(request, response);
                    }
                    else{
                        out.println("Record updated failed!");
                        request.getRequestDispatcher("ViewServlet").include(request, response);
                        return;
                    }
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String connectionURL = "jdbc:sqlserver://localhost:1433;databaseName=demodb";
                connection = DriverManager.getConnection(connectionURL, "sa", "sa");
                
                ps = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
                ps.setInt(1, Integer.parseInt(userId));

                rs = ps.executeQuery();
                if (rs.next()) {
                    result += "<h1>Update User</h1>";
                    result += "<form action='EditServlet?id=" + userId + "' method='post'>";
                    result += "<table border='0'>";
                    result += "<tr><td>Name:</td><td><input type='text' name='inputName' value='" + rs.getString("name") + "' autocomplete='off' required/></td></tr>";
                    result += "<tr><td>Password:</td><td><input type='password' name='inputPass' value='" + rs.getString("password") + "' required/></td></tr>";
                    result += "<tr><td>Email:</td><td><input type='email' name='inputEmail' value='" + rs.getString("email") + "' required/></td></tr>";
                    result += "<tr><td>Country:</td>";
                    result += "<td><select name='inputCountry'>";
                    String selectedCountry = rs.getString("country");
                    result += "<option value='vietnam'" + (selectedCountry.equalsIgnoreCase("vietnam") ? " selected" : "") + ">VietNam</option>";
                    result += "<option value='korea'" + (selectedCountry.equalsIgnoreCase("korea") ? " selected" : "") + ">Korea</option>";
                    result += "<option value='us'" + (selectedCountry.equalsIgnoreCase("us") ? " selected" : "") + ">US</option>";
                    result += "<option value='switzerland'" + (selectedCountry.equalsIgnoreCase("switzerland") ? " selected" : "") + ">Switzerland</option>";
                    result += "<option value='canada'" + (selectedCountry.equalsIgnoreCase("canada") ? " selected" : "") + ">Canada</option>";
                    result += "</select></td>";
                    result += "</tr>";
                    result += "</table>";
                    result += "<input type='submit' value='Edit&Save'/>";
                    result += "</form>";
                }
                connection.close();
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
            }
            out.println(result);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Edit User Servlet";
    }
}
