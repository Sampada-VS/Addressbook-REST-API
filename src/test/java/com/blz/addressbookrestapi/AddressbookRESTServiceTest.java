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

import static com.blz.addressbookrestapi.AddressbookRESTService.IOService.REST_IO;;

public class AddressbookRESTServiceTest {

	static AddressbookRESTService addressbookRESTService;

	@BeforeClass
	public static void createObj() {
		addressbookRESTService = new AddressbookRESTService();
	}

	@AfterClass
	public static void nullObj() {
		addressbookRESTService= null;
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
	@Test
	public void givenAddressbookDataInJsonServer_WhenRetrieved_ShouldMatchPersonCount() {
		AddressbookData[] arrayOfPerson = getAddressbookList();
		addressbookRESTService = new AddressbookRESTService(Arrays.asList(arrayOfPerson));
		long entries = addressbookRESTService.countEntries(REST_IO);
		assertEquals(2, entries);
	}



}
