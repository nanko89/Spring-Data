import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private final BufferedReader bufferedReader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }


    @Override
    public void run() {
        //TODO: Do not forget to extract database and fill your password and username
        
        System.out.println("Enter exercise number:");

        try {
            int number = Integer.parseInt(bufferedReader.readLine());

            switch (number) {
                case 2 -> changeCasing();
                case 3 -> containsEmployee();
                case 4 -> employeesWithSalaryOver50000();
                case 5 -> employeesFromDepartment();
                case 6 -> addingANewAddress();
                case 7 -> addressesWithEmployeeCount();
                case 8 -> getEmployeeWithProject();
                case 9 -> findLatestProjects();
                case 10 -> increaseSalaries();
                case 11 -> findEmployeesByFirstName();
                case 12 -> employeesMaximumSalaries();
                case 13 -> removeTowns();
                default -> System.out.println("No exist exercise number! Try again.");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Ex.2
    private void changeCasing() {
        entityManager.getTransaction().begin();

        List<Town> townList = entityManager
                .createQuery("SELECT t FROM Town t", Town.class)
                .getResultList().stream().filter(t -> t.getName().length() <= 5)
                .collect(Collectors.toList());

        for (Town town : townList) {
            town.setName(town.getName().toUpperCase());
            entityManager.persist(town);
        }

        entityManager.getTransaction().commit();
    }

    //Ex.3
    private void containsEmployee() throws IOException {
        System.out.println("Enter name of employee:");
        String name = bufferedReader.readLine();
        Long employeeCount = entityManager.createQuery
                        ("SELECT COUNT(e) " +
                                "FROM Employee e " +
                                "WHERE CONCAT(e.firstName, ' ' , e.lastName) = :name ", Long.class)
                .setParameter("name", name).getSingleResult();

        if (employeeCount > 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    //Ex.4
    private void employeesWithSalaryOver50000() {

        List<String> resultList = entityManager.createQuery
                ("SELECT e.firstName " +
                        "FROM Employee e " +
                        "WHERE e.salary > 50000", String.class).getResultList();

        resultList.forEach(System.out::println);
    }

    //Ex.5
    private void employeesFromDepartment() {

        List<Employee> resultList = entityManager.createQuery
                        ("SELECT e " +
                                "FROM Employee e " +
                                "WHERE e.department.name = :d_name " +
                                "ORDER BY e.salary, e.id", Employee.class)
                .setParameter("d_name", "Research and Development")
                .getResultList();

        resultList.forEach(e -> System.out.printf("%s %s from %s - $%.2f%n"
                , e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary()));
    }


//Ex.6
    private void addingANewAddress() throws IOException {

        System.out.println("Enter last name of employee:");
        String lastName = bufferedReader.readLine();
        Employee employee = null;

        try {
            employee = entityManager
                    .createQuery("SELECT e " +
                            "FROM Employee e " +
                            "WHERE e.lastName = :l_name", Employee.class)
                    .setParameter("l_name", lastName)
                    .getSingleResult();
        } catch (NoResultException e) {

        }
        if (employee == null) {
            System.out.println("No exist employee with this last name " + lastName);
        } else {
            entityManager.getTransaction().begin();

            String addressText = "Vitoshka 15";
            Address address = createIfNotExist(addressText);

            employee.setAddress(address);
            entityManager.persist(employee);

            entityManager.getTransaction().commit();
        }
    }

    private Address createIfNotExist(String addressText) {
        Address address = null;
        try {
            address = entityManager
                    .createQuery("SELECT a " +
                            "FROM Address a " +
                            "WHERE a.text = :a_text", Address.class)
                    .setParameter("a_text", addressText)
                    .getSingleResult();
        }catch (NoResultException exception){

        }

        if (address == null) {
            address = new Address();
            address.setText(addressText);

            entityManager.persist(address);


        }
        return address;
    }


    //Ex.7
    private void addressesWithEmployeeCount() {

        List<Address> resultList = entityManager.createQuery
                        ("SELECT a  " +
                                "FROM Address a " +
                                "ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList();

        resultList.forEach(a -> System.out.printf("%s, %s - %d employees%n",
                a.getText(), a.getTown().getName(), a.getEmployees().size()));

    }


    //Ex.8
    private void getEmployeeWithProject() throws IOException {

        System.out.println("Enter employee id:");
        int employeeId = Integer.parseInt(bufferedReader.readLine());
        Employee employee = entityManager.find(Employee.class, employeeId);


        if (employee != null) {

            System.out.printf("%s %s - %s%n",
                    employee.getFirstName(), employee.getLastName(), employee.getJobTitle());

            employee.getProjects().stream().map(Project::getName).sorted().forEach(e-> System.out.println("\t" + e));
        } else {
            System.out.println("Invalid employee id");
        }
    }

    //Ex.9
    private void findLatestProjects() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        entityManager.createQuery
                        ("SELECT p " +
                                "FROM Project p " +
                                "ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultStream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> System.out.printf("Project name: %s%n" +
                                " \tProject Description: %s %n" +
                                " \tProject Start Date: %s %n" +
                                " \tProject End Date: %s%n",
                        p.getName(), p.getDescription(), p.getStartDate().format(formatter),
                        p.getEndDate() == null ? "null" : p.getEndDate().format(formatter)));

    }

    //Ex.10
    private void increaseSalaries() {

        entityManager.getTransaction().begin();

        entityManager.createQuery("UPDATE Employee e " +
                        "SET e.salary = e.salary * 1.12" +
                        "WHERE e.department.id IN :d_id")
                .setParameter("d_id", Set.of(1, 2, 4, 11))
                .executeUpdate();

        entityManager.createQuery("SELECT e " +
                        "FROM Employee e " +
                        "WHERE e.department.id IN :d_id", Employee.class)
                .setParameter("d_id", Set.of(1, 2, 4, 11))
                .getResultStream()
                .forEach(e -> System.out.printf("%s %s ($%.2f)%n",
                        e.getFirstName(), e.getLastName(), e.getSalary()));

        entityManager.getTransaction().commit();
    }

    //Ex.11
    private void findEmployeesByFirstName() throws IOException {

        System.out.println("Enter pattern:");
        String pattern = bufferedReader.readLine();

        List<Employee> employees = entityManager.createQuery
                        ("SELECT e " +
                                "FROM Employee e " +
                                "WHERE e.firstName LIKE :e_like", Employee.class)
                .setParameter("e_like", pattern + "%")
                .getResultList();
        if (employees.isEmpty()) {
            System.out.println("No such exist employee with current pattern: " + pattern);
        } else {
            employees.forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                    e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));
        }
    }

    //Ex.12
    @SuppressWarnings("unchecked")
    private void employeesMaximumSalaries() {

        List<Object[]> rows = entityManager.createNativeQuery("SELECT d.name, MAX(e.salary) as max_salary " +
                "FROM employees e " +
                "JOIN departments d on d.department_id = e.department_id " +
                "GROUP BY d.name " +
                "HAVING max_salary NOT BETWEEN 30000 AND 70000;").getResultList();

        for (Object[] row : rows) {
            System.out.println(row[0] + " " + row[1]);
        }
    }

//Ex.13
    private void removeTowns() throws IOException {

        System.out.println("Enter town name: ");
        String townName = bufferedReader.readLine();

        entityManager.getTransaction().begin();

        setEmployeesAddressToNull(townName);

        int deleteAddresses = getDeletedAddressesFromTown(townName);

        Town town = entityManager.createQuery("SELECT t " +
                        "FROM Town AS t " +
                        "WHERE t.name = :t_name", Town.class)
                .setParameter("t_name", townName).getSingleResult();

        entityManager.remove(town);

        System.out.printf("%d address in %s deleted%n", deleteAddresses, townName);

        entityManager.getTransaction().commit();


        entityManager.close();

    }

    private int getDeletedAddressesFromTown(String town) {

        List<Address> addresses = entityManager.createQuery
                        ("SELECT a " +
                                "FROM Address AS a " +
                                "WHERE a.town.name = :t_name", Address.class)
                .setParameter("t_name", town).getResultList();

        for (Address address : addresses) {
            entityManager.remove(address);
        }
        return addresses.size();
    }

    private void setEmployeesAddressToNull(String town) {

        List<Employee> employees = entityManager.createQuery("SELECT e " +
                        "FROM Employee AS e " +
                        "WHERE e.address.town.name = :t_name", Employee.class)
                .setParameter("t_name", town).getResultList();
        for (Employee employee : employees) {
            employee.setAddress(null);
            entityManager.persist(employee);
        }
    }
}
