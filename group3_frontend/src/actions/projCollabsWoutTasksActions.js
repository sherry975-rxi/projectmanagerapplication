
import AuthService from "../pages/loginPage/AuthService";


export function updateUnassignedProjCollabs(projectId) {
    const authService = new AuthService();
    return dispatch => {
        listIsLoading()
        authService.fetch(`/projects/${projectId}/tasks/collabsAvailableForTask`, {
            method: 'GET'
        })
            .then(data => {
                dispatch(projCollabWoutTasksFetched(data));
                return data;
            }).catch((error) => {
                dispatch(fetchCollabsWoutTasksHasErrored());
            });
    };
}

export function projCollabWoutTasksFetched(allProjCollabs) {
    return {
        type: 'COLLABWOUTTASKS_FETCHED',
        collabs: allProjCollabs
    };
}

export function listIsLoading() {
    return {
        type: 'ITEMCOLLABSWOUTTASKS_LOADING'
    };
}

export function fetchCollabsWoutTasksHasErrored() {
    return {
        type: 'FETCH_HAS_ERRORED'
    };
}