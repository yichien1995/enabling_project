# Enabling
- Website URL: https://enabling.site/
- Demo video URL: https://drive.google.com/file/d/1K37Gsp0nFA1T9pSK9K_7R8F_neAKaT-Z/view?usp=sharing

## Introduction
Enabling is a platform that empowers medical workers to create websites for their institutions, streamlining administrative processes and integrating clinical workflows.

## Feature
- **Admin** user: 
  - Easily create a website for your institution by entering basic information and choosing a domain name (a segment of the URL).
  - Edit and manage your website content through a user-friendly backend interface.
  - Customize and preview the design of your homepage.
  - Integrate administrative workflows, including team member management and client management.
- **Therapist** user:
  - Modify medical articles on the institution's website (an aspect of website management, with permissions varying based on roles).
  - Manage client list.
  - Report client attendance to supervisors.
## Database Schema
![enabling_db_schema](https://github.com/yichien1995/enabling_project/assets/152785669/1149a393-771f-447b-be8f-c503bd742940)
## Architecture
![Architecture](https://github.com/yichien1995/enabling_project/assets/152785669/b1c1e354-5dbe-49d0-90ed-46bcc88498f3)
- Implemented a multi-tenant architecture, enabling multiple institutions to operate independently within the same system.
- Applied Thymeleaf templates for SSR, reducing code size and improving code maintainability.
- Utilized session/cookie-based authorization for role-based access control (RBAC).
- Optimized authorization validation process through storing sessions in Redis. 
- Integrated third-party APIs such as Google Maps API and Geocoding API, as well as data visualization tools like Full calendar and Plotly.
- Deployed the project on AWS EC2 , utilizing Nginx for reverse proxy, integrateing cloud-based services such as RDS (MySQL), ElastiCache (Redis), S3, and CloudFront to create a stateless infrastructure.
## Contact me
Email: lisa241231@gmail.com
