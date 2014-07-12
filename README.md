**IMPORTANT: I could not finnish the submission in time so you can just skip judging it.**

---------------------------------------

ase34's ten.java submission
==============================

[![ten.java](https://cdn.mediacru.sh/hu4CJqRD7AiB.svg)](https://tenjava.com/)

This is a submission for the 2014 ten.java contest.

- __Theme:__ __What random events can occur in Minecraft?__
- __Time:__ Time 3 (7/12/2014 14:00 to 7/13/2014 00:00 UTC)
- __MC Version:__ 1.7.9 (latest Bukkit beta)
- __Stream URL:__ None

<!-- put chosen theme above -->

---------------------------------------

Compilation
-----------

- Download & Install [Maven 3](http://maven.apache.org/download.html)
- Clone the repository: `git clone https://github.com/tenjava/ase34-t3`
- Compile and create the plugin package using Maven: `mvn`

Maven will download all required dependencies and build a ready-for-use plugin package!

---------------------------------------

Usage
-----

To install the plugin, just move it into the */plugins* directory, nothing more to do.

This plugin tries to simulate genetic properties and inheritance for the animals (cow, pig, chicken, sheep) in the game, allowing to actively breed specialized variants of animals.

Every animal starts with a specified list of properties with a value of _around_ 100%. A higher value represents more efficiency/quality, alower one less.

* __GROWTH__: Reduces the time of growing up from a baby animal to an adult one
* __FERTILITY__: Reduces the delay until an adult animal can make a baby again
* __SIZE__: Increases the amount of beef dropped
* __EGG_DROP__: Reduces the delay until a new egg gets dropped
* __WOOL__: Increases the amount of wool dropped

These properties are given to the following animals:

* __Pig__: GROWTH, FERTILITY, SIZE
* __Cow__: (same as _Pig_)
* __Chicken__: GROWTH, FERTILITY, EGG_DROP
* __Sheep__: GROWTH, FERTILITY, WOOL

When a new baby gets spawned, it inherits their properties from their parents, with a small additional mutation added.

To inspect the properties of a living animal, you can use the _Gene Probe Inspector_. In order to craft it, you just need a stick and a **** 