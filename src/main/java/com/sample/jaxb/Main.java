package com.sample.jaxb;

import java.io.InputStream;

import com.sample.jaxb.domain.booking.BookingCaseRQ;
import com.sample.jaxb.domain.purchase.Purchase;

public class Main {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		JAXBUtils jaxbUtils = JAXBUtils.getInstance("com.sample.jaxb.domain.sample");

		try {
			InputStream is = Main.class.getClassLoader().getResourceAsStream("sample/purchase.xml");
			Purchase purchase = (Purchase)jaxbUtils.unmarshall(is);
			String xml = jaxbUtils.marshallToStr(purchase);
			System.out.println(xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
