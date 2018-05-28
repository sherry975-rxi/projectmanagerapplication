const INITIAL_STATE = {
    authenticated: false,
    logoutButton: 'notLogged'
}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'SUCCESSFULL':
            return { ...state, authenticated: true, logoutButton: 'logged' }
        case 'FAIL':
            localStorage.removeItem('id_token')
            return { ...state, authenticated: false, logoutButton: 'notLogged' }
        default:
            return state;
    }
}