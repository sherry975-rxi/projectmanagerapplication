package project.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.EffortUnit;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;

@Service
public class LoadProjectData {

	@Autowired
	ProjectService projectService;

	@Autowired
	UserService userService;

	@Autowired
	TaskService taskService;

	public void loadProject(String pathFile)
			throws ParserConfigurationException, SAXException, IOException, DOMException, ParseException {

		Document documentProjects = FileUtils.readFromXmlFile(pathFile);
		NodeList nProjectList = documentProjects.getElementsByTagName("projeto");

		for (int indexProject = 0; indexProject < nProjectList.getLength(); indexProject++) {
			Node nNodeProject = nProjectList.item(indexProject);

			if (nNodeProject.getNodeType() == Node.ELEMENT_NODE) {
				Element eElementProject = (Element) nNodeProject;

				Project project = new Project();

				project.setName(eElementProject.getElementsByTagName("nome_projeto").item(0).getTextContent());
				project.setDescription(
						eElementProject.getElementsByTagName("descricao_projeto").item(0).getTextContent());
				eElementProject.getElementsByTagName("data_inicio_projeto").item(0).getTextContent();

				// Sets the start date and finish date. Uses the method convertStringToCalendar
				project.setStartdate(convertStringToCalendar(
						eElementProject.getElementsByTagName("data_inicio_projeto").item(0).getTextContent()));
				project.setFinishdate(convertStringToCalendar(
						eElementProject.getElementsByTagName("data_conclusao_projeto").item(0).getTextContent()));

				// Converts string to efforUnit
				EffortUnit effortUnit = EffortUnit
						.valueOf(eElementProject.getElementsByTagName("unidade_esforco").item(0).getTextContent());
				project.setEffortUnit(effortUnit);

				// Converts string to integer
				Integer budget = Integer
						.valueOf(eElementProject.getElementsByTagName("orcamento_projeto").item(0).getTextContent());
				project.setBudget(budget);

				// project.setProjectStatus(eElementProject.getElementsByTagName("estado_projeto").item(0).getTextContent())

				User user = userService.getUserByEmail(
						eElementProject.getElementsByTagName("gestor_projeto").item(0).getTextContent());
				project.setProjectManager(user);

				projectService.updateProject(project);

				NodeList nProjectCollaboratorList = documentProjects.getElementsByTagName("colaborador_projeto");

				for (int indexProjCollab = 0; indexProjCollab < nProjectList.getLength(); indexProjCollab++) {

					Node nNodeProjectCollaborator = nProjectCollaboratorList.item(indexProjCollab);

					if (nNodeProjectCollaborator.getNodeType() == Node.ELEMENT_NODE) {
						Element eElementProjectCollaborator = (Element) nNodeProjectCollaborator;

						ProjectCollaborator projCollaborator = new ProjectCollaborator();
						User userCollaborator = userService.getUserByEmail(eElementProjectCollaborator
								.getElementsByTagName("colaborador_id").item(0).getTextContent());
						projCollaborator.setCollaborator(userCollaborator);
						projectService.addProjectCollaborator(projCollaborator);

						NodeList nLigProjectList = documentProjects.getElementsByTagName("colaborador_projeto");

						for (int indexLigProject = 0; indexLigProject < nLigProjectList
								.getLength(); indexLigProject++) {

							Node nNodeLigProject = nProjectCollaboratorList.item(indexLigProject);

							if (nNodeLigProject.getNodeType() == Node.ELEMENT_NODE) {
								Element eElementLigProject = (Element) nNodeLigProject;

								Integer costEffort = Integer.valueOf(eElementLigProject
										.getElementsByTagName("custo_unitario_colaborador").item(0).getTextContent());

								projCollaborator.setCostPerEffort(costEffort);
								projectService.updateProjectCollaborator(projCollaborator);
							}
						}
					}
				}

				NodeList nTaskList = documentProjects.getElementsByTagName("tarefa");

				for (int indexTask = 0; indexTask < nTaskList.getLength(); indexTask++) {
					Node nNodeTask = nProjectList.item(indexTask);

					if (nNodeTask.getNodeType() == Node.ELEMENT_NODE) {
						Element eElementTask = (Element) nNodeTask;

						String description = eElementTask.getElementsByTagName("descricao_tarefa").item(0)
								.getTextContent();

						Task task = project.createTask(description);

						task.setTaskID(eElementTask.getElementsByTagName("codigo_tarefa").item(0).getTextContent());

						Integer taskEffort = Integer.valueOf(
								eElementTask.getElementsByTagName("esforco_estimado").item(0).getTextContent());
						task.setEstimatedTaskEffort(taskEffort);

						Integer taskBudget = Integer.valueOf(eElementTask
								.getElementsByTagName("custo_unitario_orcamentado").item(0).getTextContent());
						task.setTaskBudget(taskBudget);

						Calendar estimatedStartDate = convertStringToCalendar(
								eElementTask.getElementsByTagName("data_inicio_prevista").item(0).getTextContent());
						task.setEstimatedTaskStartDate(estimatedStartDate);

						Calendar estimatedFinishDate = convertStringToCalendar(
								eElementTask.getElementsByTagName("data_conclusao_prevista").item(0).getTextContent());

						task.setTaskDeadline((estimatedFinishDate));

						Calendar finishDate = convertStringToCalendar(
								eElementTask.getElementsByTagName("data_conclusao_efetiva").item(0).getTextContent());

						task.setFinishDate(finishDate);

						taskService.saveTask(task);

						for (int indexTaskDependencies = 0; indexTaskDependencies < nTaskList
								.getLength(); indexTaskDependencies++) {
							Node nNodeTaskDependencies = nProjectList.item(indexTaskDependencies);

							if (nNodeTaskDependencies.getNodeType() == Node.ELEMENT_NODE) {
								Element eElementTaskDependencies = (Element) nNodeTaskDependencies;

								String idTaskMain = eElementTaskDependencies.getElementsByTagName("tarefa_id").item(0)
										.getTextContent();
								Task taskMain = taskService.getTaskByTaskID(idTaskMain);

								taskService.saveTask(task);

							}
						}
					}

				}

			}
		}
	}

	/**
	 * Converts a string to a Calendar
	 *
	 * @param calendar
	 *            String in a pre-set format
	 *
	 * @return Calendar converted from String
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
