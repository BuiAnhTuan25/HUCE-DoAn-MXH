static def fillEnv(BRANCH_NAME) {
    def env = "dev"
    switch (BRANCH_NAME) {
        case ~'.*develop.*|.*feature/build.*':
            env = "dev"
            break
        case ~'.*qa.*':
            env = "qa"
            break
        case ~'.*sit.*':
            env = "sit"
            break
        case ~'.*prod.*':
            env = "prod"
            break
    }

    return env
}

static GString fillImgTag(BRANCH_NAME) {
    def now = new Date().format("yyyyMMddHHmm", TimeZone.getTimeZone('UTC'))
    GString tag = "0.0.${now}-${BRANCH_NAME.tokenize('/').pop()}"
    switch (BRANCH_NAME) {
        case ~'.*develop.*|.*feature/build.*':
            tag = "0.0.${now}-${BRANCH_NAME.tokenize('/').pop()}"
            break
        case ~'.*qa.*':
            tag = "0.1.${now}-${BRANCH_NAME.tokenize('/').pop()}"
            break
        case ~'.*sit.*':
            tag = "0.2.${now}-${BRANCH_NAME.tokenize('/').pop()}"
            break
        case ~'.*prod.*':
            tag = "1.0.${now}-${BRANCH_NAME.tokenize('/').pop()}"
            break
    }

    return tag
}

pipeline {
    agent any

    tools {
        jdk 'JDK-21'
    }

    environment {
        // SonarQube
        SONAR_PROJECT_KEY = 'social-service'

        // Docker
        DOCKER_IMAGE     = 'buianhtuan25/social-service'
        IMAGE_TAG        = fillImgTag(env.GIT_BRANCH)

        // Kubernetes
        APP_ENV          = fillEnv(env.GIT_BRANCH)
        K8S_DEPLOYMENT   = 'social-service'
        K8S_CONTAINER    = 'social-service'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Check sonar') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                        -Dsonar.projectName=${SONAR_PROJECT_KEY} \
                        -Dsonar.java.binaries=target/classes
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} ."
                sh "docker tag ${DOCKER_IMAGE}:${IMAGE_TAG} ${DOCKER_IMAGE}:latest"
            }
        }

        stage('Push image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-credentials',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                    sh "docker push ${DOCKER_IMAGE}:${IMAGE_TAG}"
                    sh "docker push ${DOCKER_IMAGE}:latest"
                }
            }
        }

        stage('Deploy') {
            steps {
                withKubeConfig([credentialsId: 'kubeconfig']) {
                    sh """
                        kubectl set image deployment/${K8S_DEPLOYMENT} \
                            ${K8S_CONTAINER}=${DOCKER_IMAGE}:${IMAGE_TAG} \
                            -n ${APP_ENV}

                        kubectl rollout status deployment/${K8S_DEPLOYMENT} \
                            -n ${APP_ENV} \
                            --timeout=120s
                    """
                }
            }
        }
    }
}