This application is an advanced task manager in which you can customize almost everything. You can manage your project, team in project and even tasks for each project. This application is fully customizable by user.

**Main features:** <br/>
-customize your project (e.g. accept/decline access to project, create/edit project etc.)<br/>
-manage team in project (e.g. add/remove contributors, append to contributors their role in project etc.)<br/>
-customize tasks in project (e.g. create your own fields for each task type, create your own task types etc.)<br/>

**Technology stack:**<br/>
Java 8, Maven, Spring Boot 1.5.9.RELEASE (will be migrated to version 2.0.4.RELEASE), Spring Security (authorization via JWT), Spring WebSockets, PostgreSQL, Spock Framework, Lombok, Angular 5, etc.

**Getting started:**<br/>
To get started with this application you should have installed on your computer such things as: JDK 8 or higher, Nodejs, PostgreSQL database, Maven.<br/>

Database connection:<br/>
name: scrumwb<br/>
user name: scrmwb<br/>
password: billteam<br/>
<br/>
Test database:<br/>
name: scrumwb_test<br/>
user name: scrmwb<br/>
password: billteam
<br/>

**Running application**:<br/>
To run this application go to the root folder open the command line and type here: **mvn spring-boot:run**. This will start the back end server. Now, go to the frontend folder in src package and again open the command line and type here: **npm start** - this command will start the front end server.
<br/>
<br/>

**Contributing:**<br/>
For each task (our tasks are here: trello.com/b/4PyesJ92/scrumweb) you should create a new brunch.<br/>
Branch name should be as a task title.<br/>
Before merging you should create pull request.<br/>
To close pull request minimum 2 reviewers should accept your pull request.<br/>
Please, merge using -> git merge --squash "branch_name".<br/>
