import AuthService from "../pages/loginPage/AuthService";

export function createTask(taskDetails, projectId) {
    const authService = new AuthService();

    return dispatch => {
        authService.fetch(
            `/projects/${projectId}/tasks/`,
            {
                body: JSON.stringify(taskDetails),
                method: 'POST'
            }
        ).then(responseData => {
            dispatch(taskWasCreated())
            return responseData
        });
    };
}


export function taskWasCreated() {
    return {
        type: 'CREATED_TASK'
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



