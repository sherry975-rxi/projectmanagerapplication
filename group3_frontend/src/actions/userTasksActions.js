import AuthService from './../pages/loginPage/AuthService';
import * as filterActions from './filterActions'


export function updateFinishedTasks(projectId) {
    return (dispatch) => {
        fetch(
            `/projects/${projectId}/tasks/finished`,
            {
                headers: { 'Authorization': localStorage.getItem('id_token') },
                method: 'GET'
            }
        ).then(responseData => responseData.json())
            .then(data => {
                dispatch(finishTasksFetched(data))
                dispatch(filterActions.changeToFinished())
                return data
            });
    }
}


export function updateOngoingTasks(projectId) {
    return (dispatch) => {
        fetch(
            `/projects/${projectId}/tasks/unfinished`,
            {
                headers: { 'Authorization': localStorage.getItem('id_token') },
                method: 'GET'
            }
        ).then(responseData => responseData.json())
            .then(data => {
                dispatch(ongoingTasksFetched(data))
                dispatch(filterActions.changeToOnGoing())
                return data
            });
    }
}


export function updateNotStartedTasks(projectId) {
    return (dispatch) => {
        fetch(
            `/projects/${projectId}/tasks/notstarted`,
            {
                headers: { 'Authorization': localStorage.getItem('id_token') },
                method: 'GET'
            }
        ).then(responseData => responseData.json())
            .then(data => {
                dispatch(filterActions.changeToNotStarted())
                return data
            });
    }
}

export function updateAllTasks(projectId) {
    return (dispatch) => {
        fetch(
            `/projects/${projectId}/tasks/all`,
            {
                headers: { 'Authorization': localStorage.getItem('id_token') },
                method: 'GET'
            }
        ).then(responseData => responseData.json())
            .then(data => {
                dispatch(allTasksFetched(data))
                dispatch(filterActions.changeToAllTasks())
                return data
            });
    }
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