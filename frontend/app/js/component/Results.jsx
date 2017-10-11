import React from 'react';
import PropTypes from 'prop-types';
import AcceptedPic from '../../static/images/check-mark.png';
import RejectedPic from '../../static/images/forbidden-mark.png';

export default class Results extends React.Component {
  renderDisplayField() {
    let acceptanceImage = null;
    let message = null;

    if (this.props.accepted) {
      message = 'Valid';
      acceptanceImage = AcceptedPic;
    } else {
      message = 'Invalid';
      acceptanceImage = RejectedPic;
    }

    // eslint-disable-next-line prefer-template
    const titleHeight = this.props.height * 0.2 + 'px';

    return (
      <div style={{ width: this.props.width, height: this.props.height,
                          display: 'flex', alignItems: 'center' }}
      >
        <div style={{ width: '100%' }}>
          <div style={{ width: '100%', fontSize: titleHeight, textAlign: 'left' }} >
          {/* eslint-disable-next-line no-dupe-keys */}
          <p style={{ fontSize: '10vmin',
            textAlign: 'left', width: this.props.width * 0.80 }}
          >
              {message}
          </p>
          </div>
          <hr />
          <div className="row">
            <img src={this.props.image}
              style={{ maxHeight: this.props.height * 0.5, maxWidth: '50%' }}
              alt="Original"
            />
            <img src={acceptanceImage}
              style={{ maxHeight: this.props.height * 0.5, maxWidth: '50%' }}
              alt={message}
            />
          </div>
        </div>
      </div>
    );
  }

  render() {
    let displayField = this.renderDisplayField();

    return (
        <section>
        <div className="dropzone"
          style={{ width: this.props.width, height: this.props.height,
                   borderRadius: '40px', borderStyle: 'dashed' }}
        >
              {displayField}
        </div>
        </section>
    );
  }
}

Results.propTypes = {
  width: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
  height: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
  image: PropTypes.string.isRequired,
  accepted: PropTypes.bool.isRequired,
};

Results.defaultProps = {
  width: 500,
  height: 200,
};
