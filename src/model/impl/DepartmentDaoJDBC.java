package model.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection connection;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO DEPARTMENT " +
                            "(Name) " +
                            "VALUES " +
                            "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, department.getName());
            int rowsAffected = preparedStatement.executeUpdate();

            //se for maior que zero significa que inseriu
            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                //se o valor abaixo existir, ele vai ser pego e atribuir o Id dentro do objeto Seller e mostrar na tela
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    department.setId(id);
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DbException("Unexpected error. No rows affected");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }

    }

    @Override
    public void update(Department department) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE DEPARTMENT " +
                            "SET Name = ? " +
                            "WHERE Id = ?");

            preparedStatement.setString(1, department.getName());
            preparedStatement.setInt(2, department.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM DEPARTMENT WHERE Id = ?");

            preparedStatement.setInt(1, id);
            int rows = preparedStatement.executeUpdate();

            if (rows == 0) {
                throw new DbException("Error. Id not found");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());

        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM DEPARTMENT " +
                            "WHERE Id = ?");

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                //Ele instancia o Departamento que est?? no m??todo no fim da classe
                Department department = instantiateDepartment(resultSet);
                return department;
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
    public List<Department> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM DEPARTMENT ORDER BY Id");

            resultSet = preparedStatement.executeQuery();
            List<Department> departmentList = new ArrayList<>();

            while (resultSet.next()) {
                Department department = new Department(resultSet.getInt("Id"), resultSet.getString("Name"));
                departmentList.add(department);
            }
            return departmentList;


        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(preparedStatement);
        }
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("Id"));
        department.setName(resultSet.getString("Name"));
        return department;
    }
}
