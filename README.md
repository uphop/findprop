# findprop

Hello there!

Set-up EC2

1. Connect to EC2 instance:
    1. ssh -i "findprop-api-dev-key-pair.pem" ubuntu@ec2-34-244-237-138.eu-west-1.compute.amazonaws.com
2. Install MySQL: 
    1. sudo apt install mysql-server
    2. sudo systemctl status mysql
3. Connect to MySQL and change root password: 
    1. sudo mysql
    2. ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password by ‘<new password>’;
    3. exit
4. Secure MySQL: 
    1. sudo mysql_secure_installation
5. Install Java: 
    1. sudo apt update
    2. sudo apt install -y openjdk-17-jre
    3. java -version
6. Create database:
    1. sudo mysql -p<root password>
    2. Run scripts from findprop-loader/src/resources/init-mysql.sql
7. Create landing folders on EC2 instance:
    1. mkdir findprop
    2. cd findprop
    3. mkdir data
8. Upload loader and data files from local machine to EC2 instance:
    1. cd findprop/findprop-loader
    2. ./mvnw clean package -DskipTests
    3. scp -i ~/Projects/findprop/findprop-api/findprop-api-dev-key-pair.pem target/findprop-loader-0.1.jar ubuntu@ec2-34-244-237-138.eu-west-1.compute.amazonaws.com:/home/ubuntu/findprop
    4. scp -i ~/Projects/findprop/findprop-api/findprop-api-dev-key-pair.pem data/*.csv ubuntu@ec2-34-244-237-138.eu-west-1.compute.amazonaws.com:/home/ubuntu/findprop/data/
10. Run loader on EC2 instance:
    1. java -jar findprop-loader-0.1.jar com.lightson.findpropapi.loader.BatchConfiguration findPropImportJob
11. Upload API from local machine to EC2 instance:
    1. scp -i ~/Projects/findprop/findprop-api/findprop-api-dev-key-pair.pem target/findprop-api-0.1.jar ubuntu@ec2-34-244-237-138.eu-west-1.compute.amazonaws.com:/home/ubuntu/findprop
12. Run API:

Configure HTTPS:
1. Install certbot: https://certbot.eff.org/instructions










