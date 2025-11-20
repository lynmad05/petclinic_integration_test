pipeline {

    agent any

    tools {
        maven 'Maven'
        jdk 'Java'
        // git 'Git'
    }

    stages {

        stage('Checkout') {
            steps {
                echo '📥 Getting code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '🔨 Building...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo '🧪 Testing...'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo '📦 Creating JAR...'
                sh 'mvn package -DskipTests'
            }
        }
        //Nuevo stage profesor
        stage('Extra') {
             steps {
                 echo 'Nuevo stage agregado (sin acciones por ahora)'
             }
        }
    }
}
