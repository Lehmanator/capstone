import React from 'react';
import MLNavbar from './Navbar';
import FileUpload from './FileUpload';
// import Navbar from './Navbar';

export default function App() {
  return (
    <div>
      <div className="alert alert-primary" role="alert">
        This is a primary alert—check it out!
      </div>
      <div>
        <MLNavbar child1={<div><p>Hello</p></div>} child2={<div><p>Goodbye</p></div>} />
      </div>
      <div>
        <center>
          <FileUpload multiple={false} width={'80%'} height={400} style={{ margin: 'auto' }} />
        </center>
      </div>
    </div>
  );
}
