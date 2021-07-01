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
        stage('Sonar Analysis'){
            enviroment{
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps{
                bat 'echo step Sonar Analysis'
                withSonarCubeEnv('SONAR_LOCAL'){
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=b8d5013b8db549a2b2c336425017ab5956e56573 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
                }
                
            }
        }
    }
}