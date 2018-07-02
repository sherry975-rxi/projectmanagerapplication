import {
    PROJECT_TABLE_DETAILS_TOOGLE,
    TOTAL_TIME_SPENT_TASKS_LAST_MONTH
} from './actions';

export const projectTableDetailsToogle = payload => {
    return {
        type: PROJECT_TABLE_DETAILS_TOOGLE,
        payload
    };
};

export const getTotalTimeSpentOnTasksLastMonth = userId => {
    return dispatch => {
        return fetch(
            `/users/${userId}/tasks/totaltimespent/completed/lastmonth`,
            {
                headers: { Authorization: localStorage.getItem('id_token') },
                method: 'GET'
            }
        )
            .then(responseData => responseData.json())
            .then(payload => {
                dispatch(totalTimeSpentOnTasksLastMonth(payload));
            });
    };
};

export function totalTimeSpentOnTasksLastMonth(payload) {
    return {
        type: TOTAL_TIME_SPENT_TASKS_LAST_MONTH,
        payload
    };
}
