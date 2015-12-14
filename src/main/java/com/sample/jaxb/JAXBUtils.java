package com.sample.jaxb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class JAXBUtils {

	private static JAXBUtils instance;
	public static JAXBContext jaxbContext;

	/**
	 * JAXBUtils Private constructor prevents instantiation from other classes
	 * 
	 * <br>
	 * 
	 * This will invoke a JAXBContext new instance passing as parameter the pkgs
	 * parameter.
	 * 
	 * A client application normally obtains new instances of this class using
	 * one of these two styles for newInstance methods, although there are other
	 * specialized forms of the method available:
	 * 
	 * JAXBContext.newInstance( "com.acme.foo:com.acme.bar" ) The JAXBContext
	 * instance is initialized from a list of colon separated Java package
	 * names. Each java package contains JAXB mapped classes, schema-derived
	 * classes and/or user annotated classes. Additionally, the java package may
	 * contain JAXB package annotations that must be processed. (see JLS 3rd
	 * Edition, Section 7.4.1. Package Annotations).
	 * 
	 * JAXBContext.newInstance( com.acme.foo.Foo.class ) The JAXBContext
	 * instance is intialized with class(es) passed as parameter(s) and classes
	 * that are statically reachable from these class(es). See
	 * newInstance(Class...) for details.
	 * 
	 * 
	 * @param pkgs
	 */
	private JAXBUtils(String pkgs) {
		try {
			jaxbContext = JAXBContext.newInstance(pkgs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Get a JAXBUtils instance using SingleTon pattern. This will invoke the
	 * JAXBUtils constructor just for the first time and only if instance
	 * attribute is null.
	 *
	 * @param pkgs
	 * @return
	 */
	public static synchronized JAXBUtils getInstance(String pkgs) {
		if (instance == null) {
			instance = new JAXBUtils(pkgs);
		}

		return instance;
	}

	/**
	 * 
	 */
	public static void setInstanceNull() {
		if (instance != null) {
			instance = null;
		}
	}

	public void marshall(Object obj, OutputStream outStream) throws JAXBException {
		Marshaller m = jaxbContext.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(obj, outStream);
	}

	public Node marshall(Object obj) throws JAXBException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc = null;
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.newDocument();
		} catch (ParserConfigurationException ex) {
			throw new JAXBException(ex);
		}

		Marshaller m = jaxbContext.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(obj, doc);
		return doc.getDocumentElement();
	}

	public void marshallToFile(Object obj, String path, String fileName, String schemaLocation) throws JAXBException, FileNotFoundException {
		try {
			Writer writer = new FileWriter(path + File.separator + fileName);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);
			marshaller.marshal(obj, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void marshallToFile(Object obj, String path, String fileName) throws JAXBException, FileNotFoundException {
		try {
			Writer writer = new FileWriter(path + File.separator + fileName);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(obj, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public byte[] marshallToByteArr(Object obj) throws JAXBException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		marshall(obj, baos);
		return baos.toByteArray();
	}

	public String marshallToStr(Object obj) throws JAXBException {
		return new String(marshallToByteArr(obj));
	}

	public Unmarshaller createUnmarshaller() throws JAXBException {
		return jaxbContext.createUnmarshaller();
	}

	public Object unmarshallFromXmlPath(String xmlPath) throws JAXBException, IOException {

		try {
			InputStream in = this.getClass().getResourceAsStream(xmlPath);
			return unmarshall(in);
		} catch (Exception e) {
			File f = new File(xmlPath);
			return unmarshall(f);
		}
	}

	public Object unmarshall(File file) throws JAXBException {
		Unmarshaller um = jaxbContext.createUnmarshaller();
		return um.unmarshal(file);
	}

	public Object unmarshall(URL url) throws JAXBException {
		Unmarshaller um = jaxbContext.createUnmarshaller();
		return um.unmarshal(url);
	}

	public Object unmarshall(String xml) throws JAXBException {
		ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
		Unmarshaller um = jaxbContext.createUnmarshaller();
		return um.unmarshal(bais);
	}

	public Object unmarshall(InputStream xml) throws JAXBException {
		Unmarshaller um = jaxbContext.createUnmarshaller();
		return um.unmarshal(xml);
	}

	public Object unmarshall(Node node) throws JAXBException {
		Unmarshaller um = jaxbContext.createUnmarshaller();
		return um.unmarshal(node);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getExtendedObjects(List<JAXBElement> objEls) {

		List list = new ArrayList();

		for (JAXBElement objEl : objEls) {
			list.add(objEl.getValue());
		}

		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getObjects(List<JAXBElement> objEls) {
		List list = new ArrayList();

		for (JAXBElement objEl : objEls) {
			list.add(objEl.getValue());
		}

		return list;
	}

}
