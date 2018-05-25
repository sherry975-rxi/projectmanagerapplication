import React from 'react';
import "./List.css";

class List extends React.Component {
  render() {
    let titleText = `${this.props.type}`;
    if (this.props.children instanceof Array) {
    	titleText += 's';
    }
    return (
      <div>
        <h4 className= "Parent">{titleText}</h4>
        <ul className= "Children">{this.props.children}</ul>
      </div>
    );
  }
}

export default List