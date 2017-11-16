import React from 'react';
import constants from './constants';
import BaseHistory from './BaseHistory';
import CreditCardHistory from './CreditCardHistory';

export default class CCHistory extends BaseHistory {
  renderPageHeader() {
    return (<h2> Credit Card Application History </h2>);
  }

  getHistoryURL() {
    return `${constants.creditHistoryUrl}?username=${constants.defaultUser}`;
  }

  renderHistoryItems(item, index) {
    // eslint-disable-next-line no-console
    console.log(item);
    return (<CreditCardHistory key={index}
      applicantName={item.applicantName}
      applicantID={item.applicantID}
      age={item.age}
      income={item.income}
      creditScore={item.creditScore}
      expenses={item.expenses}
      probability={item.result}
    />);
  }
}
