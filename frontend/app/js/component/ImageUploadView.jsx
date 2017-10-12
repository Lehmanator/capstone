import React from 'react';
import FileUpload from './FileUpload';
import ProcessingView from './Processing';
import Results from './Results';

const phaseEnum = {
  chooseImage: 1,
  processingImage: 2,
  displayResults: 3,
};

export default class ImageUploadView extends React.Component {
  constructor(props) {
    super(props);
    this.state = { showButton: false, displayImage: null,
      imageFile: null, phase: phaseEnum.chooseImage, accepted: false };
    this.onGoodUpload = this.onGoodUpload.bind(this);
    this.onUploadImage = this.onUploadImage.bind(this);
    this.setStateToFinished = this.setStateToFinished.bind(this);
    console.log(phaseEnum.chooseImage);
  }

  onGoodUpload(uploadSuccessful, displayImage, imageFile) {
    this.setState({ displayImage, imageFile });
    if (uploadSuccessful) {
      this.onUploadImage();
    }
  }

  onUploadImage() {
    console.log('Upload Image Called');
    console.log(this.state.displayImage());
    this.setState({ phase: phaseEnum.processingImage });
    setTimeout(this.sendMessage.bind(this), 1000);
  }

  setStateToFinished() {
    console.log(this);
    this.setState({ phase: phaseEnum.displayResults });
  };

  sendMessage() {
    console.log('Send Message Called');
    const file = this.state.imageFile();
    console.log('Send Message Called');
    const messageHeaders = new Headers();
    messageHeaders.append('Access-Control-Allow-Origin', '*');
    console.log(messageHeaders);
    const body = {
      'image': this.state.displayImage(),
      'name': file.name,
      'user': 'John',
    };
    const messageInit = { method: 'POST',
      headers: messageHeaders,
      body: new Blob([JSON.stringify(body, null, 2)], { type: 'application/json' }) };
    console.log(messageInit);

    fetch('http://localhost:8090/upload', messageInit).then((response) => this.setStateToFinished());
    // .then((response) => response.json())
    // .then((jsonData) => {
    //   console.log(jsonData);
    //   setStateToFinished();
    // });
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
    const image = this.state.getImage();
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
    console.log(this.state.phase);

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
