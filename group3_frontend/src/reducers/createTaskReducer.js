const INITIAL_STATE = {
    tasksToCreateDependency: [],
    beingCreated: false,
    taskCreated: false,
    task: ''
}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'TASK_CREATED':
            return { ...state, beingCreated: false, taskCreated: true, task: action.task }
        case 'TASK_DELETED':
            return { ...state, beingCreated: false, taskCreated: false, task: {} }
        case 'DEPENDENCY_TASKS_FETCHED':
            return { ...state, tasksToCreateDependency: action.dependencyTasks, beingCreated: false, taskCreated: false }
        case 'TASK_IS_BEING_CREATED':
            return { ...state, beingCreated: true, taskCreated: false }
        default:
            return state;
    }
}