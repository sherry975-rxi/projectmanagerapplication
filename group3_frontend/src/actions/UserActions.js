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


export function  searchList (event,list, option) {
    const authService = new AuthService();

        return dispatch => {
            var searchList = searching(event, list, option)
            
                dispatch(searchListUsersFetched(searchList));
                dispatch(filterUserActions.changeToSearchUsers())
            
        };
    
}

export function searching (event, list, option) {
        var newlist = list;
        //if(list.length > 0){
        switch (option){
            case '1':
                console.log("escolhi opção 1")
                newlist = newlist.filter(function(item){
                return item.name.toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
                });
            break;
            case '2':
                console.log("escolhi opção 2")
                newlist = newlist.filter(function(item){
                return item.email.toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
                });
            break;
            case '3':
                console.log("escolhi opção 3")
                newlist = newlist.filter(function(item){
                return item.userProfile.toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
                });
            break;
            case '4':
                console.log("escolhi opção 4")
                newlist = newlist.filter(function(item){
                return item.function.toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
                });
            break;
            case '5':
                console.log("escolhi opção 5")
                newlist = newlist.filter(function(item){
                return item.systemUserStateActive.toString().toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
                });
            break;
            default:
            console.log("escolhi opção default")
                newlist = newlist.filter(function(item){
                return item.email.toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
                });

            }
        console.log("LKJHGFDFGNMNBVCXSDERTH")
        console.log(newlist)
        return newlist;
      }


export function updateVisitors() {
    const authService = new AuthService();

    return dispatch => {
        authService.fetch(`/users/profiles/UNASSIGNED`, {
            method: 'GET'
        }).then(data => {
                dispatch(allVisitorsFetched(data));
                dispatch(filterUserActions.changeToVISITOR());
                return data;
            });
    };
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

export function allVisitorsFetched(allVisitors) {
    return {
        type: 'ALLVISITORS_FETCHED',
        allVisitors
    };
}
