JAPNKANJI


The client and advisor Amaha Ayaka is currently teaching introduction level Japanese in the Japanese Culture Foundation. She explained to me that the students in her class are having problem memorizing the kanji words, which is more complex alphabet than normal 26 letter Hiragana. She told me that when she teaches them the kanji letters student don’t have problem understanding the meaning but after one day they forget the letter or stroke order. She told me that this occurs because the student are young thus does not study at home. She asked for a computer game that challenges students ability to memorize kanji’s.

As the user of this educational software will be young students I suggested creating a game which involves competition which hopefully create ambition among students. This could be done by point mechanism and rewarding. In addition to that every question has time limit based on its level of hardness. We gathered at a café and Miss Ayaka stated that she liked the solution she also asked for a teacher interference which allows her to enter new kanji beside the pre-established kanji questions. The detail of the meeting is at Appendix.


1. At the home page there should be 2 types of user accepted (student and teacher).
2. The teacher button should lead to four main options: Home, Levels,  Questions, Users
3. The student button should lead two main options: home and game where the game could be selected in accordance to wanted levels by the student. The games should consist of multiple choice and written questions.

4. Teacher should be able to create, edit and delete questions.

5. Student could play the game and see his/her result on the table.

6. Teacher could create as many level as he/she wants.


##Technologies That Has Been Utilized
1	Netbeans 11.1	Used for developer IDE
2	Java 8 JDK	Needed JDK for GlassFish 5.1.0 	
3	Java 12 JDK	For developing the app
4	Apache Derby Database	Database Server for web Application (storing data)
5	GlassFish Server 5.1.0	Web Server for publishing web application
6	JavaServer Faces (JSF) 2.3
	UI library for Java Server Faces	Free and rich UI components for Java Server faces.
7	Java Persistence API (JPA) 2.0	Entity classes from the database, and manage transactions. 	(the default persistence provider for the GlassFish server.)
8	PrimeFaces 	UI Components
9	Enterprise JavaBeans (EJB) 3.1	stateless EJBs with the entity classes and the business logic for the application

##Database Architecture
<img width="452" alt="image" src="https://user-images.githubusercontent.com/75183908/112087837-17ab6b80-8b65-11eb-9ec9-28641a46cf9d.png">

No	Database Table	Description	Relations
1	Levels:	The program's question level informations are stored. Level name, Level time and Level order is stored. 	-
2	Questions:	Information on question is stored. The table consists of level id, question image and text, question types and question point. 	It has a foreign key relations to Levels table.
3	Results:	Results on user id, question id, Level ID points for that level, answers to the questions and the outcome of the answered questions will be stored. 	
4	Users:	Users first name, Surname, user Name, password and user type (teacher or student) is stored. 	

##Web Application

While developing the program two wizard has been used.
-	The first wizard is “Entity Classes from Database wizard” With this entity classes has been created.
-	The second wizard is “JSF Pages from Entity Classes wizard”, default web pages has been created.

<img width="452" alt="image" src="https://user-images.githubusercontent.com/75183908/112088061-7a046c00-8b65-11eb-817d-a1fd1d22b1ec.png">
