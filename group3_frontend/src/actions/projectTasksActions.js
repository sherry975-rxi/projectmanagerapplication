import AuthService from './../pages/loginPage/AuthService';


export function updateFinishedTasks(projectId, taskId) {
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
                return data
            });
    }
}


export function updateOngoingTasks(projectId) {
    return (dispatch) => {
        fetch(
            `/projects/${projectId}/tasks/withoutCollaborators`,
            {
                headers: { 'Authorization': localStorage.getItem('id_token') },
                method: 'GET'
            }
        ).then(responseData => {
            dispatch(ongoingTasksFetched(responseData))
            return responseData
        });
    }
}

export function updateStandByTasks(projectId) {
    return (dispatch) => {
        fetch(
            `/projects/${projectId}/tasks/`,
            {
                headers: { 'Authorization': localStorage.getItem('id_token') },
                method: 'GET'
            }
        ).then(responseData => {
            dispatch(standByTasksFetched(responseData))
            return responseData
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
        ).then(responseData => {
            dispatch(standByTasksFetched(responseData))
            return responseData
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
        ).then(responseData => {
            dispatch(allTasksFetched(responseData))
            return responseData
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
        type: 'FINISHTASKS_FETCHED',
        ongoingTasks
    };
}

export function standByTasksFetched(standbyTasks) {
    return {
        type: 'FINISHTASKS_FETCHED',
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