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
    this.submitForm = this.submitForm.bind(this);
  }

  handleInputChange(event) {
    // console.log(event.target.name, event.key, event.target.value);
    const target = event.target;
    this.setState({
      [target.name]: target.value,
    });
  }

  submitForm() {
    const messageHeaders = {};
    messageHeaders['Access-Control-Allow-Origin'] = '*';
    messageHeaders['content-type'] = 'application/json';
    const request = {
      token: "c2geZf8u9PeAGBiwTlw2hjaT0B6ZGz86",
      applicantName: this.state.name,
      applicantID: this.state.idNumber,
      age: this.state.age,
      income: this.state.income,
      creditScore: this.state.creditScore,
      expenses: this.state.expenses,
    };
    const messageInit = { method: 'POST',
      headers: messageHeaders,
      body: new Blob([JSON.stringify(request, null, 2)], { type: 'application/json' }),
    };
    console.log(messageInit);

    fetch(constants.ccUploadData, messageInit)
    .then((response) => response.json())
    .then((jsonData) => {
      const accepted = jsonData.probability > constants.positivityThreshold;
      console.log(jsonData);
      console.log(accepted);
    });
  }

  render() {
    return (
      <div className="row" style={{ margin: 'auto', padding: '15px',
        width: '60%', borderRadius: '40px', borderStyle: 'solid',
    }}>
        <div className="container-fluid credit-card-container">
            {/* <form style= {{ display: 'table', width: '100%' }} formAction=""> */}
              <FormField handleInputChange={this.handleInputChange } name="name" label="Name:" type="string"/>
              <br /><br />
              <FormField handleInputChange={this.handleInputChange } name="idNumber" label="ID Number:" type="number"/>
              <br /><br />
              <FormField handleInputChange={this.handleInputChange } name="age" label="Age:" type="number"/>
              <br /><br />
              <FormField handleInputChange={this.handleInputChange } name="income" label="Income:" type="number"/>
              <br /><br />
              <FormField handleInputChange={this.handleInputChange } name="creditScore" label="Credit Score:" type="number"/>
              <br /><br />
              <FormField handleInputChange={this.handleInputChange} name="expenses" label="Expenses:" type="number"/>
              <br /><br />
              <button className="CCFormBtn" onClick={this.submitForm}>
                Submit
              </button>
            {/* </form> */}
        </div>
      </div>
    );
  }
}
