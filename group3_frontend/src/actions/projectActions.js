import { GET_USER_PROJECTS_FULLFIELD, GET_ACTIVE_PROJECTS_FULLFIELD } from './actions';

export function getUserProjects(userId) {
    return dispatch => {
        fetch(`/projects/${userId}/myProjects`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(getUserProjectsFullfield(data));
            });
    };
}

export const getUserProjectsFullfield = payload => {
    return {
        type: GET_USER_PROJECTS_FULLFIELD,
        payload
    };
};


export function getActiveProjects(userId) {
    return dispatch => {
        fetch(`/projects/active`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(data => {
                dispatch(getActiveProjectsFullfield(data));
            });
    };
}



export const getActiveProjectsFullfield = payload => {
    return {
        type: GET_ACTIVE_PROJECTS_FULLFIELD,
        payload
    };
};
