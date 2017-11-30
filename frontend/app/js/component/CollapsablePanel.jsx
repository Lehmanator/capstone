import React from 'react';

export default class CollapsablePanel extends React.Component {
  constructor(props) {
    super(props);
    this.state = { collapsed: true };
    this.changeCollapsed = this.changeCollapsed.bind(this);
  }

  changeCollapsed() {
    this.setState({ collapsed: !this.state.collapsed });
  }

  renderHeading() {
    return (<div>Heading</div>);
  }

  renderBody() {
    return (<div>Body</div>);
  }

  renderFooter() {
    return null;
  }

  renderPanelHeading() {
    const heading = this.renderHeading();
    return (
      <div className="panel-heading">
        {heading}
      </div>
    );
  }

  renderPanelBody() {
    let body = this.renderBody();
    return (
      <div className="panel-body">
        {body}
      </div>
    );
  }

  renderPanelFooter() {
    let footer = this.renderFooter();
    return (
      <div className="panel-footer">{footer}</div>
    );
  }

  renderExpanded() {
    let body = this.renderPanelBody();
    let footer = this.renderPanelFooter();

    return (
      <div>
        {body}
        {footer}
      </div>
    );
  }

  render() {
    const headingPanel = this.renderPanelHeading();

    let expandableSection = null;
    if (!this.state.collapsed) {
      expandableSection = this.renderExpanded();
    }

    return (
      <div>
        <center>
          <div style={{ width: '80%' }} >
            <div className="panel-group">
              <div className="panel panel-default">
              <button onClick={this.changeCollapsed} style={{ width: '100%', height: '100%' }}>
                {headingPanel}
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
