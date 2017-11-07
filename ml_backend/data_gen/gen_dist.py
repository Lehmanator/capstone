import numpy as np
import scipy as sci
import matplotlib as plt

class User:
    def __init__(self, credit_data, id_data):
        self.credit_data = credit_data
        self.id_data = id_data


class CreditData:
    # TODO gather list of variables
    def __init__(self, var_dict):
        for var in var_dict.keys():
            self.vars = DistData(var, var_dict[var])
            
    def gen_data(self):
        pass


class DistData:
    def __init__(self, name, var_data):
        self.name = name
        self.dist = find_dist(var_data)
        self.dist_params = find_dist_params(self.dist, var_data)
        self.sample = gen_dist(self.dist_params)


def mean(var_data):
    sum = 0.0    
    for x in var_data:
        sum = sum + x
    return sum/len(var_data)

def standard_dev(var_data):
    pass

def find_dist(var_data):
    # TODO Figure out how to do this. 
    pass



def find_dist_params(dist_str, var_data):
    if dist_str == 'normal':
        mean = mean(var_data)
        size = len(var_data)
        std = standard_dev(var_data, mean)
        return np.random.normal(mean, size, std)
    elif dist_str == 'binomial':
        pass
    elif dist_str == 'exponential':
        pass
    elif dist_str == 'geometric':
        pass
    elif dist_str == 'hypergeometric':
        pass
    elif dist_str == 'lognormal':
        pass
    elif dist_str == 'poisson':
        pass
    # TODO more distributions

def gen_dist(dist_str, params):
    if dist_str == 'normal'
        return np.normal(**params)
    # TODO more distributions

def histogram(var_data):
    count, bins, ignored = plt.hist(var_data, 50, normed=True)
    plt.show()

