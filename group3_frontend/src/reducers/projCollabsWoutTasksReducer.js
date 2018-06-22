const INITIAL_STATE = {
    itemIsLoading: true,
    collabs: [],
    error: false
}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'COLLABWOUTTASKS_FETCHED':
            return { ...state, itemIsLoading: false, collabs: action.collabs, error: false }
        case 'ITEMCOLLABSWOUTTASKS_LOADING':
            return { ...state, itemIsLoading: true, collabs: action.collabs, error: false }
        case 'FETCH_HAS_ERRORED':
            return { ...state, itemIsLoading: false, error: true }
        default:
            return state;
    }
}