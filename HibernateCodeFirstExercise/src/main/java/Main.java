import Ex4HospitalDatabase.Diagnose;
import Ex4HospitalDatabase.Medicament;
import Ex4HospitalDatabase.Patient;
import Ex4HospitalDatabase.Visitation;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        EntityManager entityManager = Persistence
                .createEntityManagerFactory("code_first")
                .createEntityManager();


        entityManager.getTransaction().begin();

        entityManager.getTransaction().commit();

    }
}
