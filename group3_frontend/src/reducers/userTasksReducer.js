const INITIAL_STATE = {
    itemIsLoading: true,
    finishedTasks: [],
    ongoingTasks: [],
    allTasks: [],
    error: false

}

const ERROR = 'Sorry! Something went wrong. We are working to fix it quickly'

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'FINISHTASKS_FETCHED':
            return { ...state, itemIsLoading: false, finishedTasks: action.finishedTasks, error: false }
        case 'ONGOING_FETCHED':
            return { ...state, itemIsLoading: false, ongoingTasks: action.ongoingTasks, error: false }
        case 'ALLTASKS_FETCHED':
            return { ...state, itemIsLoading: false, allTasks: action.allTasks, error: false }
        case 'ITEM_LOADING':
            return { ...state, itemIsLoading: true, allTasks: action.allTasks, error: false }
        case 'FETCH_HAS_ERRORED':
            return { ...state, itemIsLoading: false, error: true }
        default:
            return state;
    }
}