package edu.miu.cs.cs489.lab1b.emplpensionplanningsystem;

import edu.miu.cs.cs489.lab1b.emplpensionplanningsystem.model.Employee;
import edu.miu.cs.cs489.lab1b.emplpensionplanningsystem.model.PensionPlan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EmplPensionPlanningSystem {
    private static List<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
        loadData();
        printAllEmployees();
        printUpcomingEnrollees();
    }

    public static void loadData() {
        PensionPlan plan1 = new PensionPlan("EX1089", LocalDate.parse("2023-01-17"), 100.00);
        Employee emp1 = new Employee(1, "Daniel", "Agar", LocalDate.parse("2018-01-17"), 105945.50, plan1);

        Employee emp2 = new Employee(2, "Benard", "Shaw", LocalDate.parse("2018-10-03"), 197750.00, null);

        PensionPlan plan2 = new PensionPlan("SM2307", LocalDate.parse("2019-11-04"), 1555.50);
        Employee emp3 = new Employee(3, "Carly", "Agar", LocalDate.parse("2014-05-16"), 842000.75, plan2);

        Employee emp4 = new Employee(4, "Wesley", "Schneider", LocalDate.parse("2018-11-02"), 74500.00, null);

        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
    }

    public static void printAllEmployees() {
        List<Employee> sortedEmployees = employees.stream()
                .sorted(Comparator.comparing(Employee::getLastName)
                        .thenComparing(Employee::getYearlySalary).reversed())
                .collect(Collectors.toList());

        sortedEmployees.forEach(emp -> System.out.println(emp.toJSON()));
    }

    public static void printUpcomingEnrollees() {
        LocalDate firstDayOfNextMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfNextMonth = firstDayOfNextMonth.plusMonths(1).minusDays(1);

        List<Employee> upcomingEnrollees = employees.stream()
                .filter(emp -> emp.getPensionPlan() == null)
                .filter(emp -> ChronoUnit.YEARS.between(emp.getEmploymentDate(), lastDayOfNextMonth) >= 5)
                .sorted(Comparator.comparing(Employee::getEmploymentDate))
                .collect(Collectors.toList());

        upcomingEnrollees.forEach(emp -> System.out.println(emp.toJSON()));
    }
}
