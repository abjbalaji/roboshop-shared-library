def lintChecks(){
    sh '''
      # ~/node_modules/jslint/bin/jslint.js server.js
      #pylint *.py
      echo Lint Checks for ${COMPONENTS}
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
        }
    }


}
