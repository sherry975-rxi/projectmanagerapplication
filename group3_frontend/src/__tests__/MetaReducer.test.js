import { metaReducer } from '../reducers/metaReducer';
import {
    PROJECT_TABLE_DETAILS_TOOGLE,
    TOTAL_TIME_SPENT_TASKS_LAST_MONTH
} from '../actions/actions';

describe('MetaReducer', () => {
    it('should change the state for action type PROJECT_TABLE_DETAILS_TOOGLE', () => {
        const initialState = {
            projectsTableOpenIndex: 0
        };
        const expectedState = {
            projectsTableOpenIndex: 3
        };
        const action = {
            type: PROJECT_TABLE_DETAILS_TOOGLE,
            payload: { index: 3 }
        };
        expect(metaReducer(initialState, action)).toEqual(expectedState);
    });

    it('should change the state for action type TOTAL_TIME_SPENT_TASKS_LAST_MONTH', () => {
        const initialState = {
            totalTimeSpentOnTasksLastMonth: 0
        };
        const expectedState = {
            totalTimeSpentOnTasksLastMonth: 3
        };
        const action = {
            type: TOTAL_TIME_SPENT_TASKS_LAST_MONTH,
            payload: 3
        };
        expect(metaReducer(initialState, action)).toEqual(expectedState);
    });

    it('should return default state when no action or state is passed', () => {
        expect(metaReducer()).toEqual({});
    });
});
