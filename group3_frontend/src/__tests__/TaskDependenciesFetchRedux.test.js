import configureMockStore from 'redux-mock-store'
import thunk from 'redux-thunk'
import * as actions from '../actions/dependencyActions';
import fetchMock from 'fetch-mock'
import expect from 'jest' // You can use any testing library

const middlewares = [thunk];
const mockStore = configureMockStore(middlewares);

describe('async actions', () => {
    afterEach(() => {
        fetchMock.reset()
        fetchMock.restore()
    })
    it('creates COLLABWOUTTASKS_FETCHED when fetching collabs has been done', () => {
        fetchMock.spy()
            .getOnce('/projects/[projectId]/tasks/[taskId]/dependencies', {headers: { 'content-type': 'application/json' } })
        const expectedActions = [
            { type: 'DEPENDENCIES_FETCHED', body: { tasks: ['do something'] } }
        ]
        const store = mockStore({ tasks: [] })
        return store.dispatch(actions.getAllTaskDependencies(() => {
            // return of async actions
            expect(store.getActions()).toEqual(expectedActions)
        }))
    })
})