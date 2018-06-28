import AuthService from '../pages/loginPage/AuthService';

export function updateAvailableUsers(projectId) {

    return dispatch => {
        const authService = new AuthService();
        authService
            .fetch(`/projects/${projectId}/team/usersAvailable`, {
                method: 'get'
            })
            .then(responseData => {
                dispatch(availableUsersWereFetched(responseData));
            });
    };
}

export function availableUsersWereFetched(availableUsers) {
    return {
        type: 'AVAILABLE_USER_FETCHED',
        availableUsers
    };
}
