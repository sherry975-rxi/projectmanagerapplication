import reducer from '../reducers/projCollabsWoutTasksReducer'
import * as types from '../actions/actions'

describe('UserReducer', () => {
  it('should return the initial state', () => {
    expect(reducer(undefined, {})).toEqual(
      {
        itemIsLoading: true,
        collabs: [],
        error: false
      }
    )
  })

  it('should handle ALL USERS and return the array full', () => {
    expect(reducer([] , {        
        type: types.COLLABWOUTTASKS_FETCHED,
        collabs: {user1: 'mike',user2: 'john',user3: 'lily'}})
    ).toEqual({collabs: {user1: 'mike',user2: 'john',user3: 'lily'}, error: false, itemIsLoading: false})
})

it('should handle ALL COLLABORATORS and return the array empty', () => {
    expect(reducer([] , {        
        type: types.ITEMCOLLABSWOUTTASKS_LOADING,
        collabs: {user1: 'mike',user2: 'john',user3: 'lily'}})
    ).toEqual({collabs: {user1: 'mike',user2: 'john',user3: 'lily'}, error: false, itemIsLoading: true})
})

it('should handle ALL USERS and return the array empty', () => {
    expect(reducer([] , {        
        type: types.FETCH_HAS_ERRORED})
    ).toEqual({error: true, itemIsLoading: false})
})

})