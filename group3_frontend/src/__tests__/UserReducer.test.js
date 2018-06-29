import reducer from '../reducers/UserReducers'
import * as types from '../actions/actions'

describe('UserReducer', () => {
  it('should return the initial state', () => {
    expect(reducer(undefined, {})).toEqual(
      {
        usersUpdated: false,
        allUsers: [],
        emailUsers: [],
        allCollaborators: [],
        allDirector: [],
        allAdministrator: [],
        searchList: [],
        allVisitors:[]
      }
    )
  })

  it('should handle ALL USERS and return the array empty', () => {
    expect(reducer([] , {        
        type: types.ALLUSERS_FETCHED,
        allUsers: {user1: 'mike',user2: 'john',user3: 'lily'}})
    ).toEqual({allUsers: {user1: 'mike',user2: 'john',user3: 'lily'}, error: undefined, usersUpdated: true})
})
})

