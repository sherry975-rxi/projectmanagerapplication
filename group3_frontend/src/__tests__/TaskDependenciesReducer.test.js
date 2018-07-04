import reducer from '../reducers/dependenciesReducer';

describe('DependenciesReducer', () => {
    it('should return the initial state', () => {
        expect(reducer(undefined, {})).toEqual({});
    });

    it('should handle CHILD TASK and return that task', () => {
        expect(
            reducer([], {
                type: 'CHILD_TASK_FETCHED',
                child: { description: 'test' }
            })
        ).toEqual({
            itemIsLoading: false,
            childTask: { description: 'test' },
            error: false
        });
    });

    it('should handle DEPENDENCIES FETCHED and return a list of tasks', () => {
        expect(
            reducer([], {
                type: 'DEPENDENCIES_FETCHED',
                tasks: { description: 'test', description2: 'another test' }
            })
        ).toEqual({
            itemIsLoading: false,
            tasksDependencies: {
                description: 'test',
                description2: 'another test'
            },
            error: false
        });
    });

    it('should handle POSSIBLE_DEPENDENCIES_FETCHED and return a list of tasks', () => {
        expect(
            reducer([], {
                type: 'POSSIBLE_DEPENDENCIES_FETCHED',
                tasks: { description: 'test', description2: 'another test' }
            })
        ).toEqual({
            itemIsLoading: false,
            possibleDependencies: {
                description: 'test',
                description2: 'another test'
            },
            error: false
        });
    });

    it('should change the state for action type ITEM_LOADING', () => {
        const expectedState = {
            itemIsLoading: true,
            error: false
        };
        const action = {
            type: 'ITEM_LOADING',
            itemIsLoading: true,
            error: false
        };
        expect(reducer({}, action)).toEqual(expectedState);
    });

    it('should change the state for action type DEPENDENCY_ERROR', () => {
        const expectedState = {
            itemIsLoading: false,
            error: true
        };
        const action = {
            type: 'DEPENDENCY_ERROR',
            itemIsLoading: false,
            error: true
        };
        expect(reducer({}, action)).toEqual(expectedState);
    });
});
