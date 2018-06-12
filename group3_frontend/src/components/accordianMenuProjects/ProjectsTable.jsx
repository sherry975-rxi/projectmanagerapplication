import React, { Component } from 'react';
import { PROJECTS } from '../utils/titleConstants';
import './Accordian.css';
import { handleProject } from '../utils/handleList';
import ProjectsTableRow from './ProjectsTableRow';

class ProjectTable extends Component {
    constructor(props) {
        super(props);
    }

    renderTitles() {
        return PROJECTS.map((element, index) => (
            <th className="rowAccordian" key={index}>
                {' '}
                {element}
            </th>
        ));
    }

    render() {
        return (
            <table>
                <thead className="tableHeadAccordian">
                    <tr className="">{this.renderTitles()}</tr>
                </thead>
                <tbody className="tableHeadAccordian">
                    {console.log(this.props.projects)}
                    {handleProject(this.props.projects).map(
                        (project, index) => (
                            <ProjectsTableRow key={index} project={project} />
                        )
                    )}
                </tbody>
            </table>
        );
    }
}

export default ProjectTable;
