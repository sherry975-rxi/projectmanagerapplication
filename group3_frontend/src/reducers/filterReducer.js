const INITIAL_STATE = {
    filterType: 'all'
}



export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'ALLPROJECTTASKS':
            return { ...state, filterType: 'all' }
        case 'ONGOING':
            return { ...state, filterType: 'unfinished' }
        case 'FINISHED':
          return { ...state, filterType: 'finished' }
        case 'STANDBY':
             return { ...state, filterType: 'withoutCollaborators' }
        case 'NOTSTARTED':
            return { ...state, filterType: 'notstarted'}
        case 'EXPIRED':
            return { ...state, filterType: 'expired'}
        default:
            return state;
    }
}