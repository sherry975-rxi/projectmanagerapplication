import * as filterActions from './filterActions';
import AuthService from "../pages/loginPage/AuthService";


export function updateFinishedTasks(userId) {
    const authService = new AuthService();

    return dispatch => {
        authService.fetch(`/users/${userId}/tasks/finished`, {
            method: 'GET'
        }).then(data => {
            dispatch(finishTasksFetched(data));
            dispatch(filterActions.changeToFinished());
            return data;
        });
    };
}

export function updateOngoingTasks(userId) {
    const authService = new AuthService();

    return dispatch => {
        authService.fetch(`/users/${userId}/tasks/pending`, {
            method: 'GET'
        }).then(data => {
                dispatch(ongoingTasksFetched(data));
                dispatch(filterActions.changeToOnGoing());
                return data;
        });
    };
}

export function updateAllTasks(userId) {
    const authService = new AuthService();

    return dispatch => {
        authService.fetch(`/users/${userId}/tasks/`, {
            method: 'GET'
        }).then(data => {
                dispatch(allTasksFetched(data));
                dispatch(filterActions.changeToAllTasks());
                return data;
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

export function allTasksFetched(allTasks) {
    return {
        type: 'ALLTASKS_FETCHED',
        allTasks
    };
}

