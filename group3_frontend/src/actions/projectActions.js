import { GET_USER_PROJECTS_FULLFIELD, GET_ACTIVE_PROJECTS_FULLFIELD } from './actions';
import AuthService from "../pages/loginPage/AuthService";

export function getUserProjects(userId) {
    const authService = new AuthService();

    return dispatch => {
        authService.fetch(`/projects/${userId}/myProjects`, {
            method: 'GET'
        }).then(data => {
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
    const authService = new AuthService();

    return dispatch => {
        authService.fetch(`/projects/active`, {
            method: 'GET'
        }).then(data => {
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


export function chooseProject(projectManager) {
    return {
        type: 'PROJECT_CHOSEN',
        projectManager
    }
}