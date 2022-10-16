# PROJECT MIRABILIUS

## Purpose
Project Mirabilius is intended for Computer Science undergraduate students pursuing research at the University of 
Virginia. The project cuts through the noise of UVA's research database and provides information on current research 
opportunities in a clear, succinct, and effective manner.
### A Note on the Name
In Latin, "mirabilius" is the comparative form of the word "mirabiliter," meaning miraculously. From my own experiences,
research is something that has worked personal miracles, hence the name.

## Authors
Emily Chang: ec5ug@virginia.edu

## Collaboration
This project was completed at the University of Virginia's 2022 Girls Hoo Hack Hackathon. This project would have not 
been possible without CS 3140 Software Development course material.

This project could not have been possible without these two individuals who helped me in the ideation of this project:
Shoug Alharbi and Yasmeen Ofleh.

## How to Set Up
### Cloning the Repository
To build this program, you must first have a GitHub account and create a personal authentication token. To do this, go
to your profile settings, scroll down on the left to select "<> Developer settings", then "Personal access tokens", and
generate a new token. Make sure to copy (and temporarily store) the token, as you will not be able to do this later
(as it gets hidden for protection).

Now, click on the Code dropdown menu and select the HTTPS tab. Copy the URL. (It should follow the format 
```https://github.com/ec5ug/ResearchRecommender.git```
).

Then, open a command prompt on your computer and move into directory (folder) you want to put the project in. Once there, 
type the following:
```bash
git clone https://github.com/ec5ug/ResearchRecommender.git
cd ResearchRecommender
```
Hooray! You are now in the project folder.
### Building With Gradle
For (non-Mac users) - open the Terminal Tab at the bottom and type:
```bash
/gradlew build
```
For Mac users - if you encounter an error saying "gradlew: command not found", type: 
```bash
chmod +x ./gradlew
```

### Running the Program
Once your gradle has built successfully, it is now time to run the jar file. To do this, enter the following commands. 
```bash
cd build/libs
java -jar Mirabilius-1.0-SNAPSHOT.jar
```