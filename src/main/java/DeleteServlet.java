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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nguye
 */
public class DeleteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String userId = request.getParameter("id");
        try (PrintWriter out = response.getWriter()) {
            Connection connection;
            PreparedStatement ps;
            ResultSet rs;
            try {  
                connection = DatabaseUtil.getConnection();
                ps = connection.prepareStatement("delete from users where id = ?");
                ps.setInt(1, Integer.parseInt(userId));
                int result = ps.executeUpdate();
                if(result > 0){
                    out.println("Record deleted successfully!");
                    request.getRequestDispatcher("ViewServlet").include(request, response);
                }
                else{
                    out.println("Record deleted failed!");
                    request.getRequestDispatcher("ViewServlet").include(request, response);
                    return;
                }
                connection.close();
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Delete Servlet</title>");
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
    }

}
