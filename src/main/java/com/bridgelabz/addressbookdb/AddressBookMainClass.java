package com.bridgelabz.addressbookdb;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.Comparator;

public class AddressBookMainClass {
	private static Scanner sc = new Scanner(System.in);
	private static AddressBook addressbookValue = new AddressBook();

	private static AddressBookMainClass addressbooks = new AddressBookMainClass();
	private Map<String, AddressBook> addressBookSystem = new HashMap<>();

	public static void main(String[] args) {
		System.out.println("Welcome to the Address Book Program\n");

		addressbooks.addContacts();

	}

	public void addContacts() {

		System.out.println("Enter your choice");
		System.out.println(
				"1 : Add new contact    2 : Edit contact  3 : Delete contact  4: Add Multiple Contacts 5: Display Contacts 6: Search Person 7: Person with City and State"
						+ " 8: Count person by city and state 9: Sorted Person's by alphabetically in Address Book 10: Sorted Person's by alphabetically by City State And Zip Code"
						+ "11: write the addressbook with persons contact into text file 14: Rerieve all the Entries from the DB 15: update the Contact Information DB ");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:

			List<Contact> contacts = new ArrayList<>();
			System.out.println("Enter Your Address Book name ");
			String addresBookname = sc.next();

			System.out.println("Enter the First Name");
			String firstName = sc.next();

			System.out.println("Enter the Last Name");
			String lastName = sc.next();

			System.out.println("Enter the Address Name");
			String address = sc.next();

			System.out.println("Enter the City Name");
			String city = sc.next();

			System.out.println("Enter the State Name");
			String state = sc.next();

			System.out.println("Enter the Zip Name");
			int zip = sc.nextInt();

			System.out.println("Enter the PhoneNumber");
			long phoneNumber = sc.nextLong();

			System.out.println("Enter the email");
			String email = sc.next();

			Contact contact = new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
			contacts.add(contact);
			addressbookValue.setContacts(contacts);
			addressBookSystem.put(addresBookname, addressbookValue);
			addressbooks.addContacts();

			break;
		case 2:
			System.out.println("Enter Your Address Book name ");
			String findAddressBook = sc.next();

			System.out.println("Enter the name of user to Edit");
			String nameToEdit = sc.next();

			System.out.println("Enter the First Name");
			String editFirstName = sc.next();

			System.out.println("Enter the Last Name");
			String editLastName = sc.next();

			System.out.println("Enter the Address Name");
			String editAddress = sc.next();

			System.out.println("Enter the City Name");
			String editCity = sc.next();

			System.out.println("Enter the State Name");
			String editState = sc.next();

			System.out.println("Enter the Zip Name");
			int editZip = sc.nextInt();

			System.out.println("Enter the PhoneNumber");
			long editPhoneNumber = sc.nextLong();

			System.out.println("Enter the email");
			String editEmail = sc.next();
			addressbooks.editContact(findAddressBook, nameToEdit, editFirstName, editLastName, editAddress, editCity,
					editState, editZip, editPhoneNumber, editEmail);
			addressbooks.addContacts();

		case 3:
			System.out.println("Enter Your Address Book name ");
			String yourBookname = sc.next();
			System.out.println("Enter the person Name");
			String deletename = sc.next();

			addressbooks.deleteContactDetails(deletename, yourBookname);
			addressbooks.addContacts();
			break;
		case 4:

			addressbooks.addMultipleContacts();
			addressbooks.addContacts();
			break;
		case 5:
			addressbooks.displayContacts(addressBookSystem);
			addressbooks.addContacts();
			break;

		case 6:
			System.out.println("Search the person in perticular city or state ");
			System.out.println("Please Enter the City Name ");
			String cityname = sc.next();
			System.out.println("Please Enter the State Name ");
			String statename = sc.next();
			addressbooks.searchPerson(cityname, statename);
			addressbooks.addContacts();
			break;
		case 7:
			viewCityAndPersonAsWellAsStateAndPesron();
			addressbooks.addContacts();
			break;
		case 8:
			addressbooks.numberOfContactsCountByCityAndState();
			addressbooks.addContacts();
			break;
		case 9:
			addressbooks.sortEntriesInAddressBookByName();
			addressbooks.addContacts();
			break;
		case 10:
			addressbooks.sortEntriesInAddressBookByCitySateAndZip();
			addressbooks.addContacts();
			break;
		case 11:
			FileHandling fileHandling = new FileHandling();
			fileHandling.createFile();
			fileHandling.writeContactToFile(addressBookSystem);
			addressbooks.addContacts();
			break;
		case 12:
			FileHandling fileHandlingcsv = new FileHandling();

			try {
				fileHandlingcsv.writeContactToCsv(addressBookSystem);
			} catch (IOException e) {
				e.printStackTrace();
			}
			addressbooks.addContacts();
		case 13:
			FileHandling fileHandlingjson = new FileHandling();
			fileHandlingjson.writeContactToJson(addressBookSystem);
			addressbooks.addContacts();
			break;
		case 14:
			DataBaseOperation db = new DataBaseOperation();
			try {
				db.retrieveAllData();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			addressbooks.addContacts();
			break;
		case 15:
			DataBaseOperation dbupdate = new DataBaseOperation();
			try {
				dbupdate.updateDataByName();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			addressbooks.addContacts();
			break;
		case 16:
			DataBaseOperation dbupdatebydate = new DataBaseOperation();
			try {
				dbupdatebydate.getContactsInDatePeriod();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			addressbooks.addContacts();
			break;
		default:
			System.out.println("Please Enter correct choice");
		}
		
	}

	public void editContact(String findAddressBook, String name, String editFirstName, String editLastName,
			String editAddress, String editCity, String editState, int editZip, long editPhoneNumber,
			String editEmail) {

		AddressBook addressbook = new AddressBook();

		AddressBook addressbookdetails = addressBookSystem.get(findAddressBook);
		List<Contact> contactDetails = addressbookdetails.getContacts();
		for (int i = 0; i <= contactDetails.size() - 1; i++) {

			Contact contactperson = contactDetails.get(i);
			if (contactperson.getFirstName().equals(name)) {

				contactperson.setFirstName(editFirstName);
				contactperson.setLastName(editLastName);
				contactperson.setAddress(editAddress);
				contactperson.setCity(editCity);
				contactperson.setPhoneNumber(editPhoneNumber);
				contactperson.setState(editState);
				contactperson.setZip(editZip);
				contactperson.setEmail(editEmail);
				contactDetails.set(i, contactperson);
				addressbook.setContacts(contactDetails);
				addressBookSystem.put(findAddressBook, addressbook);
			}
		}
		addressbooks.displayContacts(addressBookSystem);
	}

	public void displayContacts(Map<String, AddressBook> addressBookSystem) {

		for (Entry<String, AddressBook> set : addressBookSystem.entrySet()) {

			AddressBook values = set.getValue();
			List<Contact> contactDetails = values.getContacts();

			for (int i = 0; i <= contactDetails.size() - 1; i++) {
				Contact contactperson = contactDetails.get(i);
				if (!contactperson.getFirstName().equals("")) {

					System.out.println("Conatct Details :");
					System.out.println("Book  Name --- " + set.getKey());
					System.out.println("FirstName :" + contactperson.getFirstName() + "      LastName :"
							+ contactperson.getLastName() + "     Address :" + contactperson.getAddress()
							+ "      City :" + contactperson.getCity() + "       State :" + contactperson.getState()
							+ "     Zip :" + contactperson.getZip() + "     Phone Number :"
							+ contactperson.getPhoneNumber());
				} else {

					System.out.println("No Conatct Details available :");
				}
			}
		}

	}

	public void deleteContactDetails(String name, String yourBookname) {

		AddressBook addressbook = new AddressBook();

		AddressBook values = addressBookSystem.get(yourBookname);
		List<Contact> contactDetails = values.getContacts();

		for (int i = 0; i <= contactDetails.size() - 1; i++) {
			Contact contactperson = contactDetails.get(i);
			if (contactperson.getFirstName().equals(name)) {
				contactDetails.remove(i);
				addressbook.setContacts(contactDetails);
				addressBookSystem.put(yourBookname, addressbook);
			}
		}

		System.out.println("Contact deleted Successfully");
	}

	public void addMultipleContacts() {

		List<Contact> contacts = new ArrayList<>();
		System.out.println("Enter Your Address Book name ");
		String findAddressBook = sc.next();

		System.out.println("Enter the First Name");
		String firstName = sc.next();

		System.out.println("Enter the Last Name");
		String lastName = sc.next();

		System.out.println("Enter the Address Name");
		String address = sc.next();

		System.out.println("Enter the City Name");
		String city = sc.next();

		System.out.println("Enter the State Name");
		String state = sc.next();

		System.out.println("Enter the Zip Name");
		int zip = sc.nextInt();

		System.out.println("Enter the PhoneNumber");
		long phoneNumber = sc.nextLong();

		System.out.println("Enter the email");
		String email = sc.next();
		Contact contact = new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
		contacts.add(contact);

		addtoOurAdrressBook(contact, findAddressBook);

		System.out.println("Do you want add one more Contact Details");
		System.out.println("1 :Yes     2 :No");
		int choice = sc.nextInt();
		switch (choice) {

		case 1:
			addressbooks.addMultipleContacts();
			break;
		case 2:
			addressbooks.displayContacts(addressBookSystem);
			addressbooks.addContacts();
			break;
		default:
			System.out.println("Please Enter correct choice");

		}

		addressbooks.displayContacts(addressBookSystem);
		addressbooks.addContacts();

	}

	public void addtoOurAdrressBook(Contact contact, String addressBookName) {
		List<Contact> contactsLis = new ArrayList<>();
		AddressBook Addressvalues = new AddressBook();
		boolean isKeyPresent = addressBookSystem.containsKey(addressBookName);
		if (isKeyPresent) {
			AddressBook values = addressBookSystem.get(addressBookName);
			List<Contact> contactDetails = values.getContacts();
			boolean isPresent = contactDetails.stream()
					.anyMatch(con -> con.getFirstName().equals(contact.getFirstName()));
			if (isPresent) {

				System.out.println("This peson name already persent");

			} else {
				contactDetails.add(contact);
				values.setContacts(contactDetails);
				addressBookSystem.put(addressBookName, values);
			}

		} else {
			contactsLis.add(contact);
			Addressvalues.setContacts(contactsLis);
			addressBookSystem.put(addressBookName, Addressvalues);
		}
	}

	public void searchPerson(String cityname, String statename) {
		List<Contact> contactsList = new ArrayList<>();
		for (Map.Entry<String, AddressBook> set : addressBookSystem.entrySet()) {
			AddressBook addressBook = set.getValue();
			contactsList = addressBook.getContacts();
			boolean isPresent = contactsList.stream()
					.anyMatch(con -> con.getCity().equals(cityname) || con.getState().equals(statename));
			if (isPresent) {
				contactsList.stream().filter(s -> s.getCity().equals(cityname) || s.getState().equals(statename))
						.sorted().forEachOrdered(conts -> System.out.println("User name :" + conts.getFirstName()));

			} else {

				System.out.println("This peson not present in this city or state");
			}

		}
	}

	public void viewCityAndPersonAsWellAsStateAndPesron() {
		List<Contact> contactsList = new ArrayList<>();
		for (Map.Entry<String, AddressBook> set : addressBookSystem.entrySet()) {
			AddressBook addressBook = set.getValue();
			contactsList = addressBook.getContacts();
			System.out.println("Person Name and His/her city");
			contactsList.stream()
					.forEachOrdered(con -> System.out.println(con.getFirstName() + "     " + con.getCity()));
			System.out.println("Person Name and His/her State");
			contactsList.stream()
					.forEachOrdered(con -> System.out.println(con.getFirstName() + "     " + con.getState()));
		}

	}

	public void numberOfContactsCountByCityAndState() {
		List<Contact> contactsList = new ArrayList<>();
		for (Map.Entry<String, AddressBook> set : addressBookSystem.entrySet()) {
			AddressBook addressBook = set.getValue();
			contactsList = addressBook.getContacts();
			Map<Object, Integer> list = contactsList.parallelStream()
					.collect(Collectors.toConcurrentMap(w -> w.getCity(), w -> 1, Integer::sum));
			Map<Object, Integer> state = contactsList.parallelStream()
					.collect(Collectors.toConcurrentMap(w -> w.getState(), w -> 1, Integer::sum));
			System.out.println("City Name" + list.keySet() + ":  Number of persons in City " + list.values()
					+ "        State Name" + state.keySet() + ":  Number of persons in State " + state.values());

		}

	}

	public void sortEntriesInAddressBookByName() {
		List<Contact> contactsList = new ArrayList<>();
		for (Map.Entry<String, AddressBook> set : addressBookSystem.entrySet()) {
			AddressBook addressBook = set.getValue();
			contactsList = addressBook.getContacts();
			System.out.println("Sorted Person's by alphabetically in Address Book");
			List<Contact> sortedList = contactsList.stream().sorted(Comparator.comparing(Contact::getFirstName))
					.collect(Collectors.toList());
			sortedList.forEach(con -> System.out.println(con.getFirstName()));
		}

	}

	public void sortEntriesInAddressBookByCitySateAndZip() {
		List<Contact> contactsList = new ArrayList<>();
		for (Map.Entry<String, AddressBook> set : addressBookSystem.entrySet()) {
			AddressBook addressBook = set.getValue();
			contactsList = addressBook.getContacts();
			System.out.println("Sorted Person's by alphabetically in Address Book");
			System.out.println("Sorted Person's by City Name");
			List<Contact> sortedListCity = contactsList.stream().sorted(Comparator.comparing(Contact::getCity))
					.collect(Collectors.toList());
			sortedListCity.forEach(con -> System.out.println(con.getCity()));
			System.out.println("Sorted Person's by State Name");
			List<Contact> sortedListState = contactsList.stream().sorted(Comparator.comparing(Contact::getCity))
					.collect(Collectors.toList());
			sortedListState.forEach(con -> System.out.println(con.getState()));
			System.out.println("Sorted Person's by Zip Code");
			List<Contact> sortedListZip = contactsList.stream().sorted(Comparator.comparing(Contact::getZip))
					.collect(Collectors.toList());
			sortedListZip.forEach(con -> System.out.println(con.getZip()));

		}

	}

}