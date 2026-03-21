pipeline {
    agent { label 'maven' }
    tools {
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
                // catchError allows the pipeline to continue even if mvn test fails
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    sh "mvn clean test -Dmaven.test.failure.ignore=true"
                }
            }
        }
    }

    post {
        always {
            echo "Publishing Allure results..."
            // The allure step automatically handles the 'allure generate' logic
            archiveArtifacts artifacts: 'target/allure-results/**', allowEmptyArchive: true
            allure([
                includeProperties: false,
                jdk: '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])
            echo "Pipeline finished"
        }
    }
}