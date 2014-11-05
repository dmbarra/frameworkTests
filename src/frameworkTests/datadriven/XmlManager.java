package frameworkTests.datadriven;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import frameworkTests.utils.ConfigTestManager;

public class XmlManager {
	
	private final static String id="id";

	/**
	 * Method responsible for writing to the amendment made to date driver
	 * element.
	 * @author lucas.dornelas
	 * @param document  {@link Document}
	 * @param nameDataDriver & Must contain the name of the XML file in which you want to access.
	 */
	private static void recordingChangedElement(Document document, String nameDataDriver) {
		XMLOutputter xmlOutputter = new XMLOutputter();
		try {
			FileWriter archive = new FileWriter(new File(ConfigTestManager.getDirectoryDefaultDataDriver() + nameDataDriver + ConfigTestManager.getExtensionDefaultDateDriver()));
			xmlOutputter.output(document, archive);
		} catch (Exception e) {
			System.err.println("Can't to load xml file: " + nameDataDriver);

		}
	}

	/**
	 * Method responsible for creating an element according to the header of the
	 * XML file.
	 * @author lucas.dornelas
	 * @param document {@link Document}
	 * @return {@link Element}
	 */
	private static Element mountStandardElement(Document document) {
		Integer indexElement = document.getRootElement().getChildren().size();
		List<Attribute> attributes = document.getRootElement().getAttributes();
		Element element = new Element("element");
		for (Attribute attribute : attributes) {
			if (attribute.getName().equals(id)) {
				element.setAttribute(attribute.getName(), indexElement.toString());
			} else {
				element.setAttribute(attribute.getName(), "");
			}
		}
		return element;
	}

	/**
	 * Method responsible for, method for recording the element in the file.
	 * @author lucas.dornelas
	 * @param element {@link Element}
	 * @param document {@link Document} file.
	 * @param nameDataDriver & Must contain the name of the XML file in which you want to access.
	 * @param indexElement & Must contain the index of the element you want to record.
	 */
	private static void recordingElement(Element element, Document document, String nameDataDriver, Integer indexElement) {
		document.getRootElement().addContent(indexElement, element);
		XMLOutputter xmlOutputter = new XMLOutputter();
		try {
			FileWriter arquivo = new FileWriter(new File(ConfigTestManager.getDirectoryDefaultDataDriver() + nameDataDriver + ConfigTestManager.getExtensionDefaultDateDriver()));
			xmlOutputter.output(document, arquivo);
		} catch (Exception e) {
			System.err.println("Can't to load xml file: " + nameDataDriver);
		}
	}

	/**
	 * Method responsible for, by inserting the element in the file.
	 * @author lucas.dornelas
	 * @param nameDataDriver & Must contain the name of the XML file in which you want to  access.
	 * @param nameAttribute & Must contain the name of the attribute you want to change
	 * @param valueElement & Must contain the attribute value you want to insert.
	 */
	public static void setAttribute(String nameDataDriver, String nameAttribute, String valueElement) {
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = null;
		Integer indexElement = 0;
		try {
			document = saxBuilder.build(ConfigTestManager.getDirectoryDefaultDataDriver() + nameDataDriver + ConfigTestManager.getExtensionDefaultDateDriver());
			Element element = mountStandardElement(document);
			element.setAttribute(new Attribute(nameAttribute, valueElement));
			indexElement = document.getRootElement().getChildren().size() + 1;
			recordingElement(element, document, nameDataDriver, indexElement);
		} catch (Exception e) {
			System.err.println("Can't to load xml file: " + nameDataDriver);
		}
	}

	/**
	 * Method responsible for altering the value of the element according co the index.
	 * @author lucas.dornelas
	 * @param nameDataDriver & Must contain the name of the XML file in which you want to access.
	 * @param nameAttribute & Must contain the name of the attribute you want to change
	 * @param valueElement & Must contain the attribute value you want to insert.
	 * @param indexElement & Must contain the index of the element you want to record.
	 */
	public static void changeElementByindex(String nameDataDriver, String nameAttribute, String valueElement, Integer indexElement) {

		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = null;

		try {
			document = saxBuilder.build(ConfigTestManager.getDirectoryDefaultDataDriver() + nameDataDriver + ConfigTestManager.getExtensionDefaultDateDriver());
			for (Element e : document.getRootElement().getChildren()) {

				if (e.getAttributeValue(id).equals(indexElement.toString())) {
					e.setAttribute(nameAttribute, valueElement);
				}
			}
		} catch (Exception e) {
			System.err.println("Can't to load xml file: " + nameDataDriver);

		}

		recordingChangedElement(document, nameDataDriver);
	}

	/**
	 * Method responsible for altering the value of the element.
	 * 
	 * @author lucas.dornelas
	 * @param nameDataDriver
	 *            & Must contain the name of the XML file in which you want to
	 *            access.
	 * @param nameAttribute
	 *            & Must contain the name of the attribute you want to change
	 * @param valueElement
	 *            & Must contain the attribute value you want to insert.
	 */
	public static void changeElement(String nameDataDriver, String nameAttribute, String valueElement) {

		Integer indexElement = 0;
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = null;

		try {
			document = saxBuilder.build(ConfigTestManager.getDirectoryDefaultDataDriver() + nameDataDriver + ConfigTestManager.getExtensionDefaultDateDriver());
			indexElement = document.getRootElement().getChildren().size() - 1;
			document.getRootElement().getChildren().get(indexElement).setAttribute(nameAttribute, valueElement);

		} catch (Exception e) {
			System.err.println("Can't to load xml file: " + nameDataDriver);

		}

		recordingChangedElement(document, nameDataDriver);
	}
	/**
	 * Method responsible for returning the element value according co the index.
	 * @author lucas.dornelas
	 * @param nameDataDriver & Must contain the name of the XML file in which you want to access.
	 * @param nameAttribute & Must contain the name of the attribute you want to change
	 * @param indexElement & Must contain the index of the element you want to record.
	 * @return
	 */
	public static String getAtributeByIndex(String nameDataDriver, String nameAttribute, Integer indexElement) {
		String valueAtribute = null;
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = null;
		try {
			document = saxBuilder.build(ConfigTestManager.getDirectoryDefaultDataDriver() + nameDataDriver + ConfigTestManager.getExtensionDefaultDateDriver());
			for (Element element : document.getRootElement().getChildren()) {
				if (element.getAttributeValue(id).equals(indexElement.toString())) {
					valueAtribute = element.getAttributeValue(nameAttribute);
				}
			}
		} catch (Exception e) {
			System.err.println("Can't to load xml file: " + nameDataDriver);
		}
		return valueAtribute;
	}

	/**
	 * Method responsible for returning the element value contained at the end of the file.
	 * @author lucas.dornelas
	 * @param nameDataDriver & Must contain the name of the XML file in which you want to  access.
	 * @param nameAttribute & Must contain the name of the attribute you want to change
	 * @return
	 */
	public static String getAtributeByName(String nameDataDriver, String nameAttribute) {
		Integer indexElement = 0;
		String valueAtribute = null;
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = null;
		try {
			document = saxBuilder.build(ConfigTestManager.getDirectoryDefaultDataDriver() + nameDataDriver + ConfigTestManager.getExtensionDefaultDateDriver());
			indexElement = document.getRootElement().getChildren().size() - 1;
			valueAtribute = document.getRootElement().getChildren().get(indexElement).getAttributeValue(nameAttribute);
		} catch (Exception e) {
			System.err.println("Can't to load xml file: " + nameDataDriver);
		}
		return valueAtribute;

	}
}
