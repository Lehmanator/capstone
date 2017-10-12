import React from 'react';
import PropTypes from 'prop-types';
import UploadingPic from '../../static/images/loading.gif';

export default class ProcessingView extends React.Component {
  renderDisplayField() {
    return (
      <div style={{ width: this.props.width, height: this.props.height,
                          display: 'flex', alignItems: 'center' }}
      >
        <div style={{ width: '100%' }}>
          <img src={UploadingPic} style={{ height: this.props.height * 0.5 }}
            alt="Uploading"
          />
          <h3 style={{ textAlign: 'center' }}>
            Uploading...
          </h3>
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

ProcessingView.propTypes = {
  width: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
  height: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
};

ProcessingView.defaultProps = {
  width: 500,
  height: 200,
};
