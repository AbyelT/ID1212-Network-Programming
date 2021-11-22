package bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class Quiz {
    private int points;
    private int tracker;
    private int[] answers;
    private int[] facit;
    private EntityManagerFactory emf;
    private EntityManager em;
    
    private List<Quizzes> questionsList;

    public Quiz() {
        points = 0;
        tracker = 0;
        //create stuff necessary to fetch from database.
        emf = Persistence.createEntityManagerFactory("natverkprogg_labb3_war_1.0-SNAPSHOTPU");  
        em = emf.createEntityManager();  
        PersistUserAndQuiz pp = new PersistUserAndQuiz();
        
        //fetch quiz questions from database
        List<Quizzes> myList = pp.retrieveQuizQuestions(emf, em);
        this.questionsList = myList;
        setFacit(myList); 
        answers = new int[myList.size()];    
    }
    
    public int getQuizLength() {
        return this.questionsList.size();
    }   
    //hardcoded facit
    public void setFacit(List<Quizzes> myList) {
        this.facit = new int[myList.size()]; 
        for (int i = 0; i < myList.size(); i++) {
            this.facit[i] = myList.get(i).getCorrectoption();
        }
    }
    
    public void setAnswer(int ans) {
        answers[tracker] = ans;
    }
    
    public void setPoints(int p) {
        this.points = p;
    }
    public void setTracker(int p) {
        this.tracker = p;
    }
    public int[] getAnswers(){
        return this.answers;
    }
    
    public int getPoints(){
        return this.points;
    }
    
    public int getTracker(){
        return this.tracker;
    }
    
    public void retrieveAllQuestions() {}
    
    public String[] getQuizzes() {
        String[] firstQz = new String[5];
                firstQz[0] = this.questionsList.get(this.tracker).getQuestion();
                firstQz[1] = this.questionsList.get(this.tracker).getOption1();
                firstQz[2] = this.questionsList.get(this.tracker).getOption2();
                firstQz[3] = this.questionsList.get(this.tracker).getOption3();
                firstQz[4] = this.questionsList.get(this.tracker).getOption4();
           
        return firstQz;
    }
    
    public void checkAnswer(String ans) {
        this.setAnswer(Integer.parseInt(ans));
        if(Integer.parseInt(ans) == this.facit[tracker]) {
            points++;
        }
        tracker++;
    }
}