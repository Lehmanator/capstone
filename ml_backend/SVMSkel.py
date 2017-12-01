import csv
import time
import os
from sklearn import svm
from sklearn.metrics import accuracy_score
from sklearn.externals import joblib
from sklearn.externals.joblib import (Parallel,
                                      delayed,
                                      parallel_backend,
                                      register_parallel_backend)
from ipyparallel import Client
from ipyparallel.joblib import IPythonParallelBackend
# from multiprocessing import Pool

# Run ipython cluster before running this script
# Run using python3
# Install pip packages: ipyparallel
# https://ipython.org/ipython-doc/3/parallel/parallel_process.html

def start_engines():
    print("Starting engines...")
    os.system("ipython profile create --parallel --profile=myprofile")
    os.system("ipcluster start -n 4 --profile=myprofile")
    print("Engines started.")

def train():
    X = []
    Y = []

    print("Opening: data_gen/scaled_new.csv")

    with open('data_gen/scaled_new.csv', 'r') as file:
        reader = csv.reader(file)
        next(reader, None)
        for row in reader:
            nums = list(map(lambda x: float(x), row))
            X.append(nums[0:4])
            Y.append(nums[-1])

    print("Initializing model")

    model = svm.SVC(
        kernel='linear',
        C=1,
        probability=True,
        verbose=True
    ) # Model is support vector classifier

    print("Running training now")
    
    # Create a client
    c = Client(profile='myprofile')
    print(c.ids)
    bview = c.load_balanced_view()

    register_parallel_backend('ipyparallel', lambda :
                              IPythonParallelBackend(view=bview))

    # Run fit function concurrently
    with parallel_backend('ipyparallel'):
        model.fit(X,Y) # Fit SVM model based on given data

    print("Completed training")
    return model


def validate(model):
    print("Running validation")

    joblib.dump(model, 'first_try_cc.pkl')

    X_test = []
    Y_test = []

    print("Opening: data_gen/test_data.csv")

    with open('data_gen/test_data.csv', 'r') as file:
        reader = csv.reader(file)
        next(reader, None)
        for row in reader:
            nums = map(lambda x: float(x), row)
            X_test.append(nums[0:4])
            Y_test.append(nums[-1])

    print("Predicting")

    predicted = model.predict(X_test)

    # Get the accuracy
    accuracy = accuracy_score(Y_test, predicted)
    print(accuracy)
    return accuracy

# model.predict(Z) #can then predict new values. 
# Performs clasification on samples in Z


# Taken from http://www.scipy-lectures.org/advanced/scikit-learn/#support-vector-machines-svms-for-classification
# and http://scikit-learn.org/stable/modules/svm.html


if __name__ == '__main__':
    start_time = time.time()
    
    start_engines()

    model = train()
    accuracy = validate(model)

    end_time = time.time()
    print("Start: " + start_time)
    print("End  : " + end_time)
