package application;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.entities.Department;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {
    public static void main(String[] args) {

        Department obj = new Department(1, "Books");
        System.out.println(obj);

    }

}
