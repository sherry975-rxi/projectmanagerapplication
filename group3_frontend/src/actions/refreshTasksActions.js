import {
    updateMyAllTasks, updateMyFinishedTasks, updateMyLastMonthFinishedTasks,
    updateMyOngoingTasks
} from "./userTasksActions";
import {
    getAllProjectTasks,
    updateCancelledTasks, updateExpiredTasks, updateFinishedTasks, updateNotStartedTasks, updateStandByTasks,
    updateUnfinishedTasks
} from "./projectTasksActions";
import AuthService from "../pages/loginPage/AuthService";



export const refreshTasksByFilter = (projectId, filterName) => {
    const authService = new AuthService();

    switch (filterName) {
        case 'all':
            return getAllProjectTasks(projectId);
        case 'unfinished':
            return updateUnfinishedTasks(projectId);
        case 'finished':
            return updateFinishedTasks(projectId);
        case 'withoutCollaborators':
            return updateStandByTasks(projectId);
        case 'notstarted':
            return updateNotStartedTasks(projectId);
        case 'expired':
            return updateExpiredTasks(projectId);
        case 'cancelled':
            return updateCancelledTasks(projectId);
        case 'myAll':
            return updateMyAllTasks(authService.getUserId());
        case 'myUnfinished':
            return updateMyOngoingTasks(authService.getUserId());
        case 'myFinished':
            return updateMyFinishedTasks(authService.getUserId());
        case 'lastMonthFinished':
            return updateMyLastMonthFinishedTasks(authService.getUserId());
        default:
            return getAllProjectTasks(projectId);
    }
};