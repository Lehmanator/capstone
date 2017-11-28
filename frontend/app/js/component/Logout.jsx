import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { Spinner } from '@blueprintjs/core';
import { app } from './Base';

class Logout extends Component {
  constructor() {
    super();
    this.state = {
      redirect: false,
    };
  };

  componentWillMount() {
    app.auth().signOut().then((user) => {
      this.setState({ redirect: true });
    });
  };

  render() {
    if (this.state.redirect) {
      return <Redirect to='/' />;
    }

    return (
      <div style={{ textAlign: 'center', position: 'absolute', top: '25%', left: '50%' }}>
        <h3>Loading...</h3>
        <Spinner />
      </div>
    );
  };
}

export default Logout;