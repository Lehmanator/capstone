import React from 'react';
import CollapsablePanel from './CollapsablePanel';
import PropTypes from 'prop-types';
import constants from './constants';
import AcceptedPic from '../../static/images/check-mark.png';
import RejectedPic from '../../static/images/forbidden-mark.png';

export default class LogoHistory extends CollapsablePanel {
  renderImage() {
    let pic = null;
    if (this.props.imgSrc) {
      pic = this.props.imgSrc;
    }
    return (<img src={pic} style={{ maxHeight: '100%', maxWidth: '100%' }} alt="Logo Uploaded" />);
  }

  renderImageThumbnail() {
    let pic = null;
    if (this.props.imgSrc) {
      pic = this.props.imgSrc;
    }
    return (<img src={pic} style={{ height: 100 }} alt="Logo Thumbnail" />);
  }

  renderResultImage() {
    let accepted = RejectedPic;
    let alt = 'Rejected';
    if (this.props.probability) {
      if (this.props.probability > constants.positivityThreshold) {
        accepted = AcceptedPic;
        alt = 'accepted';
      }
    }
    return (<img src={accepted} style={{ height: 100 }} alt={alt} />);
  }

  // SECTION: Overrides from CollapsablePanel

  renderHeading() {
    let name = 'ImageName';
    if (this.props.name) {
      name = this.props.name;
    }

    let resultImage = this.renderResultImage();
    let imageThumbnail = this.renderImageThumbnail();

    return (
      <div className="row" >
        <div className="col-md-2"></div>
        <div className="col-md-6">
          <h1 style={{ textAlign: 'left' }} > {name} </h1>
        </div>
        <div className="col-md-2">{imageThumbnail}</div>
        <div className="col-md-2" >{resultImage}</div>
      </div>
    );
  }

  renderBody() {
    return this.renderImage();
  }

  renderFooter() {
    let probability = 0;
    if (this.props.probability) {
      probability = this.props.probability;
    }
    const probabilityDisplay = <h1>Probability: {probability}</h1>;
    return probabilityDisplay;
  }
}

LogoHistory.propTypes = {
  imgSrc: PropTypes.string.isRequired,
  probability: PropTypes.number.isRequired,
  name: PropTypes.string.isRequired,
};
