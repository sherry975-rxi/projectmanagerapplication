import * as requestsFilterActions from './requestsFilterActions';
import AuthService from "../pages/loginPage/AuthService";
import { isNullOrUndefined } from 'util';



export function getAllRequests(projectId){
    const authService = new AuthService();

    return dispatch => {
        requestsLoading()
        authService.fetch(`/projects/${projectId}/tasks/allRequests`, {
            method: 'get'
        }).then(data => {
            dispatch(allRequestsFetched(data));
            dispatch(requestsFilterActions.changeToAllRequests());
            console.log("Teste fetch dos requests");
            console.log(data);
            return data;
        }).catch((error) => {
            requestsFetchHasErrored();
        });
    };
}

/* export function getAllRequests1(projectId){
    const authService = new AuthService();
    var requests;
     
        authService.fetch(`/projects/${projectId}/tasks/allRequests`, {
            method: 'get'
        }).then(data => {
            console.log("fetch")

            console.log(data)
            requests = data;
        }).catch((error) => {
            requestsFetchHasErrored();
        });
    return requests;
} */

export function getOpenedRequests(projectId) {
 
    const authService = new AuthService();
    return dispatch => {
        authService.fetch(`/projects/${projectId}/tasks/allRequests`, {
            method: 'get'
        }).then(data => {
            console.log("fetch")
            console.log(data)
           
            dispatch(openedRequestsFetched(filterByOpen(data)));
            dispatch(requestsFilterActions.changeToOpenedRequests())
            
        }).catch((error) => {
            requestsFetchHasErrored();
        });

    };

}


/* export function getOpenedRequests1(projectId) {
    console.log("testando")
    console.log(projectId)
    var allRequests = getAllRequests1(projectId)

    return dispatch => {
        var openedList = filterByOpen(allRequests)

        dispatch(openedRequestsFetched(openedList));
        dispatch(requestsFilterActions.changeToOpenedRequests())

    };

} */

export function filterByOpen(list) {
    var newList = list
        //.filter((item, index) => (item.approvalDate === "" && item.rejectDate === ""))
        .filter((item, index) => (isNullOrUndefined(item.approvalDate) && isNullOrUndefined(item.rejectDate)))

        .map((item, index) => {
            return (
                item
            );
        });

        return newList;

/*     var newList = list.filter(function (item) {
        return item.approvalDate !== '' ? (item) : (null);
    })


    return newList; */
    
    
  /*   var newList = list;
    console.log("TESTEEEEEE::::::")
    console.log(list)
        newList = newList.filter(function (item) {
            return item.approvalDate !== null;
                
            // if(item.approvalDate === null && item.rejectDate === null){
            //     return item;}
            }); */


    
}

export function getClosedRequests(projectId) {
 
    const authService = new AuthService();
    return dispatch => {
        authService.fetch(`/projects/${projectId}/tasks/allRequests`, {
            method: 'get'
        }).then(data => {
            console.log("fetch")
            console.log(data)
           
            dispatch(closedRequestsFetched(filterByClose(data)));
            dispatch(requestsFilterActions.changeToClosedRequests())
            
        }).catch((error) => {
            requestsFetchHasErrored();
        });

    };

}



export function filterByClose(list) {
    var newList = list
        //.filter((item, index) => (item.approvalDate !== "" || item.rejectDate !== ""))
        .filter((item, index) => (!isNullOrUndefined(item.approvalDate) || !isNullOrUndefined(item.rejectDate)))

        .map((item, index) => {
            console.log("datas")
            console.log(item.approvalDate)
            return (
                item
            );
        });

        return newList;


       /*  var newList = list.filter(function (item) {
            return item.approvalDate === '' ? (item) : (null);
        })
    
    
        return newList; */

    
}



export function searchList(event, list, option) {


    return dispatch => {
        var searchList = searching(event, list, option)

        dispatch(searchListRequestsFetched(searchList));
        dispatch(requestsFilterActions.changeToSearchRequests())

    };

}

export function searching(event, list, option) {
    var filteredList = list;

    switch (option) {
        case '1':
            filteredList = filteredList.filter(function (item) {
                return item.task.taskID.toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
            });
            break;
        case '2':
            filteredList = filteredList.filter(function (item) {
                // JSON.stringify(item.project)
                return item.task.description.toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
            });
            break;
        case '3':
            filteredList = filteredList.filter(function (item) {
                return item.projCollab.collaborator.name.toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
            });
            break;
        case '4':
            filteredList = filteredList.filter(function (item) {
                return item.type.toString().toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
            });
            break;
        case '5':
            filteredList = filteredList.filter(function (item) {

                return item.approvalDate.toString().toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
            });
            break;
        case '6':

            filteredList = filteredList.filter(function (item) {
                return item.rejectDate.toString().toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
            });
            break;
        default:
            filteredList = filteredList.filter(function (item) {
                return item.taskID.toLowerCase().search(
                    event.target.value.toLowerCase()) !== -1;
            });


    }


    return filteredList;
}

export function searchListRequestsFetched(searchList) {
    return {
        type: 'SEARCH_REQUESTS_FETCHED',
        searchList
    };
}

export function allRequestsFetched(allRequests) {
    return {
        type: 'ALL_REQUESTS_FETCHED',
        allRequests
    };
}

export function openedRequestsFetched(openedRequests) {
    return {
        type: 'OPENED_REQUESTS_FETCHED',
        openedRequests
    };
}

export function closedRequestsFetched(closedRequests) {
    return {
        type: 'CLOSED_REQUESTS_FETCHED',
        closedRequests
    };
}


export function requestsLoading() {
    return {
        type: 'ITEM_LOADING'
    };
}

export function requestsFetchHasErrored() {
    return {
        type: 'FETCH_HAS_ERRORED'
    };
}



