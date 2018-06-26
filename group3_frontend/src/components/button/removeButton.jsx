import React, { Component } from 'react';
import { Glyphicon } from 'react-bootstrap';
import './removeButton.css';

class RemoveButton extends Component {
    render() { 
        return (     
            <button type="submit" className="remove-Button">     
                    <span 
                        className="glyphicon glyphicon-remove" role="button" aria-hidden="true">                       
                    </span>   
            </button>

        );
    }
}

export default RemoveButton;