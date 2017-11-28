import React from 'react';
import PropTypes from 'prop-types';
import CollapsablePanel from './CollapsablePanel';

export default class BaseFilter extends CollapsablePanel {
  constructor(props) {
    super(props);

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleReset = this.handleReset.bind(this);
  }

  // Override in child classes

  getFilterFields() {
    return {};
  }

  getFilterObjects() {
    return {};
  }

  engageFilter(filters) {
    console.log(filters);
    if (this.props.onFilter) {
      this.props.onFilter(filters);
    }
  }

  // Event Handlers

  handleReset() {
    const filterFields = this.getFilterObjects();
    const resetState = (key) => (filterFields[key].resetState());
    Object.keys(filterFields).forEach(resetState);
    this.engageFilter([]);
  }

  handleSubmit() {
    const filters = [];
    const filterFields = this.getFilterObjects();
    const addFilter = (key) => (filters[key] = this.createFilter(filterFields[key], filters));
    Object.keys(filterFields).forEach(addFilter);
    this.engageFilter(filters);
  }

  // Convenience Methods

  createFilter(filterField, filters) {
    const func = filterField.createFilter();
    if (func) {
      filters.push(func);
    }
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
    const filterForm = Object.keys(filterFields).map(
                    (key) => filterFields[key]
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
