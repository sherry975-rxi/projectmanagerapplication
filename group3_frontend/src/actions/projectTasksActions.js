import * as filterActions from './filterActions';
import { toastr } from 'react-redux-toastr';
import {
    GET_AVAILABLE_COLLABORATORS_FOR_TASK_ERROR,
    GET_AVAILABLE_COLLABORATORS_FOR_TASK_FULLFIELD
} from './actions';

export function addCollaboratorToTask(projectId, taskId, userDTO) {
    return dispatch => {
        fetch(`/projects/${projectId}/tasks/${taskId}/addCollab`, {
            method: 'post',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                Authorization: localStorage.getItem('id_token')
            },
            body: JSON.stringify(userDTO)
        })
            .then(responseData => responseData.json())
            .then(res => {
                if (res.taskFinished === false) {
                    toastr.success('Collaborator was added to task');

                    dispatch(
                        getAvailableCollaboratorsForTask(projectId, taskId)
                    );
                    dispatch(updateAllTasks(projectId));
                }
            })
            .catch(err => {
                toastr.error('Already a Task Collaborator');
            });
    };
}

export function getAvailableCollaboratorsForTask(projectId, taskId) {
    return dispatch => {
        fetch(
            `/projects/${projectId}/tasks/${taskId}/collabsAvailableForTask`,
            {
                method: 'get',
                headers: {
                    Authorization: localStorage.getItem('id_token')
                }
            }
        )
            .then(responseData => responseData.json())
            .then(responseData => {
                if (responseData.error) {
                    dispatch(
                        getAvailableCollaboratorsForTaskError(
                            responseData.error
                        )
                    );
                } else {
                    dispatch(
                        getAvailableCollaboratorsForTaskFullfield(
                            responseData,
                            taskId
                        )
                    );
                }
            });
    };
}

export function getAvailableCollaboratorsForTaskError(payload) {
    return {
        type: GET_AVAILABLE_COLLABORATORS_FOR_TASK_ERROR,
        payload
    };
}

export function getAvailableCollaboratorsForTaskFullfield(payload, taskId) {
    const collaboratorsForTask = {};
    collaboratorsForTask[taskId] = payload;
    return {
        type: GET_AVAILABLE_COLLABORATORS_FOR_TASK_FULLFIELD,
        payload: collaboratorsForTask
    };
}

export function updateFinishedTasks(projectId) {
    return dispatch => {
        tasksLoading();
        fetch(`/projects/${projectId}/tasks/finished`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(finishTasksFetched(data));
                dispatch(filterActions.changeToFinished());
                return data;
            })
            .catch(error => {
                fetchTasksHasErrored();
            });
    };
}

export function updateUnfinishedTasks(projectId) {
    return dispatch => {
        tasksLoading();
        fetch(`/projects/${projectId}/tasks/unfinished`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(unfinishedTasksFetched(data));
                dispatch(filterActions.changeToUnfinished());
                return data;
            })
            .catch(error => {
                fetchTasksHasErrored();
            });
    };
}

export function updateStandByTasks(projectId) {
    return dispatch => {
        tasksLoading();
        fetch(`/projects/${projectId}/tasks/withoutCollaborators`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(standByTasksFetched(data));
                dispatch(filterActions.changeToStandBy());
                return data;
            })
            .catch(error => {
                fetchTasksHasErrored();
            });
    };
}

export function updateNotStartedTasks(projectId) {
    return dispatch => {
        tasksLoading();
        fetch(`/projects/${projectId}/tasks/notstarted`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(notStartedTasksFetched(data));
                dispatch(filterActions.changeToNotStarted());
                return data;
            })
            .catch(error => {
                fetchTasksHasErrored();
            });
    };
}

export function updateExpiredTasks(projectId) {
    return dispatch => {
        tasksLoading();
        fetch(`/projects/${projectId}/tasks/expired`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(expiredTasksFetched(data));
                dispatch(filterActions.changeToExpired());
                return data;
            })
            .catch(error => {
                console.log(error);
                fetchTasksHasErrored();
            });
    };
}

export function updateAllTasks(projectId) {
    return dispatch => {
        tasksLoading();
        fetch(`/projects/${projectId}/tasks/all`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(allTasksFetched(data));
                dispatch(filterActions.changeToAllTasks());
                return data;
            })
            .catch(error => {
                fetchTasksHasErrored();
            });
    };
}

export function updateCancelledTasks(projectId) {
    return dispatch => {
        tasksLoading();
        fetch(`/projects/${projectId}/tasks/cancelled`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(cancelledTasksFetched(data));
                console.log(data);
                dispatch(filterActions.changeToCancelled());
                return data;
            })
            .catch(error => {
                console.log(error);
                fetchTasksHasErrored();
            });
    };
}

export function finishTasksFetched(finishedTasks) {
    return {
        type: 'FINISHTASKS_FETCHED',
        finishedTasks,
        tasks: finishedTasks
    };
}

export function unfinishedTasksFetched(unfinishedTasks) {
    return {
        type: 'UNFINISHEDTASKS_FETCHED',
        unfinishedTasks,
        tasks: unfinishedTasks
    };
}

export function standByTasksFetched(standbyTasks) {
    return {
        type: 'STANDBYTASKS_FETCHED',
        standbyTasks,
        tasks: standbyTasks
    };
}

export function notStartedTasksFetched(notStartedTasks) {
    return {
        type: 'NOTSTARTED_FETCHED',
        notStartedTasks,
        tasks: notStartedTasks
    };
}

export function expiredTasksFetched(expiredTasks) {
    return {
        type: 'EXPIRED_FETCHED',
        expiredTasks,
        tasks: expiredTasks
    };
}

export function allTasksFetched(allTasks) {
    return {
        type: 'ALLTASKS_FETCHED',
        allTasks,
        tasks: allTasks
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
