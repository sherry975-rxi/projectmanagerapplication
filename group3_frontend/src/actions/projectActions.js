import { GET_USER_PROJECTS_FULLFIELD, GET_ACTIVE_PROJECTS_FULLFIELD } from './actions';
import AuthService from "../pages/loginPage/AuthService";


export function refreshProjects(profile, userId) {
    if(profile === "DIRECTOR") {
        getActiveProjects(userId);
    } else if (profile === "COLLABORATOR") {
        getUserProjects(userId);
    }
}

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

export function getProjectCost(projectId) {
    const authService = new AuthService();
    return dispatch => {
        authService.fetch(
            `/projects/${projectId}/cost`,
            {
                method: 'get'
            }
        ).then(responseData => {
            dispatch(getProjectCostFullfield(responseData));
        });
    }
}

export function changeCalculationMethod(projectId, projectDTOData, userId) {
    const authService = new AuthService();
    return dispatch => {
        authService.fetch(
            `/projects/${projectId}`,
            {
                body: JSON.stringify(projectDTOData),
                method: 'PATCH'
            }
        ).then(responseData => {
            dispatch(getProjectCost(projectId))
            dispatch(changeCalculationMethodFullfield(responseData))
            dispatch(getUserProjects(userId))
        });
    }
}

export const changeCalculationMethodFullfield = payload => {
    return {
        type: 'CALCULATION_METHOD_UPDATED',
        payload
    }
}


export const getProjectCostFullfield = payload => {
    console.log(payload)
    return {
        type: 'PROJECT_COST_LOADED',
        payload
    }
}



export const getActiveProjectsFullfield = payload => {
    return {
        type: GET_ACTIVE_PROJECTS_FULLFIELD,
        payload
    };
};


export const chooseProject = payload => {
    return {
        type: 'PROJECT_CHOSEN',
        payload
    }
}