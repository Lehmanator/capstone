import React from 'react';
import PropTypes from 'prop-types';

class listElement extends React.Component {
  constructor(props) {
    super(props);
    this.handleClick = () => this.props.onClick(this.props.index);
  }
  render() {
    return (<li className={this.props.isActive ? 'active' : ''} onClick={this.handleClick}>
      <a href="#"> { this.props.name } </a>
    </li>);
  }
}

listElement.propTypes = {
  onClick: PropTypes.func,
  isActive: PropTypes.bool,
  name: PropTypes.string,
  index: PropTypes.number,
};

module.exports = listElement;
