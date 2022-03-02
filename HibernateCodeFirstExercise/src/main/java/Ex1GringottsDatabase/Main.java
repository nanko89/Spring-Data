package Ex1GringottsDatabase;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        //TODO:Check for database "gringotts"

        EntityManager entityManager = Persistence
                .createEntityManagerFactory("code_first")
                .createEntityManager();


    }
}
