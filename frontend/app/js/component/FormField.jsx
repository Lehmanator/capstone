import React from 'react';

export default class FormField extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      data: '',
    };
    this.handleChange= this.handleChange.bind(this)
  }

  handleChange(event) {
    this.setState({ data: event.target.value })
  }


  render() {
    return (
    <div className="row">
                <label className="col-sm-4">
                  { this.props.label }
                </label>
                  <input style= {{ border: 'none', borderBottom: '1px solid black' }}
                    className="col-sm-8"
                    name={this.props.name}
                    type="string"
                    value={this.state.data}
                    onChange={ this.handleChange }
                  />
              </div>
    );
  }
}
