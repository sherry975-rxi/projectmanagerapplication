import * as actions from '../actions/UserActions';
import * as types from '../actions/actions';

describe('actions', () => {
  it('should create an action to return all users', () => {
    
    const text = 'All Users'
    const expectedAction = {
      type: types.ALLUSERS_FETCHED,
      allUsers: text
    }
    expect(actions.allUsersFetched(text)).toEqual(expectedAction)
  })
})

describe('actions', () => {
  it('should create an action to return all collaborators', () => {
    
    const text = 'All Collaborators'
    const expectedAction = {
      type: types.ALLCOLLABORATORS_FETCHED,
      allCollaborators: text
    }
    expect(actions.allCollaboratorsFetched(text)).toEqual(expectedAction)
  })
})

describe('actions', () => {
  it('should create an action to return all directors', () => {
    
    const text = 'All Directors'
    const expectedAction = {
      type: types.ALLDIRECTORS_FETCHED,
      allDirector: text
    }
    expect(actions.allDirectorFetched(text)).toEqual(expectedAction)
  })
})

describe('actions', () => {
  it('should create an action to return all administrators', () => {
    
    const text = 'All Administrators'
    const expectedAction = {
      type: types.ALLADMINISTRATOR_FETCHED,
      allAdministrator: text
    }
    expect(actions.allAdministratorFetched(text)).toEqual(expectedAction)
  })
})

describe('actions', () => {
  it('should create an action to return all visitors', () => {
    
    const text = 'All Visitors'
    const expectedAction = {
      type: types.ALLVISITORS_FETCHED,
      allVisitors: text
    }
    expect(actions.allVisitorsFetched(text)).toEqual(expectedAction)
  })
})