import * as filterActions from './filterActions';
import { toastr } from 'react-redux-toastr';
import {
    GET_AVAILABLE_COLLABORATORS_FOR_TASK_ERROR,
    GET_AVAILABLE_COLLABORATORS_FOR_TASK_FULLFIELD,
    CHANGE_TASK_FILTER
} from './actions';
import { TASKS_FILTER } from '../constants/TasksConstants';
import { updateUnassignedProjCollabs } from './projCollabsWoutTasksActions';
import { refreshTasksByFilter } from "./refreshTasksActions";
import * as userTasksFilterActions from "./userTasksFilterActions";

export function addCollaboratorToTask(projectId, taskId, userDTO, filterName) {
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
                    dispatch(updateUnassignedProjCollabs(projectId))

                    dispatch(
                        getAvailableCollaboratorsForTask(projectId, taskId)
                    );
                    dispatch(
                        refreshTasksByFilter(projectId, filterName)
                    );

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

export function getAllProjectTasks(projectId) {
    return dispatch => {
        tasksLoading();
        fetch(`/projects/${projectId}/tasks/all`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(allProjectTasksFetched(data));
                dispatch(filterActions.changeToAllTasks());
                return data;
            })
            .catch(error => {
                fetchTasksHasErrored();
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

export const getProjectTasksByFilter = (projectId, filterName) => {
    switch (filterName) {
        case TASKS_FILTER.ALL_TASKS:
            return getAllProjectTasks(projectId);
        case TASKS_FILTER.CANCELLED_TASKS:
            return updateCancelledTasks(projectId);
        case TASKS_FILTER.EXPIRED_TASKS:
            return updateExpiredTasks(projectId);
        case TASKS_FILTER.FINISHED_TASKS:
            return updateFinishedTasks(projectId);
        case TASKS_FILTER.NOTSTARTED_TASKS:
            return updateNotStartedTasks(projectId);
        case TASKS_FILTER.STANDBY_TASKS:
            return updateStandByTasks(projectId);
        case TASKS_FILTER.UNFINISHED_TASKS:
            return updateUnfinishedTasks(projectId);
        default:
            return getAllProjectTasks(projectId);
    }
};

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

                dispatch(filterActions.changeToCancelled());
                return data;
            })
            .catch(error => {
                fetchTasksHasErrored();
            });
    };
}

export function searchList(event, list, option) {

    return dispatch => {
        var updatedList = searching(event, list, option)

        dispatch(searchListTasksFetched(updatedList));
        dispatch(userTasksFilterActions.changeToSearchTasks())

    };

}

export function searching(event, list, option) {
    var lista1 = list;

    switch (option) {
        case '1':
            lista1 = lista1.filter(function (item) {
                return item.taskID.toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
            });
            break;
        case '2':
            lista1 = lista1.filter(function (item) {
                // JSON.stringify(item.project)
                return item.project.projectId.toString().toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
            });
            break;
        case '3':
            lista1 = lista1.filter(function (item) {
                return item.description.toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
            });
            break;
        case '4':
            lista1 = lista1.filter(function (item) {
                return item.currentState.toString().toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
            });
            break;
        case '5':
            lista1 = lista1.filter(function (item) {

                return item.startDate.toString().toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
            });
            break;
        case '6':

            lista1 = lista1.filter(function (item) {
                return item.finishDate.toString().toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
            });
            break;
        default:
            lista1 = lista1.filter(function (item) {
                return item.taskID.toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
            });


    }


    return lista1;
}

export function finishTasksFetched(finishedTasks) {
    return {
        type: 'FINISHTASKS_FETCHED',
        tasks: finishedTasks
    };
}

export function unfinishedTasksFetched(unfinishedTasks) {
    return {
        type: 'UNFINISHEDTASKS_FETCHED',
        tasks: unfinishedTasks
    };
}

export function standByTasksFetched(standbyTasks) {
    return {
        type: 'STANDBYTASKS_FETCHED',
        tasks: standbyTasks
    };
}

export function notStartedTasksFetched(notStartedTasks) {
    return {
        type: 'NOTSTARTED_FETCHED',
        tasks: notStartedTasks
    };
}

export function expiredTasksFetched(expiredTasks) {
    return {
        type: 'EXPIRED_FETCHED',
        tasks: expiredTasks
    };
}

export function allTasksFetched(allTasks) {
    return {
        type: 'ALLTASKS_FETCHED',
        tasks: allTasks
    };
}

export function allProjectTasksFetched(allTasks) {
    return {
        type: 'ALL_PROJECT_TASKS_FETCHED',
        tasks: allTasks
    };
}

export function cancelledTasksFetched(cancelledTasks) {
    return {
        type: 'CANCELLED_FETCHED',
        tasks: cancelledTasks
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

export function searchListTasksFetched(updatedList) {
    return {
        type: 'SEARCHTASKS_FETCHED',
        updatedList
    };
}

export const changeTaskFilter = filterName => {
    return {
        type: CHANGE_TASK_FILTER,
        filter: filterName
    };
};
