package com.example.EducationDepartment.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * @author RaisAhmad
 * @date 29/10/2021
 * @Discription Teacher's Class
 *
 */
@Entity
@Table(name = "t_teacher")
public class Teacher {

	 @Id
	    @Column(nullable = false)
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long id; 
	 
	    @Column(nullable = false)
	    private String firstName; 
	    
	    @Column(nullable = false)
	    private String lastName;
	    
	    @Column(nullable = false, unique = true)
	    private String email;
	    
	    @Column(nullable = false)
	    private int age;
	    
	    @Column(nullable = false)
	    private String password; 
	    
	    @Column(nullable = true)
	    private String designation; 
	    
	    @Column(nullable = false)
	    private String phone;
	    
	    @Column(nullable = true)
	    private String departmentName;

	    @Column(nullable = false)
	    private String address;

	    @Column(nullable = false, unique= true)
	    private String cnic;
	    
	    @Column(nullable = true)
	    private String expirence;
	    
	    @Column(nullable = true)
	    private Date date;
	    
	    @Column(nullable = true)
	    private Date updatedDate;
	    
	    @Column(nullable = true)
	    private Boolean criminalStatus;
	    
		@Column(nullable = true)
		private int smsToken;
		
		@Column(nullable = true)
		private int emailToken;
		
		@Column(nullable = true)
		private boolean status;
		
		@Column(nullable = true)
		private Date expirationDate;
		
	    

		public Date getExpirationDate() {
			return expirationDate;
		}

		public void setExpirationDate(Date expirationDate) {
			this.expirationDate = expirationDate;
		}

		public int getSmsToken() {
			return smsToken;
		}

		public void setSmsToken(int smsToken) {
			this.smsToken = smsToken;
		}

		public int getEmailToken() {
			return emailToken;
		}

		public void setEmailToken(int emailToken) {
			this.emailToken = emailToken;
		}

		public boolean isStatus() {
			return status;
		}

		public void setStatus(boolean status) {
			this.status = status;
		}

	
		public Date getUpdatedDate() {
			return updatedDate;
		}

		public void setUpdatedDate(Date updatedDate) {
			this.updatedDate = updatedDate;
		}

		public Boolean getCriminalStatus() {
			return criminalStatus;
		}

		public void setCriminalStatus(Boolean criminalStatus) {
			this.criminalStatus = criminalStatus;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getDesignation() {
			return designation;
		}

		public void setDesignation(String designation) {
			this.designation = designation;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getDepartmentName() {
			return departmentName;
		}

		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getCnic() {
			return cnic;
		}

		public void setCnic(String cnic) {
			this.cnic = cnic;
		}

		public String getExpirence() {
			return expirence;
		}

		public void setExpirence(String expirence) {
			this.expirence = expirence;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

	

	    
	
}
