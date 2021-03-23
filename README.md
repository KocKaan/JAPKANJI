# JAPKANJI


The client and advisor Amaha Ayaka is currently teaching introduction level Japanese in the Japanese Culture Foundation. She explained to me that the students in her class are having problem memorizing the kanji words, which is more complex alphabet than normal 26 letter Hiragana. She told me that when she teaches them the kanji letters student don’t have problem understanding the meaning but after one day they forget the letter or stroke order. She told me that this occurs because the student are young thus does not study at home. She asked for a computer game that challenges students ability to memorize kanji’s.

As the user of this educational software will be young students I suggested creating a game which involves competition which hopefully create ambition among students. This could be done by point mechanism and rewarding. In addition to that every question has time limit based on its level of hardness. We gathered at a café and Miss Ayaka stated that she liked the solution she also asked for a teacher interference which allows her to enter new kanji beside the pre-established kanji questions. The detail of the meeting is at Appendix.


1. At the home page there should be 2 types of user accepted (student and teacher).
2. The teacher button should lead to four main options: Home, Levels,  Questions, Users
3. The student button should lead two main options: home and game where the game could be selected in accordance to wanted levels by the student. The games should consist of multiple choice and written questions.

4. Teacher should be able to create, edit and delete questions.

5. Student could play the game and see his/her result on the table.

6. Teacher could create as many level as he/she wants.


## Technologies That Has Been Utilized

1.	Netbeans 11.1	Used for developer IDE
2.	Java 8 JDK	Needed JDK for GlassFish 5.1.0 	
3.	Java 12 JDK	For developing the app
4.	Apache Derby Database	Database Server for web Application (storing data)
5.	GlassFish Server 5.1.0	Web Server for publishing web application
6.	JavaServer Faces (JSF) 2.3
	UI library for Java Server Faces	Free and rich UI components for Java Server faces.
7.	Java Persistence API (JPA) 2.0	Entity classes from the database, and manage transactions. 	(the default persistence provider for the GlassFish server.)
8.	PrimeFaces 	UI Components
9.	Enterprise JavaBeans (EJB) 3.1	stateless EJBs with the entity classes and the business logic for the application

### Flow Chart - Login

<img width="453" alt="image" src="https://user-images.githubusercontent.com/75183908/112090603-3b24e500-8b6a-11eb-8241-1ccf740108a6.png">

### Flow Chart - Create/Edit/Delete and View

<img width="372" alt="image" src="https://user-images.githubusercontent.com/75183908/112090677-69a2c000-8b6a-11eb-9adb-0556e04c77a0.png">

### Flow Chart - Game

<img width="453" alt="image" src="https://user-images.githubusercontent.com/75183908/112090749-8808bb80-8b6a-11eb-801b-f289ed99e6f1.png">

## Database Architecture

<img width="452" alt="image" src="https://user-images.githubusercontent.com/75183908/112087837-17ab6b80-8b65-11eb-9ec9-28641a46cf9d.png">

   	Database Table	Description	Relations

1.	Levels:	The program's question level informations are stored. Level name, Level time and Level order is stored. 	-
2.	Questions:	Information on question is stored. The table consists of level id, question image and text, question types and question point. 	It has a foreign key relations to Levels table.
3.	Results:	Results on user id, question id, Level ID points for that level, answers to the questions and the outcome of the answered questions will be stored. 	
4.	Users:	Users first name, Surname, user Name, password and user type (teacher or student) is stored. 	

## Web Application

While developing the program two wizard has been used.
-	The first wizard is “Entity Classes from Database wizard” With this entity classes has been created.
-	The second wizard is “JSF Pages from Entity Classes wizard”, default web pages has been created.

<img width="452" alt="image" src="https://user-images.githubusercontent.com/75183908/112088061-7a046c00-8b65-11eb-817d-a1fd1d22b1ec.png">

## Graphical User Interface - Admin Level
Below there are the HTLM codes for Levels page’s list page. The data and the colons for the Database web component are structuralized like in the example. The graphical side of the program is fairly basic and it meets the need of the client which is stated as basic and user friendly. In addition to that as I expect lots of students will use this program, I added basic sorting algorithms to sort the data that has been created by students. For example it is expected that abundant amount different users will play the first level of JapKanji therefore there will be lots of different points accumulated by different users. At this point by utilizing user friendly sorting boxes the user could select his/her own ID to find his her own result. Furthermore if there are overwhelming data created by that user(by playing multiple levels), the user could double sort: selecting his/her own id and the level that he/ she wants to see.

**JSF page: admin user Html Codes**

The Graphical User Interface html codes are obtained by prime face as a template. However as shown below these templated are modified and manipulated to be able to use and integrate to our backend java codes.

<img width="451" alt="image" src="https://user-images.githubusercontent.com/75183908/112089032-4296bf00-8b67-11eb-80a6-2cc2fa12c32d.png">

JSF page: Admin main menu
<img width="158" alt="image" src="https://user-images.githubusercontent.com/75183908/112089062-4e828100-8b67-11eb-9c5a-6ab6cec32b9c.png">


<img width="451" alt="image" src="https://user-images.githubusercontent.com/75183908/112089094-5e01ca00-8b67-11eb-915f-dae9c15c1b8a.png">

**Recording Test Results**

In order to record the answers that the students gave I created the TestResults class. In this class there are methods that save the results to the database. In order to restrict the amount of data that could be added to the database I restricted the insertion of redundant data. This is accomplished by deleting the old result when the level is played again. For example if one of Miss Ayaka’s student decides to play the level 1 again the old result is deleted and the new data is written on the database. In addition to that there is a distinction created for true and false answers.  As the program test whether the chosen answer is correct or false by if statement, the result is saved to SaveNewResult() according to whether it is true or false. At the diagrams those methods are revealed.

<img width="451" alt="image" src="https://user-images.githubusercontent.com/75183908/112089233-9e614800-8b67-11eb-98a8-f4d9ad003dc6.png">

At the end of SaveResult function there is CreateEditResult() function which is used record to database.

<img width="451" alt="image" src="https://user-images.githubusercontent.com/75183908/112089262-ad47fa80-8b67-11eb-8399-edf20af3559c.png">

This function is used for recording new results. The if statement is used because we want to distinguish between correct and false answer

<img width="451" alt="image" src="https://user-images.githubusercontent.com/75183908/112089312-c650ab80-8b67-11eb-8a9a-6fea3bc560b0.png">
DeleteOldResults function is responsible for deleting the old results.


### Teacher view create and Student user

<img width="1440" alt="Screen Shot 2021-03-23 at 00 06 41" src="https://user-images.githubusercontent.com/75183908/112091561-0580fb80-8b6c-11eb-8784-651a76ee194f.png">

<img width="278" alt="Screen Shot 2021-03-23 at 00 07 07" src="https://user-images.githubusercontent.com/75183908/112091574-0dd93680-8b6c-11eb-8780-a0e60fbfd838.png">

### Teacher view and create questions

<img width="1440" alt="Screen Shot 2021-03-23 at 00 07 33" src="https://user-images.githubusercontent.com/75183908/112091699-5a247680-8b6c-11eb-8fd6-320e08e0dfbc.png">

<img width="741" alt="Screen Shot 2021-03-23 at 00 07 54" src="https://user-images.githubusercontent.com/75183908/112091756-71fbfa80-8b6c-11eb-90e1-a4caefd16d12.png">

### Student choose level and play

<img width="1440" alt="Screen Shot 2021-03-23 at 00 08 21" src="https://user-images.githubusercontent.com/75183908/112091873-b5eeff80-8b6c-11eb-8cd8-10bb89ebd8b9.png">



## Possible Future Development Idea

Currently the product has two types of game for each levels: Multiple choice and short answer. While creating the question I allow the teacher to add text and picture to the questions. Then the teacher has to add the answer. One possible development could be adding different types of games. The developer could for example add matching type of question which would ensure the student to learn more kanji’s.

## Rereferences
1.	Generating a JavaServer Faces 2.x CRUD Application from a Database :[Netbeans Documentation](https://netbeans.org/kb/docs/web/jsf20-crud.html)_
2.	Netbeans Primefaces CRUD Generator Tutorial : [CRUD Tutorial](https://www.youtube.com/watch?v=kGHx3zttUQA)_
3.	Working with the Java DB (Derby) Database:[Java Database](https://netbeans.org/kb/docs/ide/java-db.html)_
4.	Installing and starting GlassFish server :[GlassFish Server](http://doraprojects.net/blog/?p=1521)_
5.	JDK 12: [JDK12 documentation](https://www.oracle.com/technetwork/java/javase/downloads/jdk12-downloads-5295953.html)_
6.	JDK 8:[JDK8 documentation](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)_
7.	NetBeans IDE:[NetBeans IDE](https://netbeans.apache.org/download/index.html)_
