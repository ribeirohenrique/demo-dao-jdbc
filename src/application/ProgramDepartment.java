package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;
import java.util.Scanner;

public class ProgramDepartment {
    public static void main(String[] args) {

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();


        System.out.println("===== Teste 1: Department insert =====");
        Department newDepartment = new Department(null,"Health");
        departmentDao.insert(newDepartment);
        System.out.println("Inserted. New Id: " + newDepartment.getId());



        System.out.println("===== Teste 2: Department update =====");
        Department updateDepartment = new Department(6, null);
        updateDepartment.setName("Infraestrutura");
        departmentDao.update(updateDepartment);
        System.out.println("Updated completed.");



        System.out.println("===== Teste 3: Department delete =====");
        System.out.print("Enter id for delete test: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Delete completed");


        System.out.println("===== Teste 4: Department findById =====");
        System.out.println();
        Department department = departmentDao.findById(3);
        System.out.println(department);

        System.out.println("===== Teste 3: Department findAll =====");
        System.out.println();
        List<Department> departmentList = departmentDao.findAll();
        departmentList.forEach(System.out::println);
    }
}
