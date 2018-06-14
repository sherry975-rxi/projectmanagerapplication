import { GET_USER_PROJECTS_FULLFIELD } from './actions';

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
