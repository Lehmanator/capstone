/* eslint-disable no-console */
import React from 'react';
import FileUpload from './FileUpload';
import ProcessingView from './Processing';
import Results from './Results';
import constants from './constants';
// import axios from 'axios';

const phaseEnum = {
  chooseImage: 1,
  processingImage: 2,
  displayResults: 3,
  unknown: 4,
};

export default class ImageUploadView extends React.Component {

  static renderProcessingView(width, height) {
    return (
      <ProcessingView width={width} height={height} style={{ margin: 'auto' }} />
    );
  }
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
    const messageHeaders = {};
    messageHeaders['Access-Control-Allow-Origin'] = '*';
    messageHeaders['content-type'] = 'application/json';
    const body = {
      image: this.state.displayImage(),
      name: file.name,
      username: constants.defaultUser,
    };
    // const messageInit = { method: 'POST',
    //   headers: messageHeaders,
    //   body: new Blob([JSON.stringify(body, null, 2)], { type: 'application/json' }),
    // };

    fetch(constants.uploadImageUrl, messageInit)
    .then((response) => response.json())
    .then((jsonData) => {
      const accepted = jsonData.probability > constants.positivityThreshold;
      this.setState({ phase: phaseEnum.displayResults, accepted });
      console.log(response);
    }, error => {
      const jsonError = error.response.data;
      console.log(jsonError);
      if (jsonError.probability) {
        const accepted = jsonError.probability > 0.69;
        this.setState({ phase: phaseEnum.displayResults, accepted });
      } else {
        this.setState({ phase: phaseEnum.unknown });
        console.error(error);
      }
    });
  }

  renderFileUpload(width, height) {
    return (
      <FileUpload multiple={false} width={width} height={height}
        style={{ margin: 'auto' }} uploadHandler={this.onGoodUpload}
      />
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
        displayView = ImageUploadView.renderProcessingView(width, height);
        break;
      case phaseEnum.displayResults:
        displayView = this.renderDisplayResults(width, height);
        break;
      case phaseEnum.unknown:
        // TODO: Display an error screen
        break;
      default:
        break;
    }

    return (
          <div>
              {displayView}
              {button}
          </div>
        );
  }
}
