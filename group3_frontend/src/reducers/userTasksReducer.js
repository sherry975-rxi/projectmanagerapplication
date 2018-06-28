const INITIAL_STATE = {
    itemIsLoading: true,
    myFinishedTasks: [],
    myOngoingTasks: [],
    myAllTasks: [],
    lastMonthFinishedTasks: [],
    updatedList: [],
    error: false

}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'MYFINISHTASKS_FETCHED':
            return { ...state, itemIsLoading: false, myFinishedTasks: action.myFinishedTasks, error: false }
        case 'MYONGOING_FETCHED':
            return { ...state, itemIsLoading: false, myOngoingTasks: action.myOngoingTasks, error: false }
        case 'MYALLTASKS_FETCHED':
            return { ...state, itemIsLoading: false, myAllTasks: action.myAllTasks, error: false }
        case 'LASTMONTHTASKS_FETCHED':
            return { ...state, itemIsLoading: false, lastMonthFinishedTasks: action.lastMonthFinishedTasks, error: false}
        case 'SEARCHTASKS_FETCHED':
            return { ...state, itemIsLoading: false, updatedList: action.updatedList, error: false }
        case 'ITEM_LOADING':
            return { ...state, itemIsLoading: true, myAllTasks: action.myAllTasks, error: false }
                case 'FETCH_HAS_ERRORED':
            return { ...state, itemIsLoading: false, error: true }
        default:
            return state;
    }
}