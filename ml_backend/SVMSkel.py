from sklearn import svm

x = [] #array of size [n_samples, n_features] holding training samples
y = [] #array of class variables (strings or ints), size [n_samples]

model = svm.SVC(kernel='poly', degree=3) #model is support vector classifier

model.fit(x,y) #Fit SVm model based on given data

model.predict(Z) #can then predictnew values. Performs clasification on samples in Z
