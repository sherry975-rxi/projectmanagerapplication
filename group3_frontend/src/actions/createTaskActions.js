import AuthService from "../pages/loginPage/AuthService";


export function createTask(taskDetails, projectId) {
    const authService = new AuthService();

    return dispatch => {
        dispatch(taskIsBeingCreated())
        authService.fetchRaw(
            `/projects/${projectId}/tasks/`,
            {
                body: JSON.stringify(taskDetails),
                method: 'POST'
            }
        ).then(responseData => {
            if (responseData.status >= 200 && responseData.status <= 300) {
                return responseData
            }
        }).then(responseData => responseData.json())
            .then(res => {
                dispatch(taskWasCreated(res))
            })
    };
}

export function deleteTaskCreated(projectId, taskId) {

    return dispatch => {
        fetch(
            `/projects/${projectId}/tasks/${taskId}`,
            {
                headers: { Authorization: localStorage.getItem('id_token') },
                method: 'DELETE'
            }).then(response => {
                dispatch(taskCreatedWasDeleted())
            })

    };
}

export function taskCreatedWasDeleted() {
    return {
        type: 'TASK_DELETED'
    };
}


export function taskWasCreated(task) {
    return {
        type: 'TASK_CREATED',
        task: task
    };
}

export function dependencyTasksWereFetched(dependencyTasks) {
    return {
        type: 'DEPENDENCY_TASKS_FETCHED',
        dependencyTasks
    };
}

export function taskIsBeingCreated() {
    return {
        type: 'TASK_IS_BEING_CREATED'
    };
}



