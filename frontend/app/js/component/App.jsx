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
import CCHistory from './CCHistory';
import Login from './Login';
import Logout from './Logout';
import { app } from './Base';

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
    });
  }

  componentWillUnmount() {
    this.removeAuthListener();
  }

  render() {
    if (this.state.loading === true) {
      return (
        <div style={{ textAlign: 'center', position: 'absolute', top: '25%', left: '50%' }}>
          <h3>Loading...</h3>
          <Spinner />
        </div>
      );
    }
    return (
      <Router history={hashHistory}>
        <div>
          <Navigation authenticated={this.state.authenticated} />

          <Route exact path="/"
            render={(props) => <Home {...props} authenticated={this.state.authenticated} />}
          />
          <Route path="/history" component={History} />
          <Route path="/credit-history" component={CCHistory} />
          <Route path="/login" component={Login} />
          <Route path="/logout" component={Logout} />
        </div>
      </Router>
    );
  }
}
export default App;
