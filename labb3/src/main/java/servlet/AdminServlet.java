/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Abyel Tesfay
 */
public class AdminServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd;
        String file = "admin.jsp";        
        HttpSession session = request.getSession(true);
        rd = request.getRequestDispatcher(file);
        rd.forward(request, response);    
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd;
        String file = "admin.jsp";        
        HttpSession session = request.getSession();
        
        //post the quiz
        /*
        show.setId((long)Integer.parseInt(request.getParameter("id")));
        show.setQuestion(request.getParameter("question"));
        show.setChoice1(request.getParameter("option1"));
        show.setChoice2(request.getParameter("option2"));
        show.setChoice3(request.getParameter("option3"));
        show.setChoice4(request.getParameter("option4"));
        show.setAnswer(request.getParameter("name"));
        show.save();
        */
        rd = request.getRequestDispatcher(file);
        rd.forward(request, response);   
    }
    
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
