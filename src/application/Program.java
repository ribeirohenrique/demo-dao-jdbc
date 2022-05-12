package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.awt.font.FontRenderContext;
import java.util.List;


public class Program {
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



    }

}
