///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// *
// * @author ADMIN
// */
//public class EditServlet extends HttpServlet {
//
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        String userId = request.getParameter("id");
//        try (PrintWriter out = response.getWriter()) {
//            String result = "";
//            Connection connection;
//            PreparedStatement ps;
//            String name = request.getParameter("inputName");
//            String password = request.getParameter("inputPass");
//            String email = request.getParameter("inputEmail");
//            String country = request.getParameter("inputCountry");
//            try {
//                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                String connectionURL = "jdbc:sqlserver://localhost:1433;databaseName=demodb";
//                connection = DriverManager.getConnection(connectionURL, "sa", "sa");
//                ps = connection.prepareStatement("update users set name = ?, password = ?, email = ?, country = ? where id = ?");
//                ps.setString(1, name);
//                ps.setString(2, password);
//                ps.setString(3, email);
//                ps.setString(4, country);
//                ps.setInt(5, Integer.parseInt(userId));
//                ps.executeUpdate();
//                connection.close();
//            } catch (Exception e) {
//                System.out.println("Loi: " +e.getMessage());
//            }
//            request.getRequestDispatcher("ViewServlet").include(request, response);
//            out.println("<h1>Update User<h1>");
//            result += "<form action=ViewServlet method=post>";
//            result += "<table border=0 padding-bottom=30>";
//            result += "<tr><td>Name:</td><td><input type=text name=inputName id=inputName autocomplete=off autofocus/></td></tr>";
//            result += "<tr><td>Password:</td><td><input type=password name=inputPass id=inputPass/></td></tr>";
//            result += "<tr><td>Email:</td><td><input type=email name=inputEmail id=inputEmail/></td></tr>";
//            result += "<tr>";
//            result += "<td>Country:</td>";
//            result += "<td>";
//            result += "<select name=inputCountry>";
//            result += "<option value=vietnam>VietNam</option>";
//            result += "<option value=korea>Korea</option>";
//            result += "<option value=us>US</option>";
//            result += "<option value=switzerland>Switzerland</option>";
//            result += "<option value=canada>Canada</option>";
//            result += "</select>";
//            result += "</td>";
//            result += "</tr>";
//            result += "</table>";
//            result += "<tr><td><button type=submit name=btn-submit>Edit&Save</button></td></tr>";
//            result += "</form>";
//            out.println(result);
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet EditServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
////            out.println("<h1>Servlet EditServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }
//
//}


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

            if (request.getMethod().equalsIgnoreCase("POST")) {
                // Cập nhật thông tin người dùng
                String name = request.getParameter("inputName");
                String password = request.getParameter("inputPass");
                String email = request.getParameter("inputEmail");
                String country = request.getParameter("inputCountry");
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    String connectionURL = "jdbc:sqlserver://localhost:1433;databaseName=demodb";
                    connection = DriverManager.getConnection(connectionURL, "sa", "sa");
                    ps = connection.prepareStatement("UPDATE users SET name = ?, password = ?, email = ?, country = ? WHERE id = ?");
                    ps.setString(1, name);
                    ps.setString(2, password);
                    ps.setString(3, email);
                    ps.setString(4, country);
                    ps.setInt(5, Integer.parseInt(userId));
                    ps.executeUpdate();
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                request.getRequestDispatcher("ViewServlet").include(request, response);
            } else {
                // Lấy thông tin người dùng để hiển thị trong biểu mẫu
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    String connectionURL = "jdbc:sqlserver://localhost:1433;databaseName=demodb";
                    connection = DriverManager.getConnection(connectionURL, "sa", "sa");
                    ps = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
                    
                    ps.setInt(1, Integer.parseInt(userId));
                    
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        result += "<h1>Edit User</h1>";
                        result += "<form action='EditServlet?id=" + userId + "' method='post'>";
                        result += "<table border='0'>";
                        result += "<tr><td>Name:</td><td><input type='text' name='inputName' value='" + rs.getString("name") + "' autocomplete='off' required/></td></tr>";
                        result += "<tr><td>Password:</td><td><input type='password' name='inputPass' value='" + rs.getString("password") + "' required/></td></tr>";
                        result += "<tr><td>Email:</td><td><input type='email' name='inputEmail' value='" + rs.getString("email") + "' required/></td></tr>";
                        result += "<tr><td>Country:</td><td><select name='inputCountry'>";
                        result += "<option value='vietnam'" + (rs.getString("country").equals("vietnam") ? " selected" : "") + ">VietNam</option>";
                        result += "<option value='korea'" + (rs.getString("country").equals("korea") ? " selected" : "") + ">Korea</option>";
                        result += "<option value='us'" + (rs.getString("country").equals("us") ? " selected" : "") + ">US</option>";
                        result += "<option value='switzerland'" + (rs.getString("country").equals("switzerland") ? " selected" : "") + ">Switzerland</option>";
                        result += "<option value='canada'" + (rs.getString("country").equals("canada") ? " selected" : "") + ">Canada</option>";
                        result += "</select></td></tr>";
                        result += "</table>";
                        result += "<input type='submit' value='Edit&Save'/>";
                        result += "</form>";
                    }
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            out.println(result);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Edit User Servlet";
    }
}

