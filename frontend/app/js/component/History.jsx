/* eslint-disable max-len */
import React from 'react';
import LogoHistory from './LogoHistory';
import LoadingPic from '../../static/images/loading.gif';
import constants from './constants';

const phaseEnum = {
  downloading: 1,
  display: 2,
};

export default class History extends React.Component {
  constructor(props) {
    super(props);
    this.state = { phase: phaseEnum.downloading,
                   images: [] };
  }

  componentDidMount() {
    const url = `${constants.historyUrl}?username=${constants.defaultUser}`;
    fetch(url)
    .then((response) => response.json())
    .then((jsonData) => {
      this.setState({ phase: phaseEnum.display, images: jsonData.response.reverse() });
    });
  }

  renderHistoryImages() {
    let history = null;
    if (this.state.images) {
      history = this.state.images.map(
        (item, index) => {
          let link = item.link;
          let prob = parseFloat(item.result);
          const uploaded = new Date(item.time);
          return <LogoHistory key={index} imgSrc={link} probability={prob} name={uploaded.toDateString()} />;
        });
    } else {
      history = <h1>No items to display</h1>;
    }
    return history;
  }

  renderUploading() {
    return <img src={LoadingPic} alt="Loading" />;
  }

  render() {
    let body = this.renderUploading();
    if (this.state.phase === phaseEnum.display) {
      body = this.renderHistoryImages();
    }

    return (
      <div>
        <div>
          <h2> Welcome to the history page </h2>
          {body}
        </div>
      </div>
    );
  }
}
