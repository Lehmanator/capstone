import keras.preprocessing.image as image
import os

datagen = image.ImageDataGenerator(
    rotation_range=40,
    width_shift_range=0.2,
    height_shift_range=0.2,
    rescale=1./255,
    shear_range=0.2,
    zoom_range=0.2,
    horizontal_flip=True,
    fill_mode='nearest'
)

for path in os.listdir(os.getcwd()+'/images/downloaded/'):
    print(path)
    for imnum, imagepath in enumerate(os.listdir(os.getcwd()+'/images/downloaded/'+path)):
        try:
            mypath = os.getcwd() + '/images/downloaded/'+path+'/'+imagepath
            img = image.load_img(mypath)
            x = image.img_to_array(img)
            x = x.reshape((1,)+x.shape)

            i = 0
            if not os.path.exists(os.getcwd() + "/images/generated/" + path):
                os.makedirs(os.getcwd() + "/images/generated/" + path)
            for batch in datagen.flow(x, batch_size=1, save_to_dir='./images/generated/'+path, save_prefix='p_logo', save_format='jpg'):
                i += 1
                print("Processed image number "+str(imnum) + " for " + path) 
                if i > 20:
                    break
        except IOError:
            print("could not open file: " + imagepath)
