import React from 'react';
import PropTypes from 'prop-types';
import CollapsablePanel from './CollapsablePanel';

export default class BaseFilter extends CollapsablePanel {
  constructor(props) {
    super(props);
    const filterFields = this.getFilterFields();
    const addState = (key) => (this.state[key] = '');
    Object.keys(filterFields).forEach(addState);

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleReset = this.handleReset.bind(this);
  }

  // Override in child classes

  getFilterFields() {
    return {};
  }

  getLabels() {
    return {};
  }

  engageFilter(filters) {
    if (this.props.onFilter) {
      this.props.onFilter(filters);
    }
  }

  // Event Handlers

  handleChange(event) {
    const newState = {};
    newState[event.target.id] = event.target.value;
    this.setState(newState);
  }

  handleReset() {
    const newState = {};
    const filterFields = this.getFilterFields();
    const resetState = (key) => (newState[key] = '');
    Object.keys(filterFields).forEach(resetState);
    this.setState(newState);
    this.engageFilter([]);
  }

  handleSubmit() {
    const filters = [];
    const filterFields = this.getFilterFields();
    const addFilter = (key) => (filters[key] = this.createFilter(key, filterFields[key], filters));
    Object.keys(filterFields).forEach(addFilter);
    this.engageFilter(filters);
  }

  // Convenience Methods

  createFilter(stateName, itemName, filters) {
    if (this.state[stateName]) {
      const compare = this.state[stateName];
      filters.push((item) => (item[itemName].indexOf(compare) > -1));
    }
  }

  createFormItem(stateName, labelName, index) {
    return (
        <div className="row form-group" key={index}>
          <label className="control-label col-md-2">{labelName}</label>
          <input type="text" id={stateName}
            className="control-input col-md-9"
            value={this.state[stateName]} onChange={this.handleChange}
          />
        </div>
    );
  }

  // Overridden from Collapsable Panel

  renderPanelHeading() {
    return (<h3> Filter Applications </h3>);
  }

  renderPanelFooter() {
    return null;
  }

  renderBody() {
    const filterFields = this.getFilterFields();
    const labels = this.getLabels();
    const createFormItem = this.createFormItem.bind(this);
    const filterForm = Object.keys(filterFields).map(
                    (key, index) => {
                      let label = key;
                      if (labels[key]) {
                        label = labels[key];
                      }
                      return createFormItem(key, label, index);
                    }
                    );
    return (
        <div>
          {filterForm}
          <div className="row">
            <input type="button" value="Filter"
              onClick={this.handleSubmit}
              className="btn btn-primary"
            />
            <input type="button" value="Reset"
              onClick={this.handleReset}
              className="btn btn-default"
            />
          </div>
        </div>
      );
  }
}

BaseFilter.propTypes = {
  onFilter: PropTypes.func.isRequired,
};
