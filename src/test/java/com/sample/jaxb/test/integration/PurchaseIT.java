package com.sample.jaxb.test.integration;

import static org.junit.Assert.fail;

import java.io.InputStream;

import org.junit.Test;

import com.sample.jaxb.JAXBUtils;
import com.sample.jaxb.Main;
import com.sample.jaxb.domain.purchase.Purchase;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PurchaseIT {
	@Test
	public void test() {
		try {
			JAXBUtils jaxbUtils = JAXBUtils.getInstance("com.sample.jaxb.domain.purchase");
			InputStream is = Main.class.getClassLoader().getResourceAsStream("schemas/purchase/purchase.xml");
			Purchase purchase = (Purchase) jaxbUtils.unmarshall(is);
			String xml = jaxbUtils.marshallToStr(purchase);
			log.info(xml);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
}
