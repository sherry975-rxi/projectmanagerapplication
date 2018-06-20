import * as filterActions from './filterActions';

export function updateFinishedTasks(projectId) {
    return dispatch => {
        tasksLoading()
        fetch(`/projects/${projectId}/tasks/finished`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(finishTasksFetched(data));
                dispatch(filterActions.changeToFinished());
                return data;
            }).catch((error) => {
                console.log(error)
                fetchTasksHasErrored();
            });
    };
}

export function updateUnfinishedTasks(projectId) {
    return dispatch => {
        tasksLoading()
        fetch(`/projects/${projectId}/tasks/unfinished`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(unfinishedTasksFetched(data));
                dispatch(filterActions.changeToUnfinished());
                return data;
            }).catch((error) => {
                console.log(error)
                fetchTasksHasErrored();
            });
    };
}

export function updateStandByTasks(projectId) {
    return dispatch => {
        tasksLoading()
        fetch(`/projects/${projectId}/tasks/withoutCollaborators`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(standByTasksFetched(data));
                dispatch(filterActions.changeToStandBy());
                return data;
            }).catch((error) => {
                console.log(error)
                fetchTasksHasErrored();
            });
    };
}

export function updateNotStartedTasks(projectId) {
    return dispatch => {
        tasksLoading()
        fetch(`/projects/${projectId}/tasks/notstarted`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(notStartedTasksFetched(data));
                dispatch(filterActions.changeToNotStarted());
                return data;
            }).catch((error) => {
                console.log(error)
                fetchTasksHasErrored();
            });
    };
}

export function updateExpiredTasks(projectId) {
    return dispatch => {
        tasksLoading()
        fetch(`/projects/${projectId}/tasks/expired`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(expiredTasksFetched(data));
                dispatch(filterActions.changeToExpired());
                return data;
            }).catch((error) => {
            console.log(error)
            fetchTasksHasErrored();
        });
    };
}

export function updateAllTasks(projectId) {
    return dispatch => {
        tasksLoading()
        fetch(`/projects/${projectId}/tasks/all`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(allTasksFetched(data));
                dispatch(filterActions.changeToAllTasks());
                return data;
            }).catch((error) => {
                console.log(error)
                fetchTasksHasErrored();
            });
    };
}

export function updateCancelledTasks(projectId) {
    
    return dispatch => {
        tasksLoading()
        fetch(`/projects/${projectId}/tasks/cancelled`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(cancelledTasksFetched(data));
                console.log(data)
                dispatch(filterActions.changeToCancelled());
                return data;
            }).catch((error) => {
                console.log(error)
                fetchTasksHasErrored();
            });
    };
}

export function finishTasksFetched(finishedTasks) {
    return {
        type: 'FINISHTASKS_FETCHED',
        finishedTasks
    };
}

export function unfinishedTasksFetched(unfinishedTasks) {
    return {
        type: 'UNFINISHEDTASKS_FETCHED',
        unfinishedTasks
    };
}

export function standByTasksFetched(standbyTasks) {
    return {
        type: 'STANDBYTASKS_FETCHED',
        standbyTasks
    };
}

export function notStartedTasksFetched(notStartedTasks) {
    return {
        type: 'NOTSTARTED_FETCHED',
        notStartedTasks
    };
}

export function expiredTasksFetched(expiredTasks) {
    return {
        type: 'EXPIRED_FETCHED',
        expiredTasks
    };
}

export function allTasksFetched(allTasks) {
    return {
        type: 'ALLTASKS_FETCHED',
        allTasks
    };
}

export function cancelledTasksFetched(cancelledTasks) {
    return {
        type: 'CANCELLED_FETCHED',
        cancelledTasks
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

