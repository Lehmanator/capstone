import React from 'react';
import PropTypes from 'prop-types';

class BaseFilterField extends React.Component {
  constructor(props) {
    super(props);
    this.state = { value: this.getDefaultValue() };

    this.handleChange = this.handleChange.bind(this);
  }
  getDefaultValue() {
    return null;
  }
  handleChange(event) {
    const newState = { value: event.target.value };
    this.setState(newState);
  }
  createFilter() {
    return null;
  }
  resetState() {
    this.setState({ value: this.getDefaultValue() });
  }
  render(index) {
    return (<div><p>{index}</p></div>);
  }
}

export class TextField extends BaseFilterField {
  createFilter() {
    if (this.state.value) {
      const compare = this.state.value;
      return (item) => (item[this.props.name].indexOf(compare) > -1);
    }
    return null;
  }
  getDefaultValue() {
    return '';
  }
  render(index) {
    return (
        <div className="row form-group" key={index}>
          <label className="control-label col-md-2">{this.props.label}</label>
          <input type="text" id={this.props.name}
            className="control-input col-md-9"
            value={this.state.value} onChange={this.handleChange}
          />
        </div>
    );
  }
}

export class NumberField extends BaseFilterField {
  createFilter() {
    let func = null;
    if (this.state.value) {
      if (this.props.isMinimum) {
        func = (item) => (parseFloat(item[this.props.name]) >= parseFloat(this.state.value));
      } else {
        func = (item) => (parseFloat(item[this.props.name]) <= parseFloat(this.state.value));
      }
    }
    return func;
  }
  getDefaultValue() {
    let defaultValue = 0;
    if (this.props.isMinimum) {
      defaultValue = 0;
    } else {
      defaultValue = 100;
    }
    return defaultValue;
  }
  render(index) {
    return (
        <div className="row form-group" key={index}>
          <label className="control-label col-md-2">{this.props.label}</label>
          <input type="number" id={this.props.name}
            className="control-input col-md-9"
            value={this.state.value} onChange={this.handleChange}
          />
        </div>
    );
  }
}

BaseFilterField.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
};

NumberField.propTypes = {
  isMinimum: PropTypes.bool.isRequired,
};
