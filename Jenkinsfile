pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    triggers {
        pollSCM('H/2 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'java -version'
                sh 'mvn -B clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn -B test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Coverage Report') {
            steps {
                publishHTML(target: [
                    reportDir: 'target/site/jacoco',
                    reportFiles: 'index.html',
                    reportName: 'JaCoCo Coverage',
                    keepAll: true,
                    allowMissing: true,
                    alwaysLinkToLastBuild: true
                ])
            }
        }

        stage('Deploy (staging)') {
            when { branch 'main' }
            steps {
                sh '''
                    mkdir -p $HOME/staging
                    pkill -f task-api || true
                    cp target/task-api-1.0.0.jar $HOME/staging/task-api.jar
                    JENKINS_NODE_COOKIE=dontKillMe nohup java -jar $HOME/staging/task-api.jar --server.port=9090 > $HOME/staging/app.log 2>&1 &
                    sleep 15
                    curl -f http://localhost:9090/actuator/health
                '''
            }
        }
    }

    post {
        success { echo 'Pipeline succeeded. Application deployed to staging.' }
        failure { echo 'Pipeline failed. Deployment skipped.' }
    }
}
