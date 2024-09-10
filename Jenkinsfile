pipeline {
  agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('DOCKER_HUB_CREDENTIALS')
        VERSION = "${env.BUILD_ID}"
    }


  tools {
    maven "Maven"
  }

  stages {

    stage('Maven Build'){
        steps{
        sh 'mvn clean package  -DskipTests'
        }
    }

     stage('Run Tests') {
      steps {
        sh 'mvn test'
      }
    }

    stage('SonarQube Analysis') {
  steps {
    sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -Dsonar.host.url=http://3.90.16.226:9000/ -Dsonar.login=squ_ec0dcd3f1f596b2ef6c2428cb8361b94deee4815'
  }
}


   stage('Check code coverage') {
            steps {
                script {
                    def token = "squ_ec0dcd3f1f596b2ef6c2428cb8361b94deee4815"
                    def sonarQubeUrl = "http://3.90.16.226:9000/api"
                    def componentKey = "com.codedecode:restaurantlisting"
                    def coverageThreshold = 80.0

                    def response = sh (
                        script: "curl -H 'Authorization: Bearer ${token}' '${sonarQubeUrl}/measures/component?component=${componentKey}&metricKeys=coverage'",
                        returnStdout: true
                    ).trim()

                    def coverage = sh (
                        script: "echo '${response}' | jq -r '.component.measures[0].value'",
                        returnStdout: true
                    ).trim().toDouble()

                    echo "Coverage: ${coverage}"

                    if (coverage < coverageThreshold) {
                        error "Coverage is below the threshold of ${coverageThreshold}%. Aborting the pipeline."
                    }
                }
            }
        }


        stage('Docker Build and Push') {
            steps {
                script {
                        // Print the Docker Hub credentials (user)
                        echo "DOCKERHUB_CREDENTIALS_USR: ${DOCKERHUB_CREDENTIALS_USR}"

                        // For security, avoid printing the password
                       echo "DOCKERHUB_CREDENTIALS_PSW: [HIDDEN]"

                        // Print the version
                        echo "VERSION: ${VERSION}"

                        // Docker login
                        sh 'echo "$DOCKERHUB_CREDENTIALS_PSW" | docker login -u "$DOCKERHUB_CREDENTIALS_USR" --password-stdin'

                        // Build the Docker image
                        sh 'docker build -t sambachennamsetty/restaurant-listing-service:${VERSION} .'

                        // Push the Docker image to the registry
                        sh 'docker push sambachennamsetty/restaurant-listing-service:${VERSION}'
                }
            }
        }


     stage('Cleanup Workspace') {
      steps {
        deleteDir()

      }
    }



    stage('Update Image Tag in GitOps') {
      steps {
         checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[ credentialsId: 'git-ssh', url: 'git@github.com:sambachennamsetty/deployment-folder.git']])
        script {
       sh '''
          sed -i "s/image:.*/image: sambachennamsetty\\/restaurant-listing-service:${VERSION}/" aws/restaurant-manifest.yml
        '''
          sh 'git checkout master'
          sh 'git add .'
          sh 'git commit -m "Update image tag"'
        sshagent(['git-ssh'])
            {
                  sh('git push')
            }
        }
      }
    }

  }

}

