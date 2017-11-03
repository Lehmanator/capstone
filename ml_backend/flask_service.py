from flask import Flask, request, jsonify, make_response, abort
import json
from keras.models import Sequential
from keras.layers import Conv2D, MaxPooling2D, Activation, Dropout, Flatten, Dense
from scipy.misc import imresize
from sklearn import svm
from PIL import Image
import cStringIO
import base64
import numpy

app = Flask(__name__)

model = Sequential()
model.add(Conv2D(32, (3, 3), input_shape=(150, 150, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Conv2D(32, (3, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Conv2D(64, (3, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Flatten())  # this converts our 3D feature maps to 1D feature vectors
model.add(Dense(64))
model.add(Activation('relu'))
model.add(Dropout(0.5))
model.add(Dense(1))
model.add(Activation('sigmoid'))

model.load_weights('first_try.h5')

model.compile(loss='binary_crossentropy',
              optimizer='rmsprop',
              metrics=['accuracy'])

@app.before_request
def checkSource():
    if request.method != "POST" or request.headers['Access-Control-Set-Origin'] != "https://www.google.com":
        abort(401)


@app.route('/process_image', methods=['POST'])
def process_image():
    if request.method == "POST":
        # TODO: Add source verification
        data = request.get_data()  # send raw base64 binary data of image
        json_data = json.loads(data)
        image_data = json_data['image']  # kind of pointless right now, but needed for json POSTs
        if not image_data:
            abort(401)
            pass
        image_string = cStringIO.StringIO(base64.b64decode(image_data))  # decode image and store it in a string buffer
        image = Image.open(image_string).convert('RGB')  # open as a PIL image
        # print(image)
        image = numpy.array(image)  # create a numpy array from the PIL image
        image = imresize(image, (150, 150, 3), mode='RGB')  # resize image to 150x150x3 pixels
        imagearr = numpy.empty(
            (1, 150, 150, 3))  # since predict expects an array create a numpy array to hold our 1 picture
        imagearr[0] = image  # set our picture to the first index of imagearr
        statistics = model.predict(imagearr)  # get the probability of PSU logo
        resp = make_response('{"P(PSU Logo)":' + str(statistics[0][0]) + '}')  # create the response
        resp.headers['Content-Type'] = "application/json"
        return resp, 200
    else:
        abort(401)
        pass
    pass


@app.route('/ccdata', methods=['POST'])
def ccdata():
    app.logger.critical(request.headers)
    if request.method == "POST":
        data = request.get_data()
        app.logger.debug(data)
        data = json.loads(data)
        if not data:
            abort(401)
            pass
        try:
            cscore = data['credit_score']
            age = data['age']
            expenses = data['expenses']
            income = data['income']
        except KeyError:
            abort(400, KeyError)
        # TODO: ML Black magic
        resp = make_response('{"P(Accepted)":'+str(69)+'}')
        resp.headers['Content-Type'] = "application/json"
        return resp, 200
    else:
        abort(401)
        pass
    pass



@app.errorhandler(401)
def error401(errorData):
    resp = make_response('{"error": "Unauthorized"}')
    resp.headers['Content-Type'] = "application/json"
    return resp, 401


@app.errorhandler(400)
def error400(errorData):
    resp = make_response('{"error": "Bad Request"}')
    resp.headers['Content-Type'] = "application/json"
    return resp, 400
