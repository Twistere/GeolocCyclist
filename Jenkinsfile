
pipeline {
    agent any

    stages {

        stage('Clean') {
            steps {
                echo 'Cleaning the project'
                sh 'mvn clean package -Dmaven.test.skip'
                sh 'mvn clean '
            }
        }
        stage('Build') {
            steps {
                echo 'Build the project from the pom file'
                sh 'mvn package -Dmaven.test.skip'
            }
        }
        stage('SeleniumTest') {
            steps {
                echo 'Test the app with selenium'
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploy the project on docker with wsl2'
                sh 'docker build --tag=dev_payara .'
                sh 'docker-compose up -d'
            }
        }
    }
}
