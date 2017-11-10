import matplotlib.pyplot as plt
import numpy as np
from scipy.interpolate import UnivariateSpline
import csv

numfigs = 1


class User:
    def __init__(self, c_data, id_data):
        self.credit_data = c_data
        self.id_data = id_data


class CreditData:
    def __init__(self, var_dict, name):
        self.name = name
        self.vars = {}
        for var in var_dict.keys():
            self.vars[var] = DistData(var + name, var_dict[var])

    def plot(self):
        num = numfigs
        figs = []
        for prop in self.vars:
            num += 1
            figs.append(self.vars[prop].plot(num))
        return figs

    def __str__(self):
        s = ""
        for var in self.vars:
            s += var + "\n"
            s += str(self.vars[var])
        return s

    def getdata(self):
        """
        :rtype: np.Array Two dimension array containing all the data in the object
        """
        result = []
        for i in range(len(self.vars['age'].var_data['yes'])):
            age = self.vars['age'].var_data['yes'][i]
            income = self.vars['income'].var_data['yes'][i]
            rent = self.vars['rent'].var_data['yes'][i]
            scores = self.vars['scores'].var_data['yes'][i]
            decision = int(1)
            result.append([age, income, rent, scores, decision])
            pass
        for i in range(len(self.vars['age'].var_data['no'])):
            age = self.vars['age'].var_data['no'][i]
            income = self.vars['income'].var_data['no'][i]
            rent = self.vars['rent'].var_data['no'][i]
            scores = self.vars['scores'].var_data['no'][i]
            decision = int(1)
            result.append([age, income, rent, scores, decision])
            pass
        return np.array(result)

class DistData:
    def __init__(self, name, v_data):
        self.name = name
        self.var_data = v_data
        self.dist = self.__find_dist__(v_data)

    def __str__(self):
        return "name: " + str(self.name) + "var_data: " + str(self.var_data) + "dist: " + str(self.dist)

    @staticmethod
    def __find_dist__(var_data):
        # Assuming that var_data is dict containing two keys: "yes" and "no", with each key containing the input data
        # for credit score approvals and corresponding properties
        yes_data = var_data["yes"]
        no_data = var_data["no"]
        dists = {"yes": [], "no": []}
        p1, x1 = np.histogram(yes_data, bins=len(yes_data) // 10)
        x1 = x1[:-1] + (x1[1] - x1[0]) / 2
        norm_y1 = [float(a) / sum(p1) for a in p1]
        dists["yes"] = [x1, norm_y1]
        p2, x2 = np.histogram(no_data, bins=len(yes_data) // 10)
        x2 = x2[:-1] + (x2[1] - x2[0]) / 2
        norm_y2 = [float(a) / sum(p2) for a in p2]
        dists["no"] = [x2, norm_y2]
        return dists
        pass

    def plot(self, num):
        f = UnivariateSpline(self.dist["yes"][0].tolist(), self.dist["yes"][1], s=10)
        g = UnivariateSpline(self.dist["no"][0].tolist(), self.dist["no"][1], s=10)
        x1 = self.dist["yes"][0]
        x2 = self.dist["no"][0]
        plt.plot(x1, f(x1), 'b', label="yes")
        plt.plot(x2, g(x2), 'r', label="no")
        plt.title(self.name)
        plt.legend(loc="upper left")
        plt.xlabel(self.name + " value")
        plt.ylabel("Frequency")
        return plt.figure(num)
        pass

    def generate(self, count, type):
        if type:
            return np.random.choice(self.dist["yes"][0].tolist(), count, self.dist["yes"][1])
        else:
            return np.random.choice(self.dist["no"][0].tolist(), count, self.dist["no"][1])


def mean(var_data):
    sum = 0.0
    for x in var_data:
        sum = sum + x
    return sum / len(var_data)


def standard_dev(var_data):
    pass


def gen_dist(dist_str, params):
    if dist_str == 'normal':
        return np.normal(**params)
        # TODO more distributions


def histogram(var_data):
    count, bins, ignored = plt.hist(var_data, 50, normed=True)
    plt.show()
    pass


def read_data():
    return {"income": {"yes": np.load("income_sample_yes.npy"), "no": np.load("income_sample_no.npy")},
            "age": {"yes": np.load("age_sample_yes.npy"), "no": np.load("age_sample_no.npy")},
            "scores": {"yes": np.load("cscore_sample_yes.npy"), "no": np.load("cscore_sample_no.npy")},
            "rent": {"yes": np.load("expenses_sample_yes.npy"), "no": np.load("expenses_sample_no.npy")}
            }


var_data = read_data()
# print var_data
credit_data = CreditData(var_data, "orig")
# print credit_data
# f = credit_data.plot()
# numfigs += len(f)

gen_dict = {}
for i in credit_data.vars:
    gen_dict[i] = {"yes": credit_data.vars[i].generate(50000, True), "no": credit_data.vars[i].generate(50000, False)}
    pass
gen_data = CreditData(gen_dict, "gen")
with open('gen_data.csv', 'wb') as file:
    data = gen_data.getdata()
    writer = csv.writer(file)
    writer.writerow(["age", "income", "expenses", "scores", "decision"])
    for i in data:
        writer.writerow(i)
# print(gen_data)
# g = gen_data.plot()
# plt.show()
