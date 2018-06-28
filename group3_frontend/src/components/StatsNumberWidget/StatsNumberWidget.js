import React, { Component } from 'react';
import './StatsNumberWidget.css';

class StatsNumberWidget extends Component {
    state = {};
    render() {
        return (
            <div className="number-widget">
                <h1 className="widget-title">{this.props.title}</h1>
                <div className="widget-values">
                    <span className="widget-value">{this.props.value}</span>
                    <span className="widget-unit">{this.props.unit}</span>
                </div>
            </div>
        );
    }
}

export default StatsNumberWidget;
