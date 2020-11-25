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

	public AddressbookData getAddressbookData(String firstName) {
		AddressbookData addressbookData;
		addressbookData = this.addressbookList.stream().filter(dataItem -> dataItem.firstName.equals(firstName))
				.findFirst().orElse(null);
		return addressbookData;
	}

	public void updatePersonContactNo(String firstName, String phone, IOService restIo) {
		AddressbookData addressbookData = this.getAddressbookData(firstName);
		if (addressbookData != null)
			addressbookData.phone = phone;
	}

	public void deleteAddressbookEntry(String firstName, IOService restIo) {
		AddressbookData addressbookData = this.getAddressbookData(firstName);
		addressbookList.remove(addressbookData);
	}
}
