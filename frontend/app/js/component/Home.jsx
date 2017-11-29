/* eslint-disable max-len */
/* eslint-disable react/prefer-stateless-function */
import React from 'react';
import PropTypes from 'prop-types';
import MLNavbar from './Navbar';
import ImageUploadView from './ImageUploadView';
import CCFormView from './CCFormView';

class Home extends React.Component {
  render() {
    const appChildren = [
      { name: 'Upload Logo', value: <ImageUploadView /> },
      { name: 'Credit Card Approval', value: <CCFormView /> },
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

Home.propTypes = {
  authenticated: PropTypes.bool.isRequired,
};
