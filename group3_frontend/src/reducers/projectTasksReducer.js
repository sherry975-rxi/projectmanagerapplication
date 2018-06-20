const INITIAL_STATE = {
    itemIsLoading: true,
    finishedTasks: [],
    unfinishedTasks: [],
    wihoutCollab: [],
    notStartedTasks: [],
    expiredTasks: [],
    allTasks: [],
    cancelledTasks: [],
    error: false

}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'FINISHTASKS_FETCHED':
            return { ...state, itemIsLoading: false, finishedTasks: action.finishedTasks, error: false }
        case 'UNFINISHEDTASKS_FETCHED':
            return { ...state, itemIsLoading: false, unfinishedTasks: action.unfinishedTasks, error: false }
        case 'STANDBYTASKS_FETCHED':
            return { ...state, itemIsLoading: false, standbyTasks: action.wihoutCollab, error: false }
        case 'NOTSTARTED_FETCHED':
            return { ...state, itemIsLoading: false, notStartedTasks: action.notStartedTasks, error: false }
        case 'EXPIRED_FETCHED':
            return { ...state, itemIsLoading: false, expiredTasks: action.expiredTasks, error: false }
        case 'ALLTASKS_FETCHED':
            return { ...state, itemIsLoading: false, allTasks: action.allTasks, error: false }
        case 'CANCELLED_FETCHED':
            return { ...state, itemIsLoading: false, cancelledTasks: action.cancelledTasks, error: false }
        case 'ITEM_LOADING':
            return { ...state, itemIsLoading: true, allTasks: action.allTasks, error: false }
        case 'FETCH_HAS_ERRORED':
            return { ...state, itemIsLoading: false, error: true }
        default:
            return state;
    }
}