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
        default:
            return state;
    }
}