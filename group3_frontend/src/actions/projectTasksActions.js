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

export function updateOngoingTasks(projectId) {
    return dispatch => {
        tasksLoading()
        fetch(`/projects/${projectId}/tasks/unfinished`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(ongoingTasksFetched(data));
                dispatch(filterActions.changeToOnGoing());
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
                dispatch(standByTasksFetched(data));
                dispatch(filterActions.changeToNotStarted());
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

export function finishTasksFetched(finishedTasks) {
    return {
        type: 'FINISHTASKS_FETCHED',
        finishedTasks
    };
}

export function ongoingTasksFetched(ongoingTasks) {
    return {
        type: 'ONGOING_FETCHED',
        ongoingTasks
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

export function allTasksFetched(allTasks) {
    return {
        type: 'ALLTASKS_FETCHED',
        allTasks
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

