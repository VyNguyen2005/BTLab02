/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nguye
 */
public class AddNewUserServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String result = "";
            
            result += "<h1>Add New Users</h1>";
            result += "<form action=InsertServlet method=POST>";
            result += "<table border=0>";
            result += "<tr><td>Name:</td><td><input type=text name=inputName autocomplete=off autofocus required autofocus/></td></tr>";
            result += "<tr><td>Password:</td><td><input type=password name=inputPass id=inputPass required/></td></tr>";
            result += "<tr><td>Email:</td><td><input type=email name=inputEmail id=inputEmail required/></td></tr>";
            result += "<tr><td>Country</td>";
            result += "<td>";
            result += "<select name=inputCountry>";
            result += "<option value=vietnam>VietNam</option>";
            result += "<option value=korea>Korea</option>";
            result += "<option value=us>US</option>";
            result += "<option value=switzerland>Switzerland</option>";
            result += "<option value=canada>Canada</option>";
            result += "</select>";
            result += "</td>";
            result += "</tr>";
            result += "<tr><td><input type=submit value=Save></td></tr>";
            result += "</table>";
            result += "</form>";
            result += "<a href=ViewServlet>View Users</a>";
            out.println(result);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddNewUserServlet</title>");            
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
