import configureMockStore from 'redux-mock-store'
import thunk from 'redux-thunk'
import * as actions from '../actions/UserActions';
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
it('creates ALLUSERS_FETCHED when fetching allUsers has been done', () => {
    fetchMock
      .getOnce('/users/allUsers', {headers: { 'content-type': 'application/json' } })
    const expectedActions = [
      { type: types.ALLUSERS_FETCHED, body: { allUsers: ['do something'] } }
    ]
    const store = mockStore({ allUsers: [] })
    return store.dispatch(actions.updateAllUsers(() => {
      // return of async actions
      expect(store.getActions()).toEqual(expectedActions)
    }))
  })
})