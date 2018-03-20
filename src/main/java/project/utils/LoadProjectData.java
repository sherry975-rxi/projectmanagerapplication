package project.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import project.Services.ProjectService;
import project.Services.UserService;
import project.model.EffortUnit;
import project.model.Project;
import project.model.User;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class LoadProjectData {

    @Autowired
    ProjectService projectService;
    
    @Autowired 
    UserService userService; 

    public void loadProject(String pathFile) throws ParserConfigurationException, SAXException, IOException, DOMException, ParseException {

        Document documentProjects = FileUtils.readFromXmlFile(pathFile);
        NodeList nProjectList = (NodeList) documentProjects.getElementsByTagName("projeto");


        for (int indexProject = 0; indexProject < nProjectList.getLength(); indexProject++) {
            Node nNodeProject =  nProjectList.item(indexProject);


            if (nNodeProject.getNodeType() == Node.ELEMENT_NODE) {
                Element eElementProject = (Element) nNodeProject;


                Project project = new Project();

                project.setName(eElementProject.getElementsByTagName("nome_projeto").item(0).getTextContent());
                project.setDescription(eElementProject.getElementsByTagName("descricao_projeto").item(0).getTextContent());
                eElementProject.getElementsByTagName("data_inicio_projeto").item(0).getTextContent();
                
                //Sets the start date and finish date. Uses the method convertStringToCalendar
                project.setStartdate(convertStringToCalendar(eElementProject.getElementsByTagName("data_inicio_projeto").item(0).getTextContent()));
                project.setFinishdate(convertStringToCalendar(eElementProject.getElementsByTagName("data_conclusao_projeto").item(0).getTextContent()));
                
                //Converts string to efforUnit
                EffortUnit effortUnit =  EffortUnit.valueOf(eElementProject.getElementsByTagName("unidade_esforco").item(0).getTextContent());
                project.setEffortUnit(effortUnit);
                
                //Converts string to integer
                Integer budget = Integer.valueOf(eElementProject.getElementsByTagName("orcamento_projeto").item(0).getTextContent());           
                project.setBudget(budget);
                      
                //project.setProjectStatus(eElementProject.getElementsByTagName("estado_projeto").item(0).getTextContent());
                
                User user = userService.getUserByEmail(eElementProject.getElementsByTagName("gestor_projeto").item(0).getTextContent()); 
                project.setProjectManager(user);

                projectService.updateProject(project);
                
            }
        }
    }

    /**
     * Converts a string to a Calendar
     *
     * @param calendar String in a pre-set format
     *
     * @return Calendar converted from a string
     *
     * @throws ParseException
     */
    private Calendar convertStringToCalendar(String calendar) throws ParseException {

        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        date.setTime(sdf.parse(calendar));
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        return date;
    }
}
