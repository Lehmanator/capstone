import React from 'react';
import LoadingPic from '../../static/images/loading.gif';

const phaseEnum = {
  downloading: 1,
  display: 2,
};

export default class History extends React.Component {
  constructor(props) {
    super(props);
    this.state = { phase: phaseEnum.downloading,
                   history: [],
                   filters: [] };
  }

  componentDidMount() {
    const url = this.getHistoryURL();
    if (url) {
      fetch(url)
        .then((response) => response.json())
        .then((jsonData) => {
          this.setState({ phase: phaseEnum.display, history: jsonData.response.reverse() });
        });
    }
  }

  // SECTION: Override these in the child class

  getHistoryURL() {
    return null;
  }

  renderPageHeader() {
    return (<h2> Welcome to the history page </h2>);
  }

  renderFilters() {
    return null;
  }

  renderHistoryItems(item, index) {
    // eslint-disable-next-line no-console
    console.log(item);
    return (<div key={index}>No Display Implemented - {index}</div>);
  }

  // End Section

  renderHistory() {
    let history = null;
    if (this.state.history) {
      const items = this.state.history.filter((item) => {
        let passes = true;
        this.state.filters.forEach(
          (filterFunction) => { passes = passes && filterFunction(item); }
        );
        return passes;
      });

      history = items.map(
        (item, index) => this.renderHistoryItems(item, index)
      );
    } else {
      history = <h1>No items to display</h1>;
    }
    return history;
  }

  renderUploading() {
    return <img src={LoadingPic} alt="Loading" />;
  }

  render() {
    const header = this.renderPageHeader();
    const filter = this.renderFilters();
    let body = this.renderUploading();
    if (this.state.phase === phaseEnum.display) {
      body = this.renderHistory();
    }

    return (
      <div>
        {header}
        {filter}
        {body}
      </div>
    );
  }
}
