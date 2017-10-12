import React from 'react';
import FileUpload from './FileUpload';
import ProcessingView from './Processing';
import Results from './Results';

const phaseEnum = {
  chooseImage: 1,
  processingImage: 2,
  displayResults: 3,
  unknown: 4,
};

export default class ImageUploadView extends React.Component {
  constructor(props) {
    super(props);
    this.state = { showButton: false, displayImage: null,
      imageFile: null, phase: phaseEnum.chooseImage, accepted: false };
    this.onGoodUpload = this.onGoodUpload.bind(this);
    this.onUploadImage = this.onUploadImage.bind(this);
  }

  onGoodUpload(uploadSuccessful, displayImage, imageFile) {
    this.setState({ displayImage, imageFile });
    if (uploadSuccessful) {
      this.onUploadImage();
    }
  }

  onUploadImage() {
    this.setState({ phase: phaseEnum.processingImage });
    setTimeout(this.sendMessage.bind(this), 1000);
  }

  sendMessage() {
    const file = this.state.imageFile();
    const messageHeaders = new Headers();
    messageHeaders.append('Access-Control-Allow-Origin', '*');
    const body = {
      image: this.state.displayImage(),
      name: file.name,
      username: 'John',
    };
    const messageInit = { method: 'POST',
      headers: messageHeaders,
      body: new Blob([JSON.stringify(body, null, 2)], { type: 'application/json' }) };

    fetch('http://localhost:5001/upload', messageInit)
    .then((response) => response.json())
    .then((jsonData) => {
      const accepted = jsonData.probability > 0.69;
      this.setState({ phase: phaseEnum.displayResults, accepted });
    });
  }

  renderFileUpload(width, height) {
    return (
      <FileUpload multiple={false} width={width} height={height}
        style={{ margin: 'auto' }} uploadHandler={this.onGoodUpload}
      />
    );
  }

  renderProcessingView(width, height) {
    return (
      <ProcessingView width={width} height={height} style={{ margin: 'auto' }} />
    );
  }

  renderDisplayResults(width, height) {
    const image = this.state.displayImage();
    return (
      <Results width={width} height={height} image={image}
        accepted={this.state.accepted} style={{ margin: 'auto' }}
      />
    );
  }

  render() {
    let button = null;
    if (this.state.showButton) {
      button = (<button
        className="btn btn-default"
        onClick={this.onUploadImage}
      >
      Submit
    </button>);
    }

    let displayView = null;

    const { width, height } = { width: '60%', height: 500 };

    switch (this.state.phase) {
      case phaseEnum.chooseImage:
        displayView = this.renderFileUpload(width, height);
        break;
      case phaseEnum.processingImage:
        displayView = this.renderProcessingView(width, height);
        break;
      case phaseEnum.displayResults:
        displayView = this.renderDisplayResults(width, height);
        break;
      default:
        break;
    }

    return (
          <div>
            <center>
              {displayView}
              {button}
            </center>
          </div>
        );
  }
}
