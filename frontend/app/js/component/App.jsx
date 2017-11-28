import React, { Component } from 'react';
import {
  BrowserRouter as Router,
  Route,
  hashHistory,
} from 'react-router-dom';
import Navigation from './Navigation';
import Home from './Home';
import History from './History';
import Login from './Login';

class App extends Component {
  constructor() {
    super();
    this.state = {
      authenticated: false,
    };
  }
  render() {
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
