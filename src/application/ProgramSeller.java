package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class ProgramSeller {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("===== Teste 1: Seller findById =====");
        System.out.println();
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("===== Teste 2: Seller findByDepartment =====");
        System.out.println();
        Department department = new Department(2, null);
        List<Seller> sellerList = sellerDao.findByDepartment(department);
        sellerList.forEach(System.out::println);


        System.out.println("===== Teste 3: Seller findAll =====");
        System.out.println();
        sellerList = sellerDao.findAll();
        sellerList.forEach(System.out::println);

        System.out.println("===== Teste 4: Seller insert =====");

        Seller newSeller = new Seller(null, "Rafael", "Rafael@gmail.com", new Date(), 24359.10, department);
        sellerDao.insert(newSeller);

        System.out.println("Inserted. New Id: " + newSeller.getId());

        System.out.println("===== Teste 5: Seller update =====");
        seller = sellerDao.findById(1);
        seller.setName("Marcela Dambrosio");
        seller.setEmail("marcela@gmail.com");
        sellerDao.update(seller);
        System.out.println("Updated completed.");

        System.out.println("===== Teste 6: Seller delete =====");
        System.out.print("Enter id for delete test: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Delete completed");

    }

}
