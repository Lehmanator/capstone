import React from 'react';
import FileUpload from './FileUpload';


export default class ImageUploadView extends React.Component {
  render() {
    return (
            <div>
            <center>
              <FileUpload multiple={false} width={'60%'} height={600} style={{ margin: 'auto' }} />
            </center>
          </div>
        );
  }
}
