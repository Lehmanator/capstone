/* eslint-disable max-len */
import React from 'react';
import MLNavbar from './Navbar';
import ImageUploadView from './ImageUploadView';

export default function Home() {
  const appChildren = [
    { name: 'Upload Logo', value: <ImageUploadView /> },
    { name: 'Credit Card Approval', value: <div><p>Goodbye</p></div> },
  ];

  return (
    <div>
        <MLNavbar children={appChildren} />
    </div>
  );
}
