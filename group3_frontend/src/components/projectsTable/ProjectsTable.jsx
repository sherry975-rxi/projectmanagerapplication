import React, { Component } from 'react';
import { PROJECTS } from '../utils/titleConstants';
import './ProjectsTable.css';
import { handleProject } from '../utils/handleList';
import ProjectsTableRow from './ProjectsTableRow';

class ProjectTable extends Component {
    constructor(props) {
        super(props);
    }

    renderTitles() {
        return PROJECTS.map((element, index) => (
            <th className="project-row" key={index}>
                {element}
            </th>
        ));
    }

    render() {
        return (
            <table className="table table-content project-table">
                <thead>
                    <tr>{this.renderTitles()}</tr>
                </thead>
                <tbody>
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
