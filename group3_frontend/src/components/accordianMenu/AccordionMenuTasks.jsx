import React, { Component } from "react";
import { PanelGroup, Panel } from 'react-bootstrap';
import './AccordionMenuTasks.css'
import * as Constants from './titleConstants'
import SmallButton from '../button/smallButton.jsx';
import { handleTaskHeaders } from './handleList';

class AccordionMenu extends Component {

    constructor(props) {
        super(props);
        this.state = {
            activeKey: '1'
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

    renderList(list) {
        let key = 1;
        return (
            handleTaskHeaders(list).map((element) =>
                <Panel eventKey={key}>
                    <Panel.Heading>
                        <Panel.Title toggle><div className="taskContent"> <table className="table table-content">
                            <thead>
                                <tr>
                                    {element.map((detail) => <th> {detail} </th>, key++)}
                                    <th><SmallButton text="Edit" /> </th>
                                </tr>
                            </thead>
                        </table></div></Panel.Title>
                    </Panel.Heading>
                    <Panel.Body collapsible> <ul className="bodyContent">
                        {element.map((detail) => <li> {detail} </li>, key++)}
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