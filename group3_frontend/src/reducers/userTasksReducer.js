const INITIAL_STATE = {
    tasksUpdated: false,
    finishedTasks: [],
    ongoingTasks: [],
    allTasks: []

}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'FINISHTASKS_FETCHED':
            return { ...state, tasksUpdated: true, finishedTasks: action.finishedTasks }
        case 'ONGOING_FETCHED':
            return { ...state, tasksUpdated: true, ongoingTasks: action.ongoingTasks }
        case 'ALLTASKS_FETCHED':
            return { ...state, tasksUpdated: true, allTasks: action.allTasks }
        default:
            return state;
    }
}