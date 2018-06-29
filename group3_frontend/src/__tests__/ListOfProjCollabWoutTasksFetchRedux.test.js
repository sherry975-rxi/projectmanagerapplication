import configureMockStore from 'redux-mock-store'
import thunk from 'redux-thunk'
import * as actions from '../actions/projCollabsWoutTasksActions';
import * as types from '../actions/actions';
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
      .getOnce('/projects/[projectId]/tasks/collabsAvailableForTask', {headers: { 'content-type': 'application/json' } })
    const expectedActions = [
      { type: types.COLLABWOUTTASKS_FETCHED, body: { collabs: ['do something'] } }
    ]
    const store = mockStore({ collabs: [] })
    return store.dispatch(actions.updateUnassignedProjCollabs(() => {
      // return of async actions
      expect(store.getActions()).toEqual(expectedActions)
    }))
  })
})