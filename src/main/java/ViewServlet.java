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
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String connectionURL = "jdbc:sqlserver://localhost:1433;databaseName=demodb";
                connection = DriverManager.getConnection(connectionURL, "sa", "sa");
                Statement stmt = connection.createStatement();
                ResultSet rsUsers = stmt.executeQuery("select * from users");
                while(rsUsers.next()){
//                    out.println("<table></table>");
                    out.println("<table><tr>"
                            + "<td>" +rsUsers.getString(1) + "</td>"
                            + "<td>" +rsUsers.getString(2) + "</td>"
                            + "<td>" +rsUsers.getString(3) + "</td>"
                            + "<td>" +rsUsers.getString(4) + "</td>"
                                    + "</tr></");
                    
                }
            } catch (Exception e) {
                System.out.println("Lỗi: " +e.getMessage());
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewServlet at " + request.getContextPath() + "</h1>");
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
