import * as actions from '../actions/projectTasksActions';
import * as types from '../actions/actions';

describe('actions', () => {
  it('should create an action to return all finished tasks', () => {
    
    const text = 'All Finished'
    const expectedAction = {
      type: types.FINISHTASKS_FETCHED,
      tasks: text
    }
    expect(actions.finishTasksFetched(text)).toEqual(expectedAction)
  })
})

describe('actions', () => {
    it('should create an action to return all unfinished tasks', () => {
      
      const text = 'All Unfinished'
      const expectedAction = {
        type: types.UNFINISHEDTASKS_FETCHED,
        tasks: text
      }
      expect(actions.unfinishedTasksFetched(text)).toEqual(expectedAction)
    })
  })

  describe('actions', () => {
    it('should create an action to return all standby tasks', () => {
      
      const text = 'All Standby'
      const expectedAction = {
        type: types.STANDBYTASKS_FETCHED,
        tasks: text
      }
      expect(actions.standByTasksFetched(text)).toEqual(expectedAction)
    })
  })

  describe('actions', () => {
    it('should create an action to return all not started tasks', () => {
      
      const text = 'All NotStarted'
      const expectedAction = {
        type: types.NOTSTARTED_FETCHED,
        tasks: text
      }
      expect(actions.notStartedTasksFetched(text)).toEqual(expectedAction)
    })
  })

  describe('actions', () => {
    it('should create an action to return all not expired tasks', () => {
      
      const text = 'All Expired'
      const expectedAction = {
        type: types.EXPIRED_FETCHED,
        tasks: text
      }
      expect(actions.expiredTasksFetched(text)).toEqual(expectedAction)
    })
  })

  
  describe('actions', () => {
    it('should create an action to return all tasks', () => {
      
      const text = 'All Tasks'
      const expectedAction = {
        type: types.ALLTASKS_FETCHED,
        tasks: text
      }
      expect(actions.allTasksFetched(text)).toEqual(expectedAction)
    })
  })

  describe('actions', () => {
    it('should create an action to return all project tasks', () => {
      
      const text = 'All Project Tasks'
      const expectedAction = {
        type: types.ALL_PROJECT_TASKS_FETCHED,
        tasks: text
      }
      expect(actions.allProjectTasksFetched(text)).toEqual(expectedAction)
    })
  })
 
  describe('actions', () => {
    it('should create an action to return all cancelled tasks', () => {
      
      const text = 'All Cancelled Tasks'
      const expectedAction = {
        type: types.CANCELLED_FETCHED,
        tasks: text
      }
      expect(actions.cancelledTasksFetched(text)).toEqual(expectedAction)
    })
  })

  describe('actions', () => {
    it('should create an action to return all dependencies tasks', () => {
      
      const text = 'All Dependencies Tasks'
      const expectedAction = {
        type: types.DEPENDENCIES_FETCHED,
        tasks: text
      }
      expect(actions.taskDependenciesFetched(text)).toEqual(expectedAction)
    })
  })

  describe('actions', () => {
    it('should create an action to change tasks filter', () => {
      
      const text = 'All Tasks Filtered'
      const expectedAction = {
        type: types.CHANGE_TASK_FILTER,
        filter: text
      }
      expect(actions.changeTaskFilter(text)).toEqual(expectedAction)
    })
  })
 



