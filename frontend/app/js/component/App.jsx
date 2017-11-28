import React, { Component } from 'react';
import {
  BrowserRouter as Router,
  Route,
  hashHistory,
} from 'react-router-dom';
import { Spinner } from '@blueprintjs/core';
import Navigation from './Navigation';
import Home from './Home';
import History from './History';
import Login from './Login';
import { app, base } from './Base';

class App extends Component {
  constructor() {
    super();
    this.state = {
      authenticated: false,
      loading: true,
    };
  }

  componentWillMount() {
    this.removeAuthListener = app.auth().onAuthStateChanged((user) => {
      if (user) {
        this.setState({
          authenticated: true,
          loading: false,
        });
      } else {
        this.setState({
          authenticated: false,
          loading: false,
        });
      }
    })
  }

  componentWillUnmount() {
    this.removeAuthListener();
  }

  render() {
    if (this.state.loading) {
      return (
        <div style={{ textAlign: 'center', position: 'aboslute', top: '25%', left: '50%' }}>
          <h3>Loading...</h3>
        </div>
      );
    }
    return (
      <Router history={hashHistory}>
        <div>

          <Navigation authenticated={this.state.authenticated} />

          <Route exact path="/" component={Home} />
          <Route path="/history" component={History} />
          <Route path="/login" component={Login} />
        </div>
      </Router>
    );
  }
}
export default App;
