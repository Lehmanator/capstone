import React from 'react';
import MLNavbar from './Navbar';

export default function App() {
  return (
    <div>
      <div className="alert alert-primary" role="alert">
        This is a primary alert—check it out!
      </div>
      <div>
        <MLNavbar child1={<div><p>Hello</p></div>} child2={<div><p>Goodbye</p></div>} />
      </div>
    </div>
  );
}
