pipeline {
  agent {
    label "k8s"
  }

  // scm poll 
  triggers {
    pollSCM('* * * * *')
  }

  stages {
    // compile
    stage("Compile") {
      steps {
        sh "./gradlew compileJava"
      }
    }
    // unit test
    stage("Unit test") {
      steps {
        sh "./gradlew test"
      }
    }
    // code coverage
    stage("Code coverage") {
      steps {
        sh "./gradlew jacocoTestReport"
        publishHTML(
          target: [
            reportDir: 'build/reports/jacoco/test/html',
            reportFiles: 'index.html',
            reportName: "JaCoCo Report"
          ]
        )
        sh "./gradlew jacocoTestCoverageVerification"
      }
    }
    // pakcage
    stage("Package") {
      steps {
        sh "./gradlew bootJar"
        // sh "./gradlew build"
      }
    }
    // docker build
    stage("Build docker image") {
      steps {
        sh "docker build -t harbor-registry.inner.youdao.com/infraop/spring-boot-docker:${BUILD_TIMESTAMP} ."
      }
    }
    // docker push to registry
    stage(
      "Push docker image to registry"
    ) {
      steps {
        sh "echo $CI_HARBOR_TOKEN | docker login -u $CI_HARBOR_USER --password-stdin $CI_HARBOR_REGISTRY"
        sh "docker push harbor-registry.inner.youdao.com/infraop/spring-boot-docker:${BUILD_TIMESTAMP}"
      }
    }

    // deploy to staging
    stage("Deploy to staging") {
      steps {
        sh "docker run -d --rm -p 8765:8080 --name calculator harbor-registry.inner.youdao.com/infraop/spring-boot-docker:${BUILD_TIMESTAMP}"
      }
    }

    // smoke test

    stage("Acceptance test") {
      steps {
        sleep 60
        sh "chmod +x acceptance_test.sh && ./acceptance_test.sh"
      }
    }
  }

  post {
    always {
      sh "docker stop calculator"
    }
  }

}
