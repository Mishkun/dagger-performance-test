# Dagger 2 Build Performance Test

This project is a playground to test the build speed performance of different  [Dagger 2](https://github.com/google/dagger) configurations:
* Kotlin + SubComponents
* Kotlin + Components
* Kotlin features + Java Module/SubComponents
    
## Methodology

This project has different gradle modules to each Dagger 2 configuration. Each module has the following structure:
```
src.main.java.com.themishkun.<module_name>/
├── di/
│   ├── components/
│   │   ├── Component0.(kt|java)
│   │   ├── Component1.(kt|java)
│   │   └── ...
│   ├── main/
│   │   ├── Main.kt # Here lies all stubs for dependencies
│   │   └── MainComponent.kt 
│   └── modules/
│       ├── Module0.(kt|java)
│       ├── Module1.(kt|java)
│       └── ...
├── features/
│   ├── Feature0.kt
│   ├── Feature1.kt
│   └── ...
└── MainActivity.kt
```
Each `ComponentX` is a Dagger Component or SubComponent depending on source set. It references the corresponding `ModuleX` and injects `FeatureX`
To see how they are generated, look for `poet` module.

#### Commands

Each set of features is generated via `./generate.sh` script:
```
./generate.sh <number_of_features>
```
Then you need to actually build generated sources. Each `build` task is already depends on `clean`, so you just need to execute this command:
```
./gradlew :<module_name>:build --no-daemon --scan
```
where `<module_name>` is `kotlincomponent` or `kotlinsubcomponent` respectively.

## Results

#### Kotlin SubComponent vs Component

Dagger 2 Kotlin Component vs SubComponent tests showed that the difference between these two configurations is negligible. Dagger 2 SubComponent variant is slightly faster: _1.54s_ vs _1.57s_ with _100_ features on my Macbook Pro Late 2015.

#### Java SubComponent and Modules + Kotlin Features

The situation with Java Components is different. Using Java to construct Dagger Tree reduces roughly in half the time consumed by `kapt` tasks. This leads to `20s` build time boost on my Macbook Pro Late 2015. 

It seems that the more Kotlin annotated code the slower the build is. Maybe using method injection instead of multiple property injections can also reduce build time significantly 

## Contributing
If you feel that my methodology is wrong or there are some tricks to speed up Dagger 2 build times on android projects, feel free to post an Issue or make a Pull Request
