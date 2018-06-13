import React, { Component } from 'react';
import { PanelGroup, Panel } from 'react-bootstrap';
import { PROJECTS } from '../utils/titleConstants';
import SmallButton from '../button/smallButton.jsx';
import { handleProjectHeaders } from '../utils/handleList';
import '../accordianMenuTasks/AccordionMenuTasks.css';

class AccordionMenuProjects extends Component {
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
        return PROJECTS.map((element, index) => (
            <th key={index}> {element}</th>
        ));
    }

    renderList(list) {
        return handleProjectHeaders(list).map((element, index) => (
            <Panel eventKey={index} key={index}>
                <Panel.Heading>
                    <Panel.Title toggle>
                        <div className="projectContent">
                            <table className="table table-content">
                                <thead>
                                    <tr>
                                        {element.map((detail, idx) => (
                                            <th key={idx}>
                                                {detail} {console.log(detail)}
                                            </th>
                                        ))}

                                        <th>
                                            <SmallButton text="Edit" />
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr />
                                </tbody>
                            </table>
                        </div>
                    </Panel.Title>
                </Panel.Heading>
                <Panel.Body collapsible>
                    <ul className="bodyContent">
                        {element.map((detail, key) => (
                            <li key={key}> {detail} </li>
                        ))}
                    </ul>
                </Panel.Body>
            </Panel>
        ));
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
                            <tr>{this.renderTitles()}</tr>
                        </thead>
                    </table>
                </Panel>
                {this.renderList(this.props.list)}
            </PanelGroup>

            // <projectsTableRow project={this.props.projects[0]} />
        );
    }
}

export default AccordionMenuProjects;
