import keras.preprocessing.image
from scipy import misc
import glob

for image_path in glob.glob("./images/*.jpg"):
    image = misc.imread(image_path)
    print(image.shape)
    print(image.dtype)
    pass




# tf.Session()
