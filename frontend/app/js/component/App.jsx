import React from 'react';
import {
  BrowserRouter as Router,
  Route,
  hashHistory,
} from 'react-router-dom';
import Navigation from './Navigation';
import Home from './Home';
import History from './History';

const App = () => (
  <Router history={hashHistory}>
    <div>

      <Navigation />

      <Route exact path="/" component={Home} />
      <Route path="/history" component={History} />
    </div>
  </Router>
);

export default App;
