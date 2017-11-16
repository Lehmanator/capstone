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

  renderExpanded() {
    let body = this.renderBody();
    let footer = this.renderFooter();

    return (
      <div>
        <div className="panel-body">
          {body}
        </div>
        <div className="panel-footer">{footer}</div>
      </div>
    );
  }

  render() {
    let heading = this.renderHeading();

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
                <div className="panel-heading">
                  {heading}
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
