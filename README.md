### Dagger 2 Build Performance Test

This project is a playground to test the build speed performance of different  [Dagger 2](https://github.com/google/dagger) configurations:
* Kotlin + SubComponents
* Kotlin + Components
    
#### Methodology

This project has different gradle modules to each Dagger 2 configuration. Each module has the following structure:
```
src.main.java.com.themishkun.<module_name>/
├── di/
│   ├── components/
│   │   ├── Component0.kt
│   │   ├── Component1.kt
│   │   └── ...
│   ├── main/
│   │   ├── Main.kt # Here lies all stubs for dependencies
│   │   └── MainComponent.kt 
│   └── modules/
│       ├── Module0.kt
│       ├── Module1.kt
│       └── ...
├── features/
│   ├── Feature0.kt
│   ├── Feature1.kt
│   └── ...
└── MainActivity.kt
```
Each `ComponentX` is a Dagger Component or SubComponent depending on source set. It references the corresponding `ModuleX` and injects `FeatureX`
To see how they are generated, look for `poet` module.

##### Commands

Each set of features is generated via `./generate.sh` script:
```
./generate.sh <number_of_features>
```
Then you need to actually build generated sources. Each `build` task is already depends on `clean`, so you just need to execute this command:
```
./gradlew :<module_name>:build --no-daemon --scan
```
where `<module_name>` is `kotlincomponent` or `kotlinsubcomponent` respectively.

### Results

Dagger 2 Kotlin Component vs SubComponent tests showed that the difference between these two configurations is negligible. Dagger 2 SubComponent variant is slightly faster: _1.54s_ vs _1.57s_ with _100_ features on my Macbook Pro Late 2015.

### Future Work 

It seems that pure Java Dagger tree injecting in Kotlin files might be faster because of better Java annotation processing. We should check that

### Contributing
If you feel that my methodology is wrong or there are some tricks to speed up Dagger 2 build times on android projects, feel free to post an Issue or make a Pull Request
