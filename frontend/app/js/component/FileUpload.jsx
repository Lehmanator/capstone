import React from 'react';
import Dropzone from 'react-dropzone';
import PropTypes from 'prop-types';
import UploadPic from '../../static/images/arrow.png';

export default class FileUpload extends React.Component {
  constructor() {
    super();
    this.state = {
      files: [],
      currentImage: null,
    };
    this.onDrop = this.onDrop.bind(this);
  }

  onImageLoad(event) {
    this.setState({
      currentImage: event.target.result,
    });
    this.props.uploadHandler(true,
      this.getImageForDisplay.bind(this),
      this.getImageForUpload.bind(this));
  }

  onClear() {
    this.props.uploadHandler(false, null);
    this.setState({
      files: [], currentImage: null,
    });
  }

  onDrop(files) {
    const reader = new FileReader();
    reader.onload = this.onImageLoad.bind(this);
    reader.readAsDataURL(files[0]);
    this.setState({
      files, currentImage: reader.result,
    });
  }

  getImageForUpload() {
    return this.state.files[0];
  }

  getImageForDisplay() {
    return this.state.currentImage;
  }

  renderUploadImage() {
    return (
      <div style={{ width: this.props.width, height: this.props.height,
                          display: 'flex', alignItems: 'center' }}
      >
        <div style={{ width: '100%' }}>
          <img src={UploadPic} style={{ maxHeight: this.props.height * 0.5, maxWidth: '100%' }}
            alt="Drag and Drop files here"
          />
          <h3 style={{ textAlign: 'center' }}>
            <strong> Drag &amp; Drop </strong> your logo here to upload
          </h3>
          <h5 style={{ textAlign: 'center' }}>
            or click this box to select your logo manually.
          </h5>
        </div>
      </div>
    );
  }

  renderUploadedImage() {
    const { files, currentImage } = this.state;
    return (
      <div style={{ width: this.props.width, height: this.props.height,
                          display: 'flex', alignItems: 'center' }}
      >
        <div style={{ width: '100%' }}>
          <img id="uploadedImage" src={currentImage}
            style={{ maxHeight: this.props.height * 0.5, maxWidth: '100%' }}
            alt="Drag and Drop files here"
          />
          <h3 style={{ textAlign: 'center' }}>
            File has been uploaded: {files[0].name} - {files[0].size} bytes
          </h3>
          <h5 style={{ textAlign: 'center' }}>
            Click this box to choose a new image
          </h5>
        </div>
      </div>
    );
  }

  render() {
    let displayField = null;
    const { files, currentImage } = this.state;
    if (files.length === 0 || currentImage === null) {
      displayField = this.renderUploadImage();
    } else {
      displayField = this.renderUploadedImage();
    }

    return (
        <section>
        <div className="dropzone"
          style={{ width: this.props.width, height: this.props.height,
                   borderRadius: '40px', borderStyle: 'dashed' }}
        >
            <Dropzone style={{ width: '100%', height: '100%' }}
              {...this.props} onDrop={this.onDrop}
            >
              {displayField}
            </Dropzone>
        </div>
        </section>
    );
  }
}

FileUpload.propTypes = {
  width: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
  height: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
  uploadHandler: PropTypes.func.isRequired,
};

FileUpload.defaultProps = {
  width: 500,
  height: 200,
};
