// take in props as picture, name, and action for each button
import React from 'react';
// import logo from '../../static/images/athletics-logo.png';
// import PropTypes from 'prop-types';

const HistoryBtn = (props) => (
  <div>
    <button className="history-button">
      <img src={props.logo} alt="Logo" />
      {props.title}
    </button>
  </div>
);

HistoryBtn.propTypes = {
  title: React.PropTypes.string.isRequired,
  logo: React.PropTypes.string.isRequired,
};

export default HistoryBtn;
