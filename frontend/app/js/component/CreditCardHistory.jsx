import React from 'react';
import CollapsablePanel from './CollapsablePanel';
import PropTypes from 'prop-types';
import constants from './constants';
import AcceptedPic from '../../static/images/check-mark.png';
import RejectedPic from '../../static/images/forbidden-mark.png';

export default class CreditCardHistory extends CollapsablePanel {
  renderResultImage() {
    let accepted = RejectedPic;
    let alt = 'Rejected';
    if (this.props.probability) {
      if (this.props.probability > constants.positivityThreshold) {
        accepted = AcceptedPic;
        alt = 'accepted';
      }
    }
    return (<img src={accepted} style={{ height: 100 }} alt={alt} />);
  }

  getOrCreate(variable, defaultValue) {
    if (variable) {
      return variable;
    }
    return defaultValue;
  }

  // SECTION: Overrides from CollapsablePanel

  renderHeading() {
    let name = this.getOrCreate(this.props.applicantName, 'Applicant Name');
    name = `Name: ${name}`;

    let id = this.getOrCreate(this.props.applicantID, 'Applicant ID');
    id = `ID: ${id}`;

    let resultImage = this.renderResultImage();

    return (
      <div className="row" >
        <div className="col-md-6">
          <h1 style={{ textAlign: 'left' }} > {name} </h1>
        </div>
        <div className="col-md-3">
            <h1 style={{ textAlign: 'left' }} > {id} </h1>
        </div>
        <div className="col-md-2" >{resultImage}</div>
      </div>
    );
  }

  renderField(key, value, defaultValue) {
    const renderedValue = this.getOrCreate(value, defaultValue);
    return (
      <div className="col-md-6 row">
        <div className="col-md-6">
          <h4 > {key}: </h4>
        </div>
        <div className="col-md-6">
          <h3> {renderedValue} </h3>
        </div>
    </div>
    );
  }

  renderBody() {
    const applicantName = this.renderField('Applicant Name', this.props.applicantName, 'Anonymous');
    const applicantID = this.renderField('Applicant ID', this.props.applicantID, 'Unknown');
    const age = this.renderField('Age', this.props.age, 'Unknown');
    const income = this.renderField('Income', this.props.income, 'Unknown');
    const creditScore = this.renderField('Credit Score', this.props.creditScore, 'Unknown');
    const expenses = this.renderField('Expenses', this.props.expenses, 'Unknown');
    return (
      <div>
        <div className="row" >
          {applicantName}
          {applicantID}
        </div>
        <div className="row" >
          {age}
          {income}
        </div>
        <div className="row" >
          {creditScore}
          {expenses}
        </div>
      </div>
    );
  }

  renderFooter() {
    let probability = 0;
    if (this.props.probability) {
      probability = this.props.probability;
    }
    const probabilityDisplay = <h1>Probability: {probability}</h1>;
    return probabilityDisplay;
  }
}

CreditCardHistory.propTypes = {
  applicantName: PropTypes.string.isRequired,
  applicantID: PropTypes.string.isRequired,
  age: PropTypes.number.isRequired,
  income: PropTypes.number.isRequired,
  creditScore: PropTypes.number.isRequired,
  expenses: PropTypes.number.isRequired,
  probability: PropTypes.number.isRequired,
};
