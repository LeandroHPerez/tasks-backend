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
                sleep(5)               
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
        stage('Api Test'){
            steps{
                bat 'echo stage API Test'
                dir('api-test'){
                    git credentialsId: 'github_login', url: 'https://github.com/LeandroHPerez/tasks-api-test'
                    bat 'mvn test'
                }
            }
        }
        stage('Deploy Frontend'){
            steps{
                bat 'echo stage Deploy Frontend'
                dir('frontend'){
                    git credentialsId: 'github_login', url: 'https://github.com/LeandroHPerez/tasks-frontend'
                    bat 'mvn clean package -DskipTests=true'
                    deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
                }
            }
        }
        stage('Functional Test'){
            steps{
                bat 'echo stage Functional Test'
                sleep(5) 
                dir('functional-test'){
                    git credentialsId: 'github_login', url: 'https://github.com/LeandroHPerez/tasks-functional-tests'
                    bat 'mvn clean package -DskipTests=true'
                    bat 'mvn dependency:resolve'
                    bat 'mvn test'
                }
            }
        }
        stage('Deploy Prod'){
            steps{
                bat 'echo stage Deploy Prod'
                bat 'docker-compose build'
                bat 'docker-compose up -d'                
            }
        }
        stage('Health Check'){            
            steps{
                sleep(10)
                bat 'echo stage Health Check'
                bat 'mvn verify -Dskip.surefile.tests'                     
            }
        }
    }
}