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

        Patient patient = new Patient("Gosho","goshov", LocalDate.of(1990,5, 20),true);
        Diagnose diagnose = new Diagnose("dignose", "coment");
        Medicament medicament = new Medicament("name");
        Visitation visitation = new Visitation(LocalDate.now(), "coment");

        entityManager.persist(patient);
        entityManager.persist(diagnose);
        entityManager.persist(medicament);
        entityManager.persist(visitation);

        patient.setDiagnoses(diagnose);

        entityManager.getTransaction().commit();

    }
}
