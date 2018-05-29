import React from 'react';
import "./List.css";

class List extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      visibility: 'hide',
      buttonActive: "ParentButton"
    };
    this.toggleVisibility = this.toggleVisibility.bind(this);
  }

  toggleVisibility() {
    const newVisibility = this.state.visibility == 'hide' ? 'show' : 'hide';
    this.setState({ visibility: newVisibility, buttonActive: "buttonActive" });
  }

  render() {


    let icon = <div className="icon" align="right">
      <span className="glyphicon glyphicon-chevron-right"></span></div>;

    let titleText = `${this.props.type}`;
    if (this.props.children instanceof Array) {
      titleText += 's';
    }

    if (this.state.visibility == 'hide') {
      return (
        <div>
          <div className="Parent"><button className="ParentButton" type="button" onClick={this.toggleVisibility}><div className="title">{titleText}</div></button>{icon}</div>
        </div>
      )
    }

    else {
      return (
        <div>
          <div className="Parent"><button className={this.state.buttonActive} type="button" onClick={this.toggleVisibility}> <div className="title">{titleText}</div></button>{icon}</div>
          <div className="list">
            <ul className="Children">{this.props.children}</ul>
          </div>
        </div>
      );
    }
  }
}

export default List