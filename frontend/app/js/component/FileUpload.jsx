import React from 'react';
import Dropzone from 'react-dropzone';
import PropTypes from 'prop-types';

export default class FileUpload extends React.Component {
  constructor() {
    super();
    this.state = { files: [] };
    this.onDrop = this.onDrop.bind(this);
  }

  onDrop(files) {
    this.setState({
      files,
    });
    this.props.uploadHandler();
  }

  render() {
    return (
        <section>
        <div className="dropzone" style={{ width: this.props.width, height: this.props.height, borderRadius: '40px', borderStyle: 'dashed', backgroundColor: '#00B1E1' }} >
            <Dropzone style={{ width: '100%', height: '100%' }}
              {...this.props} onDrop={this.onDrop}
            >
              <img src="/home/john/capitalfun/frontend/app/static/images/arrow.png"
                alt="Drag and Drop files here"
              />
            </Dropzone>
        </div>
        </section>
    );
  }
}

FileUpload.PropTypes = {
  width: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
  height: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
  uploadHandler: PropTypes.func.isRequired,
};

FileUpload.defaultProps = {
  width: 500,
  height: 200,
};
