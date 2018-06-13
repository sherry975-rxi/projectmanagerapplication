const INITIAL_STATE = {
    tasksUpdated: false,
    finishedTasks: [],
    ongoingTasks: [],
    standbyTasks: [],
    notStartedTasks: [],
    allTasks: []

}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'FINISHTASKS_FETCHED':
            return { ...state, tasksUpdated: true, finishedTasks: action.finishedTasks }
        case 'ONGOING_FETCHED':
            return { ...state, tasksUpdated: true, ongoingTasks: action.ongoingTasks }
        case 'STANDBYTASKS_FETCHED':
            return { ...state, tasksUpdated: true, standbyTasks: action.standbyTasks }
        case 'NOTSTARTED_FETCHED':
            return { ...state, tasksUpdated: true, notStarted: action.standbyTasks }
        case 'ALLTASKS_FETCHED':
            return { ...state, tasksUpdated: true, allTasks: action.allTasks }

        default:
            return state;
    }
}