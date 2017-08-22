
[![](https://jitpack.io/v/Tadas44/fb-photo-picker.svg)](https://jitpack.io/#Tadas44/fb-photo-picker)

# fb-photo-picker
Facebook photo picker. Library written from scratch using RxJava to easily login to facebook, select albums and images

## Version v0.9.x
- Login to facebook
- Show albums option (Optional, can show all photos)
- Autologin feature (No existing facebook session is needed, to pick photos)

## Getting started

Import dependencies

```groovy
	dependencies {
	        compile 'com.github.Tadas44:fb-photo-picker:0.9.3'
	}
```

On your activity implement onActivityResult method

```java
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RxActivityResults.getInstance().onActivityResult(requestCode, resultCode, data);
    }
```
To login just use RxFacebookLogin

```java
    public Single<LoginResult> login(){
        return RxFacebookLogin.using(activity)
                .setPermissions(Permissions.USER_PHOTOS)
                .login();
    }
```

To pick image just use RxFacebookPhotoPicker

```java
    @Override
    public Single<FbPhoto> pickImage() {
        return RxFacebookPhotoPicker.using(activity)
                .setAutologin(true)
                .setShowAlbums(true)
                .pickPhoto();
    }
```


## Screenshots

[Albums](https://s3.postimg.org/71myenygj/device-2017-08-22-143305.png)
[Photos](https://s3.postimg.org/vtmklwfn7/device-2017-08-22-143339.png)

[![](https://jitpack.io/v/Tadas44/fb-photo-picker.svg)](https://jitpack.io/#Tadas44/fb-photo-picker)

## LICENSE

Copyright (c) 2017-present, Tadas Valaitis.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
