import reducer from '../reducers/UserReducers';
import * as types from '../actions/actions';

describe('UserReducer', () => {
    const initialState = {
        usersUpdated: false
    };

    it('should return the initial state', () => {
        expect(reducer(undefined, {})).toEqual({
            usersUpdated: false,
            allUsers: [],
            emailUsers: [],
            allCollaborators: [],
            allDirector: [],
            allAdministrator: [],
            searchList: [],
            allVisitors: []
        });
    });

    it('should handle ALL USERS and return the array empty', () => {
        expect(
            reducer([], {
                type: types.ALLUSERS_FETCHED,
                allUsers: { user1: 'mike', user2: 'john', user3: 'lily' }
            })
        ).toEqual({
            allUsers: { user1: 'mike', user2: 'john', user3: 'lily' },
            error: undefined,
            usersUpdated: true
        });
    });

    it('should change the state for action type EMAILUSERS_FETCHED', () => {
        const expectedState = {
            emailUsers: { user1: 'mike', user2: 'john', user3: 'lily' },
            error: undefined,
            usersUpdated: true
        };
        const action = {
            type: 'EMAILUSERS_FETCHED',
            emailUsers: { user1: 'mike', user2: 'john', user3: 'lily' }
        };
        expect(reducer(initialState, action)).toEqual(expectedState);
    });

    it('should change the state for action type ALLCOLLABORATORS_FETCHED', () => {
        const expectedState = {
            allCollaborators: { user1: 'mike', user2: 'john', user3: 'lily' },
            error: undefined,
            usersUpdated: true
        };
        const action = {
            type: 'ALLCOLLABORATORS_FETCHED',
            allCollaborators: { user1: 'mike', user2: 'john', user3: 'lily' }
        };
        expect(reducer(initialState, action)).toEqual(expectedState);
    });

    it('should change the state for action type ALLDIRECTORS_FETCHED', () => {
        const expectedState = {
            allDirector: { user1: 'mike', user2: 'john', user3: 'lily' },
            error: undefined,
            usersUpdated: true
        };
        const action = {
            type: 'ALLDIRECTORS_FETCHED',
            allDirector: { user1: 'mike', user2: 'john', user3: 'lily' }
        };
        expect(reducer(initialState, action)).toEqual(expectedState);
    });

    it('should change the state for action type ALLADMINISTRATOR_FETCHED', () => {
        const expectedState = {
            allAdministrator: { user1: 'mike', user2: 'john', user3: 'lily' },
            error: undefined,
            usersUpdated: true
        };
        const action = {
            type: 'ALLADMINISTRATOR_FETCHED',
            allAdministrator: { user1: 'mike', user2: 'john', user3: 'lily' }
        };
        expect(reducer(initialState, action)).toEqual(expectedState);
    });

    it('should change the state for action type SEARCHUSERS_FETCHED', () => {
        const expectedState = {
            searchList: { user1: 'mike', user2: 'john', user3: 'lily' },
            error: undefined,
            usersUpdated: true
        };
        const action = {
            type: 'SEARCHUSERS_FETCHED',
            searchList: { user1: 'mike', user2: 'john', user3: 'lily' }
        };
        expect(reducer(initialState, action)).toEqual(expectedState);
    });

    it('should change the state for action type ALLVISITORS_FETCHED', () => {
        const expectedState = {
            allVisitors: { user1: 'mike', user2: 'john', user3: 'lily' },
            error: undefined,
            usersUpdated: true
        };
        const action = {
            type: 'ALLVISITORS_FETCHED',
            allVisitors: { user1: 'mike', user2: 'john', user3: 'lily' }
        };
        expect(reducer(initialState, action)).toEqual(expectedState);
    });
});
