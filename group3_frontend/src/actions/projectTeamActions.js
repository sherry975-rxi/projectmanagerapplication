import AuthService from "../pages/loginPage/AuthService";

export function updateProjectTeam(projectID){

    return dispatch =>{
        const authService = new AuthService();
        authService.fetch(
            `/projects/${projectID}/team/`,
            { method: 'get' }
        ).then(data => {
            dispatch(projectTeamFetched(data));
            }
        )
    };
}

export function projectTeamFetched(projectTeam){
    return{
        type: 'PROJECT_TEAM_FETCHED',
        projectTeam
    }
}