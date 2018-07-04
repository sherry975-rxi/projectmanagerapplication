import userTasksReducer from '../reducers/userTasksReducer';
import INITIAL_STATE from '../reducers/userTasksReducer';

describe('userTasksReducer', () => {
    it('should change the state for action type MYFINISHTASKS_FETCHED', () => {
        const expectedState = {
            error: false,
            itemIsLoading: false,
            myFinishedTasks: ['task1', 'task2']
        };
        const action = {
            type: 'MYFINISHTASKS_FETCHED',
            myFinishedTasks: ['task1', 'task2']
        };
        expect(userTasksReducer(INITIAL_STATE, action)).toEqual(expectedState);
    });

    it('should change the state for action type MYONGOING_FETCHED', () => {
        const expectedState = {
            error: false,
            itemIsLoading: false,
            myOngoingTasks: ['task1', 'task2']
        };
        const action = {
            type: 'MYONGOING_FETCHED',
            myOngoingTasks: ['task1', 'task2']
        };
        expect(userTasksReducer(INITIAL_STATE, action)).toEqual(expectedState);
    });

    it('should change the state for action type MYALLTASKS_FETCHED', () => {
        const expectedState = {
            error: false,
            itemIsLoading: false,
            myAllTasks: ['task1', 'task2']
        };
        const action = {
            type: 'MYALLTASKS_FETCHED',
            myAllTasks: ['task1', 'task2']
        };
        expect(userTasksReducer(INITIAL_STATE, action)).toEqual(expectedState);
    });

    it('should change the state for action type LASTMONTHTASKS_FETCHED', () => {
        const expectedState = {
            error: false,
            itemIsLoading: false,
            lastMonthFinishedTasks: ['task1', 'task2']
        };
        const action = {
            type: 'LASTMONTHTASKS_FETCHED',
            lastMonthFinishedTasks: ['task1', 'task2']
        };
        expect(userTasksReducer(INITIAL_STATE, action)).toEqual(expectedState);
    });

    it('should change the state for action type SEARCHTASKS_FETCHED', () => {
        const expectedState = {
            error: false,
            itemIsLoading: false,
            updatedList: ['task1', 'task2']
        };
        const action = {
            type: 'SEARCHTASKS_FETCHED',
            updatedList: ['task1', 'task2']
        };
        expect(userTasksReducer(INITIAL_STATE, action)).toEqual(expectedState);
    });

    it('should change the state for action type ITEM_LOADING', () => {
        const expectedState = {
            itemIsLoading: true
        };
        const action = {
            type: 'ITEM_LOADING',
            itemIsLoading: true
        };
        expect(userTasksReducer(INITIAL_STATE, action)).toEqual(expectedState);
    });

    it('should change the state for action type FETCH_HAS_ERRORED', () => {
        const expectedState = {
            error: true,
            itemIsLoading: false
        };
        const action = {
            type: 'FETCH_HAS_ERRORED',
            itemIsLoading: false,
            error: true
        };
        expect(userTasksReducer(INITIAL_STATE, action)).toEqual(expectedState);
    });

    it('should return default state when no action or state is passed', () => {
        const expectedState = {
            itemIsLoading: true,
            myFinishedTasks: [],
            myOngoingTasks: [],
            myAllTasks: [],
            lastMonthFinishedTasks: [],
            updatedList: [],
            error: false
        };

        expect(userTasksReducer(undefined, 'INVALID ACTION')).toEqual(
            expectedState
        );
    });
});
