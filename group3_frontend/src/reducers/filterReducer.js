const INITIAL_STATE = {
    filterType: 'all'
}



export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'ALLPROJECTTASKS':
            return { ...state, filterType: 'all' }
        case 'UNFINISHED':
            return { ...state, filterType: 'unfinished' }
        case 'FINISHED':
          return { ...state, filterType: 'finished' }
        case 'STANDBY':
             return { ...state, filterType: 'withoutCollaborators' }
        case 'NOTSTARTED':
            return { ...state, filterType: 'notstarted'}
        case 'MYALLTASKS':
            return { ...state, filterType: 'myAll' }
        case 'MYONGOING':
            return { ...state, filterType: 'myUnfinished' }
        case 'MYFINISHED':
          return { ...state, filterType: 'myFinished' }
        case 'EXPIRED':
            return { ...state, filterType: 'expired'}
        case 'CANCELLED':
            return { ...state, filterType: 'cancelled'}
        default:
            return state;
    }
}