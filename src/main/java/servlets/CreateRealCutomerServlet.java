package servlets;

import logic.CustomerLogic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by Dotin school 5 on 8/6/2016.
 */
public class CreateRealCutomerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest theRequest, HttpServletResponse theResponse)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest theRequest, HttpServletResponse theResponse)
            throws ServletException, IOException {
        theRequest.setCharacterEncoding("UTF-8");
        CustomerLogic customerLogic = new CustomerLogic();
        String firstName = theRequest.getParameter("firstName");
        String lastName = theRequest.getParameter("lastName");
        String fatherName = theRequest.getParameter("fatherName");
        String dateOfBirth = theRequest.getParameter("dateOfBirth");
        String internationalID = theRequest.getParameter("internationalID");
        String outputHTML = "";

        try {
            customerLogic.setCustomerInfo(firstName, lastName, fatherName, dateOfBirth, internationalID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //outputHTML = OutputGenerator.generate(realCustomer);


        theResponse.setContentType("text/html; charset=UTF-8");
        PrintWriter out = theResponse.getWriter();
        out.println(outputHTML);

    }
}