/**
 * 
 */
package project.utils;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import project.Services.UserService;
import project.model.Address;
import project.model.User;

/**
 * @author Group3
 *
 */
@Service
public class LoadUserData {
	@Autowired
	UserService users;
	public void loadUsers(String pathFile) throws ParserConfigurationException, SAXException, IOException {

		Document documentUsers = FileUtils.readFromXmlFile(pathFile);

		
		

		NodeList nListUtilizadores = (NodeList) documentUsers.getElementsByTagName("utilizador");

		for (int i = 0; i < nListUtilizadores.getLength(); i++) {
			Node nNodeUtilizador = nListUtilizadores.item(i);

			if (nNodeUtilizador.getNodeType() == Node.ELEMENT_NODE) {
				Element eElementUtilizador = (Element) nNodeUtilizador;
				User eachUser = new User();
				eachUser.setName(eElementUtilizador.getElementsByTagName("nome_utilizador").item(0).getTextContent());
				eachUser.setEmail(eElementUtilizador.getElementsByTagName("email_utilizador").item(0).getTextContent());
				eachUser.setPassword(eElementUtilizador.getElementsByTagName("password").item(0).getTextContent());

				NodeList nList = (NodeList) eElementUtilizador.getElementsByTagName("lista_enderecos").item(0);

				for (int ii = 0; ii < nList.getLength(); ii++) {
					Node nNode = nList.item(ii);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						Address eachUserAddress = new Address();

						eachUserAddress.setStreet(eElement.getElementsByTagName("rua").item(0).getTextContent());
						eachUserAddress
								.setDistrict(eElement.getElementsByTagName("localidade").item(0).getTextContent());
						eachUserAddress.setZipCode(eElement.getElementsByTagName("cp_num").item(0).getTextContent());
						eachUserAddress.setCountry(eElement.getElementsByTagName("pais").item(0).getTextContent());

						eachUser.addAddress(eachUserAddress);
					}
					
				}
				users.addUserToUserRepositoryX(eachUser);
			}

		}

	}

}
