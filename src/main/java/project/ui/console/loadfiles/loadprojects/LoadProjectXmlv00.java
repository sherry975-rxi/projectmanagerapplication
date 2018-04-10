package project.ui.console.loadfiles.loadprojects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import project.model.*;
import project.model.taskstateinterface.Finished;
import project.model.taskstateinterface.OnGoing;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;
import project.ui.console.loadfiles.FileUtils;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class LoadProjectXmlv00 implements LoadProjectXml{

	ProjectService projectService;


	UserService userService;


	TaskService taskService;

	@Autowired
	public LoadProjectXmlv00(ProjectService projectService, UserService userService, TaskService taskService) {
		this.projectService = projectService;
		this.userService = userService;
		this.taskService = taskService;
	}

	TaskCollaborator taskCollab;

	public void readProjectFile(String pathFile)
			throws ParserConfigurationException, SAXException, IOException, DOMException, ParseException {

		Document documentProjects = FileUtils.readFromXmlFile(pathFile);

		// Node projectList
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
				Double budget = Double
						.valueOf(eElementProject.getElementsByTagName("orcamento_projeto").item(0).getTextContent());
				project.setProjectBudget(budget);

				String state = eElementProject.getElementsByTagName("estado_projeto").item(0).getTextContent();

				project.setProjectStatus(6);

					if("Ativo".equals(state)) {

						project.setProjectStatus(2);
					}

				User user = userService.getUserByEmail(
						eElementProject.getElementsByTagName("gestor_projeto").item(0).getTextContent());
				project.setProjectManager(user);

				projectService.updateProject(project);

				// Node lista_colaboradores
				NodeList nProjectCollaboratorList = eElementProject.getElementsByTagName("colaborador_projeto");

				for (int indexProjCollab = 0; indexProjCollab < nProjectCollaboratorList
						.getLength(); indexProjCollab++) {

					Node nNodeProjectCollaborator = nProjectCollaboratorList.item(indexProjCollab);

					if (nNodeProjectCollaborator.getNodeType() == Node.ELEMENT_NODE) {
						Element eElementProjectCollaborator = (Element) nNodeProjectCollaborator;

                        ProjectCollaborator projCollaborator;

						User userCollaborator = userService.getUserByEmail(eElementProjectCollaborator
								.getElementsByTagName("colaborador_id").item(0).getTextContent());

						NodeList nLigProjectList = eElementProjectCollaborator.getElementsByTagName("ligacao_projeto");


						for (int indexLigProject = 0; indexLigProject < nLigProjectList
								.getLength(); indexLigProject++) {

							Node nNodeLigProject = nLigProjectList.item(indexLigProject);

							if (nNodeLigProject.getNodeType() == Node.ELEMENT_NODE) {
								Element eElementLigProject = (Element) nNodeLigProject;

								Calendar startDate = convertStringToCalendar(
										eElementProject.getElementsByTagName("data_inicio").item(0).getTextContent());

								Calendar finishDate = convertStringToCalendar(
										eElementProject.getElementsByTagName("data_fim").item(0).getTextContent());

								boolean isProjCollabActive = finishDate!=null;

								Double costEffort = Double.valueOf(eElementLigProject
										.getElementsByTagName("custo_unitario_colaborador").item(0).getTextContent());

                                projCollaborator=projectService.createProjectCollaborator(userCollaborator, project, costEffort);
                                projCollaborator.setStartDate(startDate);
                                projCollaborator.setFinishDate(finishDate);
								projCollaborator.setStatus(isProjCollabActive);
								projectService.updateProjectCollaborator(projCollaborator);
							}
						}
					}
				}

				// Node Lista de Tarefas
				NodeList nTaskList = eElementProject.getElementsByTagName("tarefa");

				for (int indexTask = 0; indexTask < nTaskList.getLength(); indexTask++) {
					Node nNodeTask = nTaskList.item(indexTask);

					if (nNodeTask.getNodeType() == Node.ELEMENT_NODE) {
						Element eElementTask = (Element) nNodeTask;

						String description = eElementTask.getElementsByTagName("nome_tarefa").item(0).getTextContent();

						Task task = project.createTask(description);

						task.setTaskID(eElementTask.getElementsByTagName("codigo_tarefa").item(0).getTextContent());

						Double taskEffort = Double.valueOf(
								eElementTask.getElementsByTagName("esforco_estimado").item(0).getTextContent());
						task.setEstimatedTaskEffort(taskEffort);

						Double taskBudget = Double.valueOf(eElementTask
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

                        if(Calendar.getInstance().after(estimatedStartDate)) {
                            task.setStartDate(estimatedStartDate);
                            task.setTaskState(new OnGoing());
                            task.setCurrentState(StateEnum.ONGOING);
                        }

						taskService.saveTask(task);

						///////////////////////////////////
						NodeList nTaskDependenceList = eElementTask.getElementsByTagName("lista_dependencias");

						for (int indexTaskDependencies = 0; indexTaskDependencies < nTaskDependenceList
								.getLength(); indexTaskDependencies++) {

							Node nNodeTaskDependencies = nTaskDependenceList.item(indexTaskDependencies);

							if (nNodeTaskDependencies.getNodeType() == Node.ELEMENT_NODE) {
								Element eElementTaskDependencies = (Element) nNodeTaskDependencies;

								String idTaskMain = eElementTaskDependencies.getElementsByTagName("tarefa_id").item(0)
										.getTextContent();
								if (idTaskMain != "") {
									Task taskMain = taskService.getTaskByTaskID(idTaskMain);
									task.getTaskDependency().add(taskMain);
								}
							}
						}
						
						//TaskCollaborator creation

						NodeList nTaskCollaborators = eElementTask.getElementsByTagName("colaborador_tarefa");

						for (int indexTaskCollaborators = 0; indexTaskCollaborators < nTaskCollaborators
								.getLength(); indexTaskCollaborators++) {

							Node nNodeTaskCollaborator = nTaskCollaborators.item(indexTaskCollaborators);

							if (nNodeTaskCollaborator.getNodeType() == Node.ELEMENT_NODE) {
								Element eElementnNodeTaskCollaborator = (Element) nNodeTaskCollaborator;

								User userOfProjectCollab = userService.getUserByEmail(eElementnNodeTaskCollaborator
										.getElementsByTagName("colaborador_id").item(0).getTextContent());

								Project project1 = projectService.getProjectById(task.getProject().getId());
								

								ProjectCollaborator projCollaborator = projectService
										.findProjectCollaborator(userOfProjectCollab, project1).orElse(null); 

								task.addProjectCollaboratorToTask(projCollaborator);

								//Dados do Task Collaborator

								String startDateString = eElementnNodeTaskCollaborator
										.getElementsByTagName("data_inicio").item(0).getTextContent();

								Calendar startDateTaskCollaborator = convertStringToCalendar(startDateString);

								TaskCollaborator taskCollaborator = task
										.getTaskCollaboratorByEmail(eElementnNodeTaskCollaborator
												.getElementsByTagName("colaborador_id").item(0).getTextContent());

								taskCollaborator.setStartDate(startDateTaskCollaborator);

								String finishDateString = eElementnNodeTaskCollaborator.getElementsByTagName("data_fim")
										.item(0).getTextContent();

								Calendar finishDateTaskCollaborator = convertStringToCalendar(finishDateString);

								taskCollaborator.setFinishDate(finishDateTaskCollaborator);

								taskCollaborator.setStatus(finishDateTaskCollaborator==null);


								NodeList nReportList = eElementnNodeTaskCollaborator.getElementsByTagName("report");

								for (int indexReport = 0; indexReport < nReportList.getLength(); indexReport++) {

									Node nNodeReport = nReportList.item(indexReport);

									if (nNodeReport.getNodeType() == Node.ELEMENT_NODE) {

										Element eElementNodeReport = (Element) nNodeReport;
										Calendar reportStartDate = convertStringToCalendar(eElementNodeReport
												.getElementsByTagName("data_inicio").item(0).getTextContent());

                                        Calendar reportLastDate = convertStringToCalendar(eElementNodeReport
                                                .getElementsByTagName("data_fim").item(0).getTextContent());

                                        if(reportLastDate==null) {
                                            reportLastDate=Calendar.getInstance();
                                        }

                                        Double timeToReport = Double.valueOf(eElementNodeReport
												.getElementsByTagName("esforco").item(0).getTextContent());

                                        Report report = new Report();
                                        report.setTaskCollaborator(taskCollab);
                                        report.setTask(task);
                                        report.setCost(taskCollaborator.getCost());
                                        report.setFirstDateOfReport(reportStartDate);
                                        report.setDateOfUpdate(reportLastDate);
                                        report.setCost(timeToReport);

                                        task.getReports().add(report);

										//task.createReport(taskCollaborator, reportStartDate, timeToReport);
									}
								}
							}
						}

                        if(finishDate!=null) {
                            task.setStartDate(estimatedStartDate);
                            task.setFinishDate(finishDate);
                            task.removeAllCollaboratorsFromTaskTeam();
                            task.setTaskState(new Finished());
                            task.setCurrentState(StateEnum.FINISHED);
                        }
						taskService.saveTask(task);
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
	private static Calendar convertStringToCalendar(String calendar) throws ParseException {

		if (calendar != "") {
			Calendar date = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			date.setTime(sdf.parse(calendar));
			date.set(Calendar.HOUR_OF_DAY, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);
			date.set(Calendar.MILLISECOND, 0);
			return date;
		}

		return null;
	}
}