import React from 'react';
import MLNavbar from './Navbar';
import HistoryBtn from './HistoryBtn';
import chipmunkLogo from '../../static/images/athletics-logo.png';
import creditCardLogo from '../../static/images/credit_card.svg';
import c1Logo from '../../static/images/c1tech.png';

export default function App() {
  return (
    <div>
      <div className="alert alert-primary" role="alert">
        This is a primary alert—check it out!
      </div>
      <div>
        <MLNavbar child1={<div><p>Hello</p></div>} child2={<div><p>Goodbye</p></div>} />
      </div>
      <div className="top-bar">
        <a src="" className="history-button" ><img src={c1Logo} alt="Home" /></a>
        <HistoryBtn title={" Logo History"} logo={chipmunkLogo} />
        <HistoryBtn title={" Credit Card Appoval History"} logo={creditCardLogo} />
        <a src="" className="user-links">Help</a>
        <a src="" className="user-links">Settings</a>
        <a src="" className="user-links">Sign Out</a>
      </div>
    </div>
  );
}
