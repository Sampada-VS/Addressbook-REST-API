package com.blz.addressbookrestapi;

import java.util.ArrayList;
import java.util.List;

public class AddressbookRESTService {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}
	private List<AddressbookData> addressbookList;

	public AddressbookRESTService(List<AddressbookData> addressbookList) {
		this.addressbookList = new ArrayList<>(addressbookList);
	}

	public AddressbookRESTService() {
	}

	public long countEntries(IOService restIo) {
		return addressbookList.size();
	}

	public void addContactToAddressbook(AddressbookData addressbookData, IOService restIo) {
		addressbookList.add(addressbookData);		
	}
}
