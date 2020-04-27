import base64
import numpy as np
import io
from PIL import Image
import keras
from keras import backend as K
from keras.models import load_model
from keras.preprocessing.image import img_to_array
import h5py
import flask
from flask import Flask,request,jsonify,render_template,url_for,current_app

app=Flask(__name__)

@app.route('/')
def home():
	return "How you doin? ;)"

@app.route('/apitest')
def apitest():
    return "API working"

def get_model():
    global model
    model=load_model('model_keras.h5')
    print("Model loaded!")

def preprocess(image,target):
    if image.mode != "RGB": 
        image = image.convert("RGB") 
      
    # Resize the image to the target dimensions 
    image = image.resize(target)  
      
    # PIL Image to Numpy array 
    image = img_to_array(image)  
      
    # Expand the shape of an array, 
    # as required by the Model 
    image = np.expand_dims(image, axis = 0)  
      
    return image



@app.route('/prediction')
def pred():
    proimage=preprocess(image,target=(200, 200))
    prediction = model.predict(proimage).tolist()

    response = {
                'prediction': {
                    'dog': prediction[0][0],
                     'cat': prediction[0][1]
                              }
                       }
    return response

if __name__ == '__main__':
    image = Image.open("sample1.jpeg")
    print("Loading Keras model...")
    get_model()
    pred()
    app.run(port=12345, debug=True)


    
