import React from 'react';
import './List.css';

class List extends React.Component {


    render() {
        let icon = (
            <div className="icon" align="right">
                <span className="glyphicon glyphicon-chevron-right" />
            </div>
        );


        return (
            <div>
                <div className="Item">
                    <button className="ItemButton" >
                        <div className="title">{this.props.children}</div>
                    </button>
                        {icon}
                </div>
            </div>
        );

    }
}

export default List;
