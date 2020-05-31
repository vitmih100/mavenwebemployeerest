package com.step;

import enums.Gender;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDb{
    
    public EmployeeDb() {
        try {
            // Register JDBC driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    
  public List<Employee> get(String searchParametr,int numParametr) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    List<Employee> list = new ArrayList<>();
    String sql = "SELECT id, idnp, nume, prenume, sarariu, data_ang, data_nast, gen " +
                 "FROM postgres.employee ";
    String sqlo = " order by id";
    PreparedStatement statement=null;

    try(Connection conn = ConnectionDb.initConnection()){
        switch(numParametr){
          case 0: 
            statement = conn.prepareStatement(sql);
            break;
          case 1: 
            sql=sql+"where id=?" + sqlo;
            statement = conn.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(searchParametr));
            break;
          case 2: 
            //System.out.println("(Integer)searchParametr="+(Integer)searchParametr);
            sql=sql+"where idnp=?" + sqlo;
            statement = conn.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(searchParametr));
            break;
          case 3: 
            sql=sql+"where nume=?" + sqlo;
            statement = conn.prepareStatement(sql);
            statement.setString(1,searchParametr);
            break;
          case 4: 
            sql=sql+"where prenume=?" + sqlo;
            statement = conn.prepareStatement(sql);
            statement.setString(1,searchParametr);
            break;
          case 5: 
            sql=sql+"where sarariu=?" + sqlo;
            statement = conn.prepareStatement(sql);
            statement.setDouble(1,Double.parseDouble(searchParametr));
            break;
          case 6: 
            sql=sql+"where data_ang=?" + sqlo;
            statement = conn.prepareStatement(sql);
            statement.setDate(1,Date.valueOf(LocalDate.parse(searchParametr,formatter)));
            break;
          case 7: 
            sql=sql+"where data_nast=?" + sqlo;
            statement = conn.prepareStatement(sql);
            statement.setDate(1,Date.valueOf(LocalDate.parse(searchParametr,formatter)));
            break;
          case 8: 
            sql=sql+"where gen=?" + sqlo;
            statement = conn.prepareStatement(sql);
            
            Gender gender;
            try{
               gender = Gender.valueOfIgnoreCase(searchParametr);
            }
            catch(IllegalArgumentException e){
                gender = Gender.valueOf(searchParametr);
            }
            statement.setInt(1,gender.ordinal());
            break;
          }
        ResultSet result = statement.executeQuery();
        while (result.next()) {
          int id = result.getInt("id");
          int idnp = result.getInt("idnp");
          String name = result.getString("nume");
          String surname = result.getString("prenume");
          double salary = result.getDouble("sarariu");
          LocalDate hireDay = result.getDate("data_ang").toLocalDate();
          LocalDate birthDay = result.getDate("data_nast").toLocalDate(); 
          Gender gender = Gender.values()[result.getInt("gen")];
          list.add(new Employee(id,idnp,name,surname,salary,hireDay,birthDay,gender));
        }
        return list;
    } catch(SQLException ex){
      System.out.println("ERROR! Select failed. " + ex.getMessage());
      ex.printStackTrace();
    }
    return null;
  }
  
  public int update(Employee emp){
  
    String sql = "update postgres.employee set idnp=?, nume=?, prenume=?, sarariu=?, data_ang=?, data_nast=?, gen=? where id=?";
    try(Connection conn = ConnectionDb.initConnection();
       PreparedStatement statement = conn.prepareStatement(sql)){
       statement.setInt(1,emp.getIdnp());
       statement.setString(2,emp.getName());
       statement.setString(3,emp.getSurName());
       statement.setDouble(4, emp.getSalary());
       statement.setDate(5,Date.valueOf(emp.getHireDay()));
       statement.setDate(6,Date.valueOf(emp.getBirthDay()));
       statement.setInt(7,emp.getGender().ordinal());      
       statement.setInt(8,emp.getId());
       int affectedRows = statement.executeUpdate();
       System.out.println(String.format("Executed update statement. Affected %d rows", affectedRows));
       return affectedRows;          
        
    }catch(SQLException ex){
      System.out.println("ERROR! Update failed. " + ex.getMessage());
      ex.printStackTrace();
    }
    return -1;
  }
  
  public int insert(Employee emp) {
     String sql = "insert into postgres.employee(idnp,nume,prenume,sarariu,data_ang,data_nast,gen)"
                //+ "values(?,?,?,?,?,?,?)";
                + "values(nextval('postgres.employee_idnp_seq'),?,?,?,?,?,?)";     
    
    try(Connection conn = ConnectionDb.initConnection();
        PreparedStatement statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
        
        //statement.setInt   (1, emp.getIdnp());
        statement.setString(1, emp.getName());
        statement.setString(2, emp.getSurName());
        statement.setDouble(3, emp.getSalary());
        statement.setDate  (4, Date.valueOf(emp.getHireDay()));
        statement.setDate  (5, Date.valueOf(emp.getBirthDay()));
        statement.setInt   (6, emp.getGender().ordinal());
        int affectedRows = statement.executeUpdate();
        if(affectedRows>0){
          ResultSet rsVal=statement.getGeneratedKeys();
          rsVal.next();
          emp.setId(rsVal.getInt(1));
          emp.setIdnp(rsVal.getInt(2));
        }
        //System.out.println("Inserted" + rows + " rows"); 
        System.out.println(String.format("Executed insert statement. Affected %d rows", affectedRows));
        return affectedRows;
    } catch(SQLException ex){
      System.out.println("ERROR! Insert failed. " + ex.getMessage());
      ex.printStackTrace();
    } 
    return -1;
  } 
  
  public int delete(Employee emp){
    String sql = "delete from postgres.employee where id=?";
    //System.out.println("emp.getId()="+emp.getName());
    try(Connection conn = ConnectionDb.initConnection();
      PreparedStatement statement = conn.prepareStatement(sql)){
      statement.setInt(1,emp.getId());
      int affectedRows = statement.executeUpdate();
      System.out.println(String.format("Executed delete statement. Affected %d rows", affectedRows));
      return affectedRows;    
    
    }catch(SQLException ex){
      System.out.println("ERROR! Delete failed. " + ex.getMessage());
    }
    return -1;
  }
  
  public int delete(int id){
    String sql = "delete from postgres.employee where id=?";
    //System.out.println("emp.getId()="+emp.getName());
    try(Connection conn = ConnectionDb.initConnection();
      PreparedStatement statement = conn.prepareStatement(sql)){
      statement.setInt(1,id);
      int affectedRows = statement.executeUpdate();
      System.out.println(String.format("Executed delete statement. Affected %d rows", affectedRows));
      return affectedRows;    
    
    }catch(SQLException ex){
      System.out.println("ERROR! Delete failed. " + ex.getMessage());
    }
    return -1;
  }  

}
