const INITIAL_STATE = {
    usersUpdated: false,
    allUsers: [],
    emailUsers: [],
    allCollaborators: [],
    allDirector: [],
    allAdministrator: []

}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'ALLUSERS_FETCHED':
            return { ...state, usersUpdated: true, allUsers: action.allUsers }
        case 'EMAILUSERS_FETCHED':
            return { ...state, usersUpdated: true, emailUsers: action.emailUsers }
        case 'ALLCOLLABORATORS_FETCHED':
            return { ...state, usersUpdated: true, allCollaborators: action.allCollaborators }
        case 'ALLDIRECTORS_FETCHED':
            return { ...state, usersUpdated: true, allDirector: action.allDirector }
        case 'ALLADMINISTRATOR_FETCHED':
            return { ...state, usersUpdated: true, allAdministrator: action.allAdministrator }
        default:
            return state;
    }
}