// /* eslint-disable */
import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { Toaster, Intent } from '@blueprintjs/core';
import { app, facebookProvider, googleProvider } from './Base';
import facebookIcon from '../../static/images/FB-f-Logo__blue_50.png';
import googleIcon from '../../static/images/btn_google_signin_light_normal_web.png';

export default class Login extends Component {
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
        this.toaster.show({
          intent: Intent.DANGER,
          message: 'Unable to sign in with Facebook',
        });
      } else {
        this.setState({ redirect: true });
      }
    });
  }

  authWithGoogle() {
    app.auth().signInWithPopup(googleProvider).then((result, error) => {
      if (error) {
        this.toaster.show({
          intent: Intent.DANGER,
          message: 'Unable to sign in with Google',
        });
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
        this.toaster.show({
          intent: Intent.WARNING,
          message: 'Your email is already registered via Facebook or Google. ' +
          'Try signing in with one of those options.',
        });
      }
      return app.auth().signInWithEmailAndPassword(email, password);
    })
      .then((user) => {
        if (user && user.email) {
          this.loginForm.reset();
          this.setState({ redirect: true });
        }
      })
      .catch((error) => {
        this.toaster.show({ intent: Intent.DANGER, message: error.message });
      });
  }

  render() {
    if (this.state.redirect === true) {
      return <Redirect to={'/'} />;
    }
    return (
      <div className={'login-style'}>
        <Toaster ref={(element) => {
          this.toaster = element;
        }}
        />
        <form
          onSubmit={(event) => {
            this.authWithEmailPassword(event);
          }}
          ref={(form) => {
            this.loginForm = form;
          }}
        >
          <div style={{ marginBottom: '10px' }}>
            <h5>
              If you do not have an account already, this form will create one for you.
            </h5>
          </div>
          <input
            style={{ width: '100%' }}
            className="pt-input"
            name="email"
            type="email"
            ref={(input) => {
              this.emailInput = input;
            }} placeholder="Email"
          >
          </input>
          <input
            id="password-ipt"
            className="pt-input"
            name="password"
            type="password"
            ref={(input) => {
              this.passwordInput = input;
            }}
            placeholder="Password"
          >
          </input>
          <input
            type="submit"
            style={{ backgroundColor: '#222222' }}
            className="mdc-button--raised login-btn"
            value="Log In"
          >
          </input>
        </form>
        <hr style={{ marginTop: '10px', marginBottom: '10px' }} />
        <button
          style={{ backgroundImage: `url(${facebookIcon})`, width: '50px', height: '50px' }}
          className="fb-login-button"
          data-max-rows="1"
          data-size="large"
          data-button-type="login_with"
          data-show-faces="false"
          data-auto-logout-link="false"
          data-use-continue-as="true"
          onClick={() => {
            this.authWithFacebook();
          }}
        ></button>
        <button
          style={{ backgroundImage: `url(${googleIcon})`, width: '191px',
          height: '46px', marginLeft: '10px' }}
          onClick={() => {
            this.authWithGoogle();
          }}
        ></button>
      </div>
    );
  }
}
