pipeline{
    agent any
    stages{
        stage('Build backend'){
            steps{
                bat 'echo step Build backend'
                bat 'mvn clean package -DskipTests=true'
            }
        }
    }
}