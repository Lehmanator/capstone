import React from "react";
import FileUpload from "./FileUpload";
import ProcessingView from "./Processing";
import Results from "./Results";
import constants from "./constants";
import { makeRequestWithToken } from "./Base";

const phaseEnum = {
  chooseImage: 1,
  processingImage: 2,
  displayResults: 3,
  unknown: 4
};

export default class ImageUploadView extends React.Component {
  static renderProcessingView(width, height) {
    return (
      <ProcessingView
        width={width}
        height={height}
        style={{ margin: "auto" }}
      />
    );
  }
  constructor(props) {
    super(props);
    this.state = {
      showButton: false,
      displayImage: null,
      imageFile: null,
      phase: phaseEnum.chooseImage,
      accepted: false
    };
    this.onGoodUpload = this.onGoodUpload.bind(this);
    this.onUploadImage = this.onUploadImage.bind(this);
    this.sendMessage = this.sendMessage.bind(this);
    this.sendMessageWithToken = this.sendMessageWithToken.bind(this);
  }

  onGoodUpload(uploadSuccessful, displayImage, imageFile) {
    this.setState({ displayImage, imageFile });
    if (uploadSuccessful) {
      this.onUploadImage();
    }
  }

  onUploadImage() {
    this.setState({ phase: phaseEnum.processingImage });
    setTimeout(this.sendMessageWithToken, 1000);
  }

  sendMessageWithToken() {
    makeRequestWithToken(this.sendMessage);
  }

  sendMessage(userToken) {
    const file = this.state.imageFile();
    const messageHeaders = {};
    messageHeaders["Access-Control-Allow-Origin"] = "*";
    messageHeaders["content-type"] = "application/json";
    const body = {
      image: this.state.displayImage(),
      name: file.name,
      token: userToken
    };
    const messageInit = {
      method: "POST",
      headers: messageHeaders,
      body: new Blob([JSON.stringify(body, null, 2)], {
        type: "application/json"
      })
    };

    fetch(constants.uploadImageUrl, messageInit)
      .then(response => response.json())
      .then(
        jsonData => {
          const accepted = jsonData.probability > constants.positivityThreshold;
          this.setState({ phase: phaseEnum.displayResults, accepted });
        },
        error => {
          const jsonError = error.response.data;
          if (jsonError.probability) {
            const accepted = jsonError.probability > 0.69;
            this.setState({ phase: phaseEnum.displayResults, accepted });
          } else {
            this.setState({ phase: phaseEnum.unknown });
            console.error(error); // eslint-disable-line no-console
          }
        }
      );
  }

  renderFileUpload(width, height) {
    return (
      <FileUpload
        multiple={false}
        width={width}
        height={height}
        style={{ margin: "auto" }}
        uploadHandler={this.onGoodUpload}
      />
    );
  }
  renderDisplayResults(width, height) {
    const image = this.state.displayImage();
    return (
      <Results
        width={width}
        height={height}
        image={image}
        accepted={this.state.accepted}
        style={{ margin: "auto" }}
      />
    );
  }

  render() {
    let button = null;
    if (this.state.showButton) {
      button = (
        <button className="btn btn-default" onClick={this.onUploadImage}>
          Submit
        </button>
      );
    }

    let displayView = null;

    const { width, height } = { width: "60%", height: 500 };

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
        displayView = (
          <div className="imageError">
            <h3>Error occurred! :(</h3>
          </div>
        );
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
