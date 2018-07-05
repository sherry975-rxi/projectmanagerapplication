import { isNullOrUndefined } from 'util';
var moment = require('moment');



export function handleTaskHeaders(list) {

    return list.map(task => ({
        taskID: task.taskID,
        project: task.project.projectId,
        currentProject: task.project,
        description: task.description,
        state: task.currentState,
        startDate: formatDate(task.startDate),
        finishDate: formatDate(task.finishDate),
        creationDate: formatDate(task.creationDate),
        estimatedTaskStartDate: formatDate(task.estimatedTaskStartDate),
        taskDeadline: formatDate(task.taskDeadline),
        cancelDate: formatDate(task.cancelDate),
        estimatedTaskEffort: task.estimatedTaskEffort,
        taskBudget: task.taskBudget,
        taskTeam: task.taskTeam
    }));
}

export function handleProject(projects) {
    return projects.map(project => ({
        projectId: project.projectId,
        projectActive: project.projectActive,
        projectName: project.name,
        projectStatusName: project.projectStatusName,
        projectDescription: project.description,
        projectManagerName: project.projectManager.name,
        projectStartDate: formatDate(project.startdate),
        projectFinishDate: formatDate(project.finishdate),
        projectBudget: project.budget,
        projectCalculationMethod: project.calculationMethod,
        projectAvaliableCalculationMethods: project.availableCalculationMethods,
        projectCost: project.projectCost,
        projectManagerEmail: project.projectManager.email,
        button: ''
    }));
}

export function handleUserHeaders(list) {
    return list.map(user => [
        user.name,
        user.email,
        user.userProfile,
        user.function,
        user.systemUserStateActive,
        ''
    ]);
}

export function formatDate(date) {

    if (date != null) {

        return moment(date).format("YYYY-MM-DD");
    } else {
        return '';
    }
}

export function handleRequestsHeaders(requests) {

    var output = [];

    for(let i = 0; i < requests.length; i++) {

        if(isNullOrUndefined(requests[i].task.project)) {
            console.log("poject nao defindo")
            console.log(requests[i])

            output.push({
                        taskId: requests[i].task,
                        collaborator: requests[i].projCollab.collaborator.name,
                        type: requests[i].type,
                        approvalDate: formatDate(requests[i].approvalDate),
                        rejectDate: formatDate(requests[i].rejectDate),
                        requestID: requests[i].taskRequestDbId,
                        projectID: requests[i].projCollab.project.projectId
                
            
            });
        
        } else {
            console.log("poject  defindo")
            console.log(requests[i].task.taskID)

            output.push({
                    taskId: requests[i].task.taskID,
                    collaborator: requests[i].projCollab.collaborator.name,
                    type: requests[i].type,
                    approvalDate: formatDate(requests[i].approvalDate),
                    rejectDate: formatDate(requests[i].rejectDate),
                    requestID: requests[i].taskRequestDbId,
                    projectID: requests[i].task.project.projectId
                
                });
        }

    }
    return output;

    
}
