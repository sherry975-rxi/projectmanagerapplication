import reducer from '../reducers/dependenciesReducer'

describe('DependenciesReducer', () => {
    it('should return the initial state', () => {
        expect(reducer(undefined, {})).toEqual({})
    })

    it('should handle CHILD TASK and return that task', () => {
        expect(reducer([] , {
            type: 'CHILD_TASK_FETCHED',
            child: {description: "test"}})
        ).toEqual({itemIsLoading: false, childTask: {description: "test"}, error: false })
    })

    it('should handle DEPENDENCIES FETCHED and return a list of tasks', () => {
        expect(reducer([] , {
            type: 'DEPENDENCIES_FETCHED',
            tasks: {description: "test", description2: "another test"}})
        ).toEqual({itemIsLoading: false, tasksDependencies: {description: "test", description2: "another test"}, error: false })
    })

    it('should handle POSSIBLE_DEPENDENCIES_FETCHED and return a list of tasks', () => {
        expect(reducer([] , {
            type: 'POSSIBLE_DEPENDENCIES_FETCHED',
            tasks: {description: "test", description2: "another test"}})
        ).toEqual({itemIsLoading: false, possibleDependencies: {description: "test", description2: "another test"}, error: false })
    })

    it('should handle FETCH_HAS_ERRORED and return the array empty', () => {
        expect(reducer([] , {
            type: 'FETCH_HAS_ERRORED'})
        ).toEqual({ itemIsLoading: false, error: true })
    })

})