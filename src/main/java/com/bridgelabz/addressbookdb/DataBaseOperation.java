package com.bridgelabz.addressbookdb;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import java.util.Scanner;

import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;

public class DataBaseOperation {

	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/AddressBook";
		String userName = "root";
		String password = "root";
		Connection connection = null;
		try {
			// DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

			connection = DriverManager.getConnection(url, userName, password);
			System.out.println("Connection established successfully..!!");
		} catch (SQLException e) {
			System.out.println(e);
		}
		return connection;
	}

	public void retrieveAllData() throws SQLException {

		Connection con = getConnection();
		// Retrieve all the data
		if (con != null) {
			String retrieveQuery = "SELECT * FROM addressbook JOIN contacts ON addressbook.id = contacts.id";
			Statement statement = (Statement) con.createStatement();
			ResultSet resultSet = statement.executeQuery(retrieveQuery);
			System.out.println("\nContact retrieved..");
			while (resultSet.next()) {

				int addressBookId = resultSet.getInt("id");
				String addressBookName = resultSet.getString("address_book");
				int personId = resultSet.getInt("id");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String address = resultSet.getString("address");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				int phoneNo = resultSet.getInt("phoneNumber");
				String email = resultSet.getString("email");
				int zip = resultSet.getInt("zip");

				String rowData = String.format(
						"\nAddressBook Id : %d \nAddressBook Name : %s \nPerson Id : %d \nFirst Name : %s  \nLast Name : %s \nAddress : %s \nCity : %s \nState : %s \nPhone Number : %s \nE-mail : %s \nZip : %d",
						addressBookId, addressBookName, personId, firstName, lastName, address, city, state, phoneNo,
						email, zip);
				System.out.println(rowData + " \n ");
			}
		}
	}

	public void updateDataByName() throws SQLException {

		Connection con = getConnection();
		// Update data with salary by person's name
		if (con != null) {

			String updateQuery = "UPDATE contacts SET address = ? WHERE firstName = ?";
			PreparedStatement updateStatement = con.prepareStatement(updateQuery);
			updateStatement.setString(1, "Navanagar");
			updateStatement.setString(2, "megha");

			int rowUpdated = updateStatement.executeUpdate();
			if (rowUpdated > 0) {
				System.out.println("Contact Updated");
			}
		}
	}

	public void getContactsInDatePeriod() throws SQLException {

		Connection con = getConnection();
		if (con != null) {
			String retrieveQuery = "SELECT * FROM contacts JOIN addressbook ON addressbook.id = contacts.id WHERE date BETWEEN CAST('2019-07-01' AS DATE) AND DATE(NOW());";
			Statement statement = (Statement) con.createStatement();
			ResultSet resultSet = statement.executeQuery(retrieveQuery);
			displayResultSet(resultSet);
		}
	}

	public void getNoOfContactsByState() throws SQLException {

		Scanner sc = new Scanner(System.in);
		Connection con = getConnection();
		System.out.print("\nEnter State : ");
		String state = sc.nextLine();

		if (con != null) {
			String retrieveQuery = "SELECT COUNT(*) AS 'count' FROM contacts JOIN addressbook ON addressbook.id = contacts.id WHERE state = ?";
			PreparedStatement statement = con.prepareStatement(retrieveQuery);
			statement.setString(1, state);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int contactCount = resultSet.getInt("count");
				System.out.println("\nNumber of contacts belongs to " + state + " : " + contactCount);
			}
		}
	}

	public void displayResultSet(ResultSet resultSet) throws SQLException {

		System.out.println("\nContact retrieved..");
		while (resultSet.next()) {

			int addressBookId = resultSet.getInt("id");
			String addressBookName = resultSet.getString("address_book");
			int personId = resultSet.getInt("id");
			String firstName = resultSet.getString("firstName");
			String lastName = resultSet.getString("lastName");
			String address = resultSet.getString("address");
			String city = resultSet.getString("city");
			String state = resultSet.getString("state");
			int phoneNo = resultSet.getInt("phoneNumber");
			String email = resultSet.getString("email");
			int zip = resultSet.getInt("zip");
			String date = resultSet.getDate("date").toString();

			String rowData = String.format(
					"\nAddressBook Id : %d \nAddressBook Name : %s \nPerson Id : %d \nFirst Name : %s  \nLast Name : %s \nAddress : %s \nCity : %s \nState : %s \nPhone Number : %s \nE-mail : %s \nZip : %d \nDate : %s",
					addressBookId, addressBookName, personId, firstName, lastName, address, city, state, phoneNo, email,
					zip, date);
			System.out.println(rowData + " \n ");
		}
	}
	
	

	public void insertContact() throws SQLException {
		
		Connection con = getConnection();
		try {
			if(con != null) {
				con.setAutoCommit(false);
				
				String insertQuery = "INSERT INTO addressbook (address_book) VALUES (?)";
				PreparedStatement insertStatement = con.prepareStatement(insertQuery);
				insertStatement.setString(1, "Other");
				int rowInserted = insertStatement.executeUpdate();
				
				
				insertQuery = "INSERT INTO contacts (id,firstName,lastName,address,city,state,phoneNumber,email,zip,date) VALUES (?,?,?,?,?,?,?,?,?,?)";
				insertStatement = con.prepareStatement(insertQuery);
				insertStatement.setInt(1, 1);
				insertStatement.setString(2, "kittu");
				insertStatement.setString(3, "Arabbi");
				insertStatement.setString(4, "Vidyagiri");
				insertStatement.setString(5, "bagalkot");
				insertStatement.setString(6, "karnataka");
				insertStatement.setInt(7, 919);
				insertStatement.setString(8, "kittu@gmail.com");
				insertStatement.setInt(9, 909);
				insertStatement.setDate(10, new Date(2021-05-25));
				rowInserted = insertStatement.executeUpdate();
				
				con.commit();
				if(rowInserted > 0){
					System.out.println("Contact Inserted");
				}
				con.setAutoCommit(true);
			}
			
		}catch(SQLException sqlException) {
			System.out.println("Insertion Rollbacked");
			con.rollback();

	    }finally {
	    	if(con != null) {
				con.close();
	    	}
	    }
	}
}
