package com.step;

import enums.Gender;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/employees")
public class EmployeeService {

    private static final String SUCCESS_RESULT = "<result>success</result>";
    private static final String FAILURE_RESULT = "<result>failure</result>";
    EmployeeDb employeeDb = new EmployeeDb();

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Employee> getUsers() {
        return employeeDb.get("0", 0);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Employee> getUser(@PathParam("id") String userid) {
        return employeeDb.get(userid, 1);
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String createUser(@FormParam("id") String id,
            @FormParam("name") String name,
            @FormParam("surName") String surName,
            @FormParam("salary") String salary,
            @FormParam("hireDay") String hireDay,
            @FormParam("birthDay") String birthDay,
            @FormParam("gender") String genderStr
    ) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        Gender gender;
        try {
            gender = Gender.valueOfIgnoreCase(genderStr);
        } catch (IllegalArgumentException e) {
            gender = Gender.valueOf(genderStr);
        }

        Employee user = new Employee(Integer.parseInt(id), name, surName, Double.parseDouble(salary), LocalDate.parse(hireDay, formatter), LocalDate.parse(birthDay, formatter), gender);
        int result = employeeDb.insert(user);
        if (result == 1) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @PUT
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String updateUser(@FormParam("id") String id,
            @FormParam("name") String name,
            @FormParam("surName") String surName,
            @FormParam("salary") String salary,
            @FormParam("hireDay") String hireDay,
            @FormParam("birthDay") String birthDay,
            @FormParam("gender") String genderStr
    ) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        Gender gender;
        try {
            gender = Gender.valueOfIgnoreCase(genderStr);
        } catch (IllegalArgumentException e) {
            gender = Gender.valueOf(genderStr);
        }

        Employee user = new Employee(Integer.parseInt(id), name, surName, Double.parseDouble(salary), LocalDate.parse(hireDay, formatter), LocalDate.parse(birthDay, formatter), gender);
        int result = employeeDb.update(user);
        if (result == 1) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String deleteUser(@FormParam("id") String id,
            @FormParam("name") String name,
            @FormParam("surName") String surName,
            @FormParam("salary") String salary,
            @FormParam("hireDay") String hireDay,
            @FormParam("birthDay") String birthDay,
            @FormParam("gender") String genderStr
    ) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        Gender gender;
        try {
            gender = Gender.valueOfIgnoreCase(genderStr);
        } catch (IllegalArgumentException e) {
            gender = Gender.valueOf(genderStr);
        }

        Employee user = new Employee(Integer.parseInt(id), name, surName, Double.parseDouble(salary), LocalDate.parse(hireDay, formatter), LocalDate.parse(birthDay, formatter), gender);
        int result = employeeDb.delete(user);
        if (result == 1) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public String deleteUserId(@PathParam("id") int id) {
        int result = employeeDb.delete(id);
        if (result == 1) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @OPTIONS
    @Produces(MediaType.APPLICATION_XML)
    public String getSupportedOperations(){
      return "<operations>GET, PUT, POST, DELETE</operations>";
    
    }
}
