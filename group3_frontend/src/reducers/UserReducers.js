const INITIAL_STATE = {
    usersUpdated: false,
    allUsers: [],
    emailUsers: [],
    allCollaborators: [],
    allDirector: [],
    allAdministrator: [],
    searchList: []

}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'ALLUSERS_FETCHED':
            return { ...state, usersUpdated: true, allUsers: action.allUsers, error: action.allUsers.error }
        case 'EMAILUSERS_FETCHED':
            return { ...state, usersUpdated: true, emailUsers: action.emailUsers, error: action.emailUsers.error }
        case 'ALLCOLLABORATORS_FETCHED':
            return { ...state, usersUpdated: true, allCollaborators: action.allCollaborators, error: action.allCollaborators.error }
        case 'ALLDIRECTORS_FETCHED':
            return { ...state, usersUpdated: true, allDirector: action.allDirector, error: action.allDirector.error }
        case 'ALLADMINISTRATOR_FETCHED':
            return { ...state, usersUpdated: true, allAdministrator: action.allAdministrator, error: action.allAdministrator.error }
        case 'SEARCHUSERS_FETCHED':
            return { ...state, usersUpdated: true, searchList: action.searchList, error: action.searchList.error }
        default:
            return state;
    }
}