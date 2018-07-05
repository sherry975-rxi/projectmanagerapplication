import usersFilterReducer from '../reducers/usersFilterReducer';
import INITIAL_STATE from '../reducers/usersFilterReducer';

describe('usersFilterReducer', () => {
    it('should change the state for action type ALLUSERS_FILTER', () => {
        const expectedState = {
            filterType: 'all'
        };
        const action = {
            type: 'ALLUSERS_FILTER'
        };
        expect(usersFilterReducer(INITIAL_STATE, action)).toEqual(
            expectedState
        );
    });

    it('should change the state for action type EMAILUSERS_FILTER', () => {
        const expectedState = {
            filterType: 'email'
        };
        const action = {
            type: 'EMAILUSERS_FILTER'
        };
        expect(usersFilterReducer(INITIAL_STATE, action)).toEqual(
            expectedState
        );
    });

    it('should change the state for action type ALLCOLLABORATORS_FILTER', () => {
        const expectedState = {
            filterType: 'collaborators'
        };
        const action = {
            type: 'ALLCOLLABORATORS_FILTER'
        };
        expect(usersFilterReducer(INITIAL_STATE, action)).toEqual(
            expectedState
        );
    });

    it('should change the state for action type ALLDIRECTORS_FILTER', () => {
        const expectedState = {
            filterType: 'directors'
        };
        const action = {
            type: 'ALLDIRECTORS_FILTER'
        };
        expect(usersFilterReducer(INITIAL_STATE, action)).toEqual(
            expectedState
        );
    });

    it('should change the state for action type ALLADMINISTRATOR_FILTER', () => {
        const expectedState = {
            filterType: 'administrators'
        };
        const action = {
            type: 'ALLADMINISTRATOR_FILTER'
        };
        expect(usersFilterReducer(INITIAL_STATE, action)).toEqual(
            expectedState
        );
    });

    it('should change the state for action type SEARCHUSERS_FILTER', () => {
        const initialState = {
            filterType: '',
            prevFilter: ''
        };
        const tempFilter = initialState.filterType;
        const expectedState = {
            filterType: 'searchUsers',
            prevFilter: tempFilter
        };
        const action = {
            type: 'SEARCHUSERS_FILTER'
        };
        expect(usersFilterReducer(initialState, action)).toEqual(expectedState);
    });

    it('should change the state for action type ALLVISITORS_FILTER', () => {
        const expectedState = {
            filterType: 'visitors'
        };
        const action = {
            type: 'ALLVISITORS_FILTER'
        };
        expect(usersFilterReducer(INITIAL_STATE, action)).toEqual(
            expectedState
        );
    });

    it('should change the state for action type TOUPDATE', () => {
        const expectedState = {
            update: true
        };
        const action = {
            type: 'TOUPDATE'
        };
        expect(usersFilterReducer(INITIAL_STATE, action)).toEqual(
            expectedState
        );
    });

    it('should change the state for action type UPDATED', () => {
        const expectedState = {
            update: false
        };
        const action = {
            type: 'UPDATED'
        };
        expect(usersFilterReducer(INITIAL_STATE, action)).toEqual(
            expectedState
        );
    });

    it('should return default state when a invalid action is passed', () => {
        const initialState = {
            filterType: '',
            update: false,
            prevFilter: ''
        };

        expect(usersFilterReducer(undefined, 'INVALID ACTION')).toEqual(
            initialState
        );
    });
});
