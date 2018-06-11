import React, { Component } from "react";
import { PanelGroup, Panel } from 'react-bootstrap';
import './AccordionMenuTasks.css'
import * as Constants from './titleConstants'
import SmallButton from '../button/smallButton.jsx';
import { handleTaskHeaders } from './handleList';
import MarkTaskAsFinished from './../../pages/tasks/MarkTaskAsFinished';

class AccordionMenu extends Component {

    constructor(props) {
        super(props);
        this.state = {
            activeKey: '1',
            type: 'Ongoing'
        };
    }

    handleSelect(activeKey) {
        this.setState({ activeKey });
    }

    renderTitles() {
        return (
            Constants.TASKS.map((element) => <th> {element}</th>
            ))
    }

    static getDerivedStateFromProps(props, prevState) {
        let newState = { type: props }
        return newState ? props : prevState
    }

    renderList(list, type) {
        console.log(this.state.type)

        let key = 1;
        return (
            handleTaskHeaders(list).map((element) =>
                <Panel eventKey={key}>
                    <Panel.Heading>
                        <Panel.Title toggle><div className="taskContent"> <table className="table table-content">
                            <thead>
                                <tr>
                                    <th> {element.taskID} </th>
                                    <th> {element.project} </th>
                                    <th> {element.description} </th>
                                    <th> <b>{element.state}</b> </th>
                                    <th> {element.startDate} </th>
                                    <th> {this.state.type == 'Ongoing' ? <MarkTaskAsFinished
                                        id={element.taskID}
                                        project={element.project}
                                    /> : ''}
                                    </th>
                                </tr>
                            </thead>
                        </table></div></Panel.Title>
                    </Panel.Heading>
                    <Panel.Body collapsible> <ul className="bodyContent">

                    </ul></Panel.Body>
                </Panel>
            )
        )
    }

    render() {

        return (
            <PanelGroup
                accordion
                id="accordion-controlled-example"
                activeKey={this.state.activeKey}
                onSelect={this.handleSelect.bind(this)}
            >
                <Panel eventKey="1">
                    <table className="table table-title">
                        <thead>
                            <tr>
                                {this.renderTitles()}
                            </tr>
                        </thead>
                    </table>
                </Panel>
                {this.renderList(this.props.list)}
            </PanelGroup>
        );
    }
}

export default AccordionMenu;