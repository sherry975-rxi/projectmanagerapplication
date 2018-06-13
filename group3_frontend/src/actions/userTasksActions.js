import AuthService from './../pages/loginPage/AuthService';
import * as filterActions from './filterActions'


export function updateFinishedTasks(userId) {
    return (dispatch) => {
        fetch(
            `/users/${userId}/tasks/finished`,
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


export function updateOngoingTasks(userId) {
    return (dispatch) => {
        fetch(
            `/users/${userId}/tasks/pending`,
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


export function updateAllTasks(userId) {
    return (dispatch) => {
        fetch(
            `/users/${userId}/tasks/`,
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


export function allTasksFetched(allTasks) {
    return {
        type: 'ALLTASKS_FETCHED',
        allTasks
    };
}