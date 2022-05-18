def lintChecks(){
    sh '''
      # ~/node_modules/jslint/bin/jslint.js server.js
      # mvn checkstyle:check
      echo Lint Checks for ${COMPONENTS}
       '''
        }

def call(){
    pipeline{
        agent any

        stages{
            stage('Lint Checks') {
                steps{
                    script{
                       lintChecks()
                    }

                }
            }
        }
    }


}
