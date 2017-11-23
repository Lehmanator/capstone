from sklearn import svm
import csv
from sklearn.externals import joblib
from sklearn.metrics import accuracy_score

X = []
Y = []
with open('data_gen/scaled_new.csv', 'r') as file:
    reader = csv.reader(file)
    next(reader, None)
    for row in reader:
        nums = map(lambda x: float(x), row)
        X.append(nums[0:4])
        Y.append(nums[-1])


model = svm.SVC(
    kernel='linear',
    C=1,
    probability=True,
    verbose=True
) #model is support vector classifier

print "Running training now"
model.fit(X,Y) #Fit SVm model based on given data
print "Completed training"
print "Running validation"
joblib.dump(model, 'first_try_cc.pkl')
X_test = []
Y_test = []
with open('data_gen/test_data.csv', 'r') as file:
    reader = csv.reader(file)
    next(reader, None)
    for row in reader:
        nums = map(lambda x: float(x), row)
        X_test.append(nums[0:4])
        Y_test.append(nums[-1])

predicted = model.predict(X_test)

    # get the accuracy
print accuracy_score(Y_test, predicted)

# model.predict(Z) #can then predictnew values. Performs clasification on samples in Z
