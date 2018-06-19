import * as filterActions from './filterActions';
import * as userTasksFilterActions from './userTasksFilterActions';
import AuthService from "../pages/loginPage/AuthService";


export function updateMyFinishedTasks(userId) {
    const authService = new AuthService();

    return dispatch => {
        myTasksLoading()
        authService.fetch(`/users/${userId}/tasks/finished`, {
            method: 'GET'
        }).then(data => {
            dispatch(myFinishedTasksFetched(data));
            dispatch(userTasksFilterActions.changeToFinished());
            return data;
        }).catch((error) => {
            console.log(error)
            myFetchTasksHasErrored();
        });
    };
}

export function updateMyOngoingTasks(userId) {
    const authService = new AuthService();

    return dispatch => {
        myTasksLoading()
        authService.fetch(`/users/${userId}/tasks/pending`, {
            method: 'GET'
        }).then(data => {
                dispatch(myOngoingTasksFetched(data));
                dispatch(userTasksFilterActions.changeToOnGoing())
                return data;
        }).catch((error) => {
            console.log(error)
            myFetchTasksHasErrored();
        });
    };
}

export function updateMyAllTasks(userId) {
    const authService = new AuthService();

    return dispatch => {
        myTasksLoading()
        authService.fetch(`/users/${userId}/tasks/`, {
            method: 'GET'
        }).then(data => {
                dispatch(myAllTasksFetched(data));
                dispatch(userTasksFilterActions.changeToAllTasks())
                return data;
        }).catch((error) => {
            console.log(error)
            myFetchTasksHasErrored();
        });
    };
}

export function myFinishedTasksFetched(finishedTasks) {
    return {
        type: 'MYFINISHTASKS_FETCHED',
        finishedTasks
    };
}

export function myOngoingTasksFetched(ongoingTasks) {
    return {
        type: 'MYONGOING_FETCHED',
        ongoingTasks
    };
}

export function myAllTasksFetched(allTasks) {
    return {
        type: 'MYALLTASKS_FETCHED',
        allTasks
    };
}


export function myTasksLoading() {
    return {
        type: 'ITEM_LOADING'
    };
}

export function myFetchTasksHasErrored() {
    return {
        type: 'FETCH_HAS_ERRORED'
    };
}



