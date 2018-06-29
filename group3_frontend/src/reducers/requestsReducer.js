const INITIAL_STATE = {
    itemIsLoading: true,
    allRequests: [],
    openedRequests: [],
    closedRequests: [],
    searchList: [],
    error: false

}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'ALL_REQUESTS_FETCHED':
            return { ...state, itemIsLoading: false, allRequests: action.allRequests, error: false }
        case 'OPENED_REQUESTS_FETCHED':
            return { ...state, itemIsLoading: false, openedRequests: action.openedRequests, error: false }
        case 'CLOSED_REQUESTS_FETCHED':
            return { ...state, itemIsLoading: false, closedRequests: action.closedRequests, error: false }
        case 'SEARCH_REQUESTS_FETCHED':
            return { ...state, itemIsLoading: false, searchList: action.searchList, error: false }
        case 'ITEM_LOADING':
            return { ...state, itemIsLoading: true, allRequests: action.allRequests, error: false }
        case 'FETCH_HAS_ERRORED':
            return { ...state, itemIsLoading: false, error: true }
        default:
            return state;
    }
}