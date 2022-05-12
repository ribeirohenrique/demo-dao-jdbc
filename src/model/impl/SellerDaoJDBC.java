package model.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT SELLER.*, DEPARTMENT.Name as DepName " +
                            "FROM SELLER INNER JOIN DEPARTMENT " +
                            "ON SELLER.DepartmentId = DEPARTMENT.Id " +
                            "WHERE SELLER.Id = ?");

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                //Ele instancia o Departamento que está no método no fim da classe
                Department department = instantiateDepartment(resultSet);

                //Ele instancia o Seller que está no método no fim da classe
                Seller seller = intatiateSeller(resultSet, department);

                return seller;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT SELLER.*, DEPARTMENT.Name as DepName " +
                            "FROM SELLER INNER JOIN DEPARTMENT " +
                            "ON SELLER.DepartmentId = DEPARTMENT.Id " +
                            "WHERE DepartmentId = ? " +
                            "ORDER BY Name");

            preparedStatement.setInt(1, department.getId());
            resultSet = preparedStatement.executeQuery();

            List<Seller> sellerList = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>();
            while (resultSet.next()) {

                //se o departamento ja existir o map vai pegar, ai o if dá falso e prossegue
                Department dep = departmentMap.get(resultSet.getInt("DepartmentId"));


                //se o dep nao existir, é retornado null e entra no if, instanciando e salvando no map
                if (dep == null) {
                    dep = instantiateDepartment(resultSet);
                    departmentMap.put(resultSet.getInt("DepartmentId"), dep);
                }

                //Ele instancia o Seller que está no método no fim da classe
                Seller seller = intatiateSeller(resultSet, dep);

                sellerList.add(seller);
            }
            return sellerList;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(preparedStatement);
        }

    }

    private Seller intatiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setBirthDate(resultSet.getDate("BirthDate"));
        seller.setDepartment(department);
        return seller;
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT SELLER.*, DEPARTMENT.Name as DepName " +
                            "FROM SELLER INNER JOIN DEPARTMENT " +
                            "ON SELLER.DepartmentId = DEPARTMENT.Id " +
                            "ORDER BY Name");

            resultSet = preparedStatement.executeQuery();

            List<Seller> sellerList = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>();
            while (resultSet.next()) {

                //se o departamento ja existir o map vai pegar, ai o if dá falso e prossegue
                Department dep = departmentMap.get(resultSet.getInt("DepartmentId"));


                //se o dep nao existir, é retornado null e entra no if, instanciando e salvando no map
                if (dep == null) {
                    dep = instantiateDepartment(resultSet);
                    departmentMap.put(resultSet.getInt("DepartmentId"), dep);
                }

                //Ele instancia o Seller que está no método no fim da classe
                Seller seller = intatiateSeller(resultSet, dep);

                sellerList.add(seller);
            }
            return sellerList;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(preparedStatement);
        }
    }
}
