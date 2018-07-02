import React, { Component } from 'react';
import './ProjectCost.css'
import { Glyphicon } from 'react-bootstrap'
import './ProjectCost.css'
import { connect } from 'react-redux';

class ProjectCostWidget extends Component {

    constructor() {
        super()
    }

    render() {
        return (
            <div className="bigBox">
                <div className="leftBox">
                    <div className="left-upperBox">   Selected Method</div>
                    <div className="left-lowerBox"><Glyphicon className="costIcon" glyph="
glyphicon glyphicon-hand-right" />   {this.props.calculationMethod}</div>
                </div>

                <div className="rightBox">
                    <div className="right-upperBox">Project Cost</div>
                    <div className="right-lowerBox"><Glyphicon className="costIcon" glyph="glyphicon glyphicon-hand-right" />   {this.props.projectCost}</div>
                </div>
            </div>
        )
    }

}

const mapStateToProps = state => {
    return {
        projectCost: state.projects.projectCost,
        calculationMethod: state.projects.calculationMethod
    };
};

export default connect(mapStateToProps,null)(ProjectCostWidget);

