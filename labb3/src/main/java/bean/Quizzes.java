package bean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "quizzes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Quizzes.findAll", query = "SELECT q FROM Quizzes q"),
    @NamedQuery(name = "Quizzes.findById", query = "SELECT q FROM Quizzes q WHERE q.id = :id"),
    @NamedQuery(name = "Quizzes.findByQuestion", query = "SELECT q FROM Quizzes q WHERE q.question = :question"),
    @NamedQuery(name = "Quizzes.findByChoice1", query = "SELECT q FROM Quizzes q WHERE q.choice1 = :choice1"),
    @NamedQuery(name = "Quizzes.findByChoice2", query = "SELECT q FROM Quizzes q WHERE q.choice2 = :choice2"),
    @NamedQuery(name = "Quizzes.findByChoice3", query = "SELECT q FROM Quizzes q WHERE q.choice3 = :choice3"),
    @NamedQuery(name = "Quizzes.findByChoice4", query = "SELECT q FROM Quizzes q WHERE q.choice4 = :choice4"),
    @NamedQuery(name = "Quizzes.findByAnswer", query = "SELECT q FROM Quizzes q WHERE q.answer = :answer")})
public class Quizzes implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "option1")
    private String option1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "option2")
    private String option2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "option3")
    private String option3;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "option4")
    private String option4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "correctoption")
    private int correctoption;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "question")
    private String question;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "choice1")
    private String choice1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "choice2")
    private String choice2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "choice3")
    private String choice3;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "choice4")
    private String choice4;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "answer")
    private String answer;

    public Quizzes() {
    }

    public Quizzes(Long id) {
        this.id = id;
    }

    public Quizzes(Long id, String question, String choice1, String choice2, String choice3, String choice4, String answer) {
        this.id = id;
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quizzes)) {
            return false;
        }
        Quizzes other = (Quizzes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Quizzes[ id=" + id + " ]";
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getCorrectoption() {
        return correctoption;
    }

    public void setCorrectoption(int correctoption) {
        this.correctoption = correctoption;
    }
    
}
