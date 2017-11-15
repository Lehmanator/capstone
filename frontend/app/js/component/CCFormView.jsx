import React from 'react';
import FormField from './FormField';
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
    this.setState({
      [target.name]: target.value + event.key,
    });
  }

  submitForm() {
    const word = '{name: \'' + this.state.name + '\', idNumber: \'' +
    this.state.idNumber + '\', age: \'' + this.state.age + '\', income: \''
    + this.state.income + '\', creditScore: \'' + this.state.creditScore
    + '\', expenses: \'' + this.state.expenses + '\'}';
    console.log(word);
  }

  render() {
    return (
      <div className="row" style={{ margin: 'auto', padding: '15px',
        width: '60%', borderRadius: '40px', borderStyle: 'solid',
    }}>
        <div className="container-fluid credit-card-container">
            <form style= {{ display: 'table', width: '100%' }}>
              <FormField handleInputChange={this.handleInputChange } name="name" label="Name:" />
              <br /><br />
              <FormField handleInputChange={this.handleInputChange } name="idNumber" label="ID Number:" />
              <br /><br />
              <FormField handleInputChange={this.handleInputChange } name="age" label="Age:" />
              <br /><br />
              <FormField handleInputChange={this.handleInputChange } name="income" label="Income:" />
              <br /><br />
              <FormField handleInputChange={this.handleInputChange } name="creditScore" label="Credit Score:" />
              <br /><br />
              <FormField handleInputChange={this.handleInputChange } name="expenses" label="Expenses:" />
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
