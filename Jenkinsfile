pipeline {
  agent {
    label "k8s"
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
        sh "docker build -t harbor-registry.inner.youdao.com/infraop/spring-boot-docker ."
      }
    }
    // docker push to registry
    stage(
      "Push docker image to registry"
    ) {
      steps {
        // withCredentials([usernamePassword(
        //   credentialsId: 'dockerhub',
        //   usernameVariable: 'USERNAME',
        //   passwordVariable: 'PASSWORD'
        // )]) {
        //   sh "docker login -u ${USERNAME} -p ${PASSWORD}"
        // }
        // sh "docker tag spring-boot-docker:latest registry.cn-hangzhou.aliyuncs.com/xxx/spring-boot-docker:latest"
        sh "echo $CI_HARBOR_TOKEN | docker login -u $CI_HARBOR_USER --password-stdin $CI_HARBOR_REGISTRY"
        sh "docker push harbor-registry.inner.youdao.com/infraop/spring-boot-docker"
      }
    }


  }
}
