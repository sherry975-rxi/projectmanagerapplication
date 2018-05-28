import React from 'react';
import "./List.css";

class List extends React.Component {
  constructor(props) {
    super(props);
    this.state = { visibility: 'hide' };
    this.toggleVisibility = this.toggleVisibility.bind(this);
  }

  toggleVisibility() {
    const newVisibility = this.state.visibility == 'hide' ? 'show' : 'hide';
    this.setState({ visibility: newVisibility });
  }

  render() {
    let titleText = `${this.props.type}`;
    if (this.props.children instanceof Array) {
    	titleText += 's';
    }

    if(this.state.visibility == 'hide'){
      return(
        <div>
        <h4 className= "Parent"><button className="Parent" type="button" onClick={this.toggleVisibility}>{titleText}</button></h4>
        </div>
      )
    }

    else{
    return (
      <div>
        <h4 className= "Parent"><button className="Parent" type="button" onClick={this.toggleVisibility}>{titleText}</button></h4>
        <ul className= "Children">{this.props.children}</ul>
      </div>
    );
  }
  }
}

export default List