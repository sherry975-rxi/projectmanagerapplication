const INITIAL_STATE = {
    filterType: 'myAll'
}



export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'MYALLTASKS':
            return { ...state, filterType: 'myAll' }
        case 'MYONGOING':
            return { ...state, filterType: 'myUnfinished' }
        case 'MYFINISHED':
          return { ...state, filterType: 'myFinished' }
        default:
            return state;
    }
}