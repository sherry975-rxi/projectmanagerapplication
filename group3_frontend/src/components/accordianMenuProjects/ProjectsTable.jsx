import React, {Component} from 'react';
import { PROJECTS } from '../utils/titleConstants';
import './Accordian.css';
import { handleProject } from '../utils/handleList';

class ProjectTable extends Component {
    
    renderTitles() {
        return PROJECTS.map((element, index) => (
            <th className='rowAccordian' key={index}> {element}</th>
        ));
    }

    render() { 
        return ( 
    <table>
        <thead className='tableHeadAccordian'>
            <tr className=''>
                {this.renderTitles()}
            </tr>
        </thead>
        <tbody>
            {/* {handleProject(this.props.projects).map((project, index) =>

                <ProjectsTableRow key={index} project= {project} /> )
            } */}
        </tbody>
    </table> 
        )
    }
}
 
export default ProjectTable;