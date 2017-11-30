import React from 'react';
import constants from './constants';
import BaseHistory from './BaseHistory';
import CreditCardHistory from './CreditCardHistory';
// import CCHistoryFilter from './CCHistoryFilter';

export default class CCHistory extends BaseHistory {
  constructor(props) {
    super(props);
    this.onFilter = this.onFilter.bind(this);
  }

  renderPageHeader() {
    return (<h2> Credit Card Application History </h2>);
  }

  getHistoryURL(token) {
    return `${constants.creditHistoryUrl}?token=${token}`;
  }

  renderHistoryItems(item, index) {
    // eslint-disable-next-line no-console
    // console.log(item);
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

  onFilter(newFilters) {
    this.setState({ filters: newFilters });
  }

  renderFilters() {
    // return (
    //   <CCHistoryFilter onFilter={this.onFilter} />
    // );
    return null;
  }
}
