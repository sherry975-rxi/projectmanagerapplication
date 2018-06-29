import {toastr} from "react-redux-toastr";


export function getAllTaskDependencies(projectId, taskId) {
    return dispatch => {
        tasksLoading();
        fetch(`/projects/${projectId}/tasks/${taskId}/dependencies`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(taskDependenciesFetched(data));
                return data;
            })
            .catch(error => {
                fetchTasksHasErrored();
            });
    };
}

export function getPossibleTaskDependencies(projectId, taskId) {
    return dispatch => {
        tasksLoading();
        fetch(`/projects/${projectId}/tasks/${taskId}/possibleDependencies`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(possibleTaskDependenciesFetched(data));
                return data;
            })
            .catch(error => {
                fetchTasksHasErrored();
            });
    };
}


export function createTaskDependency(projectId, taskId, parentId, postpone) {
    return dispatch => {
        tasksLoading();
        fetch(`/projects/${projectId}/tasks/${taskId}/createDependency/${parentId}/${postpone}`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'PUT'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(taskDependenciesFetched(data));
                toastr.success('Dependency added!');
                return data;
            })
            .catch(error => {
                toastr.error('lolnope');
                fetchTasksHasErrored();
            });
    };
}

export function removeTaskDependency(projectId, taskId, parentId) {
    return dispatch => {
        tasksLoading();
        fetch(`/projects/${projectId}/tasks/${taskId}/removeDependency/${parentId}`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'PUT'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(taskDependenciesFetched(data));
                toastr.success('Dependency removed!');
                return data;
            })
            .catch(error => {
                toastr.error('lolnope');
                fetchTasksHasErrored();

            });
    };
}


export function taskDependenciesFetched(taskDependencies) {
    return {
        type: 'DEPENDENCIES_FETCHED',
        tasks: taskDependencies
    };
}

export function possibleTaskDependenciesFetched(taskDependencies) {
    return {
        type: 'POSSIBLE_DEPENDENCIES_FETCHED',
        tasks: taskDependencies
    };
}

export function tasksLoading() {
    return {
        type: 'ITEM_LOADING'
    };
}


export function fetchTasksHasErrored() {
    return {
        type: 'FETCH_HAS_ERRORED'
    };
}