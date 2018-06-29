import * as actions from '../actions/projCollabsWoutTasksActions';
import * as types from '../actions/actions';

describe('actions', () => {
  it('should create an action to return Project Collaborators without Tasks', () => {
    
    const text = 'Project Collaborators without Tasks'
    const expectedAction = {
      type: types.COLLABWOUTTASKS_FETCHED,
      collabs: text
    }
    expect(actions.projCollabWoutTasksFetched(text)).toEqual(expectedAction)
  })
})

describe('actions', () => {
    it('should create an action when Loading Project', () => {
      
      const expectedAction = {
        type: types.ITEMCOLLABSWOUTTASKS_LOADING
      }
      expect(actions.listIsLoading()).toEqual(expectedAction)
    })
})

describe('actions', () => {
    it('should create an action when there is a Fetch Error', () => {
      
      const expectedAction = {
        type: types.FETCH_HAS_ERRORED
      }
      expect(actions.fetchCollabsWoutTasksHasErrored()).toEqual(expectedAction)
    })
})