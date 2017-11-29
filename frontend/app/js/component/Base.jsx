import Rebase from 're-base';
import firebase from 'firebase';

const config = {
  apiKey: 'AIzaSyA26luI5I4rB-QVDcmsaJfENs-Ke-s46Eo',
  authDomain: 'capital-fun.firebaseapp.com',
  databaseURL: 'https://capital-fun.firebaseio.com',
  projectId: 'capital-fun',
  storageBucket: '',
  messagingSenderId: '733920745888',
};

const app = firebase.initializeApp(config);
const base = Rebase.createClass(app.database());
const facebookProvider = new firebase.auth.FacebookAuthProvider();
const googleProvider = new firebase.auth.GoogleAuthProvider();

function makeRequestWithToken(onToken) {
  app.auth().currentUser.getIdToken(true)
  .then(idToken => {
    onToken(idToken);
  }).catch(err => {
    console.log(err); // eslint-disable-line no-console
  });
}

export { app, base, facebookProvider, googleProvider, makeRequestWithToken };
