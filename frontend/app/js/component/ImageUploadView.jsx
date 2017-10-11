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
    this.state = { showButton: false, getImage: null,
      phase: phaseEnum.chooseImage, accepted: false };
    this.onGoodUpload = this.onGoodUpload.bind(this);
    this.onUploadImage = this.onUploadImage.bind(this);
    console.log(phaseEnum.chooseImage);
  }

  onGoodUpload(uploadSuccessful, getImage) {
    this.setState({ getImage });
    if (uploadSuccessful) {
      this.onUploadImage();
    }
  }

  onUploadImage() {
    console.log('Upload Image Called');
    console.log(this.state.getImage());
    this.setState({ phase: phaseEnum.displayResults });
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
