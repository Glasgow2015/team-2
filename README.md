# Urban Roots Volunteering App

This app was developed for the Code For Good challenge by J.P. Morgan.

It lets users apply as volunteers for the non-profit Urban Roots, whose primary aim is to sponsor community gardens.
It also lets users submit reports about things that need to be done in the community. Administrators can assign tasks to volunteers.

## Architecture

The system has 3 semantically distinct parts:

1. A REST API for interacting with the models (volunteers, jobs, reports, etc.)
2. A web administrative interface, so that the admin can look over volunteering applications, accept them or reject them, look over reports, accept them or reject them, and assign volunteers to reports/jobs.
3. A mobile application aimed at the volunteers which talks to the API.

