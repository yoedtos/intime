### Get firebase cli tools
[More info here](https://firebase.google.com/docs/cli)

### Initial setup to use firebase's emulator

`$ firebase login`

```
i  Firebase optionally collects CLI and Emulator Suite usage and error reporting information to help improve our products. Data is collected in accordance with Google's privacy policy (https://policies.google.com/privacy) and is not used to identify you.

? Allow Firebase to collect CLI and Emulator Suite usage and error reporting information? No

Visit this URL on this device to log in:
https://accounts.google.com/o/oauth2/auth?client_id=563584335869-fgrhgmd47bqnekij5i8b5pr03ho849e6.apps.googleusercontent.com&scope=email%20openid%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcloudplatformprojects.readonly%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Ffirebase%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcloud-platform&response_type=code&state=946485257&redirect_uri=http%3A%2F%2Flocalhost%3A9005

Waiting for authentication...

✔  Success! Logged in as user@domain.com
```

`$ firebase init emulators`

```
     ######## #### ########  ######## ########     ###     ######  ########
     ##        ##  ##     ## ##       ##     ##  ##   ##  ##       ##
     ######    ##  ########  ######   ########  #########  ######  ######
     ##        ##  ##    ##  ##       ##     ## ##     ##       ## ##
     ##       #### ##     ## ######## ########  ##     ##  ######  ########

You're about to initialize a Firebase project in this directory:

/home/user/git/FireBase

=== Project Setup

First, let's associate this project directory with a Firebase project.
You can create multiple project aliases by running firebase use --add,
but for now we'll just set up a default project.

? Please select an option: Don't set up a default project

=== Emulators Setup
? Which Firebase emulators do you want to set up? Press Space to select emulator
s, then Enter to confirm your choices. Authentication Emulator, Firestore Emulat
or, Storage Emulator
? Which port do you want to use for the auth emulator? 9099
? Which port do you want to use for the firestore emulator? 8080
? Which port do you want to use for the storage emulator? 9199
? Would you like to enable the Emulator UI? Yes
? Which port do you want to use for the Emulator UI (leave empty to use any avai
lable port)?
? Would you like to download the emulators now? Yes
i  firestore: downloading cloud-firestore-emulator-v1.15.1.jar...
Progress: ======================================================> (100% of 62MB)
i  storage: downloading cloud-storage-rules-runtime-v1.1.1.jar...
Progress: ======================================================> (100% of 47MB)
i  ui: downloading ui-v1.11.1.zip...

i  Writing configuration info to firebase.json...
i  Writing project information to .firebaserc...

✔  Firebase initialization complete!

#########################################################################
# Have to accept Term of Use in the Firebase console to create a project
##########################################################################
```
`$ firebase emulators:start --project intime-12084`

```
i  emulators: Starting emulators: auth, firestore, storage
⚠  firestore: Did not find a Cloud Firestore rules file specified in a firebase.json config file.
⚠  firestore: The emulator will default to allowing all reads and writes. Learn more about this option: https://firebase.google.com/docs/emulator-suite/install_and_configure#security_rules_configuration.
i  firestore: Firestore Emulator logging to firestore-debug.log
✔  firestore: Firestore Emulator UI websocket is running on 9150.
i  storage: downloading cloud-storage-rules-runtime-v1.1.1.jar...
Progress: ======================================================> (100% of 47MB)
i  ui: downloading ui-v1.11.1.zip...
Progress: =======================================================> (100% of 4MB)
i  ui: Emulator UI logging to ui-debug.log

┌─────────────────────────────────────────────────────────────┐
│ ✔  All emulators ready! It is now safe to connect your app. │
│ i  View Emulator UI at http://127.0.0.1:4000/               │
└─────────────────────────────────────────────────────────────┘

┌────────────────┬────────────────┬─────────────────────────────────┐
│ Emulator       │ Host:Port      │ View in Emulator UI             │
├────────────────┼────────────────┼─────────────────────────────────┤
│ Authentication │ 127.0.0.1:9099 │ http://127.0.0.1:4000/auth      │
├────────────────┼────────────────┼─────────────────────────────────┤
│ Firestore      │ 127.0.0.1:8080 │ http://127.0.0.1:4000/firestore │
├────────────────┼────────────────┼─────────────────────────────────┤
│ Storage        │ 127.0.0.1:9199 │ http://127.0.0.1:4000/storage   │
└────────────────┴────────────────┴─────────────────────────────────┘
Emulator Hub running at 127.0.0.1:4400
Other reserved ports: 4500, 9150

Issues? Report them at https://github.com/firebase/firebase-tools/issues and attach the *-debug.log files.
```

## Using demo mode
<span style="color:green">This feature is described in the docs</span> [here](https://firebase.google.com/docs/emulator-suite/connect_firestore)
>We recommend you use demo projects wherever possible. Benefits include:
>
>Easier setup, since you can run the emulators without ever creating a Firebase project
Stronger safety, since if your code accidentally invokes non-emulated (production) resources, there is no chance of data change, usage and billing
Better offline support, since there is no need to access the internet to download your SDK configuration.

`$ firebase emulators:start --project demo-intime-12084`

```
i  emulators: Starting emulators: auth, firestore, storage
i  emulators: Detected demo project ID "demo-intime-12084", emulated services will use a demo configuration and attempts to access non-emulated services for this project will fail.
⚠  firestore: Did not find a Cloud Firestore rules file specified in a firebase.json config file.
⚠  firestore: The emulator will default to allowing all reads and writes. Learn more about this option: https://firebase.google.com/docs/emulator-suite/install_and_configure#security_rules_configuration.
i  firestore: Firestore Emulator logging to firestore-debug.log
✔  firestore: Firestore Emulator UI websocket is running on 9150.
i  ui: Emulator UI logging to ui-debug.log

┌─────────────────────────────────────────────────────────────┐
│ ✔  All emulators ready! It is now safe to connect your app. │
│ i  View Emulator UI at http://127.0.0.1:4000/               │
└─────────────────────────────────────────────────────────────┘

┌────────────────┬────────────────┬─────────────────────────────────┐
│ Emulator       │ Host:Port      │ View in Emulator UI             │
├────────────────┼────────────────┼─────────────────────────────────┤
│ Authentication │ 127.0.0.1:9099 │ http://127.0.0.1:4000/auth      │
├────────────────┼────────────────┼─────────────────────────────────┤
│ Firestore      │ 127.0.0.1:8080 │ http://127.0.0.1:4000/firestore │
├────────────────┼────────────────┼─────────────────────────────────┤
│ Storage        │ 127.0.0.1:9199 │ http://127.0.0.1:4000/storage   │
└────────────────┴────────────────┴─────────────────────────────────┘
Emulator Hub running at 127.0.0.1:4400
Other reserved ports: 4500, 9150

Issues? Report them at https://github.com/firebase/firebase-tools/issues and attach the *-debug.log files.
```
### Access from external devices
Change the configuration in *firebase.json* to allow emulator to listen outside request. Adding <em>"host": "0.0.0.0"</em>

```json
{
  "emulators": {
    "auth": {
      "port": 9099,
      "host": "0.0.0.0"
    },
    ...
  }
}
```
