/* eslint-disable max-len */
import React from 'react';
import MLNavbar from './Navbar';
import HistoryBtn from './HistoryBtn';
import chipmunkLogo from '../../static/images/athletics-logo.png';
import creditCardLogo from '../../static/images/credit-card.svg';
import c1Logo from '../../static/images/c1tech.png';
import ImageUploadView from './ImageUploadView';

export default function App() {
  const appChildren = [
    { name: 'Upload Logo', value: <ImageUploadView /> },
    { name: 'Credit Card Approval', value: <div><p>Goodbye</p></div> },
  ];

  return (
    <div>
      <div className="container-fluid top-bar">
        <div className="row">
          <a src="" className="col-md-1"><img src={c1Logo} alt="Home" className="brand" /></a>
            <HistoryBtn title={" Logo History"} logo={chipmunkLogo} className="col-md-2" />
            <HistoryBtn title={" Credit Card Appoval History" } logo={creditCardLogo} className="col-md-2" />
          <div className="col-md-4" />
          <a src="" className="col-md-1 user-links">Help</a>
          <a src="" className="col-md-1 user-links">Settings</a>
          <a src="" className="col-md-1 user-links">Sign Out</a>
        </div>
      </div>
      <div>
        <MLNavbar children={appChildren} />
      </div>
    </div>
  );
}
