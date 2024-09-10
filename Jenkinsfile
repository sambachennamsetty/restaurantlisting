pipeline {
    agent any  // Run on any available agent

    environment {
        // Jenkins credentials for DockerHub and the build version
        DOCKERHUB_CREDENTIALS = credentials('DOCKER_HUB_CREDENTIALS')
        VERSION = "${env.BUILD_ID}"  // Use Jenkins build ID as the version
    }

    tools {
        maven "Maven"  // Use Maven tool configured in Jenkins
    }

    stages {

        stage('Maven Build') {
            steps {
                // Clean and package the Maven project, skipping tests
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                // Run the test cases using Maven
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Perform SonarQube analysis using the Jacoco plugin for code coverage
                sh '''
                mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar \
                -Dsonar.host.url=http://3.90.16.226:9000/ \
                -Dsonar.login=squ_ec0dcd3f1f596b2ef6c2428cb8361b94deee4815
                '''
            }
        }

        stage('Check code coverage') {
            steps {
                script {
                    // SonarQube token, URL, and component key for API requests
                    def token = "squ_ec0dcd3f1f596b2ef6c2428cb8361b94deee4815"
                    def sonarQubeUrl = "http://3.90.16.226:9000/api"
                    def componentKey = "com.codedecode:restaurantlisting"
                    def coverageThreshold = 80.0

                    // API call to get coverage data
                    def response = sh (
                        script: "curl -H 'Authorization: Bearer ${token}' '${sonarQubeUrl}/measures/component?component=${componentKey}&metricKeys=coverage'",
                        returnStdout: true
                    ).trim()

                    // Parse the coverage value using jq
                    def coverage = sh (
                        script: "echo '${response}' | jq -r '.component.measures[0].value'",
                        returnStdout: true
                    ).trim().toDouble()

                    // Log the coverage value
                    echo "Coverage: ${coverage}"

                    // Fail the build if coverage is below the threshold
                    if (coverage < coverageThreshold) {
                        error "Coverage is below the threshold of ${coverageThreshold}%. Aborting the pipeline."
                    }
                }
            }
        }

        stage('Docker Build and Push') {
            steps {
                script {
                    // Print the Docker Hub credentials (for debugging, password hidden)
                    echo "DOCKERHUB_CREDENTIALS_USR: ${DOCKERHUB_CREDENTIALS_USR}"
                    echo "DOCKERHUB_CREDENTIALS_PSW: [HIDDEN]"

                    // Print the version
                    echo "VERSION: ${VERSION}"

                    // Docker login using credentials from Jenkins
                    sh 'echo "$DOCKERHUB_CREDENTIALS_PSW" | docker login -u "$DOCKERHUB_CREDENTIALS_USR" --password-stdin'

                    // Build the Docker image
                    sh 'docker build -t sambachennamsetty/restaurant-listing-service:${VERSION} .'

                    // Push the Docker image to DockerHub
                    sh 'docker push sambachennamsetty/restaurant-listing-service:${VERSION}'
                }
            }
        }

        stage('Cleanup Workspace') {
            steps {
                // Clean the workspace to remove any leftover files
                deleteDir()
            }
        }

        stage('Update Image Tag in GitOps') {
            steps {
                // Checkout the GitOps repo to update the image tag
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'git-ssh', url: 'git@github.com:sambachennamsetty/deployment-folder.git']])

                script {
                    // Update the image tag in the YAML manifest using sed
                    sh '''
                    sed -i "s/image:.*/image: sambachennamsetty\\/restaurant-listing-service:${VERSION}/" aws/restaurant-manifest.yml
                    '''

                    // Commit the changes to the Git repository
                    sh 'git checkout master'
                    sh 'git add .'
                    sh 'git commit -m "Update image tag"'

                    // Push the changes using SSH credentials
                    sshagent(['git-ssh']) {
                        sh('git push')
                    }
                }
            }
        }
    }
}
