import csv
import time
from sklearn import svm
from sklearn.metrics import accuracy_score
from sklearn.externals import joblib
from sklearn.externals.joblib import Parallel, delayed
from multiprocessing import Pool


def train():
    X = []
    Y = []

    print "Opening: data_gen/scaled_new.csv"

    with open('data_gen/scaled_new.csv', 'r') as file:
        reader = csv.reader(file)
        next(reader, None)
        for row in reader:
            nums = map(lambda x: float(x), row)
            X.append(nums[0:4])
            Y.append(nums[-1])

    print "Initializing model"

    model = svm.SVC(
        kernel='linear',
        C=1,
        probability=True,
        verbose=True
    ) #model is support vector classifier

    print "Running training now"

    model.fit(X,Y) # Fit SVM model based on given data

    print "Completed training"
    return model


def validate(model):
    print "Running validation"

    joblib.dump(model, 'first_try_cc.pkl')

    X_test = []
    Y_test = []

    print "Opening: data_gen/test_data.csv"

    with open('data_gen/test_data.csv', 'r') as file:
        reader = csv.reader(file)
        next(reader, None)
        for row in reader:
            nums = map(lambda x: float(x), row)
            X_test.append(nums[0:4])
            Y_test.append(nums[-1])

    print "Predicting"

    predicted = model.predict(X_test)

    # get the accuracy
    accuracy = accuracy_score(Y_test, predicted)
    print accuracy
    return accuracy

# model.predict(Z) #can then predict new values. 
# Performs clasification on samples in Z


# Taken from http://www.scipy-lectures.org/advanced/scikit-learn/#support-vector-machines-svms-for-classification
# and http://scikit-learn.org/stable/modules/svm.html


if __name__ == '__main__':
    start_time = time.time()
    
    p = Pool()

    model = train()
    accuracy = validate(model)

    end_time = time.time()
