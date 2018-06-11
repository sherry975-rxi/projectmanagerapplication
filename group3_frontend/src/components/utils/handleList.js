

export function handleTaskHeaders(list) {
    return (
        list.map((task) => ({ 'taskID': task.taskID, 'project': task.project, 'description': task.description, 'state': task.currentState, 'startDate': formatDate(task.startDate) })
        )
    )
}

export function handleProjectHeaders(list) {
    return list.map(project => [
        project.projectId,
        project.projectManager.name,
        project.description,
        project.status,
        formatDate(project.startdate),
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
