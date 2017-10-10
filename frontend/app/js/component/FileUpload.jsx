import React from 'react';
import Dropzone from 'react-dropzone';
import PropTypes from 'prop-types';
import UploadPic from '../../static/images/arrow.png';

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
        <div className="dropzone" style={{ width: this.props.width, height: this.props.height, borderRadius: '40px', borderStyle: 'dashed' }} >
            <Dropzone style={{ width: '100%', height: '100%' }}
              {...this.props} onDrop={this.onDrop}
            >
            <div style={{ paddingTop: 150 }}>
              <img src={UploadPic}
                alt="Drag and Drop files here"
              />
              <h3 style={{ textAlign: 'center' }}> <strong> Drag &amp; Drop </strong> your logo here to upload </h3>
              <h5 style={{ textAlign: 'center' }}> or click this box to select your logo manually. </h5>
            </div>
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
