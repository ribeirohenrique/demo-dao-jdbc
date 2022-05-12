package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import java.sql.*;


public class Program {
    public static void main(String[] args) {

        Department obj = new Department(1, "Books");
        System.out.println(obj);

        Seller seller = new Seller(1, "Fulano", "fulano@gmail.com", new Date(), 2000.00, obj);

        System.out.println(seller);

        SellerDao sellerDao = DaoFactory.createSellerDao();

    }

}
