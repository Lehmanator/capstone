import React from 'react';
import BaseFilter from './BaseFilter';
import {TextField, NumberField } from './BaseFilterField';

export default class CCHistoryFilter extends BaseFilter {
  constructor(props) {
    super(props);
    this.filterObjects = {};
    this.filters = {
      applicantName: <TextField name="applicantName" label="Applicant Name"
        ref={(child) => { this.filterObjects.applicantName = child; }}
      />,
      applicantID: <TextField name="applicantID" label="Applicant ID"
        ref={(child) => { this.filterObjects.applicantID = child; }}
      />,
      minimumAge: <NumberField name="age" label="Minimum Age"
        isMinimum="true"
        ref={(child) => { this.filterObjects.minimumAge = child; }}
      />,
    };
  }

  getFilterFields() {
    return this.filters;
  }

  getFilterObjects() {
    return this.filterObjects;
  }

}
