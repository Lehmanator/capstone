import React from 'react';
import PropTypes from 'prop-types';
import AcceptedPic from '../../static/images/check-mark.png';
import RejectedPic from '../../static/images/forbidden-mark.png';

export default class Results extends React.Component {
  renderDisplayField() {
    let acceptanceImage = null;
    let message = null;
    const probability = this.props.probability;
    const name = this.props.name;

    if (this.props.accepted) {
      message = 'Accepted';
      acceptanceImage = AcceptedPic;
    } else {
      message = 'Denied';
      acceptanceImage = RejectedPic;
    }

    // eslint-disable-next-line prefer-template
    const titleHeight = this.props.height * 0.2 + 'px';

    return (
      <div className="resultImage" style={{ width: this.props.width, height: this.props.height,
                          }}
      >
        <div style={{ width: '100%' }}>
          <div style={{ width: '100%', fontSize: titleHeight, textAlign: 'left' }} >
            <p style={{ fontSize: 'calc(10px + 9vmin)', textAlign: 'left', width: '100%' }}>
                {message}
            </p>
            <hr />
          </div>
          <div className="row">
            <div style={{ align: 'left' }}>
              <div style={{ fontSize: 'calc(5px + 5vmin)', textAlign: 'left',
                width: '50%', maxHeight: this.props.height * 0.5, maxWidth: '50%' }}
              >
                {name}
                <br />
                {`${(probability.toFixed(2) * 100)}%` }
              </div>
            </div>
            <div>
              <img src={acceptanceImage}
                style={{ align: 'right', maxHeight: this.props.height * 0.5, maxWidth: '50%' }}
                alt="accptance Missing!"
              />
            </div>
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
  accepted: PropTypes.bool.isRequired,
  probability: PropTypes.number.isRequired,
  name: PropTypes.string.isRequired,
};

Results.defaultProps = {
  width: 500,
  height: 200,
};
