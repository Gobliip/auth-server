### Docker Container Config

#### Container links

| Container Name | Description                                        |
| -------------- | -------------------------------------------------- |
| eureka         | Link to the dominator service discovery container  |
| authdb         | Link to the mysql databe container                 |

#### Environment Variables

| Environment Variable | Description                |
| -------------------- | -------------------------- |
| MYSQL_DATABASE       | Name of the mysql database |
| MYSQL_USER           | User for the mysql conn    |
| MYSQL_ROOT_PASSWORD  | User password for mysql    |
