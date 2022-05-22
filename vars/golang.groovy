def lintChecks()
{
    sh '''
      # ~/node_modules/jslint/bin/jslint.js server.js
       echo Link checks for ${COMPONENT}
       '''
}




def call(){
    pipeline{
        agent any

        environment {
            SONAR = credentials('SONAR')
        }

        stages{
            stage('Lint Checks') {
                steps{
                    script{
                        lintChecks()
                    }

                }
            }

            stage('Sonar Checks') {
                steps{
                    script{
                        env.ARGS= "-Dsonar.sources=."
                        common.sonarChecks()
                    }

                }
            }

            stage('Test Cases'){
                parallel{
                    stage('Unit Testing'){
                        steps{
                            sh'echo Unit Test cases'
                        }
                    }

                    stage('Integration Testing'){
                        steps{
                            sh'echo Integration Test cases'
                        }
                    }

                    stage('Functional Testing'){
                        steps{
                            sh'echo Functional Test cases'
                        }
                    }
                }


            }
        }
    }


}
