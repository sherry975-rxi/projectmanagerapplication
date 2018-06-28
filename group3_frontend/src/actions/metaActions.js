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

export function getTotalTimeSpentOnTasksLastMonth(userId) {
    return dispatch => {
        fetch(`/users/${userId}/tasks/totaltimespent/completed/lastmonth`, {
            headers: { Authorization: localStorage.getItem('id_token') },
            method: 'GET'
        })
            .then(responseData => responseData.json())
            .then(payload => {
                dispatch(totalTimeSpentOnTasksLastMonth(payload));
            })
            .catch(error => {});
    };
}

export function totalTimeSpentOnTasksLastMonth(payload) {
    return {
        type: TOTAL_TIME_SPENT_TASKS_LAST_MONTH,
        payload
    };
}
