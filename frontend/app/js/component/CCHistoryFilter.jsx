import BaseFilter from './BaseFilter';

export default class CCHistoryFilter extends BaseFilter {
  getFilterFields() {
    return {
      applicantName: 'applicantName',
    };
  }

  getLabels() {
    return {
      applicantName: 'Applicant Name:',
    };
  }

}
