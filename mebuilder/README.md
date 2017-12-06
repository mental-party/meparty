[![Build Status](https://travis-ci.org/mental-party/mebuilder.svg?branch=master)](https://travis-ci.org/mental-party/mebuilder)

# MeBuilder
A Simple Generic Java Object Builder


#### Example
<code>
    
    YourClass newYourClass = GenericBuilder.of(YourClass::new)
        .with(YourClass::setId, 1)
        .build();
        
</code>


#For Developer
Build and generate publication.

<code>
 ./gradlew clean build generatePomFileForBintrayPublicationPublication -DbambooPublishNumber=0
</code>


### After publish to bintray

<code>
./gradlew -Pbintray-user=coskundeniz -Pbintray-key=**************** bintrayUpload -x clean
</code>