export function handleTaskHeaders(list) {
    return list.map(task => ({
        taskID: task.taskID,
        project: task.project,
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

function formatDate(date) {
    if (date != null) {
        let mydate = new Date(date);
        var dateFormat = require('dateformat');
        let formatedDate = new Date(
            mydate.getTime() - mydate.getTimezoneOffset()
        );

        return dateFormat(formatedDate, 'dd/mmm/yyyy').toString();
    } else {
        return '';
    }
}
