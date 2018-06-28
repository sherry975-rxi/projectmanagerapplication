import * as projectActions from '../actions/projectTasksActions';
import {GET_AVAILABLE_COLLABORATORS_FOR_TASK_FULLFIELD} from "../actions/actions";

describe('project task actions', () => {
    it('should create an action to fetch all finished tasks', () => {
        const result = 'finished.tasks';
        const expectedAction = {
            type: 'FINISHTASKS_FETCHED',
            tasks: result
        }
            expect(projectActions.finishTasksFetched(result)).toEqual(expectedAction)
        })

    it('should create an action to fetch available collaborators for task', () => {
        const result = {};
        result[2] = 'test';
        const expectedAction = {
            type: GET_AVAILABLE_COLLABORATORS_FOR_TASK_FULLFIELD,
            payload: result
        };
        expect(projectActions.getAvailableCollaboratorsForTaskFullfield('test', 2)).toEqual(expectedAction)
    })


})