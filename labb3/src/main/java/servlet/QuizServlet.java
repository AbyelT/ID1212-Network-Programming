package servlet;

import bean.PersistUserAndQuiz;
import bean.PersistUserAndQuiz;
import bean.Users;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class QuizServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("start.jsp");
        HttpSession session = request.getSession(true);
        session.setAttribute("qz", new bean.Quiz());

        rd.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd;
        String file = "";
        HttpSession session = request.getSession();
        
        System.out.println(request.getRequestedSessionId());
        
        //get the current user and ongoing quiz
        bean.Quiz qz = (bean.Quiz)session.getAttribute("qz");
        bean.Users u = (bean.Users)session.getAttribute("user");
        
        //Necessary for persistence
        EntityManagerFactory emf;
        EntityManager em; emf = Persistence.createEntityManagerFactory("erik_labb3_war_1.0-SNAPSHOTPU");  
        em = emf.createEntityManager();
        PersistUserAndQuiz persist = new PersistUserAndQuiz();    
        int UserExists = 0;
        
        //If user wants to create an account
        if (request.getParameter("createAccount") != null) {
            List<Users> allUsers = persist.retrieveUsers(emf, em);
            
            String name =  request.getParameter("name");
            String pswd =  request.getParameter("pass");
            String email = request.getParameter("mail"); 
            
            //Look through database to see if this user already exists
            for (Users users : allUsers) {
                if (users.getEmail().equals(email) || users.getUsername().equals(name)) {
                    UserExists = 1;
                    file = "start.jsp?UserExists=TRUE";
                }
            }
            //If user does not exist
            if(UserExists == 0) { 
                    u = new bean.Users(name, pswd, email);
                    persist.createUser(emf, em, name, email, pswd);
                    session.setAttribute("user", u);
                    
                    session.setAttribute("qz", new bean.Quiz());
                    
                    file = "quiz.jsp";
            }
        }
        else if (request.getParameter("startQuiz") != null) { //If the start quiz button is pressed
            List<Users> allUsers = persist.retrieveUsers(emf, em);
            String name =  request.getParameter("name");
            String pswd =  request.getParameter("pass");
            String email = request.getParameter("mail"); 
            
            for (Users users : allUsers) {
                if (users.getEmail().equals(email) && users.getUsername().equals(name) && users.getPassword().equals(pswd)) {
                    
                    session.setAttribute("qz", new bean.Quiz());
                    file = "quiz.jsp";
                    break;
                }
                else {
                    file = "start.jsp?LoginFailed=TRUE";
                }
            }
        
        }
        else{
            //If the user has requested a new game to be started..
            if (request.getParameter("newGame") != null) {
                session.setAttribute("qz", new bean.Quiz());
                qz.setTracker(0);
                file = "quiz.jsp";
            }
            else {
                String answer = request.getParameter("option");
                
                System.out.println(answer);
                //Make sure we dont get a nullpointerexception, for example
                //when a user has entered the admin page and has gone back to the quiz.
                if(answer != null && !answer.equals("")) {
                    qz.checkAnswer(answer);

                    if(qz.getTracker() < qz.getQuizLength()) {
                        file = "quiz.jsp";
                    }
                    else
                        file = "success.jsp";
                }
                else
                    file = "quiz.jsp";
            }
        }
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
