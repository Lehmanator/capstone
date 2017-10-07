import React from 'react';
import ListElement from './ListElement';

class MLNavbar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      activeIndex: 0,
    };
    this.handleClick = (index) => this.setState({ activeIndex: index });
  }
  render() {
    return (<div>
      <nav className="navbar navbar-default ml-navbar">
        <div className="container-fluid">
          <div className="navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul className="nav navbar-nav">
              <ListElement name="Upload Logo" index={0} className="active" isActive={ this.state.activeIndex === 0 } onClick={this.handleClick} />
              <ListElement name="Credit Card Approval" index={1} isActive={ this.state.activeIndex === 1 } onClick={this.handleClick} />
              <li />
            </ul>
          </div>
        </div>
      </nav>
    </div>);
  }
}

export default MLNavbar;
