import * as actions from '../actions/dependencyActions';

describe('actions', () => {
    it('should create an action to return the selected Child Task', () => {

        const text = 'Child Task Fetched'
        const expectedAction = {
            type: 'CHILD_TASK_FETCHED',
            child: text
        }
        expect(actions.taskFetched(text)).toEqual(expectedAction)
    })
})

describe('actions', () => {
    it('should create an action to return the selected Task dependencies', () => {

        const text = 'Dependencies Fetched'
        const expectedAction = {
            type: 'DEPENDENCIES_FETCHED',
            tasks: text
        }
        expect(actions.taskDependenciesFetched(text)).toEqual(expectedAction)
    })
})

describe('actions', () => {
    it('should create an action to return the possible dependencies for selected Task', () => {

        const text = 'Possible Dependencies Fetched'
        const expectedAction = {
            type: 'POSSIBLE_DEPENDENCIES_FETCHED',
            tasks: text
        }
        expect(actions.possibleTaskDependenciesFetched(text)).toEqual(expectedAction)
    })
})

describe('actions', () => {
    it('should create an action when Loading Tasks', () => {

        const expectedAction = {
            type: 'ITEM_LOADING'
        }
        expect(actions.tasksLoading()).toEqual(expectedAction)
    })
})

describe('actions', () => {
    it('should create an action when there is a Fetch Error', () => {

        const expectedAction = {
            type: 'FETCH_HAS_ERRORED'
        }
        expect(actions.fetchTasksHasErrored()).toEqual(expectedAction)
    })
})