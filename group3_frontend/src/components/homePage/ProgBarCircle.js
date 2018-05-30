import React, { Component } from "react";
import { Line, Circle } from 'rc-progress';
import "./Homepage.css";

class ProgBarCircle extends Component{

    constructor(props) {
        super(props);
        this.match;
        this.state = {
            percent: 0,
            color: '#2b4153',
            trailWidth: 5,
            trailColor: '#aaa',
            strokeLinecap:"square",
            strokeWidth: '5',
            gapDegree: '190',
            gapPosition: 'bottom'
        

        };
        this.increase = this.increase.bind(this);
    }

    componentDidMount() {
        this.increase();
        
        }

        
      
    


    increase() {
        const percent = this.state.percent + 1;
        if (percent >= this.props.limit) {
          clearTimeout(this.tm);
          return;
        }
        this.setState(
            { percent
            });
        this.tm = setTimeout(this.increase, 10);
      }

      render(){
          return(<div className="CircleGraph">
            <Circle percent={this.state.percent} gapPosition={this.state.gapPosition} gapDegree={this.state.gapDegree} strokeWidth={this.state.strokeWidth} trailColor={this.state.trailColor} trailWidth={this.state.trailWidth} strokeLinecap={this.state.strokeLinecap} strokeColor={this.state.color} />
            </div>

          )}
}
export default ProgBarCircle