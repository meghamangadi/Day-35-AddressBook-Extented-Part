package com.bridgelabz.addressbookdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;

public class DataBaseOperation {

	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/AddressBook";
		String userName = "root";
		String password = "root";
		Connection connection = null;
		try {
			//DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			 
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

}
