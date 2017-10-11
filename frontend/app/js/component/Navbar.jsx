import React from 'react';
import ListElement from './ListElement';
import PropTypes from 'prop-types';

class MLNavbar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      activeIndex: 0,
    };
    this.handleClick = (index) => this.setState({ activeIndex: index });
  }
  render() {
    const len = this.props.children.length;
    let titleElements = [];

    for (let i = 0; i < len; i++) {
      let listElementClassName = '';
      if (i === 0) {
        listElementClassName = 'active';
      }
      titleElements.push(<ListElement name={this.props.children[i].name}
        className={listElementClassName} index={i}
        isActive={ this.state.activeIndex === i} onClick={this.handleClick}
      />);
    }

    return (<div>
      <nav className="navbar navbar-default ml-navbar">
        <div className="container-fluid">
          <div className="navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul className="nav navbar-nav">
              {titleElements}
              <li />
            </ul>
          </div>
        </div>
      </nav>
      <div>
        { this.props.children[this.state.activeIndex].value}
      </div>
    </div>);
  }
}

MLNavbar.propTypes = {
  children: React.PropTypes.arrayOf(
    PropTypes.shape({ name: PropTypes.string.isRequired,
      value: PropTypes.any.isRequired })).isRequired,
};

export default MLNavbar;
