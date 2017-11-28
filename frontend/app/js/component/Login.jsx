// /* eslint-disable */
import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { Toaster, Intent } from '@blueprintjs/core';
import { app, base, facebookProvider } from './Base';

const loginStyles = {
  width: '80%',
  maxWidth: '315px',
  margin: '20px auto',
  border: '1px solid #ddd',
  borderRadius: '5px',
  padding: '10px',
};

class Login extends Component {
  constructor(props) {
    super(props);
    this.authWithFacebook = this.authWithFacebook.bind(this);
    this.authWithEmailPassword = this.authWithEmailPassword.bind(this);
    this.state = {
      redirect: false,
    };
  }

  authWithFacebook() {
    app.auth().signInWithPopup(facebookProvider).then((result, error) => {
      if (error) {
        this.toaster.show({ intent: Intent.DANGER,
          message: 'Unable to sign in with Facebook' });
      } else {
        this.setState({ redirect: true });
      }
    });
  }

  authWithEmailPassword(event) {
    event.preventDefault();
    const email = this.emailInput.value;
    const password = this.passwordInput.value;

    app.auth().fetchProvidersForEmail(email).then((providers) => {
      if (providers.length === 0) {
        // create user
        return app.auth().createUserWithEmailAndPassword(email, password);
      } else if (providers.indexOf('password') === -1) {
        // already registered via Facebook
        this.loginForm.reset();
        this.toaster.show({ intent: Intent.WARNING,
          message: "Your email is already registered via Facebook. Try signing in with your Facebook account." });
      } else {
        // sign user in w/ email
        return app.auth().signInWithEmailAndPassword(email, password);
        // app.auth().signInWithEmailAndPassword(email, password);

        // var rootRef = firebase.database().ref();
        // var authData = rootRef.getAuth();

        // if (authData) {
        //   console.log("Authenticated user with uid:", authData.uid);
        // }
        // return;
      }
    })
    .then((user) => {
      if (user && user.email) {
        this.loginForm.reset();
        this.setState({redirect: true});
      }
    })
    .catch((error) => {
      this.toaster.show({ intent: Intent.DANGER, message: error.message });
    });
  }

  render() {
    if (this.state.redirect === true) {
      return <Redirect to='/' />;
    }

    return (
      <div style={loginStyles}>
        <Toaster ref={(element) => {this.toaster = element }} />
        <button style={{ width: '100%' }} className='pt-button'
        onClick={() => { this.authWithFacebook() }}>Log In with Facebook</button>
        <hr style={{marginTop: '10px', marginBottom: '10px'}} />
        <form onSubmit={(event) => { this.authWithEmailPassword(event) }}
        ref={(form) => { this.loginForm = form }}>
        <div></div>
        <div style={{marginBottom: "10px"}}>
          <h5>
            If you do not have an account already, this form will create one for you.
          </h5>
        </div>
        <label className="">
          Email
        <input style={{width: "100%"}} className="pt-input" name="email" type="email"
        ref={(input) => { this.emailInput = input }} placeholder="Email"></input>
        </label>
        <label className="">
          Password
          <input style={{width: "100%"}} className="pt-input" name="password" type="password"
          ref={(input) => { this.passwordInput = input }} placeholder="Password"></input>
        </label>
        <input style={{width: "100%"}} type="submit" className="pt-button" value="Log In"></input>
        </form>
      </div>
    );
  };
}

export default Login