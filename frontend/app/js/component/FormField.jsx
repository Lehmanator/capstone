import React from 'react';
import PropTypes from 'prop-types';

export default class FormField extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      data: '',
    };
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(event) {
    this.setState({ data: event.target.value });
    this.props.handleInputChange(event);
  }


  render() {
    return (
      <div className="row">
        <label className="col-sm-3">
          {this.props.label}
        </label>
        <input style={{ border: 'none', borderBottom: '1px solid black' }}
          className="col-sm-9"
          name={this.props.name}
          type={this.props.type}
          value={this.state.data}
          onChange={this.handleChange}
        />
      </div>
    );
  }
}

FormField.propTypes = {
  handleInputChange: PropTypes.func.isRequired,
  label: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
  type: PropTypes.string.isRequired,
};

