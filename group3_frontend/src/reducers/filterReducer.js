const INITIAL_STATE = {
    filterType: 'unfinished'
}



export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'ONGOING':
            return { ...state, filterType: 'ongoing' }
        case 'FINISHED':
          return { ...state, filterType: 'finished' }
        case 'STANDBY':
             return { ...state, filterType: 'standby' }
        case 'NOTSTARTED':
            return { ...state, filterType: 'notstarted'}
        default:
            return state;
    }
}