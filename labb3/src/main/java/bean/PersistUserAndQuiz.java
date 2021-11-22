/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 * @author erikb
 */

import javax.persistence.*;  
import bean.Quizzes;
import java.util.List;

public class PersistUserAndQuiz {
    public void createQuizQuestion (EntityManagerFactory emf, EntityManager em, String question, String option1, String option2, String option3, String option4, int correctOption) {
        em.getTransaction().begin();  
        Quizzes question1 = new Quizzes();
        question1.setQuestion(question);
        question1.setOption1(option1);
        question1.setOption2(option2);
        question1.setOption3(option3);
        question1.setOption4(option4);
        question1.setCorrectoption((short)correctOption);
        em.persist(question1);  
        em.getTransaction().commit();
    }
    
    public List<Quizzes> retrieveQuizQuestions(EntityManagerFactory emf, EntityManager em) {            
        TypedQuery<Quizzes> query =
        em.createNamedQuery("QuizQuestions.findAll", Quizzes.class);
        List<Quizzes> results = query.getResultList();
        return results;
    }
    
    public List<Users> retrieveUsers(EntityManagerFactory emf, EntityManager em) {         
        TypedQuery<Users> query =
        em.createNamedQuery("Users.findAll", Users.class);
        List<Users> results = query.getResultList();
        return results;
    }
    
    public void createUser(EntityManagerFactory emf, EntityManager em, String username, String email, String password) {
        em.getTransaction().begin();  
        Users newUser = new Users(); 
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
         
        em.persist(newUser);  
        try {
            em.getTransaction().commit();
        }catch (RollbackException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[])  
    {  
          
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("erik_labb3_war_1.0-SNAPSHOTPU");  
        EntityManager em=emf.createEntityManager();  
        PersistUserAndQuiz qz = new PersistUserAndQuiz();
        
        //qz.createQuizQuestion(emf, em);
        //qz.createUser(emf, em, "johan", "johan@kth.se", "1234");
        
        List<Quizzes> results =  qz.retrieveQuizQuestions(emf, em);
        
        List<Users> users =  qz.retrieveUsers(emf, em);
       
        
        for(Quizzes questions : results) {
        System.out.println("RESULTS: " + questions.getQuestion());
        }
        
        for(Users u : users) {
        System.out.println("RESULTS: " + u.getUsername());
        }
        
        //emf.close();  
        //em.close();  
    }
}
