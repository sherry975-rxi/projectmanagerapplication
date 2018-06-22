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

class DropListTasksToAddToPCollab extends Component {

    constructor(props){
        super(props);

        this.handleClick = this.handleClick.bind(this)
        this.handleSelect = this.handleSelect.bind(this)
    }

    handleClick(event){
        console.log(event.target.value)
                this.props.updateAllTasks(event.target.value)  
            }

    handleSelect = ( eventKey) => {
        console.log("emailemail")
        console.log(this.props.email)
            const collaboratorIndex = eventKey;
            const userDTO = {
                    email: this.props.email
                };
                this.props.addCollaboratorToTask(this.props.projectID, this.props.taskList[eventKey].taskID, userDTO);
            };
    

    renderDropdownButton(title, i) {
        const taskList = this.props.taskList || [];
        return (
            <DropdownButton
                className="buttonFinished"
                title={title}
                key={i}
                id={`dropdown-basic-${i}`}
                value = {this.props.projectID}
                onClick = {this.handleClick}>
                {taskList.map((listItem, index) => (
                    <MenuItem
                        eventKey={index}
                        key={index}
                        
                        onSelect={this.handleSelect}
                    >
                        {listItem.taskID}
                    </MenuItem>
                ))}
            </DropdownButton>
        );
    }
    
    render() {
            return (
            <td>{this.renderDropdownButton('add to Task', 0)}</td>
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
   
    export default connect(mapStateToProps,mapDispatchToProps)(DropListTasksToAddToPCollab);

