import com.google.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Main {


    public static List<Employee> parseXML(String fileName) throws
            ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        Node staff = doc.getDocumentElement();
        List<Employee> employees = new ArrayList<>();
        NodeList nodeList = staff.getChildNodes();
        String id = null;
        String firstName = null;
        String lastName = null;
        String country = null;
        String age = null;

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NodeList nodeList1 = node.getChildNodes();
            for (int j = 0; j < nodeList1.getLength(); j++) {
                Node node_ = nodeList1.item(j);
                Element element = (Element) node_;
                if (element.getNodeName().equals("id")) {
                    id = element.getTextContent();
                } else if (element.getNodeName().equals("firstName")) {
                    firstName = element.getTextContent();
                } else if (element.getNodeName().equals("lastName")) {
                    lastName = element.getTextContent();
                } else if (element.getNodeName().equals("country")) {
                    country = element.getTextContent();
                } else if (element.getNodeName().equals("age")) {
                    age = element.getTextContent();
                }
            }
            employees.add(new Employee(id, firstName, lastName, country,age));
        }
        return employees;
    }

    public static String listToJson(List<Employee> list) {
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(list, listType);
        return json;
    }

    public static void writeString(String fileNameJson, String json) {
        try(FileWriter file = new FileWriter(fileNameJson)) {
            file.write(json);
            file.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element staff = doc.createElement("staff");
        doc.appendChild(staff);
        Element employee = doc.createElement("employee");
        staff.appendChild(employee);
        Element id = doc.createElement("id");
        id.appendChild(doc.createTextNode("1"));
        employee.appendChild(id);
        Element firstName = doc.createElement("firstName");
        firstName.appendChild(doc.createTextNode("John"));
        employee.appendChild(firstName);
        Element lastName = doc.createElement("lastName");
        lastName.appendChild(doc.createTextNode("Smith"));
        employee.appendChild(lastName);
        Element country = doc.createElement("country");
        country.appendChild(doc.createTextNode("USA"));
        employee.appendChild(country);
        Element age = doc.createElement("age");
        age.appendChild(doc.createTextNode("25"));
        employee.appendChild(age);
        Element employee2 = doc.createElement("employee");
        staff.appendChild(employee2);
        Element id2 = doc.createElement("id");
        id2.appendChild(doc.createTextNode("2"));
        employee2.appendChild(id2);
        Element firstName2 = doc.createElement("firstName");
        firstName2.appendChild(doc.createTextNode("Ivan"));
        employee2.appendChild(firstName2);
        Element lastName2 = doc.createElement("lastName");
        lastName2.appendChild(doc.createTextNode("Petrov"));
        employee2.appendChild(lastName2);
        Element country2 = doc.createElement("country");
        country2.appendChild(doc.createTextNode("RU"));
        employee2.appendChild(country2);
        Element age2 = doc.createElement("age");
        age2.appendChild(doc.createTextNode("23"));
        employee2.appendChild(age2);

        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(new File("data.xml"));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(domSource, streamResult);



        List<Employee> list = parseXML("data.xml");
        String json = listToJson(list);
        String fileName = "data2.json";
        writeString(fileName, json);
        list.forEach(System.out::println);










    }

}
