const INITIAL_STATE = {
    authenticated: false,
    user: {},
    logoutButton: 'notLogged',
    error: ""

}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'SUCCESSFULL':
            return { ...state, authenticated: true, logoutButton: 'logged' }
        case 'AUTHORIZED':
            return { ...state, user: action.user }
        case 'LOGOUT':
            localStorage.removeItem('id_token')
            return { ...state, authenticated: false, user: {}, logoutButton: 'notLogged' }
        case 'ERROR':
            return { ...state, authenticated: false, logoutButton: 'notLogged', error: "Invalid Credentials!" }
        default:
            return state;
    }
}