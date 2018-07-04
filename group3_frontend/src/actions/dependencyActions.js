import { toastr } from "react-redux-toastr";


export function reloadTask(projectId, taskId) {
    return dispatch => {
        dispatch(tasksLoading());
        fetch(`/projects/${projectId}/tasks/${taskId}`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                if(data.msg == null) {
                    dispatch(taskFetched(data));
                    return data;
                } else {
                    fetchTasksHasErrored();
                }

            });
    };
}

export function getAllTaskDependencies(projectId, taskId) {
    return dispatch => {
        tasksLoading();
        fetch(`/projects/${projectId}/tasks/${taskId}/dependencies`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                if(data.msg == null) {
                    console.log(data);
                    dispatch(taskDependenciesFetched(data));
                    return data;
                } else {
                    fetchTasksHasErrored();
                }
            });
    };
}

export function getPossibleTaskDependencies(projectId, taskId) {
    return dispatch => {

        fetch(`/projects/${projectId}/tasks/${taskId}/possibleDependencies`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                if(data.msg == null) {
                    dispatch(possibleTaskDependenciesFetched(data));
                    return data;
                } else {
                    fetchTasksHasErrored();
                }

            });

    };
}


export function createTaskDependency(projectId, taskId, parentId, postpone) {
    return dispatch => {
        fetch(`/projects/${projectId}/tasks/${taskId}/createDependency/${parentId}/${postpone}`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'PUT'
        })
            .then(responseData => responseData.json())
            .then(data => {
                if(data.msg == null) {
                    dispatch(taskDependenciesFetched(data));
                    toastr.success('Dependency added!');
                    dispatch(reloadTask(projectId, taskId));
                    return data;
                } else {
                    toastr.error('An error occurred!');
                    fetchTasksHasErrored();
                }

            });
    };
}

export function removeTaskDependency(projectId, taskId, parentId) {
    return dispatch => {

        fetch(`/projects/${projectId}/tasks/${taskId}/removeDependency/${parentId}`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'PUT'
        })
            .then(responseData => responseData.json())
            .then(data => {
                if(data.msg == null) {
                    dispatch(taskDependenciesFetched(data));
                    toastr.success('Dependency removed!');
                    return data;
                } else {
                    toastr.error('An error occurred!');
                    fetchTasksHasErrored();
                }
            });
    };
}


export function taskFetched(task) {
    return {
        type: 'CHILD_TASK_FETCHED',
        child: task
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