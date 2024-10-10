/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.swing.text.html.HTML.Tag.SELECT;

/**
 *
 * @author ADMIN
 */
public class ViewServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Connection connection;
            PreparedStatement ps;
            ResultSet rsUsers;
            String result="";
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String connectionURL = "jdbc:sqlserver://localhost:1433;databaseName=demodb";
                connection = DriverManager.getConnection(connectionURL, "sa", "sa");
                ps = connection.prepareStatement("select * from users");
                rsUsers = ps.executeQuery();
                result += "<a href=insert.html>Add New User</a>";
                result += "<h1 padding-top=10; padding-bottom=10>Users List</h1>";
                result += "<table border=1>";
                result += "<tr><td>Id</td><td>Name</td><td>Password</td><td>Email</td><td>Country</td><td>Edit</td><td>Delete</td></tr>";
                while(rsUsers.next()){
                    result += "<tr>";
                    result += "<td>" +rsUsers.getInt(1) + "</td>";
                    result += "<td>" +rsUsers.getString(2) + "</td>";
                    result += "<td>" +rsUsers.getString(3) + "</td>";
                    result += "<td>" +rsUsers.getString(4) + "</td>";
                    result += "<td>" +rsUsers.getString(5) + "</td>";
                    result += "<td><a href='EditServlet?id=" + rsUsers.getInt(1) + "'>Edit</a></td>";
                    result += "<td><a href='DeleteServlet?id=" + rsUsers.getInt(1) + "' onclick='return confirm(\"Are you sure you want to delete this user?\");'>Delete</a></td>";
                    result += "</tr>";
                }
                out.println(result);
                connection.close();
            } catch (Exception e) {
                System.out.println("Lỗi: " +e.getMessage());
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>View Servlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("</body>");
            out.println("</html>");
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
        return "Short description";
    }// </editor-fold>

}
