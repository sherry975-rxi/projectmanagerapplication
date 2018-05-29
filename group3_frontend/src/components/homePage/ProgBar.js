import React, { Component } from "react";
import { Line, Circle } from 'rc-progress';

class ProgBar extends Component{

    constructor(props) {
        super(props);
        this.match;
        this.state = {
            percent: 0,
            color: '#3FC7FA',
            trailWidth: 0.4,
            trailColor: '#222222',
            strokeLinecap:"square"
        };
        this.increase = this.increase.bind(this);
    }

    componentDidMount() {
        this.increase();
      }
    


    increase() {
        const percent = this.state.percent + 1;
        const colorMap = ['#3FC7FA', '#85D262', '#FE8C6A'];
        if (percent >= this.props.limit) {
          clearTimeout(this.tm);
          return;
        }
        this.setState(
            { percent,
                color: colorMap[parseInt(Math.random() * 3, 10)],
            });
        this.tm = setTimeout(this.increase, 10);
      }

      render(){
          return(
            <Line percent={this.state.percent} trailColor={this.state.trailColor} trailWidth={this.state.trailWidth} strokeLinecap={this.state.strokeLinecap} strokeWidth="4" strokeColor={this.state.color} />


          )}
}
export default ProgBar