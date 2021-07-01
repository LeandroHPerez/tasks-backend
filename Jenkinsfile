pipeline{
    agent any
    stages{
        stage('Build backend'){
            steps{
                bat 'echo step Build backend'
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage('Unit Tests'){
            steps{
                bat 'echo step Unit Tests'
                bat 'mvn test'
            }
        }
    }
}