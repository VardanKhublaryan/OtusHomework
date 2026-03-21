pipeline {
    agent { label 'maven' }
    tools {
        // This MUST match the 'Name' you gave it in the Global Tools section
        allure 'Allure 2.30'
    }

    stages {
        stage('Test Allure CLI') {
            steps {
                sh "allure --version"
            }
        }

        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [[name: 'main']],
                          userRemoteConfigs: [[url: 'https://github.com/VardanKhublaryan/OtusHomework.git']]
                ])
            }
        }

        stage('Run Tests') {
            steps {
                // Run tests but do not fail the pipeline if there are test errors
                sh """
                mvn clean test
                """
            }
        }

        stage('Allure Report Publisher') {
            steps {
                echo "Publishing Allure results..."
                sh 'allure generate --clean allure-report'
                // Allure stage will always run even if tests had errors
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'allure-results']]
                ])
            }
        }
    }

    post {
        always {
            echo "Pipeline finished"
        }
    }
}