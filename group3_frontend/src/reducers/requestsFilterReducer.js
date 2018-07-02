const INITIAL_STATE = {
    filterType: 'openedRequests'
}



export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'ALL_REQUESTS':
            return { ...state, filterType: 'allRequests' }
        case 'OPENED_REQUESTS':
            return { ...state, filterType: 'openedRequests' }
        case 'CLOSED_REQUESTS':
          return { ...state, filterType: 'closedRequests' }
        case 'SEARCH_REQUESTS':
          return { ...state, filterType: 'searchList' }
        default:
            return state;
    }
}