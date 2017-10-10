import React from 'react';
import MLNavbar from './Navbar';
import HistoryBtn from './HistoryBtn';
import chipmunkLogo from '../../static/images/athletics-logo.png';
import creditCardLogo from '../../static/images/credit_card.svg';
import c1Logo from '../../static/images/c1tech.png';
import ImageUploadView from './ImageUploadView';

export default function App() {
  return (
    <div>
      <div className="row top-bar">
        <a src="" className="history-button col-md-1" ><img src={c1Logo} alt="Home" /></a>
        <HistoryBtn title={" Logo History"} logo={chipmunkLogo} className="col-md-2" />
        <HistoryBtn title={" Credit Card Appoval History"} logo={creditCardLogo} className="col-md-2" />
        <div className="col-md-4"></div>
        <a src="" className="col-md-1 user-links">Help</a>
        <a src="" className="col-md-1 user-links">Settings</a>
        <a src="" className="col-md-1 user-links">Sign Out</a>
      </div>
      <div>
        <MLNavbar children={[<ImageUploadView />, <div><p>Goodbye</p></div>]} />
      </div>
    </div>
  );
}
