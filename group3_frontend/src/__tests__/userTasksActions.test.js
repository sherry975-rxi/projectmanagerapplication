import {
    myFetchTasksHasErrored,
    myTasksLoading,
    searchListTasksFetched,
    lastMonthFinishedTasksFetched,
    myAllTasksFetched,
    myOngoingTasksFetched,
    myFinishedTasksFetched
} from '../actions/userTasksActions';

describe('userTasksActions', () => {
    const payload = 'payload';

    it('should return a valid FETCH_HAS_ERRORED action', () => {
        const expected = {
            type: 'FETCH_HAS_ERRORED'
        };
        expect(myFetchTasksHasErrored()).toEqual(expected);
    });

    it('should return a valid ITEM_LOADING action', () => {
        const expected = {
            type: 'ITEM_LOADING'
        };
        expect(myTasksLoading()).toEqual(expected);
    });

    it('should return a valid SEARCHTASKS_FETCHED action', () => {
        const expected = {
            type: 'SEARCHTASKS_FETCHED',
            updatedList: 'payload'
        };
        expect(searchListTasksFetched(payload)).toEqual(expected);
    });

    it('should return a valid LASTMONTHTASKS_FETCHED action', () => {
        const expected = {
            type: 'LASTMONTHTASKS_FETCHED',
            lastMonthFinishedTasks: 'payload'
        };
        expect(lastMonthFinishedTasksFetched(payload)).toEqual(expected);
    });

    it('should return a valid MYALLTASKS_FETCHED action', () => {
        const expected = {
            type: 'MYALLTASKS_FETCHED',
            myAllTasks: 'payload'
        };
        expect(myAllTasksFetched(payload)).toEqual(expected);
    });

    it('should return a valid MYONGOING_FETCHED action', () => {
        const expected = {
            type: 'MYONGOING_FETCHED',
            myOngoingTasks: 'payload'
        };
        expect(myOngoingTasksFetched(payload)).toEqual(expected);
    });

    it('should return a valid MYFINISHTASKS_FETCHED action', () => {
        const expected = {
            type: 'MYFINISHTASKS_FETCHED',
            myFinishedTasks: 'payload'
        };
        expect(myFinishedTasksFetched(payload)).toEqual(expected);
    });
});
