
export default (state = {}, action) => {
    switch (action.type) {
        case 'CHILD_TASK_FETCHED':
            return {
                ...state,
                itemIsLoading: false,
                childTask: action.child,
                error: false
            };
        case 'DEPENDENCIES_FETCHED':
            return {
                ...state,
                itemIsLoading: false,
                tasksDependencies: action.tasks,
                error: false
            };
        case 'POSSIBLE_DEPENDENCIES_FETCHED':
            return {
                ...state,
                itemIsLoading: false,
                possibleDependencies: action.tasks,
                error: false
            };

        case 'ITEM_LOADING':
            return {
                ...state,
                itemIsLoading: true,
                error: false
            };
        case 'FETCH_HAS_ERRORED':
            return { ...state, itemIsLoading: false, error: true };

        default:
            return state;
    }
};
