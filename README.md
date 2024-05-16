# LittleGroot: Farm Management System

LittleGroot is a farm management system developed in Java. It is designed to streamline the process of managing a farm, from tracking finances to monitoring crop growth. It is developed as an assignment of the module "Object-oriented programming with Java" at NIBM Kandy Innovation Center.

## Table of Contents

- [Features Overview](#features-overview)
- [System Workflow](#system-workflow)
- [Database Setup](#database-setup)
- [Development Methodology](#development-methodology)
- [Design](#design)
- [Team members](#team-members)
- [License](#license)
- [Note on Commits](#note-on-commits)

## Features Overview

- Dashboard with an overview of the entire farm (Work in progress)
- Get insights on field health with graphs and charts (Work in progress)
- Track finances (Work in progress)
- Manage inventory
- Manage tasks
- Add measured field metrics such as Soil pH, Soil moisture, and Light intensity (Work in progress)
- Add employees (Only for Managers)
- Reset passwords with security questions (Work in progress)
- Change password
- "Finance" and "Employees" are only visible to Managers (Work in progress)
- Get insights on fields separately (Work in progress)

## System Workflow

- **Authorization:** An employee must be registered by a higher authority to gain access to the system. There is no sign-up page, as this process is handled internally.
- During the sign-in process, the application validates the login information by checking with the MySQL database.
- If it is the user's first time logging in, they will be directed to a page where they are asked to add security questions. (Work in progress)
- Upon successful login, the application creates a User object and stores important details about the current user in its attributes. This helps reduce the number of database connections.
- Based on the logged-in user's role, the application determines the class of the object to be created. If the user is a Manager, the object is created from the Manager subclass; otherwise, it is created in the User class. This use of inheritance enables the implementation of user type restrictions.
- Logging out can be done via the Settings panel.

## Database Setup
1. Open MySQL Workbench and connect to your local MySQL server.
2. In the Navigator panel on the left, click on “Data Import/Restore” under the “Management” section.
3. Choose the option “Import from Self-Contained File” and select [this](https://github.com/gamithasam/LittleGroot/blob/master/Database/DatabaseDump.sql) .sql file.
4. Click on “Start Import” to start the import process.
This will recreate the database on your local MySQL server. Remember to replace the database connection details in the Java project with your local MySQL server details.

## Development Methodology

This project was developed using the Waterfall methodology. The requirement specification document can be found [here](https://github.com/gamithasam/LittleGroot/blob/master/Requirements/SRS%20-%20Java%20Project.pdf).

## Design

The design for this project was created using Figma. The wireframe can be found [here](https://github.com/gamithasam/LittleGroot/tree/master/Design/Wireframe). The High-Fidelity Mockup can be found [here](https://github.com/gamithasam/LittleGroot/tree/master/Design/Hifi%20Mockup)

## Team members

- [Gamitha Samarasingha](https://github.com/gamithasam)
- [Heshani Chamudika](https://github.com/heshanichamudika)
- [Himasha Sawani](https://github.com/HimashaSawani)
- [Rashmi Ekanayake](https://github.com/RashmiAyodhya)

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE.md](https://github.com/gamithasam/LittleGroot/blob/master/LICENSE.md) file for details

## Note on Commits

You may notice that most of the commits in this project were made by a single team member. This is not indicative of the distribution of work within our team. However, as some team members were not familiar with Git, their contributions were shared via WhatsApp and then added to the repository by a team member who is comfortable with Git. We are working on improving our Git skills to ensure a more accurate representation of our team's work in future projects.
