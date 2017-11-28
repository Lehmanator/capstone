/* eslint-disable react/prefer-stateless-function */
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import HistoryBtn from './HistoryBtn';
import chipmunkLogo from '../../static/images/athletics-logo.png';
import creditCardLogo from '../../static/images/credit-card.svg';
import c1Logo from '../../static/images/c1tech.png';

class Navigation extends Component {
  render() {
    return (
    <div className="container-fluid top-bar">
        <div className="row">
          <div className="col-md-1">
            <Link to="/" >
              <img src={c1Logo} alt="Home" className="brand" />
            </Link>
          </div>
          {
            this.props.authenticated
            ?
              <div>
                <Link to="/history">
                  <HistoryBtn title={" Logo History"} logo={chipmunkLogo} className="col-md-2" />
                </Link>
                <HistoryBtn title={ "Credit Card Appoval History" }
                  logo={creditCardLogo} className="col-md-2"
                />
                </div>
            :
              <div className="col-md-4"></div>
          }

          <div className="col-md-4" />
          {
            this.props.authenticated
              ?
                  <div>
                    <Link src="" className="col-md-1 user-links" to="/logout">Sign Out</Link>
                  </div>
              :
                  <div>
                    <Link src="" className="col-md-1 user-links" to="/login" >Login</Link>
                  </div>
          }
          <a src="" className="col-md-1 user-links">Help</a>
          <a src="" className="col-md-1 user-links">Settings</a>
        </div>
      </div>
    );
  }
}
export default Navigation;

Navigation.propTypes = {
  authenticated: PropTypes.bool.isRequired,
};
