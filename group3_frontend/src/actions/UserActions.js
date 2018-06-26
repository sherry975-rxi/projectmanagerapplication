import * as filterUserActions from './filterUserActions';
import AuthService from "../pages/loginPage/AuthService";

export function updateAllUsers() {
    const authService = new AuthService();

    return dispatch => {
        authService.fetch(`/users/allUsers`, {
            method: 'GET'
        }).then(data => {
                dispatch(allUsersFetched(data));
                dispatch(filterUserActions.changeToALLUSERS());
                return data;
            });
    };
}

export function updateEmail(email) {
    const authService = new AuthService();

    return dispatch => {
        authService.fetch(`/users/email/${email}`, {
            method: 'GET'
        }).then(data => {
                dispatch(emailUsersFetched(data));
                dispatch(filterUserActions.changeToEMAIL());
                return data;
            });
    };
}

export function updateCollaborators() {
    const authService = new AuthService();

    return dispatch => {
        authService.fetch(`/users/profiles/COLLABORATOR`, {
            method: 'GET'
        }).then(data => {
                dispatch(allCollaboratorsFetched(data));
                dispatch(filterUserActions.changeToCOLLABORATOR());
                return data;
            });
    };
}

export function updateDirector() {
    const authService = new AuthService();

    return dispatch => {
        authService.fetch(`/users/profiles/DIRECTOR`, {
            method: 'GET'
        }).then(data => {
                dispatch(allDirectorFetched(data));
                dispatch(filterUserActions.changeToDIRECTOR());
                return data;
            });
    };
}

export function updateAdministrator() {
    const authService = new AuthService();

    return dispatch => {
        authService.fetch(`/users/profiles/ADMIN`, {
            method: 'GET'
        }).then(data => {
                dispatch(allAdministratorFetched(data));
                dispatch(filterUserActions.changeToADMINISTRATOR());
                return data;
            });
    };
}

export function  searchList (event,list) {
    const authService = new AuthService();

        return dispatch => {
            var searchList = searching(event, list)
            
                dispatch(searchListUsersFetched(searchList));
                dispatch(filterUserActions.changeToSearchUsers())
            
        };
    
}

export function searching (event, list) {
        var newlist = list;
        //if(list.length > 0){
        newlist = newlist.filter(function(item){
          return item.email.toLowerCase().search(
            event.target.value.toLowerCase()) !== -1;
        });
        console.log("LKJHGFDFGNMNBVCXSDERTH")
        console.log(newlist)
        return newlist;
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

export function searchListUsersFetched(searchList) {
    return {
        type: 'SEARCHUSERS_FETCHED',
        searchList
    };
}
