
const baseUrl = 'http://api-capitalfun-dev.us-east-2.elasticbeanstalk.com';

const constants = {
  uploadImageUrl: `${baseUrl}/upload`,
  historyUrl: `${baseUrl}/getHistory`,
  ccUploadData: `${baseUrl}/creditCheck`,
  creditHistoryUrl: `${baseUrl}/getCreditCardHistory`,
  positivityThreshold: 0.69,
  defaultUser: 'mayank',
};

export default constants;
