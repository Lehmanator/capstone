import React from 'react';
import { Link } from 'react-router-dom';
import HistoryBtn from './HistoryBtn';
import chipmunkLogo from '../../static/images/athletics-logo.png';
import creditCardLogo from '../../static/images/credit-card.svg';
import c1Logo from '../../static/images/c1tech.png';

export default function Navigation() {
  return (
    <div className="container-fluid top-bar">
        <div className="row">
          <div className="col-md-1">
            <Link to="/" >
              <img src={c1Logo} alt="Home" className="brand" />
            </Link>
          </div>
            <Link to="/history">
              <HistoryBtn title={" Logo History"} logo={chipmunkLogo} className="col-md-2" />
            </Link>
            <Link to="/credit-history">
              <HistoryBtn title={" Credit Card Appoval History" }
                logo={creditCardLogo} className="col-md-2"
              />
            </Link>
          <div className="col-md-4" />
          <a src="" className="col-md-1 user-links">Help</a>
          <a src="" className="col-md-1 user-links">Settings</a>
          <a src="" className="col-md-1 user-links">Sign Out</a>
        </div>
      </div>
    );
}
