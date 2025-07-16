This application is an advanced task manager in which you can customize almost everything. You can manage your project, team in project and even tasks for each project. This application is fully customizable by user.

# **How to run the application:** <br/>
- you can use `docker compose up` to spin all the services and run the application locally. It will start database, UI and backend locally <br/>
- you can use `build-and-push-compose.sh` to build and push new versions of Docker images to the Docker Hub private repository (required if you want to deploy it to Docker Swarm cluster) <br/>
- you can deploy application using `deploy.sh` script which will deploy the application to Docker Swarm cluster (make sure you have access to Docker Hub private repository) <br/>

# **Main features:** <br/>
-customize your project (e.g. accept/decline request access to project, create/edit project etc.)<br/>
-manage team in project (e.g. add/remove contributors, append to contributors their role in project etc.)<br/>
-customize tasks in project (e.g. create your own fields for each task type, create your own task types etc.)<br/>

# **Technology stack:**<br/>
Java 8, Maven, Spring Boot 1.5.9.RELEASE (will be migrated to version 2.0.4.RELEASE), JPA, Spring Security (authorization via JWT), Spring WebSockets, PostgreSQL, Spock Framework, Lombok, Angular 5, etc.

# **Getting started:** <br/>
To get started with this application you should have installed on your computer such things as: JDK 8, Nodejs, PostgreSQL database, Maven. <br/>

    Database connection:
    name: scrumwb
    user name: scrmwb
    password: billteam
  
    Test database:
    name: scrumwb_test
    user name: scrmwb
    password: billteam
    
## **Before running the application do not forget to install all dependecies!<br/>**

### Back end:<br/>
Go to the root folder, open the command line and type here: `mvn clean install` - this command will install all neccessary dependencies for back end side.

### Front end:<br/>
Go to the frontend folder (`src package`), open the command line and type here: `npm install` - this command will install all neccessary dependencies for the front end. 
<br/> Also next dependencies should be installed: <br/>
`npm install bootstrap@4.0.0`<br/>
`npm install jquery`<br/>
`npm install popper.js angular-popper`<br/>

# **How to run the application**:<br/>
To run this application go to the root folder open the command line and type here: `mvn spring-boot:run`. This will start the back end server. Now, go to the frontend folder in `src package` and again open the command line and type here: `npm start` - this command will start the front end server.
<br/>

# **Contributing:**<br/>
For each task (our tasks are here: `trello.com/b/4PyesJ92/scrumweb`) you should create a new brunch.<br/>
Branch name should be as a task title.<br/>
Before merging you should create pull request.<br/>
To close pull request minimum 2 reviewers should accept your pull request.<br/>
Please, merge using this command `git merge --squash "branch_name"`.<br/>
