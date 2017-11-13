/* eslint-disable max-len */
import React from 'react';
import MLNavbar from './Navbar';
import ImageUploadView from './ImageUploadView';
import CCFormView from './CCFormView';

export default function Home() {
  const appChildren = [
    { name: 'Upload Logo', value: <ImageUploadView /> },
    { name: 'Credit Card Approval', value: <CCFormView /> },
  ];

  return (
    <div>
        <MLNavbar children={appChildren} />
    </div>
  );
}
