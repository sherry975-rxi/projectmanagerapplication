import React, { Component } from 'react';
import { PanelGroup, Panel } from 'react-bootstrap';
import { connect } from 'react-redux';
import {
    updateAllTasks,
    addCollaboratorToTask
} from '../../actions/projectTasksActions';
import { bindActionCreators } from 'redux';
import DropListTasksToAddToPCollab from './DropListTasksToAddToPCollab';
import { get } from 'lodash';

class ListOfProjCollabWoutTasks extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeKey: '1'
        };

        this.ListOfCollabs = this.ListOfCollabs.bind(this);
    }

    ListOfCollabs() {
        return this.props.unassignedCollabs.map(
            (unassignedCollabsitem, index) => {
                return (
                    <Panel>
                        <table className="table table-title">
                            <thead>
                                <tr>
                                    <th>
                                        {
                                            unassignedCollabsitem.collaborator
                                                .name
                                        }
                                    </th>
                                    <th>
                                        {
                                            unassignedCollabsitem.collaborator
                                                .email
                                        }
                                    </th>
                                    <th>
                                        {unassignedCollabsitem.costPerEffort}
                                    </th>
                                    <th>
                                        <DropListTasksToAddToPCollab
                                            email={
                                                unassignedCollabsitem
                                                    .collaborator.email
                                            }
                                            projectID={
                                                unassignedCollabsitem.project
                                                    .projectId
                                            }
                                        />{' '}
                                    </th>
                                </tr>
                            </thead>
                        </table>
                    </Panel>
                );
            }
        );
    }

    render() {
        return (
            <PanelGroup
                accordion
                className="accordion-menu-tasks"
                id="accordion-controlled-example"
            >
                <Panel>
                    <table className="table table-title">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Cost</th>
                                <th />
                            </tr>
                        </thead>
                    </table>
                </Panel>

                {this.ListOfCollabs()}
            </PanelGroup>
        );
    }
}

const mapStateToProps = state => {
    return {
        unassignedCollabs: state.collabsWoutTasks.collabs,
        taskList: get(state, 'tasks.tasksList', [])
    };
};

const mapDispatchToProps = dispatch => {
    return bindActionCreators(
        { updateAllTasks, addCollaboratorToTask },
        dispatch
    );
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ListOfProjCollabWoutTasks);
