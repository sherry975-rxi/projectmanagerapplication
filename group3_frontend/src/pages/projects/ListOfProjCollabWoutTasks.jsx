import React, { Component } from 'react';
import { PanelGroup, Panel, MenuItem } from 'react-bootstrap';
import { connect } from 'react-redux';


class ListOfProjCollabWoutTasks extends Component {

    constructor(props){
        super(props);
        this.ListOfCollabs = this.ListOfCollabs.bind(this);
        this.state = {
            activeKey: '1'
        };
    }

    
        ListOfCollabs() {

        console.log(this.props.unassignedCollabs)        
        return (this.props.unassignedCollabs.map((unassignedCollabsitem, index) => {  
            return(
            <Panel >
                <table className="table table-title">
                    <thead>
                        <tr><th>{unassignedCollabsitem.collaborator.name}</th>
                        <th>{unassignedCollabsitem.collaborator.email}</th>
                        <th>{unassignedCollabsitem.costPerEffort}</th>
                        <th></th></tr>
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
                        <tr><th>Name</th>
                        <th>Email</th>
                        <th>Cost</th>
                        <th></th></tr>
                    </thead>
                </table>
            </Panel>
            {this.ListOfCollabs()}
        </PanelGroup>
            )}
    }

    const mapStateToProps = state => {
        return {
            unassignedCollabs: state.collabsWoutTasks.collabs
        };
    };
    
    export default connect(mapStateToProps,null)(ListOfProjCollabWoutTasks);

