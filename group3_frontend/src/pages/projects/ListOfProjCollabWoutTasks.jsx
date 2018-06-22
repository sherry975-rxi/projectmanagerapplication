import React, { Component } from 'react';
import { PanelGroup, Panel } from 'react-bootstrap';
import { connect } from 'react-redux';
import { updateFinishedTasks, updateAllTasks, updateStandByTasks, updateNotStartedTasks, updateUnfinishedTasks, addCollaboratorToTask } from '../../actions/projectTasksActions';
import { bindActionCreators } from 'redux';
import {
    MenuItem,
    DropdownButton,
    FormGroup,
    FormControl,
    ControlLabel
} from 'react-bootstrap';
import DropListTasksToAddToPCollab from './DropListTasksToAddToPCollab';

class ListOfProjCollabWoutTasks extends Component {

    constructor(props){
        super(props);
        this.state = {
            activeKey: '1'
        };

        this.ListOfCollabs = this.ListOfCollabs.bind(this);
    }

    
        ListOfCollabs() {
        console.log(this.taskList)
        console.log(this.props.unassignedCollabs)        
        return (this.props.unassignedCollabs.map((unassignedCollabsitem, index) => {  
            return(
            <Panel >
                <table className="table table-title">
                    <thead>
                        <tr><th>{unassignedCollabsitem.collaborator.name}</th>
                        <th>{unassignedCollabsitem.collaborator.email}</th>
                        <th>{unassignedCollabsitem.costPerEffort}</th>
                        <th><DropListTasksToAddToPCollab email = {unassignedCollabsitem.collaborator.email} projectID = {unassignedCollabsitem.project.projectId}/> </th></tr>
                    </thead>
                </table>
        </Panel>)}))
            
        }
    
        render() {
            return (
            <PanelGroup
            accordion
            className="accordion-menu-tasks"
            id="accordion-controlled-example"
        >
            <Panel >
                <table className="table table-title">
                    <thead>
                        {console.log("priemiro")}
                        <tr><th>Name</th>
                        <th>Email</th>
                        <th>Cost</th>
                        <th></th></tr>
                    </thead>
                </table>
            </Panel>
           
            {this.ListOfCollabs()}
            {console.log("segundo")}
        </PanelGroup>
            )}
    }


    const mapStateToProps = state => {
        return {
            unassignedCollabs: state.collabsWoutTasks.collabs,
            taskList: state.projectTasks.allTasks
        };
    };

    const mapDispatchToProps = dispatch => {
        return bindActionCreators(
            { updateAllTasks, addCollaboratorToTask },
            dispatch);
    };
   
    export default connect(mapStateToProps,mapDispatchToProps)(ListOfProjCollabWoutTasks);

