/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
public class InsertServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String name = request.getParameter("inputName");
            String password = request.getParameter("inputPass");
            String email = request.getParameter("inputEmail");
            String country = request.getParameter("inputCountry");
            Connection connection;
            PreparedStatement ps;
            try {
                // 1. Nap trinh dieu khien
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                // 2. Thiet lap ket noi
                String connectionURL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=demodb";
                connection = DriverManager.getConnection(connectionURL, "sa", "sa");
                // 3. Tao doi tuong thuc hien truy van
                ps = connection.prepareStatement("insert into users(name, password, email, country) values(?,?,?,?)");
                ps.setString(1, name);
                ps.setString(2, password);
                ps.setString(3, email);
                ps.setString(4, country);
                // 4. Thi hanh truy van
                int result = ps.executeUpdate();
                if (result > 0) {
                    out.println("Record saved successfully!");
                    request.getRequestDispatcher("insert.html").include(request, response);
                } else {
                    out.println("Record saved failed!");
                    request.getRequestDispatcher("insert.html").include(request, response);
                    return;
                }
                connection.close();
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
            } 
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Insert Servlet</title>");
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
