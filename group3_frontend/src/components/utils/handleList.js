export function handleTaskHeaders(list) {
    return list.map(task => [
        task.taskID,
        task.project,
        task.description,
        task.currentState,
        formatDate(task.startDate)
    ]);
}

export function handleProjectHeaders(list) {
    return list.map(project => [
        project.projectId,
        project.ProjectManager,
        project.description,
        project.status,
        formatDate(project.startDate),
        ''
    ]);
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
    let mydate = new Date(date);
    var dateFormat = require('dateformat');
    let formatedDate = new Date(
        mydate.getTime() - mydate.getTimezoneOffset() * -60000
    );

    return dateFormat(formatedDate, 'dd/mmm/yyyy').toString();
}
