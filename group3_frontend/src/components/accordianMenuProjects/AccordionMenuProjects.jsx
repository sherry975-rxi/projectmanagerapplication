import React, { Component } from 'react';
import { PanelGroup, Panel } from 'react-bootstrap';
import * as Constants from '../utils/titleConstants';
import SmallButton from '../button/smallButton.jsx';
import { handleProjectHeaders } from '../utils/handleList';
import '../accordianMenuTasks/AccordionMenuTasks.css';

class AccordionMenuProjects extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeKey: '1'
            // projectManagerId:
            //     handleProjectHeaders.project.project_manager_user_id,
            // projectManagerName: ''
        };
    }

    handleSelect(activeKey) {
        this.setState({ activeKey });
    }

    // fetchProjectManager() {
    //     this.AuthService.fetch(
    //         `/users/id/` + this.props.list.[].project_manager_user_id,
    //         {
    //             method: 'get'
    //         }
    //     ).then(responseData => {
    //         this.setState({
    //             projectManagerName: responseData[0]['name']
    //         });
    //     });
    // }

    renderTitles() {
        return Constants.PROJECTS.map((element, index) => (
            <th key={index}> {element}</th>
        ));
    }

    renderList(list) {
        let key = 1;
        return handleProjectHeaders(list).map(element => (
            <Panel eventKey={key}>
                <Panel.Heading>
                    <Panel.Title toggle>
                        <div className="projectContent">
                            <table className="table table-content">
                                <thead>
                                    <tr>
                                        {element.map(
                                            detail => (
                                                <th>
                                                    {' '}
                                                    {detail}{' '}
                                                    {console.log(detail)}
                                                </th>
                                            ),
                                            key++
                                        )}

                                        <th>
                                            <SmallButton text="Edit" />{' '}
                                        </th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                    </Panel.Title>
                </Panel.Heading>
                <Panel.Body collapsible>
                    <ul className="bodyContent">
                        {element.map(detail => <li> {detail} </li>, key++)}
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
        );
    }
}

export default AccordionMenuProjects;
