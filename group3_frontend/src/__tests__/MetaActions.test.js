import {
    projectTableDetailsToogle,
    totalTimeSpentOnTasksLastMonth,
    getTotalTimeSpentOnTasksLastMonth
} from '../actions/metaActions';
import {
    PROJECT_TABLE_DETAILS_TOOGLE,
    TOTAL_TIME_SPENT_TASKS_LAST_MONTH
} from '../actions/actions';

describe('MetaActions', () => {
    it('should return a valid PROJECT_TABLE_DETAILS_TOOGLE action', () => {
        const payload = 'payload';
        const expected = {
            type: PROJECT_TABLE_DETAILS_TOOGLE,
            payload
        };
        expect(projectTableDetailsToogle(payload)).toEqual(expected);
    });

    it('should return a valid TOTAL_TIME_SPENT_TASKS_LAST_MONTH action', () => {
        const payload = 'payload';
        const expected = {
            type: TOTAL_TIME_SPENT_TASKS_LAST_MONTH,
            payload
        };
        expect(totalTimeSpentOnTasksLastMonth(payload)).toEqual(expected);
    });

    it('should dispatch totalTimeSpentOnTasksLastMonth when getTotalTimeSpentOnTasksLastMonth is called', () => {
        const fetchResult = {
            json: jest.fn().mockReturnValue('result')
        };
        global.fetch = jest
            .fn()
            .mockImplementation(() => Promise.resolve(fetchResult));

        const dispatchMock = jest.fn();

        getTotalTimeSpentOnTasksLastMonth('user1')(dispatchMock).then(() => {
            expect(dispatchMock).toHaveBeenCalledWith({
                type: TOTAL_TIME_SPENT_TASKS_LAST_MONTH,
                payload: 'result'
            });
        });

        expect(global.fetch).toHaveBeenCalledWith(
            '/users/user1/tasks/totaltimespent/completed/lastmonth',
            { headers: { Authorization: undefined }, method: 'GET' }
        );
    });
});
