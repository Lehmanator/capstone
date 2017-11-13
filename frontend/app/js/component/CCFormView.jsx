import React from 'react';
import constants from './constants';

export default class CCFormView extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      idNumber: '',
      age: '',
      income: '',
      creditScore: '',
      expenses: '',
    };
    this.handleInputChange = this.handleInputChange.bind(this);
  }

  handleInputChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    this.setState({
      [name]: value,
    });
  }

  submitForm() {
    var word = '{name: \'' + this.state.name + '\', idNumber: \'' + this.state.idNumber+ '\', age: \'' + this.state.age+ '\', income: \'' 
    + this.state.income+ '\', creditScore: \'' + this.state.creditScore+ '\', expenses: \'' + this.state.expenses + '\'}';
    console.log(word);
  }

  render() {
    return (
      <div className="row" style={{ margin: 'auto', padding: '15px',
        width: '60%', borderRadius: '40px', borderStyle: 'solid',
    }}>
        <div className="container-fluid credit-card-container">
            <form style={{ display: 'table', width: '100%' }}>
              <div className="row">
                <label className="col-sm-4" for="#name_input">
                  Name:
                </label>
                  <input
                    id="name_input"
                    className="col-sm-8"
                    name="name"
                    type="string"
                    value={this.state.name}
                    onChange={this.handleInputChange}
                  />
              </div>
              <br /><br />
              <div className="row">
                <label className="col-sm-4" htmlFor="#idnumber_input">
                  ID Number:
                </label>
                  <input
                    id="#idnumber_input"
                    className="col-sm-8"
                    name="idNumber"
                    type="number"
                    value={this.state.idNumber}
                    onChange={this.handleInputChange}
                  />
              </div>
              <br /><br />
              <div className="row">
                <label className="col-sm-4" htmlFor="#age_input">
                  Age:
                </label>
                  <input
                    id="age_input"
                    className="col-sm-8"
                    name="age"
                    type="number"
                    value={this.state.age}
                    onChange={this.handleInputChange}
                  />
              </div>
              <br /><br />
              <div className="row">
                <label className="col-sm-4" htmlFor="#income_input">
                  Income:
                </label>
                  <input
                    id="income_input"
                    className="col-sm-8"
                    name="income"
                    type="number"
                    value={this.state.income}
                    onChange={this.handleInputChange}
                  />
              </div>
              <br /><br />
              <div className="row">
                <label className="col-sm-4" htmlFor="#credit_score_input">
                  Credit Score:
                </label>
                  <input
                    id="credit_score_input"
                    className="col-sm-8"
                    name="creditScore"
                    type="number"
                    value={this.state.creditScore}
                    onChange={this.handleInputChange}
                  />
              </div>
              <br /><br />
              <div className="row">
                <label className="col-sm-4" htmlFor="#expenses_input">
                  Expenses:
                </label>
                  <input
                    id="expenses_input"
                    className="col-sm-8"
                    name="expenses"
                    type="number"
                    value={this.state.expenses}
                    onChange={this.handleInputChange}
                  />
              </div>
              <br /><br />
              <button className="CCFormBtn" onClick={this.submitForm()}>
                Submit
              </button>
            </form>
        </div>
      </div>
    );
  }
}
