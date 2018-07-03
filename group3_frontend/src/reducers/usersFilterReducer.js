const INITIAL_STATE = {
   filterType:'',
   update: false,
   prevFilter: ''
}

export default (state = INITIAL_STATE, action) => {
    var tempFilter = state.filterType;
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
        case 'SEARCHUSERS_FILTER':
            return { ...state, filterType: 'searchUsers', prevFilter: tempFilter }
        case 'ALLVISITORS_FILTER':
            return { ...state, filterType: 'visitors' }
        case 'TOUPDATE':
            return { ...state, update: true}
        case 'UPDATED':
            return { ...state, update: false }
        default:
            return state;
    }
}