const INITIAL_STATE = {
    authenticated: false,
    logoutButton: 'notLogged',
    error: ""

}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'SUCCESSFULL':
            return { ...state, authenticated: true, logoutButton: 'logged' }
        case 'LOGOUT':
            localStorage.removeItem('id_token')
            return { ...state, authenticated: false, logoutButton: 'notLogged' }
        case 'ERROR':
            return { ...state, authenticated: false, logoutButton: 'notLogged', error: "Invalid Credentials!" }
        default:
            return state;
    }
}