import React from 'react';
import FileUpload from './FileUpload';


export default class ImageUploadView extends React.Component {
  constructor(props) {
    super(props);
    this.state = { showButton: false, getImage: null };
    this.onGoodUpload = this.onGoodUpload.bind(this);
    this.onUploadImage = this.onUploadImage.bind(this);
  }

  onGoodUpload(uploadSuccessful, getImage) {
    this.setState({ showButton: uploadSuccessful, getImage });
  }

  onUploadImage() {
    console.log('Upload Image Called');
    console.log(this.state.getImage());
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

    return (
          <div>
            <center>
              <FileUpload multiple={false} width={'60%'} height={600}
                style={{ margin: 'auto' }} uploadHandler={this.onGoodUpload}
              />
              {button}
            </center>
          </div>
        );
  }
}
