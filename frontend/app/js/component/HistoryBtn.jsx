// take in props as picture, name, and action for each button
import React from 'react';

const HistoryBtn = (props) => (
  <div>
    <button className="nav-button">
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
