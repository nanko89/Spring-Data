import Entities.Student;
import Entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("school");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Student student = new Student("Teo", 15);
        em.persist(student);

        Teacher  teacher = new Teacher("Deo", LocalDate.now());
        em.persist(teacher);

        em.getTransaction().commit();
        em.close();
    }
}
