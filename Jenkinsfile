pipeline{
    agent any
    stages{
        stage('Build backend'){
            steps{
                bat 'echo stage Build backend'
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage('Unit Tests'){
            steps{
                bat 'echo stage Unit Tests'
                bat 'mvn test'
            }
        }
        stage('Sonar Analysis'){
            environment{
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps{
                bat 'echo stage Sonar Analysis'
                withSonarQubeEnv('SONAR_LOCAL'){
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=b8d5013b8db549a2b2c336425017ab5956e56573 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
                }
                
            }
        }
        stage('Quality Gate'){
            steps{
                sleep(10)               
                bat 'echo stage Quality Gate'
                timeout(time: 5, unit: 'MINUTES') { 
                    //waitForQualityGate abortPipeline: false
                }
            }
        }
        stage('Deploy Backend'){
            steps{
                bat 'echo stage Deploy Backend'
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
    }
}