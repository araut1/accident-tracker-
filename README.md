# **GPS location SMS Emergency Alert System**

---

## Group


Group Number: Group 15

Group members: Abhishek Dinkar Raut & Alekhya Sarakallu

---

## Note 

## Important: 
### **For testing purpose APK is available under direcory: /applicationAPK**

---

## Main goal


The application provides nimble functionality to send a distress call with the GPS location. Users can use the dashboard to store emergency contact person information. In case of emergency, instead of spending critical time on attempting a call or a message, opening the app and shaking it once will send alert SMS with location information to the contact person. Also, Web services (REST API) will transfer photo of the situation captured through the application to the CNN model hosted in the cloud (Google CLoud Platform) and will send relevant information to concerned Department (Fire department, Police department, Hospitals etc.).

---

## Application scenario

In case of an emergency, the victim will open the application and shake the device. The system will send an SMS with an emergency message and longitude and latitude of the victim’s location to the guardian whose contact information is stored in the database. Based on the captured photo, Tensorflow based Image Classifier will separate the event into Fire (Arson), Homicide (Gun Violence), and a car accident. Based on the classification, respective departments will be contacted with relevant information. For example, in the case of a car accident, the application will contact the Hospital, Traffic Police, Insurance Company, and Towing Company with the photo of incident and location of the accident.

---

## Run

1. Open project in android studio
1. Build-> Build Bundle/APK -> Build APK
1. Install any dependencies
1. Copy the APK to a mobile phone
1. Install and authenticate permissions
1. Register User
1. Register Emergency contact
1. In case of emergency, open Accident Detection     
1. Send alert by shaking device
---

## Image Classifier

* Transfer Lerning is implemented for building image classifier with TensorFlow
* Data: Car Accident ( 2481 Images), Guns ( 1657 Images), Fire ( 2266 Images)
* Data Source: Google and ImageNet

1. Script to Train Model:


    IMAGE_SIZE=224
    ARCHITECTURE="mobilenet_0.50_${IMAGE_SIZE}"
    python -m scripts.retrain \
    --bottleneck_dir=tf_files/bottlenecks \
    --model_dir=tf_files/models/"${ARCHITECTURE}" \
    --summaries_dir=tf_files/training_summaries/"${ARCHITECTURE}" \
    --output_graph=tf_files/retrained_graph.pb \
    --output_labels=tf_files/retrained_labels.txt \
    --architecture="${ARCHITECTURE}" \
    --image_dir=tf_files/YOUR_IMAGE_DIRECTORY_HERE


2. Script to to Test Model:


    python -m scripts.label_image \
    --graph=tf_files/retrained_graph.pb  \
    --image=YOUR_PATH_TO_IMAGE_HERE


---

## Software 

Android Studio

TensorFlow

Google Cloud Platform

