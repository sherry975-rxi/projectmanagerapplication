const INITIAL_STATE = {
   filterType:''
}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'ALLUSERS_FILTER':
            return { ...state, filterType: 'all' }
        case 'EMAILUSERS_FILTER':
            return { ...state, filterType: 'email' }
        case 'ALLCOLLABORATORS_FILTER':
            return { ...state, filterType: 'collaborators' }
        case 'ALLDIRECTORS_FILTER':
            return { ...state, filterType: 'directors' }
        case 'ALLADMINISTRATOR_FILTER':
            return { ...state, filterType: 'administrators' }
        default:
            return state;
    }
}