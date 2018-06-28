import * as userTasksFilterActions from './userTasksFilterActions';
import AuthService from "../pages/loginPage/AuthService";


export function updateMyFinishedTasks(userId) {
    const authService = new AuthService();

    return dispatch => {
        myTasksLoading()
        authService.fetch(`/users/${userId}/tasks/finished`, {
            method: 'GET'
        }).then(data => {
            dispatch(myFinishedTasksFetched(data));
            dispatch(userTasksFilterActions.changeToMyFinished());
            return data;
        }).catch((error) => {
            console.log(error)
            myFetchTasksHasErrored();
        });
    };
}

export function updateMyOngoingTasks(userId) {
    const authService = new AuthService();

    return dispatch => {
        myTasksLoading()
        authService.fetch(`/users/${userId}/tasks/pending`, {
            method: 'GET'
        }).then(data => {
                dispatch(myOngoingTasksFetched(data));
                dispatch(userTasksFilterActions.changeToMyOnGoing())
                return data;
        }).catch((error) => {
            console.log(error)
            myFetchTasksHasErrored();
        });
    };
}

export function updateMyAllTasks(userId) {
    const authService = new AuthService();

    return dispatch => {
        myTasksLoading()
        authService.fetch(`/users/${userId}/tasks/`, {
            method: 'GET'
        }).then(data => {
                dispatch(myAllTasksFetched(data));
                dispatch(userTasksFilterActions.changeToMyAllTasks())
                return data;
        }).catch((error) => {
            console.log(error)
            myFetchTasksHasErrored();
        });
    };
}

export function updateMyLastMonthFinishedTasks(userId) {
    const authService = new AuthService();

    return dispatch => {
        myTasksLoading()
        authService.fetch(`/users/${userId}/tasks/lastmonthfinished`, {
            method: 'GET'
        }).then(data => {
                dispatch(lastMonthFinishedTasksFetched(data));
                dispatch(userTasksFilterActions.changeToMyLastMonthFinished())
                return data;
        }).catch((error) => {
            console.log(error)
            myFetchTasksHasErrored();
        });
    };
}



export function  searchList (event,list, option) {
    const authService = new AuthService();

        return dispatch => {
            var updatedList = searching(event, list, option)
            
                dispatch(searchListTasksFetched(updatedList));
                dispatch(userTasksFilterActions.changeToSearchTasks())
            
        };
    
}

export function searching (event, list, option) {
        var lista1 = list;
        console.log("MAIS UM")
        console.log(option)
        //if(list.length > 0){
            switch (option){
                case '1':
                console.log("escolhi opção 1")

                lista1 = lista1.filter(function(item){
                    return item.taskID.toLowerCase().search(
                      event.target.value.toLowerCase()) !== -1;
                  });
                  break;
                case '2':
                console.log("escolhi opção 2")
                lista1 = lista1.filter(function(item){
                    // JSON.stringify(item.project)
                    return item.project.projectId.toString().toLowerCase().search(
                      event.target.value.toLowerCase()) !== -1;
                  });
                  break;
                case '3':
                console.log("escolhi opção 3")
                lista1 = lista1.filter(function(item){
                    return item.description.toLowerCase().search(
                      event.target.value.toLowerCase()) !== -1;
                  });
                  break;
                case '4':
                lista1 = lista1.filter(function(item){
                    return item.currentState.toString().toLowerCase().search(
                      event.target.value.toLowerCase()) !== -1;
                  });
                  break;
                case '5':
                console.log("escolhi opção 5")
                lista1 = lista1.filter(function(item){
                    console.log(item.startDate);
                    return item.startDate.toString().toLowerCase().search(
                      event.target.value.toLowerCase()) !== -1;
                  });
                  break;
                case '6':
                console.log("escolhi opção 6")
                lista1 = lista1.filter(function(item){
                    return item.finishDate.toString().toLowerCase().search(
                      event.target.value.toLowerCase()) !== -1;
                  });
                  break;
                default:
                console.log("escolhi opção default")
                lista1 = lista1.filter(function(item){
                    return item.taskID.toLowerCase().search(
                      event.target.value.toLowerCase()) !== -1;
                  });
                
                
            }      
        
        console.log("LKJHGFDFGNMNBVCXSDERTH")
        console.log(lista1)
        return lista1;
      }


export function myFinishedTasksFetched(myFinishedTasks) {
    return {
        type: 'MYFINISHTASKS_FETCHED',
        myFinishedTasks
    };
}

export function myOngoingTasksFetched(myOngoingTasks) {
    return {
        type: 'MYONGOING_FETCHED',
        myOngoingTasks
    };
}

export function myAllTasksFetched(myAllTasks) {
    return {
        type: 'MYALLTASKS_FETCHED',
        myAllTasks
    };
}

export function lastMonthFinishedTasksFetched(lastMonthFinishedTasks) {
    return {
        type: 'LASTMONTHTASKS_FETCHED',
        lastMonthFinishedTasks
    };
}

export function searchListTasksFetched(updatedList) {
    return {
        type: 'SEARCHTASKS_FETCHED',
        updatedList
    };
}


export function myTasksLoading() {
    return {
        type: 'ITEM_LOADING'
    };
}

export function myFetchTasksHasErrored() {
    return {
        type: 'FETCH_HAS_ERRORED'
    };
}



