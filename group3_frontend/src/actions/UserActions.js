import * as filterUserActions from './filterUserActions'


export function updateAllUsers() {
    return (dispatch) => {
        fetch(
            `/users/allUsers`,
            {
                headers: { 'Authorization': localStorage.getItem('id_token') },
                method: 'GET'
            }
        ).then(responseData => responseData.json())
            .then(data => {
                dispatch(allUsersFetched(data))
                dispatch(filterUserActions.changeToALLUSERS())
                return data
            });
    }
}


export function updateEmail(email) {
    return (dispatch) => {
        fetch(
            `/users/email/${email}`,
            {
                headers: { 'Authorization': localStorage.getItem('id_token') },
                method: 'GET'
            }
        ).then(responseData => responseData.json())
            .then(data => {
                dispatch(emailUsersFetched(data))
                dispatch(filterUserActions.changeToEMAIL())
                return data
            });
    }
}

export function updateCollaborators() {
    return (dispatch) => {
        fetch(
            `/users/profiles/COLLABORATOR`,
            {
                headers: { 'Authorization': localStorage.getItem('id_token') },
                method: 'GET'
            }
        ).then(responseData => responseData.json())
            .then(data => {
                dispatch(allCollaboratorsFetched(data))
                dispatch(filterUserActions.changeToCOLLABORATOR())
                return data
            });
    }
}

export function updateDirector() {
    return (dispatch) => {
        fetch(
            `/users/profiles/DIRECTOR`,
            {
                headers: { 'Authorization': localStorage.getItem('id_token') },
                method: 'GET'
            }
        ).then(responseData => responseData.json())
            .then(data => {
                dispatch(allDirectorFetched(data))
                dispatch(filterUserActions.changeToDIRECTOR())
                return data
            });
    }
}

export function updateAdministrator() {
    return (dispatch) => {
        fetch(
            `/users/profiles/ADMINISTRATOR`,
            {
                headers: { 'Authorization': localStorage.getItem('id_token') },
                method: 'GET'
            }
        ).then(responseData => responseData.json())
            .then(data => {
                dispatch(allAdministratorFetched(data))
                dispatch(filterUserActions.changeToADMINISTRATOR())
                return data
            });
    }
}




export function allUsersFetched(allUsers) {
    return {
        type: 'ALLUSERS_FETCHED',
        allUsers
    };
}

export function emailUsersFetched(emailUsers) {
    return {
        type: 'EMAILUSERS_FETCHED',
        emailUsers
    };
}

export function allCollaboratorsFetched(allCollaborators) {
    console.log('AAAAAAAAA')
    console.log(allCollaborators)
    return {
        type: 'ALLCOLLABORATORS_FETCHED',
        allCollaborators
    };
}

export function allDirectorFetched(allDirector) {
    return {
        type: 'ALLDIRECTORS_FETCHED',
        allDirector
    };
}

export function allAdministratorFetched(allAdministrator) {
    return {
        type: 'ALLADMINISTRATOR_FETCHED',
        allAdministrator
    };
}