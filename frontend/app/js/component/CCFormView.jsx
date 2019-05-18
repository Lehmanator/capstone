import React from "react";
import FormField from "./FormField";
import constants from "./constants";
import Results from "./CCResults";
import { makeRequestWithToken } from "./Base";

const phaseEnum = {
  enterData: 1,
  displayResults: 2,
  unknown: 3
};

export default class CCFormView extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
      idNumber: "",
      age: "",
      income: "",
      creditScore: "",
      expenses: "",
      phase: phaseEnum.enterData,
      probability: 0.0
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.submitForm = this.submitForm.bind(this);
    this.submitFormWithToken = this.submitFormWithToken.bind(this);
  }

  handleInputChange(event) {
    const target = event.target;
    this.setState({
      [target.name]: target.value
    });
  }

  submitFormWithToken() {
    makeRequestWithToken(this.submitForm);
  }

  submitForm(userToken) {
    this.setState({ phase: phaseEnum.displayResults });
    const messageHeaders = {};
    messageHeaders["Access-Control-Allow-Origin"] = "*";
    messageHeaders["content-type"] = "application/json";
    const request = {
      token: userToken,
      applicantName: this.state.name,
      applicantID: this.state.idNumber,
      age: this.state.age,
      income: this.state.income,
      creditScore: this.state.creditScore,
      expenses: this.state.expenses
    };
    const messageInit = {
      method: "POST",
      headers: messageHeaders,
      body: new Blob([JSON.stringify(request, null, 2)], {
        type: "application/json"
      })
    };
    console.log(messageInit); // eslint-disable-line no-console

    fetch(constants.ccUploadData, messageInit)
      .then(response => response.json())
      .then(jsonData => {
        const accepted = jsonData.probability > constants.positivityThreshold;
        this.setState({ probability: jsonData.probability });
        this.setState({ phase: phaseEnum.displayResults, accepted });
        console.log(jsonData); // eslint-disable-line no-console
        console.log(accepted); // eslint-disable-line no-console
      });
  }

  renderDataEntry() {
    return (
      <div
        className="row entryzone"
        style={{
          margin: "auto",
          padding: "50px",
          width: "50%",
          borderRadius: "40px",
          borderStyle: "solid"
        }}
      >
        <div className="container-fluid credit-card-container">
          <FormField
            handleInputChange={this.handleInputChange}
            name="name"
            label="Name:"
            type="string"
          />
          <br />
          <br />
          <FormField
            handleInputChange={this.handleInputChange}
            name="idNumber"
            label="ID Number:"
            type="number"
          />
          <br />
          <br />
          <FormField
            handleInputChange={this.handleInputChange}
            name="age"
            label="Age:"
            type="number"
          />
          <br />
          <br />
          <FormField
            handleInputChange={this.handleInputChange}
            name="income"
            label="Income:"
            type="number"
          />
          <br />
          <br />
          <FormField
            handleInputChange={this.handleInputChange}
            name="creditScore"
            label="Credit Score:"
            type="number"
          />
          <br />
          <br />
          <FormField
            handleInputChange={this.handleInputChange}
            name="expenses"
            label="Expenses:"
            type="number"
          />
          <br />
          <br />
          <button className="CCFormBtn" onClick={this.submitFormWithToken}>
            Submit
          </button>
        </div>
      </div>
    );
  }

  renderDisplayResults(width, height) {
    return (
      <Results
        width={width}
        height={height}
        probability={this.state.probability}
        name={this.state.name}
        accepted={this.state.accepted}
        style={{ margin: "auto" }}
      />
    );
  }

  render() {
    let displayView = null;

    const { width, height } = { width: "60%", height: 500 };

    switch (this.state.phase) {
      case phaseEnum.enterData:
        displayView = this.renderDataEntry();
        break;
      case phaseEnum.displayResults:
        displayView = this.renderDisplayResults(width, height);
        break;
      case phaseEnum.unknown:
        // TODO: Display an error screen
        break;
      default:
        break;
    }

    return <div>{displayView}</div>;
  }
}
