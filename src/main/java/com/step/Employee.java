package com.step;

import enums.Gender;
import java.io.Serializable;
import java.time.LocalDate;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employee")
public class Employee implements Serializable {

    private static final long serialversionUID = 1L;
    private int id;
    private int idnp;
    private static int nextIdnp = 1;
    private String name;
    private String surName;
    private double salary;
    private LocalDate hireDay;
    private LocalDate birthDay;
    private Gender gender;

    public Employee() {
    }

    public Employee(int id, String name, String surName, double salary, LocalDate hireDay, LocalDate birthDay, Gender gender) {
        this.id = id;
        //setIdnp();
        this.idnp = id;
        this.name = name;
        this.surName = surName;
        this.salary = salary;
        this.hireDay = hireDay;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    public Employee(int id, int idnp, String name, String surName, double salary, LocalDate hireDay, LocalDate birthDay, Gender gender) {
        this.id = id;
        this.idnp = idnp;
        this.name = name;
        this.surName = surName;
        this.salary = salary;
        this.hireDay = hireDay;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @XmlElement
    public void setIdnp(int idnp) {
        this.idnp = idnp;
    }

    public int getIdnp() {
        return this.idnp;
    }

    public void setIdnp() {
        this.idnp = this.nextIdnp++;
    }

    public static int getNextIdnp() {
        return nextIdnp++;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @XmlElement
    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getSurName() {
        return this.surName;
    }

    @XmlElement
    public void setSalary(double salary) throws IllegalArgumentException {
        if (salary > 0) {
            this.salary = salary;
        } else {
            throw new IllegalArgumentException("Salariul nu poate fi negativ");
        }
    }
    
    public double getSalary() {
        return this.salary;
    }

    @XmlElement
    public void setHireDay(LocalDate hireDay) {
        this.hireDay = hireDay;
    }

    public LocalDate getHireDay() {
        return this.hireDay;
    }

    @XmlElement
    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public LocalDate getBirthDay() {
        return this.birthDay;
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    public Gender getGender() {
        return gender;
    }

    @XmlElement
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Employee) {
            Employee emp = (Employee) obj;
            return this.name == emp.getName() && this.surName == emp.getSurName()
                    && this.salary == emp.getSalary() && this.hireDay == emp.getHireDay()
                    && this.birthDay == emp.getBirthDay() && this.gender == emp.getGender();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Employee [name=" + this.name + "surname=" + this.surName + "salary=" + this.salary + "gender" + this.gender;
    }

}
