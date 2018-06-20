import React, { Component } from 'react';

class RemoveTaskCollaborator extends Component {
    constructor(props) {
        super(props);
    }

    handleClick = event => {
        console.log(this.props.collaborator);
    }

    render() {
        return <button onClick={this.handleClick} className="genericButton" >Test</button>;
    }
}
export default RemoveTaskCollaborator;