import React from 'react';
import PropTypes from 'prop-types';
import constants from './constants';
import AcceptedPic from '../../static/images/check-mark.png';
import RejectedPic from '../../static/images/forbidden-mark.png';

export default class LogoHistory extends React.Component {
  constructor(props) {
    super(props);
    this.state = { collapsed: true };
    this.changeCollapsed = this.changeCollapsed.bind(this);
  }

  changeCollapsed() {
    this.setState({ collapsed: !this.state.collapsed });
  }

  renderImage() {
    let pic = null;
    if (this.props.imgSrc) {
      pic = this.props.imgSrc;
    }
    return (<img src={pic} alt="Logo Uploaded" />);
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

  renderExpanded() {
    let image = this.renderImage();

    let probability = <h1>Probability - 0</h1>;
    if (this.props.probability) {
      probability = <h1>Probability - {this.props.probability}</h1>;
    }

    return (
<div>
<div className="panel-body">
{image}
</div>
<div className="panel-footer">{probability}</div>
</div>
);
  }

  render() {
    let name = 'ImageName';
    if (this.props.name) {
      name = this.props.name;
    }

    let expandableSection = null;
    if (!this.state.collapsed) {
      expandableSection = this.renderExpanded();
    }

    let resultImage = this.renderResultImage();
    let imageThumbnail = this.renderImageThumbnail();

    return (
      <div>
        <center>
          <div style={{ width: '80%' }} >
            <div className="panel-group">
              <div className="panel panel-default">
              <button onClick={this.changeCollapsed} style={{ width: '100%', height: '100%' }}>
                <div className="panel-heading">
                  <div className="row" >
                    <div className="col-md-2">
                      <img src="" alt="Expand" />
                    </div>
                    <div className="col-md-6">
                      <h1 style={{ textAlign: 'left' }} > {name} </h1>
                    </div>
                    <div className="col-md-2">{imageThumbnail}</div>
                    <div className="col-md-2" >{resultImage}</div>
                  </div>
                </div>
              </button>
              {expandableSection}
              </div>
            </div>
          </div>
        </center>
      </div>
    );
  }
}

LogoHistory.propTypes = {
  imgSrc: PropTypes.string.isRequired,
  probability: PropTypes.number.isRequired,
  name: PropTypes.string.isRequired,
};
