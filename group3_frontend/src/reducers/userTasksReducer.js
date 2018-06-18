const INITIAL_STATE = {
    tasksUpdated: false,
    finishedTasks: [],
    ongoingTasks: [],
    allTasks: []

}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'FINISHTASKS_FETCHED':
            return { ...state, tasksUpdated: true, finishedTasks: action.finishedTasks, error: action.finishedTasks.error }
        case 'ONGOING_FETCHED':
            return { ...state, tasksUpdated: true, ongoingTasks: action.ongoingTasks, error: action.ongoingTasks.error }
        case 'ALLTASKS_FETCHED':
            return { ...state, tasksUpdated: true, allTasks: action.allTasks, error: action.allTasks.error }
        default:
            return state;
    }
}