/* eslint-disable max-len */
import React, { Component } from 'react';
import MLNavbar from './Navbar';
import ImageUploadView from './ImageUploadView';

class Home extends React.Component {
    render() {
      const appChildren = [
	    { name: 'Upload Logo', value: <ImageUploadView /> },
	    { name: 'Credit Card Approval', value: <div><p>Goodbye</p></div> },
	  ];

	  return (
	    <div>
	    {
	    	this.props.authenticated
	    	?
	        <MLNavbar children={appChildren} />
	        :
	        <div>
		        <h1>
		        	Please login to continue
		        </h1>
	        </div>
	    }
	    </div>
	  );
    }
}

export default Home;