package com.blz.addressbookrestapi;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.blz.addressbookrestapi.AddressbookRESTService.IOService.REST_IO;

public class AddressbookRESTServiceTest {

	static AddressbookRESTService addressbookRESTService;

	@BeforeClass
	public static void createObj() {
		addressbookRESTService = new AddressbookRESTService();
	}

	@AfterClass
	public static void nullObj() {
		addressbookRESTService = null;
	}

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	private AddressbookData[] getAddressbookList() {
		Response response = RestAssured.get("/addressbook");
		System.out.println("Adddressbook entries in JsonServer :\n" + response.asString());
		AddressbookData[] arrayOfPerson = new Gson().fromJson(response.asString(), AddressbookData[].class);
		return arrayOfPerson;
	}

	private Response addContactToJsonServer(AddressbookData addressbookData) {
		String contactJson = new Gson().toJson(addressbookData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contactJson);
		return request.post("/addressbook");
	}

	@Test
	public void givenAddressbookDataInJsonServer_WhenRetrieved_ShouldMatchPersonCount() {
		AddressbookData[] arrayOfPerson = getAddressbookList();
		addressbookRESTService = new AddressbookRESTService(Arrays.asList(arrayOfPerson));
		long entries = addressbookRESTService.countEntries(REST_IO);
		assertEquals(2, entries);
	}

	@Test
	public void givenMultiplePerson_WhenAdded_ShouldMatch201ResponseAndCount() {
		AddressbookData[] arrayOfContacts = getAddressbookList();
		addressbookRESTService = new AddressbookRESTService(Arrays.asList(arrayOfContacts));
		AddressbookData[] arrayOfPerson = {
				new AddressbookData(0, "Mark", "K", "Dadar", "Mumbai", "Maharashtra", "498892", "9876544213","mt@gm.com"),
				new AddressbookData(0, "Terrisa", "T", "Karve", "Pune", "Maharashtra", "491792", "9877543213","tt@gm.com"),
				new AddressbookData(0, "Charlie", "K", "S", "New Delhi", "Delhi", "493792", "9879543213","ck@gm.com") };

		for (AddressbookData addressbookData : arrayOfPerson) {

			Response response = addContactToJsonServer(addressbookData);
			int statusCode = response.getStatusCode();
			assertEquals(201, statusCode);

			addressbookData = new Gson().fromJson(response.asString(), AddressbookData.class);
			addressbookRESTService.addContactToAddressbook(addressbookData, REST_IO);
		}
		long entries = addressbookRESTService.countEntries(REST_IO);
		assertEquals(5, entries);
	}
}
