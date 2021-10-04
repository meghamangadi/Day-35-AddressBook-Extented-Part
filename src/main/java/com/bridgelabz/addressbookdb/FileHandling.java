package com.bridgelabz.addressbookdb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.opencsv.CSVWriter;

public class FileHandling {


	public void createFile() {
		try {
			File myObj = new File("AddressBook.txt");
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public void writeContactToFile(Map<String, AddressBook> addressBookSystem) {

		StringBuffer contactBuffer = new StringBuffer();
		for (String keyName : addressBookSystem.keySet()) {

			AddressBook addrBookObj = addressBookSystem.get(keyName);
			contactBuffer.append("Contact Details 	:\n");
			contactBuffer.append("Address Book Name :" + keyName + "\n");
			addrBookObj.getContacts().forEach((contact) -> {
				String contactString = ("FirstName :" + contact.getFirstName().toString().concat(", "));
				contactString += ("LastName	:" + contact.getLastName().toString().concat(", "));
				contactString += ("Address	:" + contact.getAddress().toString().concat(", "));
				contactString += ("City	:" + contact.getCity().toString().concat(", "));
				contactString += ("State :" + contact.getState().toString().concat(", "));
				contactString += ("PhoneNumber :" + String.valueOf(contact.getPhoneNumber()).concat(", "));
				contactString += ("Email :" + contact.getEmail().toString().concat(", "));
				contactString += ("Zip	:" + String.valueOf(contact.getZip()).concat("\n"));
				contactBuffer.append(" " + contactString);
			});
		}
		try {

			Files.write(Paths.get("AddressBook.txt"), contactBuffer.toString().getBytes());
			System.out.print("\nDetails written into file");
		} catch (IOException e) {
			System.out.print("Unable to write contact into file" + e.getMessage());
		}
	}

	public void writeContactToCsv(Map<String, AddressBook> addressBookSystem) throws IOException {

		// Instantiating the CSVWriter class
		CSVWriter writer = new CSVWriter(new FileWriter("AddrBookFile.csv"));
		String line[] = { "AddressBook Name", "First Name", "Last Name", "Address", "City", "State", "Pone Number",
				"E-mail", "Zip" };
		List contactList = new ArrayList();
		contactList.add(line);
		for (String keyName : addressBookSystem.keySet()) {
			AddressBook addrBookObj = addressBookSystem.get(keyName);

			addrBookObj.getContacts().forEach((contact) -> {

				String line1[] = { keyName, contact.getFirstName().toString(), contact.getLastName().toString(),
						contact.getAddress().toString(), contact.getCity().toString(), contact.getState().toString(),
						String.valueOf(contact.getPhoneNumber()), contact.getEmail().toString(),
						String.valueOf(contact.getZip()) };
				contactList.add(line1);

			});
		}
		// Writing data to the csv file
		writer.writeAll(contactList);
		writer.flush();
		System.out.println("Details written into CSV");
	}
 
	
	   public void writeContactToJson(Map<String, AddressBook> addressBookSystem) {
	    	
		    try {
		    	// create a writer
		        Writer writer = new FileWriter("AddrBookFilejson.json");
		        // convert map to JSON File
		        new Gson().toJson(addressBookSystem, writer);
		        // close the writer
		        writer.close();
		        System.out.println("Details written into JSON File");
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
	    }

}
