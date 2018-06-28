import React, { Component } from 'react';
import './StatsWidgetsContainer.css';
import StatsNumberWidget from '../StatsNumberWidget/StatsNumberWidget';

class StatsWidgetsContainer extends Component {
    renderWidgets = () => {
        return this.props.data.map((element, index) => {
            return (
                <StatsNumberWidget
                    key={index}
                    value={element.value}
                    title={element.title}
                    unit={element.unit}
                />
            );
        });
    };

    render() {
        return <div className="flex-container">{this.renderWidgets()}</div>;
    }
}

export default StatsWidgetsContainer;
