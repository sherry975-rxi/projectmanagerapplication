import React, { Component } from 'react';
import { PROJECTS } from '../utils/titleConstants';
import './ProjectsTable.css';
import { handleProject } from '../utils/handleList';
import ProjectsTableRow from './ProjectsTableRow';
import { connect } from 'react-redux';

class ProjectsTable extends Component {
    renderTitles() {
        return PROJECTS.map((element, index) => (
            <th key={index}>
                <b>{element}</b>
            </th>
        ));
    }

    render() {
        return (
            <div>
                <h3>
                    <b>My Projects</b>
                </h3>
                <table className="table table-content project-table">
                    <thead>
                        <tr>{this.renderTitles()}</tr>
                    </thead>
                    <tbody>
                        {handleProject(this.props.projects).map(
                            (project, index) => (
                                <ProjectsTableRow
                                    openIndex={this.props.openIndex}
                                    key={index}
                                    index={index}
                                    project={project}
                                />
                            )
                        )}
                    </tbody>
                </table>
            </div>
        );
    }
}

export const mapStateToProps = state => {
    const openIndex = state.meta.projectsTableOpenIndex;
    return { openIndex };
};

export default connect(mapStateToProps)(ProjectsTable);
